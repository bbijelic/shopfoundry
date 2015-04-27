package org.shopfoundry.core.security;

/**
 * Security exception.
 *
 * @author Bojan Bijelic
 */
public class SecurityException extends Exception {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -4843850026868441953L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public SecurityException(String message, Throwable e) {
		super(message, e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public SecurityException(String message) {
		super(message);
	}

}
