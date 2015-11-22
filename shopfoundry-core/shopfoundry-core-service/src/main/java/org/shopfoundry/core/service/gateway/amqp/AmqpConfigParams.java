package org.shopfoundry.core.service.gateway.amqp;

/**
 * Amqp Configuration parameters
 *
 * @author Bojan Bijelic
 */
public class AmqpConfigParams {

	/**
	 * Broker related properties
	 *
	 * @author Bojan Bijelic
	 */
	public class Broker {

		// Connection parameters
		public static final String HOSTNAME = "broker.hostname";
		public static final String PORT = "broker.port";
		public static final String VIRTUALHOST = "broker.virtualhost";
		public static final String USERNAME = "broker.username";
		public static final String PASSWORD = "broker.password";
		public static final String SSL_ENABLED = "broker.ssl.enabled";
		public static final String SSL_VERSION = "broker.ssl.version";

	}

	/**
	 * Bus related properties
	 *
	 * @author Bojan Bijelic
	 */
	public class Bus {

		/**
		 * Event bus related properties
		 *
		 * @author Bojan Bijelic
		 */
		public class Event {

			// Event bus parameters
			public static final String EXCHANGE_NAME = "bus.event.exchange.name";
			public static final String ROUTING_ALL = "bus.event.routing.all";
			public static final String ROUTING_SERVICE_GROUP = "bus.event.routing.service.group";
			public static final String ROUTING_SERVICE_INSTANCE = "bus.event.routing.service.instance";

		}

		/**
		 * Service bus related properties
		 *
		 * @author Bojan Bijelic
		 */
		public class Service {

			// Service bus parameters
			public static final String EXCHANGE_NAME = "bus.service.exchange.name";
			public static final String QUEUE_NAME = "bus.service.queue.name";
			public static final String ROUTING = "bus.service.routing";

		}

	}

}
