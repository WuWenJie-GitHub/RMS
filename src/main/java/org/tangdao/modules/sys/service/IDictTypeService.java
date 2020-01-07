package org.tangdao.modules.sys.service;

import org.tangdao.common.service.ICrudService;
import org.tangdao.modules.sys.model.domain.DictType;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
public interface IDictTypeService extends ICrudService<DictType> {

	/**
	 *  删除字典类型（包括自定值）
	 * @param dictType
	 * @return
	 */
	public boolean delete(DictType dictType);
}
