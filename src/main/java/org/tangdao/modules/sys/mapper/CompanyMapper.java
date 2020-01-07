package org.tangdao.modules.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.tangdao.modules.sys.model.domain.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 公司Mapper接口
 * @author ruyang
 * @version 2019-08-28
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
	
}