package org.shopfoundry.services.email;

import org.shopfoundry.core.service.DefaultService;
import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;

/**
 * Email Dispatcher Service.
 * 
 * @author Bojan Bijelic
 */
public class EmailDispatcherService extends DefaultService {

	/**
	 * Constructor.
	 * 
	 * @param serviceContext
	 * @param serviceStateMachine
	 */
	public EmailDispatcherService(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine) {
		super(serviceContext, serviceStateMachine);
	}
}
