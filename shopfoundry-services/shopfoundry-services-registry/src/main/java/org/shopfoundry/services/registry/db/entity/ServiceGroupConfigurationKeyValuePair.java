package org.shopfoundry.services.registry.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Service group configuration key pair.
 *
 * @author Bojan Bijelic
 */
@Entity
@Table(name = "service_group_configuration_key_pairs")
public class ServiceGroupConfigurationKeyValuePair {

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
	 * Configuration.
	 */
	@Column(name = "configuration")
	private long configuration;

	/**
	 * Configuration getter.
	 * 
	 * @return the configuration
	 */
	public long getConfiguration() {
		return configuration;
	}

	/**
	 * Configuration setter.
	 * 
	 * @param configuration
	 */
	public void setConfiguration(long configuration) {
		this.configuration = configuration;
	}

	/**
	 * Configuration key.
	 */
	@Column(name = "config_key")
	private String key;

	/**
	 * Configuration key getter.
	 * 
	 * @return the configuration key getter.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Configuration key setter.
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Configuration value
	 */
	@Column(name = "config_value")
	private String value;

	/**
	 * Configuration value
	 * 
	 * @return the configuration value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Configuration value setter.
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Is public pair
	 */
	@Column(name = "public", nullable = false)
	private boolean publicPair = false;

	/**
	 * Public pair getter.
	 * 
	 * @return the public pair
	 */
	public boolean isPublicPair() {
		return publicPair;
	}

	/**
	 * Public pair setter.
	 * 
	 * @param publicPair
	 */
	public void setPublicPair(boolean publicPair) {
		this.publicPair = publicPair;
	}

	@Override
	public String toString() {
		return "ServiceGroupConfigurationKeyValuePair [id=" + id + ", configuration=" + configuration + ", key=" + key
				+ ", value=" + value + ", publicPair=" + publicPair + "]";
	}
}
