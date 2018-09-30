package com.lumiinsight.service.privilege.impl;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.privilege.Branch;
import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.service.privilege.BranchService;

@Service
public class BranchServiceBean extends DaoSupport<Branch> implements BranchService{
	
	@Override
	public void delete(Serializable... entityids) {
		for(Serializable id : entityids){
			Branch branch = this.find(id);
			for(Employee employee : branch.getEmployees()){
				employee.setBranch(null);
			}
			em.remove(branch);
		}
	}
	
	@Override
	public void save(Branch entity) {
		entity.setBranchid(UUID.randomUUID().toString());
		super.save(entity);
	}
}
