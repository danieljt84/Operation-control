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
	
	
	public List<Object[]> getSummaryBillingWithOutInactive(){
		Query query = entityManager.createNativeQuery("select sb.id_data_activity, sb.sum_bill from public.summary_billing sb where sb.started_at_inactive is null;");
		return query.getResultList();
	}
	
	public List<Object[]> getSummaryBillingWithInactive(LocalDate start){
		Query query = entityManager.createNativeQuery("select sb.id_data_activity, sb.sum_bill, sb.started_at_inactive from public.summary_billing sb where sb.started_at_inactive >= :start");
		query.setParameter("start", start);
		return query.getResultList();
	}


}
