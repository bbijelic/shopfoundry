package org.shopfoundry.core.service.gateway.outbound.smtp;

import org.shopfoundry.core.service.gateway.outbound.OutboundGateway;

/**
 * SMTP outbound gateway.
 *
 * @author Bojan Bijelic
 */
public interface SmtpOutboundGateway extends OutboundGateway {

	/**
	 * Sends email via SMTP server
	 * 
	 * @param emailRequest
	 * @throws SmtpOutboundGatewayException
	 */
	public void sendEmail(EmailRequest emailRequest) throws SmtpOutboundGatewayException;

}
