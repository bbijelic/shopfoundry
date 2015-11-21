package org.shopfoundry.core.service.gateway.outbound.registry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.shopfoundry.core.security.SecurityProvider;
import org.shopfoundry.core.security.SecurityProviderException;
import org.shopfoundry.core.security.certificates.CertificateManagerException;
import org.shopfoundry.core.security.ssl.PermissiveTrustManager;
import org.shopfoundry.core.service.config.ConfigurationProvider;
import org.shopfoundry.core.service.info.ServiceInfoProvider;
import org.shopfoundry.core.service.info.ServiceInfoProviderException;
import org.shopfoundry.core.service.registry.dto.Request;
import org.shopfoundry.core.service.registry.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

/**
 * Default implementation of Registry outbound gateway.
 *
 * @author Bojan Bijelic
 */
public class DefaultRegistryOutboundGateway implements RegistryOutboundGateway {

	/**
	 * Logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(DefaultRegistryOutboundGateway.class);

	/**
	 * Service context.
	 */
	private ServiceInfoProvider serviceInfoProvider;

	/**
	 * Security manager.
	 */
	private SecurityProvider securityProvider;

	/**
	 * Configuration provider.
	 */
	private ConfigurationProvider configurationProvider;

	/**
	 * Registration URL
	 */
	private String registrationUrl;

	/**
	 * Constructor.
	 * 
	 * @param serviceInfoProvider
	 * @param registrationUrl
	 */
	public DefaultRegistryOutboundGateway(ConfigurationProvider configurationProvider,
			ServiceInfoProvider serviceInfoProvider, SecurityProvider securityProvider, String registrationUrl) {
		this.configurationProvider = configurationProvider;
		this.serviceInfoProvider = serviceInfoProvider;
		this.securityProvider = securityProvider;
		this.registrationUrl = registrationUrl;
	}

	@Override
	public void start() throws Exception {
		if (logger.isInfoEnabled())
			logger.info("Starting {} gateway", getClass().getSimpleName());

	}

	@Override
	public void stop() {
		if (logger.isInfoEnabled())
			logger.info("Stopping {} gateway", getClass().getSimpleName());
	}

	@Override
	public void register() throws RegistryOutboundGatewayException {

		// Initialize client
		CloseableHttpClient httpsClient = null;

		// Serializer/Deserializer
		XStream xStream = new XStream();

		try {

			// Generate key pair
			KeyPair keyPair = this.generateKeyPair();

			// Form registration request
			Request registrationRequest = new Request();
			registrationRequest.setServiceGroup(serviceInfoProvider.getServiceGroup());
			registrationRequest.setServiceVersion(serviceInfoProvider.getServiceVersion());
			registrationRequest
					.setCertificatePublicKey(new String(Base64.encodeBase64(keyPair.getPublic().getEncoded())));

			// Serialize to XML
			HttpEntity entity = new ByteArrayEntity(xStream.toXML(registrationRequest).getBytes("UTF-8"));

			// Initialize request method
			HttpPost httpPost = new HttpPost(this.registrationUrl);
			httpPost.setHeader("Content-Type", "application/xml");
			httpPost.setEntity(entity);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {

					StatusLine statusLine = httpResponse.getStatusLine();
					if (statusLine.getStatusCode() != 200)
						throw new ClientProtocolException(
								"Was unable to send registration request. Unexpected response status: "
										+ statusLine.toString());

					// Return XML body
					return IOUtils.toString(httpResponse.getEntity().getContent(), "UTF-8");
				}

			};

			// Trust managers
			TrustManager[] trustManagers = new TrustManager[] { new PermissiveTrustManager() };

			// Trust all certificates
			final SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(null, trustManagers, new SecureRandom());

			// Allow TLSv1 protocol only
			final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
					new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

			httpsClient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();

			// Execute operation
			String responseXml = httpsClient.execute(httpPost, responseHandler);

			// Unmarshall
			Response response = (Response) xStream.fromXML(responseXml);

			// Set service GUID returned by registry service
			this.serviceInfoProvider.setServiceGuid(response.getServiceGiud());

			// Set configuration obtained from the registry service
			this.configurationProvider.importConfiguration(response.getServiceConfiguration());

			// Get end-entity certificate
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream certificateInputStream = new ByteArrayInputStream(
					Base64.decodeBase64(response.getCertificate()));
			X509Certificate endEntityCertificate = (X509Certificate) certFactory
					.generateCertificate(certificateInputStream);
			Certificate[] certificate = { endEntityCertificate };

			if (logger.isTraceEnabled())
				logger.trace("End-Entity certificate: {}", certificate.toString());

			// Import end entity certificate to the key store
			this.securityProvider.getCertificateManager().getEndEntityCertificates().setKeyEntry(
					this.serviceInfoProvider.getServiceGuid(), keyPair.getPrivate(),
					this.serviceInfoProvider.getServiceGuid().toCharArray(), certificate);

		} catch (ServiceInfoProviderException | NoSuchAlgorithmException | KeyManagementException | IOException
				| CertificateException | KeyStoreException | CertificateManagerException
				| SecurityProviderException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			throw new RegistryOutboundGatewayException(e.getMessage(), e);
		} finally {
			try {

				if (httpsClient != null) {
					// Close connection
					httpsClient.close();
				}
			} catch (IOException e) {
				if (logger.isErrorEnabled())
					logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Generates and returns key pair.
	 * 
	 * @return the RSA key pair
	 * @throws NoSuchAlgorithmException
	 */
	private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		// Generating key pair
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		if (logger.isInfoEnabled())
			logger.info("Generated key pair. Public key: {}", keyPair.getPublic().toString());
		return keyPair;
	}

}
