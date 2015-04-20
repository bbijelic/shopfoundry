package org.shopfoundry.core.security;

import org.shopfoundry.core.security.certificates.CertificateManager;

/**
 * Security manager.
 * 
 * @author Bojan Bijelic
 */
public interface SecurityManager {

	/**
	 * Returns certificate manager
	 * 
	 * @return the certificate manager
	 */
	public CertificateManager getCertificateManager();

}
