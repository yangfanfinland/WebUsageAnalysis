package com.lumiinsight.web.action.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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


import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONException;
import org.json.JSONObject;


import com.lumiinsight.bean.analysis.ChartData;
import com.lumiinsight.bean.analysis.ChartDataDay;
import com.lumiinsight.bean.analysis.ChartDataDayTotal;
import com.lumiinsight.bean.analysis.ChartDataServer;
import com.lumiinsight.bean.analysis.ChartDataTotal;
import com.lumiinsight.service.analysis.ChartDataDayService;
import com.lumiinsight.service.analysis.ChartDataDayTotalService;
import com.lumiinsight.service.analysis.ChartDataServerService;
import com.lumiinsight.service.analysis.ChartDataService;
import com.lumiinsight.service.analysis.ChartDataTotalService;
import com.lumiinsight.web.action.privilege.Permission;
import com.opensymphony.xwork2.ActionSupport;

public class ChartDataAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{
	
	@Resource
	ChartDataService chartDataService;
	@Resource
	ChartDataDayService chartDataDayService;
	@Resource
	ChartDataServerService chartDataServerService;
	@Resource
	ChartDataTotalService chartDataTotalService;
	@Resource
	ChartDataDayTotalService chartDataDayTotalService;
	
	ChartData chartData = null;
	List<ChartData> chartDatas = new ArrayList<ChartData>();
	
	ChartDataDay chartDataDay = null;
	List<ChartDataDay> chartDataDays = new ArrayList<ChartDataDay>();
	
	ChartDataServer chartDataServer = null;
	List<ChartDataServer> chartDataServers = new ArrayList<>();
	
	ChartDataServer chartDataCategoryServer = null;
	List<ChartDataServer> chartDataCategoryServers = new ArrayList<>();
	
	ChartDataTotal chartDataTotal = null;
	List<ChartDataTotal> chartDataTotals = new ArrayList<>();
	
