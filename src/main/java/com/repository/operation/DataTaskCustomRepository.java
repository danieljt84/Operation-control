package com.repository.operation;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.model.Brand;

@Repository
public class DataTaskCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public Integer getCountActivityMissingBetweenDateByBrand(LocalDate initialDate, LocalDate finalDate, Long idBrand,Map<String, String[]> filter) {
		
		String sql = "select distinct count(a) from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, brand b , shop s "
				+ " where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and b.id = a.brand_id and s.id = t.shop_id "
				+ " and b.id = :idBrand and dt.\"date\" >= :initialDate and  dt.\"date\" <= :finalDate and a.description like 'Pesquisa%' and a.situation = 'sem historico' and t.\"type\" <> 'Remanejada' and  dt.situation <> 'CANCELADO' ";
		sql = sql + changeSQLString(sql, filter);
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idBrand", idBrand);
		query.setParameter("initialDate", initialDate);
		query.setParameter("finalDate", finalDate);
		changeQueryParameter(query, filter);
		
		return ((BigInteger) query.getSingleResult()).intValue();

	}

	public Integer getCountActivityCompleteByBrand(LocalDate initialDate, LocalDate finalDate, long idBrand,
			Map<String, String[]> filter) {

		String sql = "select distinct count(a) from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, brand b , shop s "
				+ " where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and b.id = a.brand_id and s.id = t.shop_id "
				+ " and b.id = :idBrand and a.\"start\" >= :initialDate and  a.\"start\"<= :finalDate  and a.description like 'Pesquisa%' and a.situation = 'completa' ";

		sql = sql + changeSQLString(sql, filter);

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idBrand", idBrand);
		query.setParameter("initialDate", initialDate);
		query.setParameter("finalDate", finalDate);
		changeQueryParameter(query, filter);
		
		return ((BigInteger) query.getSingleResult()).intValue();
	}

	private void changeQueryParameter(Query query, Map<String, String[]> filter) {
		if (filter.containsKey("shop")) {
			query.setParameter("shops", Arrays.asList(filter.get("shop")));
		}
		if (filter.containsKey("project")) {
			query.setParameter("projects", Arrays.asList(filter.get("project")));
		}
	}

	private String changeSQLString(String sql, Map<String, String[]> filter) {
		String return_sql = "";
		if (filter.containsKey("shop")) {
			return_sql = return_sql + " or s.name in ( :shops ) ";
		}
		if (filter.containsKey("project")) {
			return_sql = return_sql + " or dt.project in ( :projects ) ";
		}
		if (!return_sql.equals("")) {
			return_sql = " and ( false " + return_sql + ")";
		}
		return return_sql;

	}

}
