package org.tangdao.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import org.tangdao.common.service.impl.TreeServiceImpl;
import org.tangdao.modules.sys.service.IAreaService;
import org.tangdao.modules.sys.model.domain.Area;
import org.tangdao.modules.sys.mapper.AreaMapper;

/**
 * 行政区划ServiceImpl
 * @author ruyang
 * @version 2019-09-27
 */
@Service
public class AreaServiceImpl extends TreeServiceImpl<AreaMapper, Area> implements IAreaService{
		
}