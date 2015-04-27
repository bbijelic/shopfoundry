package org.shopfoundry.core.service.handler;

import java.io.Serializable;

import org.shopfoundry.core.service.context.ServiceContext;

/**
 * Inbound message handler interface.
 * 
 * @author Bojan Bijelic
 */
public interface InboundMessageHandler {

	/**
	 * Handles inbound service message.
	 * 
	 * @param message
	 * @param serviceContext
	 */
	public void handleMessage(Serializable message,
			ServiceContext serviceContext);

}
