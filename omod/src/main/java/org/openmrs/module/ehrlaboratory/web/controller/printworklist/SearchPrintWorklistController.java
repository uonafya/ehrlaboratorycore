/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.controller.printworklist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.LabTest;
import org.openmrs.module.ehrlaboratory.LaboratoryService;
import org.openmrs.module.ehrlaboratory.util.LaboratoryConstants;
import org.openmrs.module.ehrlaboratory.web.util.LaboratoryUtil;
import org.openmrs.module.ehrlaboratory.web.util.TestModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("LaboratorySearchPrintWorklistController")
@RequestMapping("/module/ehrlaboratory/searchPrintWorklist.form")
public class SearchPrintWorklistController {

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String searchTest(
			@RequestParam(value = "date", required = false) String dateStr,
			@RequestParam(value = "phrase", required = false) String phrase,
			@RequestParam(value = "investigation", required = false) Integer investigationId,
			@RequestParam(value = "showResults", required = false) String showResults,
			HttpServletRequest request, Model model) {
		LaboratoryService ls = (LaboratoryService) Context
				.getService(LaboratoryService.class);
		Concept investigation = Context.getConceptService().getConcept(
				investigationId);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
			Map<Concept, Set<Concept>> testTreeMap = (Map<Concept, Set<Concept>>) request
					.getSession().getAttribute(
							LaboratoryConstants.SESSION_TEST_TREE_MAP);
			Set<Concept> allowableTests = new HashSet<Concept>();
			if (investigation != null) {
				allowableTests = testTreeMap.get(investigation);
			} else {
				for (Concept c : testTreeMap.keySet()) {
					allowableTests.addAll(testTreeMap.get(c));
				}
			}
			List<LabTest> laboratoryTests = ls.getAllLaboratoryTestsByDate(
					date, phrase, allowableTests);
			// ghanshyam 19/07/2012 New Requirement #309: [LABORATORY] Show Results in Print WorkList.introduced the column 'Lab' 'Test' 'Test name' 'Result'
			//ghanshyam 20/07/2012 New Requirement #320 [LABORATORY] Show Results as an Option.added one more parameter in method generateModelsForPrintWorkListFromTests showResults 
			List<TestModel> tests = LaboratoryUtil.generateModelsForPrintWorkListFromTests(
					laboratoryTests, testTreeMap,showResults);
			// ghanshyam 04/07/2012 New Requirement #277
			Collections.sort(tests);
			model.addAttribute("tests", tests);
			model.addAttribute("testNo", tests.size());
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Error when parsing order date!");
			return null;
		}
		return "/module/ehrlaboratory/printworklist/search";
	}

}
