package org.shopfoundry.core.service.gateway.amqp;

import java.util.List;

import org.shopfoundry.core.service.config.ConfigurationProvider;

/**
 * AMQP gateway context.
 * 
 * @author Bojan Bijelic
 */
public interface AmqpGatewayContext {

	/**
	 * Returns AMQP connection properties.
	 * 
	 * @return the AMQP connection properties
	 */
	public AmqpConnectionProperties getAmqpConnectionProperties();

	/**
	 * Returns AMQP message consumer.
	 * 
	 * @return the AMQP message consumer
	 */
	public AmqpMessageConsumer getAmqpMessageConsumer();

	/**
	 * Returns AMQP exchange name
	 * 
	 * @return the AMQP exchange name
	 */
	public String getExchangeName();

	/**
	 * Returns queue name
	 * 
	 * @return the queue name
	 */
	public String getQueueName();

	/**
	 * Returns list of routing keys for the gateway
	 * 
	 * @return the list of routing keys
	 */
	public List<String> getRoutingKeys();

	/**
	 * Imports properties into the context.
	 * 
	 * @param properties
	 * @throws Exception
	 */
	public void importProperties(ConfigurationProvider configurationProvider) throws Exception;
	
	/**
	 * Amqp bus type
	 * @return the Amqp bus type
	 */
	public AmqpBusType getAmqpBusType();

}
