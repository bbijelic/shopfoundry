package org.shopfoundry.core.service.gateway;

/**
 * Communication gateway.
 * 
 * @author Bojan Bijelic
 */
public interface Gateway {

	/**
	 * Starts gateway
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception;

	/**
	 * Stops gateway
	 */
	public void stop();

}
