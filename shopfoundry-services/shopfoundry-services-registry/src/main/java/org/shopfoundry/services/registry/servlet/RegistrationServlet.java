package org.shopfoundry.services.registry.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.shopfoundry.core.service.registry.dto.ServiceRegistrationResponse;
import org.shopfoundry.services.registry.db.entity.ServiceGroup;
import org.shopfoundry.services.registry.db.repository.ServiceGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AutoconfigServlet
 * 
 * @author Bojan Bijelic
 */
@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory
			.getLogger(RegistrationServlet.class);

	private static final String PARAM_SERVICE_GROUP = "ServiceGroup";
	private static final String PARAM_SERVICE_VERSION = "ServiceVersion";
	private static final String PARAM_ACTION = "Action";

	private static final String ACTION_REGISTER = "register";

	private static final String CONTENT_TYPE_JSON = "application/json";
	private static final String CONTENT_TYPE_XML = "application/xml";

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (logger.isTraceEnabled())
			logger.trace(request.toString());

		// Initialize repository
		ServiceGroupRepository serviceGroupRepository = new ServiceGroupRepository();

		List<String> requiredHeaders = new ArrayList<String>();
		requiredHeaders.add("Accept");
		requiredHeaders.add("Content-Type");

		// Supported content types
		List<String> supportedResponseContentTypes = new ArrayList<String>();
		supportedResponseContentTypes.add("application/json");
		// supportedResponseContentTypes.add("application/xml");

		// Required request parameters
		List<String> requiredRequestParameters = new ArrayList<String>();
		requiredRequestParameters.add(PARAM_SERVICE_GROUP);
		requiredRequestParameters.add(PARAM_SERVICE_VERSION);
		requiredRequestParameters.add(PARAM_ACTION);

		if (validateRequest(requiredHeaders, supportedResponseContentTypes,
				requiredRequestParameters, request)) {

			// Set status code
			response.setStatus(HttpServletResponse.SC_OK);

			String action = request.getParameter(PARAM_ACTION);
			String serviceGroup = request.getParameter(PARAM_SERVICE_GROUP);
			String serviceVersion = request.getParameter(PARAM_SERVICE_VERSION);

			ServiceRegistrationResponse registrationResponse = new ServiceRegistrationResponse();

			try {

				// Try to find service configuration by service group name and
				// version
				ServiceGroup serviceGroupEntity = serviceGroupRepository
						.findByNameAndVersion(serviceGroup, serviceVersion);

				// Registering service
				if (action.equalsIgnoreCase(ACTION_REGISTER)) {

					// Transaction ID
					String transactionID = UUID.randomUUID().toString();

					// Generated service GUI
					String serviceGUID = UUID.randomUUID().toString();

					if (logger.isInfoEnabled())
						logger.info(
								"[Transaction: {}] Registering service from service group '{}' version '{}' with GUID '{}'",
								transactionID, serviceGroup, serviceVersion,
								serviceGUID);

					// Set response content type to accepted by client
					response.setContentType(request.getHeader("Accept"));

					// Service configuration
					Map<String, String> serviceConfiguration = new HashMap<String, String>();

					registrationResponse.setServiceGiud(serviceGUID);
					registrationResponse.setTransactionId(transactionID);
					registrationResponse
							.setServiceConfiguration(serviceConfiguration);

					if (logger.isDebugEnabled())
						logger.debug(registrationResponse.toString());

				} else {
					if (logger.isErrorEnabled())
						logger.error("Action '{}' is not implemented", action);
					response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
				}

			} catch (Exception e) {
				if (logger.isErrorEnabled())
					logger.error(e.getMessage(), e);

				// Set status code
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);

			} finally {

				// Allways return this values
				registrationResponse.setServiceGroup(serviceGroup);
				registrationResponse.setServiceVersion(serviceVersion);

				// Handle JSON response
				if (request.getHeader("Accept").equalsIgnoreCase(
						CONTENT_TYPE_JSON)) {
					// Encode to JSON and write
					response.getWriter().print(registrationResponse.toJSON());
				}

			}

		} else {
			if (logger.isErrorEnabled())
				logger.error("Request is not valid");
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}

	}

	/**
	 * Validates request
	 * 
	 * @param requiredHeaders
	 * @param supportedContentTypes
	 * @param request
	 * @return true if request is valid, false otherwise
	 */
	private boolean validateRequest(List<String> requiredHeaders,
			List<String> supportedContentTypes,
			List<String> requiredRequestParameters, HttpServletRequest request) {

		List<String> requestHeadersNames = Collections.list(request
				.getHeaderNames());

		// Validating headers
		for (String requiredHeader : requiredHeaders) {
			if (!requestHeadersNames.contains(requiredHeader)) {
				if (logger.isErrorEnabled())
					logger.error(
							"Header '{}' is required but is not contained within request",
							requiredHeader);

				return false;
			}

			if (logger.isDebugEnabled())
				logger.debug("Required header '{}' found: '{}'",
						requiredHeader, request.getHeader(requiredHeader));

			// Validate 'accept' header
			if (requiredHeader.equalsIgnoreCase("accept")
					&& !supportedContentTypes.contains(request
							.getHeader(requiredHeader))) {
				if (logger.isErrorEnabled())
					logger.error(
							"Header '{}' supports only following values: {}",
							requiredHeader, supportedContentTypes);

				return false;

			}

			// Validate 'content-type' header
			if (requiredHeader.equalsIgnoreCase("content-type")
					&& !request.getHeader(requiredHeader).equalsIgnoreCase(
							"application/x-www-form-urlencoded")) {
				if (logger.isErrorEnabled())
					logger.error("Request content type must be 'application/x-www-form-urlencoded'");

				return false;
			}

		}

		// Validate request parameters
		List<String> requestParameters = Collections.list(request
				.getParameterNames());

		for (String requiredParameter : requiredRequestParameters) {
			if (!requestParameters.contains(requiredParameter)) {
				if (logger.isErrorEnabled())
					logger.error(
							"Request parameter '{}' is required but is not contained within request",
							requiredParameter);

				return false;
			}
		}

		return true;
	}
}
