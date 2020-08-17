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
<openmrs:require privilege="Edit Laboratory Result" otherwise="/login.htm" redirect="/module/ehrlaboratory/editResult.form" />
<openmrs:globalProperty key="hospitalcore.hospitalName" defaultValue="ddu" var="hospitalName"/>
<%@ include file="../localHeader.jsp" %>

<style>
	.parameter {
		width: 280px;
		padding: 5px;
		margin: 5px;
		float: left;
		text-align: center;
		border: 1px solid black;
	}
</style>

<script type="text/javascript">
	
	jQuery(document).ready(function() {
		jQuery('#date').datepicker({yearRange:'c-30:c+30', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true,showOn: "button",
                buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
                buttonImageOnly: true});
		jQuery("#searchbox").showPatientSearchBox({				
			searchBoxView: "${hospitalName}/default",			
			resultView: "/module/ehrlaboratory/patientsearch/${hospitalName}/editresult",
			target: "#patientResult",
			beforeNewSearch: function(){
				jQuery("#patientSearchResultSection").hide();
			},
			success: function(data){
				jQuery("#patientSearchResultSection").show();
			}
		});	
    });
	
	/**
	 * GET ALL TESTS
	 * @patientIdentifier patient identifier
	 */	
	function getTests(patientIdentifier){
		var date = jQuery("#date").val();
		var phrase = patientIdentifier;
		var investigation = jQuery("#investigation").val();
		jQuery.ajax({
			type : "GET",
			url : getContextPath() + "/module/ehrlaboratory/searchCompletedTests.form",
			data : ({
				date			: date,
				phrase			: phrase,
				investigation	: investigation
			}),
			success : function(data) {				
				jQuery("#completedTests").html(data);
				jQuery("#patientSearchResultSection").hide();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(thrownError);
			}
		});
	}
	
	//  enter result for a test
	function enterResult(testId, conceptId, encounterId){
	
		jQuery(".resultParameter").hide();
		
		jQuery.ajax({
			type : "GET",
			url : getContextPath() + "/module/ehrlaboratory/showForm.form",
			data : ({
				encounterId : encounterId,
				conceptId	: conceptId
			}),
			success : function(data) {
				jQuery("#contentForm" + testId).html(data);				
				jQuery("#row" + testId).show();
				formatParameters();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(thrownError);
			}
		});
	}
	
	// format parameters for entering results
	function formatParameters(){
		jQuery(".parameter").hover(
			function(){
				jQuery(this).css("background-color", "#3399FF");
			}, 
			function(){
				jQuery(this).css("background-color", "#FFFFFF");
			}
		);
	}
	
	// submit form
	function submit(testId){
		validationResult = jQuery("#contentForm" + testId).valid();
		if(validationResult){
			jQuery("#contentForm" + testId).ajaxSubmit({
				success: function (responseText, statusText, xhr){					
					if(responseText.indexOf('success')>=0){						
						getTests();
						completeTest(testId);						
					}
				}
			});					
		}
	}
	
	/**
	 * SHOW PATIENT SEARCH RESULT
	 */
	function showPatientSearchResult(){
		jQuery('#patientSearchResultSection').show();
	}
</script> 

<div class="boxHeader"> 
	<strong>Get Patient List</strong>
</div>
<div class="box">
&nbsp;
	Date:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input id="date" value="${currentDate}" style="text-align:left; width:120px"/>
<br style="margin:15px">&nbsp;
	Investigation:&nbsp;
	<select name="investigation" id="investigation">
		<option value="0">Select an investigation</option>
		<c:forEach var="investigation" items="${investigations}">
			<option value="${investigation.id}">${investigation.name.name}</option>
		</c:forEach>	
	</select>
	
	<input type="button" onclick="javascript:showPatientSearchResult()" value="Get Result"/>
	
	<div id="searchbox"></div>

</div>

<br/>

<div id="patientSearchResultSection" style="display:none;">
	<div class="boxHeader">Patients Found</div>
	<div class="box" id="patientResult"></div>
</div>

<div id="completedTests">
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>  