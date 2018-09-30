<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>Modify Privilege Group</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/FoshanRen.js"></script>
<script type="text/javascript">
function checkfm(form){
	if (trim(form.name.value)==""){
		alert("privilege group name can't be empty!");
		form.name.focus();
		return false;
	}
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="edit" namespace="/control/privilegegroup" method="post" onsubmit="return checkfm(this)">
<s:hidden name="groupid"/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="1491c6">
    	<td colspan="2"  > <font color="#FFFFFF">Modify Privilege Group</font>
    </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" >
      		 <div align="right">Group Name:</div>
      	</td>
      	<td width="78%"> 
      		<s:textfield name="name" size="20" maxlength="20">
        	<font color="#FF0000">*</font>
        	</s:textfield>
        </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Select Privilege:</div>
      	</td>
      	<td width="78%"> 
      		<c:forEach items="${privileges}" var="privilege" varStatus="statu">
				<input type="checkbox" name="privileges" value="${privilege.id.module},${privilege.id.privilege}"
				<c:forEach items="${selectprivileges}" var="sp">
					<c:if test="${sp==privilege}">checked</c:if>
				</c:forEach>
				>${privilege.name}　
				<c:if test="${statu.count%4==0}"><br></c:if>
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