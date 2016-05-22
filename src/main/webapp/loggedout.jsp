<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    org.springframework.security.core.context.SecurityContextHolder
					.getContext().setAuthentication(null);
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Logged Out</title>

<link href="resources/css/_styles.css" rel="stylesheet" type="text/css" />
<link href="resources/css/admin-app.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/jquery-ui-1.9.2.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" charset="utf-8"
	src="resources/js/jquery-1.9.0.js"></script>
<script type="text/javascript" charset="utf-8"
	src="resources/js/jquery-ui-1.9.2.custom.js"></script>
</head>

<body>
	<div id="content">
		<h2>Access Denied</h2>
		<p>
			<c:if test='${param["error"] == "alreadyLoggedIn"}'>
				<h4>You are already logged in.</h4>
			</c:if>
			<c:if test='${param["error"] == "authenticationError"}'>
				<h4>You already have a session running.</h4>
			</c:if>
			<c:if test='${param["error"] == "sessionExpired"}'>
				<h4>Your session expired.</h4>
			</c:if>
			<c:if test='${param["error"] == "sessionExpiredDuplicateLogin"}'>
				<h4>Your session expired. Duplicate login.</h4>
			</c:if>
		<form action="<c:url value='/'/>">
			<input type="submit" id="startAgain" class="hidden" />
		</form>
		<a id="startAgain" class="btnDark" href="<c:url value='/'/>">Start
			again</a>
		</p>
	</div>

	<script type="text/javascript">
		$(document).keydown(function(e) {
			if (e.keyCode == 13) {
				$("#startAgain").click();
			}
			return false;
		});
	</script>
</body>
</html>
