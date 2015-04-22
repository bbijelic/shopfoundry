package org.shopfoundry.core.service.fsm.state;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
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
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.shopfoundry.core.service.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceState;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mainly service bootstrapping tasks and environment validation.
 * 
 * @author Bojan Bijelic
 */
@SuppressWarnings("deprecation")
public class StartingState implements ServiceState {

	private static final Logger logger = LoggerFactory
			.getLogger(StartingState.class);

	@Override
	public void handle(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine) throws Exception {

		if (logger.isInfoEnabled())
			logger.info("Handling StartingState");

		// Setup certificate trust store
		setupCertificateTrustStore(serviceContext);

		// Register service
		registerService(serviceContext);

		// Generates key pair
		obtainClientCertificateFromCertificateAuthority(serviceContext);

		// Proceed to the next state
		serviceStateMachine.changeState(new ConfigurationState());
	}

	/**
	 * Obtains client certificate from certificate authority.
	 * 
	 * @param serviceContext
	 * @throws Exception
	 */
	private void obtainClientCertificateFromCertificateAuthority(
			ServiceContext serviceContext) throws Exception {

		// Generating key pair
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		if (logger.isInfoEnabled())
			logger.info("Generated key pair. Public key: {}", keyPair
					.getPublic().toString());

		// Sending over the network as base64
		String publicStringBase64 = new String(Base64.encodeBase64(keyPair
				.getPublic().getEncoded()));
		if (logger.isInfoEnabled())
			logger.info("Public key (Base64): {}", publicStringBase64);

		HttpPost httpPost = new HttpPost("http://dev.shopfoundry:9080/pki/csr");

		// Request headers
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

		// Service common name for the certificate
		String serviceCommonName = String.format("%s.%s",
				serviceContext.getServiceGUID(),
				serviceContext.getServiceGroup());

		// Parameters
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("CommonName", serviceCommonName));
		parameters.add(new BasicNameValuePair("OrganizationalUnit", "Service"));
		parameters.add(new BasicNameValuePair("PublicKey", publicStringBase64));
		httpPost.setEntity(new UrlEncodedFormEntity(parameters));

		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse httpResponse)
					throws ClientProtocolException, IOException {

				StatusLine statusLine = httpResponse.getStatusLine();
				if (statusLine.getStatusCode() != 200)
					throw new ClientProtocolException(
							"Was unable to send certificate signing request. Unexpected response status: "
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
		X509Certificate certificate = (X509Certificate) certFactory
				.generateCertificate(new ByteArrayInputStream(
						cerEncodedResponse));

		if (logger.isInfoEnabled())
			logger.info(
					"Client certificate successsully obtained. Serial number: {}",
					certificate.getSerialNumber().toString());

		// Certificate chain for the key store
		X509Certificate[] certificateChain = { certificate };

		// Add certificate and private key to the client certificates store
		serviceContext
				.getSecurityManager()
				.getCertificateManager()
				.getEndEntityCertificates()
				.setKeyEntry(certificate.getSerialNumber().toString(),
						keyPair.getPrivate(),
						serviceContext.getServiceGUID().toCharArray(),
						certificateChain);
	}

	/**
	 * Setups certificate trust store
	 * 
	 * @param serviceContext
	 * @throws Exception
	 */
	private void setupCertificateTrustStore(ServiceContext serviceContext)
			throws Exception {

		if (logger.isInfoEnabled())
			logger.info("Obtaining CA certificate from PKI repository");

		HttpGet httpGet = new HttpGet("http://localhost:9080/pki/repository/ca");
		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse httpResponse)
					throws ClientProtocolException, IOException {

				StatusLine statusLine = httpResponse.getStatusLine();
				if (statusLine.getStatusCode() != 200)
					throw new ClientProtocolException(
							"Was unable to obtain CA certificate. Unexpected response status: "
									+ statusLine.toString());

				HttpEntity entity = httpResponse.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			}

		};

		CloseableHttpClient httpclient = HttpClients.createDefault();
		// Execute GET operation
		String caString = httpclient.execute(httpGet, responseHandler);
		InputStream caCertificateInputStream = new ByteArrayInputStream(
				caString.getBytes());

		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X509");
		@SuppressWarnings("unchecked")
		List<X509Certificate> certificateChain = (List<X509Certificate>) certificateFactory
				.generateCertificates(caCertificateInputStream);

		if (logger.isInfoEnabled())
			logger.info("CA certificate chain obtained. Size: {}",
					certificateChain.size());

		// Add certificates to the trust store
		for (X509Certificate certificate : certificateChain) {

			if (logger.isInfoEnabled())
				logger.info("Adding certificate to the trust store: {}",
						certificate.getSubjectDN().toString());

			serviceContext
					.getSecurityManager()
					.getCertificateManager()
					.getTrustedCerticiates()
					.setCertificateEntry(
							certificate.getSerialNumber().toString(),
							certificate);
		}

		if (logger.isInfoEnabled())
			logger.info(
					"CA certificate added to the trust store. Trust key store size: {}",
					serviceContext.getSecurityManager().getCertificateManager()
							.getTrustedCerticiates().size());
	}

	/**
	 * Registers service on registry service and obtains service GUID.
	 * 
	 * @param serviceContext
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 */
	private void registerService(ServiceContext serviceContext)
			throws ClientProtocolException, IOException,
			KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, CertificateException {
		if (logger.isInfoEnabled())
			logger.info("Registering service");

		HttpPost httpPost = new HttpPost(
				serviceContext.getRegistryServiceEndpoint());

		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("Action", "register"));
		parameters.add(new BasicNameValuePair("ServiceGroup", serviceContext
				.getServiceGroup()));
		parameters.add(new BasicNameValuePair("ServiceVersion", "1.0.0.0"));
		httpPost.setEntity(new UrlEncodedFormEntity(parameters));

		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse httpResponse)
					throws ClientProtocolException, IOException {

				StatusLine statusLine = httpResponse.getStatusLine();
				if (statusLine.getStatusCode() != 200)
					throw new ClientProtocolException(
							"Was unable to register service. Unexpected response status: "
									+ statusLine.toString());

				HttpEntity entity = httpResponse.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			}

		};

		// Creates client which ignores certificate warnings and trusts all
		// certificates
		CloseableHttpClient httpclient = HttpClients
				.custom()
				.setSSLHostnameVerifier(new AllowAllHostnameVerifier())
				.setSslcontext(
						new SSLContextBuilder().loadTrustMaterial(
								serviceContext.getSecurityManager()
										.getCertificateManager()
										.getTrustedCerticiates(),
								new TrustStrategy() {
									public boolean isTrusted(
											X509Certificate[] certificateChain,
											String arg1)
											throws CertificateException {
										// Trust only certificates from trust
										// store
										return false;
									}
								}).build()).build();

		// Execute POST operation
		String response = httpclient.execute(httpPost, responseHandler);

		if (logger.isDebugEnabled())
			logger.debug(response);

		JSONObject jsonObject = new JSONObject(response);
		String serviceGuid = jsonObject.getString("ServiceGUID");

		if (logger.isInfoEnabled())
			logger.info("Service GUID successfully obtained: {}", serviceGuid);

		// Set service GUID to context
		serviceContext.setServiceGUID(serviceGuid);
	}

	@Override
	public String getStateName() {
		return this.getClass().getSimpleName();
	}

}
