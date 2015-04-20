package org.shopfoundry.core.security.pki;

/**
 * Certificate Signing Request.
 * 
 * @author Bojan Bijelic
 */
public class CertificateSigningRequest {

	/**
	 * @param commonName
	 * @param organizationalUnit
	 * @param organization
	 * @param locality
	 * @param state
	 * @param country
	 */
	public CertificateSigningRequest(String commonName, String email,
			String organizationalUnit, String organization, String locality,
			String state, String country) {
		super();
		this.commonName = commonName;
		this.email = email;
		this.organizationalUnit = organizationalUnit;
		this.organization = organization;
		this.locality = locality;
		this.state = state;
		this.country = country;
	}

	private String commonName;

	private String organizationalUnit;

	private String organization;

	private String locality;

	private String state;

	private String country;
	
	private String email;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the commonName
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * @return the organizationalUnit
	 */
	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CN=");
		builder.append(this.commonName);
		builder.append(", E=");
		builder.append(this.email);
		builder.append(", OU=");
		builder.append(this.organizationalUnit);
		builder.append(", O=");
		builder.append(this.organization);
		builder.append(", L=");
		builder.append(this.locality);
		builder.append(", ST=");
		builder.append(this.state);
		builder.append(", C=");
		builder.append(this.country);
		return builder.toString();
	}

}
