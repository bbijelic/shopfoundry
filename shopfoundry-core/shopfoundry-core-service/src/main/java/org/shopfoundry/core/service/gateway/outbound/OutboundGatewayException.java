package org.shopfoundry.core.service.gateway.outbound;

import org.shopfoundry.core.service.gateway.GatewayException;

/**
 * Outbound gateway exception
 *
 * @author Bojan Bijelic
 */
public class OutboundGatewayException extends GatewayException {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -2534272033357926246L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param e
	 */
	public OutboundGatewayException(String message, Throwable e) {
		super(message, e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public OutboundGatewayException(String message) {
		super(message);
	}

}
