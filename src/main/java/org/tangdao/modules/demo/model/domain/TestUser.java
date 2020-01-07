package org.tangdao.modules.demo.model.domain;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import org.tangdao.common.suports.DataEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用例用户Entity
 * @author ruyang
 * @version 2020-01-06
 */
@TableName("demo_test_user")
public class TestUser extends DataEntity<TestUser> {
	
	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;		// 编号
	private String username;		// 用户名
	
	public TestUser() {
		super();
	}

	public TestUser(String id){
		super(id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@NotBlank(message="用户名不能为空")
	@Length(min=0, max=50, message="用户名长度不能超过  50 个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}