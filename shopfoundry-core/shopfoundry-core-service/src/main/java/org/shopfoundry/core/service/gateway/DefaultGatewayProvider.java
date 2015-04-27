package org.shopfoundry.core.service.gateway;

import java.util.Map;

import org.shopfoundry.core.service.gateway.inbound.InboundGateway;
import org.shopfoundry.core.service.gateway.outbound.OutboundGateway;

/**
 * Default gateway provider.
 *
 * @author Bojan Bijelic
 */
public class DefaultGatewayProvider implements GatewayProvider {

	/**
	 * Constructor.
	 * 
	 * @param outboundGateways
	 * @param inboundGateways
	 */
	public DefaultGatewayProvider(
			Map<String, OutboundGateway> outboundGateways,
			Map<String, InboundGateway> inboundGateways) {
		this.outboundGateways = outboundGateways;
		this.inboundGateways = inboundGateways;
	}

	/**
	 * Outbound gateways.
	 */
	private Map<String, OutboundGateway> outboundGateways;

	@Override
	public Map<String, OutboundGateway> getOutboundGateways()
			throws GatewayException {
		if (this.outboundGateways == null)
			throw new GatewayException("Outbound gateways not defined");

		return this.outboundGateways;
	}

	/**
	 * Inbound gateways.
	 */
	private Map<String, InboundGateway> inboundGateways;

	@Override
	public Map<String, InboundGateway> getInboundGateways()
			throws GatewayException {
		if (this.inboundGateways == null)
			throw new GatewayException("Inbound gateways not defined");

		return this.inboundGateways;
	}

}
