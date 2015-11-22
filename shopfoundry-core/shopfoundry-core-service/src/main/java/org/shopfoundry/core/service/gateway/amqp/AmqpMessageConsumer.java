package org.shopfoundry.core.service.gateway.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;

/**
 * @author Bojan Bijelic
 */
public interface AmqpMessageConsumer extends Consumer {

	/**
	 * Associates consumer with a channel
	 * 
	 * @param channel
	 */
	public void associateWithChannel(Channel channel);

}
