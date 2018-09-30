package com.lumiinsight.web.action.privilege;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.aspectj.lang.annotation.Aspect;

import com.lumiinsight.bean.privilege.Branch;
import com.lumiinsight.service.privilege.BranchService;
import com.lumiinsight.utils.SiteUrl;
import com.lumiinsight.web.modeldriven.privilege.BranchModelDriven;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * branch company management
 * @author yangfan
 *
 *
 */

public class BranchManageAction extends ActionSupport implements ModelDriven<BranchModelDriven>, ServletRequestAware{
	BranchModelDriven branchModelDriven = new BranchModelDriven();
	public BranchModelDriven getModel() {
		return branchModelDriven;
	}
	
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Resource
	BranchService branchService;
	
	/**
	 * branch company adding interface
	 * @return
	 */
	@Permission(module="branch", privilege="insert")
	public String addUI(){
		return SUCCESS;
	}
	
	/**
	 * add branch company
	 * @return
	 */
	@Permission(module="branch", privilege="insert")
	public String add(){
		Branch branch = new Branch();
		branch.setName(branchModelDriven.getName());
		branchService.save(branch);
		
		request.setAttribute("message", "Branch company is added successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.branch.list"));
		return "message";
	}
	
	/**
	 * branch company modify interface
	 * @return
	 */
	@Permission(module="branch", privilege="update")
	public String editUI(){
		Branch department = branchService.find(branchModelDriven.getBranchid());
		branchModelDriven.setName(department.getName());
		return SUCCESS;
	}
	
	/**
	 * modify branch company
	 * @return
	 */
	@Permission(module="branch", privilege="update")
	public String edit(){
		Branch branch = branchService.find(branchModelDriven.getBranchid());
		branch.setName(branchModelDriven.getName());
		branchService.update(branch);
		
		request.setAttribute("message", "Branch company is modified successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.branch.list"));
		return "message";
	}
	@Permission(module="branch", privilege="delete")
	public String delete(){
		branchService.delete((Serializable)branchModelDriven.getBranchid());
		
		request.setAttribute("message", "Branch company is deleted successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.branch.list"));
		return "message";
	}
}
