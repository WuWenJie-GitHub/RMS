package org.tangdao.modules.demo.model.domain;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import org.tangdao.common.suports.TreeEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用例机构Entity
 * @author ruyang
 * @version 2020-01-06
 */
@TableName("demo_test_office")
public class TestOffice extends TreeEntity<TestOffice> {
	
	private static final long serialVersionUID = 1L;
	
	@TableId
	private String testOfficeCode;		// 机构编码
	private String testOfficeName;		// 机构名称
	
	public TestOffice() {
		super();
	}

	public TestOffice(String testOfficeCode){
		super(testOfficeCode);
	}
	
	@Override
	public TestOffice getParent() {
		return parent;
	}

	
	public String getTestOfficeCode() {
		return testOfficeCode;
	}

	public void setTestOfficeCode(String testOfficeCode) {
		this.testOfficeCode = testOfficeCode;
	}
	
	@NotBlank(message="机构名称不能为空")
	@Length(min=0, max=100, message="机构名称长度不能超过  100 个字符")
	public String getTestOfficeName() {
		return testOfficeName;
	}

	public void setTestOfficeName(String testOfficeName) {
		this.testOfficeName = testOfficeName;
	}
	
}