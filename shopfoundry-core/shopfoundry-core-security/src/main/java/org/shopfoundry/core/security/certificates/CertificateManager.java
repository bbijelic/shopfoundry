package org.shopfoundry.core.security.certificates;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Certificate manager
 * 
 * @author Bojan Bijelic
 */
public interface CertificateManager {

	/**
	 * Returns trusted certificates key store (JKS)
	 * 
	 * @return the trusted certificates key store
	 * @throws CertificateManagerException
	 */
	public KeyStore getTrustedCerticiates() throws CertificateManagerException;

	/**
	 * Imports trusted certificates into the key store (JKS)
	 * 
	 * @param trustedCertificates
	 * @throws CertificateManagerException
	 */
	public void importTrustedCertificates(
			List<X509Certificate> trustedCertificates)
			throws CertificateManagerException;

	/**
	 * Exports trusted certificates.
	 * 
	 * @return the PEM encoded trusted certificates chain
	 * @throws CertificateManagerException
	 */
	public String exportTrustedCertificates()
			throws CertificateManagerException;

	/**
	 * Returns end entity certificates key store (PKCS12).
	 * 
	 * @return the end entity certificates key store
	 * @throws CertificateManagerException
	 */
	public KeyStore getEndEntityCertificates()
			throws CertificateManagerException;

}
