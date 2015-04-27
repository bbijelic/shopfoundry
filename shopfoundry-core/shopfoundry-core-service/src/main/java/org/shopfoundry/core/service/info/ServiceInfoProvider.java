package org.shopfoundry.core.service.info;

/**
 * Service info provider.
 *
 * @author Bojan Bijelic
 */
public interface ServiceInfoProvider {

	/**
	 * Returns service group.
	 * 
	 * @return the service group
	 * @throws ServiceInfoProviderException
	 */
	public String getServiceGroup() throws ServiceInfoProviderException;

	/**
	 * Returns service version.
	 * 
	 * @return the service version
	 * @throws ServiceInfoProviderException
	 */
	public String getServiceVersion() throws ServiceInfoProviderException;

	/**
	 * Sets service GUID.
	 * 
	 * @param serviceGuid
	 */
	public void setServiceGuid(String serviceGuid);

	/**
	 * Returns service GUID.
	 * 
	 * @return the service GUID
	 * @throws ServiceInfoProviderException
	 */
	public String getServiceGuid() throws ServiceInfoProviderException;
}
