package org.tangdao.modules.sys.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.suports.BaseController;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.service.IUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Controller
@RequestMapping("${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	@RequestMapping("/listData")
	public @ResponseBody IPage<User> listData(User user, HttpServletRequest request) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		if(StringUtils.isNotBlank(user.getStatus())) {
			queryWrapper.eq("status", user.getStatus());
		}
		if(StringUtils.isNotBlank(user.getUserType())) {
			queryWrapper.eq("user_type", user.getUserType());
		}
		if(StringUtils.isNotBlank(user.getUsername())||StringUtils.isNotBlank(user.getNickname())) {
			queryWrapper.like("username", user.getUsername()).or().like("nickname", user.getNickname());
		}
		return this.userService.page(user.getPage(), queryWrapper);
	}

}
