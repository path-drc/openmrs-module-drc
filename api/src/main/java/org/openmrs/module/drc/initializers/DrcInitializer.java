package org.openmrs.module.drc.initializers;

import org.springframework.core.Ordered;

/**
 * Interface for all initializers that should be run by this module at start-up. Each initializer is
 * called based on its defined order.
 */
public interface DrcInitializer extends Ordered {
	
	String getName();
	
	void initialize();
	
}
