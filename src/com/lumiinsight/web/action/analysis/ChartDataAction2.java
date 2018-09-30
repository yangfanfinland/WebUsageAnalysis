package com.lumiinsight.web.action.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONException;
import org.json.JSONObject;


import com.lumiinsight.bean.analysis.ChartData;
import com.lumiinsight.bean.analysis.ChartDataDay;
import com.lumiinsight.service.analysis.ChartDataDayService;
import com.lumiinsight.service.analysis.ChartDataService;
import com.lumiinsight.web.action.privilege.Permission;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 这个类没有数据库的相关操作，数据从接口获得
 * @author yangfan
 *
 */
public class ChartDataAction2 extends ActionSupport implements ServletRequestAware, ServletResponseAware{
	
	@Resource
	ChartDataService chartDataService;

	@Resource
	
	ChartDataDayService chartDataDayService;
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private HttpServletResponse response;
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String getWeekDayString(int dayOfWeek){
		String weekString = "";
		final String dayNames[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		weekString = dayNames[dayOfWeek - 1];
		return weekString;
	}
	
	@Permission(module="analysisdata", privilege="view")
	public String execute() throws Exception {
		
		String selectDate = request.getParameter("selectDate").toString();
		String selectChartType = request.getParameter("selectRadioChart").toString();
		String selectServerType = request.getParameter("selectRadioServer").toString();
		
		
		String year = "";
		String month = "";
		String day = "";
		String hour = "";
		String weekInYear = "";
		String weekDay = "";
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date d=df.parse(selectDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		year = String.valueOf(cal.get(Calendar.YEAR));
		month = String.valueOf((cal.get(Calendar.MONTH)));
		day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		hour = String.valueOf(12);
		weekInYear = String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
		weekDay = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		
		System.out.println("year:"+year);
		System.out.println("month:"+month);
		System.out.println("monthday:"+day);
		System.out.println("weekday:"+hour);
		System.out.println("day of week:"+weekDay);
		System.out.println("week of year:"+weekInYear);
		
		
		
		
		
		List data1 = new ArrayList();  
        List data2 = new ArrayList();  
        List date = new ArrayList();  
        List data = new ArrayList();  
        Map map1 = null;  
        Map map2 = null;  
        Map map = new HashMap();  
        String subTitle = "";
        String url = "";
        String jsonresponse = "";
        int connectionMonday = 0;
        int completeMonday = 0;
        int connectionTuesday = 0;
        int completeTuesday = 0;
        int connectionWednesday = 0;
        int completeWednesday = 0;
        
        
        
        if(!"".equals(selectChartType)&& "week".equals(selectChartType) && "all".equals(selectServerType)){

        }else if(!"".equals(selectChartType)&& "week".equals(selectChartType)){  
        	int connectiontotal = 0;
        	int completetotal = 0;
        	JSONObject jsonObject;
        	Date time = null;
        	for(int i = 1; i < 8; i++){
        		time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
        		
        		System.out.println(time);
        
        		
        		Calendar queryTime = Calendar.getInstance();
        		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
        		
        		url = "http://qa2.lumisurvey.com/reactor-webapp/pt/lumi_survey_test/pn/lumicompass/api/get_stats?user_name=stats&password=Md8V71x77D0360D&year="+ String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
            	jsonresponse = loadJSON(url);
            	try {
        			jsonObject = new JSONObject(jsonresponse);
        			
        			connectiontotal = jsonObject.getInt("connections");
        			completetotal =	jsonObject.getInt("completes");

        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
            	data1.add(connectiontotal);
    			data2.add(completetotal);
    			date.add(getWeekDayString(i));
    			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
        	}
        }else if(!"".equals(selectChartType)&& "day".equals(selectChartType) && "all".equals(selectServerType)){
        	
        }else if(!"".equals(selectChartType)&& "day".equals(selectChartType)){
        	int connection = 0;
        	int complete = 0;
        	JSONObject jsonObject;
        	for(int i=0; i<24; i++){
        		url = "http://qa2.lumisurvey.com/reactor-webapp/pt/lumi_survey_test/pn/lumicompass/api/get_stats?user_name=stats&password=Md8V71x77D0360D&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
            	jsonresponse = loadJSON(url);
            	try {
        			jsonObject = new JSONObject(jsonresponse);
        			
        			connection = jsonObject.getInt("connections");
        			complete =	jsonObject.getInt("completes");

        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
            	data1.add(connection);
    			data2.add(complete);
    			date.add(i+":00");
    			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
        	}
        	
        }else if(!"".equals(selectChartType)&& "month".equals(selectChartType) && "all".equals(selectServerType)){
        	
        }else if(!"".equals(selectChartType)&& "month".equals(selectChartType)){ 
        	int connectiontotal = 0;
    		int completetotal = 0;
    		JSONObject jsonObject;
        	for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
        		url = "http://qa2.lumisurvey.com/reactor-webapp/pt/lumi_survey_test/pn/lumicompass/api/get_stats?user_name=stats&password=Md8V71x77D0360D&year="+ year +"&month="+month+"&day="+(i+1);
            	jsonresponse = loadJSON(url);
            	try {
        			jsonObject = new JSONObject(jsonresponse);
        			
        			connectiontotal = jsonObject.getInt("connections");
        			completetotal =	jsonObject.getInt("completes");

        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
            	data1.add(connectiontotal);
    			data2.add(completetotal);
    			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
    			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
        	}
        }
       
        map1 = combinationSeriesData("connection", data1);  
        map2 = combinationSeriesData("complete", data2);  
        
        data.add(map1);  
        data.add(map2);  
        
        map.put("categories",date);  
        map.put("series", data);  
        map.put("subtitle", subTitle);  
        
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();  
        json.putAll(map);
        try {  
            response.getWriter().print(json);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })  
    public Map combinationSeriesData(String name,List data){  
        Map map = new HashMap();  
        map.put("name", name);  
        map.put("data", data);

        return map;  
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
	
	public static Date getDayOfWeek(int year, int weekInYear, int count){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, weekInYear);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, count);
		return calendar.getTime();
	}
}
