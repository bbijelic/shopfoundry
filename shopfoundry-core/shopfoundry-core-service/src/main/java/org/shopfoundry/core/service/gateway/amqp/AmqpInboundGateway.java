package org.shopfoundry.core.service.gateway.amqp;

import java.io.IOException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.shopfoundry.core.security.SecurityProvider;
import org.shopfoundry.core.service.config.ConfigurationProvider;
import org.shopfoundry.core.service.gateway.inbound.InboundGateway;
import org.shopfoundry.core.service.gateway.inbound.InboundGatewayException;
import org.shopfoundry.core.service.info.ServiceInfoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * AMQP Inbound Gateway implementation.
 * 
 * @author Bojan Bijelic
 */
public class AmqpInboundGateway implements InboundGateway {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AmqpInboundGateway.class);

	private ConfigurationProvider configurationProvider;

	private SecurityProvider securityProvider;

	private ServiceInfoProvider serviceInfoProvider;

	public AmqpInboundGateway(ServiceInfoProvider serviceInfoProvider, SecurityProvider securityProvider,
			ConfigurationProvider configurationProvider, AmqpGatewayContext amqpGatewayContext) {
		this.serviceInfoProvider = serviceInfoProvider;
		this.securityProvider = securityProvider;
		this.configurationProvider = configurationProvider;
		this.amqpGatewayContext = amqpGatewayContext;
	}

	/**
	 * AMQP gateway context.
	 */
	private AmqpGatewayContext amqpGatewayContext;

	public AmqpGatewayContext getAmqpGatewayContext() {
		return amqpGatewayContext;
	}

	public void setAmqpGatewayContext(AmqpGatewayContext amqpGatewayContext) {
		this.amqpGatewayContext = amqpGatewayContext;
	}

	/**
	 * Connection
	 */
	private Connection connection;

	/**
	 * Channel
	 */
	private Channel channel;

	@Override
	public void start() throws Exception {

		// Validate server host
		if (amqpGatewayContext.getAmqpConnectionProperties().getHost().isEmpty()) {
			String errorMessage = "AMQP server host name can not be empty";
			if (logger.isErrorEnabled())
				logger.error(errorMessage);

			throw new Exception(errorMessage);
		}

		// Validate server host
		if (amqpGatewayContext.getAmqpConnectionProperties().getPort() == 0) {
			String errorMessage = "AMQP server port can not be empty";
			if (logger.isErrorEnabled())
				logger.error(errorMessage);

			throw new Exception(errorMessage);
		} else {

			// Validate server host
			if (amqpGatewayContext.getAmqpConnectionProperties().getPort() < 1
					|| amqpGatewayContext.getAmqpConnectionProperties().getPort() > 65535) {
				String errorMessage = "AMQP server port can not be less than 1 and greater that 65535";
				if (logger.isErrorEnabled())
					logger.error(errorMessage);

				throw new Exception(errorMessage);
			}

		}

		// Validate username
		if (amqpGatewayContext.getAmqpConnectionProperties().getUsername().isEmpty()) {
			String errorMessage = "AMQP server username is not set";
			if (logger.isErrorEnabled())
				logger.error(errorMessage);

			throw new Exception(errorMessage);
		}

		// Validate password
		if (amqpGatewayContext.getAmqpConnectionProperties().getPassword().isEmpty()) {
			String errorMessage = "AMQP server password is not set";
			if (logger.isErrorEnabled())
				logger.error(errorMessage);

			throw new Exception(errorMessage);
		}

		// Initialize connection factory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(amqpGatewayContext.getAmqpConnectionProperties().getHost());
		connectionFactory.setPort(amqpGatewayContext.getAmqpConnectionProperties().getPort());
		connectionFactory.setVirtualHost(amqpGatewayContext.getAmqpConnectionProperties().getVirtualHost());
		connectionFactory.setUsername(amqpGatewayContext.getAmqpConnectionProperties().getUsername());
		connectionFactory.setPassword(amqpGatewayContext.getAmqpConnectionProperties().getPassword());

		if (amqpGatewayContext.getAmqpConnectionProperties().isUsingSSL()) {

			// End entity key manager
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			keyManagerFactory.init(securityProvider.getCertificateManager().getEndEntityCertificates(),
					serviceInfoProvider.getServiceGuid().toCharArray());

			// Truested certificates manager factory
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			trustManagerFactory.init(securityProvider.getCertificateManager().getTrustedCerticiates());

			// Define ssl context
			SSLContext sslContext = SSLContext
					.getInstance(amqpGatewayContext.getAmqpConnectionProperties().getSslProtocol());
			sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

			// Provide ssl contect to the connection factory
			connectionFactory.useSslProtocol(sslContext);
		}

		// Create connection
		connection = connectionFactory.newConnection();

		if (connection.isOpen()) {
			// When connection is open, create new channel

			if (logger.isInfoEnabled())
				logger.info("AMQP connection is established");

			// Open channel
			channel = connection.createChannel();

			if (channel == null) {
				String errorMessage = "AMQP Channel is not set";
				if (logger.isErrorEnabled())
					logger.error(errorMessage);

				throw new Exception(errorMessage);
			}

			if (channel.isOpen()) {
				// When channel is open, declare exchgange passively, create
				// queue

				if (logger.isInfoEnabled())
					logger.info("AMQP channel is established");

				// Create direct durable exchange
				// Durable exchange will survive server crash
				channel.exchangeDeclare(amqpGatewayContext.getExchangeName(), "direct", true);

				String queue;

				// When queue name is defined, it is considered for queue to be
				// durable persistent
				if (amqpGatewayContext.getQueueName() != null) {

					// Get configured queue
					queue = amqpGatewayContext.getQueueName();

				} else {
					// Actively declare a server-named exclusive, autodelete,
					// non-durable queue.
					queue = channel.queueDeclare().getQueue();

					if (logger.isDebugEnabled())
						logger.debug("Declared queue '{}'", queue);
				}

				if (!amqpGatewayContext.getRoutingKeys().isEmpty()) {
					// Bind routing keys to the queue on the exchange

					for (String routingKey : amqpGatewayContext.getRoutingKeys()) {

						// if routing key contains {uuid}, replace it with
						// service uuid
						if (routingKey.contains("{uuid}"))
							routingKey = routingKey.replace("{uuid}", serviceInfoProvider.getServiceGuid());

						if (logger.isDebugEnabled())
							logger.debug("Binding routing key '{}' to the queue '{}' on the exchange '{}'", routingKey,
									queue, amqpGatewayContext.getExchangeName());
						// Bind
						channel.queueBind(queue, amqpGatewayContext.getExchangeName(), routingKey);
					}

					if (amqpGatewayContext.getAmqpMessageConsumer() != null) {

						// Associate channel with consumer
						amqpGatewayContext.getAmqpMessageConsumer().associateWithChannel(channel);

						// Start non-autoacknowladged consumer
						String consumerTag = channel.basicConsume(queue, false,
								amqpGatewayContext.getAmqpMessageConsumer());

						if (logger.isDebugEnabled())
							logger.debug("Started consuming on consumer '{}'", consumerTag);

					} else {
						throw new Exception(String.format("Consumer implementation not defined for the %s bus",
								amqpGatewayContext.getAmqpBusType().toString()));
					}

				} else {

					// No routing keys defined, throw exception
					String errorMessage = "No routing keys defined for the queue '" + queue + "' on exchanged '"
							+ amqpGatewayContext.getExchangeName() + "'";

					if (logger.isErrorEnabled())
						logger.error(errorMessage);

					throw new Exception(errorMessage);
				}

			} else {
				String errorMessage = "AMQP channel is not opened";
				if (logger.isErrorEnabled())
					logger.error(errorMessage);
				// Throw message
				throw new Exception(errorMessage);
			}

		} else {
			String errorMessage = "AMQP connection is not opened";
			if (logger.isErrorEnabled())
				logger.error(errorMessage);
			// Throw message
			throw new Exception(errorMessage);
		}

		if (logger.isInfoEnabled())
			logger.info("AMQP Inbound Gateway started successfully");
	}

	@Override
	public void stop() {

		if (channel != null && channel.isOpen()) {
			try {
				// Close channel
				channel.close();
			} catch (IOException e) {
				if (logger.isErrorEnabled())
					logger.error("Failed to close AMQP channel: {}", e.getMessage());
			}
		}

		if (connection != null && connection.isOpen()) {
			try {
				// Close connection
				connection.close();
			} catch (IOException e) {
				if (logger.isErrorEnabled())
					logger.error("Failed to close AMQP connection: {}", e.getMessage());
			}
		}

		if (logger.isInfoEnabled())
			logger.info("AMQP inbound gateway stopped");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AmqpInboundGateway [amqpGatewayContext=");
		builder.append(amqpGatewayContext);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public void configure() throws InboundGatewayException {

		try {

			// Import amqp connection properties
			getAmqpGatewayContext().getAmqpConnectionProperties().importProperties(configurationProvider);

			// Import amqp context properties
			getAmqpGatewayContext().importProperties(configurationProvider);

		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage());

			throw new InboundGatewayException(e.getMessage(), e);
		}

	}

}
