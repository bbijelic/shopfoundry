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

	private KeyStore trustedCertifiates;

	@Override
	public KeyStore getTrustedCerticiates() throws CertificateManagerException {
		if (this.trustedCertifiates == null)
			throw new CertificateManagerException(
					"Trusted certifiacate key store not set");

		return trustedCertifiates;
	}

	/**
	 * Constructor.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public DefaultCertificateManager() throws CertificateManagerException {

		try {

			// Creating an empty JKS keystore for a trusted certificates
			trustedCertifiates = KeyStore
					.getInstance(KeyStore.getDefaultType());
			trustedCertifiates.load(null, null);

			// Creating an empty PKCS12 keystore for client certificates
			endEntityCertificates = KeyStore.getInstance("PKCS12");
			endEntityCertificates.load(null, null);

		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			throw new CertificateManagerException(e.getMessage(), e);
		}

	}

	/**
	 * Client certificates.
	 */
	private KeyStore endEntityCertificates;

	@Override
	public KeyStore getEndEntityCertificates()
			throws CertificateManagerException {
		if (this.endEntityCertificates == null)
			throw new CertificateManagerException(
					"End entity certifiacate key store not set");
		return this.endEntityCertificates;
	}

	@Override
	public void importTrustedCertificates(
			List<X509Certificate> trustedCertificates)
			throws CertificateManagerException {

		// Add certificate chain to the trust store
		for (X509Certificate x509Certificate : trustedCertificates) {

			if (logger.isInfoEnabled())
				logger.info("Adding certificate to the trust store: {}",
						x509Certificate.getSubjectDN().toString());

			try {
				getTrustedCerticiates().setCertificateEntry(
						x509Certificate.getSubjectDN().toString(),
						x509Certificate);
			} catch (KeyStoreException e) {
				if (logger.isErrorEnabled())
					logger.error(e.getMessage(), e);

				throw new CertificateManagerException(e.getMessage(), e);
			}
		}

	}

}
