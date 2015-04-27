package org.shopfoundry.core.service.info;

import org.shopfoundry.core.service.ServiceException;

/**
 * Service info exception.
 *
 * @author Bojan Bijelic
 */
public class ServiceInfoProviderException extends ServiceException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 5104350257141413794L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ServiceInfoProviderException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public ServiceInfoProviderException(String message, Throwable e) {
		super(message, e);
	}

}
