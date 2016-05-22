<%@ page isErrorPage="true" import="java.io.*"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Error page</title>
<script>
	$(function() {
		$("#accordion").accordion({
			collapsible : true,
			active : false
		});
	});
</script>

</head>
<body>

	<h2>An error has occured processing the request</h2>

	<h3>${name}</h3>
	<h3>${ex.message}</h3>

	<form:form>

		<input type="submit" formaction="listCircuits" formmethod="get"
			value="Back" onclick='this.form.action="listCircuits";'>
	</form:form>


	<div id="accordion">
		<h3>Click for full error details.</h3>
		<div>
			<pre>
<%
    // if there is an exception
    if (null != exception) {
        // print the stack trace hidden in the HTML source code for debug
        exception.printStackTrace(new PrintWriter(out));
    } else {
        exception = (Exception) request.getSession().getAttribute("exception");
        if (null != exception) {
            exception.printStackTrace(new PrintWriter(out));
        }
    }
%>
</pre>

		</div>

	</div>



</body>
</html>