package org.shopfoundry.core.service.gateway.http;

import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Connector;

/**
 * Default HTTP server context
 * 
 * @author Bojan Bijelic
 */
public class DefaultHttpServerContext implements HttpServerContext {

	/**
	 * Connectors.
	 */
	private List<Connector> connectors;

	@Override
	public List<Connector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<Connector> connectors) {
		this.connectors = connectors;
	}

	/**
	 * Servlet mapping
	 */
	private Map<String, Servlet> servletMapping;

	@Override
	public Map<String, Servlet> getServletMapping() {
		return servletMapping;
	}

	public void setServletMapping(Map<String, Servlet> servletMapping) {
		this.servletMapping = servletMapping;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultServerContext [connectors=");
		builder.append(connectors);
		builder.append(", servletMapping=");
		builder.append(servletMapping);
		builder.append("]");
		return builder.toString();
	}

}
