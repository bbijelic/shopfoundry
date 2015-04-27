package org.shopfoundry.services.pki.ca;

import org.shopfoundry.core.service.gateway.inbound.InboundGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * PKI CA service.
 * 
 * @author Bojan Bijelic
 */
public class CAService {

	/**
	 * Logger
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(CAService.class);

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Load application context from classpath
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"ServiceConfiguration.xml");

		// Make instance and service
		CAService service = (CAService) applicationContext.getBean("Service");
	}

	/**
	 * Constructor
	 */
	public CAService(InboundGateway httpInboundGateway) {

		try {
			httpInboundGateway.start();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

}
