package com.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.DataTask;
import com.model.Promoter;
import com.util.Status;

public interface DataTaskRepository extends JpaRepository<DataTask, Long>{
	
	@Query(value = "select dt from DataTask dt where dt.situation = 'incomplento'"
			+ " and dt.promoter.name like %:shop% and dt.promoter.grade = :grade")
	List<DataTask>  findByShopAndGradeAndSituationEqualsIncompleto(@Param("grade") String grade,
			@Param("shop")String shop);
	@Query(value = "select dt.id from DataTask dt where dt.date = :#{#param.date} and dt.promoter.name= :#{#param.promoter.name}")
	Optional<Long> findIdDataTask(@Param(value = "param") DataTask dataTask);
	List<DataTask> findByDate(LocalDate date);
	List<DataTask> findByDateBetweenAndPromoterStatus(LocalDate start, LocalDate end,Status status);
	List<DataTask> findByDateBetweenAndPromoterName(LocalDate start, LocalDate end,String name);
	List<DataTask> findByDateBetweenAndProject(LocalDate start, LocalDate end,String project);
}
