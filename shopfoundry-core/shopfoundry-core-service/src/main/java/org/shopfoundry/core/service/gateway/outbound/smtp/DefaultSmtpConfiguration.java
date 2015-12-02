package org.shopfoundry.core.service.gateway.outbound.smtp;

import org.shopfoundry.core.service.config.ConfigurationProvider;

/**
 * SMTP configuration implementation
 *
 * @author Bojan Bijelic
 */
public class DefaultSmtpConfiguration implements SmtpConfiguration {

	/**
	 * Hostname
	 */
	private String hostname;

	/**
	 * Hostname setter
	 * 
	 * @param hostname
	 *            the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public String getHostname() {
		return hostname;
	}

	/**
	 * Port
	 */
	private int port;

	/**
	 * Port setter
	 * 
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public int getPort() {
		return port;
	}

	/**
	 * Username
	 */
	private String username;

	/**
	 * Username setter
	 * 
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * Password
	 */
	private String password;

	/**
	 * Password setter
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DefaultSmtpConfiguration [hostname=" + hostname + ", port=" + port + ", username=" + username
				+ ", password=*** ]";
	}

	@Override
	public void importProperties(ConfigurationProvider configurationProvider) throws Exception {

		if (configurationProvider.getConfiguration().containsKey(SmtpConfigParams.Server.HOSTNAME))
			hostname = configurationProvider.getConfigurationValue(SmtpConfigParams.Server.HOSTNAME);

		if (configurationProvider.getConfiguration().containsKey(SmtpConfigParams.Server.PORT))
			port = Integer.valueOf(configurationProvider.getConfigurationValue(SmtpConfigParams.Server.PORT));

		if (configurationProvider.getConfiguration().containsKey(SmtpConfigParams.Server.USERNAME))
			username = configurationProvider.getConfigurationValue(SmtpConfigParams.Server.USERNAME);

		if (configurationProvider.getConfiguration().containsKey(SmtpConfigParams.Server.PASSWORD))
			password = configurationProvider.getConfigurationValue(SmtpConfigParams.Server.PASSWORD);

	}

}
