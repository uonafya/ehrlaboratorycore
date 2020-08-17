/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Concept;
import org.openmrs.ConceptWord;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.LabTest;
import org.openmrs.module.hospitalcore.util.PatientUtils;
import org.openmrs.module.ehrlaboratory.LaboratoryService;
import org.openmrs.module.ehrlaboratory.util.LaboratoryConstants;
import org.openmrs.module.ehrlaboratory.web.util.LaboratoryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("LaboratoryAjaxController")
public class AjaxController {

	/**
	 * Accept a test
	 * 
	 * @param orderId
	 * @param model
	 * @return id of accepted laboratory test
	 */
	@RequestMapping(value = "/module/ehrlaboratory/ajax/acceptTest.htm", method = RequestMethod.GET)
	public String acceptTest(@RequestParam("orderId") Integer orderId,
			@RequestParam("date") String dateStr,
			@RequestParam("sampleId") String sampleId, Model model) {
		Order order = Context.getOrderService().getOrder(orderId);
		if (order != null) {
			try {
				LaboratoryService ls = (LaboratoryService) Context
						.getService(LaboratoryService.class);
				Integer acceptedTestId = ls.acceptTest(order, sampleId);
				model.addAttribute("acceptedTestId", acceptedTestId);
				if (acceptedTestId > 0) {
					model.addAttribute("status", "success");
				} else {
					model.addAttribute("status", "fail");
					if (acceptedTestId.equals(LaboratoryConstants.ACCEPT_TEST_RETURN_ERROR_EXISTING_SAMPLEID)) {
						model.addAttribute("error", "Existing sample id found");
					} else if (acceptedTestId == LaboratoryConstants.ACCEPT_TEST_RETURN_ERROR_EXISTING_TEST) {
						model.addAttribute("error",
								"Existing accepted test found");
					}
				}
			} catch (Exception e) {
				model.addAttribute("acceptedTestId", "0");
			}
		}
		return "/module/ehrlaboratory/ajax/acceptTest";
	}

	/**
	 * Complete a test
	 * 
	 * @param testId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/module/ehrlaboratory/ajax/completeTest.htm", method = RequestMethod.GET)
	public String completeTest(@RequestParam("testId") Integer testId,
			Model model) {
		LaboratoryService ls = (LaboratoryService) Context
				.getService(LaboratoryService.class);
		LabTest test = ls.getLaboratoryTest(testId);
		String completeStatus = ls.completeTest(test);
		model.addAttribute("completeStatus", completeStatus);
		return "/module/ehrlaboratory/ajax/completeTest";
	}

	/**
	 * Show patient/test information when showing on patient report page
	 * 
	 * @param patientIdentifier
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/module/ehrlaboratory/ajax/showTestInfo.htm", method = RequestMethod.GET)
	public String showTestInfo(
			@RequestParam("patientId") Integer patientId,			
			@RequestParam(value = "orderDate", required = false) String orderDate,
			Model model) {
		Patient patient = Context.getPatientService().getPatient(
				patientId);
		if (patient!=null) {
			model.addAttribute("patient_identifier", patient
					.getPatientIdentifier().getIdentifier());
			model.addAttribute("patient_age", patient.getAge());
			model.addAttribute("patient_gender", patient.getGender());
			model.addAttribute("patient_name", PatientUtils.getFullName(patient).replace(","," "));
			model.addAttribute("test_orderDate", orderDate);
		}
		return "/module/ehrlaboratory/ajax/showTestInfo";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/module/ehrlaboratory/ajax/getDefaultSampleId.htm", method = RequestMethod.GET)
	public void getDefaultSampleId(@RequestParam("orderId") Integer orderId,
			HttpServletResponse response, HttpServletRequest request) throws IOException, ParseException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Map<Concept, Set<Concept>> testTreeMap = (Map<Concept, Set<Concept>>) request
		.getSession().getAttribute(
				LaboratoryConstants.SESSION_TEST_TREE_MAP);
		Order order = Context.getOrderService().getOrder(orderId);
		LaboratoryService ls = (LaboratoryService) Context
				.getService(LaboratoryService.class);
		out.print(ls.getDefaultSampleId(LaboratoryUtil.getInvestigationName(
				order.getConcept(), testTreeMap)));
	}
	
	/**
	 * Concept search autocomplete for form
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/module/ehrlaboratory/ajax/autocompleteConceptSearch.htm", method = RequestMethod.GET)
	public String autocompleteConceptSearch(
			@RequestParam(value = "q", required = false) String name,
			Model model) {
		List<ConceptWord> cws = Context.getConceptService().findConcepts(name,
				new Locale("en"), false);
		Set<String> conceptNames = new HashSet<String>();
		for (ConceptWord word : cws) {
			String conceptName = word.getConcept().getName().getName();
			conceptNames.add(conceptName);
		}
		List<String> concepts = new ArrayList<String>();
		concepts.addAll(conceptNames);
		Collections.sort(concepts, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		});
		model.addAttribute("conceptNames", concepts);
		return "/module/ehrlaboratory/ajax/autocompleteConceptSearch";
	}
	
	@RequestMapping(value = "/module/ehrlaboratory/ajax/validateRescheduleDate.htm", method = RequestMethod.GET)
	public void validateRescheduleDate(@RequestParam("rescheduleDate") String rescheduleDateStr, HttpServletResponse response) throws IOException, ParseException{
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		Date rescheduleDate = LaboratoryUtil.parseDate(rescheduleDateStr + " 00:00:00");
		Date now = new Date();
		String currentDateStr = LaboratoryUtil.formatDate(now) + " 00:00:00";
		Date currentDate = LaboratoryUtil.parseDate(currentDateStr);
		if(rescheduleDate.after(currentDate))
			writer.print("success");
		else
			writer.print("fail");
	}
}
