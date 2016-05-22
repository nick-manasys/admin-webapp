<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.servletsuite.com/servlets/tailtag" prefix="t"%>
<%@ page session="false"%>
<html>
<head>
<title>Log file ${catalinaHome}/</title>
</head>

<body>
	<div>
		<form:form id="viewLogId" action="${pageContext.request.contextPath}/"
			method="GET">
			<input class="submit-button hidden" type="submit" formmethod="get"
				formaction="viewLog" value="Home"
				onclick='this.form.action="viewLog/";'>
		</form:form>
		<a class="btnDark" style="float: right; margin-top: 3px;" id="new"
			href="#" onClick="document.getElementById('viewLogId').submit();">Home</a>
	</div>
	<h1>Log file ${catalinaHome}/</h1>

	<!-- 
	<div class="log-lines">
		<t:tail file="${catalinaHome}/logs/admin-app_operations.log"
			count="50" id="S">
			<div class="log-line"><%=S%></div>
		</t:tail>
	</div>
	-->

	<table id="list-circuits-table" class="display">
		<thead>
			<tr>
				<th align="left">When</th>
				<th align="left">Operation</th>
				<th align="left">Details</th>
				<th align="left"><img src="resources/images/icon-edit.png"
					width="25" height="25" alt="View" border="0" />View</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${logEntries}" var="logEntry" varStatus="status">
				<tr>
					<td align="left" title="${logEntry.when}"><span> <c:out
								value="${logEntry.when}" />
					</span></td>
					<td align="left" title="${logEntry.operation.operationType}"><span>
							<c:out value="${logEntry.operation.operationType}" />
					</span></td>
					<td align="left" title="${logEntry.operation.operands[0]}"><span>
							<c:out value="${logEntry.operation.operands[0]}" /> <c:choose>
								<c:when test="${not empty logEntry.operation.operands[1]}">
									<br />
									<c:out value="${logEntry.operation.operands[1]}" />
								</c:when>
							</c:choose>
					</span></td>
					<td align="left" title="${logEntry.operation.operationType}"><span>
							<c:out value="${logEntry.operation.operationType}" />
					</span></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>



