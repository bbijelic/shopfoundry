package org.shopfoundry.core.service.gateway.amqp;

/**
 * AMQP Message handler.
 * 
 * @author Bojan Bijelic
 */
public interface AmqpMessageHandler {

	/**
	 * Handles incoming amqp message.
	 */
	public void handleMessage();

}
