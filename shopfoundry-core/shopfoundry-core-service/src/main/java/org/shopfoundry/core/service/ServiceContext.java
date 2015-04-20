package org.shopfoundry.core.service;

import java.util.List;

import org.shopfoundry.core.security.SecurityManager;
import org.shopfoundry.core.service.gateway.InboundGateway;
import org.shopfoundry.core.service.gateway.InboundSystemEventGateway;
import org.shopfoundry.core.service.gateway.OutboundGateway;
import org.shopfoundry.core.service.gateway.OutboundSystemEventGateway;
import org.shopfoundry.core.service.system.SystemSpecification;

/**
 * Service context interface.
 * 
 * @author Bojan Bijelic
 */
public interface ServiceContext {

	/**
	 * Returns registry service endpoint.
	 * 
	 * @return the registry service endpoint
	 */
	public String getRegistryServiceEndpoint();

	/**
	 * Returns service group.
	 * 
	 * @return the service group
	 */
	public String getServiceGroup();

	/**
	 * Returns service GUID.
	 * 
	 * @return the service GUID
	 */
	public String getServiceGUID();

	/**
	 * Service GUID setter.
	 * 
	 * @param serviceGUID
	 */
	public void setServiceGUID(String serviceGUID);

	/**
	 * Returns system specification containing information about hardware and
	 * software
	 * 
	 * @return the system specification
	 */
	public SystemSpecification getSystemSpecification();

	/**
	 * Inbound gateways getter.
	 * 
	 * @return the list of inbound gateways
	 */
	public List<InboundGateway> getInboundGateways();

	/**
	 * Outbound gateways getter.
	 * 
	 * @return the list of outbound gateways
	 */
	public List<OutboundGateway> getOutboundGateways();

	/**
	 * Inbound system event gateways getter.
	 * 
	 * @return the list of inbound system event gateways
	 */
	public List<InboundSystemEventGateway> getInboundSystemEventGateways();

	/**
	 * Outbound system event gateways getter.
	 * 
	 * @return the list of outbound system event gateways
	 */
	public List<OutboundSystemEventGateway> getOutboundSystemEventGateways();

	/**
	 * Returns security manager
	 * 
	 * @return the security manager
	 */
	public SecurityManager getSecurityManager();

}
