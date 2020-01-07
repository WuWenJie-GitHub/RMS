package org.tangdao.modules.demo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.tangdao.modules.demo.model.domain.TestUser;
import org.tangdao.modules.demo.service.ITestUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用例用户Controller
 * @author ruyang
 * @version 2020-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/demo/testUser")
public class TestUserController extends BaseController {

	@Autowired
	private ITestUserService testUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TestUser get(String id, boolean isNewRecord) {
		return testUserService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@PreAuthorize("hasAuthority('demo:testUser:view')")
	@RequestMapping(value = {"list", ""})
	public String list(TestUser testUser, Model model) {
		model.addAttribute("testUser", testUser);
		return "modules/demo/testUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@PreAuthorize("hasAuthority('demo:testUser:view')")
	@RequestMapping(value = "listData")
	public @ResponseBody IPage<TestUser> listData(TestUser testUser, HttpServletRequest request, HttpServletResponse response) {
		QueryWrapper<TestUser> queryWrapper = new QueryWrapper<TestUser>();
		return testUserService.page(testUser.getPage(), queryWrapper);
	}

	/**
	 * 查看编辑表单
	 */
	@PreAuthorize("hasAuthority('demo:testUser:view')")
	@RequestMapping(value = "form")
	public String form(TestUser testUser, Model model) {
		model.addAttribute("testUser", testUser);
		return "modules/demo/testUserForm";
	}

	/**
	 * 保存用例用户
	 */
	@PreAuthorize("hasAuthority('demo:testUser:edit')")
	@PostMapping(value = "save")
	public @ResponseBody String save(@Validated TestUser testUser) {
		testUserService.saveOrUpdate(testUser);
		return renderResult(Global.TRUE, "保存成功！");
	}
	
	/**
	 * 停用用例用户
	 */
	@PreAuthorize("hasAuthority('demo:testUser:edit')")
	@RequestMapping(value = "disable")
	public @ResponseBody String disable(TestUser testUser) {
		testUser.setStatus(TestUser.STATUS_DISABLE);
		testUserService.updateById(testUser);
		return renderResult(Global.TRUE, "停用成功");
	}
	
	/**
	 * 启用用例用户
	 */
	@PreAuthorize("hasAuthority('demo:testUser:edit')")
	@RequestMapping(value = "enable")
	public @ResponseBody String enable(TestUser testUser) {
		testUser.setStatus(TestUser.STATUS_NORMAL);
		testUserService.updateById(testUser);
		return renderResult(Global.TRUE, "启用成功");
	}
	
	/**
	 * 删除用例用户
	 */
	@PreAuthorize("hasAuthority('demo:testUser:edit')")
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(TestUser testUser) {
		testUserService.deleteById(testUser);
		return renderResult(Global.TRUE, "删除成功！");
	}
	
}