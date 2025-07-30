/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.drc;

import org.openmrs.api.context.Context;
import org.openmrs.api.context.ServiceContext;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.drc.initializers.DrcInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class DRCActivator extends BaseModuleActivator {
	
	private static final Logger log = LoggerFactory.getLogger(DRCActivator.class);
	
	/**
	 * @see #started()
	 */
	public void started() {
		List<DrcInitializer> initializers = ServiceContext.getInstance().getRegisteredComponents(DrcInitializer.class);
		MessageSourceService messageSourceService = Context.getMessageSourceService();
		for (DrcInitializer initializer : initializers) {
			try {
				initializer.initialize();
			}
			catch (Exception e) {
				log.error(messageSourceService.getMessage("drc.initializer.error"), initializer.getName(), e);
			}
		}
	}
	
	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
	}
	
}
