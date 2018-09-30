<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>Employee Query</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/FoshanRen.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="list" namespace="/control/employee" method="post">
<input type="hidden" name="query" value="true">
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="1491c6">
    	<td colspan="2"  > 
    	<font color="#FFFFFF">Query Condition</font>
    </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">User name:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="username" size="30" maxlength="20"/>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Real Name:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="realname" size="15" maxlength="10"/>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Branch Company:</div>
      	</td>
      	<td width="78%"> 
      		<select name="branchid">
      			<option value="">Please select branch</option>
      				<c:forEach items="${branches }" var="branch">
      					<option value="${branch.branchid}">${branch.name}</option>
      				</c:forEach>
      		</select>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td colspan="2"> 
      		<div align="center"> 
          	<input type="submit" name="SYS_SET" value=" Confirm " class="frm_btn">
        	</div>
        </td>
    </tr>
  </table>
</s:form>
<br>
</body>
</html>