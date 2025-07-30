package org.openmrs.module.drc.initializers.impl;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.util.ConfigUtil;
import org.openmrs.util.OpenmrsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminUserInitializer implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(AdminUserInitializer.class);
	
	@Override
	public void run() {
		final MessageSourceService messageSourceService = Context.getService(MessageSourceService.class);
		
		final User adminUser = Context.getUserService().getUser(1);
		final String defaultLocale = ConfigUtil.getGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_DEFAULT_LOCALE);
		final String adminDefaultLocale = adminUser.getUserProperty(OpenmrsConstants.USER_PROPERTY_DEFAULT_LOCALE);
		if (StringUtils.isNotBlank(defaultLocale)
		        && (adminDefaultLocale == null || !adminDefaultLocale.equals(defaultLocale))) {
			log.warn(messageSourceService.getMessage("drc.admin.set.locale"), defaultLocale);
			adminUser.setUserProperty(OpenmrsConstants.USER_PROPERTY_DEFAULT_LOCALE, defaultLocale);
			Context.getUserService().saveUser(adminUser);
		}
	}
}
