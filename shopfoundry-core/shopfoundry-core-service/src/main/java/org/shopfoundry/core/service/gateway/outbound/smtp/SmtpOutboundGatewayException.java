package org.shopfoundry.core.service.gateway.outbound.smtp;

import org.shopfoundry.core.service.gateway.outbound.OutboundGatewayException;

/**
 * SMTP outbound gateway exception
 *
 * @author Bojan Bijelic
 */
public class SmtpOutboundGatewayException extends OutboundGatewayException {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = -4178498834171481485L;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public SmtpOutboundGatewayException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param e
	 */
	public SmtpOutboundGatewayException(String message, Throwable e) {
		super(message, e);
	}

}
