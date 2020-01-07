package org.tangdao.modules.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.tangdao.modules.demo.model.domain.TestUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用例用户Mapper接口
 * @author ruyang
 * @version 2020-01-06
 */
@Mapper
public interface TestUserMapper extends BaseMapper<TestUser> {
	
}