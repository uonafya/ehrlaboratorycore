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
<openmrs:require privilege="Print Laboratory Worklist" otherwise="/login.htm" redirect="/module/ehrlaboratory/printWorklist.form" />
<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Print Work List an Option in the investigation drop down list --%>
<openmrs:globalProperty key="laboratory.printworklist.findAllInvestigation" defaultValue="false" var="findAllInvestigation" />
<%@ include file="../localHeader.jsp" %>

<script type="text/javascript">
<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Print Work List an Option in the investigation drop down list --%>
var GLOBAL = {
		findAllInvestigation: ${findAllInvestigation}
	};

	jQuery(document).ready(function() {
		jQuery('#date').datepicker({yearRange:'c-30:c+30', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true,showOn: "button",
                buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
                buttonImageOnly: true});
    });
	
	// get all tests
	function getTests(){
		var date = jQuery("#date").val();
		var phrase = jQuery("#phrase").val();
		var investigation = jQuery("#investigation").val();
		<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Print Work List an Option in the investigation drop down list --%>
		validation = validate(investigation);	
		//ghanshyam 20/07/2012 New Requirement #320 [LABORATORY] Show Results as an Option
		var showResults=document.getElementById("showResults").checked;
		<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Print Work List an Option in the investigation drop down list --%>
		if(validation.status){			
			jQuery.ajax({
			type : "GET",
			url : getContextPath() + "/module/ehrlaboratory/searchPrintWorklist.form",
			data : ({
				date			: date,
				phrase			: phrase,
				investigation	: investigation,
				//ghanshyam 20/07/2012 New Requirement #320 [LABORATORY] Show Results as an Option
				showResults		: showResults
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
	} 
	<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Print Work List an Option in the investigation drop down list --%>
	else {
			alert(validation.message);
		}		
	}
	
	<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Work List an Option in the investigation drop down list --%>
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
		
		if(investigation>0){			
			validation.status = true;
		} 
		else if(investigation=="select"){
		        validation.status = false;
				validation.message = "Please select an investigation to proceed!";
		}
		else{
			if(GLOBAL.findAllInvestigation){
				validation.status = true;
			} 
			
		}		
		
		return validation;
	}
	
	function printWorklist(){
		jQuery("#printArea").printArea({
			mode : "popup",
			popClose : true
		});
	}
	
	// ghanshyam 24-sept-2012 New Requirement #361 [Laboratory] Export to Excel option in print worklist
	function exportWorklist(){
		var date = jQuery("#date").val();
		var phrase = jQuery("#phrase").val();
		var investigation = jQuery("#investigation").val();
		validation = validate(investigation);	
		var showResults=document.getElementById("showResults").checked;
		if(validation.status){			
		window.location = getContextPath() + "/module/ehrlaboratory/download.form?date=" + date + "&phrase=" + phrase + "&investigation=" + investigation + "&showResults=" + showResults;
	} 
	else {
			alert(validation.message);
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
	<%-- ghanshyam 09/07/2012 New Requirement #307 --%>
	<%--ghanshyam 8-august-2012  New Requirement #319 [LABORATORY] Make Consolidated Print Work List an Option in the investigation drop down list --%>
		<option value="select">Select</option>	
		<c:forEach var="investigation" items="${investigations}">
			<option value="${investigation.id}">${investigation.name.name}</option>
		</c:forEach>
		<option value="0">CONSOLIDATED LIST</option>	
	</select>
	<input type="button" value="Print worklist" onClick="printWorklist();"/>
	&nbsp;
	<%-- ghanshyam 24-sept-2012 New Requirement #361 [Laboratory] Export to Excel option in print worklist --%>
	<input type="button" value="Export worklist" onClick="exportWorklist();" />
	<br/>
	<input type="button" value="Get worklist" onClick="getTests();"/>
	<%-- ghanshyam 20/07/2012 New Requirement #320 [LABORATORY] Show Results as an Option --%>
	<input type="checkbox" name="showResults" id="showResults" checked="checked"> with results<BR>
</div>

<div id="tests">
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>  