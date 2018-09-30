package com.lumiinsight.web.modeldriven.privilege;

public class BaseModelDriven {
	private int page;
	private String query;

	public int getPage() {
		return page<1? 1 : page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
}
