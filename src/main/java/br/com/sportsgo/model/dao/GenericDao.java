package br.com.sportsgo.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public abstract class GenericDao<T, I extends Serializable> {

    @Autowired
    private HibernateTemplate hibernateTemplate;

	protected Class<T> persistedClass;

	protected GenericDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SportsGoPU");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	protected GenericDao(Class<T> persistedClass) {
		this();
		this.persistedClass = persistedClass;
	}

	@Transactional
	public T salvar(T entity) {
		hibernateTemplate.persist(entity);
		hibernateTemplate.flush();
		return entity;
	}
	
	@Transactional
	public T atualizar(T entity) {
		hibernateTemplate.merge(entity);
		hibernateTemplate.flush();
		return entity;
	}
	@Transactional
	public void remover(I id) {
		T entity = consultarPorId(id);
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		T mergedEntity = entityManager.merge(entity);
		entityManager.remove(mergedEntity);
		entityManager.flush();
		tx.commit();
	}

	public List<T> obterLista() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistedClass);
		query.from(persistedClass);
		return entityManager.createQuery(query).getResultList();
	}

	public T consultarPorId(I id) {
		return entityManager.find(persistedClass, id);
	}
}