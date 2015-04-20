package org.shopfoundry.core.service.gateway.http;

import java.util.Map;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.shopfoundry.core.service.gateway.InboundGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP inbound gateway
 * 
 * @author Bojan Bijelic
 */
public class DefaultHttpInboundGateway implements InboundGateway {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultHttpInboundGateway.class);

	/**
	 * HTTP server
	 */
	private Server httpServer;
	
	public Server getHttpServer() {
		return httpServer;
	}

	public void setHttpServer(Server httpServer) {
		this.httpServer = httpServer;
	}

	/**
	 * HTTP server context
	 */
	private HttpServerContext httpServerContext;

	/**
	 * Returns HTTP server context.
	 * 
	 * @return the httpServerContext
	 */
	public HttpServerContext getHttpServerContext() {
		return httpServerContext;
	}

	/**
	 * Sets HTTP server context.
	 * 
	 * @param httpServerContext
	 *            the httpServerContext to set
	 */
	public void setHttpServerContext(HttpServerContext httpServerContext) {
		this.httpServerContext = httpServerContext;
	}

	@Override
	public void start() throws Exception {

		// Validate HTTP server context
		if (httpServerContext == null) {
			String errorMessage = "HTTP server context must be defined";
			if (logger.isErrorEnabled())
				logger.error(errorMessage);

			throw new Exception(errorMessage);
		}

		// Validate HTTP server connectors
		// Expected is at least one connector
		if (httpServerContext.getConnectors().isEmpty()) {
			String errorMessage = "Al least one HTTP server connector must be defined";
			if (logger.isErrorEnabled())
				logger.error(errorMessage);

			throw new Exception(errorMessage);
		}

		// Validate HTTP server servlet mapping
		// Expected is at least one mapping
		if (httpServerContext.getServletMapping().isEmpty()) {
			String errorMessage = "Al least one HTTP server servlet mapping must be defined";
			if (logger.isErrorEnabled())
				logger.error(errorMessage);

			throw new Exception(errorMessage);
		}

		// Add all connectors
		for (Connector connector : httpServerContext.getConnectors()) {
			// Add connector to server
			httpServer.addConnector(connector);

			if (logger.isDebugEnabled())
				logger.debug(
						"Connector '{}' successfully registered to server",
						connector.getName());
		}

		// Initialize servlet context handler
		ServletContextHandler servletContextHandler = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		servletContextHandler.setContextPath("/");
		httpServer.setHandler(servletContextHandler);

		// Add all servlets
		for (Map.Entry<String, Servlet> servletMap : httpServerContext
				.getServletMapping().entrySet()) {

			// Add servlet to server
			servletContextHandler.addServlet(
					new ServletHolder(servletMap.getValue()),
					servletMap.getKey());

			if (logger.isDebugEnabled())
				logger.debug("HTTP servlet registered for the path '{}'",
						servletMap.getKey());
		}

		// Start server
		httpServer.start();

		if (logger.isInfoEnabled())
			logger.info("HTTP Inbound Gateway started successfully");

	}

	@Override
	public void stop() {

		try {
			httpServer.stop();
		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);
		}

		if (logger.isInfoEnabled())
			logger.info("HTTP Inbound Gateway stopped");
	}

}
