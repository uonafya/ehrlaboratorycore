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
<openmrs:require privilege="Manage Laboratory" otherwise="/login.htm" redirect="/module/ehrlaboratory/listDepartment.form" />
<%@ include file="../localHeader.jsp" %>
<script type="text/javascript">
	$(document).ready(function() 
		{ 
			$("#myTable").tablesorter({sortList: [[0,0]]}); 
		} 
	); 
</script>
<a href="editDepartment.form">Add new department</a>
<table id="myTable" class="tablesorter">
	<thead>
		<tr> 
			<th>No</th>
			<th>Name</th>
			<th>Description</th>
			<th>Role</th>
			<th width="100px;"></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="department" items="${departments}" varStatus="index">
			<c:choose>
				<c:when test="${index.count mod 2 == 0}">
					<c:set var="klass" value="odd"/>
				</c:when>					
				<c:otherwise>
					<c:set var="klass" value="even"/>
				</c:otherwise>
			</c:choose>
			<tr class="${klass}">
				<td>${index.count}</td>
				<td>
					<a href="editDepartment.form?id=${department.labId}">${department.name}</a>
				</td>
				<td>
					${department.description}
				</td>
				<td>
					${department.role.role}
				</td>
				<td>
					<center>
						<a href="deleteDepartment.form?id=${department.labId}">
							Delete
						</a>
					</center>
				</td>
			</tr>	
		</c:forEach>
	</tbody>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>  