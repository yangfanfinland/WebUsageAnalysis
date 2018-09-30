package com.lumiinsight.service.privilege.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.privilege.SystemPrivilege;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.service.privilege.SystemPrivilegeService;

@Service
public class SystemPrivilegeServiceBean extends DaoSupport<SystemPrivilege> implements SystemPrivilegeService{
	
	public void saves(List<SystemPrivilege> privileges){
		for(SystemPrivilege p : privileges){
			super.save(p);
		}
	}
}
