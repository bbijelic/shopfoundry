package org.shopfoundry.services.pki.ca.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.shopfoundry.core.security.pki.CertificateAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;
import sun.security.provider.X509Factory;

/**
 * PKI CA Repository servlet
 * 
 * @author Bojan Bijelic
 */
@SuppressWarnings("restriction")
public class RepositoryServlet extends HttpServlet {

	private static final long serialVersionUID = -2534103454706857013L;

	private static final Logger logger = LoggerFactory
			.getLogger(RepositoryServlet.class);

	private String rootCaCertificateFilePath;
	private String rootCaCertificateKeyFilePath;
	private String intermediateCaCertificateFilePath;
	private String intermediateCaCertificateKeyFilePath;

	public RepositoryServlet(String rootCaCertificateFilePath,
			String rootCaCertificateKeyFilePath,
			String intermediateCaCertificateFilePath,
			String intermediateCaCertificateKeyFilePath) {
		super();

		this.rootCaCertificateFilePath = rootCaCertificateFilePath;
		this.rootCaCertificateKeyFilePath = rootCaCertificateKeyFilePath;
		this.intermediateCaCertificateFilePath = intermediateCaCertificateFilePath;
		this.intermediateCaCertificateKeyFilePath = intermediateCaCertificateKeyFilePath;
	}

	/**
	 * GET
	 * 
	 * Returns CA certificate
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Set content type to x509 CA certificate
		response.setContentType("application/x-x509-ca-cert");

		// Get certificate authority
		try {

			// Root CA certificate
			File rootCaCertificateFile = new File(
					this.rootCaCertificateFilePath);
			// Root CA private key
			File rootCaPrivateKeyFile = new File(
					this.rootCaCertificateKeyFilePath);

			// Intermediate CA certificate
			File intermediateCaCertificateFile = new File(
					this.intermediateCaCertificateFilePath);
			// Intermediate CA private key
			File intermediateCaPrivateKeyFile = new File(
					this.intermediateCaCertificateKeyFilePath);

			// Initialize certificate authority
			CertificateAuthority ca = CertificateAuthority
					.getInstance(rootCaCertificateFile, rootCaPrivateKeyFile,
							intermediateCaCertificateFile,
							intermediateCaPrivateKeyFile);

			X509Certificate caCertificate = ca.getCACertificate();
			X509Certificate rootCaCertificate = ca.getRootCACertificate();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			BASE64Encoder caEncoder = new BASE64Encoder();
			response.getWriter().println(X509Factory.BEGIN_CERT);
			caEncoder.encodeBuffer(caCertificate.getEncoded(), bos);
			response.getWriter().write(new String(bos.toByteArray()));
			response.getWriter().println(X509Factory.END_CERT);
			bos.reset();

			BASE64Encoder rootCaEncoder = new BASE64Encoder();
			response.getWriter().println(X509Factory.BEGIN_CERT);
			rootCaEncoder.encodeBuffer(rootCaCertificate.getEncoded(), bos);
			response.getWriter().write(new String(bos.toByteArray()));
			response.getWriter().println(X509Factory.END_CERT);

		} catch (Exception e) {

			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);
		}
	}

}
