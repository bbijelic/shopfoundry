package org.shopfoundry.core.service.fsm.state;

import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.shopfoundry.core.service.gateway.inbound.InboundGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shutting down state.
 * 
 * @author Bojan Bijelic
 */
public class ShuttingDownState implements ServiceState {

	private static final Logger logger = LoggerFactory.getLogger(StartingState.class);

	@Override
	public void handle(ServiceContext serviceContext, ServiceStateMachine serviceStateMachine)
			throws ServiceStateException {

		if (logger.isInfoEnabled())
			logger.info("Handling state {}", getState());

		try {

			if (!serviceContext.getGatewayProvider().getInboundGateways().isEmpty()) {
				for (InboundGateway inboundGateway : serviceContext.getGatewayProvider().getInboundGateways()
						.values()) {
					if (logger.isInfoEnabled())
						logger.info("Stopping inbound gateay: {}", inboundGateway.getClass().getCanonicalName());

					// Configure
					inboundGateway.stop();
				}
			}

		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

		} finally {

			// Close service
			System.exit(0);
		}

	}

	@Override
	public AllowedState getState() {
		return AllowedState.SHUTTING_DOWN;
	}

}
