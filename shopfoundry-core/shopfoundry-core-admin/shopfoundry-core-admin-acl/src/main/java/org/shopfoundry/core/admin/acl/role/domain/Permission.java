package org.shopfoundry.core.admin.acl.role.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * ACL Permission
 * @author Bojan Bijelic
 */
@Entity(name="sf_acl_permission")
public class Permission {

	/**
	 * Permission ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Permission ID getter.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Permission ID setter.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Permission key.
	 */
	private String key;

	/**
	 * Permission key getter.
	 * 
	 * @return the key
	 */
	@Column(name = "permissionKey", insertable = false, updatable = false, nullable = false, length = 100, unique = true)
	public String getKey() {
		return key;
	}

	/**
	 * Permission key setter.
	 * 
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Permission name.
	 */
	private String name;

	/**
	 * Permission name getter.
	 * 
	 * @return the name
	 */
	@Column(name = "name", insertable = false, updatable = false, nullable = false, length = 100, unique = true)
	public String getName() {
		return name;
	}

	/**
	 * Permission name setter.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Permission description.
	 */
	private String description;

	/**
	 * Permission description getter.
	 * 
	 * @return the description
	 */
	@Column(name = "description", insertable = false, updatable = false, nullable = false, length = 255, unique = false)
	public String getDescription() {
		return description;
	}

	/**
	 * Permission description setter.
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Permission [id=");
		builder.append(id);
		builder.append(", key=");
		builder.append(key);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
	
}
