package com.lumiinsight.service.privilege;

import java.util.List;

import com.lumiinsight.bean.privilege.SystemPrivilege;
import com.lumiinsight.service.base.DAO;

public interface SystemPrivilegeService extends DAO<SystemPrivilege>{
	/**
	 * 批量保存权限
	 * @param privileges
	 */
	public void saves(List<SystemPrivilege> privileges);
}
