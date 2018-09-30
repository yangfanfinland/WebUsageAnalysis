<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>Privilege Set</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/FoshanRen.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="privilegeGroupSet" namespace="/control/employee" method="post">
<s:hidden name="username"/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="1491c6">
    	<td > 
    		<font color="#FFFFFF">Select Privilege Group User Belongs to</font>
    	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td>
      		<c:forEach items="${groups}" var="privilegegroup" varStatus="statu">
      			<input type="checkbox" name="groupids" value="${privilegegroup.groupid}"
      			<c:forEach items="${usergroups }" var="ug">
      				<c:if test="${ug==privilegegroup}">checked</c:if>
      			</c:forEach>
      			>${privilegegroup.name}
      			<c:if test="${statu.count%8==0}"><br></c:if>
      		</c:forEach>
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