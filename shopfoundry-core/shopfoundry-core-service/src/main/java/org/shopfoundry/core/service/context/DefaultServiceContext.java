package org.shopfoundry.core.service.context;

import org.shopfoundry.core.security.SecurityProvider;
import org.shopfoundry.core.service.config.ConfigurationProvider;
import org.shopfoundry.core.service.environment.EnvironmentProvider;
import org.shopfoundry.core.service.gateway.GatewayProvider;
import org.shopfoundry.core.service.guid.GuidProvider;
import org.shopfoundry.core.service.info.ServiceInfoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default service context.
 *
 * @author Bojan Bijelic
 */
public class DefaultServiceContext implements ServiceContext {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DefaultServiceContext.class);

	/**
	 * Constructor.
	 * 
	 * @param serviceInfoProvider
	 * @param environmentProvider
	 * @param guidProvider
	 * @param securityProvider
	 * @param gatewayProvider
	 */
	public DefaultServiceContext(ConfigurationProvider configurationProvider, ServiceInfoProvider serviceInfoProvider,
			EnvironmentProvider environmentProvider, GuidProvider guidProvider, SecurityProvider securityProvider,
			GatewayProvider gatewayProvider) {
		this.configurationProvider = configurationProvider;
		this.serviceInfoProvider = serviceInfoProvider;
		this.environmentProvider = environmentProvider;
		this.guidProvider = guidProvider;
		this.securityProvider = securityProvider;
		this.gatewayProvider = gatewayProvider;

		if (logger.isInfoEnabled())
			logger.info("{} initialized", DefaultServiceContext.class.getSimpleName());
	}

	/**
	 * Service Info provider.
	 */
	private ServiceInfoProvider serviceInfoProvider;

	@Override
	public ServiceInfoProvider getServiceInfoProvider() throws ServiceContextException {
		if (this.serviceInfoProvider == null)
			throw new ServiceContextException("Service info provider not set");

		return this.serviceInfoProvider;
	}

	/**
	 * Environment provider.
	 */
	private EnvironmentProvider environmentProvider;

	@Override
	public EnvironmentProvider getEnvironmentProvider() throws ServiceContextException {
		if (this.environmentProvider == null)
			throw new ServiceContextException("Environment provider not set");

		return this.environmentProvider;
	}

	/**
	 * Guid provider.
	 */
	private GuidProvider guidProvider;

	@Override
	public GuidProvider getGuidProvider() throws ServiceContextException {
		if (this.guidProvider == null)
			throw new ServiceContextException("GUID provider not set");

		return this.guidProvider;
	}

	/**
	 * Security provider.
	 */
	private SecurityProvider securityProvider;

	@Override
	public SecurityProvider getSecurityProvider() throws ServiceContextException {
		if (this.securityProvider == null)
			throw new ServiceContextException("Security provider not set");

		return this.securityProvider;
	}

	/**
	 * Gateway provider.
	 */
	private GatewayProvider gatewayProvider;

	@Override
	public GatewayProvider getGatewayProvider() throws ServiceContextException {
		if (this.gatewayProvider == null)
			throw new ServiceContextException("Gateway provider not set");

		return this.gatewayProvider;
	}

	private ConfigurationProvider configurationProvider;

	@Override
	public ConfigurationProvider getConfigurationProvider() throws ServiceContextException {
		if (this.configurationProvider == null)
			throw new ServiceContextException("Configuration provider not set");

		return this.configurationProvider;
	}

}
