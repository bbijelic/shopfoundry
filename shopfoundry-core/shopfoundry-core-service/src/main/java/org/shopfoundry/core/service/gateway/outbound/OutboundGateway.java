package org.shopfoundry.core.service.gateway.outbound;

import org.shopfoundry.core.service.gateway.Gateway;

/**
 * Outbound gateway.
 * 
 * Outbound gateway is considered to be an outbound only communication
 * interface. Services can run multiple outbound communication interfaces
 * simultaniously.
 * 
 * @author Bojan Bijelic
 */
public interface OutboundGateway extends Gateway {

	/**
	 * Configures outbound with obtained configuration.
	 * 
	 * @throws OutboundGatewayException
	 */
	public void configure() throws OutboundGatewayException;

}
