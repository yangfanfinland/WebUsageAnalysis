package com.lumiinsight.service.analysis.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.analysis.ChartDataServer;
import com.lumiinsight.service.analysis.ChartDataServerService;
import com.lumiinsight.service.base.DaoSupport;

@Service
public class ChartDataServerServiceBean extends DaoSupport<ChartDataServer> implements ChartDataServerService{
	@Override
	public void save(ChartDataServer entity) {
		entity.setServerID(UUID.randomUUID().toString());
		super.save(entity);
	}
	
	public List<ChartDataServer> getServers(){
		List<ChartDataServer> chartDataServers= new ArrayList<ChartDataServer>();
		Query q = em.createQuery("select o from ChartDataServer o");
		chartDataServers = q.getResultList();
		return chartDataServers;
	} 
	
	public List<ChartDataServer> getCategorySevers(String serverCategory){
		List<ChartDataServer> chartDataServers= new ArrayList<ChartDataServer>();
		Query q = em.createQuery("select o from ChartDataServer o where o.serverCategory = ?1");
		q.setParameter(1, serverCategory);
		chartDataServers = q.getResultList();
		return chartDataServers;
	}
	
	public ChartDataServer getServerByServerName(String serverName){
		ChartDataServer chartDataServer = new ChartDataServer();
		Query q = em.createQuery("select o from ChartDataServer o where o.serverName = ?1");
		q.setParameter(1, serverName);
		chartDataServer = (ChartDataServer) q.getSingleResult();
		return chartDataServer;
	}
}
