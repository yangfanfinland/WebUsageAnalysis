package com.lumiinsight.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import javax.annotation.Resource;

import org.json.JSONException;
import org.json.JSONObject;

import com.lumiinsight.bean.analysis.ChartData;
import com.lumiinsight.service.analysis.ChartDataService;

public class SendRequestUpdateDataTask {
	
	@Resource
	ChartDataService chartDataService;
	ChartData chartData = null;
	ChartData chartData2 = null;
	
	
	public void requestAndUpdate(){
//		Calendar calendar = Calendar.getInstance();
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH)+1;
//		int day = calendar.get(Calendar.DAY_OF_MONTH);
//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
//		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//		
//		
//		
//		System.out.println("Interger hour send request and gathering data to update!");
//		System.out.println("Hour is :" + hour);
//		//1. 每天凌晨00：59：00时开始，之后每隔一个小时发送一次请求, 向接口发送请求
//		//2. 接口返回json格式的数据后，解析，并更新数据库
//
//		
//		String url = "http://qa2.lumisurvey.com/reactor-webapp/pt/lumi_survey_test/pn/lumicompass/api/get_stats?user_name=stats&password=Md8V71x77D0360D&year="+ year +"&month="+month+"&day="+day+"&hour="+hour;
//		String json = loadJSON(url);
//        System.out.println(json);
//        
//        String url2 = "http://qa2.lumisurvey.com/reactor-webapp/pt/lumi_survey_test/pn/lumicompass/api/get_stats?user_name=stats&password=Md8V71x77D0360D&year="+ year +"&month="+month+"&day="+day+"&hour="+hour;
//        String json2 = loadJSON(url);
//        System.out.println(json2);
//        
//        chartData = chartDataService.queryByYearMonthDayHour(String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(hour), "LSR");
//        chartData2 = chartDataService.queryByYearMonthDayHour(String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(hour), "GMI");
//        
//        
//        JSONObject jsonObject;
//        JSONObject jsonObject2;
//		try {
//			jsonObject = new JSONObject(json);
//			jsonObject2 = new JSONObject(json2);
//			
//			if(jsonObject.getInt("connections") == 0 ){
//				int i = (int) (Math.random()*1000);
//				int j = (int) (Math.random()*1000);
//				chartData.setComplete(i);
//				chartData.setConnection(j);
//			}else{
//				chartData.setComplete(jsonObject.getInt("connections"));
//				chartData.setConnection(jsonObject.getInt("completes"));
//			}
//			
//			if(jsonObject2.getInt("connections") == 0 ){
//				int i = (int) (Math.random()*1000);
//				int j = (int) (Math.random()*1000);
//				chartData2.setComplete(i);
//				chartData2.setConnection(j);
//			}else{
//				chartData2.setComplete(jsonObject.getInt("connections"));
//				chartData2.setConnection(jsonObject.getInt("completes"));
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		chartDataService.update(chartData);
//		chartDataService.update(chartData2);
		
		
		System.out.println("Interger hour send request and gathering data to update!");
	}
	
	
	
	
	
	
	
	public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                        yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
}
