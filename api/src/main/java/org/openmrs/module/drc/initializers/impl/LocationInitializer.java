package org.openmrs.module.drc.initializers.impl;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.drc.initializers.RunnableDrcInitializer;
import org.openmrs.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.openmrs.module.drc.DRCConstants.DRC_INSTANCE_PROPERTY;
import static org.openmrs.module.drc.DRCConstants.DRC_LOCATION_ATTRIBUTE_TYPE_UUID;

public class LocationInitializer implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(LocationInitializer.class);
	
	@Override
	public void run() {
		// To translate messages we display anywhere
		final MessageSourceService messageSourceService = Context.getService(MessageSourceService.class);
		
		// We load the property, "drc.instance" via ConfigUtil, which means this can be set as a system property,
		// a runtime property or a global property. However, for simplicity, we only explain setting up the runtime
		// property
		String drcLocation = ConfigUtil.getProperty(DRC_INSTANCE_PROPERTY);
		if (StringUtils.isNotBlank(drcLocation)) {
			log.info(messageSourceService.getMessage("drc.location.configured"), drcLocation);
			
			LocationService locationService = Context.getLocationService();
			
			for (Location location : locationService.getAllLocations()) {
				boolean shouldRetire = true;
				Location parent = location;
				while (parent != null && !parent.hasTag("Visit Location")) {
					parent = parent.getParentLocation();
				}
				
				if (parent != null) {
					shouldRetire = drcLocation.equalsIgnoreCase(parent.getName());
				}
				
				// Essentially, if this location is not retired and doesn't have an attribute indicating it should be
				// Note that the matching is case-insensitive, but not accent insensitive
				if (shouldRetire) {
					locationService.retireLocation(location, messageSourceService.getMessage("drc.location.retire.reason"));
				}
			}
		} else {
			// If the property is not configured, we display a warning, but not a lot to do
			log.warn(messageSourceService.getMessage("drc.location.not.configured"));
		}
	}
}
