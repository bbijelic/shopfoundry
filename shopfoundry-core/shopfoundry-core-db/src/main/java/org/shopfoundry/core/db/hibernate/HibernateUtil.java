package org.shopfoundry.core.db.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate util class
 * 
 * @author Bojan Bijelic
 */
public class HibernateUtil {

	/**
	 * Session factory
	 */
	private static SessionFactory sessionFactory;

	/**
	 * Provides instance of a Hibernate session factory
	 * 
	 * @return the session factory
	 * @throws ExceptionInInitializerError
	 */
	public synchronized static SessionFactory getSessionFactory()
			throws ExceptionInInitializerError {

		if (sessionFactory == null) {

			try {

				// Configuration load from hibernate configuration file
				Configuration configuration = new Configuration()
						.configure(HibernateUtil.class
								.getResource("hibernate.cfg.xml"));

				// Standard service registry builder instance
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

				// Apply configuration to registry builder
				serviceRegistryBuilder.applySettings(configuration
						.getProperties());

				// Build service registry
				ServiceRegistry serviceRegistry = serviceRegistryBuilder
						.build();

				// Build session factory
				sessionFactory = configuration
						.buildSessionFactory(serviceRegistry);

			} catch (Throwable ex) {
				throw new ExceptionInInitializerError(ex);
			}
		}

		return sessionFactory;
	}

	/**
	 * Shuts down hibernate session factory
	 */
	public synchronized static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}
