package org.shopfoundry.core.service;

/**
 * Service Exception
 * 
 * @author Bojan Bijelic
 */
public class ServiceException extends Exception {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 6247704546586885755L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public ServiceException(String message, Throwable e) {
		super(message, e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

}
