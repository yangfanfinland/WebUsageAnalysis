package com.lumiinsight.service.analysis;

import java.util.List;

import com.lumiinsight.bean.analysis.ChartDataDay;
import com.lumiinsight.bean.analysis.ChartDataDayTotal;
import com.lumiinsight.service.base.DAO;

public interface ChartDataDayTotalService extends DAO<ChartDataDayTotal>{
	public boolean WeekDataExist(String year, String weekInYear);
	public List<ChartDataDayTotal> queryByYearWeekInYear(String year, String weekInYear);
	public boolean MonthDataExist(String year, String month);
	public List<ChartDataDayTotal> queryByYearMonth(String year, String month);
	public boolean MonthSeverCategoryDataExist(String year, String month, String serverCategory);
	public List<ChartDataDayTotal> queryByYearMonthServerCategory(String year, String month, String serverCategory);
	public boolean WeekServerCategoryDataExist(String year, String weekInYear, String serverCategory);
	public List<ChartDataDayTotal> queryByYearWeekInYearServerCategory(String year, String weekInYear, String serverCategory);
}
