package com.repository;

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


	
}
