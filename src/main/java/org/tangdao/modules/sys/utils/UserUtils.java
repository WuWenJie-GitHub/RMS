package org.tangdao.modules.sys.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.config.Global;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.utils.SpringUtils;
import org.tangdao.common.web.http.ServletUtils;
import org.tangdao.modules.sys.model.domain.Menu;
import org.tangdao.modules.sys.model.domain.Role;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.service.IMenuService;
import org.tangdao.modules.sys.service.IRoleService;
import org.tangdao.modules.sys.service.IUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class UserUtils {

	private static final class Static {
		private static IUserService userService = SpringUtils.getBean(IUserService.class);
		private static IRoleService roleService = SpringUtils.getBean(IRoleService.class);
		private static IMenuService menuService = SpringUtils.getBean(IMenuService.class);
	}
	
	/**
	 * 获取当前登录用户对象
	 * 
	 * @return
	 */
	public static User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal != null) {
			if (principal instanceof User) {
				return (User) principal;
			}
		}
		return null;
	}

	/**
	 * 获取当前登录用户的菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuTree() {
		List<Menu> targetList = new ArrayList<>();
		List<Menu> sourceList = getUser().getMenus().stream()
				.filter(menu -> Global.YES.equals(menu.getIsShow()) && Menu.TYPE_MENU.equals(menu.getMenuType()))
				.collect(Collectors.toList());
		Static.menuService.convertChildList(sourceList, targetList, Menu.ROOT_CODE);
		return targetList;
	}

	/**
	 * 获取菜单列表
	 * 
	 * @param parentCode 父节点编码
	 * @return
	 */
	public static List<Menu> getMenuByParentCode(String parentCode) {
		User user = null;
		List<Menu> menus = null;
		if (User.isSuperAdmin((user = getUser()).getUserCode())) {
			QueryWrapper<Menu> queryWrapper = new QueryWrapper<Menu>();
			queryWrapper.ne("status", Menu.STATUS_DELETE);
			queryWrapper.ge("weight", Menu.SUPER_ADMIN_GET_MENU_MIN_WEIGHT); // 管理员获取的最小权重菜单，（用于有些菜单管理员不可查看，如财务报表等）

			queryWrapper.orderByAsc("tree_sort");
			menus = Static.menuService.select(queryWrapper);
		} else {
			// 获取用户类型指定默认角色
			Menu menu = new Menu();
			String defaultRoleCodes = Global.getConfig("sys.user.defaultRoleCodes." + user.getUserType());
			if (!User.USER_TYPE_NONE.equals(user.getUserType()) && StringUtils.isNotBlank(defaultRoleCodes)) {
				menu.setDefaultRoleCodes(Arrays.asList(StringUtils.split(defaultRoleCodes, ",")));
			}
			// 管理员角色追加
			if (user.isAdmin()) {
				Collection<String> roleCodes = menu.getDefaultRoleCodes();
				if (ListUtils.isEmpty(roleCodes)) {
					roleCodes = new ArrayList<>();
					menu.setDefaultRoleCodes(roleCodes);
				}
				roleCodes.add(Role.DEFAULT_ADMIN_ROLE_CODE);
				menu.setDefaultRoleCodes(roleCodes);
			}
			menu.setUserCode(user.getUserCode());
			menu.setParentCode(parentCode);
			menus = Static.menuService.findByUserMenu(menu, false);
		}
		return menus;
	}

	/**
	 * 根据用户编码获取用户信息
	 * 
	 * @param userCode
	 * @return
	 */
	public static User getByUserCode(String userCode) {
		if (StringUtils.isBlank(userCode)) {
			return null;
		}
		Object obj;
		HttpServletRequest request = ServletUtils.getRequest();
		if (request != null && (obj = request.getAttribute("__userCode__" + userCode)) != null) {
			return (User) obj;
		} else {
			User user = null;
			user = Static.userService.get(userCode);
			if (user == null) {
				return null;
			}

			user.setRoles(Static.roleService.findByUserCode(userCode));

			if (request != null) {
				request.setAttribute("__userCode__" + user.getUserCode(), user);
			}

			return (User) user.clone();
		}
	}

	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param username
	 * @return
	 */
	public static User getByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		Object obj;
		HttpServletRequest request = ServletUtils.getRequest();
		if (request != null && (obj = request.getAttribute("__username__" + username)) != null) {
			return (User) obj;
		} else {
			User user = null;
			user = Static.userService.getUserByUsername(username);
			if (user == null) {
				return null;
			}

			user.setRoles(Static.roleService.findByUserCode(user.getUserCode()));

			if (request != null) {
				request.setAttribute("__username__" + username, user);
			}

			return (User) user.clone();
		}
	}

	/**
	 * 更新登录信息
	 * 
	 * @param user
	 */
	public static void updateUserLogininfo(User user) {
		Static.userService.updateLoginUserinfo(user);
	}

}
