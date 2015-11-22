package org.shopfoundry.core.service.handler;

/**
 * Amqp message handler.
 *
 * @author Bojan Bijelic
 */
public interface MessageHandler {

	/**
	 * Handles message.
	 * 

	 * @param message
	 * @throws Exception
	 */
	public void handleMessage(byte[] message) throws Exception;

}
