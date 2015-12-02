package org.shopfoundry.services.email.handler;

import org.shopfoundry.core.service.gateway.outbound.smtp.SmtpOutboundGateway;
import org.shopfoundry.core.service.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Email service message handler
 *
 * @author Bojan Bijelic
 */
public class EmailServiceMessageHandler implements MessageHandler {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceMessageHandler.class);

	/**
	 * Smtp outbound gateway
	 */
	private SmtpOutboundGateway smtpOutboundGateway;

	/**
	 * Constructor
	 * 
	 * @param smtpOutboundGateway
	 */
	public EmailServiceMessageHandler(SmtpOutboundGateway smtpOutboundGateway) {
		this.smtpOutboundGateway = smtpOutboundGateway;
	}

	@Override
	public void handleMessage(byte[] message) throws Exception {
		logger.info("Handling email service bus message (Size: {})", message.length);

		/*
		 * // From email request EmailRequest emailRequest = new EmailRequest();
		 * emailRequest.getTo().add("test@gmail.com");
		 * emailRequest.setFrom("noreply@shopfoundry.org");
		 * emailRequest.setSubject("Test email message subject");
		 * emailRequest.setMessage("Test email message body");
		 * 
		 * // Send email smtpOutboundGateway.sendEmail(emailRequest);
		 */
	}

}
