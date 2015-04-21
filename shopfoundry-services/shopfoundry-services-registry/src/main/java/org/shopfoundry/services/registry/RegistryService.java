package org.shopfoundry.services.registry;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.List;

import org.shopfoundry.core.security.SecurityManager;
import org.shopfoundry.core.security.pki.rsa.RSAKeyPairGenerator;
import org.shopfoundry.core.service.gateway.InboundGateway;
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

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Load application context from classpath
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
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
	 */
	public RegistryService(List<InboundGateway> inboundGateways,
			SecurityManager securityManager,
			CaServiceOutboundGateway caServiceOutboundGateway,
			GuidProvider guidProvider) {

		try {

			// Start ca service outbound service
			caServiceOutboundGateway.start();

			// Pull CA certificate chain from Certificate Authority
			List<X509Certificate> certificateChain = caServiceOutboundGateway
					.pullCertificateChain();

			// Add certificate chain to the trust store
			for (X509Certificate x509Certificate : certificateChain) {

				if (logger.isInfoEnabled())
					logger.info("Adding certificate to the trust store: {}",
							x509Certificate.getSubjectDN().toString());

				securityManager
						.getCertificateManager()
						.getTrustedCerticiates()
						.setCertificateEntry(
								x509Certificate.getSubjectDN().toString(),
								x509Certificate);
			}

			// Generate key pair for the service certificate
			RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGenerator();
			KeyPair keyPair = rsaKeyPairGenerator.generateKey(2048);

			// Certificate subject information
			CertificateSubjectInformation csi = new CertificateSubjectInformation();
			csi.setCommonName(guidProvider.getGUID() + "@RegistryService");
			csi.setOrganizationalUnit("RegistryService");
			csi.setPublicKey(keyPair.getPublic());

			// Send certificate signing request to the Certificate Authority
			X509Certificate serviceCertificate = caServiceOutboundGateway
					.requestCertificateSign(csi);

			if (logger.isTraceEnabled())
				logger.trace("Service certificate: {}",
						serviceCertificate.toString());

			// Loop all inbound gateways and start
			for (InboundGateway inboundGateway : inboundGateways) {
				inboundGateway.start();
			}

		} catch (Exception e) {

			if (logger.isErrorEnabled())
				logger.error(e.toString(), e);

			if (logger.isInfoEnabled())
				logger.info("Shutting down service");

			// Kill application on error
			System.exit(0);
		}
	}

}
