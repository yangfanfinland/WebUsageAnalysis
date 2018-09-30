package com.lumiinsight.service.analysis;

import java.util.List;

import com.lumiinsight.bean.analysis.ChartDataServer;
import com.lumiinsight.service.base.DAO;

public interface ChartDataServerService extends DAO<ChartDataServer>{
	/**
	 * Fetch all data servers
	 * @return
	 */
	public List<ChartDataServer> getServers();
	
	public List<ChartDataServer> getCategorySevers(String serverCategory);
	
	public ChartDataServer getServerByServerName(String serverName);
}
