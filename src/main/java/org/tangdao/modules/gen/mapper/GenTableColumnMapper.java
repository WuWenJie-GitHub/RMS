package org.tangdao.modules.gen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tangdao.modules.gen.model.domain.GenTableColumn;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn>{

	public List<GenTableColumn> findList(GenTableColumn genTableColumn);
}
