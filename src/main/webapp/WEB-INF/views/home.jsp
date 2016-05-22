<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>

<script type="text/javascript">
	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			$("#enter").click();
		}
	});
</script>
</head>

<body>
	<div>
		<form:form>
			<input class="submit-button hidden" type="submit" id="enter"
				value="enter">
		</form:form>
		<a class="btnDark" style="float: right; margin-top: 3px;" id="enter"
			href="listCircuits">Enter</a>

		<form:form id="viewLogId" action="viewLog" method="GET">
			<input class="submit-button hidden" type="submit" formmethod="get"
				formaction="viewLog" value="View Log"
				onclick='this.form.action="viewLog/";'>
		</form:form>
		<a class="btnDark" style="float: right; margin-top: 3px;" id="new"
			href="#" onClick="document.getElementById('viewLogId').submit();">View
			Log</a>
	</div>

	<div id="motd">
		<h2>${motd}</h2>
	</div>

	<div id="links">
		<a href="https://10.60.0.67:8443/">NNC Prod instance</a><br /> <a
			href="http://10.66.4.9:8080/">NNC Lab instance</a>
	</div>
</body>
</html>
