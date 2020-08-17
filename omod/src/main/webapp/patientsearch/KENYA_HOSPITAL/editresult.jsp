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
<%@ page import="java.util.Map" %>
<%@ page import="org.openmrs.Patient" %>

<script type="text/javascript">
	
	PATIENTSEARCHRESULT = {
		oldBackgroundColor: ""
	};
	
	jQuery(document).ready(function(){
	
		// hover rows
		jQuery(".patientSearchRow").hover(
			function(event){					
				obj = event.target;
				while(obj.tagName!="TR"){
					obj = obj.parentNode;
				}
				PATIENTSEARCHRESULT.oldBackgroundColor = jQuery(obj).css("background-color");
				jQuery(obj).css("background-color", "#00FF99");									
			}, 
			function(event){
				obj = event.target;
				while(obj.tagName!="TR"){
					obj = obj.parentNode;
				}
				jQuery(obj).css("background-color", PATIENTSEARCHRESULT.oldBackgroundColor);				
			}
		);
	});
</script>

<c:choose>
	<c:when test="${not empty patients}" >		
	<table style="width:100%">
		<tr>			
			<td align="center"><b>Patient ID</b></td>
			<td align="center"><b>Name</b></td>
			<td align="center"><b>Age</b></td>
			<td align="center"><b>Gender</b></td>			
			<!-- <td><b>Birthdate</b></td>
			<td><b>Relative Name</b></td>
			<td><b>Phone Number</b></td> -->
		</tr>
		<c:forEach items="${patients}" var="patient" varStatus="varStatus">
			<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } patientSearchRow' onClick="getTests('${patient.patientIdentifier.identifier}');">				
				<td align="center">
					${patient.patientIdentifier.identifier}
				</td>
				<td align="center">${patient.givenName}  ${patient.familyName} ${fn:replace(patient.middleName,',',' ')}</td>
				<td align="center"> 
                	<c:choose>
                		<c:when test="${patient.age == 0}">&lt 1</c:when>
                		<c:otherwise >${patient.age}</c:otherwise>
                	</c:choose>
                </td>
				<%-- <td>
					<c:choose>
                		<c:when test="${patient.gender eq 'M'}">
							<img src="${pageContext.request.contextPath}/images/male.gif"/>
						</c:when>
                		<c:otherwise><img src="${pageContext.request.contextPath}/images/female.gif"/></c:otherwise>
                	</c:choose>
				</td>  --%>
				<td align="center">
					${patient.gender}
				</td>                
				<%-- <td> 
                	<openmrs:formatDate date="${patient.birthdate}"/>
                </td>
				<td> 
                	<%
						Patient patient = (Patient) pageContext.getAttribute("patient");
						Map<Integer, Map<Integer, String>> attributes = (Map<Integer, Map<Integer, String>>) pageContext.findAttribute("attributeMap");						
						Map<Integer, String> patientAttributes = (Map<Integer, String>) attributes.get(patient.getPatientId());						
						String relativeName = patientAttributes.get(8); 
						if(relativeName!=null)
							out.print(relativeName);
					%>
                </td>
                <td> 
                	<%						
						String phoneNumber = patientAttributes.get(16);
						if(phoneNumber!=null)
							out.print(phoneNumber);					
					%>
                </td> --%>
			</tr>
		</c:forEach>
	</table>
	</c:when>
	<c:otherwise>
	No Patient found.
	</c:otherwise>
</c:choose>