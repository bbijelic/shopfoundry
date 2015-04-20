package org.shopfoundry.core.security;

import org.shopfoundry.core.security.certificates.CertificateManager;

/**
 * Default security manager
 * 
 * @author Bojan Bijelic
 */
public class DefaultSecurityManager implements SecurityManager {

	/**
	 * Certificate manager
	 */
	private CertificateManager certificateManager;

	/**
	 * Certificate manager setter
	 * 
	 * @param certificateManager
	 *            the certificateManager to set
	 */
	public void setCertificateManager(CertificateManager certificateManager) {
		this.certificateManager = certificateManager;
	}

	@Override
	public CertificateManager getCertificateManager() {
		return certificateManager;
	}

}
