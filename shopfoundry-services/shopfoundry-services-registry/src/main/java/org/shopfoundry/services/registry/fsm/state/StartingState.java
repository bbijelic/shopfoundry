package org.shopfoundry.services.registry.fsm.state;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import org.shopfoundry.core.security.pki.rsa.RSAKeyPairGenerator;
import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.shopfoundry.core.service.fsm.state.AllowedState;
import org.shopfoundry.core.service.fsm.state.ServiceState;
import org.shopfoundry.core.service.fsm.state.ServiceStateException;
import org.shopfoundry.services.registry.gateway.outbound.CaServiceOutboundGateway;
import org.shopfoundry.services.registry.gateway.outbound.CertificateSubjectInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry service starting state.
 *
 * @author Bojan Bijelic
 */
public class StartingState implements ServiceState {

	private static final Logger logger = LoggerFactory
			.getLogger(StartingState.class);

	@Override
	public AllowedState getState() {
		return AllowedState.STARTING;
	}

	@Override
	public void handle(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine)
			throws ServiceStateException {

		try {

			// Get Certificate Authority outbound gateway
			CaServiceOutboundGateway caOutboundGateway = (CaServiceOutboundGateway) serviceContext
					.getGatewayProvider().getOutboundGateways()
					.get("CertificateAuthority");
			// Start Certificate Authority outbound gateway
			caOutboundGateway.start();

			// Pull CA certificate chain from Certificate Authority
			List<X509Certificate> certificateChain = caOutboundGateway
					.pullCertificateChain();

			// Import trusted certificates
			serviceContext.getSecurityProvider().getCertificateManager()
					.importTrustedCertificates(certificateChain);

			// Generate key pair for the service certificate
			KeyPair keyPair = RSAKeyPairGenerator.generateKey(2048);

			// Certificate subject information
			CertificateSubjectInformation csi = new CertificateSubjectInformation();
			csi.setCommonName(serviceContext.getEnvironmentProvider()
					.getHostname());
			csi.setOrganizationalUnit(serviceContext.getServiceInfoProvider()
					.getServiceGroup());
			csi.setPublicKey(keyPair.getPublic());

			// Send certificate signing request to the Certificate Authority
			Certificate serviceCertificate = caOutboundGateway
					.requestCertificateSign(csi);
			Certificate[] certificates = { serviceCertificate };

			// Import end entity certificate into the key store
			serviceContext
					.getSecurityProvider()
					.getCertificateManager()
					.getEndEntityCertificates()
					.setKeyEntry(
							csi.getCommonName(),
							keyPair.getPrivate(),
							serviceContext.getServiceInfoProvider()
									.getServiceGroup().toCharArray(),
							certificates);

			if (logger.isInfoEnabled()) {
				logger.info("Trusted certificates key store size: {}",
						serviceContext.getSecurityProvider()
								.getCertificateManager()
								.getTrustedCerticiates().size());
				logger.info("End entity certificate key store size: {}",
						serviceContext.getSecurityProvider()
								.getCertificateManager()
								.getEndEntityCertificates().size());
			}

			// Proceed to the Configuration state
			serviceStateMachine.changeState(AllowedState.CONFIGURING);

		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			throw new ServiceStateException(e.getMessage(), e);
		}
	}

}
