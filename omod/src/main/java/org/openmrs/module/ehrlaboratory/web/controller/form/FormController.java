/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.controller.form;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.LabTest;
import org.openmrs.module.ehrlaboratory.LaboratoryService;
import org.openmrs.module.ehrlaboratory.util.LaboratoryUtil;
import org.openmrs.module.ehrlaboratory.util.ParameterModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("LaboratoryShowFormController")
@RequestMapping("/module/ehrlaboratory/showForm.form")
public class FormController {

	/**
	 * Show for to enter result
	 * 
	 * @param encounterId
	 * @param conceptId
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "encounterId") Integer encounterId,
			@RequestParam(value = "conceptId") Integer conceptId, Model model) {

		Concept concept = Context.getConceptService().getConcept(conceptId);
		List<ParameterModel> parameters = new ArrayList<ParameterModel>();
		LaboratoryUtil.generateParameterModels(parameters, concept);

		Encounter encounter = Context.getEncounterService().getEncounter(
				encounterId);

		model.addAttribute("parameters", parameters);
		model.addAttribute("encounterId", encounter.getEncounterId());
		LaboratoryUtil.generateDataFromEncounter(model, encounter);
		return "/module/ehrlaboratory/form/show";
	}

	/*
	 * Generate parameter list
	 */
	

	/**
	 * Save form values into an existing encounter
	 * 
	 * @param request
	 * @param encounterId
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String saveEncounter(HttpServletRequest request,
			@RequestParam("encounterId") Integer encounterId, Model model) {
		Map<String, String> parameters = buildParameterList(request);
		Encounter encounter = Context.getEncounterService().getEncounter(
				encounterId);

		LabTest test = Context.getService(LaboratoryService.class)
				.getLaboratoryTest(encounter);

		if (encounter != null) {
			for (String key : parameters.keySet()) {
				Concept concept = LaboratoryUtil.searchConcept(key);
				Obs obs = insertValue(encounter, concept, parameters.get(key),
						test);
				if (obs.getId() == null)
					encounter.addObs(obs);
			}
			Context.getEncounterService().saveEncounter(encounter);
			model.addAttribute("status", "success");
			return "/module/ehrlaboratory/form/enterForm";
		}
		model.addAttribute("status", "fail");
		return "/module/ehrlaboratory/form/enterForm";

	}

	@SuppressWarnings("rawtypes")
	private Map<String, String> buildParameterList(HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
			String parameterName = (String) e.nextElement();
			if (!parameterName.equalsIgnoreCase("id"))
				if (!parameterName.equalsIgnoreCase("mode"))
					if (!parameterName.equalsIgnoreCase("encounterId"))
						parameters.put(parameterName,
								request.getParameter(parameterName));

		}
		return parameters;
	}

	private Obs insertValue(Encounter encounter, Concept concept, String value,
			LabTest test) {

		Obs obs = getObs(encounter, concept);
		obs.setConcept(concept);
		obs.setOrder(test.getOrder());
		if (concept.getDatatype().getName().equalsIgnoreCase("Text") || concept.getDatatype().getName().equalsIgnoreCase("Numeric")) {
			obs.setValueText(value);
		} else if (concept.getDatatype().getName().equalsIgnoreCase("Coded")) {
			Concept answerConcept = LaboratoryUtil.searchConcept(value);
			obs.setValueCoded(answerConcept);
		}
		return obs;
	}

	private Obs getObs(Encounter encounter, Concept concept) {
		for (Obs obs : encounter.getAllObs()) {
			if (obs.getConcept().equals(concept))
				return obs;
		}
		return new Obs();
	}

}
