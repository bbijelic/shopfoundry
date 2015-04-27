package org.shopfoundry.core.service.guid;

/**
 * GUID provider.
 *
 * @author Bojan Bijelic
 */
public interface GuidProvider {

	/**
	 * Returns generated GUID.
	 * 
	 * @return the GUID.
	 * @throws GuidException
	 */
	public String getGuid() throws GuidException;

}
