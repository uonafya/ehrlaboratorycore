/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.controller.functionalstatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.model.BillableService;
import org.openmrs.module.ehrlaboratory.util.LaboratoryConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("LaboratoryFunctionalStatusController")
@RequestMapping("/module/ehrlaboratory/functionalStatus.form")
public class FunctionalStatus {

	@SuppressWarnings("unchecked")
	@ModelAttribute("billableServices")
	List<BillableService> getBillableServices(HttpServletRequest request) {
		Map<Concept, Set<Concept>> testTreeMap = (Map<Concept, Set<Concept>>) request
				.getSession().getAttribute(
						LaboratoryConstants.SESSION_TEST_TREE_MAP);
		Set<Concept> concepts = new HashSet<Concept>();
		for (Concept key : testTreeMap.keySet()) {
			concepts.addAll(testTreeMap.get(key));
		}
		BillingService bs = Context.getService(BillingService.class);
		List<BillableService> billableServices = new ArrayList<BillableService>();
		for (Concept concept : concepts) {
			BillableService s = bs
					.getServiceByConceptId(concept.getConceptId());
			if (s != null) {
				if (s.getPrice() != null)
					billableServices.add(s);
			}
		}
		return billableServices;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String firstView() {
		return "/module/ehrlaboratory/functionalstatus/list";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String updateBillableServices(
			@RequestParam(value = "serviceIds", required = false) String serviceIds,
			Model model, HttpServletRequest request) {

		if (!StringUtils.isBlank(serviceIds)) {
			String[] ids = serviceIds.split("[<>]");
			for (String id : ids) {
				try {
					if (!StringUtils.isBlank(id)) {
						Integer serviceId = Integer.parseInt(id);
						toggleBillableService(serviceId);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		model.addAttribute("billableServices", getBillableServices(request));
		return "/module/ehrlaboratory/functionalstatus/list";
	}

	private void toggleBillableService(Integer id) {
		BillingService bs = Context.getService(BillingService.class);
		BillableService s = bs.getServiceById(id);
		if (s != null) {
			s.setDisable(!s.getDisable());
			bs.saveService(s);
		}
	}
}
