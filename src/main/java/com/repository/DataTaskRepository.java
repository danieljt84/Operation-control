package com.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.DataTask;
import com.model.Promoter;

public interface DataTaskRepository extends JpaRepository<DataTask, Long>{
	
	@Query(value = "select dt from DataTask dt where dt.situation = 'incomplento'"
			+ " and dt.promoter.name like %:shop% and dt.promoter.grade = :grade")
	List<DataTask>  findByShopAndGradeAndSituationEqualsIncompleto(@Param("grade") String grade,
			@Param("shop")String shop);
	
	List<DataTask> findByDate(LocalDate date);

}
