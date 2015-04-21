/**
 * 
 */
package org.shopfoundry.test.core.security.pki;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.security.KeyPair;
import java.security.cert.X509Certificate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.shopfoundry.core.security.pki.CertificateAuthority;
import org.shopfoundry.core.security.pki.CertificateSigningRequest;
import org.shopfoundry.core.security.pki.rsa.RSAKeyPairGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Certificate authority test class.
 * 
 * @author Bojan Bijelic
 */
public class CertificateAuthorityTest {

	private static final Logger logger = LoggerFactory
			.getLogger(CertificateAuthorityTest.class);

	@BeforeClass
	public static void beforeClass() {

	}

	@AfterClass
	public static void afterClass() {

	}

	@Test
	public void testGetCaCertificates() {

		try {
			// Root CA certificate
			File rootCaCertificateFile = new File(getClass().getResource(
					"/shopfoundry-root-ca.crt").getFile());
			// Root CA private key
			File rootCaPrivateKeyFile = new File(getClass().getResource(
					"/shopfoundry-root-ca.key").getFile());

			// Intermediate CA certificate
			File intermediateCaCertificateFile = new File(getClass()
					.getResource("/shopfoundry-intermediate-ca.crt").getFile());
			// Intermediate CA private key
			File intermediateCaPrivateKeyFile = new File(getClass()
					.getResource("/shopfoundry-intermediate-ca.key").getFile());

			// Initialize certificate authority
			CertificateAuthority certificateAuthority = CertificateAuthority
					.getInstance(rootCaCertificateFile, rootCaPrivateKeyFile,
							intermediateCaCertificateFile,
							intermediateCaPrivateKeyFile);

			assertNotNull(certificateAuthority.getRootCACertificate());
			assertNotNull(certificateAuthority.getCACertificate());

		} catch (Exception e) {

			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			fail();
		}

	}

	@Test
	public void testSignCertificate() {

		try {

			// Root CA certificate
			File rootCaCertificateFile = new File(getClass().getResource(
					"/shopfoundry-root-ca.crt").getFile());
			// Root CA private key
			File rootCaPrivateKeyFile = new File(getClass().getResource(
					"/shopfoundry-root-ca.key").getFile());

			// Intermediate CA certificate
			File intermediateCaCertificateFile = new File(getClass()
					.getResource("/shopfoundry-intermediate-ca.crt").getFile());
			// Intermediate CA private key
			File intermediateCaPrivateKeyFile = new File(getClass()
					.getResource("/shopfoundry-intermediate-ca.key").getFile());

			// Initialize certificate authority
			CertificateAuthority certificateAuthority = CertificateAuthority
					.getInstance(rootCaCertificateFile, rootCaPrivateKeyFile,
							intermediateCaCertificateFile,
							intermediateCaPrivateKeyFile);

			// Certificate signing request
			CertificateSigningRequest certificateSigningRequest = new CertificateSigningRequest(
					"TestService", "test.service@service", "Test",
					"Shopfoundry", "Osijek", "Osjecko-baranjska zupanija", "HR");

			if (logger.isTraceEnabled())
				logger.trace("CSR: {}", certificateSigningRequest.toString());

			// Generate key pair so we can sign certificate
			KeyPair keyPair = RSAKeyPairGenerator.generateKey(2048);

			if (logger.isTraceEnabled())
				logger.trace("Generated public key: {}", keyPair.getPublic());

			// Sign certificate
			X509Certificate signedCertificate = certificateAuthority
					.generateAndSignCertificate(certificateSigningRequest,
							3650, keyPair.getPublic()).getSignedCertificate();

			assertNotNull(signedCertificate);

			if (logger.isTraceEnabled())
				logger.trace("Signed certificate subject DN: {}",
						signedCertificate.getSubjectDN().toString());

		} catch (Exception e) {

			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			fail();
		}

	}
}
