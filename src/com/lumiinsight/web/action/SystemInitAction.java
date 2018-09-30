package com.lumiinsight.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.lumiinsight.bean.privilege.SystemPrivilege;
import com.lumiinsight.service.privilege.SystemPrivilegeService;
import com.lumiinsight.utils.SiteUrl;
import com.opensymphony.xwork2.ActionSupport;

/**
 * System initialization
 * @author yangfan
 *
 */
public class SystemInitAction extends ActionSupport implements ServletRequestAware{
	@Resource SystemPrivilegeService privilegeService;
	
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public String execute() throws Exception {
		initPrivileges();
		request.setAttribute("message", "System initializes successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("employee.logon"));
		return SUCCESS;
	}

	private void initPrivileges() {
		//if(privilegeService.getCount() == 0){
			List<SystemPrivilege> privileges = new ArrayList<SystemPrivilege>();
			privileges.add(new SystemPrivilege("branch", "view", "View Branch"));
			privileges.add(new SystemPrivilege("branch", "insert", "Add Branch"));
			privileges.add(new SystemPrivilege("branch", "update", "Update Branch"));
			privileges.add(new SystemPrivilege("branch", "delete", "Delete Branch"));
			
			privileges.add(new SystemPrivilege("employee", "view", "View Employee"));
			privileges.add(new SystemPrivilege("employee", "insert", "Add Employee"));
			privileges.add(new SystemPrivilege("employee", "update", "Update Employee Info"));
			privileges.add(new SystemPrivilege("employee", "leave", "Employee Leave"));
			privileges.add(new SystemPrivilege("employee", "privilegeSet", "Set Employee Privilege"));
			
			privileges.add(new SystemPrivilege("privilegegroup", "view", "View Privilege Group"));
			privileges.add(new SystemPrivilege("privilegegroup", "insert", "Add Privilege Group"));
			privileges.add(new SystemPrivilege("privilegegroup", "update", "Update Privilege Group"));
			privileges.add(new SystemPrivilege("privilegegroup", "delete", "Delete Privilege Group"));
			
			privileges.add(new SystemPrivilege("analysisdata", "view", "View Data Chart"));
			privileges.add(new SystemPrivilege("dataserver", "view", "View Data Server"));
			privileges.add(new SystemPrivilege("dataserver", "insert", "Add Data Server"));
			privileges.add(new SystemPrivilege("dataserver", "update", "Update Data Server"));
			privileges.add(new SystemPrivilege("dataserver", "delete", "Delete Data Server"));
			
			privileges.add(new SystemPrivilege("file", "view", "View File"));
			privileges.add(new SystemPrivilege("file", "upload", "Upload File"));
			privileges.add(new SystemPrivilege("file", "delete", "Delete File"));
			privilegeService.saves(privileges);
		//}
	}
}
