package org.shopfoundry.core.service.fsm;

import org.shopfoundry.core.service.ServiceException;

/**
 * Service state machine exception.
 *
 * @author Bojan Bijelic
 */
public class ServiceStateMachineException extends ServiceException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 780439796102826825L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ServiceStateMachineException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public ServiceStateMachineException(String message, Throwable e) {
		super(message, e);
	}

}
