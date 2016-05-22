<%@page import="nz.co.example.dev.domain.model.Circuit"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<script type="text/javascript" charset="utf-8"
	src="resources/js/list-circuits.js"></script>

<script type="text/javascript">
	var oTable;
	var data = null;

	$(document).ready(function() {
		/* Add a click handler to the rows - this could be used as a callback */
		$("#list-circuits-table tbody tr").click(function(e) {
			if ($(this).hasClass('row_selected')) {
				$(this).removeClass('row_selected');
				data = null;
			} else {
				oTable.$('tr.row_selected').removeClass('row_selected');
				$(this).addClass('row_selected');
				data = oTable.fnGetData(this);
			}

		});

		/* Init the table */
		oTable = $('#list-circuits-table').dataTable({
			title : 'Product List',
			"sScrollY" : "400px",
			"sScrollXInner" : "110%",
			"bScrollCollapse" : false,
			"bPaginate" : true,
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bStateSave" : true,
			"bAutoWidth" : false,
			"aoColumnDefs" : [ {
				"sWidth" : "10%",
				"aTargets" : [ -1 ]
			}, {
				"sWidth" : "180px",
				"aTargets" : [ -1 ]
			} ],
			"sDom" : 'pl<"wrapper"t>pi'
		});
		//	"sDom" : '<"H"fp><"bottom"il><"wrapper"t><"F"lip>'

		$('#list-circuits-table td[title]').hover(function() {
			showTooltip($(this));
		}, function() {
			hideTooltip();
		});

		$("#regionId").change(function() {
			var id = $(this).children(":selected").val();
			$('#col9_filter').val(id);
			fnFilterRegion2(id);
		});

		$("#global_filter").keyup(fnFilterGlobal);
		$("#global_regex").click(fnFilterGlobal);
		$("#global_smart").click(fnFilterGlobal);

		$("#col1_filter").keyup(function() {
			fnFilterColumn(0);
		});
		$("#col1_regex").click(function() {
			fnFilterColumn(0);
		});
		$("#col1_smart").click(function() {
			fnFilterColumn(0);
		});

		$("#col9_filter").keyup(function() {
			fnFilterColumn(8);
		});
		$("#col9_regex").click(function() {
			fnFilterColumn(8);
		});
		$("#col9_smart").click(function() {
			fnFilterColumn(8);
		});

		$('#filterRecordsButton').click();
	});

	function showTooltip($el) {
		// $tip.html($el.attr('title'));
	}

	function hideTooltip() {
		// $tip.hide();
	}

	function fnFilterRegion() {
		$('#list-circuits-table').dataTable().fnFilter($("#regionId").val(),
				null, $("#global_regex")[0].checked,
				$("#global_smart")[0].checked);
	}

	function fnFilterGlobal() {
		$('#list-circuits-table').dataTable().fnFilter(
				$("#global_filter").val(), null, true, true);
	}

	function fnFilterRegion2(regionId) {
		$('#list-circuits-table').dataTable().fnFilter(regionId, 8, true, true);
	}

	/* Get the rows which are currently selected */
	function fnGetSelected(oTableLocal) {
		return oTableLocal.$('tr.row_selected');
	}

	/* Get the rows which are currently selected */
	function submitEditDetails() {
		if (data) {
			var carrierId = data[0];
			$("#carrierId").val(carrierId);
			var ipAddress = data[1];
			$("#ipAddress").val(ipAddress);
			var vLAN = data[2];
			$("#vLAN").val(vLAN);
			var region = data[3];
			$("#regionId").val(region);
			var targetForm = $("#editCircuitId1");
			targetForm[0].setAttribute('action', 'editCircuit');
			targetForm[0].submit();
		}
	}
</script>
</head>

