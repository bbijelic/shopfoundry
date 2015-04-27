package org.shopfoundry.core.security;

import java.security.KeyStoreException;

/**
 * Security Provider exception.
 *
 * @author Bojan Bijelic
 */
public class SecurityProviderException extends SecurityException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 5219632906115599045L;

	/**
	 * Exception.
	 * 
	 * @param message
	 * @param e
	 */
	public SecurityProviderException(String message, KeyStoreException e) {
		super(message, e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public SecurityProviderException(String message) {
		super(message);
	}

}
