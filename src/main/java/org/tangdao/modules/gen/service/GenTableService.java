package org.tangdao.modules.gen.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tangdao.common.config.Global;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.modules.gen.mapper.GenDataDictMapper;
import org.tangdao.modules.gen.mapper.GenTableColumnMapper;
import org.tangdao.modules.gen.mapper.GenTableMapper;
import org.tangdao.modules.gen.model.domain.GenTable;
import org.tangdao.modules.gen.model.domain.GenTableColumn;
import org.tangdao.modules.gen.model.vo.GenConfig;
import org.tangdao.modules.gen.model.vo.GenTemplate;
import org.tangdao.modules.gen.utils.GenUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class GenTableService extends CrudServiceImpl<GenTableMapper, GenTable>{

	@Autowired
	private GenTableMapper genTableMapper;
	@Autowired
	private GenTableColumnMapper genTableColumnMapper;
	@Autowired
	private GenDataDictMapper genDataDictMapper;
	
	public GenTable get(String tableName) {
		GenTable genTable = genTableMapper.selectById(tableName);
		if(genTable!=null) {
			GenTableColumn genTableColumn = new GenTableColumn();
			genTableColumn.setGenTable(genTable);
			genTable.setColumnList(genTableColumnMapper.findList(genTableColumn));
		}
		return genTable;
	}
	
	/**
	 * 获取物理数据表列表
	 * @param genTable
	 * @return
	 */
	public List<GenTable> findTableListFormDb(GenTable genTable){
		return genDataDictMapper.findTableList(genTable);
	}
	
	/**
	 * 验证表名是否可用，如果已存在，则返回false
	 * @param genTable
	 * @return
	 */
	public boolean checkTableName(String tableName){
		if (StringUtils.isBlank(tableName)){
			return true;
		}
		QueryWrapper<GenTable> queryWrapper = new QueryWrapper<GenTable>();
		queryWrapper.eq("table_name", tableName);
		return genTableMapper.selectCount(queryWrapper)==0;
	}
	
	public List<GenTable> findGenBaseDirList(){
		return genTableMapper.findGenBaseDirList();
	}
	
	/**
	 * 获取物理数据表列表
	 * @param genTable
	 * @return
	 */
	public GenTable getTableFormDb(GenTable genTable){
		// 如果有表名，则获取物理表
		if (StringUtils.isNotBlank(genTable.getTableName())){
			
			List<GenTable> list = genDataDictMapper.findTableList(genTable);
			if (list.size() > 0){
				// 如果是新增，初始化表属性
				if (genTable.getIsNewRecord()){
					genTable = list.get(0);
					// 设置字段说明
					if (StringUtils.isBlank(genTable.getComments())){
						genTable.setComments(genTable.getTableName());
					}
					genTable.setClassName(StringUtils.capCamelCase(genTable.getTableName()));
					genTable.setFunctionName(genTable.getComments());
					genTable.setFunctionNameSimple(genTable.getComments());
					
					if(StringUtils.isBlank(genTable.getPackageName())) {
						genTable.setPackageName(Global.getConfig("gen.defaultPackageName"));
					}
					if(StringUtils.isBlank(genTable.getPackageName())) {
						genTable.setPackageName("org.tangdao.modules");
					}
					if(StringUtils.isBlank(genTable.getModuleName())) {
						genTable.setModuleName(StringUtils.substringBefore(genTable.getTableName(), "_"));	
					}
				}
				
				// 添加新列
				List<GenTableColumn> columnList = genDataDictMapper.findTableColumnList(genTable);
				for (GenTableColumn column : columnList){
					boolean b = false;
					for (GenTableColumn e : genTable.getColumnList()){
						if (e.getColumnName().equals(column.getColumnName())){
							b = true;
						}
					}
					if (!b){
						genTable.getColumnList().add(column);
					}
				}
				
				// 删除已删除的列
				for (GenTableColumn e : genTable.getColumnList()){
					boolean b = false;
					for (GenTableColumn column : columnList){
						if (column.getColumnName().equals(e.getColumnName())){
							b = true;
						}
					}
					if (!b){
						e.setStatus(GenTableColumn.STATUS_DELETE);
					}
				}
				
				// 获取主键
				genTable.setPkList(genDataDictMapper.findTablePK(genTable));
				
				// 初始化列属性字段
				GenUtils.initColumnField(genTable);
				
			}
		}
		return genTable;
	}
	
	@Transactional(readOnly = false)
	public void saveGenTable(GenTable genTable) {
		super.saveOrUpdate(genTable);
		// 保存列
		for (GenTableColumn column : genTable.getColumnList()){
			column.setTableName(genTable.getTableName());
			column.setGenTable(genTable);
			if (StringUtils.isBlank(column.getId())){
				genTableColumnMapper.insert(column);
				continue;
			}
			if ("1".equals(column.getStatus())) {	
				genTableColumnMapper.deleteById(column);
			} else{
				genTableColumnMapper.updateById(column);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(GenTable genTable) {
		genTableMapper.deleteById(genTable);
		QueryWrapper<GenTableColumn> wrapper = new QueryWrapper<GenTableColumn>();
		wrapper.eq("table_name", genTable.getTableName());
		genTableColumnMapper.delete(wrapper);
	}
	
	public String generateCode(GenTable genTable){

		StringBuilder result = new StringBuilder();
		
		// 查询主表及字段列
		genTable.setColumnList(genTableColumnMapper.findList(new GenTableColumn(genTable)));
		
		// 获取所有代码模板
		GenConfig config = GenUtils.getConfig();
		
		// 获取模板列表
		List<GenTemplate> templateList = GenUtils.getTemplateList(config, genTable.getTplCategory(), false);
		List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(config, genTable.getTplCategory(), true);
		
		// 如果有子表模板，则需要获取子表列表
		if (childTableTemplateList.size() > 0){
			QueryWrapper<GenTable> queryWrapper = new QueryWrapper<GenTable>();
			queryWrapper.eq("parent_table", genTable.getTableName());
			genTable.setChildList(genTableMapper.selectList(queryWrapper));
		}
//		String projectPath = GenUtils.getProjectPath(null);
		// 生成子表模板代码
//		for (GenTable childTable : genTable.getChildList()){
//			childTable.setParent(genTable);
//			childTable.setColumnList(genTableColumnMapper.findList(new GenTableColumn(childTable)));
//			Map<String, Object> childTableModel = GenUtils.getDataModel(childTable);
//			for (GenTemplate tpl : childTableTemplateList){
//				result.append(GenUtils.generateToFile(projectPath, tpl, childTableModel, genTable.getReplaceFile()));
//			}
//		}
//		System.out.println(projectPath);
		// 生成主表模板代码
		Map<String, Object> model = GenUtils.getDataModel(genTable);
		for (GenTemplate tpl : templateList){
			result.append(GenUtils.generateToFile(tpl, model, genTable));
		}
		return result.toString();
	}


}