<body>
	<div id="container" class="list-container">
		<div id="button-header" class="button-header"
			style="display: inline-block; float: right">
			<sec:authorize access="hasRole('administrator') or hasRole('manager-gui')">
				<div style="float: right;">
					<form:form id="editCircuitId1" action="editCircuit">
						<input Type="Hidden" id="carrierId" Name="carrierId" Value="">
						<input Type="Hidden" id="ipAddress" Name="ipAddress" Value="">
						<input Type="Hidden" id="vLAN" Name="vLAN" Value="">
						<input Type="Hidden" id="region" Name="region" Value="">
						<input class="submit-button hidden" type="submit"
							onclick="submitEditDetails();" formmethod="get"
							formaction="editCircuit" value="Edit Circuit"
							onclick='this.form.action="editCircuit";'>
						<!-- 
						<a class="btnDark" style="float: right; margin-top: 3px;"
							id="edit" href="#"
							onClick="document.getElementById('editCircuitId1').submit();">Edit
							Circuit</a>  -->
					</form:form>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('administrator') or hasRole('manager-gui')">
			</sec:authorize>
				<div style="float: right;">
					<form:form id="newCircuitId" action="newCircuit">
						<input class="submit-button hidden" type="submit" formmethod="get"
							formaction="newCircuit" value="New Circuit"
							onclick='this.form.action="newCircuit";'>
					</form:form>
					<a class="btnDark" style="float: right; margin-top: 3px;" id="new"
						href="#"
						onClick="document.getElementById('newCircuitId').submit();">New
						Circuit</a>
					<form:form id="viewLogId" action="viewLog" method="GET">
						<input class="submit-button hidden" type="submit" formmethod="get"
							formaction="viewLog" value="View Log"
							onclick='this.form.action="viewLog/";'>
					</form:form>
					<a class="btnDark" style="float: right; margin-top: 3px;" id="new"
						href="#" onClick="document.getElementById('viewLogId').submit();">View
						Log</a>
				</div>
		</div>

		<div class="filtering" style="display: inline-block; float: right;">
			<form name="input_form">
				<table cellpadding="0" cellspacing="0" border="0" class="display">
					<thead>
						<tr>
						</tr>
					</thead>
					<tbody>
						<tr id="filter_global">
							<td align="center">Global search: <input type="text"
								name="global_filter" id="global_filter"> Region: <select
								id="regionId" name="regionId">
									<option selected="selected" value="">All Regions</option>
									<option value="auc">Auckland</option>
									<option value="chch">Christchurch</option>
									<option value="wlgn">Wellington</option>
									<option value="lab">Lab</option>
							</select> <hidden type="text" name="col9_filter" id="col9_filter"></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>

		<div id="content" class="list-content">
			<table id="list-circuits-table" class="display">
				<thead>
					<tr>
						<th align="left">Carrier Id<br />Parent Realm
						</th>
						<th align="left" width="180px">Access Realms</th>
						<th align="left">Network Interface Id</th>
						<th align="left">IP Address</th>
						<th align="left">Primary IP Address</th>
						<th align="left">Secondary IP Address</th>
						<th align="left">Gateway IP Address</th>
						<th align="left">VLAN</th>
						<th align="left">Region</th>
						<th align="left"><img src="resources/images/icon-edit.png"
							width="25" height="25" alt="Edit" border="0" /> Edit</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${circuits}" var="circuit" varStatus="status">
						<tr title="">
							<td align="left" title="${circuit.hint}"><span> <c:out
										value="${circuit.carrierId}" /> <br /> <c:out
										value="${circuit.primaryAccessRealm.parentRealm}" />
							</span></td>
							<td align="left" width="180px"><c:out
									value="${circuit.primaryAccessRealm.identifier}" /><br /> <c:out
									value="${circuit.secondaryAccessRealm.identifier}" /></td>
							<td><c:out value="${circuit.networkInterface.id}" /></td>
							<td><c:out value="${circuit.ipAddress}" /></td>
							<td><c:out
									value="${circuit.networkInterface.primaryUtilityAddress.hostAddress}" /></td>
							<td><c:out
									value="${circuit.networkInterface.secondaryUtilityAddress.hostAddress}" /></td>
							<td><c:out
									value="${circuit.networkInterface.ipAddress.hostAddress}" /></td>
							<td><c:out value="${circuit.VLAN}" /></td>
							<td><c:out value="${circuit.region}" /></td>
							<td>									<a class="btnDark" style="float: right; margin-top: 3px;"
										id="edit" href="#"
										onClick="document.getElementById('editCircuitId${status.index}').submit();">Edit
										Circuit</a>
							<c:choose>
									<c:when
										test="${fn:startsWith(circuit.primaryAccessRealm.parentRealm, 'CS2K')}">
										<a href="#">A</a>
									</c:when>
								</c:choose> <form:form id="editCircuitId${status.index}"
									action="editCircuit">
									<input Type="Hidden" id="carrierId" Name="carrierId"
										Value="${circuit.carrierId}">
									<input Type="Hidden" id="ipAddress" Name="ipAddress"
										Value="${circuit.ipAddress}">
									<input Type="Hidden" id="vLAN" Name="vLAN"
										Value="${circuit.VLAN}">
									<input Type="Hidden" id="region" Name="region"
										Value="${circuit.region}">
									<input Type="Hidden" id="circuitType" Name="circuitType"
										Value="${circuit.circuitType}">
									<input class="submit-button hidden" type="submit"
										onclick="submitEditDetails();" formmethod="get"
										formaction="editCircuit" value="Edit Circuit"
										onclick='this.form.action="editCircuit";'>
								</form:form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
