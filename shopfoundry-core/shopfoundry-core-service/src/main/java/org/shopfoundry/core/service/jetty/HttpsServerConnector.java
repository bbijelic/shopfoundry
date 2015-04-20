package org.shopfoundry.core.service.jetty;

import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;

/**
 * @author Bojan Bijelic
 */
public class HttpsServerConnector extends ServerConnector {

	/**
	 * @param server
	 * @param factories
	 */
	public HttpsServerConnector(Server server,
			SslConnectionFactory sslConnectionFactory,
			HttpConnectionFactory httpConnectionFactory) {
		super(server, sslConnectionFactory, httpConnectionFactory);
	}

}
