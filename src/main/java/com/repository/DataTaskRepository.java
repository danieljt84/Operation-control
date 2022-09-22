package com.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.DataTask;
import com.model.Promoter;
import com.model.Team;
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
	List<DataTask> findByDateAndPromoterTeam(LocalDate date,Team team);
	@Query(value = "select count(a)  from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and t2.id = :idTeam and  dt.\"date\"=:date and a.description like 'Pesquisa%' and a.situation = 'completa'",nativeQuery = true)
	Integer getCountActivityCompleteByTeam(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);
	@Query(value = "select count(a)  from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and t2.id = :idTeam and  dt.\"date\"=:date and a.description like 'Pesquisa%' and a.situation = 'sem historico'",nativeQuery = true)
	Integer getCountActivityMissingByTeam(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);
	@Query(value = "select distinct count(a) from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, brand b , shop s "
			+ "where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and b.id = a.brand_id and s.id = t.shop_id "
			+ "and b.id = :idBrand and a.\"start\" >= :initialDate and  a.\"start\"<= :finalDate  and a.description like 'Pesquisa%' and a.situation = 'completa' ",nativeQuery = true)
	Integer getCountActivityCompleteByBrand(@Param(value ="initialDate")  LocalDate initialDate,@Param(value ="finalDate") LocalDate finalDate , @Param(value ="idBrand") Long idBrand);
	@Query(value = "select count(a) from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, brand b , shop s "
			+ "where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and b.id = a.brand_id and s.id = t.shop_id "
			+ "and b.id = :idBrand and dt.\"date\" = :date and a.description like 'Pesquisa%' and a.situation = 'sem historico' and t.\"type\" <> 'Remanejada' and dt.situation = 'NÃO REALIZADO'",nativeQuery = true)
	Integer getCountActivityMissingByBrand(@Param(value ="date")  LocalDate date , @Param(value ="idBrand") Long idBrand);
	@Query(value = "select distinct count(a) from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, brand b , shop s "
			+ "where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and b.id = a.brand_id and s.id = t.shop_id "
			+ "and b.id = :idBrand and dt.\"date\" >= :initialDate and  dt.\"date\" <= :finalDate and a.description like 'Pesquisa%' and a.situation = 'sem historico' and t.\"type\" <> 'Remanejada' and  dt.situation <> 'CANCELADO'",nativeQuery = true)
	Integer getCountActivityMissingBetweenDateByBrand(@Param(value ="initialDate")  LocalDate initialDate , @Param(value ="finalDate")  LocalDate finalDate , @Param(value ="idBrand") Long idBrand);
	@Query(value = "select count(a) from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, brand b , shop s "
			+ "where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t2.id = dt.team_id and t.id = ta.task_id and ta.activities_id = a.id and b.id = a.brand_id and s.id = t.shop_id "
			+ "and b.id = :idBrand and dt.\"date\" = :date and a.description like 'Pesquisa%' and a.situation = 'sem historico' and t.\"type\" <> 'Remanejada' and dt.situation = 'INCOMPLETO'",nativeQuery = true)
	Integer getCountActivityDoingByBrand(@Param(value ="date")  LocalDate date , @Param(value ="idBrand") Long idBrand);
	@Query(value = "select p.\"name\", t2.name as team, count(a)  from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, promoter p  where dt.id = dtt.data_task_id and dt.promoter_id = p.id and dtt.tasks_id = t.id and  t.id = ta.task_id and ta.activities_id = a.id and  dt.\"date\"= :date and  p.team_id= :idTeam and t2.id = p.team_id and a.description like 'Pesquisa%' and a.situation = 'completa' group by p.\"name\", t2.name",nativeQuery = true)
    List<Object[]> getCountActivityCompleteByTeamAndPromoter(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);
	@Query(value = "select p.\"name\", t2.name as team, count(a)  from data_task dt, task t ,activity a,data_task_tasks dtt , team t2,  task_activities ta, promoter p  where dt.id = dtt.data_task_id and dt.promoter_id = p.id and dtt.tasks_id = t.id and  t.id = ta.task_id and ta.activities_id = a.id and  dt.\"date\"= :date and  p.team_id= :idTeam and t2.id = p.team_id and a.description like 'Pesquisa%' and a.situation = 'sem historico' group by p.\"name\", t2.name",nativeQuery = true)
    List<Object[]> getCountActivityMissingByTeamAndPromoter(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);
    @Query(value = "select dt.\"date\",s.\"name\" as shop ,b.\"name\" as brand, a.situation, a.\"_end\""
			+ " from data_task dt, task t ,activity a,shop s, brand b,data_task_tasks dtt , task_activities ta "
			+ "where dt.id = dtt.data_task_id and dtt.tasks_id = t.id and t.id = ta.task_id and ta.activities_id = a.id and t.shop_id = s.id and a.brand_id = b.id and dt.\"date\" <= :end and dt.\"date\" >= :start and dt.project = :project and a.description like 'Pesquisa%' and b.\"name\" is not null  order by b.\"name\" asc, s.\"name\"  asc "
			, nativeQuery = true)
    
	List<String[]> getRealizadovsProgramado(@Param(value = "start") LocalDate start,@Param(value = "end")  LocalDate end,@Param(value = "project") String project );
}
