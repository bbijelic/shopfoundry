package org.shopfoundry.core.service.gateway.outbound.registry;

import org.shopfoundry.core.service.gateway.outbound.OutboundGatewayException;

/**
 * Registry outbound gateway exception.
 *
 * @author Bojan Bijelic
 */
public class RegistryOutboundGatewayException extends OutboundGatewayException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -7708190087700687864L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public RegistryOutboundGatewayException(String message, Throwable e) {
		super(message, e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public RegistryOutboundGatewayException(String message) {
		super(message);
	}

}
