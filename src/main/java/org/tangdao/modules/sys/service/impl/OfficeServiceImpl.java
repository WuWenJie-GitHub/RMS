package org.tangdao.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import org.tangdao.common.service.impl.TreeServiceImpl;
import org.tangdao.modules.sys.service.IOfficeService;
import org.tangdao.modules.sys.model.domain.Office;
import org.tangdao.modules.sys.mapper.OfficeMapper;

/**
 * 机构ServiceImpl
 * @author ruyang
 * @version 2019-08-24
 */
@Service
public class OfficeServiceImpl extends TreeServiceImpl<OfficeMapper, Office> implements IOfficeService{
		
}