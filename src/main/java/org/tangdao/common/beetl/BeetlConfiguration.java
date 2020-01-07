package org.tangdao.common.beetl;

import org.beetl.ext.spring.AccessExpressionIfFunction;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.tangdao.common.beetl.ext.fn.ToJson;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {
	
	private AccessExpressionIfFunction accessExpressionIfFunction;
	
	public BeetlConfiguration(AccessExpressionIfFunction accessExpressionIfFunction) {
		this.accessExpressionIfFunction = accessExpressionIfFunction;
	}
	
	@Override
	protected void initOther() {
		groupTemplate.registerFunction("expIf", accessExpressionIfFunction);
		groupTemplate.registerFunction("toJson", new ToJson());
	}
}
