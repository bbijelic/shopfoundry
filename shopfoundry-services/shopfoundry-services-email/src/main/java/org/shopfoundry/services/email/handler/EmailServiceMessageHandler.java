package org.shopfoundry.services.email.handler;

import org.shopfoundry.core.service.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Email service message handler
 *
 * @author Bojan Bijelic
 */
public class EmailServiceMessageHandler implements MessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceMessageHandler.class);

	@Override
	public void handleMessage(byte[] message) throws Exception {
		logger.info("Handling email service bus message (Size: {})", message.length);
	}

}
