package org.shopfoundry.core.service.fsm;

import java.util.Map;

import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.state.AllowedState;
import org.shopfoundry.core.service.fsm.state.ServiceState;
import org.shopfoundry.core.service.fsm.state.ServiceStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of service state machine.
 * 
 * @author Bojan Bijelic
 */
public class DefaultServiceStateMachine implements ServiceStateMachine {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultServiceStateMachine.class);

	/**
	 * Constructor.
	 * 
	 * @param serviceContext
	 * @param stateMap
	 */
	public DefaultServiceStateMachine(ServiceContext serviceContext,
			Map<AllowedState, ServiceState> stateMap) {
		this.serviceContext = serviceContext;
		this.stateMap = stateMap;
	}

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
	public ServiceState getCurrentState() {
		return this.currentState;
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
		builder.append("]");
		return builder.toString();
	}

	@Override
	public void changeState(AllowedState nextState)
			throws ServiceStateMachineException {

		if (logger.isInfoEnabled())
			logger.info(
					"Changing service state [ Current: {}, Next: {} ]",
					((this.currentState == null) ? "None" : this.currentState
							.getState()), nextState.toString());

		// Set new state
		this.currentState = getStateMapping().get(nextState);

		try {

			// Handle new state
			currentState.handle(serviceContext, this);

		} catch (ServiceStateException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			throw new ServiceStateMachineException(e.getMessage(), e);
		}
	}

	/**
	 * State mapping
	 */
	private Map<AllowedState, ServiceState> stateMap;

	@Override
	public Map<AllowedState, ServiceState> getStateMapping()
			throws ServiceStateMachineException {
		if (this.stateMap == null)
			throw new ServiceStateMachineException("State mapping not set");

		return this.stateMap;
	}

}
