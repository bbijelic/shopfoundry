package org.shopfoundry.core.service;

import org.shopfoundry.core.service.context.ContextualService;
import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.context.ServiceContextException;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;
import org.shopfoundry.core.service.fsm.ServiceStateMachineException;
import org.shopfoundry.core.service.fsm.StatefulService;
import org.shopfoundry.core.service.fsm.state.AllowedState;
import org.shopfoundry.core.service.info.ServiceInfoProviderException;
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
	 * Service configuration XML file. Must be in classpath.
	 */
	private final static String ServiceConfigurationXml = "ServiceConfiguration.xml";

	/**
	 * Service bean name.
	 */
	private final static String ServiceBeanName = "Service";

	/**
	 * Main method.
	 * 
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		// Load application context from classpath
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ServiceConfigurationXml);

		// Make instance and start email dispatcher service
		Service service = (Service) applicationContext.getBean(ServiceBeanName);

		try {
			// Start service
			service.start();

		} catch (ServiceException e) {
			if (logger.isErrorEnabled())
				logger.error("Service failed to start: {}", e.getMessage());

			// Exit
			System.exit(-1);
		}

	}

	/**
	 * Constructor.
	 * 
	 * @param serviceContext
	 * @param serviceStateMachine
	 */
	public DefaultService(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine) {
		this.serviceContext = serviceContext;
		this.serviceStateMachine = serviceStateMachine;
	}

	@Override
	public void start() throws ServiceException {

		try {

			if (logger.isInfoEnabled())
				logger.info("Starting service [ Service group: {} ]",
						serviceContext.getServiceInfoProvider()
								.getServiceGroup());

			// Start state machine
			getServiceStateMachine().changeState(AllowedState.STARTING);

		} catch (ServiceStateMachineException | ServiceInfoProviderException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);

			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * Service context.
	 */
	private ServiceContext serviceContext;

	@Override
	public ServiceContext getServiceContext() throws ServiceContextException {
		return serviceContext;
	}

	/**
	 * Service state machine
	 */
	private ServiceStateMachine serviceStateMachine;

	@Override
	public ServiceStateMachine getServiceStateMachine() {
		return serviceStateMachine;
	}

}
