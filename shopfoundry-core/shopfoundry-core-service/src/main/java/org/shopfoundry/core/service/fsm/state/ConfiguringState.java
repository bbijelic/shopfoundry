package org.shopfoundry.core.service.fsm.state;

import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.shopfoundry.core.service.fsm.ServiceStateMachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration state.
 * 
 * @author Bojan Bijelic
 */
public class ConfiguringState implements ServiceState {

	private static final Logger logger = LoggerFactory
			.getLogger(StartingState.class);

	@Override
	public void handle(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine)
			throws ServiceStateException {

		if (logger.isInfoEnabled())
			logger.info("Handling state {}", getState());

		try {

			try {
				
				// Simulating configuration work
				Thread.sleep(10000);
				
			} catch (InterruptedException e) {
				if (logger.isErrorEnabled())
					logger.error(e.getMessage(), e);

				// Change state to running
				serviceStateMachine.changeState(AllowedState.SHUTTING_DOWN);
			}

			// Change state to running
			serviceStateMachine.changeState(AllowedState.RUNNING);

		} catch (ServiceStateMachineException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			try {

				// Change state to shutting down
				serviceStateMachine.changeState(AllowedState.SHUTTING_DOWN);

			} catch (ServiceStateMachineException e1) {
				if (logger.isErrorEnabled())
					logger.error(e.getMessage(), e);

				// If cant change state to shutting down, throw state exception
				throw new ServiceStateException(e.getMessage(), e);
			}
		}
	}

	@Override
	public AllowedState getState() {
		return AllowedState.CONFIGURING;
	}

}
