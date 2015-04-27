package org.shopfoundry.core.db.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.shopfoundry.core.db.hibernate.HibernateUtil;

/**
 * Generic repository.
 * 
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
	protected Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	/**
	 * Transaction
	 */
	private Transaction transaction;

	/**
	 * Transaction getter.
	 * 
	 * @return the Hibernate transaction
	 */
	public Transaction getTransaction() {
		return transaction;
	}

	/**
	 * Transaction setter
	 * 
	 * @param transaction
	 */
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
		this.transactionOwner = false;
	}

	private boolean transactionOwner = true;

	/**
	 * Begins transaction
	 */
	public void beginTransaction() {
		if (this.transaction == null || !this.transaction.isActive()) {
			this.transaction = getSession().beginTransaction();
			this.transactionOwner = true;
		}
	}

	/**
	 * Commits transaction
	 * 
	 * @throws Exception
	 */
	public void commitTransaction() throws Exception {
		if (transactionOwner)
			this.transaction.commit();
	}

	/**
	 * Rollbacks transaction
	 * 
	 * @throws Exception
	 */
	public void rollbackTransaction() throws Exception {
		if (transactionOwner)
			this.transaction.rollback();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(ID id) throws Exception {
		beginTransaction();
		T result = (T) getSession().load(clazz, id);
		commitTransaction();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() throws Exception {
		beginTransaction();
		Criteria criteria = getSession().createCriteria(clazz);
		commitTransaction();
		return criteria.list();
	}

	@Override
	public T create(T entity) throws Exception {
		beginTransaction();
		getSession().saveOrUpdate(entity);
		commitTransaction();
		return entity;
	}

	@Override
	public void update(T transientEntity) throws Exception {
		beginTransaction();
		getSession().update(transientEntity);
		commitTransaction();
	}

	@Override
	public void delete(T persistentEntity) throws Exception {
		beginTransaction();
		getSession().delete(persistentEntity);
		commitTransaction();
	}

}
