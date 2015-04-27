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
	 * @throws Exception 
	 */
	T findById(ID id) throws Exception;

	/**
	 * Finds all entities in database
	 * 
	 * @return the entity list
	 * @throws Exception 
	 */
	List<T> findAll() throws Exception;

	/**
	 * Creates new database entry
	 * 
	 * @param entity
	 * @return the id
	 * @throws Exception 
	 */
	T create(T entity) throws Exception;

	/**
	 * Updates database entry
	 * 
	 * @param transientEntity
	 * @throws Exception 
	 */
	void update(T transientEntity) throws Exception;

	/**
	 * Deletes database entry
	 * 
	 * @param persistentEntity
	 * @throws Exception
	 */
	void delete(T persistentEntity) throws Exception;

}
