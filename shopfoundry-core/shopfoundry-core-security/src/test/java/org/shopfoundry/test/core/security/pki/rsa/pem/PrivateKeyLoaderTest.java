package org.shopfoundry.test.core.security.pki.rsa.pem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;

import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.shopfoundry.core.security.pki.rsa.pem.PrivateKeyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Private Key Loader test.
 * 
 * @author Bojan Bijelic
 */
public class PrivateKeyLoaderTest {

	/**
	 * Logger
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(PrivateKeyLoaderTest.class);

	@BeforeClass
	public static void beforeClass() {
		if (logger.isInfoEnabled())
			logger.info("Registering BouncyCastle Security provider");

		// Add BouncyCastle security provider
		java.security.Security.addProvider(new BouncyCastleProvider());
	}

	@AfterClass
	public static void afterClass() {
		if (logger.isInfoEnabled())
			logger.info("Removing BouncyCastle Security provider");

		// Add BouncyCastle security provider
		java.security.Security
				.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
	}

	@Test
	public void testRSAKeyGet() {

		try {

			assertNotNull("Private key file missing",
					getClass().getResource("/shopfoundry-intermediate-ca.key"));

			// Open private key file
			File privateKeyFile = new File(getClass().getResource(
					"/shopfoundry-intermediate-ca.key").getFile());

			if (logger.isDebugEnabled()) {
				logger.debug(privateKeyFile.toString());
			}

			// Method under test
			RSAPrivateCrtKeyParameters keyParams = PrivateKeyLoader
					.getPrivateKeyParameters(privateKeyFile);
			
			if (logger.isDebugEnabled())
				logger.debug(keyParams.toString());

		} catch (Exception e) {

			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			fail();
		}
	}

}
