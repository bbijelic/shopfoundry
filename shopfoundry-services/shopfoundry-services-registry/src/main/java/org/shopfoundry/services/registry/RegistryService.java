package org.shopfoundry.services.registry;

import org.shopfoundry.core.service.gateway.InboundGateway;
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
	 * Constructor
	 */
	public RegistryService(InboundGateway httpInboundGateway) {

		try {
			httpInboundGateway.start();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

}
