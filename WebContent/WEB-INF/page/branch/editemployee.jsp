<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>Modify Employee Info</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/FoshanRen.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/xmlhttp.js"></script>
<script type="text/javascript">
function Formfield(name, label){
	this.name=name;
	this.label=label;
}
function checkfm(form){
	var list  = new Array(new Formfield("realname", "Employee Name"),new Formfield("cardno", "Id Number")
	,new Formfield("birthday", "Birthday"),new Formfield("address", "Address"),new Formfield("phone", "Telephone"));
	for(var i=0;i<list.length;i++){
		var objfield = eval("form."+ list[i].name);
		if(trim(objfield.value)==""){
			alert(list[i].label+ "can't be empty");
			if(objfield.type!="hidden" && objfield.focus()) objfield.focus();
			return false;
		}
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
<s:form action="editEmployee" namespace="/control/employee" method="post" enctype="multipart/form-data" onsubmit="return checkfm(this)">
<s:hidden name="username"/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="1491c6">
    	<td colspan="2"  > 
    		<font color="#FFFFFF">Modify Employee Info</font>
    	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
    	<td width="22%" > 
    		<div align="right">Employee Name:</div>
    	</td>
      	<td width="78%"> 
      		<input type="text" name="realname" size="10" maxlength="10" value="${realname }"/>
        	<font color="#FF0000">*</font>
        </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
     	<td width="22%" > 
     		<div align="right">Employee Photo</div>
     	</td>
      	<td width="78%"> 
      		<input type="file" name="picture" size="50">
     		<c:if test="${!empty imagePath}">
     			<img src="<%=request.getContextPath() %>${imagePath}" border="0" height="30">
     		</c:if> 
     	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Id Number:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="cardno" size="20" maxlength="18" value="${cardno }"/>
        	<font color="#FF0000">*</font>
        </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Birthday:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="birthday" size="20" maxlength="18" value="${birthday }"/>
        	<font color="#FF0000">*</font>
        </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Address:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="address" size="60" maxlength="100" value="${address }"/>
        	<font color="#FF0000">*</font>
        </td>
    </tr>  
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Telephone:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="phone" size="20" maxlength="18" value="${phone }"/>
      		<font color="#FF0000">*</font>
      	</td>
    </tr> 
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Email:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="email" size="40" maxlength="40" value="${email }"/>
      	</td>
    </tr> 
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Degree:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="degree" size="10" maxlength="20" value="${degree }"/>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">School:</div>
      	</td>
      	<td width="78%"> 
      		<input type="text" name="school" size="20" maxlength="40" value="${school }"/>
      	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td width="22%" > 
      		<div align="right">Department:</div>
      	</td>
      	<td><!-- 每8个部门,用<br>进行分行 -->
      	<c:forEach items="${branches}" var="branch" varStatus="statu">      
      	<input <c:if test="${selectbranchid==branch.branchid}">checked</c:if> type="radio" name="branchid" value="${branch.branchid}">${branch.name}	
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