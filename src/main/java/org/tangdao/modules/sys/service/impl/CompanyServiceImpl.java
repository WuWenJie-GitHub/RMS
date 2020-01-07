package org.tangdao.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import org.tangdao.common.service.impl.TreeServiceImpl;
import org.tangdao.modules.sys.service.ICompanyService;
import org.tangdao.modules.sys.model.domain.Company;
import org.tangdao.modules.sys.mapper.CompanyMapper;

/**
 * 公司ServiceImpl
 * @author ruyang
 * @version 2019-08-28
 */
@Service
public class CompanyServiceImpl extends TreeServiceImpl<CompanyMapper, Company> implements ICompanyService{
	
}