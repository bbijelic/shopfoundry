package org.shopfoundry.core.service.fsm.state;

import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.shopfoundry.core.service.fsm.ServiceStateMachineException;
import org.shopfoundry.core.service.gateway.inbound.InboundGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Running state.
 * 
 * @author Bojan Bijelic
 */
public class RunningState implements ServiceState {

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
						logger.info("Starting inbound gateay: {}", inboundGateway.getClass().getCanonicalName());

					// Configure
					inboundGateway.start();
				}
			}

		} catch (Exception e) {
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
		return AllowedState.RUNNING;
	}

}
