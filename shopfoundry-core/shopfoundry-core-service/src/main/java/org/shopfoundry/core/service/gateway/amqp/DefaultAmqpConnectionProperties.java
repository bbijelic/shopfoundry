package org.shopfoundry.core.service.gateway.amqp;

import org.shopfoundry.core.service.config.ConfigurationProvider;
import org.shopfoundry.core.service.config.ConfigurationProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AMQP Connection properties default implementation.
 * 
 * @author Bojan Bijelic
 */
public class DefaultAmqpConnectionProperties implements AmqpConnectionProperties {

	private final static Logger logger = LoggerFactory.getLogger(DefaultAmqpConnectionProperties.class);

	private String host;

	@Override
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	private int port;

	@Override
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	private String virtualHost;

	@Override
	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	private String username;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String password;

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private boolean usingSSL = false;

	@Override
	public boolean isUsingSSL() {
		return usingSSL;
	}

	public void setUsingSSL(boolean usingSSL) {
		this.usingSSL = usingSSL;
	}

	private String sslProtocol;

	@Override
	public String getSslProtocol() {
		return sslProtocol;
	}

	public void setSslProtocol(String sslProtocol) {
		this.sslProtocol = sslProtocol;
	}

	@Override
	public String toString() {
		return "DefaultAmqpConnectionProperties [host=" + host + ", port=" + port + ", virtualHost=" + virtualHost
				+ ", username=" + username + ", password=" + password + ", usingSSL=" + usingSSL + ", sslProtocol="
				+ sslProtocol + "]";
	}

	@Override
	public void importProperties(ConfigurationProvider configurationProvider) throws Exception {

		try {

			// Broker hostname
			host = configurationProvider.getConfigurationValue(AmqpConfigParams.Broker.HOSTNAME);
			// Broker port
			port = new Integer(configurationProvider.getConfigurationValue(AmqpConfigParams.Broker.PORT));
			// Broker username
			username = configurationProvider.getConfigurationValue(AmqpConfigParams.Broker.USERNAME);
			// Password
			password = configurationProvider.getConfigurationValue(AmqpConfigParams.Broker.PASSWORD);
			// Virtual host
			virtualHost = configurationProvider.getConfigurationValue(AmqpConfigParams.Broker.VIRTUALHOST);
			// Using ssl
			usingSSL = configurationProvider.getConfigurationValue(AmqpConfigParams.Broker.SSL_ENABLED)
					.toLowerCase() == "true" ? true : false;
			sslProtocol = configurationProvider.getConfigurationValue(AmqpConfigParams.Broker.SSL_VERSION);

		} catch (ConfigurationProviderException e) {
			if(logger.isErrorEnabled())
				logger.error(e.getMessage());
			
			throw new Exception(e.getMessage(), e);
		}

	}

}
