package org.tangdao.modules.sys.utils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.method.HandlerMethod;
import org.tangdao.common.config.Global;
import org.tangdao.common.lang.ExceptionUtils;
import org.tangdao.common.lang.ObjectUtils;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.network.IpUtils;
import org.tangdao.common.suports.BaseEntity;
import org.tangdao.common.utils.SpringUtils;
import org.tangdao.common.web.http.UserAgentUtils;
import org.tangdao.modules.sys.model.domain.Log;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.service.ILogService;
import org.tangdao.modules.sys.service.IMenuService;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;

import eu.bitwalker.useragentutils.UserAgent;

public class LogUtils {

	/**
	 * 静态内部类，延迟加载，懒汉式，线程安全的单例模式
	 */
	private static final class Static {
		private static ILogService logService = SpringUtils.getBean(ILogService.class);
		private static IMenuService menuService = SpringUtils.getBean(IMenuService.class);
	}
	
	// 参数名获取工具（尝试获取标注为@ModelAttribute注解的方法，第一个参数名一般为主键名）
	private static ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();

	/**
	 * 保存日志
	 */
	public static void saveLog(User user, HttpServletRequest request, String logTitle, String logType){
		saveLog(user, request, null, null, logTitle, logType, 0);
	}
	
	/**
	 * 保存日志
	 * @param executeTime 
	 */
	public static void saveLog(User user, HttpServletRequest request, Object handler, Exception ex, String logTitle, String logType, long executeTime){
		if (user == null || StringUtils.isBlank(user.getUserCode()) || request == null){
			return;
		}
		Log log = new Log();
		if (handler instanceof HandlerMethod){
			HandlerMethod hm = ((HandlerMethod)handler);
			Method m = hm.getMethod();
			org.tangdao.common.annotation.Log dsl = m.getAnnotation(org.tangdao.common.annotation.Log.class);
			if(dsl!=null) {
				if(dsl.ignore()) {
					return;
				}
				if(StringUtils.isNotBlank(logTitle)) {
					logTitle = dsl.title();
				}
			}
		}
		log.setLogTitle(logTitle);
		log.setLogType(logType);
		if (StringUtils.isBlank(log.getLogType())){
			String sqlCommandTypes = ObjectUtils.toString2(request.getAttribute(SqlCommandType.class.getName()));
			if (StringUtils.containsAny(","+sqlCommandTypes+",", ",INSERT,", ",UPDATE,", ",DELETE,")){
				log.setLogType(Log.TYPE_UPDATE);
			}else if (StringUtils.contains(","+sqlCommandTypes+",", ",SELECT,")){
				log.setLogType(Log.TYPE_SELECT);
			}else{
				log.setLogType(Log.TYPE_ACCESS);
			}
		}
		log.setServerAddr(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
		log.setRemoteAddr(IpUtils.getRemoteAddr(request));
		UserAgent userAgent = UserAgentUtils.getUserAgent(request);
		log.setDeviceName(userAgent.getOperatingSystem().getName());
		log.setBrowserName(userAgent.getBrowser().getName());
		log.setUserAgent(request.getHeader("User-Agent"));
		log.setRequestUri(StringUtils.abbr(request.getRequestURI(), 255));
		log.setRequestParams(request.getParameterMap());
		log.setRequestMethod(request.getMethod());
		log.setExecuteTime(executeTime);
		log.setCreateBy(user.getUserCode());
		log.setCreateByName(user.getNickname());
		log.setCreateDate(new Date());
        
        // 获取异常对象
        Throwable throwable = null;
        if (ex != null){
        	throwable = ex.getCause();
        }else {
        	throwable = ExceptionUtils.getThrowable(request);
        }
		
		// 异步保存日志
		new SaveLogThread(log, handler, request.getContextPath(), throwable).start();
	}
	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private Log log;
		private Object handler;
		private String contextPath;
		private Throwable throwable;
		
		public SaveLogThread(Log log, Object handler, String contextPath, Throwable throwable){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.contextPath = contextPath;
			this.throwable = throwable;
		}
		
		@Override
		public void run() {
			// 获取日志标题
			if (StringUtils.isBlank(log.getLogTitle())){
				String permission = "";
				if (handler instanceof HandlerMethod){
					HandlerMethod hm = ((HandlerMethod)handler);
					Method m = hm.getMethod();
					// 获取权限字符串
					PreAuthorize rp = m.getAnnotation(PreAuthorize.class);
					if(rp != null) {
						Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+"); 
						Matcher matcher = pattern.matcher(rp.value());
						while(matcher.find()){
							permission = permission + matcher.group() + ",";
						} 
						permission = permission.replaceAll("'", "");
					}
					// 尝试获取BaseEntity的设置的主键值
					for (Class<?> type : m.getParameterTypes()){
						try {
							// 判断是否是BaseEntity的子类
							Class<?> superClass = type.getSuperclass();
							while(superClass != null && superClass != BaseEntity.class){
								superClass = superClass.getSuperclass();
							};
							// 如果是BaseEntity的子类，则获取主键名
							if (superClass != null){
								try {
									TableInfo tableInfo = TableInfoHelper.getTableInfo(type);
									if(tableInfo!=null) {
										log.setBizKey(log.getRequestParam(tableInfo.getKeyProperty()));
										log.setBizType(type.getSimpleName());
									}
								} catch (Exception e) {
									break;
								}
							}
						} catch (Exception e) {
							break;
						}
					}

					// 尝试获取标注为@ModelAttribute注解的方法，第一个参数名一般为主键名
					if (StringUtils.isBlank(log.getBizKey())){
						for (Method me : hm.getBeanType().getMethods()){
							ModelAttribute ma = AnnotationUtils.findAnnotation(me, ModelAttribute.class);
							if(ma != null){
								String[] ps = pnd.getParameterNames(me);
								if(ps != null && ps.length > 0){
									log.setBizKey(StringUtils.abbr(log.getRequestParam(ps[0]), 64));
									log.setBizType(me.getReturnType().getSimpleName());
									break;
								}
							}
						}
					}
				}
				String href = log.getRequestUri();
				if (StringUtils.startsWith(href, contextPath)){
					href = StringUtils.substringAfter(href, contextPath);
				}
				log.setLogTitle(Static.menuService.getMenuNamePath(href, permission));
			}
			if (StringUtils.isBlank(log.getLogTitle())){
				log.setLogTitle("未知操作");
			}
			// 如果有异常，设置异常信息（将异常对象转换为字符串）
			log.setIsException(throwable != null ? Global.YES : Global.NO);
			log.setExceptionInfo(ExceptionUtils.getStackTraceAsString(throwable));
			// 如果无地址并无异常日志，则不保存信息
			if (StringUtils.isBlank(log.getRequestUri()) && StringUtils.isBlank(log.getExceptionInfo())){
				return;
			}
			// 保存日志信息
			Static.logService.insertLog(log);
		}
	}
}
