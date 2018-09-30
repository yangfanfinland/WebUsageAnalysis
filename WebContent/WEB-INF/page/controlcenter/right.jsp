<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/styles.css" type="text/css">

<!-- 
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/main.css" type="text/css">
 -->

<style type=text/css>
	.cs {
  		border: 2px solid #cfd8e2;
  		border-radius: 6px;
  		color: #333333;
  		background-color: #fff;
  		padding: 7px 5px 7px 5px;
  		margin-left: 5px;
  		margin-right: 5px;
  		height: 38px;
  		transition: all 0.3s ease;
  		-webkit-transition: all 0.3s ease;
  		-ms-transition: all 0.3s ease;
  		-moz-transition: all 0.3s ease;
  		-o-transition: all 0.3s ease; }
 	.cs.invalid {
    	border-color: #ffd200;
    	background-color: #fffbc2;
    	transition: all 0.3s ease;
    	-webkit-transition: all 0.3s ease;
    	-ms-transition: all 0.3s ease;
    	-moz-transition: all 0.3s ease;
    	-o-transition: all 0.3s ease; }
	.cs.nlm {
    	margin-left: 0; }
	.cs.nrm {
    	margin-right: 0; }
	.cs.centered {
    	text-align: center; }
	.cs.search {
    	margin-left: 0;
    	margin-right: 0;
    	border-right: none;
    	border-top-right-radius: 0;
    	border-bottom-right-radius: 0; }
	.cs:disabled {
    	opacity: 0.6; }
  	.cs.focused {
    	transition: all 0.3s ease;
    	-webkit-transition: all 0.3s ease;
    	-ms-transition: all 0.3s ease;
    	-moz-transition: all 0.3s ease;
    	-o-transition: all 0.3s ease; }
	.cs.dpicker {
    	padding-right: 70px;
    	background-image: url("/WebUsageAnalysis2/images/skin/datepicker-sprites.png");
    	background-repeat: no-repeat;
    	background-position: 100% 3px;
    	cursor: pointer; }
	select.cs {
		font-size: 12px;
  		-webkit-appearance: none;
  		-moz-appearance: none;
  		text-indent: 10px;
  		-o-text-overflow: '';
  		-ms-text-overflow: '';
  		text-overflow: '';
  		appearance: none;
  		background-image: url("/WebUsageAnalysis2/images/skin/triangledown.png");
  		background-repeat: no-repeat;
  		background-position: 95% -68%;
  		min-width: 150px; }
	select.cs option:disabled {
    	color: #CFD7E1; }
    .btn {
  		border: 0;
  		border-radius: 6px;
  		background-color: #6f7d8d;
  		color: #fff;
  		margin-left: 5px;
  		margin-right: 5px;
  		cursor: pointer;
  		height: 38px;
  		padding-left: 14px;
  		padding-right: 14px;
  		font: inherit;
  		background-position: -500px;
  		background-image: url("/WebUsageAnalysis2/images/skin/btn-sprites.png");
  		background-repeat: no-repeat;
  		/* &:hover
    	{
  	  	background-color: #606B79;
  		} */ }
</style>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.blockUI.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>
<script src="https://rawgithub.com/highslide-software/export-csv/master/export-csv.js"></script>
<script>

setTimeout(function(){
	showChart();
	},
	60*60*1000);

$(function(){  
    $("input[type=button]").bind("click",showChart);  
});  

function changeValue(){
	var selectServerCategory = $("#serverCategory").val();
	
	$.ajax({  
        url:'testAction2?action=getSeverByCategory',  
        type:'post',
        dataType:'json',

        data:{
        	selectServerCategory: selectServerCategory
        	
        },  
        success:function(data){  
        	
        	//var jsonData = eval(data);  
        	$('#server').empty();
        	$('#server').prepend("<option value='all'>All Servers</option>");
   
        	/* $(jsonData).each(function(){
        		$('#server').append("<option value='"+ this.value+"'>"+ this.name +"</option>"); 
        		
        	}); */
        	$.each(data, function(i, item){
        		$('#server').append("<option value='"+ item.value+"'>"+ item.name +"</option>"); 
        		
        	});
        },
        error:function(){  
            alert('Error!');  
        }  
    });
}

//unblock when ajax activity stops 
$(document).ajaxStop($.unblockUI); 

var chart;  
function showChart(){  
	$.blockUI({ message: $('#domMessage') }); 
	var param = $("#datepicker")[0].value;
	var radioChart = $("#chart").val();
	//var radioServer = $("input[name='server']:checked").val();
	var radioServer = $("#server").val();
	
	var radioServerName = $("#server option:selected").text();
	
	var selectServerCategory = $("#serverCategory").val();
	
    $.ajax({  
        url:'testAction',  
        type:'post',  

        data:{
        	selectDate : param,
        	selectRadioChart: radioChart,
        	selectRadioServer: radioServer,
        	selectRadioServerName : radioServerName,
        	selectServerCategory: selectServerCategory
        },
        
        dataType:'json',  
        success:function(data){  
            var json = eval(data);  
            if(json!="" && json!=null){  
                chart = new Highcharts.Chart({  
                    chart: {  
                        renderTo: 'container',  
                        type: json.graph 
                    },  
                    title: {  
                        text: 'Relative Data Analysis and Report '
                    },  
                    subtitle: {  
                        text: json.subtitle,
                        x: 10
                    }, 
                    credits: {
                        enabled: false
                    },
                    xAxis: {  
                        categories: json.categories  
                    },  
                    yAxis: {  
                        title: {  
                            text: 'Amount'  
                        },  
                        min:0  
                    },  
                    series: json.series  
                });  
            }  
        },  
        error:function(){  
            alert('Error!');  
        }  
    });
    
    $(document).ready(function() { 
        $('#pageDemo1').click(function() { 
            $.blockUI(); 
            test(); 
        }); 
        $('#pageDemo2').click(function() { 
            $.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' }); 
            test(); 
        }); 
        $('#pageDemo3').click(function() { 
            $.blockUI({ css: { backgroundColor: '#f00', color: '#fff' } }); 
            test(); 
        }); 
 
        $('#pageDemo4').click(function() { 
            $.blockUI({ message: $('#domMessage') }); 
            test(); 
        }); 
    }); 
}
</script>

<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="<%=request.getContextPath() %>/js/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
	$(function() {
	$( "#datepicker" ).datepicker();
	});
</script>

<style type="text/css">
    #bodyheader{
     width:750px;height:200px;
     }
    #bodyheader_left{
    width: 250px;height:170px;float: left;
    }
    #bodyheader_middle{
    width: 180px;height:200px;float: left;
    }
    #bodyheader_middle2{
    width: 180px;height:200px;float: left;
    }
    #bodyheader_right{
    width: 130px;height:200px;float: left;
    }
