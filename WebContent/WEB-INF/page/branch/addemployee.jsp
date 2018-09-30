<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>Add Employee</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/FoshanRen.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/xmlhttp.js"></script>
<script type="text/javascript">
function validateUsernameExsit(username){
	if(username.trim()!=""){
		var usernameview = document.getElementById('usernameview');		
		if(usernameview){
			send_request(function(value){usernameview.innerHTML=value},
					 '<s:url action="exist" namespace="/control/employee" includeParams="none"/>?username='+ username, true);
		}
	}
}

function Formfield(name, label){
	this.name=name;
	this.label=label;
}
function checkfm(form){
	var list  = new Array(new Formfield("username", "Login Account"),new Formfield("password", "Login Password"),
	new Formfield("realname", "Employee Name"),new Formfield("cardno", "Id Number")
	,new Formfield("birthday", "Birthday"),new Formfield("address", "Address"),new Formfield("phone", "Telephone"));
	for(var i=0;i<list.length;i++){
		var objfield = eval("form."+ list[i].name);
		if(trim(objfield.value)==""){
			alert(list[i].label+ " can't be empty");
			if(objfield.type!="hidden" && objfield.focus()) objfield.focus();
			return false;
		}
	}
	if(form.username.value.length<6){
		alert("login account length bigger than 6");
		return false;
	}
	if(form.password.value.length<6){
		alert("password length bigger than 6");
		return false;
	}
	if(form.password.value!=form.password2.value){
		alert("Password can't match");
		return false;
	}
	if(form.cardno.value.length!=11){
		alert("Format like: 130888-2077");
		return false;
	}

	var picture = form.picture.value;
	if(trim(picture)!=""){
		var ext = picture.substring(picture.length-3).toLowerCase();
		if (ext!="jpg" && ext!="gif" && ext!="bmp" && ext!="png"){
			alert("only allow upload gif、jpg、bmp、png！");
			return false; 
		}
	}
	return true;
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="regEmployee" namespace="/control/employee" enctype="multipart/form-data" method="post" onsubmit="return checkfm(this)">
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="1491c6">
    	<td colspan="2"  > 
    		<font color="#FFFFFF">Add Employee</font>
    	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Login Account:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="username" size="32" maxlength="32" onblur="javascript:validateUsernameExsit(this.value)">(account length bigger than 6)
      		<font color="#FF0000">*</font>
       		<div id="usernameview"></div>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Login Password:</div>
      	</td>
      	<td width="78%">
      		<input type="password" name="password" size="32" maxlength="32">(password length bigger than 6)
        	<font color="#FF0000">*</font>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
    	<td width="22%" > 
    		<div align="right">Repeat Password:</div>
    	</td>
      	<td width="78%"> 
      		<input type="password" name="password2" size="32" maxlength="32"/>
        	<font color="#FF0000">*</font>
        </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Employee Name:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="realname" size="10" maxlength="10">
        	<font color="#FF0000">*</font>
      	</td>
    </tr>

	<tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Employee Photo:</div>
      	</td>
      	<td width="78%"> 
      		<input type="file" name="picture" size="50">
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
    	<td width="22%" > 
    		<div align="right">Id Number:</div>
    	</td>
      	<td width="78%"> 
      		<input type="text" name="cardno" size="20" maxlength="18">
        	<font color="#FF0000">*</font>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
     	<td width="22%" > 
     		<div align="right">Birthday:</div>
     	</td>
      	<td width="78%"> 
      		<input type="text" name="birthday" size="20" maxlength="18">
        	<font color="#FF0000">*</font>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Address:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="address" size="60" maxlength="100">
        	<font color="#FF0000">*</font>
      	</td>
    </tr>  
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Telephone:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="phone" size="20" maxlength="18">
      		<font color="#FF0000">*</font>
    	</td>
    </tr> 
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Email:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="email" size="20" maxlength="40"/>
      	</td>
    </tr> 
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Degree:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="degree" size="10" maxlength="20"/>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">School:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="school" size="20" maxlength="40"/>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Branch Company:</div>
      	</td>
      	<td><!-- 每8个部门,用<br>进行分行 -->
      		<c:forEach items="${branches}" var="branch" varStatus="statu">      
      			<input type="radio" name="branchid" value="${branch.branchid}">${branch.name}	
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