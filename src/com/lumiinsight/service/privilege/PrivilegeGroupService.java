package com.lumiinsight.service.privilege;

import java.io.Serializable;

import com.lumiinsight.bean.privilege.PrivilegeGroup;
import com.lumiinsight.service.base.DAO;

public interface PrivilegeGroupService extends DAO<PrivilegeGroup>{
	public void delete(Serializable... entityids);
}
