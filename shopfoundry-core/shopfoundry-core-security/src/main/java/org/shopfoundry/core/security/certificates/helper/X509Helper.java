package org.shopfoundry.core.security.certificates.helper;

import java.util.Hashtable;

import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;

/**
 * X509 Helper class. Subsitution for private X509Util class from BouncyCastle
 * library¸
 * 
 * @author Bojan Bijelic
 */
@SuppressWarnings("unchecked")
public class X509Helper {

	@SuppressWarnings("rawtypes")
	private static Hashtable algorithms = new Hashtable();

	static {
		algorithms.put("MD2WITHRSAENCRYPTION", new DERObjectIdentifier(
				"1.2.840.113549.1.1.2"));
		algorithms.put("MD2WITHRSA", new DERObjectIdentifier(
				"1.2.840.113549.1.1.2"));
		algorithms.put("MD5WITHRSAENCRYPTION", new DERObjectIdentifier(
				"1.2.840.113549.1.1.4"));
		algorithms.put("MD5WITHRSA", new DERObjectIdentifier(
				"1.2.840.113549.1.1.4"));
		algorithms.put("SHA1WITHRSAENCRYPTION", new DERObjectIdentifier(
				"1.2.840.113549.1.1.5"));
		algorithms.put("SHA1WITHRSA", new DERObjectIdentifier(
				"1.2.840.113549.1.1.5"));
		algorithms.put("SHA224WITHRSAENCRYPTION",
				PKCSObjectIdentifiers.sha224WithRSAEncryption);
		algorithms.put("SHA224WITHRSA",
				PKCSObjectIdentifiers.sha224WithRSAEncryption);
		algorithms.put("SHA256WITHRSAENCRYPTION",
				PKCSObjectIdentifiers.sha256WithRSAEncryption);
		algorithms.put("SHA256WITHRSA",
				PKCSObjectIdentifiers.sha256WithRSAEncryption);
		algorithms.put("SHA384WITHRSAENCRYPTION",
				PKCSObjectIdentifiers.sha384WithRSAEncryption);
		algorithms.put("SHA384WITHRSA",
				PKCSObjectIdentifiers.sha384WithRSAEncryption);
		algorithms.put("SHA512WITHRSAENCRYPTION",
				PKCSObjectIdentifiers.sha512WithRSAEncryption);
		algorithms.put("SHA512WITHRSA",
				PKCSObjectIdentifiers.sha512WithRSAEncryption);
		algorithms.put("RIPEMD160WITHRSAENCRYPTION", new DERObjectIdentifier(
				"1.3.36.3.3.1.2"));
		algorithms.put("RIPEMD160WITHRSA", new DERObjectIdentifier(
				"1.3.36.3.3.1.2"));
		algorithms.put("SHA1WITHDSA", new DERObjectIdentifier(
				"1.2.840.10040.4.3"));
		algorithms.put("DSAWITHSHA1", new DERObjectIdentifier(
				"1.2.840.10040.4.3"));
		algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
		algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
		algorithms.put("GOST3411WITHGOST3410",
				CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
		algorithms.put("GOST3411WITHGOST3410-94",
				CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
	}

	public static DERObjectIdentifier getAlgorithmOID(String algorithmName) {
		algorithmName = algorithmName.toUpperCase();

		if (algorithms.containsKey(algorithmName)) {
			return (DERObjectIdentifier) algorithms.get(algorithmName);
		}

		return new DERObjectIdentifier(algorithmName);
	}

}
