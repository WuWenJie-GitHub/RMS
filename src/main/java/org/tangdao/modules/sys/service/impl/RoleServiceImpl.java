package org.tangdao.modules.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.common.suports.Page;
import org.tangdao.modules.sys.mapper.RoleMapper;
import org.tangdao.modules.sys.model.domain.Role;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.service.IRoleService;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Service
public class RoleServiceImpl extends CrudServiceImpl<RoleMapper, Role> implements IRoleService {

	public List<Role> findByUserCode(String userCode) {
		Role role = new Role();
		role.setUserCode(userCode);
		return baseMapper.findByUserCode(role);
	}

	/**
	 * 检查角色名称
	 * 
	 * @param oldRoleName
	 * @param roleName
	 * @return
	 */
	public boolean checkRoleNameExists(String oldRoleName, String roleName) {
		if (roleName != null && roleName.equals(oldRoleName)) {
			return true;
		} else if (roleName != null && this.count(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, roleName)) == 0) {
			return true;
		}
		return false;
	}

	public void insertRoleMenu(Role role, String[] menuCodes) {
		// 删除原有角色的所有权限
		this.baseMapper.deleteRoleMenu(role.getRoleCode());
		// 新增角色授权
		if (menuCodes != null&&menuCodes.length>0 && StringUtils.isNotBlank(role.getRoleCode())) {
			this.baseMapper.insertRoleMenu(role.getRoleCode(), menuCodes);
		}
	}
	
	@Override
	public Page<User> findUserPage(Page<Role> page, Role role) {
		return this.baseMapper.findUserPage(page, role);
	}

	@Override
	public int deleteRoleUser(String roleCode, String userCode) {
		// TODO Auto-generated method stub
		return this.baseMapper.deleteRoleUser(roleCode, userCode);
	}
	
	@Override
	public int insertRoleUser(String roleCode, String[] userCodes) {
		int count = 0;
		if (userCodes != null && roleCode!=null) {
			for (String userCode : userCodes) {
				this.baseMapper.deleteRoleUser(roleCode, userCode);
				count+=this.baseMapper.insertRoleUser(roleCode, userCode);
			}
		}
		return count;
	}
}
