package org.shopfoundry.core.service.gateway.outbound.registry;

import org.shopfoundry.core.service.gateway.outbound.OutboundGateway;

/**
 * Registry service outbound gateway.
 *
 * @author Bojan Bijelic
 */
public interface RegistryOutboundGateway extends OutboundGateway {

	/**
	 * Registers service on Registry service. Registry service returns signed
	 * certificate along with service configuration parameters.
	 * 
	 * @throws RegistryOutboundGatewayException
	 */
	public void register() throws RegistryOutboundGatewayException;
}
