/**
 * 
 */
package org.shopfoundry.core.service.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bojan Bijelic
 */
public class DefaultConfigurationProvider implements ConfigurationProvider {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DefaultConfigurationProvider.class);

	@Override
	public Map<String, String> getConfiguration() {
		return configurationMap;
	}

	@Override
	public String getConfigurationValue(String parameter) throws ConfigurationProviderException {
		if (!configurationMap.containsKey(parameter))
			throw new ConfigurationProviderException(
					String.format("Configuration parameter '%s' not defined", parameter));

		// Return configuration parameter
		return configurationMap.get(parameter);
	}

	private Map<String, String> configurationMap = new HashMap<String, String>();

	@Override
	public void importConfiguration(Map<String, String> configuration) {
		logger.debug("Importing configuration: {}", configuration.toString());
		configurationMap.putAll(configuration);
		logger.debug("Imported configuration: {}", configurationMap.toString());
	}

}
