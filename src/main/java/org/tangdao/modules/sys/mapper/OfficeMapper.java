package org.tangdao.modules.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.tangdao.modules.sys.model.domain.Office;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 机构Mapper接口
 * @author ruyang
 * @version 2019-08-24
 */
@Mapper
public interface OfficeMapper extends BaseMapper<Office> {
	
}