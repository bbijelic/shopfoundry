package org.shopfoundry.core.service.gateway.outbound.smtp;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.shopfoundry.core.service.config.ConfigurationProvider;
import org.shopfoundry.core.service.gateway.outbound.OutboundGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation for SMTP outbound gateway.
 *
 * @author Bojan Bijelic
 */
public class DefaultSmtpOutboundGateway implements SmtpOutboundGateway {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DefaultSmtpOutboundGateway.class);

	/**
	 * Configuration provider
	 */
	private ConfigurationProvider configurationProvider;

	/**
	 * Smtp configuration
	 */
	private SmtpConfiguration smtpConfiguration;

	/**
	 * Smtp configuration getter
	 * 
	 * @return the smtpConfiguration
	 */
	public SmtpConfiguration getSmtpConfiguration() {
		return smtpConfiguration;
	}

	/**
	 * Smtp configuration setter
	 * 
	 * @param smtpConfiguration
	 *            the smtpConfiguration to set
	 */
	public void setSmtpConfiguration(SmtpConfiguration smtpConfiguration) {
		this.smtpConfiguration = smtpConfiguration;
	}

	/**
	 * Constructor
	 * 
	 * @param smtpConfiguration
	 * @param configurationProvider
	 */
	public DefaultSmtpOutboundGateway(SmtpConfiguration smtpConfiguration,
			ConfigurationProvider configurationProvider) {
		this.smtpConfiguration = smtpConfiguration;
		this.configurationProvider = configurationProvider;
	}

	@Override
	public void configure() throws OutboundGatewayException {
		// Validate if mandatory configuration parameters are defined
		if (!configurationProvider.getConfiguration().containsKey(SmtpConfigParams.Server.HOSTNAME)) {
			throw new OutboundGatewayException(
					String.format("SMTP mandatory parameter '%s' not defined", SmtpConfigParams.Server.HOSTNAME));
		}

		if (!configurationProvider.getConfiguration().containsKey(SmtpConfigParams.Server.PORT)) {
			throw new OutboundGatewayException(
					String.format("SMTP mandatory parameter '%s' not defined", SmtpConfigParams.Server.PORT));
		}

		if (smtpConfiguration == null)
			throw new OutboundGatewayException("Smtp properties instance not defined");

		try {

			// Import properties from configuration provider
			smtpConfiguration.importProperties(configurationProvider);

			if (logger.isInfoEnabled())
				logger.info("Smtp properties successfully imported");

		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage());

			throw new OutboundGatewayException("Import of SMTP properties failed", e);
		}
	}

	@Override
	public void start() throws Exception {
		if (logger.isInfoEnabled())
			logger.info("Smtp outbound gateway started");
	}

	@Override
	public void stop() {
		if (logger.isInfoEnabled())
			logger.info("Smtp outbound gateway stopped");
	}

	@Override
	public void sendEmail(EmailRequest emailRequest) throws SmtpOutboundGatewayException {

		// Multipart email message instance
		MultiPartEmail email = new MultiPartEmail();

		// Set SMTP server hostname
		email.setHostName(smtpConfiguration.getHostname());

		// Set SMTP server port
		email.setSmtpPort(smtpConfiguration.getPort());

		// Setup authentication
		if (smtpConfiguration.getUsername() != null && smtpConfiguration.getPassword() != null) {
			email.setAuthentication(smtpConfiguration.getUsername(), smtpConfiguration.getPassword());
		}

		try {

			// TO
			if (!emailRequest.getTo().isEmpty()) {
				for (String to : emailRequest.getTo())
					email.addTo(to);
			}

			// CC
			if (!emailRequest.getCc().isEmpty()) {
				for (String cc : emailRequest.getCc())
					email.addCc(cc);
			}

			// BCC
			if (!emailRequest.getBcc().isEmpty()) {
				for (String bcc : emailRequest.getBcc())
					email.addBcc(bcc);
			}

			// ReplyTo
			if (!emailRequest.getReplyTo().isEmpty()) {
				for (String replyTo : emailRequest.getReplyTo())
					email.addReplyTo(replyTo);
			}

			// From
			if (emailRequest.getFrom() != null) {
				email.setFrom(emailRequest.getFrom());
			}

			// Subject
			if (emailRequest.getSubject() != null) {
				email.setSubject(emailRequest.getSubject());
			}

			// Message body
			if (emailRequest.getMessage() != null) {
				email.setMsg(emailRequest.getMessage());
			}

			// Sends email message
			email.send();

			if (logger.isInfoEnabled())
				logger.info("Email message sent");

			if (logger.isTraceEnabled())
				logger.trace("Email message: {}", email.toString());

		} catch (EmailException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage());

			throw new SmtpOutboundGatewayException(e.getMessage(), e);
		}

	}

}
