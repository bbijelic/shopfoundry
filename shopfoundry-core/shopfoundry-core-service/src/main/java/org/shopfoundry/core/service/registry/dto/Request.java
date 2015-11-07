package org.shopfoundry.core.service.registry.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Service registration request.
 *
 * @author Bojan Bijelic
 */
@XStreamAlias("ServiceRegistrationRequest")
public class Request {

	/**
	 * Service group
	 */
	@XStreamAlias("ServiceGroup")
	private String serviceGroup;

	/**
	 * Service group getter
	 * 
	 * @return the service group
	 */
	public String getServiceGroup() {
		return serviceGroup;
	}

	/**
	 * Service group setter
	 * 
	 * @param serviceGroup
	 */
	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	/**
	 * Service version
	 */
	@XStreamAlias("ServiceVersion")
	private String serviceVersion;

	/**
	 * Service version getter.
	 * 
	 * @return the service version
	 */
	public String getServiceVersion() {
		return serviceVersion;
	}

	/**
	 * Service version setter
	 * 
	 * @param serviceVersion
	 */
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	/**
	 * Certificate public key
	 */
	@XStreamAlias("CertificatePublicKey")
	private String certificatePublicKey;

	/**
	 * Certificate public key getter
	 * 
	 * @return the certificate public key
	 */
	public String getCertificatePublicKey() {
		return certificatePublicKey;
	}

	/**
	 * Certificate public key setter
	 * 
	 * @param certificatePublicKey
	 */
	public void setCertificatePublicKey(String certificatePublicKey) {
		this.certificatePublicKey = certificatePublicKey;
	}

	@Override
	public String toString() {
		return "ServiceRegistrationRequest [serviceGroup=" + serviceGroup
				+ ", serviceVersion=" + serviceVersion
				+ ", certificatePublicKey=" + certificatePublicKey + "]";
	}

}
