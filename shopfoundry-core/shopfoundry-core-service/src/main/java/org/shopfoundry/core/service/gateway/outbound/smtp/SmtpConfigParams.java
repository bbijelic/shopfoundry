package org.shopfoundry.core.service.gateway.outbound.smtp;

/**
 * SMTP configuration parameters
 *
 * @author Bojan Bijelic
 */
public class SmtpConfigParams {

	/**
	 * SMTP service parameters
	 *
	 * @author Bojan Bijelic
	 */
	public class Server {
		
		public static final String HOSTNAME = "smtp.server.hostname";
		public static final String PORT = "smtp.server.port";
		public static final String USERNAME = "smtp.server.username";
		public static final String PASSWORD = "smtp.server.password";
		
	}
	
}
