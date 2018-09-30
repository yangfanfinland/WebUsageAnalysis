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
<title>Branch company list view</title>
</head>
<body>
<s:form action="list" namespace="/control/branch" method="post">
<s:hidden name="page"/>
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr >
    	<td colspan="4" bgcolor="1491c6" align="right">
    	<%@ include file="/WEB-INF/page/share/fenye.jsp" %>
   		</td>
   	</tr>
    <tr>
      <td width="30%" bgcolor="1491c6"> <div align="center"><font color="#FFFFFF">No.</font></div></td>
      <td width="8%" nowrap bgcolor="1491c6"> <div align="center"><font color="#FFFFFF">Modify</font></div></td>
      <td bgcolor="1491c6"> <div align="center"><font color="#FFFFFF">Branch Company Name</font></div></td>
      <td width="10%" bgcolor="1491c6"><div align="center"><font color="#FFFFFF">Delete</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<c:forEach items="${pageView.records}" var="entry">
    <tr>
      <td bgcolor="f5f5f5"> <div align="center">${entry.branchid}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
      <!-- 
      <itcast:permission module="department" privilege="update">
       -->
      <a href='<s:url action="branch_editUI" namespace="/control/branch" includeParams="none"/>?branchid=${entry.branchid}'>
	  <img src="<%=request.getContextPath() %>/images/edit_25.png" width="25" height="25" border="0"></a>
	  <!-- 
	  </itcast:permission>
	  -->
	  </div></td>
      <td bgcolor="f5f5f5"> <div align="center">${entry.name}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
      <!-- 
         <a href='<s:url action="delete_branch" namespace="/control/branch" includeParams="none"/>?branchid=${entry.branchid}'>Delete</a>
       -->
     <a href='<s:url action="delete_branch" namespace="/control/branch" includeParams="none"/>?branchid=${entry.branchid}'>
	  	<img src="<%=request.getContextPath() %>/images/org_delete_25.png" width="25" height="25" border="0">
	 </a>
     
     
      </div></td>
	</tr>
</c:forEach>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="4" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
              <td width="85%">
              <!-- 
              <itcast:permission module="department" privilege="insert">
              -->
              <input type="button" value="Add Branch Company" class="frm_btn" onClick="javascript:window.location.href='<s:url action="branch_addUI" namespace="/control/branch" includeParams="none"/>'"> &nbsp;&nbsp;
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