package org.shopfoundry.core.service.fsm;

import org.shopfoundry.core.service.ServiceContext;

/**
 * Service state.
 * 
 * @author Bojan Bijelic
 */
public interface ServiceState {

	/**
	 * Returns service state name.
	 * 
	 * @return the service state name
	 */
	public String getStateName();

	/**
	 * Handles service state.
	 * 
	 * @param serviceContext
	 * @param serviceStateMachine
	 * @throws Exception
	 */
	public void handle(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine) throws Exception;

}
