package org.shopfoundry.core.service.gateway.http;

import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Connector;

/**
 * HTTP server context.
 * 
 * @author Bojan Bijelic
 */
public interface HttpServerContext {

	/**
	 * Returns list of available connectors.
	 * 
	 * @return the list of available connectors
	 */
	public List<Connector> getConnectors();

	/**
	 * Returns mapping of URIs to the HTTP servlets.
	 * 
	 * @return the mapping of URIs to the HTTP servlets
	 */
	public Map<String, Servlet> getServletMapping();

}
