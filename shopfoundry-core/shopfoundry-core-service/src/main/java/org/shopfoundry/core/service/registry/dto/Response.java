package org.shopfoundry.core.service.registry.dto;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Service registration response.
 * 
 * @author Bojan Bijelic
 */
@XStreamAlias("RegistrationResult")
public class Response {

	/**
	 * Service GUID
	 */
	@XStreamAlias("ServiceGuid")
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
	@XStreamAlias("Configuration")
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
	@XStreamAlias("ServiceGroup")
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
	@XStreamAlias("ServiceVersion")
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

	/**
	 * Signed certificate. Base64.
	 */
	@XStreamAlias("Certificate")
	private String certificate;

	/**
	 * Signed certificate getter.
	 * 
	 * @return the signed certificate base64 encoded.
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * Signed certificate setter.
	 * 
	 * @param certificate
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * Certificate authority chain.
	 */
	@XStreamAlias("CertificateAuthorityChain")
	private String certificateAuthorityChain;

	/**
	 * Certificate authority chain getter.
	 * 
	 * @return the certificate authority chain.
	 */
	public String getCertificateAuthorityChain() {
		return certificateAuthorityChain;
	}

	/**
	 * Certificate authority chain settter.
	 * 
	 * @param certificateAuthorityChain
	 */
	public void setCertificateAuthorityChain(String certificateAuthorityChain) {
		this.certificateAuthorityChain = certificateAuthorityChain;
	}

	@Override
	public String toString() {
		return "ServiceRegistrationResponse [serviceGiud=" + serviceGiud
				+ ", serviceConfiguration=" + serviceConfiguration
				+ ", serviceGroup=" + serviceGroup + ", serviceVersion="
				+ serviceVersion + ", certificate=" + certificate
				+ ", certificateAuthorityChain=" + certificateAuthorityChain
				+ "]";
	}
}
