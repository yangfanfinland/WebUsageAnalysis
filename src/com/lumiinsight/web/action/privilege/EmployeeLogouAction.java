package com.lumiinsight.web.action.privilege;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 员工退出登录
 * @author King
 *
 */
public class EmployeeLogouAction extends ActionSupport implements ServletRequestAware{
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String execute(){
		request.getSession().removeAttribute("employee");
		return "success";
	}
}
