package com.lumiinsight.service.analysis;

import java.util.List;

import com.lumiinsight.bean.analysis.ChartDataDay;
import com.lumiinsight.service.base.DAO;

public interface ChartDataDayService extends DAO<ChartDataDay>{
	/**
	 * 判断请求当天所在的这一个周某个服务器是否存在数据
	 * @param year
	 * @param weekInYear
	 * @param serverName
	 * @return
	 */
	public boolean serverWeekDataExist(String year, String weekInYear, String serverName);
	
	public List<ChartDataDay> queryByYearWeekInYearServerName(String year, String weekInYear, String serverName);
	
	public boolean serverMonthDataExist(String year, String month, String serverName);
	
	public List<ChartDataDay> queryByYearMonthServerName(String year, String month, String serverName);
}
