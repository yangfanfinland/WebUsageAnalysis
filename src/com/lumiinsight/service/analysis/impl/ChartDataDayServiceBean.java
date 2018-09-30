package com.lumiinsight.service.analysis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.analysis.ChartData;
import com.lumiinsight.bean.analysis.ChartDataDay;
import com.lumiinsight.service.analysis.ChartDataDayService;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.utils.GenericsUtils;

@Service
public class ChartDataDayServiceBean extends DaoSupport<ChartDataDay> implements ChartDataDayService{
	
	protected Class<ChartDataDay> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	@Override
	public void save(ChartDataDay entity) {
		entity.setDataID(UUID.randomUUID().toString());
		super.save(entity);
	}
	
	public boolean serverWeekDataExist(String year, String weekInYear, String serverName){
		long count = (Long)em.createQuery("select count(o) from ChartDataDay o where o.year=?1 and o.weekInYear=?2 and o.serverName=?3")
		.setParameter(1, year).setParameter(2, weekInYear).setParameter(3, serverName).getSingleResult();
		return count>6;
	}
	
	public List<ChartDataDay> queryByYearWeekInYearServerName(String year, String weekInYear, String serverName){
		List<ChartDataDay> chartDataDays = new ArrayList<ChartDataDay>();
		Query q = em.createQuery("select o from ChartDataDay o where o.year=?1 and o.weekInYear=?2 and o.serverName = ?3 order by (o.weekDay+1)");
		q.setParameter(1, year);
		q.setParameter(2, weekInYear);
		q.setParameter(3, serverName);
		chartDataDays = q.getResultList();
		return chartDataDays;
	}
	
	public boolean serverMonthDataExist(String year, String month, String serverName){
		long count = (Long)em.createQuery("select count(o) from ChartDataDay o where o.year=?1 and o.month=?2 and o.serverName=?3")
		.setParameter(1, year).setParameter(2, month).setParameter(3, serverName).getSingleResult();
		return count>27;
	}
	
	public List<ChartDataDay> queryByYearMonthServerName(String year, String month, String serverName){
		List<ChartDataDay> chartDataDays = new ArrayList<ChartDataDay>();
		Query q = em.createQuery("select o from ChartDataDay o where o.year=?1 and o.month=?2 and o.serverName = ?3 order by (o.day+1)");
		q.setParameter(1, year);
		q.setParameter(2, month);
		q.setParameter(3, serverName);
		chartDataDays = q.getResultList();
		return chartDataDays;
	}
}
