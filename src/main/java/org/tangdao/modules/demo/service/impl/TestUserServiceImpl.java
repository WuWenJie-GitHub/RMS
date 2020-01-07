package org.tangdao.modules.demo.service.impl;

import org.springframework.stereotype.Service;

import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.modules.demo.service.ITestUserService;
import org.tangdao.modules.demo.model.domain.TestUser;
import org.tangdao.modules.demo.mapper.TestUserMapper;

/**
 * 用例用户ServiceImpl
 * @author ruyang
 * @version 2020-01-06
 */
@Service
public class TestUserServiceImpl extends CrudServiceImpl<TestUserMapper, TestUser> implements ITestUserService{
		
}