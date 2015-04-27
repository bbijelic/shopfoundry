package org.shopfoundry.core.service.context;

import java.net.UnknownHostException;

import org.shopfoundry.core.service.ServiceException;

/**
 * Service context exception.
 *
 * @author Bojan Bijelic
 */
public class ServiceContextException extends ServiceException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 4714923667231347034L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public ServiceContextException(String message, UnknownHostException e) {
		super(message, e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ServiceContextException(String message) {
		super(message);
	}

}
