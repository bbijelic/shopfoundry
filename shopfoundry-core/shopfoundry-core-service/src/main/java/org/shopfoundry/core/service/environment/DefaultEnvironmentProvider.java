package org.shopfoundry.core.service.environment;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default environment provider implementation.
 *
 * @author Bojan Bijelic
 */
public class DefaultEnvironmentProvider implements EnvironmentProvider {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultEnvironmentProvider.class);

	@Override
	public Map<String, String> getEnvironmentVariables()
			throws EnvironmentProviderException {
		// Get environment variables
		Map<String, String> environmentVars = System.getenv();

		if (logger.isTraceEnabled())
			logger.trace("Environment variables: {}",
					environmentVars.toString());

		// Return environment variables
		return environmentVars;
	}

	@Override
	public String getHostname() throws EnvironmentProviderException {

		try {

			// Try to resolve hostname
			String hostname = Inet4Address.getLocalHost().getHostName();

			if (logger.isTraceEnabled())
				logger.trace("Hostname: {}", hostname);

			// Return hostname
			return hostname;

		} catch (UnknownHostException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			// Throw environment provider exception
			throw new EnvironmentProviderException(e.getMessage(), e);
		}
	}

	@Override
	public int getAvailableProcessors() {
		// Get total available processors
		int availableProcessors = Runtime.getRuntime().availableProcessors();

		if (logger.isTraceEnabled())
			logger.trace("Total available processors: {}", availableProcessors);

		return availableProcessors;
	}

}
