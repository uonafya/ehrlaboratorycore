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
<c:choose>
	<c:when test='${duplicatedFormFound}'>
		<script type='text/javascript'>
			DUPLICATED_FORM = true;
		</script>
		<span style='color:red;'>Duplicated form with the same concept and type was found!</span>
		<c:forEach var='form' items='${duplicatedForms}'>
			<a href='editForm.form?id=${form.id}'>
				${form.name}
			</a>
		</c:forEach>
	</c:when>
	<c:otherwise>
		Fine!
		<script type='text/javascript'>
			DUPLICATED_FORM = false;
		</script>
	</c:otherwise>
</c:choose>