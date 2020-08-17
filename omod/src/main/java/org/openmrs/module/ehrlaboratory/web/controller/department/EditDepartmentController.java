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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.LaboratoryCoreService;
import org.openmrs.module.hospitalcore.model.Lab;
import org.openmrs.module.ehrlaboratory.LaboratoryService;
import org.openmrs.module.ehrlaboratory.web.controller.propertyeditor.LaboratoryLabPropertyEditor;
import org.openmrs.module.ehrlaboratory.web.controller.propertyeditor.RolePropertyEditor;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("LaboratoryEditDepartmentController")
@RequestMapping("/module/ehrlaboratory/editDepartment.form")
public class EditDepartmentController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Lab.class,
				new LaboratoryLabPropertyEditor());
		binder.registerCustomEditor(Role.class, new RolePropertyEditor());
		binder.registerCustomEditor(Set.class, "investigationsToDisplay",
				new CustomCollectionEditor(Set.class) {
					ConceptService conceptService = Context.getConceptService();

					protected Object convertElement(Object element) {
						String conceptName = null;
						if (element instanceof String)
							conceptName = (String) element;
						return conceptName != null ? conceptService
								.getConcept(conceptName) : null;
					}
				});
		binder.registerCustomEditor(Set.class, "confidentialTestsToDisplay",
				new CustomCollectionEditor(Set.class) {
					ConceptService conceptService = Context.getConceptService();

					protected Object convertElement(Object element) {
						String conceptName = null;
						if (element instanceof String)
							conceptName = (String) element;
						return conceptName != null ? conceptService
								.getConcept(conceptName) : null;
					}
				});
	}

	@ModelAttribute("department")
	public Lab getDepartment(
			@RequestParam(value = "id", required = false) Integer id) {
		Lab department = new Lab();
		if (id != null) {
			LaboratoryService ls = (LaboratoryService) Context
					.getService(LaboratoryService.class);
			department = ls.getLaboratoryDepartment(id);
		}
		return department;
	}

	@ModelAttribute("roles")
	public List<Role> getRoles(
			@RequestParam(value = "id", required = false) Integer id) {		
		List<Role> roles = Context.getUserService().getAllRoles();
		LaboratoryCoreService lcs = Context.getService(LaboratoryCoreService.class);
		List<Lab> departments = lcs.getAllDepartments();

		// get all existing roles
		Set<Role> existingRoles = new HashSet<Role>();
		for (Lab d : departments) {
			if (!d.getLabId().equals(id)) {
				existingRoles.add(d.getRole());
			} else {
				System.out.println("department ==> " + d);
			}
		}

		// eliminate them from all roles list
		for (Role r : existingRoles) {
			roles.remove(r);
		}

		return roles;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showDepartment(
			@RequestParam(value = "id", required = false) Integer id,
			Model model) {
		return "/module/ehrlaboratory/department/edit";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveForm(
			@ModelAttribute("department") Lab department,
			Model model) {
		LaboratoryService ls = (LaboratoryService) Context
				.getService(LaboratoryService.class);
		ls.saveLaboratoryDepartment(department);
		return "redirect:/module/ehrlaboratory/listDepartment.form";
	}
}
