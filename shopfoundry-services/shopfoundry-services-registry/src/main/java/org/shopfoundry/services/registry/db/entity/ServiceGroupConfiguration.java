package org.shopfoundry.services.registry.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Service group configuration.
 * 
 * @author Bojan Bijelic
 */
@Entity
@Table(name = "service_group_configuration")
public class ServiceGroupConfiguration {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	/**
	 * ID getter.
	 * 
	 * @return the ID
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
	 * Active from.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "active_from")
	private Date activeFrom;

	/**
	 * Active from getter.
	 * 
	 * @return the active from timestamp
	 */
	public Date getActiveFrom() {
		return activeFrom;
	}

	/**
	 * Active from setter.
	 * 
	 * @param activeFrom
	 */
	public void setActiveFrom(Date activeFrom) {
		this.activeFrom = activeFrom;
	}

	/**
	 * Configuration data
	 */
	@Column(name = "configuration_data")
	private String configurationData;

	/**
	 * Configuration data getter.
	 * 
	 * @return the configuration data
	 */
	public String getConfigurationData() {
		return configurationData;
	}

	/**
	 * Configuration data setter
	 * 
	 * @param configurationData
	 */
	public void setConfigurationData(String configurationData) {
		this.configurationData = configurationData;
	}

	@Override
	public String toString() {
		return "ServiceGroupConfiguration [id=" + id + ", activeFrom="
				+ activeFrom + ", configurationData=" + configurationData + "]";
	}

}
