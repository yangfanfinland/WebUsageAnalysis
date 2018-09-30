package com.lumiinsight.web.action.privilege;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.aspectj.lang.annotation.Aspect;

import com.lumiinsight.bean.privilege.PrivilegeGroup;
import com.lumiinsight.bean.privilege.SystemPrivilege;
import com.lumiinsight.bean.privilege.SystemPrivilegePK;
import com.lumiinsight.service.privilege.PrivilegeGroupService;
import com.lumiinsight.service.privilege.SystemPrivilegeService;
import com.lumiinsight.web.modeldriven.privilege.PrivilegeGroupModelDriven;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class PrivilegeGroupManageAction extends ActionSupport implements ModelDriven<PrivilegeGroupModelDriven>,
	ServletRequestAware{
	@Resource SystemPrivilegeService privilegeService;
	@Resource PrivilegeGroupService groupService;
	
	PrivilegeGroupModelDriven privilegeGroupModelDriven = new PrivilegeGroupModelDriven();
	public PrivilegeGroupModelDriven getModel() {
		return privilegeGroupModelDriven;
	}
	
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * 权限组添加界面
	 * @return
	 */
	@Permission(module="privilegegroup", privilege="insert")
	public String addUI(){
		request.setAttribute("privileges", privilegeService.getScrollData().getResultlist());
		return "success";
	}
	/**
	 * 添加权限组
	 * @return
	 */
	@Permission(module="privilegegroup", privilege="insert")
	public String add(){
		PrivilegeGroup group = new PrivilegeGroup();
		group.setName(privilegeGroupModelDriven.getName());
		
		if(privilegeGroupModelDriven.getPrivileges()!= null){
			for(SystemPrivilegePK id : privilegeGroupModelDriven.getPrivileges())
				group.addSystemPrivilege(new SystemPrivilege(id));
			
			groupService.save(group);
		}
		
		request.setAttribute("message", "Add privilege group successfully!");
		request.setAttribute("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
	/**
	 * 权限组修改界面
	 * @return
	 */
	@Permission(module="privilegegroup", privilege="update")
	public String editUI(){
		PrivilegeGroup group = groupService.find(privilegeGroupModelDriven.getGroupid());
		privilegeGroupModelDriven.setName(group.getName());
		request.setAttribute("selectprivileges", group.getPrivileges());
		request.setAttribute("privileges", privilegeService.getScrollData().getResultlist());
		return "success";
	}
	/**
	 * 修改权限组
	 * @return
	 */
	@Permission(module="privilegegroup", privilege="update")
	public String edit(){
		PrivilegeGroup group = groupService.find(privilegeGroupModelDriven.getGroupid());
		group.setName(privilegeGroupModelDriven.getName());
		group.getPrivileges().clear();
		if(privilegeGroupModelDriven.getPrivileges()!= null){
			for(SystemPrivilegePK id : privilegeGroupModelDriven.getPrivileges())
			group.addSystemPrivilege(new SystemPrivilege(id));
			groupService.update(group);
		}
		request.setAttribute("message", "Modify privilege group successfully!");
		request.setAttribute("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
	/**
	 * 删除权限组
	 * @return
	 */
	@Permission(module="privilegegroup", privilege="delete")
	public String delete(){
		groupService.delete((Serializable)privilegeGroupModelDriven.getGroupid());
		
		request.setAttribute("message", "Delete privilege group successfully!");
		request.setAttribute("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
}
