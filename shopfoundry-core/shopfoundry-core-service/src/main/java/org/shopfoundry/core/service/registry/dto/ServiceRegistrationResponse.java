package org.shopfoundry.core.service.registry.dto;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * Service registration response.
 * 
 * @author Bojan Bijelic
 */
public class ServiceRegistrationResponse {

	/**
	 * Transaction ID
	 */
	private String transactionId;

	/**
	 * Transaction ID getter.
	 * 
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Transaction ID setter.
	 * 
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Service GUID
	 */
	private String serviceGiud;

	/**
	 * Service GUID getter
	 * 
	 * @return the serviceGiud
	 */
	public String getServiceGiud() {
		return serviceGiud;
	}

	/**
	 * Service GUID setter.
	 * 
	 * @param serviceGiud
	 *            the serviceGiud to set
	 */
	public void setServiceGiud(String serviceGiud) {
		this.serviceGiud = serviceGiud;
	}

	/**
	 * Service configuration
	 */
	public Map<String, String> serviceConfiguration = new HashMap<String, String>();

	/**
	 * Service configuration getter.
	 * 
	 * @return the serviceConfiguration
	 */
	public Map<String, String> getServiceConfiguration() {
		return serviceConfiguration;
	}

	/**
	 * Service configuration setter.
	 * 
	 * @param serviceConfiguration
	 *            the serviceConfiguration to set
	 */
	public void setServiceConfiguration(Map<String, String> serviceConfiguration) {
		this.serviceConfiguration = serviceConfiguration;
	}

	/**
	 * Service group
	 */
	private String serviceGroup;

	/**
	 * Service group getter.
	 * 
	 * @return the serviceGroup
	 */
	public String getServiceGroup() {
		return serviceGroup;
	}

	/**
	 * Service group setter.
	 * 
	 * @param serviceGroup
	 *            the serviceGroup to set
	 */
	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	/**
	 * Service version
	 */
	private String serviceVersion;

	/**
	 * Service version getter.
	 * 
	 * @return the serviceVersion
	 */
	public String getServiceVersion() {
		return serviceVersion;
	}

	/**
	 * Service version setter.
	 * 
	 * @param serviceVersion
	 *            the serviceVersion to set
	 */
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("TransactionID", transactionId);
		jsonObject.accumulate("ServiceGroup", serviceGroup);
		jsonObject.accumulate("ServiceVersion", serviceVersion);
		jsonObject.accumulate("ServiceGUID", serviceGiud);
		jsonObject.accumulate("ServiceConfiguration", serviceConfiguration);
		return jsonObject.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceRegistrationResponse [transactionId=");
		builder.append(transactionId);
		builder.append(", serviceGiud=");
		builder.append(serviceGiud);
		builder.append(", serviceConfiguration=");
		builder.append(serviceConfiguration);
		builder.append(", serviceGroup=");
		builder.append(serviceGroup);
		builder.append(", serviceVersion=");
		builder.append(serviceVersion);
		builder.append("]");
		return builder.toString();
	}

}
