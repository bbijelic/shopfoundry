package org.shopfoundry.core.service.gateway.inbound;

import org.shopfoundry.core.service.gateway.GatewayException;

/**
 * Inbound gateway exception.
 *
 * @author Bojan Bijelic
 */
public class InboundGatewayException extends GatewayException {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 4235365542608239120L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public InboundGatewayException(String message, Throwable e) {
		super(message, e);
	}
	
}
