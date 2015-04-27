package org.shopfoundry.core.service.environment;

import java.util.Map;

/**
 * Environment provider.
 * 
 * @author Bojan Bijelic
 */
public interface EnvironmentProvider {

	/**
	 * Returns environment variables.
	 * 
	 * @return the environment variables map
	 * @throws EnvironmentProviderException
	 */
	public Map<String, String> getEnvironmentVariables()
			throws EnvironmentProviderException;

	/**
	 * Returns hostname.
	 * 
	 * @return the hostname
	 * @throws EnvironmentProviderException
	 */
	public String getHostname() throws EnvironmentProviderException;
	
	/**
	 * Available processors number getter.
	 * 
	 * @return the list of available processors
	 */
	public int getAvailableProcessors();
}
