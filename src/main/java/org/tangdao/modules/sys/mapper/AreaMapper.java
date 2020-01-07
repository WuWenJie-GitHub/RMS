package org.tangdao.modules.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.tangdao.modules.sys.model.domain.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 行政区划Mapper接口
 * @author ruyang
 * @version 2019-09-27
 */
@Mapper
public interface AreaMapper extends BaseMapper<Area> {
	
}