package org.tangdao.modules.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.tangdao.modules.demo.model.domain.TestOffice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用例机构Mapper接口
 * @author ruyang
 * @version 2020-01-06
 */
@Mapper
public interface TestOfficeMapper extends BaseMapper<TestOffice> {
	
}