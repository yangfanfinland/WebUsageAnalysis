package com.lumiinsight.web.action.analysis;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.annotations.Check;

import com.lumiinsight.bean.analysis.ChartDataServer;
import com.lumiinsight.service.analysis.ChartDataServerService;
import com.lumiinsight.utils.SiteUrl;
import com.lumiinsight.web.action.privilege.Permission;
import com.lumiinsight.web.modeldriven.analysis.ChartDataServerModelDriven;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class ChartDataServerManageAction extends ActionSupport implements ModelDriven<ChartDataServerModelDriven>, ServletRequestAware{
	
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
	
	@Permission(module="dataserver", privilege="insert")
	public String addUI(){
		return SUCCESS;
	}
	@Permission(module="dataserver", privilege="insert")
	public String add(){
		ChartDataServer chartDataServer = new ChartDataServer();
		chartDataServer.setServerName(chartDataServerModelDriven.getServerName());
		chartDataServer.setServerURLAddress(chartDataServerModelDriven.getServerURLAddress());
		chartDataServer.setServerCategory(chartDataServerModelDriven.getServerCategory());
		chartDataServerService.save(chartDataServer);
		
		request.setAttribute("message", "Data Server is added successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.analysis.list"));
		return "message";
	}
	
	@Permission(module="dataserver", privilege="update")
	public String editUI(){
		ChartDataServer chartDataServer = chartDataServerService.find(chartDataServerModelDriven.getServerID());
		chartDataServerModelDriven.setServerName(chartDataServer.getServerName());
		chartDataServerModelDriven.setServerURLAddress(chartDataServer.getServerURLAddress());
		chartDataServerModelDriven.setServerCategory(chartDataServer.getServerCategory());
		
		request.setAttribute("serverCategory", chartDataServer.getServerCategory());
		return SUCCESS;
	}
	
	@Permission(module="dataserver", privilege="update")
	public String edit(){
		ChartDataServer chartDataServer = chartDataServerService.find(chartDataServerModelDriven.getServerID());
		chartDataServer.setServerName(chartDataServerModelDriven.getServerName());
		chartDataServer.setServerURLAddress(chartDataServerModelDriven.getServerURLAddress());
		chartDataServer.setServerCategory(chartDataServerModelDriven.getServerCategory());
		chartDataServerService.update(chartDataServer);
		
		request.setAttribute("message", "Data Server is modified successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.analysis.list"));
		return "message";
	}
	
	@Permission(module="dataserver", privilege="delete")
	public String delete(){
		chartDataServerService.delete((Serializable)chartDataServerModelDriven.getServerID());
		
		request.setAttribute("message", "Data Server is deleted successfully!");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.analysis.list"));
		return "message";
	}
}
