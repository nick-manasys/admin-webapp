<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>New Circuit</title>

<link rel="stylesheet" href="resources/css/validation.css" />

<script type="text/javascript" src="resources/js/jquery.validate.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var validator = $("#mainFormId")
								.validate(
										{
											rules : {
												circuitType : "required",
												carrierName : {
													required : true,
													minlength : 3,
													maxlength : 16
												},
												carrierShortCode : {
													required : true,
													minlength : 3,
													maxlength : 10
												},
												region : "required",
												trunkNumber : {
													required : true,
													range : [ 1, 99 ]
												},
												ipAddress : {
													required : true,
													IPChecker : true
												},
												primaryUtilityIpAddress : {
													required : true,
													IPChecker : true
												},
												secondaryUtilityIpAddress : {
													required : true,
													IPChecker : true
												},
												networkMask : {
													required : true,
													IPChecker : true
												},
												accessVLan : {
													required : true,
													range : [ 2, 4095 ]
												},
												defaultGatewayIpAddress : {
													required : false,
													OptionalIPChecker : true
												}
											},
											messages : {
												carrierName : "Enter your carrierName between 3 and 8 characters (e.g. Tux)",
												carrierShortCode : "Enter your carrierShortCode between 3 and 8 characters (e.g. tux)",
												trunkNumber : "Enter the trunk number [1..99]",
												ipAddress : "Enter the ip address (e.g 192.168.1.1)",
												primaryUtilityIpAddress : "Enter the primary utility address (e.g 192.168.1.1)",
												secondaryUtilityIpAddress : "Enter the secondary utility address (e.g 192.168.1.1)",
												networkMask : "Enter the network mask (e.g 255.255.255.0)",
												accessVLan : "Enter the vlan [2..4095]",
												defaultGatewayIpAddress : "Optional default gateway ip address (e.g. blank or 192.168.1.1)"
											},
											// the errorPlacement has to take the table layout into account
											errorPlacement : function(error,
													element) {
												if (element.is(":radio"))
													error.appendTo(element
															.parent().next()
															.next());
												else if (element
														.is(":checkbox"))
													error.appendTo(element
															.next());
												else
													error.appendTo(element
															.parent().next());
											},
											// specifying a submitHandler prevents the default submit, good for the demo
											// submitHandler: function() {
											//	alert("submitted!");
											//	$('#formButtonId').click();
											// },
											// set this class to error-labels to indicate valid fields
											success : function(label) {
												// set &nbsp; as text for IE
												label.html("&nbsp;").addClass(
														"checked");
											},
											highlight : function(element,
													errorClass) {
												$(element).parent().next()
														.find("." + errorClass)
														.removeClass("checked");
											}
										});

						//Validate the IP addresses
						$(function() {
							$.validator
									.addMethod(
											'IPChecker',
											function(value) {
												var ip4 = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
												var ip6 = /^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))$/i;
												if (value == "0.0.0.0"
														|| value == "255.255.255.255") {
													return false;
												}
												return value.match(ip4);
											});
						});
						$(function() {
							$.validator
									.addMethod(
											'OptionalIPChecker',
											function(value) {
												var ip4 = /(^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$)|^[ \t]*$/;
												var ip6 = /^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))$/i;
												if (value == "0.0.0.0"
														|| value == "255.255.255.255") {
													return false;
												}
												return value.match(ip4);
											});
						});

						$("#carrierShortCode").focus();
					});

	// validation	
	function validateForm() {
		$("#mainFormId").validate({
			submitHandler : function(form) {
				form.submit();
			}
		});

	}
</script>

</head>

