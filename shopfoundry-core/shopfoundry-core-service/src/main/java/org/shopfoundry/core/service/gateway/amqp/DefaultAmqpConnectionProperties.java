package org.shopfoundry.core.service.gateway.amqp;

/**
 * AMQP Connection properties default implementation.
 * 
 * @author Bojan Bijelic
 */
public class DefaultAmqpConnectionProperties implements
		AmqpConnectionProperties {

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultAmqpConnectionProperties [host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", virtualHost=");
		builder.append(virtualHost);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=***");
		builder.append("]");
		return builder.toString();
	}

}
