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
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<table border='0'>
	<tr>
		<td style="text-align:left;"><b>Identifier</b></td>
		<td>:${patient_identifier}</td>
		<td style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td style="text-align:left;"><b>Age</b></td>
		<td>:${patient_age}</td>
		<td style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;</td>		
		<td style="text-align:left;"><b>Gender</b></td>
		<td><c:choose>
				<c:when test="${patient_gender eq 'M'}">:Male</c:when>
				<c:otherwise>:Female</c:otherwise>
			</c:choose>
			
		</td>
	</tr>
	<tr>		
		<td style="text-align:left;"><b>Name</b></td>
		<td>:${patient_name}</td>
		<td style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td style="text-align:left;"><b>Order date<b></td>
		<td>:${test_orderDate}</td>
		<td></td>
		<td></td>
	</tr>
</table>