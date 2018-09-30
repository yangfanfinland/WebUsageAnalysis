<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>Add Server</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/FoshanRen.js"></script>
<script type="text/javascript">
function checkfm(form){
	if (trim(form.serverName.value)==""){
		alert("Server name can't be empty！");
		form.serverName.focus();
		return false;
	}
	if (trim(form.serverURLAddress.value)==""){
		alert("Server URL Address can't be empty！");
		form.serverURLAddress.focus();
		return false;
	}
	if (trim(form.serverCategory.value)==""){
		alert("Server Category can't be empty！");
		
		return false;
	}
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="add_server" namespace="/control/analysis" method="post" onsubmit="return checkfm(this)">
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="1491c6"><td colspan="2"  > <font color="#FFFFFF">Add Server:</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > 
      	<div align="right">Server Name:</div>
      </td>
      <td width="78%"> 
       	<input type="text" name="serverName" size="40" maxlength="20"/>
       	<font color="#FF0000">*</font>
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > 
      	<div align="right">Server URLAddress:</div>
      </td>
      <td width="78%"> 
       	<input type="text" name="serverURLAddress" size="40" maxlength="200"/> 	
       	<font color="#FF0000">*</font>
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > 
      	<div align="right">Server Category:</div>
      </td>
      <td width="78%"> 
      	<font color="#FF0000">*</font>
       	<input type="radio" name="serverCategory" value="lumiSay"/>Lumi Say
       	<input type="radio" name="serverCategory" value="lumiJoin"/>Lumi Join
       	
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="SYS_SET" value=" Confirm " class="frm_btn">
        </div></td>
    </tr>
  </table>
</s:form>
<br>
</body>
</html>