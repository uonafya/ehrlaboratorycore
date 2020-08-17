/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.controller.worklist;

import java.util.Date;
import java.util.UUID;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingConstants;
import org.openmrs.module.hospitalcore.model.LabTest;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.ehrlaboratory.LaboratoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("LaboratoryEnterResultController")
@RequestMapping("/module/ehrlaboratory/enterResult.form")
public class EnterResultController {

	@RequestMapping(method = RequestMethod.GET)
	public String enterResult(
			@RequestParam(value = "testId") Integer testId,
			Model model) {
		LaboratoryService ls = (LaboratoryService) Context.getService(LaboratoryService.class);
		LabTest test = ls.getLaboratoryTest(testId);
		String encounterTypeStr = GlobalPropertyUtil.getString(BillingConstants.GLOBAL_PROPRETY_LAB_ENCOUNTER_TYPE, "LABENCOUNTER");
		EncounterType encounterType = Context.getEncounterService().getEncounterType(encounterTypeStr);
		Encounter enc = new Encounter();
		enc.setCreator(Context.getAuthenticatedUser());
		enc.setDateCreated(new Date());
		Location loc = Context.getLocationService().getLocation(1);		
		enc.setLocation(loc);
		enc.setPatient(test.getPatient());
		enc.setPatientId(test.getPatient().getId());		
		enc.setEncounterType(encounterType);
		enc.setVoided(false);
		enc.setProvider(Context.getAuthenticatedUser().getPerson());
		enc.setUuid(UUID.randomUUID().toString());		
		enc.setEncounterDatetime(new Date());		
		enc = Context.getEncounterService().saveEncounter(enc);		
		test.setEncounter(enc);
		ls.saveLaboratoryTest(test);
		return "redirect:/module/ehrlaboratory/showForm.form?encounterId=" + enc.getEncounterId() + "&conceptId=" + test.getConcept().getConceptId();
	}
}
