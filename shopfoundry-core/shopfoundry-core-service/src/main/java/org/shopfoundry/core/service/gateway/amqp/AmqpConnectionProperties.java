package org.shopfoundry.core.service.gateway.amqp;

/**
 * AMQP Connection properties
 * 
 * @author Bojan Bijelic
 */
public interface AmqpConnectionProperties {

	/**
	 * Server host getter.
	 * 
	 * @return the server host name
	 */
	public String getHost();

	/**
	 * Server port getter.
	 * 
	 * @return the server port
	 */
	public int getPort();

	/**
	 * Server virtual host getter.
	 * 
	 * @return the server virtual host
	 */
	public String getVirtualHost();

	/**
	 * Server username getter.
	 * 
	 * @return the server username
	 */
	public String getUsername();

	/**
	 * Server password getter.
	 * 
	 * @return the server password
	 */
	public String getPassword();
	
}
