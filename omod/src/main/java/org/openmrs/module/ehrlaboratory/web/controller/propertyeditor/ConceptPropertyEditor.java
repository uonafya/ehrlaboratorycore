/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.controller.propertyeditor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrlaboratory.web.util.LaboratoryUtil;

public class ConceptPropertyEditor extends PropertyEditorSupport {
	private Log log = LogFactory.getLog(this.getClass());

	public ConceptPropertyEditor() {
	}

	public void setAsText(String text) throws IllegalArgumentException {
		Context.getConceptService();
		if (text != null && text.trim().length() > 0) {
			try {
				Concept concept = LaboratoryUtil.searchConcept(text);
				setValue(concept);
			} catch (Exception ex) {
				log.error("Error setting text: " + text, ex);
				throw new IllegalArgumentException("Concept not found: "
						+ ex.getMessage());
			}
		} else {
			setValue(null);
		}
	}

	public String getAsText() {
		Concept concept = (Concept) getValue();
		if (concept == null) {
			return null;
		} else {
			return concept.getName().getName();
		}
	}
}
