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
 * 此类为数据库和接口相结合，但没有实现all server查询功能
 * @author yangfan
 *
 */
public class ChartDataAction3 extends ActionSupport implements ServletRequestAware, ServletResponseAware{
	
	@Resource
	ChartDataService chartDataService;
	@Resource
	ChartDataDayService chartDataDayService;
	
	ChartData chartData = null;
	List<ChartData> chartDatas = new ArrayList<ChartData>();
	
	ChartDataDay chartDataDay = null;
	List<ChartDataDay> chartDataDays = new ArrayList<ChartDataDay>();
	
	
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
		//selected server name
		String selectServerType = request.getParameter("selectRadioServerName").toString();
		//selected server url address value
		String selectServerTypeValue = request.getParameter("selectRadioServer").toString();
		
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
        
        
        
        
        if(!"".equals(selectChartType)&& "week".equals(selectChartType) && "all".equals(selectServerType)){

        }else if(!"".equals(selectChartType)&& "week".equals(selectChartType)){  
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(weekInYear) == today.get(Calendar.WEEK_OF_YEAR)){//请求的这天所在的周和当天所在的周一致
        		int connectiontotal = 0;
            	int completetotal = 0;
            	JSONObject jsonObject;
            	Date time = null;
            	for(int i = 1; i < 8; i++){
            		time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
            		System.out.println(time);
            		Calendar queryTime = Calendar.getInstance();
            		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
            		
            		url = selectServerTypeValue+"&year=" + String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
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
        	}else{//请求的这天所在的周和当天所在的周不一致
        		Date time = null;
    			Calendar queryTime = null;
    			for(int i = 1; i < 8; i++){
            		time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
            		System.out.println(time);
            		queryTime = Calendar.getInstance();
            		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
    			}
        		if(chartDataDayService.serverWeekDataExist(String.valueOf(queryTime.get(Calendar.YEAR)), String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)), selectServerType)){
        			
        			chartDataDays = chartDataDayService.queryByYearWeekInYearServerName(String.valueOf(queryTime.get(Calendar.YEAR)), String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)), selectServerType);
        			for(ChartDataDay chartDataDay : chartDataDays){
        				data1.add(chartDataDay.getConnection());
            			data2.add(chartDataDay.getComplete());
            			date.add(getWeekDayString(Integer.parseInt(chartDataDay.getWeekDay())));
            			subTitle = year+"-"+month+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
        			}
        		}else{
        			int connectiontotal = 0;
                	int completetotal = 0;
                	JSONObject jsonObject;
                	time = null;
                	for(int i = 1; i < 8; i++){
                		time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
                		System.out.println(time);
                		queryTime = Calendar.getInstance();
                		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
                		url = selectServerTypeValue+"&year="+ String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
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
            			
//            			chartDataDay = new ChartDataDay();
//            			chartDataDay.setConnection(connectiontotal);
//            			chartDataDay.setComplete(completetotal);
//            			chartDataDay.setServerName(selectServerType);
//            			chartDataDay.setDay(String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH)));
//            			chartDataDay.setMonth(String.valueOf(queryTime.get(Calendar.MONTH)+1));
//            			chartDataDay.setYear(String.valueOf(queryTime.get(Calendar.YEAR)));
//            			chartDataDay.setWeekDay(String.valueOf(queryTime.get(Calendar.DAY_OF_WEEK)));
//            			chartDataDay.setWeekInYear(String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)));
//            			
//            			chartDataDayService.save(chartDataDay);
                	}
        		}
        	}
        }else if(!"".equals(selectChartType)&& "day".equals(selectChartType) && "all".equals(selectServerType)){
        	
        }else if(!"".equals(selectChartType)&& "day".equals(selectChartType)){
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	//如果是请求当天的数据，从接口请求数据
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(month) == today.get(Calendar.MONTH) && Integer.parseInt(day) == today.get(Calendar.DAY_OF_MONTH)){
        		int connection = 0;
            	int complete = 0;
            	JSONObject jsonObject;
            	for(int i=0; i<24; i++){
            		url = selectServerTypeValue+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
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
        	}else{//不是请求当天数据
        		if(chartDataService.serverDayDataExist(year, String.valueOf((Integer.parseInt(month)+1)), day, selectServerType)){//如果请求的这天数据，数据库已经存在
        			System.out.println("data alreadt exists in database!");
        			chartDatas = chartDataService.queryByYearMonthDayServerName(String.valueOf(year), String.valueOf((Integer.parseInt(month)+1)), String.valueOf(day), selectServerType);
        			for(ChartData chartData : chartDatas){
            			data1.add(chartData.getConnection());
            			data2.add(chartData.getComplete());
            			date.add(chartData.getHour()+":00");
            			subTitle = year+"-"+month+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            		}
        		}else{//如果请求的这天数据，数据库不存在
        			int connection = 0;
                	int complete = 0;
                	JSONObject jsonObject;
                	for(int i=0; i<24; i++){
                		url = selectServerTypeValue+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
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
            			
            			chartData = new ChartData();
            			chartData.setConnection(connection);
            			chartData.setComplete(complete);
            			chartData.setServerName(selectServerType);
            			chartData.setHour(String.valueOf(i));
            			chartData.setDay(day);
            			chartData.setMonth(String.valueOf((Integer.parseInt(month)+1)));
            			chartData.setYear(year);
            			chartData.setWeekDay(weekDay);
            			chartData.setWeekInYear(weekInYear);
            			
            			chartDataService.save(chartData);
                	}
        		}
        	}
        }else if(!"".equals(selectChartType)&& "month".equals(selectChartType) && "all".equals(selectServerType)){
        	
        }else if(!"".equals(selectChartType)&& "month".equals(selectChartType)){ 
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(month) == today.get(Calendar.MONTH)){//请求的这天所在的月和当天所在的月一致
        		int connectiontotal = 0;
        		int completetotal = 0;
        		JSONObject jsonObject;
            	for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
            		url = selectServerTypeValue+"&year="+ year +"&month="+month+"&day="+(i+1);
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
        	}else{//请求的这天所在的月和当天所在的月不一致
        		if(chartDataDayService.serverMonthDataExist(year, String.valueOf((Integer.parseInt(month)+1)), selectServerType)){
        			chartDataDays = chartDataDayService.queryByYearMonthServerName(year, String.valueOf((Integer.parseInt(month)+1)), selectServerType);
        			for(ChartDataDay chartDataDay : chartDataDays){
        				data1.add(chartDataDay.getConnection());
            			data2.add(chartDataDay.getComplete());
            			date.add(chartDataDay.getMonth()+"-"+chartDataDay.getDay());
            			subTitle = year+"-"+String.valueOf((Integer.parseInt(month)+1))+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
        			}
        		}else{
        			int connectiontotal = 0;
            		int completetotal = 0;
            		JSONObject jsonObject;
                	for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
                		url = selectServerTypeValue+"&year="+ year +"&month="+month+"&day="+(i+1);
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
            			
            			chartDataDay = new ChartDataDay();
            			chartDataDay.setConnection(connectiontotal);
            			chartDataDay.setComplete(completetotal);
            			chartDataDay.setServerName(selectServerType);
            			chartDataDay.setDay(String.valueOf(i+1));
            			chartDataDay.setMonth(String.valueOf(Integer.parseInt(month)+1));
            			chartDataDay.setYear(year);
            			
            			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            			Date dateTransfer = sdf.parse(year+"-"+(Integer.parseInt(month)+1)+"-"+(i+1));
            			Calendar calendar = Calendar.getInstance();
            			calendar.setTime(dateTransfer);
            			chartDataDay.setWeekDay(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
            			chartDataDay.setWeekInYear(String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)));
            			
            			chartDataDayService.save(chartDataDay);
                	}
        		}
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
