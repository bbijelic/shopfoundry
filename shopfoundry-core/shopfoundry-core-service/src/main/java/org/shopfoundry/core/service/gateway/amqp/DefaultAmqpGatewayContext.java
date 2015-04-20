package org.shopfoundry.core.service.gateway.amqp;

import java.util.List;

/**
 * Default AMQP gateway context
 * 
 * @author Bojan Bijelic
 */
public class DefaultAmqpGatewayContext implements AmqpGatewayContext {

	private AmqpConnectionProperties amqpConnectionProperties;

	@Override
	public AmqpConnectionProperties getAmqpConnectionProperties() {
		return amqpConnectionProperties;
	}

	public void setAmqpConnectionProperties(
			AmqpConnectionProperties amqpConnectionProperties) {
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
	private List<String> routingKeys;

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

}
