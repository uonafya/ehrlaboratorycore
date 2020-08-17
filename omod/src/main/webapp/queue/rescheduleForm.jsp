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
	jQuery(document).ready(function() {
		
		jQuery('#rescheduledDate').datepicker({yearRange:'c-30:c+30', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true,
		showOn: "button",		
		 buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
         buttonImageOnly: true,
		 minDate:1});
		
		
    });
</script>

<style>
	.info {
		font-weight: bold;
		text-align:right
	}
</style>

<center>
	<c:choose>
		<c:when test="${not empty test}">
			<table class="testInfo" cellspacing="15">
				<tr>
					<td class='info'>Patient Name</td>
					<td>${fn:replace(test.patientName,',',' ')}</td>
					<td></td>
					<td class='info'>Patient Identifier</td>
					<td>${test.patientIdentifier}</td>
				</tr>
				<tr>
					<td class='info'>Date</td>
					<td>${test.startDate}</td>
					<td width="30px"></td>
					<%-- ghanshyam 19/07/2012 New Requirement #309: [LABORATORY] Show Results in Print WorkList.introduced the column 'Lab' 'Test' 'Test name' 'Result' --%>
					<td class='info'>Test</td>
					<td>${test.test.name}</td>
				</tr>
				<tr>
					<td class='info'>Gender</td>
					<td>${test.gender}</td>
					<td></td>
					<td class='info'>Age</td>
					<td>${test.age}</td>
				</tr>
			</table>
			
			<b>Reschedule Date</b>: <input id="rescheduledDate" value="${currentDate}" style="text-align:right;"/>&nbsp;
			<input type="button" onClick="javascript:window.parent.rescheduleTest(${test.orderId}, $('#rescheduledDate').val()); tb_remove();" value="Reschedule" />
			&nbsp;
			<input type="button" onClick="tb_remove();" value="Cancel" />
		</c:when>
		<c:otherwise>
			Not found<br />
			<input type="button" onClick="tb_remove();" value="Cancel" />
		</c:otherwise>
	</c:choose>
</center>