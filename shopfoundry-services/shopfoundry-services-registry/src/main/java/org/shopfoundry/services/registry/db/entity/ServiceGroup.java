/**
 * 
 */
package org.shopfoundry.services.registry.db.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Service group entity.
 * 
 * @author Bojan Bijelic
 */
@Entity
@Table(name = "service_group")
public class ServiceGroup {

	/**
	 * ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;

	/**
	 * ID getter.
	 * 
	 * @return the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * ID setter.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Service group name.
	 */
	@Column(name = "name", length = 255, nullable = false)
	private String name;

	/**
	 * Service group name getter.
	 * 
	 * @return the service group name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Service group name setter.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Service group description
	 */
	@Column(name = "description", length = 255, nullable = true)
	private String description;

	/**
	 * Service group description getter
	 * 
	 * @return the service group description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Service group description setter
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Service group version
	 */
	@Column(name = "version", length = 20, nullable = false)
	private String version;

	/**
	 * Service group version getter
	 * 
	 * @return the service group version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Service group version setter
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Suspended.
	 */
	@Column(name = "suspended", nullable = false)
	private boolean suspended = false;

	/**
	 * Is susespended
	 * 
	 * @return true if suspended, false otherwise
	 */
	public boolean isSuspended() {
		return suspended;
	}

	/**
	 * Suspended setter
	 * 
	 * @param suspended
	 */
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	/**
	 * Service group configuration.
	 */
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="service_group")
	private List<ServiceGroupConfiguration> serviceGroupConfiguration = new ArrayList<ServiceGroupConfiguration>();

	/**
	 * Service group configuration getter.
	 * 
	 * @return the service group configuration
	 */
	public List<ServiceGroupConfiguration> getServiceGroupConfiguration() {
		return serviceGroupConfiguration;
	}

	/**
	 * Service group configuration setter.
	 * 
	 * @param serviceGroupConfiguration
	 */
	public void setServiceGroupConfiguration(
			List<ServiceGroupConfiguration> serviceGroupConfiguration) {
		this.serviceGroupConfiguration = serviceGroupConfiguration;
	}

	@Override
	public String toString() {
		return "ServiceGroup [id=" + id + ", name=" + name + ", description="
				+ description + ", version=" + version + ", suspended="
				+ suspended + ", serviceGroupConfiguration="
				+ serviceGroupConfiguration + "]";
	}
}