<body>

	<spring:hasBindErrors name="circuit">
		<c:set var="circuitHasError" value="true" />
	</spring:hasBindErrors>

	<div id="container" class="circuit-container">
		<div id="button-header" class="button-header">
			<form:form action="listCircuits" id="formId">
				<input class="submit-button hidden" type="submit" formmethod="get"
					formaction="listCircuits" value="Back to List"
					onclick='this.form.action="listCircuits";'>
			</form:form>

			<a class="btnDark" style="float: right; margin-top: 3px;" id="new"
				href="#" onClick="document.getElementById('formId').submit();">Back
				to List</a>
		</div>


		<div id="content" class="new-circuit-content">
			<form:form method="post" commandName="circuit" id="mainFormId">

				<table id="new-circuit-table" class="new-circuit-table">
					<tbody>
						<tr>
							<td>
								<!-- align="left">
								<c:choose>
								    <c:when test="${circuitHasError}">
										width="70%"
								    </c:when>
								    <c:otherwise>
										width="50%"
								    </c:otherwise>
								</c:choose> --> Circuit Type
							</td>
							<td>
								<!-- align="left"
								<c:choose>
								    <c:when test="${circuitHasError}">
										width="70%"
								    </c:when>
								    <c:otherwise>
										width="50%"
								    </c:otherwise>
								</c:choose>> --> <form:select path="circuitType"
									disabled="${circuit.validatedReadyForSave}"
									style="width: 150px;">
									<form:option value="NONE" label="--- Select ---" />
									<form:options items="${circuit.circuitTypeOptions}" />
								</form:select>
							</td>
							<td width="300px" class="status checked" align="left"><label
								for="circuitType" class="error checked">&nbsp;</label> <form:errors
									path="circuitType" cssClass="error" /></td>
						</tr>
						<tr>
							<td>CLLI (omit SP prefix)</td>
							<td><form:input path="carrierShortCode" maxlength="10"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="carrierShortCode" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Customer</td>
							<td><form:input path="carrierName" maxlength="16"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="carrierName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Region</td>
							<td><form:select path="region"
									disabled="${circuit.validatedReadyForSave}"
									style="width: 150px;">
									<form:option value="NONE" label="--- Select ---" />
									<form:options items="${circuit.regionOptions}" />
								</form:select></td>
							<td width="30px" class="status checked" align="left"><label
								for="region" class="error checked">&nbsp;</label> <form:errors
									path="region" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Sip Definition</td>
							<td><form:input path="trunkNumber"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="trunkNumber" cssClass="error" /></td>
						</tr>
						<tr>
							<td>IP Address</td>
							<td><form:input path="ipAddress"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="ipAddress" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Primary Utility IP Address</td>
							<td><form:input path="primaryUtilityIpAddress"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="primaryUtilityIpAddress" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Secondary Utility IP Address</td>
							<td><form:input path="secondaryUtilityIpAddress"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="secondaryUtilityIpAddress" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Network Mask</td>
							<td><form:input path="networkMask"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="networkMask" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Access VLAN</td>
							<td><form:input path="accessVLan"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="accessVLan" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Default Gateway IP Address</td>
							<td><form:input path="defaultGatewayIpAddress"
									disabled="${circuit.validatedReadyForSave}" /></td>
							<td class="status" align="left"><form:errors
									path="defaultGatewayIpAddress" cssClass="error" /></td>
						</tr>
					</tbody>
				</table>
				<div id="form-buttons" class="form-buttons">
					<c:if test="${circuit.validatedReadyForSave}">
						<button id="view-circuit" class="form-button hidden"
							onclick="showDetails(); return false;">View Circuit
							Details</button>
						<a class="btnDark" style="float: right; margin-top: 3px;"
							id="edit" href="#" onClick="showDetails(); return true;">View
							Circuit Details</a>
					</c:if>
				</div>
		</div>

		<div id="button-footer" class="button-footer">
			<c:choose>
				<c:when test="${circuit.validatedReadyForSave}">
					<input Type="Hidden" id="circuitType" Name="circuitType"
						Value="${circuit.circuitType}">
					<input Type="Hidden" id="carrierShortCode" Name="carrierShortCode"
						Value="${circuit.carrierShortCode}">
					<input Type="Hidden" id="carrierName" Name="carrierName"
						Value="${circuit.carrierName}">
					<input Type="Hidden" id="region" Name="region"
						Value="${circuit.region}">
					<input Type="Hidden" id="trunkNumber" Name="trunkNumber"
						Value="${circuit.trunkNumber}">

					<input Type="Hidden" id="ipAddress" Name="ipAddress"
						Value="${circuit.ipAddress}">
					<input Type="Hidden" id="primaryUtilityIpAddress"
						Name="primaryUtilityIpAddress"
						Value="${circuit.primaryUtilityIpAddress}">
					<input Type="Hidden" id="secondaryUtilityIpAddress"
						Name="secondaryUtilityIpAddress"
						Value="${circuit.secondaryUtilityIpAddress}">
					<input Type="Hidden" id="networkMask" Name="networkMask"
						Value="${circuit.networkMask}">
					<input Type="Hidden" id="accessVLan" Name="accessVLan"
						Value="${circuit.accessVLan}">
					<input Type="Hidden" id="defaultGatewayIpAddress"
						Name="defaultGatewayIpAddress"
						Value="${circuit.defaultGatewayIpAddress}">

					<input class="submit-button hidden" type="submit" formmethod="post"
						id="saveNewCircuitId" formaction="saveNewCircuit"
						value="Save New Circuit"
						onclick='this.form.action="saveNewCircuit";'>
					<a class="btnDark" style="float: right; margin-top: 3px;" id="edit"
						href="#" onClick="$('#saveNewCircuitId').click();">Save New
						Circuit</a>
					<input class="submit-button hidden" type="submit" formmethod="post"
						formaction="cancelNewCircuit" value="Cancel New Circuit"
						onclick='this.form.action="cancelNewCircuit";'>
					<a class="btnDark" style="float: right; margin-top: 3px;" id="edit"
						href="#" onClick="document.getElementById('formId').submit();">Cancel
						New Circuit</a>
				</c:when>
				<c:otherwise>
					<input class="submit-button hidden" type="submit" formmethod="post"
						id="validateNewCircuitId" formaction="validateNewCircuit"
						value="Validate New Circuit"
						onclick='this.form.action="validateNewCircuit";'>
					<a class="btnDark" style="float: right; margin-top: 3px;" id="edit"
						href="#"
						onClick="$('#validateNewCircuitId').click(); return true;">Validate
						New Circuit</a>
				</c:otherwise>
			</c:choose>
		</div>

		</form:form>

	</div>

	<div id="dialog-modal" title="Full Circuit Details"
		style="text-align: left">
		<c:if test="${not empty fullCircuitDetails}">
			<pre>
				<c:out value="${fullCircuitDetails}"></c:out>
			</pre>
		</c:if>
	</div>
</body>
</html>
