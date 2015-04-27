package org.shopfoundry.core.security;

import org.shopfoundry.core.security.certificates.CertificateManager;

/**
 * Default security manager
 * 
 * @author Bojan Bijelic
 */
public class DefaultSecurityProvider implements SecurityProvider {

	/**
	 * Certificate manager
	 */
	private CertificateManager certificateManager;

	/**
	 * Constructor.
	 * 
	 * @param certificateManager
	 */
	public DefaultSecurityProvider(CertificateManager certificateManager) {
		this.certificateManager = certificateManager;
	}

	@Override
	public CertificateManager getCertificateManager()
			throws SecurityProviderException {
		if (this.certificateManager == null)
			throw new SecurityProviderException("Certificate manager not set.");
		return certificateManager;
	}

}
