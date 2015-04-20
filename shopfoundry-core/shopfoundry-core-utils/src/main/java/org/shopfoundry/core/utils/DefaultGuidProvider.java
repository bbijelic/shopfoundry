package org.shopfoundry.core.utils;

import java.util.UUID;

/**
 * Default GUID provider.
 * 
 * @author Bojan Bijelic
 */
public class DefaultGuidProvider implements GuidProvider {

	@Override
	public String getGUID() {
		return UUID.randomUUID().toString();
	}

}
