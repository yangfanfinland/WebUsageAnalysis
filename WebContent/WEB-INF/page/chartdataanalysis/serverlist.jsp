<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<script type="text/javascript">
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.page.value=page;
		form.submit();
	}
</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/FoshanRen.js"></script>
<title>Server list view</title>
</head>
<body>
<s:form action="list" namespace="/control/analysis" method="post">
<s:hidden name="page"/>
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr >
    	<td colspan="6" bgcolor="1491c6" align="right">
    	<%@ include file="/WEB-INF/page/share/fenye.jsp" %>
   		</td>
   	</tr>
    <tr>
      <td width="30%" bgcolor="1491c6"> <div align="center"><font color="#FFFFFF">No.</font></div></td>
      <td width="8%" nowrap bgcolor="1491c6"> <div align="center"><font color="#FFFFFF">Modify</font></div></td>
      <td bgcolor="1491c6"> <div align="center"><font color="#FFFFFF">Sever Name</font></div></td>
      <td bgcolor="1491c6"> <div align="center"><font color="#FFFFFF">Sever URLAddress</font></div></td>
      <td bgcolor="1491c6"> <div align="center"><font color="#FFFFFF">Sever Category</font></div></td>
      <td width="10%" bgcolor="1491c6"><div align="center"><font color="#FFFFFF">Delete</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<c:forEach items="${pageView.records}" var="entry">
    <tr>
      <td bgcolor="f5f5f5"> <div align="center">${entry.serverID}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
      <!-- 
      <itcast:permission module="department" privilege="update">
       -->
      <a href='<s:url action="server_editUI" namespace="/control/analysis" includeParams="none"/>?serverID=${entry.serverID}'>
	  <img src="<%=request.getContextPath() %>/images/edit_25.png" width="25" height="25" border="0"></a>
	  <!-- 
	  </itcast:permission>
	  -->
	  </div></td>
      <td bgcolor="f5f5f5"> <div align="center">${entry.serverName}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">${entry.serverURLAddress}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">${entry.serverCategory}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
		<!-- 
         <a href='<s:url action="delete_server" namespace="/control/analysis" includeParams="none"/>?serverID=${entry.serverID}'>Delete</a>
		 -->
	 <a href='<s:url action="delete_server" namespace="/control/analysis" includeParams="none"/>?serverID=${entry.serverID}'>
	  	<img src="<%=request.getContextPath() %>/images/org_delete_25.png" width="25" height="25" border="0">
	 </a>
		
      </div></td>
	</tr>
</c:forEach>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="6" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
              <td width="85%">
              <!-- 
              <itcast:permission module="department" privilege="insert">
              -->
              <input type="button" value="Add Data Server" class="frm_btn" onClick="javascript:window.location.href='<s:url action="server_addUI" namespace="/control/analysis" includeParams="none"/>'"> &nbsp;&nbsp;
           	  <!-- 
           	  </itcast:permission>
            -->
            </td>
            
          </tr>
        </table></td>
    </tr>
  </table>
</s:form>
</body>
</html>