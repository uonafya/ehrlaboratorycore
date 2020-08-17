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
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<br/>
<openmrs:require privilege="Manage Laboratory" otherwise="/login.htm" redirect="/module/ehrlaboratory/listForm.form" />
<%@ include file="../localHeader.jsp" %>
<script type="text/javascript">
	$(document).ready(function() {
		$("#myTable").tablesorter({sortList: [[0,0]]});
    });	
	
	// enable/disable a test
	function toggleTest(serviceId){
		serviceIds = $('#serviceIds').val();
		if(serviceIds.indexOf("<" + serviceId + ">")>=0){
			serviceIds = serviceIds.replace("<" + serviceId + ">", "");
		} else {
			serviceIds += "<" + serviceId + ">"
		}
		$('#serviceIds').val(serviceIds);
	}
</script> 

<table id='myTable' class='tablesorter' style='width: 500px;'>
	<thead>
		<tr> 
			<th>Test</th>
			<th>Disabled</th>
		</tr>
	</thead>
	<tbody>

<c:forEach var='bs' items='${billableServices}'>
		<tr>
			<td>${bs.name}</td>
			<td>
				<center>					
					<input id='cb${bs.serviceId}' type='checkbox' onClick='toggleTest(${bs.serviceId});' <c:if test='${bs.disable}'>checked='checked'</c:if>/>
				</center>				
			</td>
		</tr>	
</c:forEach>		
	</tbody>
</table>
<form method="POST">	
	<input type='hidden' id='serviceIds' name='serviceIds' value=''/>	
	<input type='submit' value='Save'/>	
</form>	



<%@ include file="/WEB-INF/template/footer.jsp" %>  