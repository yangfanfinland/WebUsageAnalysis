<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>File Upload</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css"/>
<script type="text/javascript">
	function checkfm(form){
		var uploadfile = form.uploadfile.value;
		if(uploadfile != 0){
			var ext = uploadfile.substring(uploadfile.length-3).toLowerCase();
			var types = ["jpg","gif","bmp","png","exe","doc","pdf","txt","xls","ppt","swf"];
			var sing = false;
			for(var i=0; i<types.length;i++){
				if(ext==types[i]){
					sing = true;
				}
			}
			if(!sing){
				alert("Only allow upload /flash/word/exe/pdf/txt/xls/ppt");
				return false;
			}
		}
		
		return true;
	}
</script>
</head>	
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="upload" namespace="/control/uploadfile" method="post" enctype="multipart/form-data" onsubmit="return checkfm(this)">

<s:hidden name="parentid"/>

<br/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="1491c6">
    	<td colspan="2"  > 
    		<font color="#FFFFFF">Upload File</font>
        </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      	<td width="22%" >
        	<div align="right">File:</div>
       	</td>
      	<td width="78%">
      		 
      		 <input type="file" name="uploadfile" size="50"/><br/>
      		 Only allow upload /flash/word/exe/pdf/txt/xls/ppt
      		  <!--
      		 <s:file name="logofile" size="80"/>
      		 -->
        </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      	<td colspan="2"> <div align="center"> 
          <input type="submit" name="SYS_SET" value=" Confirm " class="frm_btn"/>
        </div></td>
    </tr>
  </table>
<br/>
</s:form>
</body>
</html>