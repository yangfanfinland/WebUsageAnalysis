package com.lumiinsight.web.modeldriven.analysis;

import com.lumiinsight.web.modeldriven.privilege.BaseModelDriven;

public class ChartDataServerModelDriven extends BaseModelDriven {
	private String serverName;
	private String serverID;
	private String serverURLAddress;
	private String serverCategory;
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerID() {
		return serverID;
	}
	public void setServerID(String serverID) {
		this.serverID = serverID;
	}
	public String getServerURLAddress() {
		return serverURLAddress;
	}
	public void setServerURLAddress(String serverURLAddress) {
		this.serverURLAddress = serverURLAddress;
	}
	public String getServerCategory() {
		return serverCategory;
	}
	public void setServerCategory(String serverCategory) {
		this.serverCategory = serverCategory;
	}
	
}
