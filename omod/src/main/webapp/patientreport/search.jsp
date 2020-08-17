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
<hr/>
<div id="patientReportTestInfo"></div>
<table class="tablesorter" style="width:100%">
	<thead>
		<tr>		
			<th style="width:40%"><center>Test</center></th>
			<th style="width:10%"><center>Result</center></th>
			<th style="width:10%"><center>Units</center></th>
			<th style="width:40%"><center>Reference Range</center></th> 
		</tr>
	</thead>	
	<tbody>
<c:forEach var="test" items="${tests}">
	<tr>		
		<td>
			<c:if test="${test.level eq 'LEVEL_INVESTIGATION'}"><b>${test.investigation}</b></c:if>
			<c:if test="${test.level eq 'LEVEL_SET'}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				${test.set}
			</c:if>
			<c:if test="${test.level eq 'LEVEL_TEST'}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${test.test}</c:if>  
		</td>
		<td>${test.value}</td>
		<td>${test.unit}</td>
		<td>
			<c:if test="${not empty test.lowNormal or not empty test.hiNormal}">
			Adult/Male:${test.lowNormal}//${test.hiNormal}
			</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${not empty test.lowNormal or not empty test.hiNormal}">
			Female:${test.lowCritical}//${test.hiCritical}
			</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${not empty test.lowNormal or not empty test.hiNormal}">
			Child:${test.lowAbsolute}//${test.hiAbsolute}
			</c:if>
		</td>
	</tr>	
</c:forEach>
	</tbody>
</table>

<div id="patientReportPrintArea" style="display: none;">
	<style>
		table.wltable {
			
			font-family: Verdana, 'Lucida Grande', 'Trebuchet MS', Arial, Sans-Serif;			
			font-style: normal;
			font-size: 10px;
			
		}

		table.wltable th {
			border-bottom: 1px solid;
		}

		table.wltable td {
			padding: 0px 5px 0px 5px;
		}

		table.wltable .right {
			border-right: 1px solid;
		}
	</style>
	<div id="printAreaTestInfo"></div><br/><br/>
	<table class="wltable" cellspacing="0" style="width:100%; border: 1px solid; margin-left: auto; margin-right: auto;">
		<thead>
			<tr>
				<th class="right" style="width:40%"><center>Test</center></th>
				<th class="right" style="width:10%"><center>Result</center></th>
				<th class="right" style="width:10%"><center>Units</center></th>
				<th style="width:40%"><center>Reference Range</center></th> 
			</tr>
		</thead>	
		<tbody>
	<c:forEach var="test" items="${tests}">
		<tr>		
			<td class="right">&nbsp;
				<c:if test="${test.level eq 'LEVEL_INVESTIGATION'}"><b>${test.investigation}</b></c:if>
				<c:if test="${test.level eq 'LEVEL_SET'}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${test.set}
				</c:if>
				<c:if test="${test.level eq 'LEVEL_TEST'}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${test.test}</c:if>  
			</td>
			<td class="right">${test.value}&nbsp;</td>
			<td class="right">${test.unit}&nbsp;</td>
			<td>&nbsp;
			<c:if test="${not empty test.lowNormal or not empty test.hiNormal}">
			Adult/Male:${test.lowNormal}//${test.hiNormal}
			</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${not empty test.lowNormal or not empty test.hiNormal}">
			Female:${test.lowCritical}//${test.hiCritical}
			</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${not empty test.lowNormal or not empty test.hiNormal}">
			Child:${test.lowAbsolute}//${test.hiAbsolute}
			</c:if>
			</td>
		</tr>	
	</c:forEach>
		</tbody>
	</table>
	<div style="text-align: right; margin-top: 30px; margin-right: 150px;">Signature</div>
</div>