package org.tangdao.modules.gen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.tangdao.modules.gen.model.domain.GenTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface GenTableMapper extends BaseMapper<GenTable> {

	@Select("SELECT a.gen_base_dir AS genBaseDir FROM gen_table a GROUP BY a.gen_base_dir ORDER BY max(a.update_time) DESC")
	public List<GenTable> findGenBaseDirList();
}
