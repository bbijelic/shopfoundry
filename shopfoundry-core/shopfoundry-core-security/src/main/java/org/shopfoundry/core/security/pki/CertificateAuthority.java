package org.shopfoundry.core.security.pki;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jce.PrincipalUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;
import org.shopfoundry.core.security.certificates.helper.X509Helper;
import org.shopfoundry.core.security.pki.rsa.pem.PrivateKeyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Certificate authority
 * 
 * @author Bojan Bijelic
 */
public class CertificateAuthority {

	private final static Logger logger = LoggerFactory
			.getLogger(CertificateAuthority.class);

	/**
	 * Root CA Certificate
	 */
	private X509Certificate rootCACertificate;

	/**
	 * Root CA Private key
	 */
	@SuppressWarnings("unused")
	private RSAPrivateCrtKeyParameters rootCACertificatePrivateKey;

	/**
	 * Returns Root CA Certificate
	 * 
	 * @return the root CA certificate
	 */
	public X509Certificate getRootCACertificate() {
		return rootCACertificate;
	}

	/**
	 * CA Private key
	 */
	private RSAPrivateCrtKeyParameters caCertificatePrivateKey;

	/**
	 * CA Certificate
	 */
	private X509Certificate caCertificate;

	public X509Certificate getCACertificate() {
		return caCertificate;
	}

	/**
	 * Singleton
	 */
	private static CertificateAuthority instance;

	/**
	 * Returns singleton instance of certificate authority.
	 * 
	 * @return the certificate authority
	 * @throws Exception
	 */
	public static CertificateAuthority getInstance(File rootCaCertificateFile,
			File rootCaCertificateKeyFile, File caCertificateFile,
			File caCertificateKeyFile) throws Exception {

		if (instance == null)
			instance = new CertificateAuthority(rootCaCertificateFile,
					rootCaCertificateKeyFile, caCertificateFile,
					caCertificateKeyFile);

		return instance;
	}

	/**
	 * Constructor.
	 * 
	 * @param rootCaCertificateFile
	 * @param rootCaCertificateKeyFile
	 * @param caCertificateFile
	 * @param caCertificateKeyFile
	 * @throws Exception
	 */
	public CertificateAuthority(File rootCaCertificateFile,
			File rootCaCertificateKeyFile, File caCertificateFile,
			File caCertificateKeyFile) throws Exception {

		// Register security provider
		registerBouncyCastleSecurityProvider();

		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X.509");

		// Load root CA certificate
		InputStream rootCaCertificateInputStream = new FileInputStream(
				rootCaCertificateFile);
		rootCACertificate = (X509Certificate) certificateFactory
				.generateCertificate(rootCaCertificateInputStream);
		// Verify integrity
		rootCACertificate.verify(rootCACertificate.getPublicKey());

		if (logger.isTraceEnabled())
			logger.trace(rootCACertificate.getSubjectDN().toString());

		// Load CA certificate
		InputStream caCertificateInputStream = new FileInputStream(
				caCertificateFile);
		caCertificate = (X509Certificate) certificateFactory
				.generateCertificate(caCertificateInputStream);
		// Verify integrity
		caCertificate.verify(rootCACertificate.getPublicKey());

		if (logger.isTraceEnabled())
			logger.trace(caCertificate.getSubjectDN().toString());

		// Load private keys
		// Load root CA private key
		rootCACertificatePrivateKey = PrivateKeyLoader
				.getPrivateKeyParameters(rootCaCertificateKeyFile);

		// Load CA private key
		caCertificatePrivateKey = PrivateKeyLoader
				.getPrivateKeyParameters(caCertificateKeyFile);
	}

	/**
	 * Registers Bouncy Castle security provider
	 */
	private void registerBouncyCastleSecurityProvider() {
		if (logger.isInfoEnabled())
			logger.info("Registering BouncyCastle Security provider");

		// Add BouncyCastle security provider
		java.security.Security.addProvider(new BouncyCastleProvider());
	}

	private X509Certificate signedCertificate;

