package org.shopfoundry.services.registry;

import org.shopfoundry.core.service.DefaultService;
import org.shopfoundry.core.service.context.ServiceContext;
import org.shopfoundry.core.service.fsm.ServiceStateMachine;

/**
 * Registry service.
 * 
 * @author Bojan Bijelic
 */
public class RegistryService extends DefaultService {

	/**
	 * Constructor.
	 * 
	 * @param serviceContext
	 * @param serviceStateMachine
	 */
	public RegistryService(ServiceContext serviceContext,
			ServiceStateMachine serviceStateMachine) {
		super(serviceContext, serviceStateMachine);
	}

}
