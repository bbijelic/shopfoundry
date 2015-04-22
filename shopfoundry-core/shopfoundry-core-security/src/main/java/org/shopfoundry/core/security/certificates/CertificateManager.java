package org.shopfoundry.core.security.certificates;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.List;

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
	 * Imports trusted certificates into the key store (JKS)
	 * 
	 * @param trustedCertificates
	 * @throws KeyStoreException
	 */
	public void importTrustedCertificates(
			List<X509Certificate> trustedCertificates) throws KeyStoreException;

	/**
	 * Returns client certificates key store (PKCS12).
	 * 
	 * @return the client certificates key store
	 */
	public KeyStore getClientCertificates();

}
