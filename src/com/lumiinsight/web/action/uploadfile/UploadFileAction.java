package com.lumiinsight.web.action.uploadfile;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.lumiinsight.bean.PageView;
import com.lumiinsight.bean.uploadfile.UploadFile;
import com.lumiinsight.service.uploadfile.UploadFileService;
import com.lumiinsight.web.action.privilege.Permission;
import com.lumiinsight.web.modeldriven.uploadfile.UploadfileModelDriven;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UploadFileAction extends ActionSupport implements ModelDriven<UploadfileModelDriven>{
	UploadfileModelDriven uploadfileModelDriven = new UploadfileModelDriven();
	
	public UploadfileModelDriven getModel() {
		return uploadfileModelDriven;
	}
	
	@Resource
	private UploadFileService uploadFileService;
	
	@Permission(module="file", privilege="view")
	public String execute() throws Exception {
		
		
		PageView<UploadFile> pageView = new PageView<UploadFile>(12, uploadfileModelDriven.getPage());
		int firstindex = (uploadfileModelDriven.getPage()-1)*pageView.getMaxresult();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		pageView.setQueryResult(uploadFileService.getScrollData(firstindex, pageView.getMaxresult(), orderby));
		ActionContext actionContext = ActionContext.getContext();
		Map<String,Object> request = (Map) actionContext.get("request"); 
		request.put("pageView", pageView);
		return "success";
	}
	
}
