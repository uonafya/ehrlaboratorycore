 <%--
 /*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
*/
--%> 
<%@ include file="/WEB-INF/template/include.jsp" %>
<script type="text/javascript">
/*
 *Ghanshyam - Sagar 
 Sep 14,2012  
 Bug #360: [Laboratory Module] Same result entered twice in database.
 */
function refreshLabResult(){
        jQuery.ajax({
                type : "GET",
                url : getContextPath() + "/module/ehrlaboratory/searchWorklist.form"
                });
}
	testNo = ${testNo};
</script>
<table id="myTable" class="tablesorter">
	<thead>
		<tr> 
			<th><center>Sr. No.</center></th>
			<th>Sample ID</th>
			<th>Results</th>
			<th>Reorder</th>	
			<th>Date</th>
			<th>Patient ID</th>
			<th>Name</th>
			<th>Gender</th>
			<th>Age</th>
			<th>Test</th>					
		</tr>
	</thead>
	<tbody>
		<c:forEach var="test" items="${tests}" varStatus="index">
			<c:choose>
				<c:when test="${index.count mod 2 == 0}">
					<c:set var="klass" value="odd"/>
				</c:when>					
				<c:otherwise>
					<c:set var="klass" value="even"/>
				</c:otherwise>
			</c:choose>
			<tr class="${klass}">
				<td><center>${index.count}</center></td>
				<td>${test.sampleId}</td>
				<td>
					<a href="javascript:enterResult(${test.testId});">
						Enter results
					</a>
				</td>
				<td>
					<a href="rescheduleTest.form?type=reorder&orderId=${test.orderId}&modal=true&height=200&width=800" class="thickbox" title="Reschedule test">Reorder</a> 					
				</td>
				<td>
					${test.startDate}
				</td>
				<td>
					${test.patientIdentifier}
				</td>
				<td>
					${fn:replace(test.patientName,',',' ')}
				</td>
				<td>
					${test.gender}
				</td>
				<td>
					${test.age}
				</td>
				<%-- ghanshyam 19/07/2012 New Requirement #309: [LABORATORY] Show Results in Print WorkList.introduced the column 'Lab' 'Test' 'Test name' 'Result' --%>
				<td>
					${test.test.name}
				</td>
				
			</tr>	
			<tr class='resultParameter' id='row${test.testId}' style='display:none;'>
				<td colspan='10'>
					<form id="contentForm${test.testId}" method="post" action="showForm.form">
						
					</form>
				<%-- 
					 Ghanshyam - Sagar 
					 Sep 14,2012  
					 Bug #360: [Laboratory Module] Same result entered twice in database.
					 --%>
					<div style='clear: both;'></div>
					<input type="button" value="Save" onClick="submit(${test.testId});refreshLabResult();"/>
					<input type="button" value="Cancel" onClick='jQuery("#row${test.testId}").hide();'/>	
					
					&nbsp; &nbsp; Click on "Save" to save the result</td>
			</tr>
		</c:forEach>
	</tbody>
</table>