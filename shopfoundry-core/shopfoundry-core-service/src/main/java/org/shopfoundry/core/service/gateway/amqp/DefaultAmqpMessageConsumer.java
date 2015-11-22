package org.shopfoundry.core.service.gateway.amqp;

import java.io.IOException;

import org.shopfoundry.core.service.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
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
	private static final Logger logger = LoggerFactory.getLogger(DefaultAmqpMessageConsumer.class);

	/**
	 * Message handler
	 */
	private MessageHandler messageHandler;

	/**
	 * Amqp channel
	 */
	private Channel channel;

	public Channel getChannel() {
		return channel;
	}

	@Override
	public void associateWithChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * Constructor
	 * 
	 * @param amqpMessageHandler
	 */
	public DefaultAmqpMessageConsumer(MessageHandler amqpMessageHandler) {
		this.messageHandler = amqpMessageHandler;
	}

	/**
	 * Called when the consumer is cancelled for reasons other than by a call to
	 * Channel.basicCancel(java.lang.String).
	 * 
	 * @see <a href="https://www.rabbitmq.com/consumer-cancel.html">RabbitMQ
	 *      consumer cancel</a>
	 */
	@Override
	public void handleCancel(String consumerTag) throws IOException {
		if (logger.isInfoEnabled())
			logger.info("Consumer '{}' canceled by the broker", consumerTag);
	}

	/**
	 * Called when the consumer is cancelled by a call to
	 * Channel.basicCancel(java.lang.String).
	 */
	@Override
	public void handleCancelOk(String consumerTag) {
		if (logger.isInfoEnabled())
			logger.info("Consumer '{}' successfuly canceled", consumerTag);
	}

	/**
	 * Called when the consumer is registered by a call to any of the
	 * Channel.basicConsume(java.lang.String, com.rabbitmq.client.Consumer)
	 * methods.
	 */
	@Override
	public void handleConsumeOk(String consumerTag) {
		if (logger.isInfoEnabled())
			logger.info("Consumer '{}' successfully registered", consumerTag);
	}

	/**
	 * Called when a basic.deliver is received for this consumer.
	 */
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties basicProperties, byte[] body)
			throws IOException {
		if (logger.isInfoEnabled())
			logger.info("Consumer with tag '{}' received delivery of {} bytes", consumerTag, body.length);

		try {

			// Handle message
			this.messageHandler.handleMessage(body);

			// Acknowlage only this message
			channel.basicAck(envelope.getDeliveryTag(), false);

		} catch (Exception e) {

			if (logger.isErrorEnabled())
				logger.error("Error '{}' occured while handling message '{}' on consumer '{}', rejecting message",
						e.getMessage(), envelope.getDeliveryTag(), consumerTag);

			// Reject message, do not requeue
			channel.basicReject(envelope.getDeliveryTag(), false);
		}
	}

	/**
	 * Called when a basic.recover-ok is received in reply to a basic.recover.
	 */
	@Override
	public void handleRecoverOk(String consumerTag) {
		if (logger.isInfoEnabled())
			logger.info("Consumer with tag '{}' is successfuly recovered", consumerTag);
	}

	/**
	 * Called when either the channel or the underlying connection has been shut
	 * down.
	 */
	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException shutdownSignalException) {
		if (logger.isInfoEnabled())
			logger.info("Consumer with tag '{}' is shutting down: {}", consumerTag,
					shutdownSignalException.getMessage());

	}

}
