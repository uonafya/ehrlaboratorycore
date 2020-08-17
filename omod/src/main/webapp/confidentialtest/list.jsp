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
<openmrs:require privilege="Manage Laboratory Confidential Test" otherwise="/login.htm" redirect="/module/laboratory/confidentialTest.form" />
<%@ include file="../localHeader.jsp" %>



<div class="boxHeader"> 
	<strong>Search patient</strong>
</div>
<div class="box">
	<div id="advSearch"></div>
</div>
<br/>
<div id="patientResult"></div>

<script>
	function showConfidentialTest(patientIdentifier){
		tb_show("confidential test", "addConfidentialTest.form?modal=true&width=600&height=320&patientIdentifier=" + patientIdentifier);
	}
	
	jQuery("#advSearch").toggleSearchBox({
		view:'laboratory_confidentialTest'
	});
</script>

<%@ include file="/WEB-INF/template/footer.jsp" %>  