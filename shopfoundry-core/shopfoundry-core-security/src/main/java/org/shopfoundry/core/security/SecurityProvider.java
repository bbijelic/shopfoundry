package org.shopfoundry.core.security;

import org.shopfoundry.core.security.certificates.CertificateManager;

/**
 * Security provider.
 *
 * @author Bojan Bijelic
 */
public interface SecurityProvider {

	/**
	 * Returns certificate manager.
	 * 
	 * @return the certificate manager
	 * @throws SecurityProviderException
	 */
	public CertificateManager getCertificateManager()
			throws SecurityProviderException;

}
