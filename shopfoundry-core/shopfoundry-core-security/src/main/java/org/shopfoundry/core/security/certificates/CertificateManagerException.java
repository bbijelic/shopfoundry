package org.shopfoundry.core.security.certificates;

import org.shopfoundry.core.security.SecurityException;

/**
 * Certificate Manager Exception.
 *
 * @author Bojan Bijelic
 */
public class CertificateManagerException extends SecurityException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 119807020362113674L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public CertificateManagerException(String message, Throwable e) {
		super(message, e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public CertificateManagerException(String message) {
		super(message);
	}

}
