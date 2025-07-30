package org.openmrs.module.drc.initializers;

import org.openmrs.api.context.Context;

/**
 * This class can be used to define a simple bean which takes a {@link Runnable} that performs the
 * actual initialization. It is not necessary, but included as a convenience to make defining an
 * {@link DrcInitializer} somewhat simpler.
 */
public class RunnableDrcInitializer extends BaseDrcInitializer {
	
	private String name = RunnableDrcInitializer.class.getSimpleName();
	
	private Runnable initializer = null;
	
	private int order = 0;
	
	@Override
	public void initialize() {
		if (initializer != null) {
			try {
				initializer.run();
			}
			catch (Exception e) {
				log.error(Context.getMessageSourceService().getMessage("drc.initializer.error"), getName(), e);
			}
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public void setInitializer(Runnable initializer) {
		this.initializer = initializer;
	}
}
