 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>Upload File List</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/vip.css" type="text/css">
<script type="text/javascript">
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.page.value=page;
		form.submit();
	}
	//全选功能
	function allselect(allobj, items){
		var state = document.forms[0].select.checked;
		//alert(state);
		if(items.length){
			for(var i=0;i<items.length;i++){
				if(!items[i].disabled)
					items[i].checked=state;
			}
		}else{
			if(!items.disabled)
				items.checked=states;
		}
	}
	
	function deleteFiles(objform){
		objform.action='<s:url action="delete" namespace="/control/uploadfile"/>';
		objform.submit();
	}
	
</script>
</head>
<body>
<s:form action="list" namespace="/control/uploadfile" method="post" >
<s:hidden name="page"/>
	<table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">

    	<tr >
        	<td colspan="4"  bgcolor="1491c6" align="right">
        		<%@ include file="/WEB-INF/page/share/fenye.jsp" %>
   			</td>
   		</tr>
        <tr>
        	<td width="8%" bgcolor="1491c6"> 
            	<div align="center"><font color="#FFFFFF">Select</font></div>
            </td>
            <td width="10%" bgcolor="1491c6"> 
            	<div align="center"><font color="#FFFFFF">No.</font></div>
            </td>
            <td width="60%" bgcolor="1491c6">
            	<div align="center"><font color="#FFFFFF">File</font></div>
            </td>
            <td width="22%" nowrap bgcolor="1491c6">
            	<div align="center"><font color="#FFFFFF">Upload Time</font></div>
            </td>
    	</tr>
    	<!-- Loop Start -->
    	<c:forEach items="${pageView.records}" var="uploadfile">
	        <tr>
	        	<td bgcolor="f5f5f5"> 
	            	<div align="center"><input type="checkbox" name="fileids" value="${uploadfile.id}"/></div>
	            </td>
	      		<td bgcolor="f5f5f5"> 
	            	<div align="center">${uploadfile.id}</div>
	            </td>
	      		<td bgcolor="f5f5f5">
	            	<div align="center">
	            		<a href="<%=request.getContextPath() %>${uploadfile.filepath}" target="_blank">${uploadfile.filepath}</a>
	            	</div>
	            </td>
		  		<td bgcolor="f5f5f5"> 
	            	<div align="center">${uploadfile.uploadtime}</div>
	            </td>
			</tr>
		</c:forEach>
		<!-- Loop end -->
    <tr> 
      <td bgcolor="f5f5f5" colspan="4" align="center">
      	<table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="10%"><input type="checkbox" name="select" onclick="javascript:allselect(this, this.form.fileids)"/>All</td>
              <td width="85%">
              <input type="button" class="frm_btn" onClick="javascript:deleteFiles(this.form)" value="Delete"/> &nbsp;&nbsp;
            </td>
          </tr>
        </table>
      </td>
    </tr>
    </table>
</s:form>
</body>
</html>