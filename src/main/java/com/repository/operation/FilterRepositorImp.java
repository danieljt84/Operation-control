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
	
	public List<Object[]> getAllValuesToShopPossibleToFilter(LocalDate initialDate, LocalDate finalDate, List<Long> idsBrand) {
		Query query = entityManager.createNativeQuery("select distinct  s.id, s.\"name\" from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join brand b on a.brand_id = b.id inner join shop s on s.id = t.shop_id where dt.\"date\" >= :initialDate and dt.\"date\" <= :finalDate and a.brand_id in :idsBrand and dt.situation <>'CANCELADO';");
		query.setParameter("idsBrand", idsBrand);
    	query.setParameter("initialDate", initialDate);
    	query.setParameter("finalDate", finalDate);
    	return query.getResultList();
	}
	
	public List<Object[]> getAllValuesToProjectPossibleToFilter(LocalDate initialDate, LocalDate finalDate, List<Long> idsBrand) {
		Query query = entityManager.createNativeQuery("select distinct pr.id, pr.\"name\" from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join project pr on pr.id = dt.project_id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join brand b on a.brand_id = b.id inner join shop s on s.id = t.shop_id where dt.\"date\" >= :initialDate and dt.\"date\" <= :finalDate and a.brand_id in :idsBrand and dt.situation <>'CANCELADO';");
		query.setParameter("idsBrand", idsBrand);
    	query.setParameter("initialDate", initialDate);
    	query.setParameter("finalDate", finalDate);
    	return query.getResultList();
	}

	public List<Object> getAllValuesToShopPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct s.\"name\"  from finance.data_activity da, shop s where s.id = da.shop_id");
    	return query.getResultList();
	}
	
	public List<Object> getAllValuesToProjectPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct s.project  from finance.data_activity da, shop s where s.id = da.shop_id");
    	return query.getResultList();
	}
	

	public List<Object> getAllValuesToDescriptionPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct a.description  from finance.data_activity da, activity a where a.id = da.activity_id");
    	return query.getResultList();
	}
	
	public List<Object> getAllValuesToBrandDescriptionPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct b.\"name\"  from finance.data_activity da, activity a, brand b where a.id = da.activity_id and a.brand_id = b.id");
    	return query.getResultList();
	}
	
	public List<Object> getAllValuesToHoursContractedPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct da.hours_contracted from finance.data_activity da");
    	return query.getResultList();
	}
	
	public List<Object> getAllValuesToDaysInWeekContractedPossibleToFilterInDataTableDataActivity() {
		Query query = entityManager.createNativeQuery("select distinct da.days_in_week_contracted from finance.data_activity da");
    	return query.getResultList();
	}


	
}
