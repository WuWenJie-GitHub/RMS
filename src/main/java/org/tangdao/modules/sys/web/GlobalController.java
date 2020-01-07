package org.tangdao.modules.sys.web;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.config.Global;
import org.tangdao.common.exception.ServiceException;
import org.tangdao.common.lang.ExceptionUtils;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.suports.BaseController;
import org.tangdao.common.web.http.ServletUtils;

/**
 * @ClassName: GlobalController.java
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ruyang
 * @date 2019年3月1日 上午10:04:23
 * 
 */
@Controller
public class GlobalController extends BaseController {

	@RequestMapping({ "" })
	public String defaultPath() {
		return REDIRECT + Global.getProperty("web.defaultPath", "/");
	}

	@RequestMapping({ "error/{status}" })
	public String error(@PathVariable String status, HttpServletRequest request, HttpServletResponse response, Model model) {
		String message = "";
		if(request.getAttribute("message")!=null) {
			message = (String)request.getAttribute("message");
		}
		Throwable ex = ExceptionUtils.getThrowable(request);
		if(StringUtils.isBlank(message)) {
			if(ex!=null) {
				if("400".equals(status)) {
					if(StringUtils.startsWith(ex.getMessage(), "msg:")){
						message = StringUtils.replace(ex.getMessage(), "msg:", "");
					}else if (ex instanceof BindException){
						BindException be = (BindException) ex;
						for (ObjectError e : be.getGlobalErrors()) {
							message = e.getDefaultMessage() + "(" + e.getObjectName() + ")";
							break;
						}
						for (FieldError e : be.getFieldErrors()) {
							message = e.getDefaultMessage() + "(" + e.getField() + ")";
							break;
						}
					}else if (ex instanceof ConstraintViolationException){
						ConstraintViolationException cve = (ConstraintViolationException) ex;
						Iterator<ConstraintViolation<?>> iterator = cve.getConstraintViolations().iterator();
						while (iterator.hasNext()) {
							ConstraintViolation<?> e = iterator.next();
							message = e.getMessage() + "(" + e.getPropertyPath() + ")";
							break;
						}
					}else{
						message = message + ex.getMessage();
					}
					org.slf4j.LoggerFactory.getLogger("Status code [400]").info(ex.getMessage(), ex);
				} else if("401".equals(status)) {
					if(StringUtils.startsWith(ex.getMessage(), "msg:")){
						message = StringUtils.replace(ex.getMessage(), "msg:", "");
					}else{
						message = message + ex.getMessage();
					}
					org.slf4j.LoggerFactory.getLogger("Status code [401]").error(ex.getMessage());
				} else if(StringUtils.inString(status, "403","500")) {
					List<Throwable> throwables = ListUtils.newArrayList();
					throwables.add(ex);
					if(ex.getCause()!=null) {
						throwables.add(ex.getCause());
						if(ex.getCause().getCause()!=null) {
							throwables.add(ex.getCause().getCause());
							if(ex.getCause().getCause().getCause()!=null) {
								throwables.add(ex.getCause().getCause().getCause());
							}
						}
					}
					for (Throwable throwable : throwables) {
						if (StringUtils.startsWith(throwable.getMessage(), "msg:")){
							message = StringUtils.replace(throwable.getMessage(), "msg:", "");
							break;
						}else if (ex instanceof ServiceException){
							message = ((ServiceException) ex).getMessage();
							break;
						}
					}
					org.slf4j.LoggerFactory.getLogger("Status code ["+status+"]").error(ex.getMessage(), ex);
				}
			}
			
		}
		if("400".equals(status)) {
			if(StringUtils.isBlank(message)) {
				message = "请求参数错误，服务器无法解析！";
			}
			response.setStatus(400);
		}else if("401".equals(status)) {
			if(StringUtils.isBlank(message)) {
				message = "认证请求失败！";
			}
			response.setStatus(401);
		}else if("403".equals(status)) {
			if(StringUtils.isBlank(message)) {
				message = "您的操作权限不足！";
			}
			response.setStatus(403);
		}else if("404".equals(status)) {
			if(StringUtils.isBlank(message)) {
				message = "您访问的地址不存在！";
			}
			response.setStatus(404);
		}else if("405".equals(status)) {
			if(StringUtils.isBlank(message)) {
				message = "您请求的方法不允许！";
			}
			response.setStatus(405);
		}else {
			if(StringUtils.isBlank(message)) {
				message = "您访问的页面出错啦！";
			}
			response.setStatus(500);
		}
		
		if(ServletUtils.isAjaxRequest(request)) {
			return ServletUtils.renderResult(response, Global.FALSE, message);
		}
		
		model.addAttribute("ex", ex);
		model.addAttribute("message", message);
		model.addAttribute("status", status);
		if(StringUtils.inString(status, "405","401")) {
			return "error/other";
		}
		return "error/"+status;
	}
	
	@RequestMapping({ "/global.min.js" })
	@ResponseBody
	public String globalJs(String ctx, HttpServletRequest request, Model model) {
		StringBuilder bui = new StringBuilder();
		String ctxPath = request.getContextPath();
		bui.append("ctx='").append(StringUtils.defaultIfBlank(ctx, ctxPath)).append("',");
		bui.append("ctxPath='").append(ctxPath).append("',");
		bui.append("ctxAdmin='").append(ctxPath).append(Global.getAdminPath()+"',");
		bui.append("ctxStatic='").append(ctxPath).append("/static',");
		bui.append("Global=").append(Global.getConst("Global.Fields"));
		return bui.toString();
	}
}
