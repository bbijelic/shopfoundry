package org.shopfoundry.core.service.guid;

import org.shopfoundry.core.service.ServiceException;

/**
 * GUID Exception.
 *
 * @author Bojan Bijelic
 */
public class GuidException extends ServiceException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 7449704432036379809L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public GuidException(String message, Throwable e) {
		super(message, e);
	}

}
