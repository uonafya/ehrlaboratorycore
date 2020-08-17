/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.export.controller;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openmrs.module.ehrlaboratory.web.export.DownloadService;
import org.openmrs.module.ehrlaboratory.web.export.ExportAttributeDetailsApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller("LaboratoryExportWorkListController")
@SessionAttributes("adts")
public class ExportPrintWorklistController {
	@SuppressWarnings("restriction")
	@Resource(name = "downloadService")
	private DownloadService downloadService;

	@RequestMapping(value = "/module/ehrlaboratory/download.form", method = RequestMethod.GET)
	public void excelExport(
			@RequestParam(value = "date", required = false) String dateStr,
			@RequestParam(value = "phrase", required = false) String phrase,
			@RequestParam(value = "investigation", required = false) Integer investigationId,
			@RequestParam(value = "showResults", required = false) String showResults,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws ClassNotFoundException, ParseException {
		ExportAttributeDetailsApi adts = new ExportAttributeDetailsApi();
		adts.setDateStr(dateStr);
		adts.setPhrase(phrase);
		adts.setInvestigationId(investigationId);
		adts.setShowResults(showResults);
		model.addAttribute(adts);

		// Delegate to downloadService.
		downloadService.downloadXLS(adts, request, response);
	}
}