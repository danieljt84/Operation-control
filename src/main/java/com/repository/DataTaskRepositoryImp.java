package com.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	@Autowired
	private DataTaskRepository dataTaskRepository;
	
    @Transactional
	public void checkDataTask(DataTask dataTask) {
		Optional<Long> id = null;
		try {
			id = findIdDataTask(dataTask);

			if (id.isPresent()) {
				dataTask.setId(id.get());
				this.entityManager.merge(dataTask);;
			} else {
				this.entityManager.merge(dataTask);;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@Transactional
	public Optional<Long> findIdDataTask(DataTask dataTask) {
		Session session = (Session) entityManager.getDelegate();
		try {
			String hql = "select dt.id from DataTask dt where dt.date = :data and dt.promoter.name=:promoter";
			Query query = session.createQuery(hql);
			query.setParameter("promoter", dataTask.getPromoter().getName());
			query.setParameter("data", dataTask.getDate());
			return Optional.ofNullable((Long) query.getResultList().stream().findFirst().orElse(null));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
