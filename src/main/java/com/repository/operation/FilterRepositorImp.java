package com.repository.operation;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FilterRepositorImp{

	@PersistenceContext
	EntityManager entityManager;
	
	public List<Object> getAllValuesToShopPossibleToFilter(LocalDate initialDate, LocalDate finalDate, Long brandId) {
		Query query = entityManager.createNativeQuery("select distinct  s.\"name\" from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, brand b , shop s\r\n"
				+ "where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and b.id = a.brand_id and s.id = t.shop_id \r\n"
				+ "and dt.\"date\" >= :initialDate and dt.\"date\" <= :finalDate and a.brand_id = :idBrand and dt.situation <>'CANCELADO'");
		query.setParameter("idBrand", brandId);
    	query.setParameter("initialDate", initialDate);
    	query.setParameter("finalDate", finalDate);
    	return query.getResultList();
	}
	
	public List<Object> getAllValuesToProjectPossibleToFilter(LocalDate initialDate, LocalDate finalDate, Long brandId) {
		Query query = entityManager.createNativeQuery("select distinct  dt.project  from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, brand b , shop s\r\n"
				+ "where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and b.id = a.brand_id and s.id = t.shop_id \r\n"
				+ "and dt.\"date\" >= :initialDate and dt.\"date\" <= :finalDate and a.brand_id = :idBrand and dt.situation <>'CANCELADO'");
		query.setParameter("idBrand", brandId);
    	query.setParameter("initialDate", initialDate);
    	query.setParameter("finalDate", finalDate);
    	return query.getResultList();
	}

	public List<Object> getAllValuesToShopPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct s.\"name\"  from finance.data_activity da, shop s where s.id = da.shop_id and da.status = 'ATIVO'");
    	return query.getResultList();
	}
	

	public List<Object> getAllValuesToDescriptionPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct a.description  from finance.data_activity da, activity a where a.id = da.activity_id and da.status = 'ATIVO'");
    	return query.getResultList();
	}
	
	public List<Object> getAllValuesToBrandDescriptionPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct b.\"name\"  from finance.data_activity da, activity a, brand b where a.id = da.activity_id and a.brand_id = b.id and da.status = 'ATIVO'");
    	return query.getResultList();
	}
	
	public List<Object> getAllValuesToHoursContractedPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct da.hours_contracted from finance.data_activity da where da.status = 'ATIVO'");
    	return query.getResultList();
	}
	
	public List<Object> getAllValuesToDaysInWeekContractedPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct da.days_in_week_contracted from finance.data_activity da where da.status = 'ATIVO'");
    	return query.getResultList();
	}


	
}
