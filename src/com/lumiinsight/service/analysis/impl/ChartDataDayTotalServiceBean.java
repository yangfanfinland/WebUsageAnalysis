package com.lumiinsight.service.analysis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.analysis.ChartDataDay;
import com.lumiinsight.bean.analysis.ChartDataDayTotal;
import com.lumiinsight.service.analysis.ChartDataDayTotalService;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.utils.GenericsUtils;

@Service
public class ChartDataDayTotalServiceBean extends DaoSupport<ChartDataDayTotal> implements ChartDataDayTotalService{
	protected Class<ChartDataDayTotal> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	@Override
	public void save(ChartDataDayTotal entity) {
		entity.setDataID(UUID.randomUUID().toString());
		super.save(entity);
	}
	
	public boolean WeekDataExist(String year, String weekInYear){
		long count = (Long)em.createQuery("select count(o) from ChartDataDayTotal o where o.year=?1 and o.weekInYear=?2")
		.setParameter(1, year).setParameter(2, weekInYear).getSingleResult();
		return count>6;
	}
	
	public boolean WeekServerCategoryDataExist(String year, String weekInYear, String serverCategory){
		long count = (Long)em.createQuery("select count(o) from ChartDataDayTotal o where o.year=?1 and o.weekInYear=?2 and o.serverCategory=?3")
		.setParameter(1, year).setParameter(2, weekInYear).setParameter(3, serverCategory).getSingleResult();
		return count>6;
	}
	
	public List<ChartDataDayTotal> queryByYearWeekInYear(String year, String weekInYear){
		List<ChartDataDayTotal> chartDataDayTotals = new ArrayList<ChartDataDayTotal>();
		Query q = em.createQuery("select o from ChartDataDayTotal o where o.year=?1 and o.weekInYear=?2 order by (o.weekDay+1)");
		q.setParameter(1, year);
		q.setParameter(2, weekInYear);
		chartDataDayTotals = q.getResultList();
		return chartDataDayTotals;
	}
	
	public List<ChartDataDayTotal> queryByYearWeekInYearServerCategory(String year, String weekInYear, String serverCategory){
		List<ChartDataDayTotal> chartDataDayTotals = new ArrayList<ChartDataDayTotal>();
		Query q = em.createQuery("select o from ChartDataDayTotal o where o.year=?1 and o.weekInYear=?2 and o.serverCategory=?3 order by (o.weekDay+1)");
		q.setParameter(1, year);
		q.setParameter(2, weekInYear);
		q.setParameter(3, serverCategory);
		chartDataDayTotals = q.getResultList();
		return chartDataDayTotals;
	}
	
	public boolean MonthDataExist(String year, String month){
		long count = (Long)em.createQuery("select count(o) from ChartDataDayTotal o where o.year=?1 and o.month=?2")
		.setParameter(1, year).setParameter(2, month).getSingleResult();
		return count>27;
	}
	
	public boolean MonthSeverCategoryDataExist(String year, String month, String serverCategory){
		long count = (Long)em.createQuery("select count(o) from ChartDataDayTotal o where o.year=?1 and o.month=?2 and o.serverCategory=?3")
		.setParameter(1, year).setParameter(2, month).setParameter(3, serverCategory).getSingleResult();
		return count>27;
	}
	
	public List<ChartDataDayTotal> queryByYearMonth(String year, String month){
		List<ChartDataDayTotal> chartDataDayTotals = new ArrayList<ChartDataDayTotal>();
		Query q = em.createQuery("select o from ChartDataDayTotal o where o.year=?1 and o.month=?2 order by (o.day+1)");
		q.setParameter(1, year);
		q.setParameter(2, month);
		chartDataDayTotals = q.getResultList();
		return chartDataDayTotals;
	}
	
	public List<ChartDataDayTotal> queryByYearMonthServerCategory(String year, String month, String serverCategory){
		List<ChartDataDayTotal> chartDataDayTotals = new ArrayList<ChartDataDayTotal>();
		Query q = em.createQuery("select o from ChartDataDayTotal o where o.year=?1 and o.month=?2 and o.serverCategory=?3 order by (o.day+1)");
		q.setParameter(1, year);
		q.setParameter(2, month);
		q.setParameter(3, serverCategory);
		chartDataDayTotals = q.getResultList();
		return chartDataDayTotals;
	}
}
