package org.shopfoundry.services.pki.ca.servlet;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.PrincipalUtil;
import org.bouncycastle.jce.X509Principal;
import org.shopfoundry.core.security.pki.CertificateAuthority;
import org.shopfoundry.core.security.pki.CertificateSigningRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Certificate request signing servlet.
 * 
 * @author Bojan Bijelic
 */
public class CertificateRequestSigningServlet extends HttpServlet {

	private static final long serialVersionUID = -2113253843521456355L;

	private static final Logger logger = LoggerFactory
			.getLogger(CertificateRequestSigningServlet.class);

	private static final String PARAM_COMMON_NAME = "CommonName";
	private static final String PARAM_ORGANIZATION = "Organization";
	private static final String PARAM_ORGANIZATIONAL_UNIT = "OrganizationalUnit";
	private static final String PARAM_LOCALITY = "Locality";
	private static final String PARAM_STATE = "State";
	private static final String PARAM_COUNTRY = "Country";
	private static final String PARAM_EMAIL = "Email";
	private static final String PARAM_PUBLIC_KEY = "PublicKey";

	private String rootCaCertificateFilePath;
	private String rootCaCertificateKeyFilePath;
	private String intermediateCaCertificateFilePath;
	private String intermediateCaCertificateKeyFilePath;

	public CertificateRequestSigningServlet(String rootCaCertificateFilePath,
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
	 * Post
	 * 
	 * Returns CA certificate
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (logger.isTraceEnabled())
			logger.trace(request.toString());

		// Required request parameters
		List<String> requiredRequestParameters = new ArrayList<String>();
		requiredRequestParameters.add(PARAM_COMMON_NAME);
		requiredRequestParameters.add(PARAM_PUBLIC_KEY);

		if (validateRequest(requiredRequestParameters, request)) {
			// Request is valid

			try {
				// Get certificate authority
				CertificateAuthority ca = CertificateAuthority.getInstance(
						rootCaCertificateFilePath,
						rootCaCertificateKeyFilePath,
						intermediateCaCertificateFilePath,
						intermediateCaCertificateKeyFilePath);

				// CA Certificate
				X509Principal principal = PrincipalUtil
						.getSubjectX509Principal(ca.getCACertificate());
				String caOrganization = (String) principal
						.getValues(X509Name.O).get(0);
				String caOrganizationalUnit = (String) principal.getValues(
						X509Name.OU).get(0);
				String caLocality = (String) principal.getValues(X509Name.L)
						.get(0);
				String caState = (String) principal.getValues(X509Name.ST).get(
						0);
				String caCountry = (String) principal.getValues(X509Name.C)
						.get(0);

				// Common name
				String commonName = request.getParameter(PARAM_COMMON_NAME);

				// Organization
				String organization = caOrganization;
				if (request.getParameterMap().containsKey(PARAM_ORGANIZATION))
					organization = request.getParameter(PARAM_ORGANIZATION);

				// Organizational unit
				String organizationalUnit = caOrganizationalUnit;
				if (request.getParameterMap().containsKey(
						PARAM_ORGANIZATIONAL_UNIT))
					organizationalUnit = request
							.getParameter(PARAM_ORGANIZATIONAL_UNIT);

				// Locality
				String locality = caLocality;
				if (request.getParameterMap().containsKey(PARAM_LOCALITY))
					locality = request.getParameter(PARAM_LOCALITY);

				// State
				String state = caState;
				if (request.getParameterMap().containsKey(PARAM_STATE))
					state = request.getParameter(PARAM_STATE);

				// Country
				String country = caCountry;
				if (request.getParameterMap().containsKey(PARAM_COUNTRY))
					country = request.getParameter(PARAM_COUNTRY);

				// Email
				String email = null;
				if (request.getParameterMap().containsKey(PARAM_EMAIL))
					email = request.getParameter(PARAM_EMAIL);

				// Certificate signing request
				CertificateSigningRequest certificateSigningRequest = new CertificateSigningRequest(
						commonName, email, organizationalUnit, organization,
						locality, state, country);

				// Requestor public key
				byte[] publicKeyByteArray = Base64.decodeBase64(request
						.getParameter(PARAM_PUBLIC_KEY));

				// Recreating public key from base64
				X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
						publicKeyByteArray);

				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				PublicKey requestorPublicKey = keyFactory
						.generatePublic(publicKeySpec);

				ca.generateAndSignCertificate(certificateSigningRequest, 365,
						requestorPublicKey);

				// Get signed certificate
				X509Certificate signedCertificate = ca.getSignedCertificate();

				String base64Certificate = new String(
						Base64.encodeBase64(signedCertificate.getEncoded()));

				// Print certificate to the response
				response.getWriter().print(base64Certificate);

			} catch (Exception e) {

				if (logger.isErrorEnabled())
					logger.error(e.getMessage(), e);

				// Set proper response status
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

		} else {
			// Request is invalid
			if (logger.isErrorEnabled())
				logger.error("Request is invalid");
			// Set proper response status
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

	/**
	 * Validates request.
	 * 
	 * @param requiredRequestParameters
	 * @param request
	 * @return true if request is valid, false otherwise
	 */
	private boolean validateRequest(List<String> requiredRequestParameters,
			HttpServletRequest request) {

		// Validate request parameters
		List<String> requestParameters = Collections.list(request
				.getParameterNames());

		for (String requiredParameter : requiredRequestParameters) {
			if (!requestParameters.contains(requiredParameter)) {
				if (logger.isErrorEnabled())
					logger.error(
							"Request parameter '{}' is required but is not contained within request",
							requiredParameter);

				return false;
			}
		}

		return true;
	}
}
