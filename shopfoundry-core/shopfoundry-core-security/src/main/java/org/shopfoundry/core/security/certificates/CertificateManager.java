package org.shopfoundry.core.security.certificates;

import java.security.KeyStore;

/**
 * @author Bojan Bijelic
 */
public interface CertificateManager {

	/**
	 * Returns trusted certificates key store (JKS)
	 * 
	 * @return the trusted certificates key store
	 */
	public KeyStore getTrustedCerticiates();

	/**
	 * Returns client certificates key store (PKCS12).
	 * 
	 * @return the client certificates key store
	 */
	public KeyStore getClientCertificates();

}
