package org.shopfoundry.core.service.fsm.state;

import org.shopfoundry.core.service.fsm.ServiceStateMachineException;

/**
 * Service State Exception.
 *
 * @author Bojan Bijelic
 */
public class ServiceStateException extends ServiceStateMachineException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 5475616888528020670L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ServiceStateException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public ServiceStateException(String message, Throwable e) {
		super(message, e);
	}

}
