package com.lumiinsight.service.privilege.impl;



import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.bean.privilege.PrivilegeGroup;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.service.privilege.PrivilegeGroupService;

@Service
public class PrivilegeGroupServiceBean extends DaoSupport<PrivilegeGroup> implements PrivilegeGroupService{
	@Override
	public void save(PrivilegeGroup entity) {
		// TODO Auto-generated method stub
		PrivilegeGroup group = entity;
		group.setGroupid(UUID.randomUUID().toString());
		super.save(entity);
	}
	
	@Override
	public void delete(Serializable... entityids) {
		for(Serializable id: entityids){
			PrivilegeGroup group = this.find(id);
			for(Employee employee : group.getEmployees()){
				employee.getGroups().remove(group);
			}
			em.remove(group);
		}
	}
}
