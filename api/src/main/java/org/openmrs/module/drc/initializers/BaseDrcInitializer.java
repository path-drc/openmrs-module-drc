package org.openmrs.module.drc.initializers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDrcInitializer implements DrcInitializer {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public int getOrder() {
		return 0;
	}
}
