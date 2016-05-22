<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="icon" type="image/png" href="resources/images/favicon.png" />
<link href="resources/css/_styles.css" rel="stylesheet" type="text/css" />
<link href="resources/css/jquery-ui-1.9.2.custom.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/admin-app.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/data_table.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" charset="utf-8"
	src="resources/js/jquery-1.9.0.js"></script>
<script type="text/javascript" charset="utf-8"
	src="resources/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8"
	src="resources/js/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" charset="utf-8"
	src="resources/js/admin-app.js"></script>

<title>dev Admin</title>

<!-- Then the pages head -->
<decorator:head />
<!-- End pages head -->
</head>
<body>
	<div class="masthead">
		<div class="mastheadLogo">
			<img src="resources/images/example.png" width="54" height="54"
				alt="example" border="0" />
		</div>
		<h1>dev Admin</h1>
	</div>
	<div id="dev-selection-header" class="dev-selection-header">
		<div style="float: right; display: inline-block;">
			<div>
				<%
				    if (nz.co.example.dev.integration.CannedNNCServiceImpl.getInstance().isAvailable()) {
				%>
				The NNC server is up. <img
					src="${pageContext.request.contextPath}/resources/css/images/checked.gif" />
				<%
				    } else {
				%>
				The NNC server is down. <img
					src="${pageContext.request.contextPath}/resources/css/images/unchecked.gif" />
				<%
				    }
				%>
				<sec:authorize access="isAuthenticated()">
					<div style="float: right">

						You are logged in as&nbsp;userId ${userId}
						<sec:authentication property="principal.username" />
						<!-- 
				<a href="j_security_logout">Logout</a>
				 -->
					</div>
				</sec:authorize>

				<%
				    if (null == request.getUserPrincipal()) {
				%>
				Not logged in. <a class="btnDark" style="margin-top: 3px;"
					id="log_in" href="login.jsp">Log In</a>
				<%
				    } else {
				%>
				User:
				<%=request.getUserPrincipal().getName()%>
				<a href="${pageContext.request.contextPath}/logout.jsp">Log Out</a>
				<a class="btnDark" style="margin-top: 3px;" id="add"
					href="j_security_logout">Log Out</a> <a href="logout.jsp">Log
					Out</a>
				<%
				    }
				%>
			</div>
		</div>
	</div>

	<!-- Decorated pages body.  -->
	<decorator:body />
	<!-- End pages body.  -->
	<div
		style="display: block; position: fixed; width: 100%; height: 40px; padding: 0px; bottom: 10px;">
		<h6><%=nz.co.example.dev.Version.version%></h6>
	</div>
</body>
</html>