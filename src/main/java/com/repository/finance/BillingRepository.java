package com.repository.finance;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.view.BillingByBrandView;

@Repository
public class BillingRepository{
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	public List<Object[]> getSummaryBillingWithOutInactive(LocalDate start){
		Query query = entityManager.createNativeQuery("select * from finance.summary_billing sb where sb.last_finished_at < :start or (sb.last_finished_at is null and sb.last_started_at is null);");
		query.setParameter("start", start);
		return query.getResultList();
	}
	
	public List<Object[]> getSummaryBillingWithInactive(LocalDate start,LocalDate end){
		Query query = entityManager.createNativeQuery("select * from finance.summary_billing sb where sb.last_finished_at > :start or (sb.last_finished_at is null or sb.last_started_at is not null) and (sb.last_finished_at > :end and sb.last_started_at < :start )");
		query.setParameter("start", start);
		query.setParameter("end", start);
		return query.getResultList();
	}


}
