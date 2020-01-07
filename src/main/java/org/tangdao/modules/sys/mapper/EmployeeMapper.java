package org.tangdao.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.tangdao.common.suports.Page;
import org.tangdao.modules.sys.model.domain.Employee;
import org.tangdao.modules.sys.model.vo.EmpPost;
import org.tangdao.modules.sys.model.vo.EmpUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 员工Mapper接口
 * 
 * @author ruyang
 * @version 2019-12-16
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

	public Page<EmpUser> findEmpUserPage(Page<Employee> page, EmpUser empUser);
	
	public EmpUser getEmpUserByUserCode(String userCode);
	
	public List<EmpPost> findEmpPost(String empCode);
	
	public void deleteEmpPost(String empCode);
	
	public int insertEmpPost(@Param("empCode") String empCode, @Param("postCodes") String[] postCodes);
	
}