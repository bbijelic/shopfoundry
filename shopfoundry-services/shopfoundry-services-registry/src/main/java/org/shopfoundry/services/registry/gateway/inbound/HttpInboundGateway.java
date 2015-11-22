package org.shopfoundry.services.registry.gateway.inbound;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.shopfoundry.core.security.SecurityProviderException;
import org.shopfoundry.core.security.certificates.CertificateManagerException;
import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.gateway.inbound.InboundGateway;
import org.shopfoundry.core.service.gateway.inbound.InboundGatewayException;
import org.shopfoundry.core.service.info.ServiceInfoProviderException;
import org.shopfoundry.services.registry.servlet.RegistrationServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http inbound gateway.
 *
 * @author Bojan Bijelic
 */
public class HttpInboundGateway implements InboundGateway {

	private static final Logger logger = LoggerFactory.getLogger(HttpInboundGateway.class);

	/**
	 * Http(s) Server
	 */
	private Server httpServer;

	private ServiceContext serviceContext;

	/**
	 * Constructor.
	 * 
	 * @param serviceContext
	 */
	public HttpInboundGateway(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	/**
	 * Constructor.
	 * 
	 */
	public HttpInboundGateway() {

	}

	/**
	 * Service context setter.
	 * 
	 * @param serviceContext
	 */
	public void setServiceContext(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	@Override
	public void start() throws Exception {

		// Create a basic jetty server object
		httpServer = new Server();

		// HTTP Configuration
		HttpConfiguration httpConfig = new HttpConfiguration();
		httpConfig.setSecureScheme("https");
		httpConfig.setSecurePort(8443);
		httpConfig.setOutputBufferSize(32768);

		// SSL Context Factory for HTTPS
		SslContextFactory sslContextFactory = new SslContextFactory();
		try {

			// Set trusted certificates
			sslContextFactory.setTrustStore(
					serviceContext.getSecurityProvider().getCertificateManager().getTrustedCerticiates());

			// Set key store
			sslContextFactory.setKeyStore(
					serviceContext.getSecurityProvider().getCertificateManager().getEndEntityCertificates());

			// Set key store type
			sslContextFactory.setKeyStoreType("PKCS12");

			// Set key store password
			sslContextFactory.setKeyStorePassword(serviceContext.getServiceInfoProvider().getServiceGroup());

		} catch (CertificateManagerException | SecurityProviderException | ServiceInfoProviderException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);
		}

		// HTTPS Configuration
		HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
		httpsConfig.addCustomizer(new SecureRequestCustomizer());

		// HTTPS connector
		ServerConnector https = new ServerConnector(httpServer, new SslConnectionFactory(sslContextFactory, "http/1.1"),
				new HttpConnectionFactory(httpsConfig));
		https.setPort(8443);

		// Set the connectors
		httpServer.setConnectors(new Connector[] { https });

		// Context
		ServletContextHandler context = new ServletContextHandler();
		httpServer.setHandler(context);

		// Servlet holder
		ServletHolder servletHolder = new ServletHolder(new RegistrationServlet(serviceContext));
		// Add servlet to the context
		context.addServlet(servletHolder, "/register");

		// Start server
		httpServer.start();
	}

	@Override
	public void stop() {
		try {
			// Stop http server
			httpServer.stop();
		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void configure() throws InboundGatewayException {
		// TODO Auto-generated method stub
		
	}

}
