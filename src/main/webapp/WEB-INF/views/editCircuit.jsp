<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Edit Circuit</title>

<link rel="stylesheet" href="resources/css/validation.css" />

<script type="text/javascript" src="resources/js/jquery.validate.js"></script>

<script type="text/javascript">
	var oTable;
	var data = null;

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

	<form:form method="post" id="mainFormId" commandName="circuit"
		name="input_form">
		<div id="container" class="circuit-container">
			<div id="edit-circuit-header" class="edit-circuit-header"></div>
			<div id="button-header" class="button-header"
				style="display: block; float: right;">
				<form:form action="listCircuits" id="formId"
					commandName="editCircuit">
					<input class="submit-button hidden" type="submit" formmethod="get"
						id="formButtonId" formaction="listCircuits" value="Back to List"
						onclick='this.form.action="listCircuits";'>
					<a class="btnDark" style="float: right; margin-top: 3px;" id="edit"
						href="#" onclick="$('#formButtonId').click(); return true;">Back
						to List</a>
				</form:form>
			</div>

			<div id="content" class="edit-circuit-content"
				style="float: center; display: inline-block;">
				<table id="edit-circuit-table" class="edit-circuit-table">
					<tbody>
						<tr>
							<td align="right"
								<c:choose>
								    <c:when test="${circuitHasError}">
										width="30%"
								    </c:when>
								    <c:otherwise>
										width="50%"
								    </c:otherwise>
								</c:choose>>Circuit
								Type</td>
							<td><form:select path="circuitType" id="circuitType"
									disabled="${circuit.validatedReadyForSave}"
									style="width: 150px;">
									<form:options items="${circuit.circuitTypeOptions}" />
								</form:select> <form:errors path="circuitType" cssClass="error" /></td>
							<td width="30px" class="status checked" align="left"><label
								for="circuitType" class="error checked">&nbsp;</label></td>
						</tr>
						<tr>
							<td align="right">CLLI (omit SP prefix)</td>
							<td><form:input id="carrierShortCode" class="valid"
									maxlength="10" name="carrierShortCode" path="carrierShortCode"
									value="${circuit.carrierShortCode}"
									disabled="${circuit.validatedReadyForSave}" /> <form:errors
									path="carrierShortCode" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
						<tr>
							<td align="right">Customer</td>
							<td><form:input id="carrierName" name="carrierName"
									maxlength="16" path="carrierName"
									value="${circuit.carrierName}"
									disabled="${circuit.validatedReadyForSave}" /> <form:errors
									path="carrierName" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
						<tr>
							<td align="right">Region</td>
							<td><form:select path="region"
									disabled="${circuit.validatedReadyForSave}"
									style="width: 150px;">
									<form:options items="${circuit.regionOptions}" />
								</form:select> <form:errors path="region" cssClass="error" /></td>
							<td class="status" align="left"><label for="region"
								class="error checked">&nbsp;</label></td>
						</tr>
						<tr>
							<td title="Number between 1 and 99" align="right">Sip
								Definition</td>
							<td><form:input path="trunkNumber"
									value="${circuit.trunkNumber}"
									disabled="${circuit.validatedReadyForSave}" /> <form:errors
									path="trunkNumber" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
						<tr>
							<td align="right">IP Address</td>
							<td><form:input path="ipAddress"
									value="${circuit.ipAddress}"
									disabled="${circuit.validatedReadyForSave}" /> <form:errors
									path="ipAddress" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
						<tr>
							<td align="right">Primary Utility IP Address</td>
							<td><form:input path="primaryUtilityIpAddress"
									value="${circuit.primaryUtilityIpAddress}"
									disabled="${circuit.validatedReadyForSave}" /> <form:errors
									path="primaryUtilityIpAddress" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
						<tr>
							<td align="right">Secondary Utility IP Address</td>
							<td><form:input path="secondaryUtilityIpAddress"
									value="${circuit.secondaryUtilityIpAddress}"
									disabled="${circuit.validatedReadyForSave}" /> <form:errors
									path="secondaryUtilityIpAddress" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
						<tr>
							<td align="right">Network Mask</td>
							<td><form:input path="networkMask"
									value="${circuit.networkMask}"
									disabled="${circuit.validatedReadyForSave}" /> < form:errors
								path="networkMask" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
						<tr>
							<td align="right">Access VLAN</td>
							<td><form:input path="accessVLan"
									value="${circuit.accessVLan}"
									disabled="${circuit.validatedReadyForSave}" /> <form:errors
									path="accessVLan" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
						<tr>
							<td title="Default gateway IP address (optional)" align="right">Default
								Gateway IP Address</td>
							<td><form:input path="defaultGatewayIpAddress"
									value="${circuit.defaultGatewayIpAddress}"
									disabled="${circuit.validatedReadyForSave}" /> <form:errors
									path="defaultGatewayIpAddress" cssClass="error" /></td>
							<td class="status" align="left"></td>
						</tr>
					</tbody>
				</table>
				<!-- 
				<div id="form-buttons" class="form-buttons">
					<c:if test="${circuit.validatedReadyForSave}">
						<button id="view-circuit" class="form-button hidden"
							onclick="showDetails(); return false;">View Circuit
							Details</button>
						<a class="btnDark" style="float: right; margin-top: 3px;"
							id="edit" href="#" onClick="showDetails(); return true;">View
							Circuit Details</a>B
					</c:if>
				</div>
				 -->

			</div>

			<div id="form-buttons" class="form-buttons">
				<button id="view-circuit" class="form-button hidden"
					onclick="showDetails(); return false;">View Circuit
					Details</button>
				<a class="btnDark" style="float: right; margin-top: 3px;" id="edit"
					href="#" onClick="showDetails(); return true;">View Circuit
					Details</a>
			</div>
		</div>

		<div id="button-footer" class="button-footer">

			<c:choose>
				<c:when test="${circuit.validatedReadyForSave}">
					<!-- 
					<input type="hidden" id="circuitType" name="circuitType"
						Value="${circuit.circuitType}">
					<input type="hidden" id="carrierShortCode" name="carrierShortCode"
						value="${circuit.carrierShortCode}">
						 -->
					<input type="hidden" id="carrierName" name="carrierName"
						Value="${circuit.carrierName}">
					<input type="hidden" id="region" name="region"
						Value="${circuit.region}">
					<input type="hidden" id="trunkNumber" name="trunkNumber"
						Value="${circuit.trunkNumber}">

					<input type="hidden" id="ipAddress" name="ipAddress"
						Value="${circuit.ipAddress}">
					<input type="hidden" id="primaryUtilityIpAddress"
						Name="primaryUtilityIpAddress"
						Value="${circuit.primaryUtilityIpAddress}">
					<input type="hidden" id="secondaryUtilityIpAddress"
						Name="secondaryUtilityIpAddress"
						Value="${circuit.secondaryUtilityIpAddress}">
					<input type="hidden" id="networkMask" name="networkMask"
						Value="${circuit.networkMask}">
					<input type="hidden" id="accessVLan" name="accessVLan"
						Value="${circuit.accessVLan}">
					<input type="hidden" id="defaultGatewayIpAddress"
						name="defaultGatewayIpAddress"
						Value="${circuit.defaultGatewayIpAddress}">

					<input class="submit-button hidden" type="submit" formmethod="post"
						id="cancelCircuitModificationsId"
						formaction="cancelCircuitModifications"
						value="Cancel Circuit Modifications"
						onclick="$('#mainFormId').get(0).setAttribute('action', 'cancelCircuitModifications'); $('#mainFormId').submit();">
					<a class="btnDark" style="float: right; margin-top: 3px;" id="edit"
						href="#" onClick="$('#cancelCircuitModificationsId').click();">Cancel
						Circuit Modifications</a>
					<form:form action="saveCircuitModifications"
						id="saveCircuitModificationsFormId" commandName="editCircuit">
						<input type="hidden" id="circuitType" name="circuitType"
							value="${circuit.circuitType}">
						<input type="hidden" id="carrierShortCode" name="carrierShortCode"
							value="${circuit.carrierShortCode}">
						<input type="hidden" id="carrierName" name="carrierName"
							Value="${circuit.carrierName}">
						<input type="hidden" id="region" name="region"
							Value="${circuit.region}">
						<input type="hidden" id="trunkNumber" name="trunkNumber"
							Value="${circuit.trunkNumber}">
						<input type="hidden" id="ipAddress" name="ipAddress"
							Value="${circuit.ipAddress}">
						<input type="hidden" id="primaryUtilityIpAddress"
							Name="primaryUtilityIpAddress"
							Value="${circuit.primaryUtilityIpAddress}">
						<input type="hidden" id="secondaryUtilityIpAddress"
							Name="secondaryUtilityIpAddress"
							Value="${circuit.secondaryUtilityIpAddress}">
						<input type="hidden" id="networkMask" name="networkMask"
							Value="${circuit.networkMask}">
						<input type="hidden" id="accessVLan" name="accessVLan"
							Value="${circuit.accessVLan}">
						<input type="hidden" id="defaultGatewayIpAddress"
							Name="defaultGatewayIpAddress"
							Value="${circuit.defaultGatewayIpAddress}">

						<input class="submit-button hidden" type="submit"
							formmethod="post" id="saveCircuitModificationsId"
							formaction="saveCircuitModifications"
							value="Save Circuit Modifications"
							onClick="$('#saveCircuitModificationsFormId').get(0).setAttribute('action', 'saveCircuitModifications'); $('#saveCircuitModificationsFormId').submit();">
						<a class="btnDark" style="float: right; margin-top: 3px;"
							id="edit" href="#"
							onClick="$('#saveCircuitModificationsId').click();">Save
							Circuit Modifications</a>
					</form:form>
				</c:when>
				<c:otherwise>
					<form:form action="relinquishCircuit" id="formId"
						commandName="editCircuit">
						<input type="hidden" id="carrierShortCode" name="carrierShortCode"
							value="${circuit.carrierShortCode}">
						<input type="hidden" id="circuitType" name="circuitType"
							value="${circuit.circuitType}">
						<input type="hidden" id="region" name="region"
							value="${circuit.region}">
						<input type="hidden" id="trunkNumber" name="trunkNumber"
							value="${circuit.trunkNumber}">
						<input type="hidden" id="accessVLan" name="accessVLan"
							value="${circuit.accessVLan}">
						<input class="submit-button hidden" type="submit"
							id="formButtonRelinquishCircuitId" formmethod="get"
							formaction="relinquishCircuit" value="Relinquish Circuit"
							onclick='this.form.action="relinquishCircuit";'>
						<a class="btnDark" style="float: right; margin-top: 3px;"
							id="edit" href="#"
							onClick="$('#formButtonRelinquishCircuitId').click(); return true;">Relinquish
							Circuit</a>
					</form:form>
					<input class="submit-button hidden" type="submit"
						id="formButtonValidateCircuitModificationsId" formmethod="get"
						formaction="validateCircuitModifications"
						value="Validate Circuit Modifications"
						onclick="$('#mainFormId').get(0).setAttribute('action', 'validateCircuitModifications'); $('#mainFormId').submit();">
					<a class="btnDark" style="float: right; margin-top: 3px;" id="edit"
						href="#"
						onClick="$('#formButtonValidateCircuitModificationsId').get(0).click(); return true;">Validate
						Circuit Modifications</a>
				</c:otherwise>
			</c:choose>
		</div>
	</form:form>

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
