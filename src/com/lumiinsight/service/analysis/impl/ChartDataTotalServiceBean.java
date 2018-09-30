package com.lumiinsight.service.analysis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.analysis.ChartData;
import com.lumiinsight.bean.analysis.ChartDataTotal;
import com.lumiinsight.service.analysis.ChartDataTotalService;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.utils.GenericsUtils;

@Service
public class ChartDataTotalServiceBean extends DaoSupport<ChartDataTotal> implements ChartDataTotalService{
	protected Class<ChartDataTotal> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	
	@Override
	public void save(ChartDataTotal entity) {
		entity.setDataID(UUID.randomUUID().toString());
		super.save(entity);
	}
	public boolean serverDayDataExist(String year, String month, String day){
		long count = (Long)em.createQuery("select count(o) from ChartDataTotal o where o.year=?1 and o.month=?2 and o.day=?3")
				.setParameter(1, year).setParameter(2, month).setParameter(3, day).getSingleResult();
		return count>0;
	}
	public boolean serverCategoryDayDataExist(String year, String month, String day, String serverCategory){
		long count = (Long)em.createQuery("select count(o) from ChartDataTotal o where o.year=?1 and o.month=?2 and o.day=?3 and o.serverCategory=?4")
				.setParameter(1, year).setParameter(2, month).setParameter(3, day).setParameter(4, serverCategory).getSingleResult();
		return count>0;
	}
	
	public List<ChartDataTotal> queryByYearMonthDay(String year, String month, String day){
		List<ChartDataTotal> chartDataTotals = new ArrayList<ChartDataTotal>();
		Query q = em.createQuery("select o from ChartDataTotal o where o.year=?1 and o.month=?2 and o.day=?3 order by (o.hour+1)");
		q.setParameter(1, year);
		q.setParameter(2, month);
		q.setParameter(3, day);
		chartDataTotals = q.getResultList();
		return chartDataTotals;
	}
	
	public List<ChartDataTotal> queryByYearMonthDayServerCategory(String year, String month, String day, String serverCategory){
		List<ChartDataTotal> chartDataTotals = new ArrayList<ChartDataTotal>();
		Query q = em.createQuery("select o from ChartDataTotal o where o.year=?1 and o.month=?2 and o.day=?3 and o.serverCategory=?4 order by (o.hour+1)");
		q.setParameter(1, year);
		q.setParameter(2, month);
		q.setParameter(3, day);
		q.setParameter(4, serverCategory);
		chartDataTotals = q.getResultList();
		return chartDataTotals;
	}
}
