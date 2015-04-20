package org.shopfoundry.core.service.fsm;

/**
 * Stateful service.
 * 
 * @author Bojan Bijelic
 */
public interface StatefulService {

	/**
	 * Returns service state machine.
	 * 
	 * @return the service state machine
	 */
	public ServiceStateMachine getServiceStateMachine();

}
