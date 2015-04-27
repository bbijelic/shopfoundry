package org.shopfoundry.services.registry.db.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.shopfoundry.core.db.repository.GenericRepository;
import org.shopfoundry.services.registry.db.entity.ServiceGroup;

public class ServiceGroupRepository extends
		GenericRepository<ServiceGroup, Long> {

	/**
	 * Finds service group by name.
	 * 
	 * @param name
	 * @return the service group
	 * @throws Exception
	 */
	public ServiceGroup findByNameAndVersion(String name, String version)
			throws Exception {
		// Find by name AND version
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("name", name));
		conjunction.add(Restrictions.eq("version", version));

		// Begin transaction
		beginTransaction();
		
		// Create criteria
		Criteria criteria = getSession().createCriteria(ServiceGroup.class);
		criteria.add(conjunction);

		ServiceGroup resultServiceGroup = (ServiceGroup) criteria
				.uniqueResult();
		if (resultServiceGroup == null)
			throw new Exception("Service group not found");

		// Commit transaction
		commitTransaction();
		
		// Return service group
		return resultServiceGroup;
	}

}
