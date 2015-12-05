 <%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Laboratory module.
 *
 *  Laboratory module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Laboratory module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Laboratory module.  If not, see <http://www.gnu.org/licenses/>.
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