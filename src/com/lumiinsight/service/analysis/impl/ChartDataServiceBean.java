package com.lumiinsight.service.analysis.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import com.lumiinsight.bean.analysis.ChartData;
import com.lumiinsight.service.analysis.ChartDataService;
import com.lumiinsight.service.base.DaoSupport;
import com.lumiinsight.utils.GenericsUtils;

@Service
public class ChartDataServiceBean extends DaoSupport<ChartData> implements ChartDataService{
	
	protected Class<ChartData> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	
	@Override
	public void save(ChartData entity) {
		entity.setDataID(UUID.randomUUID().toString());
		super.save(entity);
	}
	public boolean serverDayDataExist(String year, String month, String day, String serverName){
		long count = (Long)em.createQuery("select count(o) from ChartData o where o.year=?1 and o.month=?2 and o.day=?3 and o.serverName = ?4")
				.setParameter(1, year).setParameter(2, month).setParameter(3, day).setParameter(4, serverName).getSingleResult();
		return count>0;
	}
	public List<ChartData> queryByYearMonthDayServerName(String year, String month, String day, String serverName){
		List<ChartData> chartDatas = new ArrayList<ChartData>();
		Query q = em.createQuery("select o from ChartData o where o.year=?1 and o.month=?2 and o.day=?3 and o.serverName = ?4 order by (o.hour+1)");
		q.setParameter(1, year);
		q.setParameter(2, month);
		q.setParameter(3, day);
		q.setParameter(4, serverName);
		chartDatas = q.getResultList();
		return chartDatas;
	}
}
