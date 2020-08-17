/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.controller.department;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.Lab;
import org.openmrs.module.ehrlaboratory.LaboratoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("LaboratoryDeleteDepartmentController")
@RequestMapping("/module/ehrlaboratory/deleteDepartment.form")
public class DeleteDepartmentController {

	@RequestMapping(method = RequestMethod.GET)
	public String deleteForm(@RequestParam("id") Integer id,
			Model model) {
		LaboratoryService ls = (LaboratoryService) Context.getService(LaboratoryService.class);
		Lab department = ls.getLaboratoryDepartment(id);
		if(department!=null){
			ls.deleteLaboratoryDepartment(department);
		}
		return "redirect:/module/ehrlaboratory/listDepartment.form";
	}

}
