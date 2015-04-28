package org.shopfoundry.services.registry.fsm.state;

import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.shopfoundry.core.service.fsm.state.AllowedState;
import org.shopfoundry.core.service.fsm.state.ServiceState;
import org.shopfoundry.core.service.fsm.state.ServiceStateException;
import org.shopfoundry.core.service.gateway.inbound.InboundGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry service configuring state.
 *
 * @author Bojan Bijelic
 */
public class ConfiguringState implements ServiceState {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfiguringState.class);

	@Override
	public AllowedState getState() {
		return AllowedState.CONFIGURING;
	}

	@Override
	public void handle(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine)
			throws ServiceStateException {

		try {

			// Start all inbound gateways
			for (InboundGateway inboundGateway : serviceContext
					.getGatewayProvider().getInboundGateways().values()) {
				inboundGateway.start();
			}

			// Change state to running
			serviceStateMachine.changeState(AllowedState.RUNNING);

		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			throw new ServiceStateException(e.getMessage(), e);
		}

	}

}
