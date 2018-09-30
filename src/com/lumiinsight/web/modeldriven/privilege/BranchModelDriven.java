package com.lumiinsight.web.modeldriven.privilege;


public class BranchModelDriven extends BaseModelDriven{
	private String name;
	private String branchid;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
}
