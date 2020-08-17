/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.export;

public class ExportAttributeDetailsApi {
	private String dateStr;
	private String phrase;
	private int investigationId;
	private String showResults;

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public int getInvestigationId() {
		return investigationId;
	}

	public void setInvestigationId(int investigationId) {
		this.investigationId = investigationId;
	}

	public String getShowResults() {
		return showResults;
	}

	public void setShowResults(String showResults) {
		this.showResults = showResults;
	}

}
