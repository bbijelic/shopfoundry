package org.shopfoundry.core.admin.acl.user.domain;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.shopfoundry.core.admin.acl.role.domain.Role;

/**
 * User domain entity.
 * 
 * @author Bojan Bijelic
 */
@Entity
@Table(name = "sf_acl_user")
public class User {

	/**
	 * User ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * User ID getter.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * User ID setter.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * User email.
	 */
	private String email;

	/**
	 * User email getter.
	 * 
	 * @return the email
	 */
	@Column(name = "email", insertable = true, updatable = false, length = 255, nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	/**
	 * User email setter.
	 * 
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * User password.
	 */
	private String password;

	/**
	 * User password getter.
	 * 
	 * @return the password
	 */
	@Column(name = "password", insertable = true, updatable = true, length = 64, nullable = false, unique = false)
	public String getPassword() {
		return password;
	}

	/**
	 * User password setter.
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * User password salt.
	 */
	public String salt;

	/**
	 * User password salt getter.
	 * 
	 * @return the salt
	 */
	@Column(name = "salt", insertable = true, updatable = false, length = 36, nullable = false, unique = true)
	public String getSalt() {
		return salt;
	}

	/**
	 * User password salt setter.
	 * 
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Users first name.
	 */
	private String firstName;

	/**
	 * Users first name getter.
	 * 
	 * @return the firstName
	 */
	@Column(name = "firstName", insertable = true, updatable = false, length = 100, nullable = false, unique = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Users first name setter.
	 * 
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Users last name.
	 */
	private String lastName;

	/**
	 * Users last name getter.
	 * 
	 * @return the lastName
	 */
	@Column(name = "lastName", insertable = true, updatable = true, length = 100, nullable = false, unique = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Users last name setter.
	 * 
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Account created at.
	 */
	private Date createdAt;

	/**
	 * Account created at getter.
	 * 
	 * @return the createdAt
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", insertable = true, updatable = false, nullable = false, unique = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Account created at setter.
	 * 
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@PrePersist
	public void prePersist() {
		this.salt = generateSalt();
		this.createdAt = new Date();
	}
	
	/**
	 * Generates and returns random 36 characters long salt string
	 * @return
	 */
	private String generateSalt(){
		return new BigInteger(180, new SecureRandom()).toString(32);
	}

	/**
	 * Account last updated at.
	 */
	private Date updatedAt;

	/**
	 * Account last updated at getter.
	 * 
	 * @return the updatedAt
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", insertable = true, updatable = true, nullable = true, unique = false)
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Account last updated at setter.
	 * 
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = new Date();
	}

	/**
	 * Roles.
	 */
	private Collection<Role> roles;

	/**
	 * Roles getter.
	 * 
	 * @return the roles
	 */
	@ManyToMany
	@JoinTable(name = "sf_acl_user_roles", inverseJoinColumns = { @JoinColumn(name = "role", referencedColumnName = "id") }, joinColumns = { @JoinColumn(name = "acluser", referencedColumnName = "id") })
	public Collection<Role> getRoles() {
		return roles;
	}

	/**
	 * Rolessetter.
	 * 
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append(", salt=");
		builder.append(salt);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", updatedAt=");
		builder.append(updatedAt);
		builder.append(", roles=");
		builder.append(roles);
		builder.append("]");
		return builder.toString();
	}
}