</style>
<title>Untitled Document</title>
</head>

<body onload="" id="body">


<div id="body_main">
	<div id="bodyheader">
		<div id="bodyheader_left" > 
			Date : <br/><br/>
			<input id ="datepicker" class="cs dpicker" type="text" name="date" size="20" readonly="readonly" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())%>"/><br/><br/>
		</div>
		<div id="bodyheader_middle">
			 <font size="2">Chart Category: </font><br/><br/>
			<select name="chart" id="chart" class="cs">
				<option value="month">Monthly</option>
				<option value="week">Weekly</option>
				<option value="day" selected="selected">Daily</option>
			</select>
		</div>
		
		<div id="bodyheader_middle2">
			<font size="2">Server Category: </font><br/><br/>
			<select name="serverCategory" id="serverCategory" onchange="changeValue()" class="cs nrm">
				<option value="all" selected="selected">All Category</option>
				<option value="lumiSay">Lumi Say</option>
				<option value="lumiJoin">Lumi Join</option>
			</select>
			<br/><br/><br/><br/>
		</div>
	
		
		<div id="bodyheader_right">
			<font size="2">Server Name: </font><br/><br/>
			<select name="server" id="server" class="cs">
				<option value='all'>All Servers</option>	
				<c:forEach items="${pageView.records}" var="server" varStatus="statu">
					<option value='${server.serverURLAddress}'>${server.serverName}</option>
				</c:forEach>
			</select>
			<input class="btn" name="query" type="button" value="Query" id="button" style="margin-left: 80px; margin-top: 60px;"/>
		</div>
	</div>
</div>
<div id="domMessage" style="display:none;"> 
    <font size="3">I am processing your request.  Please be patient.</font> 
</div> 
<div id="container" style="height:480px;width:750px;background-color:#efefef;"> </div>

</body>
</html>