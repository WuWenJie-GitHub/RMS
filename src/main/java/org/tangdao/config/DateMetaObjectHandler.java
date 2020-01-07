/**
 *
 */
package org.tangdao.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.suports.DataEntity;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.utils.UserUtils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 *  統一字段填充
 * @author Ryan Ru(ruyangit@gmail.com)
 */
@Component
public class DateMetaObjectHandler implements MetaObjectHandler {

	private final static String STATUS = "status";
	private final static String UPDATE_BY = "updateBy";
	private final static String UPDATE_TIME = "updateTime";
	private final static String CREATE_BY = "createBy";
	private final static String CREATE_TIME = "createTime";

	@Override
	public void insertFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		Object status = getFieldValByName(STATUS, metaObject);
		Object updateBy = getFieldValByName(UPDATE_BY, metaObject);
		Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
		Object createBy = getFieldValByName(CREATE_BY, metaObject);
		Object createTime = getFieldValByName(CREATE_TIME, metaObject);

		if (StringUtils.isEmpty(String.valueOf(status))) {
			setFieldValByName(STATUS, DataEntity.STATUS_NORMAL, metaObject);
		}

		if (updateBy == null || createBy == null) {
			User user = UserUtils.getUser();
			if (user != null && user.getUserCode() != null) {
				if (createBy == null) {
					setFieldValByName(CREATE_BY, user.getUserCode(), metaObject);
				}
				if (updateBy == null) {
					setFieldValByName(UPDATE_BY, user.getUserCode(), metaObject);
				}
			}
		}

		if (createTime == null || updateTime == null) {
			Date date = new Date();
			if (createTime == null) {
				setFieldValByName(CREATE_TIME, date, metaObject);
			}
			if (updateTime == null) {
				setFieldValByName(UPDATE_TIME, date, metaObject);
			}
		}

	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		User user = UserUtils.getUser();
		if (user != null && user.getUserCode() != null) {
			setFieldValByName(UPDATE_BY, user.getUserCode(), metaObject);
		}
		setFieldValByName(UPDATE_TIME, new Date(), metaObject);
		
	}

}
