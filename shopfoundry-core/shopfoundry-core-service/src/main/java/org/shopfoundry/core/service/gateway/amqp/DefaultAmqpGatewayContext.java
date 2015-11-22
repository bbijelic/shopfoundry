package org.shopfoundry.core.service.gateway.amqp;

import java.util.ArrayList;
import java.util.List;

import org.shopfoundry.core.service.config.ConfigurationProvider;
import org.shopfoundry.core.service.config.ConfigurationProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default AMQP gateway context
 * 
 * @author Bojan Bijelic
 */
public class DefaultAmqpGatewayContext implements AmqpGatewayContext {

	private static final Logger logger = LoggerFactory.getLogger(DefaultAmqpGatewayContext.class);

	private AmqpBusType amqpBusType;

	public AmqpBusType getAmqpBusType() {
		return amqpBusType;
	}

	public DefaultAmqpGatewayContext(AmqpConnectionProperties amqpConnectionProperties,
			AmqpMessageConsumer amqpMessageConsumer, AmqpBusType amqpBusType) {
		this.amqpConnectionProperties = amqpConnectionProperties;
		this.amqpMessageConsumer = amqpMessageConsumer;
		this.amqpBusType = amqpBusType;
	}

	private AmqpConnectionProperties amqpConnectionProperties;

	@Override
	public AmqpConnectionProperties getAmqpConnectionProperties() {
		return amqpConnectionProperties;
	}

	public void setAmqpConnectionProperties(AmqpConnectionProperties amqpConnectionProperties) {
		this.amqpConnectionProperties = amqpConnectionProperties;
	}

	/**
	 * AMQP message consumer
	 */
	private AmqpMessageConsumer amqpMessageConsumer;

	@Override
	public AmqpMessageConsumer getAmqpMessageConsumer() {
		return amqpMessageConsumer;
	}

	public void setAmqpMessageConsumer(AmqpMessageConsumer amqpMessageConsumer) {
		this.amqpMessageConsumer = amqpMessageConsumer;
	}

	/**
	 * Exchange name
	 */
	private String exchangeName;

	@Override
	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	private String queueName;

	@Override
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * Routing keys
	 */
	private List<String> routingKeys = new ArrayList<String>();

	@Override
	public List<String> getRoutingKeys() {
		return routingKeys;
	}

	public void setRoutingKeys(List<String> routingKeys) {
		this.routingKeys = routingKeys;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultAmqpGatewayContext [amqpConnectionProperties=");
		builder.append(amqpConnectionProperties);
		builder.append(", amqpMessageConsumer=");
		builder.append(amqpMessageConsumer);
		builder.append(", exchangeName=");
		builder.append(exchangeName);
		builder.append(", queueName=");
		builder.append(queueName);
		builder.append(", routingKeys=");
		builder.append(routingKeys);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public void importProperties(ConfigurationProvider configurationProvider) throws Exception {

		try {

			if (amqpBusType.equals(AmqpBusType.EVENT)) {

				// Exchange name
				exchangeName = configurationProvider.getConfigurationValue(AmqpConfigParams.Bus.Event.EXCHANGE_NAME);

				// Add routing keys
				routingKeys.add(configurationProvider.getConfigurationValue(AmqpConfigParams.Bus.Event.ROUTING_ALL));
				routingKeys.add(
						configurationProvider.getConfigurationValue(AmqpConfigParams.Bus.Event.ROUTING_SERVICE_GROUP));
				routingKeys.add(configurationProvider
						.getConfigurationValue(AmqpConfigParams.Bus.Event.ROUTING_SERVICE_INSTANCE));

			} else if (amqpBusType.equals(AmqpBusType.SERVICE)) {

				// Exchange name
				exchangeName = configurationProvider.getConfigurationValue(AmqpConfigParams.Bus.Service.EXCHANGE_NAME);
				// Queue name
				queueName = configurationProvider.getConfigurationValue(AmqpConfigParams.Bus.Service.QUEUE_NAME);
				// Add routing keys
				routingKeys.add(configurationProvider.getConfigurationValue(AmqpConfigParams.Bus.Service.ROUTING));
			}

		} catch (ConfigurationProviderException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage());

			throw new Exception(e.getMessage(), e);
		}

	}

}
