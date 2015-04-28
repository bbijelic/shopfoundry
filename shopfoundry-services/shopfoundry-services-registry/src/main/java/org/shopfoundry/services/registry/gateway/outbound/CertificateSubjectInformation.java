package org.shopfoundry.services.registry.gateway.outbound;

import java.security.PublicKey;

/**
 * Certificate Subject Information.
 * 
 * @author Bojan Bijelic
 *
 */
public class CertificateSubjectInformation {

	/**
	 * Subject common name.
	 */
	private String commonName;

	/**
	 * Common name getter.
	 * 
	 * @return the common name
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * Common name setter.
	 * 
	 * @param commonName
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * Organizational unit.
	 */
	private String organizationalUnit;

	/**
	 * Organizational unit getter.
	 * 
	 * @return the organizational unit
	 */
	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	/**
	 * Organizational unit setter.
	 * 
	 * @param organizationalUnit
	 */
	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	/**
	 * Public key.
	 */
	private PublicKey publicKey;

	/**
	 * Public key getter.
	 * 
	 * @return the public key
	 */
	public PublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * Public key setter.
	 * 
	 * @param publicKey
	 */
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String toString() {
		return "CertificateSubjectInformation [commonName=" + commonName
				+ ", organizationalUnit=" + organizationalUnit + ", publicKey="
				+ publicKey + "]";
	}

}
