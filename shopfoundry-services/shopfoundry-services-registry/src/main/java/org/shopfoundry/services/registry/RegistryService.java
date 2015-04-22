package org.shopfoundry.services.registry;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import org.shopfoundry.core.security.SecurityManager;
import org.shopfoundry.core.security.pki.rsa.RSAKeyPairGenerator;
import org.shopfoundry.core.service.gateway.InboundGateway;
import org.shopfoundry.core.service.system.SystemSpecification;
import org.shopfoundry.core.utils.GuidProvider;
import org.shopfoundry.services.registry.outbound.ca.CaServiceOutboundGateway;
import org.shopfoundry.services.registry.outbound.ca.CertificateSubjectInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Registry service.
 * 
 * @author Bojan Bijelic
 */
public class RegistryService {

	/**
	 * Logger
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(RegistryService.class);

	private static ApplicationContext applicationContext;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Load application context from classpath
		applicationContext = new ClassPathXmlApplicationContext(
				"ServiceConfiguration.xml");

		// Make instance and start email dispatcher service
		RegistryService service = (RegistryService) applicationContext
				.getBean("Service");
	}

	/**
	 * Constructor.
	 * 
	 * @param inboundGateways
	 * @param securityManager
	 * @param caServiceOutboundGateway
	 * @param guidProvider
	 * @param systemSpecification
	 */
	public RegistryService(List<InboundGateway> inboundGateways,
			SecurityManager securityManager,
			CaServiceOutboundGateway caServiceOutboundGateway,
			GuidProvider guidProvider, SystemSpecification systemSpecification) {

		try {

			// Start ca service outbound service
			caServiceOutboundGateway.start();

			// Pull CA certificate chain from Certificate Authority
			List<X509Certificate> certificateChain = caServiceOutboundGateway
					.pullCertificateChain();

			// Import trusted certificates
			securityManager.getCertificateManager().importTrustedCertificates(
					certificateChain);

			// Generate key pair for the service certificate
			KeyPair keyPair = RSAKeyPairGenerator.generateKey(2048);

			// Certificate subject information
			CertificateSubjectInformation csi = new CertificateSubjectInformation();
			csi.setCommonName(systemSpecification.getHostname());
			csi.setOrganizationalUnit(getClass().getSimpleName());
			csi.setPublicKey(keyPair.getPublic());

			// Send certificate signing request to the Certificate Authority
			Certificate serviceCertificate = caServiceOutboundGateway
					.requestCertificateSign(csi);

			Certificate[] certificates = { serviceCertificate };

			// Import serviceCertificate and corresponding private key to the
			// key store
			securityManager
					.getCertificateManager()
					.getEndEntityCertificates()
					.setKeyEntry("1", keyPair.getPrivate(), "".toCharArray(),
							certificates);

			if (logger.isTraceEnabled())
				logger.trace("Service certificate: {}",
						serviceCertificate.toString());

			// Loop all inbound gateways and start
			for (InboundGateway inboundGateway : inboundGateways) {
				inboundGateway.start();
			}

		} catch (Exception e) {

			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			if (logger.isInfoEnabled())
				logger.info("Shutting down service");

			// Kill application on error
			System.exit(0);
		}
	}

}
