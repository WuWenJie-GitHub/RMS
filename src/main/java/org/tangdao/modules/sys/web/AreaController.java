//package org.tangdao.modules.sys.web;
//
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import org.tangdao.common.config.Global;
//import org.tangdao.common.suports.BaseController;
//import org.tangdao.common.utils.ListUtils;
//import org.tangdao.common.utils.MapUtils;
//import org.tangdao.common.utils.StringUtils;
//
//import org.tangdao.modules.sys.model.domain.Area;
//import org.tangdao.modules.sys.service.IAreaService;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//
///**
// * 行政区划Controller
// * @author ruyang
// * @version 2019-09-27
// */
//@Controller
//@RequestMapping(value = "${adminPath}/sys/area")
//public class AreaController extends BaseController {
//
//	@Autowired
//	private IAreaService areaService;
//	
//	/**
//	 * 获取数据
//	 */
//	@ModelAttribute
//	public Area get(String areaCode, boolean isNewRecord) {
//		return areaService.get(areaCode, isNewRecord);
//	}
//	
//	/**
//	 * 查询列表
//	 */
//	@PreAuthorize("hasAuthority('sys:area:view')")
//	@RequestMapping(value = {"list", ""})
//	public String list(Area area, Model model) {
//		model.addAttribute("area", area);
//		return "modules/sys/areaList";
//	}
//	
//	/**
//	 * 查询列表数据
//	 */
//	@PreAuthorize("hasAuthority('sys:area:view')")
//	@RequestMapping(value = "listData")
//	public @ResponseBody List<Area> listData(Area area) {
//		QueryWrapper<Area> queryWrapper = new QueryWrapper<Area>();
//		return areaService.select(queryWrapper);
//	}
//
//	/**
//	 * 查看编辑表单
//	 */
//	@PreAuthorize("hasAuthority('sys:area:view')")
//	@RequestMapping(value = "form")
//	public String form(Area area, Model model) {
//		if (StringUtils.isNotBlank(area.getParentCode())) {
//			area.setParent(areaService.get(area.getParentCode()));
//		}
//		if (area.getParent() == null) {
//			area.setParent(new Area(Area.ROOT_CODE));
//		}
//		model.addAttribute("area", area);
//		return "modules/sys/areaForm";
//	}
//
//	/**
//	 * 保存行政区划
//	 */
//	@PreAuthorize("hasAuthority('sys:area:edit')")
//	@PostMapping(value = "save")
//	public @ResponseBody String save(@Validated Area area) {
//		areaService.saveOrUpdate(area);
//		return renderResult(Global.TRUE, "保存成功！");
//	}
//	
//	/**
//	 * 停用行政区划
//	 */
//	@PreAuthorize("hasAuthority('sys:area:edit')")
//	@RequestMapping(value = "disable")
//	public @ResponseBody String disable(Area area) {
//		QueryWrapper<Area> queryWrapper = new QueryWrapper<Area>();
//		queryWrapper.eq("status",Area.STATUS_NORMAL);
//		queryWrapper.like("area_code","," + area.getAreaCode() + ",");
//		long count = areaService.count(queryWrapper);
//		if (count > 0) {
//			return renderResult(Global.FALSE, "该行政区划包含未停用的子行政区划！");
//		}
//		area.setStatus(Area.STATUS_DISABLE);
//		areaService.updateById(area);
//		return renderResult(Global.TRUE, "停用成功");
//	}
//	
//	/**
//	 * 启用行政区划
//	 */
//	@PreAuthorize("hasAuthority('sys:area:edit')")
//	@RequestMapping(value = "enable")
//	public @ResponseBody String enable(Area area) {
//		area.setStatus(Area.STATUS_NORMAL);
//		areaService.updateById(area);
//		return renderResult(Global.TRUE, "启用成功");
//	}
//	
//	/**
//	 * 删除行政区划
//	 */
//	@PreAuthorize("hasAuthority('sys:area:edit')")
//	@RequestMapping(value = "delete")
//	public @ResponseBody String delete(Area area) {
//		areaService.deleteById(area);
//		return renderResult(Global.TRUE, "删除成功！");
//	}
//	
//	/**
//	 * 获取树结构数据
//	 * @param excludeCode 排除的Code
//	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
//	 * @return
//	 */
//	@RequestMapping(value = "treeData")
//	public @ResponseBody List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
//		QueryWrapper<Area> queryWrapper = new QueryWrapper<Area>();
//		if (StringUtils.isNotBlank(excludeCode)) {
//			queryWrapper.ne("area_code", excludeCode);
//			queryWrapper.notLike("parent_codes", excludeCode);
//		}
//		queryWrapper.ne("status", Area.STATUS_DELETE);
//		
//		queryWrapper.orderByAsc("tree_sort");
//		List<Area> sourceList = areaService.select(queryWrapper);
//		List<Map<String, Object>> targetList = ListUtils.newArrayList();
//		Map<String, Object> tempMap = null;
//		for (Area area : sourceList) {
//			// 过滤被排除的编码（包括所有子级）
//			if (StringUtils.isNotEmpty(excludeCode)){
//				if (area.getAreaCode().equals(excludeCode)){
//					continue;
//				}
//				if (area.getParentCodes().contains("," + excludeCode + ",")){
//					continue;
//				}
//			}
//		
//			tempMap = MapUtils.newHashMap();
//			tempMap.put("id", area.getAreaCode());
//			tempMap.put("pId", area.getParentCode());
//			tempMap.put("name", StringUtils.getTreeNodeName(isShowCode, company.getCompanyCode(), company.getCompanyName()));
//			targetList.add(tempMap);
//		}
//		return targetList;
//	}
//	
//}