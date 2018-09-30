package com.lumiinsight.quartz;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lumiinsight.bean.analysis.ChartData;
import com.lumiinsight.service.analysis.ChartDataService;

public class SchedulerTask {
	
	@Resource
	ChartDataService chartDataService;
	ChartData chartData = null;
	ChartData chartData2 = null;
	
	
	
	public void insert24Data() {
//		Calendar calendar = Calendar.getInstance();
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH)+1;
//		int day = calendar.get(Calendar.DAY_OF_MONTH);
//		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
//		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//		
//		//1. 每天凌晨00：00：01时，将这一天的24个小时都赋予默认值，connection和complete值都为0；
//
//		for(int i=0; i<24; i++){
//			chartData = new ChartData();
//			chartData.setComplete(0);
//			chartData.setConnection(0);
//			chartData.setHour(String.valueOf(i));
//			chartData.setDay(String.valueOf(day));
//			chartData.setMonth(String.valueOf(month));
//			chartData.setYear(String.valueOf(year));
//			chartData.setWeekDay(String.valueOf(dayOfWeek));
//			chartData.setWeekInYear(String.valueOf(weekOfYear));
//			chartData.setServerName("LSR");
//			chartDataService.save(chartData);
//		}
//		
//		for(int i=0; i<24; i++){
//			chartData2 = new ChartData();
//			chartData2.setComplete(0);
//			chartData2.setConnection(0);
//			chartData2.setHour(String.valueOf(i));
//			chartData2.setDay(String.valueOf(day));
//			chartData2.setMonth(String.valueOf(month));
//			chartData2.setYear(String.valueOf(year));
//			chartData2.setWeekDay(String.valueOf(dayOfWeek));
//			chartData2.setWeekInYear(String.valueOf(weekOfYear));
//			chartData2.setServerName("GMI");
//			chartDataService.save(chartData2);
//		}
//		
//		System.out.println("00:00:01 insert 24 pieces data!");
		System.out.println("Struts 2 + Spring + Quartz ......");
	 
	   }
}
