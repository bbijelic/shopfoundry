package org.shopfoundry.core.service;

import java.util.List;

import org.shopfoundry.core.security.SecurityManager;
import org.shopfoundry.core.service.gateway.InboundGateway;
import org.shopfoundry.core.service.gateway.InboundSystemEventGateway;
import org.shopfoundry.core.service.gateway.OutboundGateway;
import org.shopfoundry.core.service.gateway.OutboundSystemEventGateway;
import org.shopfoundry.core.service.system.SystemSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of service context.
 * 
 * @author Bojan Bijelic
 */
public class DefaultServiceContext implements ServiceContext {

	/**
	 * Logger
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultServiceContext.class);

	/**
	 * Service group
	 */
	private String serviceGroup;

	/**
	 * Service group setter.
	 * 
	 * @param serviceGroup
	 *            the serviceGroup to set
	 */
	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	@Override
	public String getServiceGroup() {
		return serviceGroup;
	}

	/**
	 * System specification
	 */
	private SystemSpecification systemSpecification;

	/**
	 * System specification setter
	 * 
	 * @param systemSpecification
	 *            the systemSpecification to set
	 */
	public void setSystemSpecification(SystemSpecification systemSpecification) {
		this.systemSpecification = systemSpecification;
	}

	@Override
	public SystemSpecification getSystemSpecification() {
		return systemSpecification;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultServiceContext [serviceGroup=");
		builder.append(serviceGroup);
		builder.append(", systemSpecification=");
		builder.append(systemSpecification);
		builder.append(", inboundGateways=");
		builder.append(inboundGateways);
		builder.append(", outboundGateways=");
		builder.append(outboundGateways);
		builder.append(", inboundSystemEventGateways=");
		builder.append(inboundSystemEventGateways);
		builder.append(", outboundSystemEventGateways=");
		builder.append(outboundSystemEventGateways);
		builder.append(", serviceGUID=");
		builder.append(serviceGUID);
		builder.append(", registryServiceEndpoint=");
		builder.append(registryServiceEndpoint);
		builder.append(", securityManager=");
		builder.append(securityManager);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Inbound gateways
	 */
	private List<InboundGateway> inboundGateways;

	/**
	 * Inbound gateways setter.
	 * 
	 * @param inboundGateways
	 *            the inboundGateways to set
	 */
	public void setInboundGateways(List<InboundGateway> inboundGateways) {
		this.inboundGateways = inboundGateways;
	}

	@Override
	public List<InboundGateway> getInboundGateways() {
		return inboundGateways;
	}

	/**
	 * Outbound gateways
	 */
	private List<OutboundGateway> outboundGateways;

	/**
	 * Outbound gateways setter
	 * 
	 * @param outboundGateways
	 *            the outboundGateways to set
	 */
	public void setOutboundGateways(List<OutboundGateway> outboundGateways) {
		this.outboundGateways = outboundGateways;
	}

	@Override
	public List<OutboundGateway> getOutboundGateways() {
		return outboundGateways;
	}

	/**
	 * Inbound system event gateways
	 */
	private List<InboundSystemEventGateway> inboundSystemEventGateways;

	/**
	 * Inbound system event gateways setter.
	 * 
	 * @param inboundSystemEventGateways
	 *            the inboundSystemEventGateways to set
	 */
	public void setInboundSystemEventGateways(
			List<InboundSystemEventGateway> inboundSystemEventGateways) {
		this.inboundSystemEventGateways = inboundSystemEventGateways;
	}

	@Override
	public List<InboundSystemEventGateway> getInboundSystemEventGateways() {
		return inboundSystemEventGateways;
	}

	/**
	 * Outbound system event gateways
	 */
	private List<OutboundSystemEventGateway> outboundSystemEventGateways;

	/**
	 * Outbound system event gateways setter
	 * 
	 * @param outboundSystemEventGateways
	 *            the outboundSystemEventGateways to set
	 */
	public void setOutboundSystemEventGateways(
			List<OutboundSystemEventGateway> outboundSystemEventGateways) {
		this.outboundSystemEventGateways = outboundSystemEventGateways;
	}

	@Override
	public List<OutboundSystemEventGateway> getOutboundSystemEventGateways() {
		return outboundSystemEventGateways;
	}

	/**
	 * Service GUID.
	 */
	private String serviceGUID;

	/**
	 * Service GUID settter
	 * 
	 * @param serviceGUID
	 *            the serviceGUID to set
	 */
	public void setServiceGUID(String serviceGUID) {
		this.serviceGUID = serviceGUID;
	}

	@Override
	public String getServiceGUID() {
		return this.serviceGUID;
	}

	private String registryServiceEndpoint;

	/**
	 * Registry service endpoint setter
	 * 
	 * @param registryServiceEndpoint
	 *            the registryServiceEndpoint to set
	 */
	public void setRegistryServiceEndpoint(String registryServiceEndpoint) {
		this.registryServiceEndpoint = registryServiceEndpoint;
	}

	@Override
	public String getRegistryServiceEndpoint() {
		return registryServiceEndpoint;
	}

	/**
	 * Security manager
	 */
	private SecurityManager securityManager;

	/**
	 * Security manager setter
	 * 
	 * @param securityManager
	 *            the securityManager to set
	 */
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	@Override
	public SecurityManager getSecurityManager() {
		return securityManager;
	}

}
