package org.shopfoundry.core.service.context;

import org.shopfoundry.core.security.SecurityProvider;
import org.shopfoundry.core.service.environment.EnvironmentProvider;
import org.shopfoundry.core.service.gateway.GatewayProvider;
import org.shopfoundry.core.service.guid.GuidProvider;
import org.shopfoundry.core.service.info.ServiceInfoProvider;

/**
 * Service Context.
 * 
 * @author Bojan Bijelic
 */
public interface ServiceContext {

	/**
	 * Returns service info provider.
	 * 
	 * @return the service info provider
	 * @throws ServiceContextException
	 */
	public ServiceInfoProvider getServiceInfoProvider()
			throws ServiceContextException;

	/**
	 * Returns environment provider.
	 * 
	 * @return the environment provider
	 * @throws ServiceContextException
	 */
	public EnvironmentProvider getEnvironmentProvider()
			throws ServiceContextException;

	/**
	 * Returns GUID provider.
	 * 
	 * @return the GUID provider
	 * @throws ServiceContextException
	 */
	public GuidProvider getGuidProvider() throws ServiceContextException;

	/**
	 * Returns security provider.
	 * 
	 * @return the security provider
	 * @throws ServiceContextException
	 */
	public SecurityProvider getSecurityProvider()
			throws ServiceContextException;

	/**
	 * Returns gateway provider.
	 * 
	 * @return the gateway provider
	 * @throws ServiceContextException
	 */
	public GatewayProvider getGatewayProvider() throws ServiceContextException;
}
