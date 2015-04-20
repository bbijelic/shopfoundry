package org.shopfoundry.core.service.gateway.amqp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * Default AMQP Message consumer
 * 
 * @author Bojan Bijelic
 */
public class DefaultAmqpMessageConsumer implements AmqpMessageConsumer {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultAmqpMessageConsumer.class);

	/**
	 * Called when the consumer is cancelled for reasons other than by a call to
	 * Channel.basicCancel(java.lang.String).
	 */
	@Override
	public void handleCancel(String consumerTag) throws IOException {
		if (logger.isInfoEnabled())
			logger.info("Message with tag '{}' canceled by the consumer",
					consumerTag);
	}

	/**
	 * Called when the consumer is cancelled by a call to
	 * Channel.basicCancel(java.lang.String).
	 */
	@Override
	public void handleCancelOk(String consumerTag) {
		if (logger.isInfoEnabled())
			logger.info("Message with tag '{}' canceled by the consumer",
					consumerTag);
	}

	/**
	 * Called when the consumer is registered by a call to any of the
	 * Channel.basicConsume(java.lang.String, com.rabbitmq.client.Consumer)
	 * methods.
	 */
	@Override
	public void handleConsumeOk(String consumerTag) {
		if (logger.isInfoEnabled())
			logger.info("Consumer with tag '{}' successfully registered",
					consumerTag);
	}

	/**
	 * Called when a basic.deliver is received for this consumer.
	 */
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope,
			BasicProperties basicProperties, byte[] body) throws IOException {
		if (logger.isInfoEnabled())
			logger.info("Consumer with tag '{}' received delivery of {} bytes",
					consumerTag, body.length);
	}

	/**
	 * Called when a basic.recover-ok is received in reply to a basic.recover.
	 */
	@Override
	public void handleRecoverOk(String consumerTag) {
		if (logger.isInfoEnabled())
			logger.info("Consumer with tag '{}' is recovering", consumerTag);
	}

	/**
	 * Called when either the channel or the underlying connection has been shut
	 * down.
	 */
	@Override
	public void handleShutdownSignal(String consumerTag,
			ShutdownSignalException shutdownSignalException) {
		if (logger.isInfoEnabled())
			logger.info("Consumer with tag '{}' is shutting down: {}",
					consumerTag, shutdownSignalException.getMessage());

	}

}
