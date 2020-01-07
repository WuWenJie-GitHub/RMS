package org.tangdao.common.beetl;

import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.spring.BeetlSpringView;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.tangdao.common.config.Global;
import org.tangdao.common.lang.DateUtils;

public class BeetlViewResolver extends BeetlSpringViewResolver {

	public BeetlViewResolver() {
		this.setViewClass(BeetlView.class);
	}

	protected Class<BeetlSpringView> requiredViewClass() {
		return BeetlSpringView.class;
	}

	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		BeetlSpringView beetlView = (BeetlSpringView) super.buildView(viewName);
		beetlView.setGroupTemplate(this.getConfig().getGroupTemplate());
		String suffix = this.getSuffix();
		if (suffix != null && suffix.length() != 0 && viewName.contains("#")) {
			String[] viewNames = viewName.split("#");
			if (viewNames.length > 2) {
				throw new Exception("视图名称有误：" + viewName);
			}
			beetlView.setUrl(this.getPrefix() + viewNames[0] + this.getSuffix() + "#" + viewNames[1]);
		}
		return beetlView;
	}

	@Override
	public void afterPropertiesSet() throws NoSuchBeanDefinitionException, NoUniqueBeanDefinitionException,
			SecurityException, NoSuchFieldException {
		super.afterPropertiesSet();

		String ctxPath = this.getServletContext().getContextPath();
		String serverStartDate = DateUtils.formatDate(DateUtils.getServerStartDate(), (String) "MMddHHmm");

		Map<String, Object> sharedVars = this.getConfig().getGroupTemplate().getSharedVars();
		sharedVars.put("ctxStatic", ctxPath + "/static");
		sharedVars.put("ctxPath", ctxPath);
		sharedVars.put("ctxAdmin", ctxPath + Global.getAdminPath());
		sharedVars.put("_version", "1.0.0-" + serverStartDate);

		GroupTemplate groupTemplate = this.getConfig().getGroupTemplate();
		groupTemplate.setSharedVars(sharedVars);
	}
}
