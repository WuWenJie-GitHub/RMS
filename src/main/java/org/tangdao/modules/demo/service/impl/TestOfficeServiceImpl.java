package org.tangdao.modules.demo.service.impl;

import org.springframework.stereotype.Service;

import org.tangdao.common.service.impl.TreeServiceImpl;
import org.tangdao.modules.demo.service.ITestOfficeService;
import org.tangdao.modules.demo.model.domain.TestOffice;
import org.tangdao.modules.demo.mapper.TestOfficeMapper;

/**
 * 用例机构ServiceImpl
 * @author ruyang
 * @version 2020-01-06
 */
@Service
public class TestOfficeServiceImpl extends TreeServiceImpl<TestOfficeMapper, TestOffice> implements ITestOfficeService{
		
}