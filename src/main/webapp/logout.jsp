<html>
<head>
<title>Logout Page</title>

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

	<%
	    session = request.getSession(false);
	    if (null != session)
	        session.invalidate();
	    request.getRequestDispatcher("/").forward(request, response);
	    response.sendRedirect(session.getServletContext().getContextPath() + "/");
	%>
</body>
</html>
