package org.tangdao.common.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.tangdao.common.mapper.JsonMapper;

public class ToJson implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		Object para;
		return (para = paras[0])==null?"":JsonMapper.toJson(para);
	}

}
