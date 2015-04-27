package org.shopfoundry.core.service.gateway;

import java.util.Map;

import org.shopfoundry.core.service.gateway.inbound.InboundGateway;
import org.shopfoundry.core.service.gateway.outbound.OutboundGateway;

/**
 * Gateway provider.
 *
 * @author Bojan Bijelic
 */
public interface GatewayProvider {

	/**
	 * Returns outbound gateway map.
	 * 
	 * @return the outbound gateway map
	 * @throws GatewayException
	 */
	public Map<String, OutboundGateway> getOutboundGateways()
			throws GatewayException;

	/**
	 * Returns inbound gateway map.
	 * 
	 * @return the inbound gateway map
	 * @throws GatewayException
	 */
	public Map<String, InboundGateway> getInboundGateways()
			throws GatewayException;
}
