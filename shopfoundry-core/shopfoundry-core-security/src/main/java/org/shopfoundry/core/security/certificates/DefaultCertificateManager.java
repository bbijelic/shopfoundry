package org.shopfoundry.core.security.certificates;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bojan Bijelic
 */
public class DefaultCertificateManager implements CertificateManager {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultCertificateManager.class);

	private KeyStore trustedCerticiates;

	/**
	 * Trusted certificates setter.
	 * 
	 * @param trustedCerticiates
	 *            the trustedCerticiates to set
	 */
	public void setTrustedCerticiates(KeyStore trustedCerticiates) {
		this.trustedCerticiates = trustedCerticiates;
	}

	@Override
	public KeyStore getTrustedCerticiates() {
		return trustedCerticiates;
	}

	/**
	 * Constructor.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public DefaultCertificateManager() throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {

		// Creating an empty JKS keystore for a trusted certificates
		trustedCerticiates = KeyStore.getInstance(KeyStore.getDefaultType());
		trustedCerticiates.load(null, null);

		// Creating an empty PKCS12 keystore for client certificates
		clientCertificates = KeyStore.getInstance("PKCS12");
		clientCertificates.load(null, null);
	}

	/**
	 * Client certificates.
	 */
	private KeyStore clientCertificates;

	/**
	 * Client certificates setter.
	 * 
	 * @param clientCertificates
	 *            the clientCertificates to set
	 */
	public void setClientCertificates(KeyStore clientCertificates) {
		this.clientCertificates = clientCertificates;
	}

	@Override
	public KeyStore getClientCertificates() {
		return clientCertificates;
	}

	@Override
	public void importTrustedCertificates(
			List<X509Certificate> trustedCertificates) throws KeyStoreException {
		
		// Add certificate chain to the trust store
		for (X509Certificate x509Certificate : trustedCertificates) {

			if (logger.isInfoEnabled())
				logger.info("Adding certificate to the trust store: {}",
						x509Certificate.getSubjectDN().toString());

			getTrustedCerticiates().setCertificateEntry(
					x509Certificate.getSubjectDN().toString(), x509Certificate);
		}

	}

}
