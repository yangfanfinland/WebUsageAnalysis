package com.lumiinsight.web.action.privilege;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.aspectj.lang.annotation.Aspect;

import com.lumiinsight.bean.PageView;
import com.lumiinsight.bean.privilege.PrivilegeGroup;
import com.lumiinsight.service.privilege.PrivilegeGroupService;
import com.lumiinsight.web.modeldriven.privilege.PrivilegeGroupModelDriven;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * privilege paging list view
 * @author King
 *
 */
public class PrivilegeGroupListAction extends ActionSupport implements ModelDriven<PrivilegeGroupModelDriven>,
	ServletRequestAware{

	@Resource PrivilegeGroupService groupService;
	
	PrivilegeGroupModelDriven privilegeGroupModelDriven = new PrivilegeGroupModelDriven();
	public PrivilegeGroupModelDriven getModel() {
		return privilegeGroupModelDriven;
	}
	
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Permission(module="privilegegroup", privilege="view")
	public String execute(){
		
		PageView<PrivilegeGroup> pageView = new PageView<PrivilegeGroup>(12, privilegeGroupModelDriven.getPage());
		pageView.setQueryResult(groupService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult()));
		request.setAttribute("pageView", pageView);
		
		return "success";
	}
}
