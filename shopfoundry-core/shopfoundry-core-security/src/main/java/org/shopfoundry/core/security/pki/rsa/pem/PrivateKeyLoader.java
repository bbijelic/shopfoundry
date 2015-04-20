package org.shopfoundry.core.security.pki.rsa.pem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

/**
 * PEM RSA Private key loader class.
 * 
 * @author Bojan Bijelic
 */
@SuppressWarnings("restriction")
public class PrivateKeyLoader {

	private final static Logger logger = LoggerFactory
			.getLogger(PrivateKeyLoader.class);

	/**
	 * Reads RSA private key parameters from a private key file.
	 * 
	 * @param privateKeyFile
	 * @return the RSA private key parameters
	 * @throws IOException
	 * @throws Exception
	 */
	public static RSAPrivateCrtKeyParameters getPrivateKeyParameters(
			File privateKeyFile) throws IOException, Exception {
		// read key bytes
		FileInputStream in = new FileInputStream(privateKeyFile);
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();

		String privateKey = new String(keyBytes, "UTF-8");

		privateKey = privateKey
				.replaceAll(
						"(-+BEGIN RSA PRIVATE KEY-+\\r?\\n|-+END RSA PRIVATE KEY-+\\r?\\n?)",
						"");

		BASE64Decoder decoder = new BASE64Decoder();
		keyBytes = decoder.decodeBuffer(privateKey);
		
		if(logger.isDebugEnabled())
			logger.debug("Loaded {} bytes", keyBytes.length);

		// generate private key
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPrivateCrtKey privKey = (RSAPrivateCrtKey) keyFactory
				.generatePrivate(spec);

		return new RSAPrivateCrtKeyParameters(privKey.getModulus(),
				privKey.getPublicExponent(), privKey.getPrivateExponent(),
				privKey.getPrimeP(), privKey.getPrimeQ(),
				privKey.getPrimeExponentP(), privKey.getPrimeExponentQ(),
				privKey.getCrtCoefficient());
	}

}
