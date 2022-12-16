package com.repository.finance;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.controller.form.FilterDataTableDataActivityForm;
import com.model.finance.DataActivity;

@Repository
public class DataActivityCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public List<BigInteger> findByFilter(FilterDataTableDataActivityForm filter){
		String sql = "select da.id as da_id from finance.data_activity da, shop s, activity a, brand b "
				+ "where da.shop_id = s.id and da.activity_id = a.id and a.brand_id = b.id";
		 sql = sql + changeSQLString(sql, filter);
		Query query = entityManager.createNativeQuery(sql);
		changeQueryParameter(query, filter);
		return query.getResultList();
	}
	
	private String changeSQLString(String sql,FilterDataTableDataActivityForm filter) {
		String return_sql = "";
			if (!filter.getBrand().isEmpty()) {
				return_sql = return_sql + " and b.name in ( :brands ) ";
			}
			if (!filter.getShop().isEmpty()) {
				return_sql = return_sql + " and s.name in ( :shops ) ";
			}
			if (!filter.getDescription().isEmpty()) {
				return_sql = return_sql + " and a.description in ( :descriptions ) ";
			}
			if (!filter.getDaysInWeekContracted().isEmpty()) {
				return_sql = return_sql + " and da.days_in_week_contracted in ( :daysInWeekContracted ) ";
			}
			if (!filter.getHoursContracted().isEmpty()) {
				return_sql = return_sql + " and da.hours_contracted in (:hoursContracted ) ";
			}
			if (!filter.getProject().isEmpty()) {
				return_sql = return_sql + " and s.project in (:project ) ";
			}
			if (!return_sql.equals("")) {
				return_sql = " and ( true " + return_sql + ")";
			}
		return return_sql;
	}
	
	private void changeQueryParameter(Query query, FilterDataTableDataActivityForm filter) {
		if (!filter.getBrand().isEmpty()) {
			query.setParameter("brands", filter.getBrand());
		}
		if (!filter.getShop().isEmpty()) {
			query.setParameter("shops", filter.getShop());
		}
		if (!filter.getDescription().isEmpty()) {
			query.setParameter("descriptions", filter.getDescription());
		}
		if (!filter.getDaysInWeekContracted().isEmpty()) {
			query.setParameter("daysInWeekContracted", filter.getDaysInWeekContracted());
		}
		if (!filter.getHoursContracted().isEmpty()) {
			query.setParameter("hoursContracted", filter.getHoursContracted());
		}
		if (!filter.getProject().isEmpty()) {
			query.setParameter("project", filter.getProject());
		}
	}
	
}
