package com.lumiinsight.web.action.privilege;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.aspectj.lang.annotation.Aspect;

import com.lumiinsight.bean.PageView;
import com.lumiinsight.bean.privilege.Branch;
import com.lumiinsight.service.privilege.BranchService;
import com.lumiinsight.web.modeldriven.privilege.BranchModelDriven;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * branch company list view
 * @author yangfan
 *
 */
public class BranchListAction extends ActionSupport implements ModelDriven<BranchModelDriven>,
	ServletRequestAware{

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
	
	
	@Permission(module="branch", privilege="view")
	public String execute() throws Exception {
		
		PageView<Branch> pageView = new PageView<Branch>(12, branchModelDriven.getPage());
	
		pageView.setQueryResult(branchService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult()));
		
		request.setAttribute("pageView", pageView);           
		
		return "success";
	}
}
