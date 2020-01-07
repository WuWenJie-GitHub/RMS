package org.tangdao.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.modules.sys.mapper.DictTypeMapper;
import org.tangdao.modules.sys.model.domain.DictType;
import org.tangdao.modules.sys.service.IDictDataService;
import org.tangdao.modules.sys.service.IDictTypeService;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Service
public class DictTypeServiceImpl extends CrudServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {
	
	@Autowired
	private IDictDataService dictDataService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean delete(DictType dictType) {
		this.dictDataService.deleteByType(dictType.getDictType());
		return super.deleteById(dictType);
	}

}
