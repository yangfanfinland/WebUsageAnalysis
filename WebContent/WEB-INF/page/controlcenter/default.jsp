<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<html>
<head>
<link href="<%=request.getContextPath() %>/css/styles.css" rel="stylesheet" type="text/css" />
<title>Lumiinsight Web Usage Analysis Platform</title>
</head>
<body>
	<div id="top">
    	<iframe src="<s:url action='top' namespace="/control/center"/>" name="top" width="980" height="60" frameborder="0" scrolling="no">
        </iframe>
    </div>
    <div id="middle">
        <div id="menu">
            <iframe src="<s:url action='menu' namespace="/control/center" />" name="menu" width="200" height="760" frameborder="0" scrolling="auto">
            </iframe>
        </div>
        <div id="right">
            <iframe src="<s:url action='right' namespace="/control/center" />" name="right" width="780" height="760" frameborder="0" scrolling="auto">
            </iframe>
        </div>
    </div>
    <div id="end">
    	<iframe src="<s:url action='end' namespace="/control/center" />" name="end" width="980" height="15" frameborder="0" scrolling="no">
        </iframe>
    </div>
</body>
</html>
