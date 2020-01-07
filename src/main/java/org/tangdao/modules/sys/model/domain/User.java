package org.tangdao.modules.sys.model.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.config.Global;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.suports.DataEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends DataEntity<User> implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户编码
	 */
	@TableId
	private String userCode;

	/**
	 * 登录账号
	 */
	private String username;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 电子邮箱
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 办公电话
	 */
	private String phone;

	/**
	 * 用户性别
	 */
	private String sex;

	/**
	 * 头像路径
	 */
	private String avatar;

	/**
	 * 个性签名
	 */
	private String sign;

	/**
	 * 用户类型
	 */
	private String userType;

	/**
	 * 管理员类型（0非管理员 1系统管理员）
	 */
	private String mgrType;

	/**
	 * 最后登陆IP
	 */
	private String lastLoginIp;

	/**
	 * 最后登陆时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginDate;

	/**
	 * 冻结时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date freezeDate;

	/**
	 * 冻结原因
	 */
	private String freezeCause;

	/**
	 * 用户权重（降序）
	 */
	private Integer userWeight;

	/**
	 * 角色列表
	 */
	@JsonIgnore
	@TableField(exist = false)
	private List<Role> roles = ListUtils.newArrayList();

	public String getRoleNames() {
		if (ListUtils.isNotEmpty(roles)) {
			return ListUtils.extractToString(roles, "roleName", ",");
		}
		return null;
	}
	
	public String getRoleCodes() {
		if (ListUtils.isNotEmpty(roles)) {
			return ListUtils.extractToString(roles, "roleCode", ",");
		}
		return null;
	}

	/**
	 * 菜单权限列表
	 */
	@JsonIgnore
	@TableField(exist = false)
	private List<Menu> menus = ListUtils.newArrayList();

	public static final String SUPER_ADMIN_CODE = Global.getProperty("user.superAdminCode", "system");
	
	public static final String USER_TYPE_NONE = "none";
	public static final String USER_TYPE_MEMBER = "member";
	public static final String USER_TYPE_EMPLOYEE = "employee";
	
	public static final String MGR_TYPE_NOT_ADMIN = "0";
	public static final String MGR_TYPE_DEFAULT_ADMIN = "1";
	
	public boolean isAdmin() {
		return MGR_TYPE_DEFAULT_ADMIN.equals(mgrType);
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin(this.userCode);
	}

	public static boolean isSuperAdmin(String id) {
		return (id != null && StringUtils.inString(id, SUPER_ADMIN_CODE.split(",")));
	}
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> collect = this.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRoleCode().toUpperCase())).collect(Collectors.toSet());

		menus.stream().filter(m -> StringUtils.isNotBlank(m.getPermission())).forEach(m -> {
			// 添加基于Permission的权限信息
			for (String permission : StringUtils.split(m.getPermission(), ",")) {
				collect.add(new SimpleGrantedAuthority(permission));
			}
		});
		collect.add(new SimpleGrantedAuthority("ROLE_USER"));
		return collect;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	public void setRoleCodeString(String roleCodeString) {
		if(StringUtils.isNotBlank(roleCodeString)) {
			String[] roleCodes = roleCodeString.split(",");
			for (String roleCode : roleCodes) {
				if(StringUtils.isNotEmpty(roleCode)) {
					Role role = new Role();
					role.setRoleCode(roleCode);
					role.setUserCode(this.userCode);
					roles.add(role);
				}
			}
		}
	}

}
