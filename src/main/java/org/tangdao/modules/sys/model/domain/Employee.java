package org.tangdao.modules.sys.model.domain;

import java.util.List;

import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.suports.DataEntity;
import org.tangdao.modules.sys.model.vo.EmpPost;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 员工Entity
 * @author ruyang
 * @version 2019-12-16
 */
@Getter
@Setter
@TableName("sys_employee")
public class Employee extends DataEntity<Employee> {
	
	private static final long serialVersionUID = 1L;
	
	@TableId
	private String empCode;		// 员工编码
	private String empName;		// 员工姓名
	private String empNameEn;		// 英文名
	private String officeCode;		// 机构编码
	private String officeName;		// 机构名称
	private String companyCode;		// 公司编码
	private String companyName;		// 公司名称
	
	@TableField(exist = false)
	private Company company;
	
	@TableField(exist = false)
	private Office office;
	
	@TableField(exist = false)
	private List<EmpPost> empPostList = ListUtils.newLinkedList();
	
	public Employee() {
		super();
	}

	public Employee(String empCode){
		super(empCode);
	}
	
	public String getPostCodes() {
//		List<String> list = ListUtils.extractToList(empPostList, "postCode");
//		return list.toArray(new String[list.size()]);
		if (ListUtils.isNotEmpty(empPostList)) {
			return ListUtils.extractToString(empPostList, "postCode", ",");
		}
		return null;
	}
	
	public void setPostCodes(String postCodes[]) {
		for (String var : postCodes) {
			if(StringUtils.isNotBlank(var)) {
				EmpPost e = new EmpPost();
				e.setEmpCode(empCode);
				e.setPostCode(var);
				empPostList.add(e);
			}
		}
	}
}