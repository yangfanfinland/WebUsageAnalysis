package com.lumiinsight.web.action.analysis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.lumiinsight.bean.PageView;
import com.lumiinsight.bean.analysis.ChartDataServer;
import com.lumiinsight.bean.privilege.Branch;
import com.lumiinsight.service.analysis.ChartDataServerService;
import com.lumiinsight.web.action.privilege.Permission;
import com.lumiinsight.web.modeldriven.analysis.ChartDataServerModelDriven;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ChartDataServerListAction extends ActionSupport implements ModelDriven<ChartDataServerModelDriven>,
	ServletRequestAware{
	
	ChartDataServerModelDriven chartDataServerModelDriven = new ChartDataServerModelDriven();
	public ChartDataServerModelDriven getModel() {
		return chartDataServerModelDriven;
	}
	
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Resource
	ChartDataServerService chartDataServerService;

	@Permission(module="dataserver", privilege="view")
	public String execute() throws Exception {
		PageView<ChartDataServer> pageView = new PageView<ChartDataServer>(12, chartDataServerModelDriven.getPage());
		
		System.out.println(chartDataServerModelDriven.getPage());
		
		pageView.setQueryResult(chartDataServerService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult()));
		
		request.setAttribute("pageView", pageView);   
		
		return SUCCESS;
	}
}
