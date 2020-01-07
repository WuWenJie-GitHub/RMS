package org.tangdao.common.beetl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.tangdao.common.io.PropertiesUtils;
import org.tangdao.common.lang.ExceptionUtils;
import org.tangdao.common.lang.StringUtils;

public class BeetlUtils {

	private static GroupTemplate resourceGroupTemplate;	
	private static GroupTemplate stringGroupTemplate;	
	private static Configuration configuration;	
		
	public static synchronized Configuration getConfiguration() {	
		if (configuration == null) {	
	            try {	
	                PropertiesUtils propertiesUtils = new PropertiesUtils("classpath*:/beetl.properties");	
	                configuration = new Configuration(propertiesUtils.getProperties());	
	                Set<Map.Entry<Object,Object>> set = propertiesUtils.getProperties().entrySet();
	                Iterator<Entry<Object, Object>> iterator = set.iterator();
	                while(iterator.hasNext()) {
	                	Entry<Object, Object> entry = iterator.next();
	                	if (!StringUtils.startsWithIgnoreCase((String)entry.getKey(), Configuration.IMPORT_PACKAGE+"_" ) || !StringUtils.isNotBlank((String)entry.getValue())) continue;
	                	String[] arrstring = ((String)entry.getValue()).split(";");	
	                	for (String as : arrstring) {
	                		configuration.getPkgList().add(as);	
						}
	                }
	            } catch (IOException e) {	
	               e.printStackTrace();
	            }	
	        }	
	        return configuration;	
	    }	
		
	    public static synchronized GroupTemplate getResourceGroupTemplate() {	
	        if (resourceGroupTemplate == null) {	
	            try {	
	                BeetlUtil.getWebRoot();	
	            } catch (Exception a) {	
	                BeetlUtil.setWebroot(System.getProperty("user.dir"));	
	            }	
	            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
	            resourceGroupTemplate = new GroupTemplate(resourceLoader, BeetlUtils.getConfiguration());	
	        }	
	        return resourceGroupTemplate;	
	    }	
		
	    public static String renderFromResource(String tplResourcePath, Map<String, Object> data) {	
	        try {	
	            GroupTemplate groupTemplate = BeetlUtils.getResourceGroupTemplate();	
	            Template template = groupTemplate.getTemplate(tplResourcePath);	
	            template.binding(data);	
	            return template.render();	
	        }	
	        catch (Exception a) {	
	            Exception exception = a;	
	            exception.printStackTrace();	
	            throw ExceptionUtils.unchecked(exception);	
	        }	
	    }	
		
	    public static String renderFromString(String tplString, Map<String, Object> data) {	
	        try {	
	            GroupTemplate groupTemplate = BeetlUtils.getStringGroupTemplate();	
	            Template template = groupTemplate.getTemplate(tplString);	
	            template.binding(data);	
	            return template.render();	
	        }	
	        catch (Exception e) {	
	            throw ExceptionUtils.unchecked(e);	
	        }	
	    }	
		
	    public static synchronized GroupTemplate getStringGroupTemplate() {	
	        if (stringGroupTemplate == null) {	
	            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
	            stringGroupTemplate = new GroupTemplate(resourceLoader, BeetlUtils.getConfiguration());	
	        }	
	        return stringGroupTemplate;	
	    }	
}
