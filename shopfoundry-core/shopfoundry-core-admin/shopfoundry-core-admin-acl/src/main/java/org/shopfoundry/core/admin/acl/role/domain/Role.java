package org.shopfoundry.core.admin.acl.role.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * ACL Role domain entity.
 * 
 * @author Bojan Bijelic
 */
@Entity
@Table(name = "sf_acl_role")
public class Role {

	/**
	 * Role ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Role ID getter.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Role ID setter.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Role name.
	 */
	private String name;

	/**
	 * Role name getter.
	 * 
	 * @return the name
	 */
	@Column(name = "name", insertable = true, length = 100, nullable = false, unique = true, updatable = true)
	public String getName() {
		return name;
	}

	/**
	 * Role name setter.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Role description.
	 */
	private String description;

	/**
	 * Role description getter.
	 * 
	 * @return the description
	 */
	@Column(name = "description", insertable = true, updatable = true, nullable = true, length = 255, unique = false)
	public String getDescription() {
		return description;
	}

	/**
	 * Role description setter.
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Permissions.
	 */
	private Collection<Permission> permissions;

	/**
	 * Permissions getter.
	 * 
	 * @return the permissions
	 */
	@ManyToMany
	@JoinTable(name = "sf_acl_role_permissions", inverseJoinColumns = { @JoinColumn(name = "permission", referencedColumnName = "id") }, joinColumns = { @JoinColumn(name = "role", referencedColumnName = "id") })
	public Collection<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * Permissions setter.
	 * 
	 * @param permissions
	 *            the permissions to set
	 */
	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", permissions=");
		builder.append(permissions);
		builder.append("]");
		return builder.toString();
	}
	
}
