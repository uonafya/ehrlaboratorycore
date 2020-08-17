/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
*/
function changePageSize( baseLink )
{
	var pageSize = jQuery("#sizeOfPage").val();
	if(  !/^ *[0-9]+ *$/.test(pageSize) ){
		alert("Invalid number!");
	}else {
		window.location.href = baseLink +"pageSize=" + pageSize ;
	}
}

function jumpPage( baseLink )
{
	var pageSize = jQuery("#sizeOfPage").val();
	var currentPage = jQuery("#jumpToPage").val();
	if(  !/^ *[0-9]+ *$/.test(pageSize)  ||  !/^ *[0-9]+ *$/.test(currentPage) ){
		alert("Invalid number!");
	}else {
		window.location.href = baseLink +"pageSize=" + pageSize +"&currentPage=" +currentPage;
	}
}