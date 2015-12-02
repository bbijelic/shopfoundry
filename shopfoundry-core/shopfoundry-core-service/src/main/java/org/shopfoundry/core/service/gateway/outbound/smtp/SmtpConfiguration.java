package org.shopfoundry.core.service.gateway.outbound.smtp;

import org.shopfoundry.core.service.config.ConfigurationProvider;

/**
 * SMTP configuration
 *
 * @author Bojan Bijelic
 */
public interface SmtpConfiguration {

	/**
	 * Hostname getter
	 * 
	 * @return the hostname
	 */
	public String getHostname();

	/**
	 * Port getter
	 * 
	 * @return the port
	 */
	public int getPort();

	/**
	 * Server username
	 * 
	 * @return the username
	 */
	public String getUsername();

	/**
	 * Server password
	 * 
	 * @return the password
	 */
	public String getPassword();

	/**
	 * Imports properties from configuration provider
	 * 
	 * @param configurationProvider
	 * @throws Exception
	 */
	public void importProperties(ConfigurationProvider configurationProvider) throws Exception;

}
