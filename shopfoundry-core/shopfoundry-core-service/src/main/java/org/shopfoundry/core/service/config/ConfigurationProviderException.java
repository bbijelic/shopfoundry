package org.shopfoundry.core.service.config;

import org.shopfoundry.core.service.ServiceException;

/**
 * Configuration provider exception
 *
 * @author Bojan Bijelic
 */
public class ConfigurationProviderException extends ServiceException {

	/**
	 * Generated serial version UUID
	 */
	private static final long serialVersionUID = -6808943303254557739L;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public ConfigurationProviderException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param e
	 */
	public ConfigurationProviderException(String message, Throwable e) {
		super(message, e);
	}

}
