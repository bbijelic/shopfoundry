package org.shopfoundry.core.service.fsm.state;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.apache.http.client.ClientProtocolException;
import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.context.ServiceContextException;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.shopfoundry.core.service.fsm.ServiceStateMachineException;
import org.shopfoundry.core.service.gateway.GatewayException;
import org.shopfoundry.core.service.gateway.outbound.registry.RegistryOutboundGateway;
import org.shopfoundry.core.service.info.ServiceInfoProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mainly service bootstrapping tasks and environment validation.
 * 
 * @author Bojan Bijelic
 */
public class StartingState implements ServiceState {

	private static final Logger logger = LoggerFactory.getLogger(StartingState.class);

	@Override
	public void handle(ServiceContext serviceContext, ServiceStateMachine serviceStateMachine)
			throws ServiceStateException {

		if (logger.isInfoEnabled())
			logger.info("Handling state: {}", getState());

		try {

			// Register service
			registerService(serviceContext);

			// Proceed to the Configuration state
			serviceStateMachine.changeState(AllowedState.CONFIGURING);

		} catch (ServiceStateMachineException | GatewayException | ServiceContextException | NoSuchAlgorithmException
				| ServiceInfoProviderException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			throw new ServiceStateException(e.getMessage(), e);
		}
	}

	/**
	 * Registers service on registry service and obtains service GUID and
	 * configuration.
	 * 
	 * @param serviceContext
	 * @throws ServiceContextException
	 * @throws GatewayException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws ServiceInfoProviderException
	 * @throws ServiceStateException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 */
	private void registerService(ServiceContext serviceContext) throws GatewayException, ServiceContextException,
			NoSuchAlgorithmException, ServiceInfoProviderException, ServiceStateException {
		if (logger.isInfoEnabled())
			logger.info("Registering service");

		try {

			// Get instance of Registry Service outbound gateway
			RegistryOutboundGateway registryOutboundGateway = (RegistryOutboundGateway) serviceContext
					.getGatewayProvider().getOutboundGateways().get("RegistryService");

			// Stat registry service outbound gateway
			registryOutboundGateway.start();

			// Performs registration
			registryOutboundGateway.register();

		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			throw new ServiceStateException(e.getMessage(), e);
		}
	}

	@Override
	public AllowedState getState() {
		return AllowedState.STARTING;
	}

}
