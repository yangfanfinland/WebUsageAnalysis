package com.lumiinsight.web.action.uploadfile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.lumiinsight.bean.uploadfile.UploadFile;
import com.lumiinsight.service.uploadfile.UploadFileService;
import com.lumiinsight.utils.SiteUrl;
import com.lumiinsight.web.action.privilege.Permission;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadfileManageAction extends ActionSupport{
	@Resource
	private UploadFileService uploadFileService;
	
	/**
	 * upload interface
	 */
	@Permission(module="file", privilege="upload")
	public String uploadUI(){
		return "success";
	}
	
	/**
	 * 
	 * store file uploaded
	 */
	
	private File uploadfile;
	private String uploadfileFileName;//struts2 默认命名规范，必须是      "xxxxFileName"
	private Integer[] fileids;
	

	public File getUploadfile() {
		return uploadfile;
	}


	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}


	public String getUploadfileFileName() {
		return uploadfileFileName;
	}


	public void setUploadfileFileName(String uploadfileFileName) {
		this.uploadfileFileName = uploadfileFileName;
	}

	public Integer[] getFileids() {
		return fileids;
	}


	public void setFileids(Integer[] fileids) {
		this.fileids = fileids;
	}

	@Permission(module="file", privilege="upload")
	public String upload(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String,Object> request = (Map) actionContext.get("request"); 
		
		
		String pathdir ="/images/uploadfile";
		String realpathdir = ServletActionContext.getServletContext().getRealPath(pathdir);
		if(uploadfile!=null){
			File savedir = new File(realpathdir);
			if(!savedir.getParentFile().exists())
				savedir.mkdirs();
			String ext = uploadfileFileName.substring(uploadfileFileName.lastIndexOf("."));
			String filename = UUID.randomUUID().toString() + ext;
			String path = pathdir + "/" + filename;
			File savefile = new File(savedir,filename);
			try {
				FileUtils.copyFile(uploadfile, savefile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			UploadFile uploadFile = new UploadFile(path);
			
			//uploadFile.setFilepath(realpathdir+"/"+filename);
			uploadFile.setFilepath(path);
			
			uploadFile.setUploadtime(new Date());
			
			uploadFileService.save(uploadFile);
			request.put("message", "File Upload Success!");
			System.out.println(realpathdir);
		}else{
			request.put("message", "Please Upload File!");
		}
		
		request.put("urladdress", SiteUrl.readUrl("control.uploadfile.uploadpage"));
		return "message";
	}
	
	/**
	 * delete file uploaded
	 */
	@Permission(module="file", privilege="delete")
	public String delete(){
		List<String> files = uploadFileService.getFilepath(fileids);
		if(files!=null){
			for(String file : files){
				String realpath = ServletActionContext.getServletContext().getRealPath(file);
				File deletefile = new File(realpath);
				if(deletefile.exists())
					deletefile.delete();
			}
			uploadFileService.delete(UploadFile.class, fileids);
		}
		ActionContext actionContext = ActionContext.getContext();
		Map<String,Object> request = (Map) actionContext.get("request");
		request.put("message", "File Delete Success!");
		request.put("urladdress", SiteUrl.readUrl("control.uploadfile.list"));
		return "message";
	}
}
