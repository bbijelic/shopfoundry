package org.shopfoundry.core.service.fsm;

import org.shopfoundry.core.service.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of service state machine.
 * 
 * @author Bojan Bijelic
 */
public class DefaultServiceStateMachine implements ServiceStateMachine {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultServiceStateMachine.class);

	/**
	 * Service context
	 */
	private ServiceContext serviceContext;

	/**
	 * Service context getter
	 * 
	 * @return the serviceContext
	 */
	public ServiceContext getServiceContext() {
		return serviceContext;
	}

	/**
	 * Service context setter.
	 * 
	 * @param serviceContext
	 *            the serviceContext to set
	 */
	public void setServiceContext(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	/**
	 * Current service state.
	 */
	private ServiceState currentState;

	/**
	 * Current service state setter.
	 * 
	 * @param currentState
	 *            the currentState to set
	 */
	public void setCurrentState(ServiceState currentState) {
		this.currentState = currentState;
	}

	@Override
	public ServiceState getCurrentState() throws Exception {
		return currentState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultServiceStateMachine [serviceContext=");
		builder.append(serviceContext);
		builder.append(", currentState=");
		builder.append(currentState);
		builder.append(", defaultState=");
		builder.append(defaultState);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public void changeState(ServiceState serviceState) throws Exception {
		if (logger.isInfoEnabled())
			logger.info(
					"Changing service state [ Current: {}, Next: {} ]",
					((currentState == null) ? "None" : currentState
							.getStateName()), serviceState.getStateName());

		// Set new state
		currentState = serviceState;

		// Handle new state
		currentState.handle(serviceContext, this);
	}

	/**
	 * Default service state.
	 */
	private ServiceState defaultState;

	/**
	 * Default service state setter.
	 * 
	 * @param defaultState
	 *            the defaultState to set
	 */
	public void setDefaultState(ServiceState defaultState) {
		this.defaultState = defaultState;
	}

	@Override
	public ServiceState getDefaultState() throws Exception {
		return defaultState;
	}

}
