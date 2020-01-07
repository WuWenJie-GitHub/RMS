package org.tangdao.common.config;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.tangdao.common.collect.MapUtils;
import org.tangdao.common.io.FileUtils;
import org.tangdao.common.io.PropertiesUtils;
import org.tangdao.common.lang.ObjectUtils;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.mapper.JsonMapper;
import org.tangdao.common.suports.DataEntity;
import org.tangdao.common.web.http.ServletUtils;
import org.tangdao.modules.sys.model.domain.Config;
import org.tangdao.modules.sys.utils.ConfigUtils;

public class Global implements PropertySourceLoader {

	private static Map<String, String> props = MapUtils.newHashMap();
	private static final Global global = new Global();
	private static boolean isLoadPropertySource = false;

	static {
		isLoadPropertySource = false;
	}

	@Override
	public String[] getFileExtensions() {
		return new String[] { "properties", "yml" };
	}

	@Override
	public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
		if (!isLoadPropertySource) {
			isLoadPropertySource = true;
			Properties properties = PropertiesUtils.getInstance().getProperties();
			return Collections.singletonList(new OriginTrackedMapPropertySource("framework", properties));
		}
		return Collections.emptyList();
	}

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}

	public static final String YES = "1";
	public static final String NO = "0";
	public static final String SHOW = "1";
	public static final String HIDE = "0";
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	public static final String OP_ADD = "add";
	public static final String OP_EDIT = "edit";
	public static final String OP_AUTH = "auth";

	public static final String USERFILES_BASE_URL = "/userfiles/";
	
	/**
	 *  获取系统配置
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		String value = props.get(key);
		if (value == null) {
			value = PropertiesUtils.getInstance().getProperty(key);
			if(value != null) {
				props.put(key, value);
				return value;
			}
			value = StringUtils.EMPTY;
			props.put(key, value);
		}
		return value;
	}
	
	/**
	 *  获取系统配置
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getProperty(String key, String defValue) {
		String value = Global.getProperty(key);
		if (StringUtils.isEmpty(value)) {
			return defValue;
		}
		return value;
	}

	/**
	 *  获取系统配置
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		String value = props.get(key);
		if (value == null) {
			value = PropertiesUtils.getInstance().getProperty(key);
			if(value != null) {
				props.put(key, value);
			}else {
				Config config = ConfigUtils.getConfig(key);
				if(config!=null) {
					props.put(key, "read_sys_config_table");
					return config.getConfigValue();
				}
				props.put(key, StringUtils.EMPTY);
				return value;
			}
		}
		if("read_sys_config_table".equals(value)) {
			Config config = ConfigUtils.getConfig(key);
			if(config!=null) {
				value = config.getConfigValue();
			}
		}
		return value;
	}

	/**
	 *  获取系统配置
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getConfig(String key, String defValue) {
		String value = Global.getConfig(key);
		if (StringUtils.isEmpty(value)) {
			return defValue;
		}
		return value;
	}

	public static Boolean getgetPropertyToBoolean(String key, String defValue) {
		return ObjectUtils.toBoolean(getProperty(key, defValue));
	}

	public static Integer getgetPropertyToInteger(String key, String defValue) {
		return ObjectUtils.toInteger(getProperty(key, defValue));
	}
	
	public static Long getgetPropertyToLong(String key, String defValue) {
		return ObjectUtils.toLong(getProperty(key, defValue));
	}

	public static Boolean getConfigToBoolean(String key, String defValue) {
		return ObjectUtils.toBoolean(getConfig(key, defValue));
	}
	
	public static Integer getConfigToInteger(String key, String defValue) {
		return ObjectUtils.toInteger(getConfig(key, defValue));
	}
	
	public static Long getConfigToLong(String key, String defValue) {
		return ObjectUtils.toLong(getConfig(key, defValue));
	}
	
	public static Object getConst(String field) {
		try {
			if (!"Global.Fields".equals(field)) {	
				Object gloablField  = Global.class.getField(field).get(null);
				if(gloablField==null) {
					return DataEntity.class.getField(field).get(null);
				}
				return gloablField;
			}else {
				String data = props.get("__"+field);
				if(data == null) {
					Map<String, Object> result = MapUtils.newHashMap();
					Field[] globalFields = Global.class.getFields();
					for (Field f : globalFields) {
						result.put(f.getName(), f.get(null));
					}
					Field[] dataEntityFields = DataEntity.class.getFields();
					for (Field f : dataEntityFields) {
						result.put(f.getName(), f.get(null));
					}
					data = JsonMapper.toJson(result);
					props.put("__"+field, data);	
				}
				return data;
				
			}
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public static String getUserfilesBaseDir(String path) {
		String baseDir = getProperty("file.baseDir");
		if (StringUtils.isBlank(baseDir)) {
			baseDir = ServletUtils.getRequest().getSession().getServletContext().getRealPath("/");
		}

		if (StringUtils.isBlank(baseDir)) {
			baseDir = System.getProperty("user.dir");
		}

		if (!baseDir.endsWith("/")) {
			baseDir = baseDir + "/";
		}

		return path != null ? FileUtils.path(baseDir + USERFILES_BASE_URL + path) : FileUtils.path(baseDir);
	}
	
	public static String getAdminPath() {
		return getConfig("adminPath", StringUtils.EMPTY);
	}
	
	public static String getJdbcType() {
		return getConfig("jdbcType", "mysql");
	}
}
