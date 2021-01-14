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

<openmrs:require privilege="Manage Radiology" otherwise="/login.htm" redirect="/module/ehrlaboratory/editDepartment.form" />
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<br/>
<%@ include file="../localHeader.jsp" %>

<h2>Manage Department</h2>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#investigationConceptAutocomplete").autocomplete(getContextPath() + '/module/ehrlaboratory/ajax/autocompleteConceptSearch.htm').result(function(event, item){
			insertInvestigationConcept(item);
		});
		jQuery("#confidentialConceptAutocomplete").autocomplete(getContextPath() + '/module/ehrlaboratory/ajax/autocompleteConceptSearch.htm').result(function(event, item){
			insertConfidentialConcept(item);
		});
		
    });
	
	function insertInvestigationConcept(item){
		jQuery("#investigationNames").append("<option value='" + item + "'>" + item + "</option>");
		jQuery("#investigationConceptAutocomplete").val('');
	}
	
	function deleteInvestigationConcept() {
		options = jQuery('#investigationNames option:checked').each(function(index, element){
			e = jQuery(this);
			e.remove();
		});
	}
	
	function insertConfidentialConcept(item){
		jQuery("#confidentialNames").append("<option value='" + item + "'>" + item + "</option>");
		jQuery("#confidentialConceptAutocomplete").val('');
	}
	
	function deleteConfidentialConcept() {
		options = jQuery('#confidentialNames option:checked').each(function(index, element){
			e = jQuery(this);
			e.remove();
		});
	}

	function submitForm(){
		var investigations = document.getElementById("investigationNames");
		for( var i=0; i< investigations.childNodes.length; i++ ){
			investigations.childNodes[i].selected=true;
		}
		var confidentials = document.getElementById("confidentialNames");
		for( var k=0; k< confidentials.childNodes.length; k++ ){
			confidentials.childNodes[i].selected=true;
		}
		document.forms["departmentForm"].submit(); 
	}
</script>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span>
</c:forEach>
<spring:bind path="department">
	<c:if test="${not empty status.errorMessages}">
		<div class="error">
			<ul>
				<c:forEach items="${status.errorMessages}" var="error">
					<li>${error}</li>   
				</c:forEach>
			</ul>
		</div>
	</c:if>
</spring:bind>

<form method="post" class="box" id="departmentForm" onsubmit="return false;">
<table>
	<tr>
		<td>Name</td>
		<td>
			<spring:bind path="department.name">
				<input type="text" tabindex="1"  name="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top">Description</td>
		<td>
			<spring:bind path="department.description">
				<input type="text" tabindex="2"  name="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind> 
		</td>
	</tr>
	<tr>
		<td valign="top">Role</td>
		<td>			
			<spring:bind path="department.role">
			<select name="${status.expression}" tabindex="3" >
				<option value=""><spring:message code="hospitalcore.pleaseSelect"/></option>
                <c:forEach items="${roles}" var="role">
					<c:choose>
						<c:when test="${role.role eq department.role.role}">
							<c:set var="selected" value="selected='selected'"/>
						</c:when>
						<c:otherwise>
							<c:set var="selected" value=""/>
						</c:otherwise>
					</c:choose>					
                    <option value="${role.role}" ${selected}>${role.role}</option>
                </c:forEach>
   			</select>
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top">Investigations</td>
		<td><input type='text' id='investigationConceptAutocomplete' style="width:290px;"/><input type='button' value='Delete' onClick='deleteInvestigationConcept();'/></td>
	</tr>
	<tr>
		<td></td>
		<td>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top">
						<spring:bind path="department.investigationsToDisplay">
							<select class="largeWidth" size="6" name="${status.expression}" id="investigationNames" multiple="multiple">
								<c:forEach items="${department.investigationsToDisplay}" var="item">
									<option value="${item.conceptId}">${item.name}</option>
								</c:forEach>
							</select>
							<c:if test="${not empty status.errorMessage}"><span class="error">${status.errorMessage}</span></c:if>
						</spring:bind>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">Confidential Tests</td>
		<td><input type='text' id='confidentialConceptAutocomplete' style="width:290px;"/><input type='button' value='Delete' onClick='deleteConfidentialConcept();'/></td>
	</tr>
	<tr>
		<td></td>
		<td>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top">
						<spring:bind path="department.confidentialTestsToDisplay">
							<select class="largeWidth" size="6" name="${status.expression}" id="confidentialNames" multiple="multiple">
								<c:forEach items="${department.confidentialTestsToDisplay}" var="item">
									<option value="${item.conceptId}">${item.name}</option>
								</c:forEach>
							</select>
							<c:if test="${not empty status.errorMessage}"><span class="error">${status.errorMessage}</span></c:if>
						</spring:bind>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br/>
<input type="button" onclick="submitForm()" value="<spring:message code="general.save"/>">
<input type="button" value="<spring:message code="general.cancel"/>" onclick="javascript:window.location.href='listDepartment.form'">
</form>
<%@ include file="/WEB-INF/template/footer.jsp"%>