<%--
 **
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 *
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<script type="text/javascript">
	testNo = ${testNo};
</script>
<table id="myTable" class="tablesorter">
	<thead>
		<tr>
			<th>Order Date</th>
			<th>Patient Identifier</th>
			<%-- ghanshyam 26-sept-2012 [Laboratory - Support #391] (New) Same format in the 'Print Worklist and 'Export Worklist' --%>
			<th>Name</th>
			<th>Age</th>
			<th>Gender</th>
			<th>Sample No.</th>
			<th>Lab</th>
			<th>Test</th>
			<th>Test name</th>
			<th>Result</th>
			<td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="test" items="${tests}" varStatus="index">
			<c:choose>
				<c:when test="${index.count mod 2 == 0}">
					<c:set var="klass" value="odd" />
				</c:when>
				<c:otherwise>
					<c:set var="klass" value="even" />
				</c:otherwise>
			</c:choose>
			<tr class="${klass}">
				<td>${test.acceptedDate}</td>
				<td>${test.patientIdentifier}</td>
				<%-- ghanshyam 26-sept-2012 [Laboratory - Support #391] (New) Same format in the 'Print Worklist and 'Export Worklist' --%>
				<td>${fn:replace(test.patientName,',',' ')}</td>
				<td>${test.age}</td>
				<td>${test.gender}</td>
				<td>${test.sampleId}</td>
				<%-- ghanshyam 19/07/2012 New Requirement #309: [LABORATORY] Show Results in Print WorkList.introduced the column 'Lab' 'Test' 'Test name' 'Result' --%>
				<td>${test.investigation}</td>
				<td>${test.test.name}</td>
				<td>${test.testName.name}</td>
				<td>${test.value}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="printArea" style="display: none;">
	<style>
table.wltable {
	margin-left: auto;
	margin-right: auto;
	font-family: Verdana, 'Lucida Grande', 'Trebuchet MS', Arial, Sans-Serif;
	font-style: normal;
	font-size: 10px;
	border: 1px solid;
}

table.wltable th {
	border-bottom: 1px solid;
}

table.wltable td {
	padding: 5px 5px 0px 5px;
}

table.wltable .right {
	border-right: 1px solid;
}
</style>
	<table class="wltable" cellspacing="0">
		<tr>
			<th class="right">Order Date</th>
			<%-- ghanshyam 26-sept-2012 [Laboratory - Support #391] (New) Same format in the 'Print Worklist and 'Export Worklist' --%>
			<th class="right">Patient Identifier</th>
			<th class="right">Name</th>
			<th class="right">Age</th>
			<th class="right">Gender</th>
			<th class="right">Sample No.</th>
			<th class="right">Lab</th>
			<th class="right">Test</th>
			<th class="right">Test name</th>
			<th class="right">Result</th>
		</tr>
		<c:forEach var="test" items="${tests}" varStatus="index">
			<c:choose>
				<c:when test="${index.count mod 2 == 0}">
					<c:set var="klass" value="odd" />
				</c:when>
				<c:otherwise>
					<c:set var="klass" value="even" />
				</c:otherwise>
			</c:choose>
			<tr class="${klass}">
				<td class="right">${test.acceptedDate}</td>
				<td class="right">${test.patientIdentifier}</td>
				<%-- ghanshyam 26-sept-2012 [Laboratory - Support #391] (New) Same format in the 'Print Worklist and 'Export Worklist' --%>
				<td class="right">${test.patientName}</td>
				<td class="right">${test.age}</td>
				<td class="right">${test.gender}</td>
				<td class="right">${test.sampleId}</td>
				<td class="right">${test.investigation}</td>
				<td class="right">${test.test.name}</td>
				<td class="right">${test.testName.name}</td>
				<td class="right">${test.value}</td>
			</tr>
		</c:forEach>
	</table>
</div>
