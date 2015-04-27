package org.shopfoundry.core.service.context;

/**
 * Contextual service interface.
 * 
 * @author Bojan Bijelic
 */
public interface ContextualService {

	/**
	 * Returns service context.
	 * 
	 * @return the service context
	 * @throws ServiceContextException
	 */
	public ServiceContext getServiceContext() throws ServiceContextException;

}