	@SuppressWarnings("resource")
	public CertificateAuthority generateAndSignCertificate(
			CertificateSigningRequest certificateSignignRequest,
			int validityDays, PublicKey requestorPublicKey)
			throws NoSuchAlgorithmException, IOException,
			InvalidCipherTextException, InvalidKeyException,
			CertificateException, NoSuchProviderException, SignatureException {

		// Client certificate expiry
		Calendar clientCertificateExpiry = Calendar.getInstance();
		clientCertificateExpiry.add(Calendar.DAY_OF_YEAR, validityDays);

		// For CSR
		X509Name x509Name = new X509Name(certificateSignignRequest.toString());
		V3TBSCertificateGenerator certGen = new V3TBSCertificateGenerator();
		certGen.setSerialNumber(new DERInteger(BigInteger.valueOf(System
				.currentTimeMillis())));
		certGen.setIssuer(PrincipalUtil.getSubjectX509Principal(caCertificate));
		certGen.setSubject(x509Name);

		DERObjectIdentifier sigOID = (DERObjectIdentifier) X509Helper
				.getAlgorithmOID(caCertificate.getSigAlgName());
		AlgorithmIdentifier sigAlgId = new AlgorithmIdentifier(sigOID,
				new DERNull());
		certGen.setSignature(sigAlgId);
		certGen.setSubjectPublicKeyInfo(new SubjectPublicKeyInfo(
				(ASN1Sequence) new ASN1InputStream(new ByteArrayInputStream(
						requestorPublicKey.getEncoded())).readObject()));
		certGen.setStartDate(new Time(new Date(System.currentTimeMillis())));
		certGen.setEndDate(new Time(clientCertificateExpiry.getTime()));

		// Basic constraints extension
		X509Extension basicConstraintsExtension = new X509Extension(false,
				new DEROctetString(new BasicConstraints(false)));

		// Authority key identifier extension
		X509Extension authorityKeyIndentifierExtension = new X509Extension(
				false, new DEROctetString(new AuthorityKeyIdentifierStructure(
						caCertificate)));

		// Subject key identifier
		X509Extension subjectKeyIdentifier = new X509Extension(false,
				new DEROctetString(new SubjectKeyIdentifierStructure(
						requestorPublicKey)));

		Hashtable<DERObjectIdentifier, X509Extension> extHashtable = new Hashtable<DERObjectIdentifier, X509Extension>();
		extHashtable.put(X509Extensions.AuthorityKeyIdentifier,
				authorityKeyIndentifierExtension);
		extHashtable.put(X509Extensions.BasicConstraints,
				basicConstraintsExtension);
		extHashtable.put(X509Extensions.SubjectKeyIdentifier,
				subjectKeyIdentifier);

		certGen.setExtensions(new X509Extensions(extHashtable));
		TBSCertificateStructure tbsCert = certGen.generateTBSCertificate();

		// SIGN
		SHA1Digest digester = new SHA1Digest();
		AsymmetricBlockCipher rsa = new PKCS1Encoding(new RSAEngine());
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		DEROutputStream dOut = new DEROutputStream(bOut);
		dOut.writeObject(tbsCert);
		byte[] signature;
		byte[] certBlock = bOut.toByteArray();
		// first create digest
		digester.update(certBlock, 0, certBlock.length);
		byte[] hash = new byte[digester.getDigestSize()];
		digester.doFinal(hash, 0);
		// and sign that
		rsa.init(true, caCertificatePrivateKey);
		DigestInfo dInfo = new DigestInfo(new AlgorithmIdentifier(
				X509ObjectIdentifiers.id_SHA1, null), hash);
		byte[] digest = dInfo.getEncoded(ASN1Encodable.DER);
		signature = rsa.processBlock(digest, 0, digest.length);

		// and finally construct the certificate structure
		ASN1EncodableVector v = new ASN1EncodableVector();
		v.add(tbsCert);
		v.add(sigAlgId);
		v.add(new DERBitString(signature));

		X509CertificateObject clientCert = new X509CertificateObject(
				new X509CertificateStructure(new DERSequence(v)));
		clientCert.verify(caCertificate.getPublicKey());

		signedCertificate = clientCert;

		return this;
	}

	public X509Certificate getSignedCertificate() {
		return signedCertificate;
	}

}
