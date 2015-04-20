package org.shopfoundry.core.db.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.shopfoundry.core.db.hibernate.HibernateUtil;

/**
 * @author Bojan Bijelic
 */
public abstract class GenericRepository<T, ID extends Serializable> implements
		Repository<T, ID> {

	private Class<T> clazz;

	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public GenericRepository() {
		clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Returns current session
	 * 
	 * @return the current session
	 */
	private Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		return (T) getSession().load(clazz, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria criteria = getSession().createCriteria(clazz);
		return criteria.list();
	}

	@Override
	public T create(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public void update(T transientEntity) {
		getSession().update(transientEntity);

	}

	@Override
	public void delete(T persistentEntity) {
		getSession().delete(persistentEntity);

	}

}
