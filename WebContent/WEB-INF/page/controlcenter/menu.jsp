<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>Menu</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/styles.css" type="text/css">
<script type="text/javascript">
	function getTablesByStart(name){
		var inputs = document.getElementsByTagName("table");
		var files = new Array();
		var y = 0;
		for (var i=0; i<inputs.length; i++) {		
		  if (inputs[i].id !=null && inputs[i].id.length>name.length && inputs[i].id.substring(0, name.length)==name){
			 files[y] = inputs[i];
			 y++;
		  }
		}
		return files;
	}
	function HideAll(){
		var tables = getTablesByStart("menu_");
		for (var i=0; i<tables.length; i++) {
			tables[i].style.display = "none";
			var id = tables[i].id.substring("menu_".length);
			var imgId = document.getElementById("Img"+ id);
			var imgId2 = document.getElementById("Img"+ id + "_0");
			if(imgId) imgId.src="<%=request.getContextPath() %>/images/midclosedfolder.gif";
			if(imgId2) imgId2.src="<%=request.getContextPath() %>/images/folder_18.png";
		}
	}
	
	function exitsystem() {
		if (confirm('Confirm Exit System?'))
			window.parent.location = '<s:url action="logout" namespace="/employee" includeParams="none"/>';
	}

	function turnit(id) {
		var menu = document.getElementById("menu_"+ id);
		var imgId = document.getElementById("Img"+ id);
		var imgId2 = document.getElementById("Img"+ id + "_0");
		if (menu.style.display=="none"){
			HideAll();
			menu.style.display = "";
			if(imgId) imgId.src="<%=request.getContextPath() %>/images/midopenedfolder.gif";
			if(imgId2) imgId2.src="<%=request.getContextPath() %>/images/openfolder_18.png";
		}else{
			menu.style.display = "none";
			if(imgId) imgId.src="<%=request.getContextPath() %>/images/midclosedfolder.gif";
			if(imgId2) imgId2.src="<%=request.getContextPath() %>/images/folder_18.png";
		}
}
</script>
</head>
<body>

    <!-- Usage Analysis Management start -->
    <table border="0" width="98%" align="center" cellspacing="0" cellpadding="0">
    	<tr> 
            <td colspan="3" onMouseUp="turnit('analysis')" style="CURSOR: hand"> 
              <img id="Imganalysis" src="<%=request.getContextPath() %>/images/midclosedfolder.gif" align="absMiddle" border="0" width="16">
              <img id="Imganalysis_0" src="<%=request.getContextPath() %>/images/folder_18.png" align="absMiddle" border="0"> 
                <span>Analysis Management</span>
            </td>
  		</tr>
    </table>
    <table id="menu_analysis" border="0" width="98%" align="center" cellspacing="0" cellpadding="0" style="display:none">
        <tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/vertline.gif" border=0>
                <img src="<%=request.getContextPath() %>/images/lastnodeline.gif" align="absMiddle" border=0 width="16" height="22">
                <img src="<%=request.getContextPath() %>/images/document_18.png" align="absMiddle" border="0" width="16" height="16">
            </td>
        	<td>
        		<a href="<s:url action='right' namespace='/control/center' />" target="right">Analysis Chart</a>
            </td>
        </tr>
        <tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/vertline.gif" border=0>
                <img src="<%=request.getContextPath() %>/images/lastnodeline.gif" align="absMiddle" border=0 width="16" height="22">
                <img src="<%=request.getContextPath() %>/images/document_18.png" align="absMiddle" border="0" width="16" height="16">
            </td>
        	<td>
        		<a href="<s:url action='list' namespace='/control/analysis' />" target="right">Server Management</a>
            </td>
        </tr>
    </table>
    <!-- Usage Analysis Management end -->
    
    <!-- 文件管理start -->
    <table border="0" width="98%" align="center" cellspacing="0" cellpadding="0">
    	<tr> 
            <td colspan="3" onMouseUp="turnit('file')" style="CURSOR: hand"> 
              <img id="Imgfile" src="<%=request.getContextPath() %>/images/midclosedfolder.gif" align="absMiddle" border="0" width="16">
              <img id="Imgfile_0" src="<%=request.getContextPath() %>/images/folder_18.png" align="absMiddle" border="0"> 
                <span>File Management</span>
            </td>
  		</tr>
    </table>
    <table id="menu_file" border="0" width="98%" align="center" cellspacing="0" cellpadding="0" style="display:none">
    	<tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/vertline.gif" border=0>
                <img src="<%=request.getContextPath() %>/images/lastnodeline.gif" align="absMiddle" border=0 width="16" height="22">
                <img src="<%=request.getContextPath() %>/images/document_18.png" align="absMiddle" border="0" width="16" height="16">
            </td>
        	<td>
        		<a href="<s:url action='uploadpage' namespace='/control/uploadfile' />" target="right">Upload File</a>
            </td>
        </tr>
    	<tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/vertline.gif" border=0>
                <img src="<%=request.getContextPath() %>/images/lastnodeline.gif" align="absMiddle" border=0 width="16" height="22">
                <img src="<%=request.getContextPath() %>/images/document_18.png" align="absMiddle" border="0" width="16" height="16">
            </td>
        	<td>
        		<a href="<s:url action='list' namespace='/control/uploadfile' />" target="right">Upload Management</a>
            </td>
        </tr>
    </table>
    <!-- 文件管理end -->
    
    <!-- Privilege Management start -->
    <table border="0" width="98%" align="center" cellspacing="0" cellpadding="0">
    	<tr> 
            <td colspan="3" onMouseUp="turnit('department')" style="CURSOR: hand"> 
              <img id="Imgdepartment" src="<%=request.getContextPath() %>/images/midclosedfolder.gif" align="absMiddle" border="0" width="16">
              <img id="Imgdepartment_0" src="<%=request.getContextPath() %>/images/folder_18.png" align="absMiddle" border="0"> 
                <span>Privilege Management</span>
            </td>
  		</tr>
    </table>
    <table id="menu_department" border="0" width="98%" align="center" cellspacing="0" cellpadding="0" style="display:none">
        <tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/vertline.gif" border=0>
                <img src="<%=request.getContextPath() %>/images/lastnodeline.gif" align="absMiddle" border=0 width="16" height="22">
                <img src="<%=request.getContextPath() %>/images/document_18.png" align="absMiddle" border="0" width="16" height="16">
            </td>
        	<td>
        		<a href="<s:url action='list' namespace='/control/branch' />" target="right">Branch Company</a>
            </td>
        </tr>
        <tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/vertline.gif" border=0>
                <img src="<%=request.getContextPath() %>/images/lastnodeline.gif" align="absMiddle" border=0 width="16" height="22">
                <img src="<%=request.getContextPath() %>/images/document_18.png" align="absMiddle" border="0" width="16" height="16">
            </td>
        	<td>
        		<a href="<s:url action='list' namespace='/control/employee' />" target="right">Employee</a>
            </td>
        </tr>
        <tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/vertline.gif" border=0>
                <img src="<%=request.getContextPath() %>/images/lastnodeline.gif" align="absMiddle" border=0 width="16" height="22">
                <img src="<%=request.getContextPath() %>/images/document_18.png" align="absMiddle" border="0" width="16" height="16">
            </td>
        	<td>
        		<a href="<s:url action='query' namespace='/control/employee' />" target="right">Employee Query</a>
            </td>
        </tr>
        <tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/vertline.gif" border=0>
                <img src="<%=request.getContextPath() %>/images/lastnodeline.gif" align="absMiddle" border=0 width="16" height="22">
                <img src="<%=request.getContextPath() %>/images/document_18.png" align="absMiddle" border="0" width="16" height="16">
            </td>
        	<td>
        		<a href="<s:url action='list' namespace='/control/privilegegroup' />" target="right">Privilege Group</a>
            </td>
        </tr>
    </table>
    <!-- Privilege Management end -->
    
    <table border="0" width="98%" align="center" cellspacing="0" cellpadding="0">
    	<tr>
        	<td>
            	<img src="<%=request.getContextPath() %>/images/lastnodeline.gif" border="0">
            </td>
            <td>
            	<a href="javascript:exitsystem()" target="_parent" ><font color="ff9e15">Logout System</font></a>
            </td>
        <tr/> 
    </table>
<body>
</body>
</html>