package org.shopfoundry.core.service.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default service info provider.
 *
 * @author Bojan Bijelic
 */
public class DefaultServiceInfoProvider implements ServiceInfoProvider {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultServiceInfoProvider.class);

	/**
	 * Constructor.
	 * 
	 * @param serviceGroup
	 * @param serviceVersion
	 */
	public DefaultServiceInfoProvider(String serviceGroup, String serviceVersion) {
		this.serviceGroup = serviceGroup;
		this.serviceVersion = serviceVersion;

		if (logger.isInfoEnabled())
			logger.info("{} initialized",
					DefaultServiceInfoProvider.class.getSimpleName());
	}

	/**
	 * Service group.
	 */
	private String serviceGroup;

	@Override
	public String getServiceGroup() throws ServiceInfoProviderException {
		if (this.serviceGroup == null)
			throw new ServiceInfoProviderException("Service group not set");

		return this.serviceGroup;
	}

	/**
	 * Service version.
	 */
	private String serviceVersion;

	@Override
	public String getServiceVersion() throws ServiceInfoProviderException {
		if (this.serviceVersion == null)
			throw new ServiceInfoProviderException("Service version not set");

		return this.serviceVersion;
	}

	/**
	 * Service GUID.
	 */
	private String serviceGUID;

	@Override
	public void setServiceGuid(String serviceGuid) {
		this.serviceGUID = serviceGuid;
	}

	@Override
	public String getServiceGuid() throws ServiceInfoProviderException {
		if (this.serviceGUID == null)
			throw new ServiceInfoProviderException("Service GUID not set");

		return this.serviceGUID;
	}

	@Override
	public String toString() {
		return "DefaultServiceInfoProvider [serviceGroup=" + serviceGroup
				+ ", serviceVersion=" + serviceVersion + ", serviceGUID="
				+ serviceGUID + "]";
	}

}
