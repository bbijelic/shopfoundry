package org.shopfoundry.core.service.environment;

import java.net.UnknownHostException;

import org.shopfoundry.core.service.ServiceException;

/**
 * Environment Exception.
 *
 * @author Bojan Bijelic
 */
public class EnvironmentProviderException extends ServiceException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -1043346906252072547L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public EnvironmentProviderException(String message, UnknownHostException e) {
		super(message, e);
	}

}
