package org.shopfoundry.core.service.config;

import java.util.Map;

/**
 * Configuration provider.
 *
 * @author Bojan Bijelic
 */
public interface ConfigurationProvider {

	/**
	 * Returns whole configuration
	 * 
	 * @return the configuration map
	 */
	Map<String, String> getConfiguration();

	/**
	 * Returns configuration value for given parameter name
	 * 
	 * @param parameter
	 * @return the configuration value
	 */
	String getConfigurationValue(String parameter) throws ConfigurationProviderException;

	/**
	 * Imports configuration into configuration provider
	 * 
	 * @param configuration
	 */
	void importConfiguration(Map<String, String> configuration);
}
