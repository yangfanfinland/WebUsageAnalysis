package com.lumiinsight.service.uploadfile;

import java.util.List;

import com.lumiinsight.bean.uploadfile.UploadFile;
import com.lumiinsight.service.base.DAO;

public interface UploadFileService extends DAO<UploadFile>{
	/**
	 * get file upload path
	 * @param ids
	 * @return
	 */
	public List<String> getFilepath(Integer[] ids);
}
