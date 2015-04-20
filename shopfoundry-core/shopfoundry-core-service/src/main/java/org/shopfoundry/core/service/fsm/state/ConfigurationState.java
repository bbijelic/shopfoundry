package org.shopfoundry.core.service.fsm.state;

import org.shopfoundry.core.service.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceState;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration state.
 * 
 * @author Bojan Bijelic
 */
public class ConfigurationState implements ServiceState {

	private static final Logger logger = LoggerFactory
			.getLogger(StartingState.class);

	@Override
	public void handle(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine) throws Exception {

		if (logger.isInfoEnabled())
			logger.info("Handling ConfigurationState");

	}

	@Override
	public String getStateName() {
		return this.getClass().getSimpleName();
	}

}
