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
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<br/>
<openmrs:require privilege="Manage Laboratory Worklist" otherwise="/login.htm" redirect="/module/ehrlaboratory/worklist.form" />
<openmrs:globalProperty key="laboratory.worklist.findAllInvestigation" defaultValue="false" var="findAllInvestigation" />
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
	
	.chosen {		
		background-color:yellow;
	}
</style>

<script type="text/javascript">

	var GLOBAL = {
		findAllInvestigation: ${findAllInvestigation}
	};
	
	jQuery(document).ready(function() {
		jQuery('#date').datepicker({yearRange:'c-30:c+30', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true,
		showOn: "button",		
		 buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
         buttonImageOnly: true
		 });		
    });
	
	
	/**
	 * GET ALL TESTS
	 */
	function getTests(){
		var date = jQuery("#date").val();
		var phrase = jQuery("#phrase").val();
		var investigation = jQuery("#investigation").val();
		
		validation = validate(investigation);		
		
		// GETTING THE TESTS
		if(validation.status){			
			jQuery.ajax({
				type : "GET",
				url : getContextPath() + "/module/ehrlaboratory/searchWorklist.form",
				data : ({
					date			: date,
					phrase			: phrase,
					investigation	: investigation
				}),
				success : function(data) {
					jQuery("#tests").html(data);	
					if(testNo>0){					
						tb_init("a.thickbox"); // init to show thickbox
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(thrownError);
				}
			});
		} else {
			alert(validation.message);
		}		
	}
	
	/*
	 * Check whether user is allowed to get tests from all investigations
	 * return @validated {status, message}
	 */
	function validate(investigation){
	//alert(investigation);
		var validation = {
			status: "true",
			message: "fine"
		};
		
		<!-- ghanshyam 07/07/2012 New Requirement #307 Generate Consolidated Work List in laboratory module-->
		
		if(investigation>0){			
			validation.status = true;
		} 
		<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Work List an Option in the investigation drop down list --%>
		else if(investigation=="select"){
		        validation.status = false;
				validation.message = "Please select an investigation!";
		}
		else{
			if(GLOBAL.findAllInvestigation){
				validation.status = true;
			} 
			
		}		
		
		return validation;
	}
	
	// reschedule a test
		function rescheduleTest(orderId, rescheduledDate) {
			validateRescheduleDate(orderId, rescheduledDate);
		}
		
		// validate reschedule date
		function validateRescheduleDate(orderId, rescheduledDate){			
			validateRescheduleDateResult = false;
			jQuery.ajax({
				type : "GET",
				url : getContextPath() + "/module/ehrlaboratory/ajax/validateRescheduleDate.htm",
				data : ({				
					rescheduleDate : rescheduledDate
				}),
				success : function(data) {
					
					if (data.indexOf('success')>=0) {						
						jQuery.ajax({
							type : "POST",
							url : getContextPath() + "/module/ehrlaboratory/rescheduleTest.form",
							data : ({
								orderId : orderId,
								rescheduledDate : rescheduledDate
							}),
							success : function(data) {
								if (data.indexOf('success')>=0) {
									getTests();
								} else {
									alert(data);
								}
							},
							error : function(xhr, ajaxOptions, thrownError) {
								alert("ERROR " + xhr);
							}
						});
						tb_remove();
					} else {
						alert('Invalid reorder date! It must be after the current date');
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
					
				}
			});		
		}
	
	// complete a test
	function completeTest(testId) {		

		jQuery.ajax({
			type : "GET",
			url : getContextPath() + "/module/ehrlaboratory/ajax/completeTest.htm",
			data : ({
				testId : testId
			}),
			success : function(data) {
				if (data.indexOf('success')>=0) {
					getTests();
				} else {
					
				}
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(thrownError);
			}
		});
	}
	
	//  enter result for a test
	function enterResult(testId){
		
		jQuery(".resultParameter").hide();
		
		jQuery.ajax({
			type : "GET",
			url : getContextPath() + "/module/ehrlaboratory/enterResult.form",
			data : ({
				testId : testId
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
	
</script> 

<div class="boxHeader"> 
	<strong>Get Patient List</strong>
</div>
<div class="box">
	Date:
	<input id="date" value="${currentDate}" style="text-align:right;"/>
	Patient Identifier/Name:
	<input id="phrase"/>
	Investigation:
	<select name="investigation" id="investigation">
	<%-- ghanshyam 07/07/2012 New Requirement #307 Generate Consolidated Work List in laboratory module --%>
	<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Work List an Option in the investigation drop down list --%>
		<option value="select">Select</option>	
		<c:forEach var="investigation" items="${investigations}">
			<option value="${investigation.id}">${investigation.name.name}</option>
		</c:forEach>
		<option value="0">CONSOLIDATED LIST</option>	
	</select>
	<br/>
	<input type="button" value="Get worklist" onClick="getTests();"/>
</div>

<div id="tests">
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>  