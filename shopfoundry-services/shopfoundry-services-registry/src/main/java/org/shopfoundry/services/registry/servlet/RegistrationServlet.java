package org.shopfoundry.services.registry.servlet;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.registry.dto.Request;
import org.shopfoundry.core.service.registry.dto.Response;
import org.shopfoundry.services.registry.db.entity.ServiceGroup;
import org.shopfoundry.services.registry.db.entity.ServiceGroupConfiguration;
import org.shopfoundry.services.registry.db.repository.ServiceGroupRepository;
import org.shopfoundry.services.registry.gateway.outbound.CaServiceOutboundGateway;
import org.shopfoundry.services.registry.gateway.outbound.CertificateSubjectInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * AutoconfigServlet
 * 
 * @author Bojan Bijelic
 */
@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory
			.getLogger(RegistrationServlet.class);

	/**
	 * Service context
	 */
	private ServiceContext serviceContext;

	/**
	 * Constructor.
	 * 
	 * @param serviceContext
	 */
	public RegistrationServlet(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	/**
	 * Decodes public key from base64 into the PublicKey instance.
	 * 
	 * @param publicKey
	 * @return the public key object
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private PublicKey decodePublicKey(String publicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Requestor public key
		byte[] publicKeyByteArray = Base64.decodeBase64(publicKey);

		// Recreating public key from base64
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				publicKeyByteArray);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey servicePublicKey = keyFactory.generatePublic(publicKeySpec);
		return servicePublicKey;
	}

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (logger.isTraceEnabled())
			logger.trace(request.toString());

		// XML marshaller/unmarshaller
		XStream xstream = new XStream(new StaxDriver());
		xstream.processAnnotations(Request.class);

		// Unmarshal request
		Request registrationRequest = (Request) xstream.fromXML(request
				.getInputStream());

		// Initialize repository
		ServiceGroupRepository serviceGroupRepository = new ServiceGroupRepository();

		// Set status code
		response.setStatus(HttpServletResponse.SC_OK);

		// Initialize service registration response
		Response registrationResponse = new Response();

		try {

			// Try to find service configuration by service group name and
			// version
			ServiceGroup serviceGroupEntity = serviceGroupRepository
					.findByNameAndVersion(
							registrationRequest.getServiceGroup(),
							registrationRequest.getServiceVersion());

			// Generated service GUI
			String serviceGUID = UUID.randomUUID().toString();

			// Set service guid to the response
			registrationResponse.setServiceGiud(serviceGUID);

			// Service common name for the certificate
			String serviceCommonName = String.format("%s.%s", serviceGUID,
					registrationRequest.getServiceGroup());

			// Get public key
			PublicKey publicKey = decodePublicKey(registrationRequest
					.getCertificatePublicKey());

			// Certificate subject information
			CertificateSubjectInformation csi = new CertificateSubjectInformation();
			csi.setCommonName(serviceCommonName);
			csi.setOrganizationalUnit(registrationRequest.getServiceGroup());
			csi.setPublicKey(publicKey);

			// Get Certificate Authority outbound gateway
			CaServiceOutboundGateway caOutboundGateway = (CaServiceOutboundGateway) serviceContext
					.getGatewayProvider().getOutboundGateways()
					.get("CertificateAuthority");

			// Send certificate signing request to the Certificate Authority
			Certificate serviceCertificate = caOutboundGateway
					.requestCertificateSign(csi);

			String signedCertificateBase64 = Base64
					.encodeBase64String(serviceCertificate.getEncoded());

			// Set signed certificate to the response
			registrationResponse.setCertificate(signedCertificateBase64);

			if (logger.isInfoEnabled())
				logger.info(
						"Registering service from service group '{}' version '{}' with GUID '{}'",
						registrationRequest.getServiceGroup(),
						registrationRequest.getServiceVersion(), serviceGUID);

			// Set response content type to accepted by client
			response.setContentType("application/xml");

			// Get configurations
			List<ServiceGroupConfiguration> configurations = serviceGroupEntity
					.getServiceGroupConfiguration();

			// Service group configuration
			ServiceGroupConfiguration activeConfiguration;

			// Find active configuration by detemining biggest unix time
			if (!configurations.isEmpty()) {
				activeConfiguration = configurations.get(0);
				for (ServiceGroupConfiguration serviceGroupConfiguration : configurations) {
					if (serviceGroupConfiguration.getActiveFrom().getTime() > activeConfiguration
							.getActiveFrom().getTime()) {
						activeConfiguration = serviceGroupConfiguration;
					}
				}
			}

			// CA chain
			String caChain = serviceContext.getSecurityProvider()
					.getCertificateManager().exportTrustedCertificates();
			String caChainBase64 = Base64
					.encodeBase64String(caChain.getBytes());

			// Set CA chain to registration response
			registrationResponse.setCertificateAuthorityChain(caChainBase64);

			if (logger.isDebugEnabled())
				logger.debug(registrationResponse.toString());

		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			// Set status code
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

		} finally {

			// Allways return this values
			registrationResponse.setServiceGroup(registrationRequest
					.getServiceGroup());
			registrationResponse.setServiceVersion(registrationRequest
					.getServiceVersion());

			// Marshall to XML
			response.getWriter().print(xstream.toXML(registrationResponse));
		}

	}
}
