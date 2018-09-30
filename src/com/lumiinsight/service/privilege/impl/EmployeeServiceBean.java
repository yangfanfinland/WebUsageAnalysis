package com.lumiinsight.service.privilege.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.service.privilege.EmployeeService;

@Service
@Transactional
public class EmployeeServiceBean extends DaoSupport<Employee> implements EmployeeService {
	public boolean validate(String username, String password){
		long count = (Long)em.createQuery("select count(o) from Employee o where o.username=?1 and o.password=?2 and o.visible=?3")
				.setParameter(1, username).setParameter(2, password).setParameter(3, true).getSingleResult();
		return count>0;
	}
	
	public boolean exist(String username){
		long count = (Long)em.createQuery("select count(o) from Employee o where o.username=?1")
		.setParameter(1, username).getSingleResult();
		return count>0;
	}
	@Override
	public void delete(Serializable... entityids) {
		if(entityids!=null && entityids.length>0){
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<entityids.length;i++){
				sb.append('?').append(i+2).append(',');
			}
			sb.deleteCharAt(sb.length()-1);
			Query query = em.createQuery("update Employee o set o.visible=?1 where o.username in("+ sb.toString() +")");
			query.setParameter(1, false);
			for(int i=0; i<entityids.length;i++){
				query.setParameter(i+2, entityids[i]);
			}
			query.executeUpdate();
		}
	}
}
