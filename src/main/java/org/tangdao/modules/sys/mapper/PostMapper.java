package org.tangdao.modules.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.tangdao.modules.sys.model.domain.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 岗位Mapper接口
 * @author ruyang
 * @version 2019-08-23
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
	
}