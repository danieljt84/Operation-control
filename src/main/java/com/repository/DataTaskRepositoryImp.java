package com.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.model.DataTask;

@Repository
public class DataTaskRepositoryImp {

	@Autowired
	private EntityManager entityManager;
	@Transactional
	public void checkDataTask(DataTask dataTask) {
		Long id = null;
		try {
			id = findIdDataTask(dataTask);

			if (id != null) {
				dataTask.setId(id);
				this.entityManager.merge(dataTask);
			} else {
				this.entityManager.persist(dataTask);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
    @Transactional(readOnly = false )
	public Long findIdDataTask(DataTask dataTask) {
		Session session = (Session) entityManager.getDelegate();
		try {
			String hql = "select dt.id from DataTask dt, Promoter promoter where dt.promoter.name =:promoter and dt.date = :data";
			Query query = session.createQuery(hql);
			query.setParameter("promoter", dataTask.getPromoter().getName());
			query.setParameter("data", dataTask.getDate());
			return (Long) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
