package org.shopfoundry.core.db.repository;

import java.io.Serializable;
import java.util.List;

/**
 * Generic repository interface.
 * 
 * @author Bojan Bijelic
 */
public interface Repository<T, ID extends Serializable> {

	/**
	 * Finds entity by id
	 * 
	 * @param id
	 * @return the entity
	 */
	T findById(ID id);

	/**
	 * Finds all entities in database
	 * 
	 * @return the entity list
	 */
	List<T> findAll();

	/**
	 * Finds by entity equality
	 * 
	 * @param entity
	 * @return the entity list
	 */
	List<T> find(T entity);

	/**
	 * Creates new database entry
	 * 
	 * @param entity
	 * @return the id
	 */
	T create(T entity);

	/**
	 * Updates database entry
	 * 
	 * @param transientEntity
	 */
	void update(T transientEntity);

	/**
	 * Deletes database entry
	 * 
	 * @param persistentEntity
	 */
	void delete(T persistentEntity);

}
