/**
 * 
 */
package org.shopfoundry.services.registry.gateway.outbound;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.shopfoundry.core.security.SecurityProvider;
import org.shopfoundry.core.service.gateway.outbound.OutboundGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Certificate Authority (CA) outbound service gateway.
 * 
 * @author Bojan Bijelic
 *
 */
public class DefaultCaServiceOutboundGateway implements
		CaServiceOutboundGateway {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultCaServiceOutboundGateway.class);

	private String certificateChainUrl;
	private String certificateSigningUrl;
	private String certificateRevocationUrl;
	private String certificateRevocationListUrl;

	@SuppressWarnings("unused")
	private SecurityProvider securityManager;

	/**
	 * Constructor.
	 * 
	 * @param certificateChainUrl
	 * @param certificateSigningUrl
	 * @param certificateRevocationUrl
	 * @param certificateRevocationListUrl
	 */
	public DefaultCaServiceOutboundGateway(SecurityProvider securityManager,
			String certificateChainUrl, String certificateSigningUrl,
			String certificateRevocationUrl, String certificateRevocationListUrl) {

		this.securityManager = securityManager;
		this.certificateChainUrl = certificateChainUrl;
		this.certificateSigningUrl = certificateSigningUrl;
		this.certificateRevocationUrl = certificateRevocationUrl;
		this.certificateRevocationListUrl = certificateRevocationListUrl;
	}

	@Override
	public void start() throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("Startin {}", this.getClass().getCanonicalName());
			logger.info("Certificate Authority Chain URL: {}",
					this.certificateChainUrl);
			logger.info("Certificate Authoirty certificate signing URL: {}",
					this.certificateSigningUrl);
			logger.info("Certificate Authority certificate revocation URL: {}",
					this.certificateRevocationUrl);
			logger.info(
					"Certificate Authority certificate revocation list URL: {}",
					this.certificateRevocationListUrl);
		}
	}

	@Override
	public void stop() {
		if (logger.isInfoEnabled())
			logger.info("Stopping {}", this.getClass().getCanonicalName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<X509Certificate> pullCertificateChain()
			throws ClientProtocolException, IOException, CertificateException {
		// Initialize certificate chain list
		List<X509Certificate> certificateChain;

		if (logger.isInfoEnabled())
			logger.info("Pulling Certificate Authority certificate chain");

		// Initialize http GET method
		HttpGet httpGet = new HttpGet(this.certificateChainUrl);
		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse httpResponse)
					throws ClientProtocolException, IOException {

				StatusLine statusLine = httpResponse.getStatusLine();
				if (statusLine.getStatusCode() != 200)
					throw new ClientProtocolException(
							"Was unable to pull Certificate Authority chain certificate. Unexpected response status: "
									+ statusLine.toString());

				HttpEntity entity = httpResponse.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			}

		};

		CloseableHttpClient httpclient = HttpClients.createDefault();
		// Execute GET operation and get certificate chain in PEM format
		String certificateChainPEM = httpclient.execute(httpGet,
				responseHandler);

		// Initialize input stream
		InputStream caCertificateInputStream = new ByteArrayInputStream(
				certificateChainPEM.getBytes());

		// Initialize certificate factory
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X509");

		certificateChain = (List<X509Certificate>) certificateFactory
				.generateCertificates(caCertificateInputStream);

		// Close input stream
		caCertificateInputStream.close();

		if (logger.isTraceEnabled())
			logger.trace("Certificate chain: {}", certificateChain.toString());

		// Return chain
		return certificateChain;
	}

	@Override
	public X509Certificate requestCertificateSign(
			CertificateSubjectInformation certificateSubjectInformation)
			throws ClientProtocolException, IOException, CertificateException {

		// Initialize signed certificate
		X509Certificate signedCertificate = null;

		if (logger.isTraceEnabled())
			logger.trace("Certificate signing request for: {}",
					certificateSubjectInformation.toString());

		// Sending over the network as base64
		String publicStringBase64 = new String(
				Base64.encodeBase64(certificateSubjectInformation
						.getPublicKey().getEncoded()));

		if (logger.isDebugEnabled())
			logger.debug("Public key (Base64): {}", publicStringBase64);

		// Request parameters
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("CommonName",
				certificateSubjectInformation.getCommonName()));
		int ouIncrement = 1;
		parameters.add(new BasicNameValuePair("OrganizationalUnit"
				+ ouIncrement, certificateSubjectInformation
				.getOrganizationalUnit()));
		parameters.add(new BasicNameValuePair("PublicKey", publicStringBase64));

		// Initialize http POST method
		HttpPost httpPost = new HttpPost(this.certificateSigningUrl);
		httpPost.setEntity(new UrlEncodedFormEntity(parameters));

		// Request headers
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse httpResponse)
					throws ClientProtocolException, IOException {

				StatusLine statusLine = httpResponse.getStatusLine();
				if (statusLine.getStatusCode() != 200)
					throw new ClientProtocolException(
							"Was unable to send certificate signing request to Certificate Authority. Unexpected response status: "
									+ statusLine.toString());

				HttpEntity entity = httpResponse.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			}

		};

		// Instance of http client
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// Execute operation
		String cerEncodedBase64Response = httpclient.execute(httpPost,
				responseHandler);

		// Base64 decode response
		byte[] cerEncodedResponse = Base64
				.decodeBase64(cerEncodedBase64Response);

		// Get X509 certificate from sream of bytes
		CertificateFactory certFactory = CertificateFactory
				.getInstance("X.509");
		signedCertificate = (X509Certificate) certFactory
				.generateCertificate(new ByteArrayInputStream(
						cerEncodedResponse));

		// TODO Validate certificate against CA certificates in trust store.

		if (logger.isInfoEnabled())
			logger.info("Certificate successsully signed. Serial number: {}",
					signedCertificate.getSerialNumber().toString());

		// Return signed certificate
		return signedCertificate;
	}

	@Override
	public void requestCertificateRevocation(X509Certificate certificate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configure() throws OutboundGatewayException {
		// TODO Auto-generated method stub
		
	}

}