	ChartDataDayTotal chartDataDayTotal = new ChartDataDayTotal();
	List<ChartDataDayTotal> chartDataDayTotals = new ArrayList<ChartDataDayTotal>();
	
	
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
	
	
	@Permission(module="dataserver", privilege="view")
	public String getSeverByCategory(){
		String selectServerCategory = request.getParameter("selectServerCategory").toString();
		
		if(selectServerCategory.equals("all")){
			chartDataCategoryServers = chartDataServerService.getServers();
		}else{
			chartDataCategoryServers = chartDataServerService.getCategorySevers(selectServerCategory);
		}
		
		Map map = null;
		String name = "name";
		String value = "value";
		JSONArray jsonArray = new JSONArray();
		net.sf.json.JSONObject jsonObject = null;
		for(ChartDataServer chartDataServer: chartDataCategoryServers){
			map = new HashMap();
			map.put(name, chartDataServer.getServerName());
			map.put(value, chartDataServer.getServerURLAddress());
			jsonObject = net.sf.json.JSONObject.fromObject(map);
			jsonArray.add(jsonObject);
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonArray.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Permission(module="dataserver", privilege="view")
	public String execute() throws Exception {
		
		String selectDate = request.getParameter("selectDate").toString();
		String selectChartType = request.getParameter("selectRadioChart").toString();
		//选择的服务器的名字
		String selectServerType = request.getParameter("selectRadioServerName").toString();
		//选择的服务器的URL地址的值
		String selectServerTypeValue = request.getParameter("selectRadioServer").toString();
		//选择的服务器的类别
		String selectServerCategory = request.getParameter("selectServerCategory").toString();
		
		System.out.println(selectServerCategory);
		
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
		
		System.out.println("year:"+year+"month:"+month+"monthday:"+day+"hour:"+hour+"day of week:"+weekDay+"week of year:"+weekInYear);
		
		List data1 = new ArrayList();  
        List data2 = new ArrayList(); 
        List data3 = new ArrayList();
        List data4 = new ArrayList();
        List date = new ArrayList();  
        List data = new ArrayList();  
        Map map1 = null;  
        Map map2 = null;  
        Map map3 = null;  
        Map map4 = null;  
        Map map = new HashMap();  
        
        String subTitle = "";
        String url = "";
        String jsonresponse = "";
        String graph = "column";
        
        if(!"".equals(selectChartType)&& "week".equals(selectChartType) && "all".equals(selectServerTypeValue)){
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(weekInYear) == today.get(Calendar.WEEK_OF_YEAR)){//请求的这天所在的周和当天所在的周一致
        		JSONObject jsonObject;
        		
        		if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
        			Date time = null;
                	chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                	for(int i = 1; i < 8; i++){
                		int totalConnection = 0;
                    	int totalComplete = 0;
                    	for(ChartDataServer chartDataServer : chartDataServers){
                    		int connection = 0;
                        	int complete = 0;
                        	time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
                    		System.out.println(time);
                    		Calendar queryTime = Calendar.getInstance();
                    		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
                    		url = chartDataServer.getServerURLAddress()+"&year=" + String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
                        	jsonresponse = loadJSON(url);
                        	try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    		} catch (JSONException e) {
                    			e.printStackTrace();
                    		}
                        	totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                    	}
                    	data1.add(totalConnection);
            			data2.add(totalComplete);
            			date.add(getWeekDayString(i));
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        data.add(map1);  
        	        data.add(map2);
        		}else if(selectServerCategory.equals("lumiJoin")){
        			Date time = null;
                	chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                	for(int i = 1; i < 8; i++){
                		int totalConnection = 0;
                    	int totalComplete = 0;
                    	int totalInteraction = 0;
                    	int totalEvents = 0;
                    	for(ChartDataServer chartDataServer : chartDataServers){
                    		int connection = 0;
                        	int complete = 0;
                        	int interaction = 0;
                        	int events = 0;
                        	time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
                    		System.out.println(time);
                    		Calendar queryTime = Calendar.getInstance();
                    		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
                    		url = chartDataServer.getServerURLAddress()+"&year=" + String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
                        	jsonresponse = loadJSON(url);
                        	try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    			interaction = jsonObject.getInt("interaction");
                    			events = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interaction = 0;
                    			events = 0;
                    		}
                        	totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                			totalInteraction = totalInteraction + interaction;
                			totalEvents = totalEvents + events;
                    	}
                    	data1.add(totalConnection);
            			data2.add(totalComplete);
            			data3.add(totalInteraction);
            			data4.add(totalEvents);
            			date.add(getWeekDayString(i));
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
        			graph = "line";
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4);
        		}else if(selectServerCategory.equals("all")){
        			Date time = null;
                	chartDataServers = chartDataServerService.getServers();
                	for(int i = 1; i < 8; i++){
                		int totalConnection = 0;
                    	int totalComplete = 0;
                    	int totalInteraction = 0;
                    	int totalEvents = 0;
                    	for(ChartDataServer chartDataServer : chartDataServers){
                    		int connection = 0;
                        	int complete = 0;
                        	int interaction = 0;
                        	int events = 0;
                        	time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
                    		System.out.println(time);
                    		Calendar queryTime = Calendar.getInstance();
                    		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
                    		url = chartDataServer.getServerURLAddress()+"&year=" + String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
                        	jsonresponse = loadJSON(url);
                        	try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    			interaction = jsonObject.getInt("interaction");
                    			events = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interaction = 0;
                    			events = 0;
                    		}
                        	totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                			totalInteraction = totalInteraction + interaction;
                			totalEvents = totalEvents + events;
                    	}
                    	data1.add(totalConnection);
            			data2.add(totalComplete);
            			data3.add(totalInteraction);
            			data4.add(totalEvents);
            			date.add(getWeekDayString(i));
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
        			graph = "line";
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4);
        		}
        	}else{
        		Date time = null;
    			Calendar queryTime = null;
    			for(int i = 1; i < 8; i++){
            		time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
            		System.out.println(time);
            		queryTime = Calendar.getInstance();
            		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
    			}
        		if(chartDataDayTotalService.WeekServerCategoryDataExist(String.valueOf(queryTime.get(Calendar.YEAR)), String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)), selectServerCategory)){
        			if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
        				chartDataDayTotals = chartDataDayTotalService.queryByYearWeekInYearServerCategory(String.valueOf(queryTime.get(Calendar.YEAR)), String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)), selectServerCategory);
            			for(ChartDataDayTotal chartDataDayTotal : chartDataDayTotals){
            				data1.add(chartDataDayTotal.getTotalConnection());
                			data2.add(chartDataDayTotal.getTotalComplete());
                			date.add(getWeekDayString(Integer.parseInt(chartDataDayTotal.getWeekDay())));
            			}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(selectServerCategory.equals("lumiJoin")){
        				chartDataDayTotals = chartDataDayTotalService.queryByYearWeekInYearServerCategory(String.valueOf(queryTime.get(Calendar.YEAR)), String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)), selectServerCategory);
            			for(ChartDataDayTotal chartDataDayTotal : chartDataDayTotals){
            				data1.add(chartDataDayTotal.getTotalConnection());
                			data2.add(chartDataDayTotal.getTotalComplete());
                			data3.add(chartDataDayTotal.getTotalInteraction());
                			data4.add(chartDataDayTotal.getTotalEvents());
                			date.add(getWeekDayString(Integer.parseInt(chartDataDayTotal.getWeekDay())));
            			}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
            			graph = "line";
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}else if(selectServerCategory.equals("all")){
        				chartDataDayTotals = chartDataDayTotalService.queryByYearWeekInYearServerCategory(String.valueOf(queryTime.get(Calendar.YEAR)), String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)), selectServerCategory);
            			for(ChartDataDayTotal chartDataDayTotal : chartDataDayTotals){
            				data1.add(chartDataDayTotal.getTotalConnection());
                			data2.add(chartDataDayTotal.getTotalComplete());
                			data3.add(chartDataDayTotal.getTotalInteraction());
                			data4.add(chartDataDayTotal.getTotalEvents());
                			date.add(getWeekDayString(Integer.parseInt(chartDataDayTotal.getWeekDay())));
            			}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
            			graph = "line";
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        		}else{
        			if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
        				JSONObject jsonObject;
                    	time = null;
                    	chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                    	for(int i = 1; i < 8; i++){
                    		int totalConnection = 0;
                        	int totalComplete = 0;
                        	for(ChartDataServer chartDataServer : chartDataServers){
                        		int connection = 0;
                            	int complete = 0;
                            	time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
                        		System.out.println(time);
                        		queryTime = Calendar.getInstance();
                        		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
                        		
                        		url = chartDataServer.getServerURLAddress()+"&year=" + String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
                            	jsonresponse = loadJSON(url);
                            	try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");

                        		} catch (JSONException e) {
                        			e.printStackTrace();
                        		}
                            	totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                        	}
                        	data1.add(totalConnection);
                			data2.add(totalComplete);
                			date.add(getWeekDayString(i));
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(selectServerCategory.equals("lumiJoin")){//server的类别是lumi Join
        				JSONObject jsonObject;
                    	time = null;
                    	chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                    	for(int i = 1; i < 8; i++){
                    		int totalConnection = 0;
                        	int totalComplete = 0;
                        	int totalInteraction = 0;
                        	int totalEvents = 0;
                        	for(ChartDataServer chartDataServer : chartDataServers){
                        		int connection = 0;
                            	int complete = 0;
                            	int interaction = 0;
                            	int events = 0;
                            	time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
                        		System.out.println(time);
                        		queryTime = Calendar.getInstance();
                        		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
                        		
                        		url = chartDataServer.getServerURLAddress()+"&year=" + String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
                            	jsonresponse = loadJSON(url);
                            	try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");
                        			interaction = jsonObject.getInt("interaction");
                        			events = jsonObject.getInt("events");
                        		} catch (JSONException e) {
                        			interaction = 0;
                        			events = 0;
                        		}
                            	totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                    			totalInteraction = totalInteraction + interaction;
                    			totalEvents = totalEvents + events;
                        	}
                        	data1.add(totalConnection);
                			data2.add(totalComplete);
                			data3.add(totalInteraction);
                			data4.add(totalEvents);
                			date.add(getWeekDayString(i));
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
                    	graph = "line";
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4); 
        			}else if(selectServerCategory.equals("all")){
        				JSONObject jsonObject;
                    	time = null;
                    	chartDataServers = chartDataServerService.getServers();
                    	for(int i = 1; i < 8; i++){
                    		int totalConnection = 0;
                        	int totalComplete = 0;
                        	int totalInteraction = 0;
                        	int totalEvents = 0;
                        	for(ChartDataServer chartDataServer : chartDataServers){
                        		int connection = 0;
                            	int complete = 0;
                            	int interaction = 0;
                            	int events = 0;
                            	time = getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i);
                        		System.out.println(time);
                        		queryTime = Calendar.getInstance();
                        		queryTime.setTime(getDayOfWeek(Integer.parseInt(year), Integer.parseInt(weekInYear), i));
                        		
                        		url = chartDataServer.getServerURLAddress()+"&year=" + String.valueOf(queryTime.get(Calendar.YEAR)) +"&month="+String.valueOf(queryTime.get(Calendar.MONTH))+"&day="+String.valueOf(queryTime.get(Calendar.DAY_OF_MONTH));
                            	jsonresponse = loadJSON(url);
                            	try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");
                        			interaction = jsonObject.getInt("interaction");
                        			events = jsonObject.getInt("events");
                        		} catch (JSONException e) {
                        			interaction = 0;
                        			events = 0;
                        		}
                            	totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                    			totalInteraction = totalInteraction + interaction;
                    			totalEvents = totalEvents + events;
                        	}
                        	data1.add(totalConnection);
                			data2.add(totalComplete);
                			data3.add(totalInteraction);
                			data4.add(totalEvents);
                			date.add(getWeekDayString(i));
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
                    	graph = "line";
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4); 
        			}
        		}
        	}
        }else if(!"".equals(selectChartType)&& "week".equals(selectChartType)){  
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(weekInYear) == today.get(Calendar.WEEK_OF_YEAR)){//请求的这天所在的周和当天所在的周一致
        		if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
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
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        data.add(map1);  
        	        data.add(map2);  
        		}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        			int connectiontotal = 0;
                	int completetotal = 0;
                	int interactiontotal = 0;
                	int eventstotal = 0;
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
                			interactiontotal = jsonObject.getInt("interaction");
                			eventstotal = jsonObject.getInt("events");
                		} catch (JSONException e) {
                			interactiontotal = 0;
                			eventstotal = 0;
                		}
                    	data1.add(connectiontotal);
            			data2.add(completetotal);
            			data3.add(interactiontotal);
            			data4.add(eventstotal);
            			date.add(getWeekDayString(i));
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
                	graph = "line";
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4);
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
        			if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
        				chartDataDays = chartDataDayService.queryByYearWeekInYearServerName(String.valueOf(queryTime.get(Calendar.YEAR)), String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)), selectServerType);
            			for(ChartDataDay chartDataDay : chartDataDays){
            				data1.add(chartDataDay.getConnection());
                			data2.add(chartDataDay.getComplete());
                			date.add(getWeekDayString(Integer.parseInt(chartDataDay.getWeekDay())));
            			}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        				chartDataDays = chartDataDayService.queryByYearWeekInYearServerName(String.valueOf(queryTime.get(Calendar.YEAR)), String.valueOf(queryTime.get(Calendar.WEEK_OF_YEAR)), selectServerType);
            			for(ChartDataDay chartDataDay : chartDataDays){
            				data1.add(chartDataDay.getConnection());
                			data2.add(chartDataDay.getComplete());
                			data3.add(chartDataDay.getInteraction());
                			data4.add(chartDataDay.getEvents());
                			date.add(getWeekDayString(Integer.parseInt(chartDataDay.getWeekDay())));
            			}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
            			graph = "line";
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        		}else{
        			if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
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
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        				int connectiontotal = 0;
                    	int completetotal = 0;
                    	int interactiontotal = 0;
                    	int eventstotal = 0;
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
                    			interactiontotal = jsonObject.getInt("interaction");
                    			eventstotal = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interactiontotal = 0;
                    			eventstotal = 0;
                    		}
                        	data1.add(connectiontotal);
                			data2.add(completetotal);
                			data3.add(interactiontotal);
                			data4.add(eventstotal);
                			date.add(getWeekDayString(i));
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Weekly Chart]    Server : " +selectServerType;
                    	graph = "line";
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        		}
        	}
        }else if(!"".equals(selectChartType)&& "day".equals(selectChartType) && "all".equals(selectServerTypeValue)){
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	//如果是请求当天的数据，从接口请求数据
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(month) == today.get(Calendar.MONTH) && Integer.parseInt(day) == today.get(Calendar.DAY_OF_MONTH)){
            	JSONObject jsonObject;
            	if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
            		chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                	for(int i=0; i<24; i++){
                		int totalConnection = 0;
                    	int totalComplete = 0;
                		for(ChartDataServer chartDataServer : chartDataServers){
                			int connection = 0;
                        	int complete = 0;
                			url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
                			System.out.println("url is" + url + "server is " + chartDataServer.getServerName());
                			jsonresponse = loadJSON(url);
                			try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    		} catch (JSONException e) {
                    			e.printStackTrace();
                    		}
                			totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                		}
                		data1.add(totalConnection);
            			data2.add(totalComplete);
            			date.add(i+":00");
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;	
                	}
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        data.add(map1);  
        	        data.add(map2);     
            	}else if(selectServerCategory.equals("lumiJoin")){//server的类别是lumi Join
            		chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                	for(int i=0; i<24; i++){
                		int totalConnection = 0;
                    	int totalComplete = 0;
                    	int totalInteraction = 0;
                    	int totalEvents = 0;
                		for(ChartDataServer chartDataServer : chartDataServers){
                			int connection = 0;
                        	int complete = 0;
                        	int interaction = 0;
                        	int events = 0;
                        	url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
                			System.out.println("url is" + url + "server is " + chartDataServer.getServerName());
                			jsonresponse = loadJSON(url);
                			try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    			interaction = jsonObject.getInt("interaction");
                    			events = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interaction = 0;
                    			events = 0;
                    		}
                			totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                			totalInteraction = totalInteraction + interaction;
                			totalEvents = totalEvents + events;
                		}
                		data1.add(totalConnection);
            			data2.add(totalComplete);
            			data3.add(totalInteraction);
            			data4.add(totalEvents);
            			date.add(i+":00");	
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
        			graph = "line";
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4);
            	}else if(selectServerCategory.equals("all")){//server类别是所有的类别
            		chartDataServers = chartDataServerService.getServers();
                	for(int i=0; i<24; i++){
                		int totalConnection = 0;
                    	int totalComplete = 0;
                    	int totalInteraction = 0;
                    	int totalEvents = 0;
                		for(ChartDataServer chartDataServer : chartDataServers){
                			int connection = 0;
                        	int complete = 0;
                        	int interaction = 0;
                        	int events = 0;
                        	url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
                			System.out.println("url is" + url + "server is " + chartDataServer.getServerName());
                			jsonresponse = loadJSON(url);
                			try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    			interaction = jsonObject.getInt("interaction");
                    			events = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interaction = 0;
                    			events = 0;
                    		}
                			totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                			totalInteraction = totalInteraction + interaction;
                			totalEvents = totalEvents + events;
                		}
                		data1.add(totalConnection);
            			data2.add(totalComplete);
            			data3.add(totalInteraction);
            			data4.add(totalEvents);
            			date.add(i+":00");
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			graph = "line";
                	}
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4);
            	}
        	}else{//如果不是请求当天的数据
        		if(chartDataTotalService.serverCategoryDayDataExist(year, String.valueOf((Integer.parseInt(month)+1)), day, selectServerCategory)){//数据库存在请求的这一天的数据
        			if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
        				chartDataTotals = chartDataTotalService.queryByYearMonthDayServerCategory(String.valueOf(year), String.valueOf((Integer.parseInt(month)+1)), String.valueOf(day), selectServerCategory);
            			for(ChartDataTotal chartDataTotal : chartDataTotals){
                			data1.add(chartDataTotal.getTotalConnection());
                			data2.add(chartDataTotal.getTotalComplete());
                			date.add(chartDataTotal.getHour()+":00");
                		}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2); 
        			}else if(selectServerCategory.equals("lumiJoin")){//server的类别是lumi Join
        				chartDataTotals = chartDataTotalService.queryByYearMonthDayServerCategory(String.valueOf(year), String.valueOf((Integer.parseInt(month)+1)), String.valueOf(day), selectServerCategory);
            			for(ChartDataTotal chartDataTotal : chartDataTotals){
                			data1.add(chartDataTotal.getTotalConnection());
                			data2.add(chartDataTotal.getTotalComplete());
                			data3.add(chartDataTotal.getTotalInteraction());
                			data4.add(chartDataTotal.getTotalEvents());
                			date.add(chartDataTotal.getHour()+":00");
                		}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}else if(selectServerCategory.equals("all")){//server类别是所有的类别
        				chartDataTotals = chartDataTotalService.queryByYearMonthDayServerCategory(String.valueOf(year), String.valueOf((Integer.parseInt(month)+1)), String.valueOf(day), selectServerCategory);
            			for(ChartDataTotal chartDataTotal : chartDataTotals){
                			data1.add(chartDataTotal.getTotalConnection());
                			data2.add(chartDataTotal.getTotalComplete());
                			data3.add(chartDataTotal.getTotalInteraction());
                			data4.add(chartDataTotal.getTotalEvents());
                			date.add(chartDataTotal.getHour()+":00");
                		}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        		}else{//数据库不存在请求的这一天的数据
        			JSONObject jsonObject;
        			if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
        				chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                    	for(int i=0; i<24; i++){
                    		int totalConnection = 0;
                        	int totalComplete = 0;
                    		for(ChartDataServer chartDataServer : chartDataServers){
                    			int connection = 0;
                            	int complete = 0;
                    			url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
                    			System.out.println("url is" + url + "server is " + chartDataServer.getServerName());
                    			jsonresponse = loadJSON(url);
                    			try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");
                        		} catch (JSONException e) {
                        			e.printStackTrace();
                        		}
                    			totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                    		}
                    		data1.add(totalConnection);
                			data2.add(totalComplete);
                			date.add(i+":00");
                			
                			chartDataTotal = new ChartDataTotal();
                			chartDataTotal.setTotalConnection(totalConnection);
                			chartDataTotal.setTotalComplete(totalComplete);
                			chartDataTotal.setTotalInteraction(0);
                			chartDataTotal.setTotalEvents(0);
                			chartDataTotal.setServerName(selectServerType);
                			chartDataTotal.setServerCategory(selectServerCategory);
                			chartDataTotal.setHour(String.valueOf(i));
                			chartDataTotal.setDay(day);
                			chartDataTotal.setMonth(String.valueOf((Integer.parseInt(month)+1)));
                			chartDataTotal.setYear(year);
                			chartDataTotal.setWeekDay(weekDay);
                			chartDataTotal.setWeekInYear(weekInYear);
                			chartDataTotalService.save(chartDataTotal);
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(selectServerCategory.equals("lumiJoin")){//server的类别是lumi Join
        				chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                    	for(int i=0; i<24; i++){
                    		int totalConnection = 0;
                        	int totalComplete = 0;
                        	int totalInteraction = 0;
                        	int totalEvents = 0;
                    		for(ChartDataServer chartDataServer : chartDataServers){
                    			int connection = 0;
                            	int complete = 0;
                            	int interaction = 0;
                            	int events = 0;
                    			url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
                    			System.out.println("url is" + url + "server is " + chartDataServer.getServerName());
                    			jsonresponse = loadJSON(url);
                    			try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");
                        			interaction = jsonObject.getInt("interaction");
                        			events = jsonObject.getInt("events");
                        		} catch (JSONException e) {
                        			interaction = 0;
                        			events = 0;
                        		}
                    			totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                    			totalInteraction = totalInteraction + interaction;
                    			totalEvents = totalEvents + events;
                    		}
                    		data1.add(totalConnection);
                			data2.add(totalComplete);
                			data3.add(totalInteraction);
                			data4.add(totalEvents);
                			date.add(i+":00");
                			
                			
                			chartDataTotal = new ChartDataTotal();
                			chartDataTotal.setTotalConnection(totalConnection);
                			chartDataTotal.setTotalComplete(totalComplete);
                			chartDataTotal.setTotalInteraction(totalInteraction);
                			chartDataTotal.setTotalEvents(totalEvents);
                			chartDataTotal.setServerName(selectServerType);
                			chartDataTotal.setServerCategory(selectServerCategory);
                			chartDataTotal.setHour(String.valueOf(i));
                			chartDataTotal.setDay(day);
                			chartDataTotal.setMonth(String.valueOf((Integer.parseInt(month)+1)));
                			chartDataTotal.setYear(year);
                			chartDataTotal.setWeekDay(weekDay);
                			chartDataTotal.setWeekInYear(weekInYear);
                			chartDataTotalService.save(chartDataTotal);
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}else if(selectServerCategory.equals("all")){//server类别是所有的类别
        				chartDataServers = chartDataServerService.getServers();
                    	for(int i=0; i<24; i++){
                    		int totalConnection = 0;
                        	int totalComplete = 0;
                        	int totalInteraction = 0;
                        	int totalEvents = 0;
                    		for(ChartDataServer chartDataServer : chartDataServers){
                    			int connection = 0;
                            	int complete = 0;
                            	int interaction = 0;
                            	int events = 0;
                    			url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
                    			System.out.println("url is" + url + "server is " + chartDataServer.getServerName());
                    			jsonresponse = loadJSON(url);
                    			try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");
                        			interaction = jsonObject.getInt("interaction");
                        			events = jsonObject.getInt("events");
                        		} catch (JSONException e) {
                        			interaction = 0;
                        			events = 0;
                        		}
                    			totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                    			totalInteraction = totalInteraction + interaction;
                    			totalEvents = totalEvents + events;
                    		}
                    		data1.add(totalConnection);
                			data2.add(totalComplete);
                			data3.add(totalInteraction);
                			data4.add(totalEvents);
                			date.add(i+":00");
                			
                			chartDataTotal = new ChartDataTotal();
                			chartDataTotal.setTotalConnection(totalConnection);
                			chartDataTotal.setTotalComplete(totalComplete);
                			chartDataTotal.setTotalInteraction(totalInteraction);
                			chartDataTotal.setTotalEvents(totalEvents);
                			chartDataTotal.setServerName(selectServerType);
                			chartDataTotal.setServerCategory(selectServerCategory);
                			chartDataTotal.setHour(String.valueOf(i));
                			chartDataTotal.setDay(day);
                			chartDataTotal.setMonth(String.valueOf((Integer.parseInt(month)+1)));
                			chartDataTotal.setYear(year);
                			chartDataTotal.setWeekDay(weekDay);
                			chartDataTotal.setWeekInYear(weekInYear);
                			chartDataTotalService.save(chartDataTotal);
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        		}
        	}
        }else if(!"".equals(selectChartType)&& "day".equals(selectChartType)){
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	//如果是请求当天的数据，从接口请求数据
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(month) == today.get(Calendar.MONTH) && Integer.parseInt(day) == today.get(Calendar.DAY_OF_MONTH)){
        		if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
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
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        data.add(map1);  
        	        data.add(map2);  
        		}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        			int connection = 0;
                	int complete = 0;
                	int interaction = 0;
                	int events = 0;
                	JSONObject jsonObject;
                	for(int i=0; i<24; i++){
                		url = selectServerTypeValue+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
                    	jsonresponse = loadJSON(url);
                    	try {
                			jsonObject = new JSONObject(jsonresponse);
                			connection = jsonObject.getInt("connections");
                			complete =	jsonObject.getInt("completes");
                			interaction = jsonObject.getInt("interaction");
                			events = jsonObject.getInt("events");
                		} catch (JSONException e) {
                			interaction = 0;
                			events = 0;
                		}
                    	data1.add(connection);
            			data2.add(complete);
            			data3.add(interaction);
            			data4.add(events);
            			date.add(i+":00");
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
                	graph = "line";
        			map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4);
        		}	
        	}else{//不是请求当天数据
        		if(chartDataService.serverDayDataExist(year, String.valueOf((Integer.parseInt(month)+1)), day, selectServerType)){//如果请求的这天数据，数据库已经存在
        			if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
        				chartDatas = chartDataService.queryByYearMonthDayServerName(String.valueOf(year), String.valueOf((Integer.parseInt(month)+1)), String.valueOf(day), selectServerType);
            			for(ChartData chartData : chartDatas){
                			data1.add(chartData.getConnection());
                			data2.add(chartData.getComplete());
                			date.add(chartData.getHour()+":00");
                		}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2); 
        			}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        				chartDatas = chartDataService.queryByYearMonthDayServerName(String.valueOf(year), String.valueOf((Integer.parseInt(month)+1)), String.valueOf(day), selectServerType);
            			for(ChartData chartData : chartDatas){
                			data1.add(chartData.getConnection());
                			data2.add(chartData.getComplete());
                			data3.add(chartData.getInteraction());
                			data4.add(chartData.getEvents());
                			date.add(chartData.getHour()+":00");
                		}
            			subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
            			graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        		}else{//如果请求的这天数据，数据库不存在
        			if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
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
   
                			chartData = new ChartData();
                			chartData.setConnection(connection);
                			chartData.setComplete(complete);
                			chartData.setInteraction(0);
                			chartData.setEvents(0);
                			chartData.setServerName(selectServerType);
                			chartData.setServerCategory(chartDataServerService.getServerByServerName(selectServerType).getServerCategory());
                			chartData.setHour(String.valueOf(i));
                			chartData.setDay(day);
                			chartData.setMonth(String.valueOf((Integer.parseInt(month)+1)));
                			chartData.setYear(year);
                			chartData.setWeekDay(weekDay);
                			chartData.setWeekInYear(weekInYear);
                			chartDataService.save(chartData);
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        				int connection = 0;
                    	int complete = 0;
                    	int interaction = 0;
                    	int events = 0;
                    	JSONObject jsonObject;
                    	for(int i=0; i<24; i++){
                    		url = selectServerTypeValue+"&year="+ year +"&month="+month+"&day="+day+"&hour="+i;
                        	jsonresponse = loadJSON(url);
                        	try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    			interaction = jsonObject.getInt("interaction");
                    			events = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interaction = 0;
                    			events = 0;
                    		}
                        	data1.add(connection);
                			data2.add(complete);
                			data3.add(interaction);
                			data4.add(events);
                			date.add(i+":00");
   
                			chartData = new ChartData();
                			chartData.setConnection(connection);
                			chartData.setComplete(complete);
                			chartData.setInteraction(interaction);
                			chartData.setEvents(events);
                			chartData.setServerName(selectServerType);
                			chartData.setServerCategory(chartDataServerService.getServerByServerName(selectServerType).getServerCategory());
                			chartData.setHour(String.valueOf(i));
                			chartData.setDay(day);
                			chartData.setMonth(String.valueOf((Integer.parseInt(month)+1)));
                			chartData.setYear(year);
                			chartData.setWeekDay(weekDay);
                			chartData.setWeekInYear(weekInYear);
                			chartDataService.save(chartData);
                    	}
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Daily Chart]    Server : " +selectServerType;
                    	graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        			
        		}
        	}
        }else if(!"".equals(selectChartType)&& "month".equals(selectChartType) && "all".equals(selectServerTypeValue)){
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(month) == today.get(Calendar.MONTH)){//请求的这天所在的月和当天所在的月一致
        		JSONObject jsonObject;
        		if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
        			chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
            		for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
            			int totalConnection = 0;
                    	int totalComplete = 0;
                    	for(ChartDataServer chartDataServer : chartDataServers){
                    		int connection = 0;
                        	int complete = 0;
                        	url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+(i+1);
                        	jsonresponse = loadJSON(url);
                        	try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");

                    		} catch (JSONException e) {
                    			e.printStackTrace();
                    		}
                        	totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                		}
                    	data1.add(totalConnection);
            			data2.add(totalComplete);
            			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
            		}
            		subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
            		map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        data.add(map1);  
        	        data.add(map2);  
        		}else if(selectServerCategory.equals("lumiJoin")){
        			chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
            		for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
            			int totalConnection = 0;
                    	int totalComplete = 0;
                    	int totalInteraction = 0;
                    	int totalEvents = 0;
                    	for(ChartDataServer chartDataServer : chartDataServers){
                    		int connection = 0;
                        	int complete = 0;
                        	int interaction = 0;
                        	int events = 0;
                        	url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+(i+1);
                        	jsonresponse = loadJSON(url);
                        	try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    			interaction = jsonObject.getInt("interaction");
                    			events = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interaction = 0;
                    			events = 0;
                    		}
                        	totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                			totalInteraction = totalInteraction + interaction;
                			totalEvents = totalEvents + events;
                		}
                    	data1.add(totalConnection);
            			data2.add(totalComplete);
            			data3.add(totalInteraction);
            			data4.add(totalEvents);
            			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
            		}
            		subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
            		graph = "line";
        			map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4); 
        		}else if(selectServerCategory.equals("all")){
        			chartDataServers = chartDataServerService.getServers();
            		for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
            			int totalConnection = 0;
                    	int totalComplete = 0;
                    	int totalInteraction = 0;
                    	int totalEvents = 0;
                    	for(ChartDataServer chartDataServer : chartDataServers){
                    		int connection = 0;
                        	int complete = 0;
                        	int interaction = 0;
                        	int events = 0;
                        	url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+(i+1);
                        	jsonresponse = loadJSON(url);
                        	try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connection = jsonObject.getInt("connections");
                    			complete =	jsonObject.getInt("completes");
                    			interaction = jsonObject.getInt("interaction");
                    			events = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interaction = 0;
                    			events = 0;
                    		}
                        	totalConnection = totalConnection + connection;
                			totalComplete = totalComplete + complete;
                			totalInteraction = totalInteraction + interaction;
                			totalEvents = totalEvents + events;
                		}
                    	data1.add(totalConnection);
            			data2.add(totalComplete);
            			data3.add(totalInteraction);
            			data4.add(totalEvents);
            			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
            		}
            		subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
            		graph = "line";
        			map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4);
        		}       		
        	}else{
        		if(chartDataDayTotalService.MonthSeverCategoryDataExist(year, String.valueOf((Integer.parseInt(month)+1)), selectServerCategory)){
        			if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
        				chartDataDayTotals = chartDataDayTotalService.queryByYearMonthServerCategory(year, String.valueOf((Integer.parseInt(month)+1)), selectServerCategory);
        				for(ChartDataDayTotal chartDataDayTotal : chartDataDayTotals){
            				data1.add(chartDataDayTotal.getTotalConnection());
                			data2.add(chartDataDayTotal.getTotalComplete());
                			date.add(chartDataDayTotal.getMonth()+"-"+chartDataDayTotal.getDay());
            			}
        				subTitle = year+"-"+String.valueOf((Integer.parseInt(month)+1))+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
        				map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(selectServerCategory.equals("lumiJoin")){
        				chartDataDayTotals = chartDataDayTotalService.queryByYearMonthServerCategory(year, String.valueOf((Integer.parseInt(month)+1)), selectServerCategory);
        				for(ChartDataDayTotal chartDataDayTotal : chartDataDayTotals){
            				data1.add(chartDataDayTotal.getTotalConnection());
                			data2.add(chartDataDayTotal.getTotalComplete());
                			data3.add(chartDataDayTotal.getTotalInteraction());
                			data4.add(chartDataDayTotal.getTotalEvents());
                			date.add(chartDataDayTotal.getMonth()+"-"+chartDataDayTotal.getDay());
            			}
        				subTitle = year+"-"+String.valueOf((Integer.parseInt(month)+1))+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
        				graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}else if(selectServerCategory.equals("all")){
        				chartDataDayTotals = chartDataDayTotalService.queryByYearMonthServerCategory(year, String.valueOf((Integer.parseInt(month)+1)), selectServerCategory);
        				for(ChartDataDayTotal chartDataDayTotal : chartDataDayTotals){
            				data1.add(chartDataDayTotal.getTotalConnection());
                			data2.add(chartDataDayTotal.getTotalComplete());
                			data3.add(chartDataDayTotal.getTotalInteraction());
                			data4.add(chartDataDayTotal.getTotalEvents());
                			date.add(chartDataDayTotal.getMonth()+"-"+chartDataDayTotal.getDay());
            			}
        				subTitle = year+"-"+String.valueOf((Integer.parseInt(month)+1))+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
        				graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        		}else{
        			JSONObject jsonObject;
        			if(selectServerCategory.equals("lumiSay")){//server的类别是lumi Say
        				chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                		for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
                			int totalConnection = 0;
                        	int totalComplete = 0;
                        	for(ChartDataServer chartDataServer : chartDataServers){
                        		int connection = 0;
                            	int complete = 0;
                            	url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+(i+1);
                            	jsonresponse = loadJSON(url);
                            	try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");
                        		} catch (JSONException e) {
                        			e.printStackTrace();
                        		}
                            	totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                    		}
                        	data1.add(totalConnection);
                			data2.add(totalComplete);
                			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
                			
                			chartDataDayTotal = new ChartDataDayTotal();
                			chartDataDayTotal.setTotalConnection(totalConnection);
                			chartDataDayTotal.setTotalComplete(totalComplete);
                			chartDataDayTotal.setTotalInteraction(0);
                			chartDataDayTotal.setTotalEvents(0);
                			chartDataDayTotal.setServerName(selectServerType);
                			chartDataDayTotal.setServerCategory(selectServerCategory);
                			chartDataDayTotal.setDay(String.valueOf(i+1));
                			chartDataDayTotal.setMonth(String.valueOf(Integer.parseInt(month)+1));
                			chartDataDayTotal.setYear(year);
                			
                			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
                			Date dateTransfer = sdf.parse(year+"-"+(Integer.parseInt(month)+1)+"-"+(i+1));
                			Calendar calendar = Calendar.getInstance();
                			calendar.setTime(dateTransfer);
                			chartDataDayTotal.setWeekDay(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
                			chartDataDayTotal.setWeekInYear(String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)));
                			chartDataDayTotalService.save(chartDataDayTotal);
                		}
                		subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
                		map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(selectServerCategory.equals("lumiJoin")){//server的类别是lumi Join
        				chartDataServers = chartDataServerService.getCategorySevers(selectServerCategory);
                		for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
                			int totalConnection = 0;
                        	int totalComplete = 0;
                        	int totalInteraction = 0;
                        	int totalEvents = 0;
                        	for(ChartDataServer chartDataServer : chartDataServers){
                        		int connection = 0;
                            	int complete = 0;
                            	int interaction = 0;
                            	int events = 0;
                            	url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+(i+1);
                            	jsonresponse = loadJSON(url);
                            	try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");
                        			interaction = jsonObject.getInt("interaction");
                        			events = jsonObject.getInt("events");
                        		} catch (JSONException e) {
                        			interaction = 0;
                        			events = 0;
                        		}
                            	totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                    			totalInteraction = totalInteraction + interaction;
                    			totalEvents = totalEvents + events;
                    		}
                        	data1.add(totalConnection);
                			data2.add(totalComplete);
                			data3.add(totalInteraction);
                			data4.add(totalEvents);
                			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
                			
                			chartDataDayTotal = new ChartDataDayTotal();
                			chartDataDayTotal.setTotalConnection(totalConnection);
                			chartDataDayTotal.setTotalComplete(totalComplete);
                			chartDataDayTotal.setTotalInteraction(totalInteraction);
                			chartDataDayTotal.setTotalEvents(totalEvents);
                			chartDataDayTotal.setServerName(selectServerType);
                			chartDataDayTotal.setServerCategory(selectServerCategory);
                			chartDataDayTotal.setDay(String.valueOf(i+1));
                			chartDataDayTotal.setMonth(String.valueOf(Integer.parseInt(month)+1));
                			chartDataDayTotal.setYear(year);
                			
                			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
                			Date dateTransfer = sdf.parse(year+"-"+(Integer.parseInt(month)+1)+"-"+(i+1));
                			Calendar calendar = Calendar.getInstance();
                			calendar.setTime(dateTransfer);
                			chartDataDayTotal.setWeekDay(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
                			chartDataDayTotal.setWeekInYear(String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)));
                			chartDataDayTotalService.save(chartDataDayTotal);
                		}
                		subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
                		graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}else if(selectServerCategory.equals("all")){//all server类别
        				chartDataServers = chartDataServerService.getServers();
                		for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
                			int totalConnection = 0;
                        	int totalComplete = 0;
                        	int totalInteraction = 0;
                        	int totalEvents = 0;
                        	for(ChartDataServer chartDataServer : chartDataServers){
                        		int connection = 0;
                            	int complete = 0;
                            	int interaction = 0;
                            	int events = 0;
                            	url = chartDataServer.getServerURLAddress()+"&year="+ year +"&month="+month+"&day="+(i+1);
                            	jsonresponse = loadJSON(url);
                            	try {
                        			jsonObject = new JSONObject(jsonresponse);
                        			connection = jsonObject.getInt("connections");
                        			complete =	jsonObject.getInt("completes");
                        			interaction = jsonObject.getInt("interaction");
                        			events = jsonObject.getInt("events");
                        		} catch (JSONException e) {
                        			interaction = 0;
                        			events = 0;
                        		}
                            	totalConnection = totalConnection + connection;
                    			totalComplete = totalComplete + complete;
                    			totalInteraction = totalInteraction + interaction;
                    			totalEvents = totalEvents + events;
                    		}
                        	data1.add(totalConnection);
                			data2.add(totalComplete);
                			data3.add(totalInteraction);
                			data4.add(totalEvents);
                			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
                			
                			chartDataDayTotal = new ChartDataDayTotal();
                			chartDataDayTotal.setTotalConnection(totalConnection);
                			chartDataDayTotal.setTotalComplete(totalComplete);
                			chartDataDayTotal.setTotalInteraction(totalInteraction);
                			chartDataDayTotal.setTotalEvents(totalEvents);
                			chartDataDayTotal.setServerName(selectServerType);
                			chartDataDayTotal.setServerCategory(selectServerCategory);
                			chartDataDayTotal.setDay(String.valueOf(i+1));
                			chartDataDayTotal.setMonth(String.valueOf(Integer.parseInt(month)+1));
                			chartDataDayTotal.setYear(year);
                			
                			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
                			Date dateTransfer = sdf.parse(year+"-"+(Integer.parseInt(month)+1)+"-"+(i+1));
                			Calendar calendar = Calendar.getInstance();
                			calendar.setTime(dateTransfer);
                			chartDataDayTotal.setWeekDay(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
                			chartDataDayTotal.setWeekInYear(String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)));
                			chartDataDayTotalService.save(chartDataDayTotal);
                		}
                		subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
                		graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        			
        		}
        	}
        }else if(!"".equals(selectChartType)&& "month".equals(selectChartType)){ 
        	Calendar today = Calendar.getInstance();
        	today.setTime(new Date());
        	if(Integer.parseInt(year) == today.get(Calendar.YEAR) && Integer.parseInt(month) == today.get(Calendar.MONTH)){//请求的这天所在的月和当天所在的月一致
        		if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
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
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
                	map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        data.add(map1);  
        	        data.add(map2);  
        		}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        			int connectiontotal = 0;
            		int completetotal = 0;
            		int interactiontotal = 0;
            		int eventstotal = 0;
            		JSONObject jsonObject;
                	for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
                		url = selectServerTypeValue+"&year="+ year +"&month="+month+"&day="+(i+1);
                    	jsonresponse = loadJSON(url);
                    	try {
                			jsonObject = new JSONObject(jsonresponse);
                			connectiontotal = jsonObject.getInt("connections");
                			completetotal =	jsonObject.getInt("completes");
                			interactiontotal = jsonObject.getInt("interaction");
                			eventstotal = jsonObject.getInt("events");
                		} catch (JSONException e) {
                			interactiontotal = 0;
                			eventstotal = 0;
                		}
                    	data1.add(connectiontotal);
            			data2.add(completetotal);
            			data3.add(interactiontotal);
            			data4.add(eventstotal);
            			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
                	}
                	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
                	graph = "line";
        			map1 = combinationSeriesData("connection", data1);  
        	        map2 = combinationSeriesData("complete", data2);
        	        map3 = combinationSeriesData("interaction", data3);
        	        map4 = combinationSeriesData("events", data4);
        	        data.add(map1);  
        	        data.add(map2);  
        	        data.add(map3);
        	        data.add(map4);
        		}
        	}else{//请求的这天所在的月和当天所在的月不一致
        		if(chartDataDayService.serverMonthDataExist(year, String.valueOf((Integer.parseInt(month)+1)), selectServerType)){
        			if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
        				chartDataDays = chartDataDayService.queryByYearMonthServerName(year, String.valueOf((Integer.parseInt(month)+1)), selectServerType);
            			for(ChartDataDay chartDataDay : chartDataDays){
            				data1.add(chartDataDay.getConnection());
                			data2.add(chartDataDay.getComplete());
                			date.add(chartDataDay.getMonth()+"-"+chartDataDay.getDay());
            			}
            			subTitle = year+"-"+String.valueOf((Integer.parseInt(month)+1))+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);
        			}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        				chartDataDays = chartDataDayService.queryByYearMonthServerName(year, String.valueOf((Integer.parseInt(month)+1)), selectServerType);
            			for(ChartDataDay chartDataDay : chartDataDays){
            				data1.add(chartDataDay.getConnection());
                			data2.add(chartDataDay.getComplete());
                			data3.add(chartDataDay.getInteraction());
                			data4.add(chartDataDay.getEvents());
                			date.add(chartDataDay.getMonth()+"-"+chartDataDay.getDay());
            			}
            			subTitle = year+"-"+String.valueOf((Integer.parseInt(month)+1))+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
            			graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        		}else{
        			if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiSay")){
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
                			
                			chartDataDay = new ChartDataDay();
                			chartDataDay.setConnection(connectiontotal);
                			chartDataDay.setComplete(completetotal);
                			chartDataDay.setInteraction(0);
                			chartDataDay.setEvents(0);
                			chartDataDay.setServerName(selectServerType);
                			chartDataDay.setServerCategory(chartDataServerService.getServerByServerName(selectServerType).getServerCategory());
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
                    	subTitle = year+"-"+(Integer.parseInt(month)+1)+"-"+day + "    [Monthly Chart]    Server : " +selectServerType;
                    	map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        data.add(map1);  
            	        data.add(map2);  
        			}else if(chartDataServerService.getServerByServerName(selectServerType).getServerCategory().equals("lumiJoin")){
        				int connectiontotal = 0;
                		int completetotal = 0;
                		int interactiontotal = 0;
                		int eventstotal = 0;
                		JSONObject jsonObject;
                    	for(int i=0; i<cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
                    		url = selectServerTypeValue+"&year="+ year +"&month="+month+"&day="+(i+1);
                        	jsonresponse = loadJSON(url);
                        	try {
                    			jsonObject = new JSONObject(jsonresponse);
                    			connectiontotal = jsonObject.getInt("connections");
                    			completetotal =	jsonObject.getInt("completes");
                    			interactiontotal = jsonObject.getInt("interaction");
                    			eventstotal = jsonObject.getInt("events");
                    		} catch (JSONException e) {
                    			interactiontotal = 0;
                    			eventstotal = 0;
                    		}
                        	data1.add(connectiontotal);
                			data2.add(completetotal);
                			data3.add(interactiontotal);
                			data4.add(eventstotal);
                			date.add((Integer.parseInt(month)+1)+"-"+(i+1));
                			
                			chartDataDay = new ChartDataDay();
                			chartDataDay.setConnection(connectiontotal);
                			chartDataDay.setComplete(completetotal);
                			chartDataDay.setInteraction(interactiontotal);
                			chartDataDay.setEvents(eventstotal);
                			chartDataDay.setServerName(selectServerType);
                			chartDataDay.setServerCategory(chartDataServerService.getServerByServerName(selectServerType).getServerCategory());
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
                    	graph = "line";
            			map1 = combinationSeriesData("connection", data1);  
            	        map2 = combinationSeriesData("complete", data2);
            	        map3 = combinationSeriesData("interaction", data3);
            	        map4 = combinationSeriesData("events", data4);
            	        data.add(map1);  
            	        data.add(map2);  
            	        data.add(map3);
            	        data.add(map4);
        			}
        			
        		}
        	}
        }
       
//        map1 = combinationSeriesData("connection", data1);  
//        map2 = combinationSeriesData("complete", data2);
//        map3 = combinationSeriesData("interaction", data3);
//        map4 = combinationSeriesData("events", data4);
//        
//        data.add(map1);  
//        data.add(map2);  
//        data.add(map3);
//        data.add(map4);
        
        map.put("categories",date);  
        map.put("series", data);  
        //设置图表的副标题
        map.put("subtitle", subTitle);
        //设置图表样式
        map.put("graph", graph);
        
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
        String errorJsonData = "{'status':'ok','connections':0,'completes':0, 'interaction':0, 'events':0}";
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(
            		new InputStreamReader(yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
        	return errorJsonData;
        }
        return json.toString();
    }
	//根据某一天获取这一天所在这个周的其他天数日期
	public static Date getDayOfWeek(int year, int weekInYear, int count){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, weekInYear);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, count);
		return calendar.getTime();
	}
}
