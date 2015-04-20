package org.shopfoundry.core.service.fsm;

/**
 * State machine interface.
 * 
 * @author Bojan Bijelic
 */
public interface ServiceStateMachine {

	/**
	 * Returns default service state.
	 * 
	 * @return the default service state
	 * @throws Exception
	 */
	public ServiceState getDefaultState() throws Exception;

	/**
	 * Returns current service state.
	 * 
	 * @return the current service state
	 */
	public ServiceState getCurrentState() throws Exception;

	/**
	 * Changes service state.
	 * 
	 * @param serviceState
	 * @throws Exception
	 */
	public void changeState(ServiceState serviceState) throws Exception;
}
