package org.tangdao.modules.demo.web;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.tangdao.common.config.Global;
import org.tangdao.common.suports.BaseController;
import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.collect.MapUtils;
import org.tangdao.common.lang.StringUtils;

import org.tangdao.modules.demo.model.domain.TestOffice;
import org.tangdao.modules.demo.service.ITestOfficeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 用例机构Controller
 * @author ruyang
 * @version 2020-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/demo/testOffice")
public class TestOfficeController extends BaseController {

	@Autowired
	private ITestOfficeService testOfficeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TestOffice get(String testOfficeCode, boolean isNewRecord) {
		return testOfficeService.get(testOfficeCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@PreAuthorize("hasAuthority('demo:testOffice:view')")
	@RequestMapping(value = {"list", ""})
	public String list(TestOffice testOffice, Model model) {
		model.addAttribute("testOffice", testOffice);
		return "modules/demo/testOfficeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@PreAuthorize("hasAuthority('demo:testOffice:view')")
	@RequestMapping(value = "listData")
	public @ResponseBody List<TestOffice> listData(TestOffice testOffice) {
		QueryWrapper<TestOffice> queryWrapper = new QueryWrapper<TestOffice>();
		return testOfficeService.select(queryWrapper);
	}

	/**
	 * 查看编辑表单
	 */
	@PreAuthorize("hasAuthority('demo:testOffice:view')")
	@RequestMapping(value = "form")
	public String form(TestOffice testOffice, Model model) {
		if (StringUtils.isNotBlank(testOffice.getParentCode())) {
			testOffice.setParent(testOfficeService.get(testOffice.getParentCode()));
		}
		if (testOffice.getParent() == null) {
			testOffice.setParent(new TestOffice(TestOffice.ROOT_CODE));
		}
		model.addAttribute("testOffice", testOffice);
		return "modules/demo/testOfficeForm";
	}

	/**
	 * 保存用例机构
	 */
	@PreAuthorize("hasAuthority('demo:testOffice:edit')")
	@PostMapping(value = "save")
	public @ResponseBody String save(@Validated TestOffice testOffice) {
		testOfficeService.saveOrUpdate(testOffice);
		return renderResult(Global.TRUE, "保存成功！");
	}
	
	/**
	 * 停用用例机构
	 */
	@PreAuthorize("hasAuthority('demo:testOffice:edit')")
	@RequestMapping(value = "disable")
	public @ResponseBody String disable(TestOffice testOffice) {
		QueryWrapper<TestOffice> queryWrapper = new QueryWrapper<TestOffice>();
		queryWrapper.eq("status",TestOffice.STATUS_NORMAL);
		queryWrapper.like("test_office_code","," + testOffice.getTestOfficeCode() + ",");
		long count = testOfficeService.count(queryWrapper);
		if (count > 0) {
			return renderResult(Global.FALSE, "该用例机构包含未停用的子用例机构！");
		}
		testOffice.setStatus(TestOffice.STATUS_DISABLE);
		testOfficeService.updateById(testOffice);
		return renderResult(Global.TRUE, "停用成功");
	}
	
	/**
	 * 启用用例机构
	 */
	@PreAuthorize("hasAuthority('demo:testOffice:edit')")
	@RequestMapping(value = "enable")
	public @ResponseBody String enable(TestOffice testOffice) {
		testOffice.setStatus(TestOffice.STATUS_NORMAL);
		testOfficeService.updateById(testOffice);
		return renderResult(Global.TRUE, "启用成功");
	}
	
	/**
	 * 删除用例机构
	 */
	@PreAuthorize("hasAuthority('demo:testOffice:edit')")
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(TestOffice testOffice) {
		testOfficeService.deleteById(testOffice);
		return renderResult(Global.TRUE, "删除成功！");
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequestMapping(value = "treeData")
	public @ResponseBody List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		QueryWrapper<TestOffice> queryWrapper = new QueryWrapper<TestOffice>();
		if (StringUtils.isNotBlank(excludeCode)) {
			queryWrapper.ne("test_office_code", excludeCode);
			queryWrapper.notLike("parent_codes", excludeCode);
		}
		queryWrapper.ne("status", TestOffice.STATUS_DELETE);
		
		queryWrapper.orderByAsc("tree_sort");
		List<TestOffice> sourceList = testOfficeService.select(queryWrapper);
		List<Map<String, Object>> targetList = ListUtils.newArrayList();
		Map<String, Object> tempMap = null;
		for (TestOffice testOffice : sourceList) {
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotEmpty(excludeCode)){
				if (testOffice.getTestOfficeCode().equals(excludeCode)){
					continue;
				}
				if (testOffice.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
		
			tempMap = MapUtils.newHashMap();
			tempMap.put("id", testOffice.getTestOfficeCode());
			tempMap.put("pId", testOffice.getParentCode());
			tempMap.put("name", testOffice.getTestOfficeName());
			targetList.add(tempMap);
		}
		return targetList;
	}
	
}