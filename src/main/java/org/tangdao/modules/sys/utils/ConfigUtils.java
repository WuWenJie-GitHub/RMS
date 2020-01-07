package org.tangdao.modules.sys.utils;

import org.tangdao.common.config.Global;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.utils.SpringUtils;
import org.tangdao.common.web.CookieUtils;
import org.tangdao.common.web.http.ServletUtils;
import org.tangdao.modules.sys.model.domain.Config;
import org.tangdao.modules.sys.service.IConfigService;

/**
 * @ClassName: ConfigUtils.java
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ruyang
 * @date 2019年3月5日 下午3:52:39
 * 
 */
public class ConfigUtils {
	
	private static final class Static {
		private static IConfigService configService = SpringUtils.getBean(IConfigService.class);
	}
	
	public static synchronized Config getConfig(String key) {
		return Static.configService.getConfigByKey(key);
	}
	
	public static synchronized String getConfig(String key, String defValue) {
		String value = Global.getConfig(key);
		if(StringUtils.isBlank(value)) {
			Config config = getConfig(key);
			if(config!=null) {
				return config.getConfigValue();
			}else {
				return defValue;
			}
		}
		return value;
	}
	
	/**
	 *  获取主题值
	 * @return
	 */
	public static String getSkinValue() {
		String skinValue = CookieUtils.getCookie(ServletUtils.getRequest(), "sys.index.skinName");
		if(StringUtils.isEmpty(skinValue)) {
			skinValue = getConfig("sys.index.skinName", "default");
		}
		return skinValue;
	}
}
