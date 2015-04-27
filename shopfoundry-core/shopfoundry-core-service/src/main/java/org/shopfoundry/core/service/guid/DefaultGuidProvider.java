package org.shopfoundry.core.service.guid;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default GUID provider implementation.
 *
 * @author Bojan Bijelic
 */
public class DefaultGuidProvider implements GuidProvider {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultGuidProvider.class);

	@Override
	public String getGuid() throws GuidException {
		// Generate GUID
		String guid = UUID.randomUUID().toString();

		if (logger.isTraceEnabled())
			logger.trace("Generated GUID: {}", guid);

		// Return GUID
		return guid;
	}

}
