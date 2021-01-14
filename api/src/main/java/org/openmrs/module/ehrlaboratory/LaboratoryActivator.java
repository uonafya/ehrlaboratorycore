/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.ModuleActivator;
import org.openmrs.module.ehrlaboratory.util.SetupLabTestsMetadata;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class LaboratoryActivator implements ModuleActivator {
	
	private Log log = LogFactory.getLog(this.getClass());

	public void contextRefreshed() {
		// TODO Auto-generated method stub
		
	}

	public void started() {
		//call the lab service here to save the object
		SetupLabTestsMetadata.getAllInvestigationsSaved();
		log.info("Started Laboratory module");
	}

	public void stopped() {
		log.info("Stopped Laboratory module");		
	}

	public void willRefreshContext() {
		// TODO Auto-generated method stub
		
	}

	public void willStart() {
		// TODO Auto-generated method stub
		
	}

	public void willStop() {
		// TODO Auto-generated method stub
		
	}
	
}
