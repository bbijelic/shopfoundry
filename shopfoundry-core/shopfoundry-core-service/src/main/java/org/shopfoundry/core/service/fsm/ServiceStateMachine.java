package org.shopfoundry.core.service.fsm;

import java.util.Map;

import org.shopfoundry.core.service.fsm.state.AllowedState;
import org.shopfoundry.core.service.fsm.state.ServiceState;

/**
 * State machine interface.
 * 
 * @author Bojan Bijelic
 */
public interface ServiceStateMachine {

	/**
	 * Returns current service state.
	 * 
	 * @return the current service state
	 * @throws ServiceStateMachineException
	 */
	public ServiceState getCurrentState() throws ServiceStateMachineException;

	/**
	 * Changes service state.
	 * 
	 * @param nextState
	 * @throws ServiceStateMachineException
	 */
	public void changeState(AllowedState nextState)
			throws ServiceStateMachineException;

	/**
	 * Reutns state mapping.
	 * 
	 * @return the state mapping
	 */
	public Map<AllowedState, ServiceState> getStateMapping()
			throws ServiceStateMachineException;
}
