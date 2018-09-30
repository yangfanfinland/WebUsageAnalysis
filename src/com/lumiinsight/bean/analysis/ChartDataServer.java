package com.lumiinsight.bean.analysis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Server Category
 * @author yangfan
 *
 */
@Entity
public class ChartDataServer {
	private String serverID;
	private String serverName;
	private String serverURLAddress;
	private String serverCategory;
	
	
	public ChartDataServer(){}
	public ChartDataServer(String serverID) {
		super();
		this.serverID = serverID;
	}
	@Id @Column(length=36)
	public String getServerID() {
		return serverID;
	}
	public void setServerID(String serverID) {
		this.serverID = serverID;
	}
	@Column(length=20, nullable=false)
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	@Column(length=200, nullable=false)
	public String getServerURLAddress() {
		return serverURLAddress;
	}
	public void setServerURLAddress(String serverURLAddress) {
		this.serverURLAddress = serverURLAddress;
	}
	@Column(length=20, nullable=false)
	public String getServerCategory() {
		return serverCategory;
	}
	public void setServerCategory(String serverCategory) {
		this.serverCategory = serverCategory;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serverID == null) ? 0 : serverID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChartDataServer other = (ChartDataServer) obj;
		if (serverID == null) {
			if (other.serverID != null)
				return false;
		} else if (!serverID.equals(other.serverID))
			return false;
		return true;
	}
	
}
