package com.lumiinsight.service.analysis;

import java.util.List;

import com.lumiinsight.bean.analysis.ChartDataTotal;
import com.lumiinsight.service.base.DAO;

public interface ChartDataTotalService extends DAO<ChartDataTotal>{
	/**
	 * 判断某天是否存在某个服务器的数据
	 * @param year
	 * @param weekInYear
	 * @param serverName
	 * @return
	 */
	public boolean serverDayDataExist(String year, String month, String day);
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param serverCategory
	 * @return
	 */
	public boolean serverCategoryDayDataExist(String year, String month, String day, String serverCategory);
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public List<ChartDataTotal> queryByYearMonthDay(String year, String month, String day);
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param serverCategory
	 * @return
	 */
	public List<ChartDataTotal> queryByYearMonthDayServerCategory(String year, String month, String day, String serverCategory);
}
