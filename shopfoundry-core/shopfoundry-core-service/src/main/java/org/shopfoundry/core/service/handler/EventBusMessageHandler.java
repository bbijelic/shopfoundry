package org.shopfoundry.core.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusMessageHandler implements MessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(EventBusMessageHandler.class);

	@Override
	public void handleMessage(byte[] message) throws Exception {
		logger.info("Handling event bus message (Size: {})", message.length);
	}

}
