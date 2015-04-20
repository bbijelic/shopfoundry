package org.shopfoundry.core.service;

import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.shopfoundry.core.service.fsm.StatefulService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Default service.
 * 
 * @author Bojan Bijelic
 */
public class DefaultService implements Service, ContextualService,
		StatefulService {

	/**
	 * Logger
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(DefaultService.class);

	/**
	 * Main method.
	 * 
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		// Load application context from classpath
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"ServiceConfiguration.xml");

		// Make instance and start email dispatcher service
		Service service = (Service) applicationContext.getBean("Service");

		try {
			// Start service
			service.start();

		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error("Servic failed to strart: " + e.getMessage(), e);

		}

	}

	/**
	 * Service context.
	 */
	private ServiceContext serviceContext;

	/**
	 * Service context setter
	 * 
	 * @param serviceContext
	 *            the serviceContext to set
	 */
	public void setServiceContext(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	@Override
	public ServiceContext getServiceContext() {
		return serviceContext;
	}

	@Override
	public void start() throws Exception {
		if (logger.isInfoEnabled())
			logger.info("Starting service [ Service group: {} ]",
					serviceContext.getServiceGroup());

		// Start state machine
		getServiceStateMachine().changeState(
				getServiceStateMachine().getDefaultState());
	}

	/**
	 * Service state machine
	 */
	private ServiceStateMachine serviceStateMachine;

	/**
	 * Service state machine setter.
	 * 
	 * @param serviceStateMachine
	 *            the serviceStateMachine to set
	 */
	public void setServiceStateMachine(ServiceStateMachine serviceStateMachine) {
		this.serviceStateMachine = serviceStateMachine;
	}

	@Override
	public ServiceStateMachine getServiceStateMachine() {
		return serviceStateMachine;
	}

}
