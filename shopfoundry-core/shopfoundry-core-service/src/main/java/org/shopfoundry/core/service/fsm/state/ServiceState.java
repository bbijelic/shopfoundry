package org.shopfoundry.core.service.fsm.state;

import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;

/**
 * Service state.
 * 
 * @author Bojan Bijelic
 */
public interface ServiceState {

	/**
	 * Returns service state.
	 * 
	 * @return the service state
	 */
	public AllowedState getState();

	/**
	 * Handles service state.
	 * 
	 * @param serviceContext
	 * @param serviceStateMachine
	 * @throws ServiceStateException
	 */
	public void handle(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine)
			throws ServiceStateException;

}
