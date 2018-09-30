package com.lumiinsight.service.uploadfile.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.uploadfile.UploadFile;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.service.uploadfile.UploadFileService;

@Service
@Transactional
public class UploadFileServiceBean extends DaoSupport<UploadFile> implements UploadFileService{
	public List<String> getFilepath(Integer[] ids){
		if(ids!=null&&ids.length>0){
			StringBuffer jpql = new StringBuffer();
			for(int i=0; i<ids.length;i++){
				jpql.append('?').append((i+1)).append(',');
			}
			jpql.deleteCharAt(jpql.length()-1);
			Query query = em.createQuery("select o.filepath from UploadFile o where o.id in("+jpql.toString()+")");
			for(int i=0; i<ids.length;i++){
				query.setParameter(i+1, ids[i]);
			}
			return query.getResultList();
		}
		return null;
	}
}
