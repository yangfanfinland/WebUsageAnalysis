package com.lumiinsight.service.analysis;

import java.util.List;

import com.lumiinsight.bean.analysis.ChartData;
import com.lumiinsight.service.base.DAO;

public interface ChartDataService extends DAO<ChartData>{
	
	/**
	 * 判断某天是否存在某个服务器的数据
	 * @param year
	 * @param weekInYear
	 * @param serverName
	 * @return
	 */
	public boolean serverDayDataExist(String year, String month, String day, String serverName);
	/**
	 * 查询某个服务器的天数据，用于单独服务器一天24小时数据展示
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @return
	 */
	public List<ChartData> queryByYearMonthDayServerName(String year, String month, String day, String serverName);
}


