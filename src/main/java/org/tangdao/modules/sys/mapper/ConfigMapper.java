package org.tangdao.modules.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.tangdao.modules.sys.model.domain.Config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 参数配置表 Mapper 接口
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

}
