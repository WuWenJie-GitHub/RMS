package org.tangdao.modules.gen.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tangdao.common.collect.MapUtils;
import org.tangdao.common.config.Global;
import org.tangdao.common.io.FileUtils;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.suports.BaseController;
import org.tangdao.modules.gen.model.domain.GenTable;
import org.tangdao.modules.gen.model.domain.GenTableColumn;
import org.tangdao.modules.gen.service.GenTableService;
import org.tangdao.modules.gen.utils.GenUtils;
import org.tangdao.modules.sys.model.domain.DictType;
import org.tangdao.modules.sys.service.IDictTypeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

@Controller
@RequestMapping(value = "${adminPath}/genTable")
public class GenTableController extends BaseController {
	
	@Autowired
	private IDictTypeService dictTypeService;
	
	@Autowired
	private GenTableService genTableService;
	
	@ModelAttribute
	public GenTable get(String tableName, boolean isNewRecord) {
		GenTable genTable;
		if((!isNewRecord)&&StringUtils.isNoneEmpty(tableName)&&(genTable =genTableService.get(tableName))!=null) {
			return genTable;
		}
		return new GenTable();
	}
	
	@RequestMapping(value = "list")
	public String list(GenTable genTable, Model model){
		return "modules/gen/tableList";
	}
	
	@RequestMapping({"listData"})
	public @ResponseBody IPage<GenTable> listData(GenTable genTable) {
		QueryWrapper<GenTable> queryWrapper = new QueryWrapper<GenTable>();
		return genTableService.page(genTable.getPage(), queryWrapper);
	}
	
	@RequestMapping(value = "form")
	public String form(GenTable genTable, String op, Model model) {
		boolean newRecord;
		// 验证表是否存在
		if ((newRecord=genTable.getIsNewRecord())&&StringUtils.isNotBlank(genTable.getTableName()) && !genTableService.checkTableName(genTable.getTableName())){
			model.addAttribute("message", "下一步失败！" + genTable.getTableName() + " 表已经添加！自动跳转到编辑");
			return "redirect:"+this.adminPath+"/genTable/form?table_name="+genTable.getTableName();
		}else{
			genTable = genTableService.getTableFormDb(genTable);
			if(newRecord) {
				genTable.setIsNewRecord(newRecord);
			}
			model.addAttribute("genTable", genTable);
			model.addAttribute("config",  GenUtils.getConfig());
//			QueryWrapper<DictType> queryWrapper = new QueryWrapper<DictType>();
//			List<DictType> dictTypeList = this.dictTypeService.select(queryWrapper);
//			model.addAttribute("dictTypeList",  dictTypeList);
			
			if (StringUtils.isBlank(genTable.getTableName())) {
				
				// 获取物理表列表
				List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
				model.addAttribute("tableList", tableList);
				
				return "modules/gen/tableFormSelect";
			}else {
				List<GenTable> tableList = this.genTableService.select();
				model.addAttribute("tableList", tableList);
				model.addAttribute("genBaseDirList", this.genTableService.findGenBaseDirList());	
				String defaultGenBaseDir = FileUtils.getProjectPath();	
			    model.addAttribute("defaultGenBaseDir", defaultGenBaseDir);	
				if (StringUtils.isBlank(genTable.getGenBaseDir())) {	
		            genTable.setGenBaseDir(defaultGenBaseDir);	
		        }	
		        if (StringUtils.isNotBlank(genTable.getTplCategory())) {	
		            genTable.setFlag("0");	
		        }	
		        if (StringUtils.isBlank(op)) {	
		            op = "step1";	
		        }
			}
			
		}
		model.addAttribute("op", op);
		return "modules/gen/tableForm";
	}
	
	@RequestMapping(value = "save")
	public @ResponseBody String save(@Validated GenTable genTable , Model model) {
		boolean ispk = false;
		boolean isnull = false;
		boolean isinsert = false;
		boolean isupdate = false;
		boolean islist = false;
		boolean vis = false;
		for (GenTableColumn gtc : genTable.getColumnList()) {	
            if ("1".equals(gtc.getIsPk())) {	
            	ispk = true;	
            }	
            if ("1".equals(gtc.getIsNull())) {	
            	isnull = true;	
            }	
            if ("1".equals(gtc.getIsInsert())) {	
            	isinsert = true;	
            }	
            if ("1".equals(gtc.getIsUpdate())) {	
            	isupdate = true;	
            }	
            if ("1".equals(gtc.getIsList())) {	
            	islist = true;	
            }	
            if (!"1".equals(gtc.getIsQuery())) continue;	
            vis = true;	
        }
		if (!ispk) {	
            return this.renderResult(Global.FALSE, "必须选择一列作为主键！");	
        }	
        if (!(isnull && isinsert && isupdate && islist && vis)) {	
            return this.renderResult(Global.FALSE, "复选框的列必须选择一项！");	
        }
        this.genTableService.saveGenTable(genTable);
        String message = new StringBuilder().insert(0, "保存表'").append(genTable.getTableName()).append("'成功").toString();
        if (StringUtils.inString(genTable.getFlag(), new String[]{"1", "2"})) {	
            String result = this.genTableService.generateCode(genTable);	
            String apply = "1".equals(genTable.getFlag()) ? "编译" : "生成";	
            message = "posfull:"+apply +"'"+genTable.getTableName()+"'成功: <br/>"+ result;
        }
        return this.renderResult(Global.TRUE, message);	
	}
	
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(GenTable genTable) {
		genTableService.delete(genTable);
		 return this.renderResult(Global.TRUE, "删除成功！");	
	};
	
	@RequestMapping(value = "dictTypeTreeData")
	public @ResponseBody List<Map<String, Object>> listData(DictType dictType,  @RequestParam(defaultValue = "1") String isShowCode, String excludeCode){
		List<DictType> sourceList =  dictTypeService.select();
		List<Map<String, Object>> targetList = new ArrayList<>();
		Map<String, Object> tempMap = null;
		for (DictType item : sourceList) {
			tempMap = MapUtils.newHashMap();
			tempMap.put("id", item.getId());
			tempMap.put("pId", "0");
			tempMap.put("name", StringUtils.getTreeNodeName(isShowCode, item.getDictType(), item.getDictName()));
			tempMap.put("value", item.getDictType());
			targetList.add(tempMap);
		}
		return targetList;
	}

}
