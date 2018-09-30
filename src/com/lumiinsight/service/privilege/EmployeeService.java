package com.lumiinsight.service.privilege;

import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.service.base.DAO;

public interface EmployeeService extends DAO<Employee>{
	/**
	 * measure username exists or not
	 * @param username
	 * @return
	 */
	public boolean exist(String username);
	/**
	 * measure username and password exit or not
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean validate(String username, String password);
}