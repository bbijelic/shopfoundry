package org.shopfoundry.core.service.gateway;

import org.shopfoundry.core.service.ServiceException;

/**
 * Gateway exception.
 *
 * @author Bojan Bijelic
 */
public class GatewayException extends ServiceException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -8384598344474227497L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public GatewayException(String message, Throwable e) {
		super(message, e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public GatewayException(String message) {
		super(message);
	}

}
