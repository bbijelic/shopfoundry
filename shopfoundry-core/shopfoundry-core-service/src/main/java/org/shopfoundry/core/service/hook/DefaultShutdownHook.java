package org.shopfoundry.core.service.hook;

import org.shopfoundry.core.service.ContextualService;
import org.shopfoundry.core.service.ServiceContext;
import org.shopfoundry.core.service.gateway.InboundGateway;
import org.shopfoundry.core.service.gateway.InboundSystemEventGateway;
import org.shopfoundry.core.service.gateway.OutboundGateway;
import org.shopfoundry.core.service.gateway.OutboundSystemEventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default service shutdown hook.
 * 
 * @author Bojan Bijelic
 */
public class DefaultShutdownHook implements Runnable, ContextualService {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultShutdownHook.class);

	/**
	 * Service context.
	 */
	private ServiceContext serviceContext;

	/**
	 * Constructor.
	 * 
	 * @param serviceContext
	 */
	public DefaultShutdownHook(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	@Override
	public void run() {

		if (logger.isInfoEnabled())
			logger.info("Shutting down service '{}@{}'",
					serviceContext.getServiceGUID(),
					serviceContext.getServiceGroup());

		// Stopping all outbound gateways
		if (!serviceContext.getOutboundGateways().isEmpty()) {

			if (logger.isInfoEnabled())
				logger.info("Stopping {} outbound gateways", serviceContext
						.getOutboundGateways().size());

			// Stopping outbound gateways
			for (OutboundGateway outboundGateway : serviceContext
					.getOutboundGateways()) {
				// Stop outbound gateway
				outboundGateway.stop();
			}
		} else {
			if (logger.isInfoEnabled())
				logger.info("No outbound gateways defined");
		}

		// Stopping all inbound gateways
		if (!serviceContext.getInboundGateways().isEmpty()) {

			if (logger.isInfoEnabled())
				logger.info("Stopping {} inbound gateways", serviceContext
						.getInboundGateways().size());

			// Stopping inbound gateways
			for (InboundGateway inboundGateway : serviceContext
					.getInboundGateways()) {
				// Stop inbound gateway
				inboundGateway.stop();
			}
		} else {
			if (logger.isInfoEnabled())
				logger.info("No inbound gateways defined");
		}

		// Stopping all inbound system event gateways
		if (!serviceContext.getInboundSystemEventGateways().isEmpty()) {

			if (logger.isInfoEnabled())
				logger.info("Stopping {} inbound gateways", serviceContext
						.getInboundSystemEventGateways().size());

			// Stopping inbound system event gateways
			for (InboundSystemEventGateway inboundSystemEventGateway : serviceContext
					.getInboundSystemEventGateways()) {
				// Stop inbound system event gateway
				inboundSystemEventGateway.stop();
			}
		} else {
			if (logger.isInfoEnabled())
				logger.info("No inbound system event gateways defined");
		}

		// Stopping all outbound system event gateways
		if (!serviceContext.getOutboundSystemEventGateways().isEmpty()) {

			if (logger.isInfoEnabled())
				logger.info("Stopping {} outbound gateways", serviceContext
						.getOutboundSystemEventGateways().size());

			// Stopping inbound system event gateways
			for (OutboundSystemEventGateway outboundSystemEventGateway : serviceContext
					.getOutboundSystemEventGateways()) {
				// Stop inbound system event gateway
				outboundSystemEventGateway.stop();
			}
		} else {
			if (logger.isInfoEnabled())
				logger.info("No outbound system event gateways defined");
		}

	}

	@Override
	public ServiceContext getServiceContext() {
		return serviceContext;
	}

}
