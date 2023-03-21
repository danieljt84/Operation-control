package com.repository.operation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
	Optional<DataTask> findByPromoterAndDate(Promoter promoter, LocalDate date);
	
	@Query(value = "select dt.id from operation.data_task dt where (COALESCE(:ids,null) is not null and dt.id IN (:ids))",nativeQuery = true)
	List<Object> teste(@Param(value ="ids") List<Long> ids);
	
	@Query(value = "select count(a) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id where t2.id = :idTeam and  dt.\"date\"=:date and a.description  like 'PESQUISA%' and ta.situation  = 'completa';",nativeQuery = true)
	Integer getCountActivityCompleteByTeam(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);

	@Query(value = "select count(a) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id where t2.id = :idTeam and  dt.\"date\"=:date and a.description  like 'PESQUISA%' and ta.situation  = 'sem historico';",nativeQuery = true)
	Integer getCountActivityMissingByTeam(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);
	
	@Query(value = "select count(a) from data_task dt "
			+ "inner join data_task_tasks dtt on dtt.data_task_id = dt.id "
			+ "inner join task t on dtt.tasks_id = t.id "
			+ "inner join task_activity ta on ta.task_id = t.id "
			+ "inner join team t2 on dt.team_id = t2.id "
			+ "inner join activity a on a.id = ta.activity_id "
			+ "inner join brand b on b.id = a.brand_id "
			+ "inner join shop s on s.id = t.shop_id "
			+ "inner join project pr on pr.id = dt.project_id "
			+ "where b.id in :idsBrand "
			+ "and dt.\"date\" >= :initialDate "
			+ "and  dt.\"date\" <= :finalDate"
			+ " and a.description like 'PESQUISA%' "
			+ "and ta.situation = 'completa' "
			+ "and t.\"type\" <> 'Remanejada' "
			+ "and  dt.situation <> 'CANCELADO' "
			+ "and (CASE WHEN COALESCE(:idsProject,null) is not null THEN pr.id IN (:idsProject) ELSE true END) ",nativeQuery = true)
	Integer getCountActivityCompleteByBrand(@Param(value ="initialDate")  LocalDate initialDate,@Param(value ="finalDate") LocalDate finalDate, @Param(value ="idsBrand") List<Long> idsBrand, @Param(value ="idsProject") List<Long> idsProject);
	
	@Query(value = "select count(a) from data_task dt "
			+ "inner join data_task_tasks dtt on dtt.data_task_id = dt.id "
			+ "inner join task t on dtt.tasks_id = t.id "
			+ "inner join task_activity ta on ta.task_id = t.id "
			+ "inner join team t2 on dt.team_id = t2.id "
			+ "inner join activity a on a.id = ta.activity_id "
			+ "inner join brand b on b.id = a.brand_id "
			+ "inner join shop s on s.id = t.shop_id "
			+ "inner join project pr on pr.id = dt.project_id "
			+ "where b.id in :idsBrand "
			+ "and dt.\"date\" >= :initialDate "
			+ "and  dt.\"date\" <= :finalDate"
			+ " and a.description like 'PESQUISA%' "
			+ "and ta.situation = 'completa' "
			+ "and t.\"type\" <> 'Remanejada' "
			+ "and  dt.situation <> 'CANCELADO' "
			+ "and (CASE WHEN COALESCE(:idsProject,null) is not null THEN pr.id IN (:idsProject) ELSE true END) "
			+ "and (CASE WHEN COALESCE(:idsShop,null) is not null THEN s.id IN (:idsShop) ELSE true END)",nativeQuery = true)
	Integer getCountActivityCompleteByBrand(@Param(value ="initialDate")  LocalDate initialDate,@Param(value ="finalDate") LocalDate finalDate, @Param(value ="idsBrand") List<Long> idsBrand, @Param(value ="idsProject") List<Long> idsProject, @Param(value ="idsShop") List<Long> idsShop);
	
	@Query(value = "select count(a) from data_task dt "
			+ "inner join data_task_tasks dtt on dtt.data_task_id = dt.id "
			+ "inner join task t on dtt.tasks_id = t.id "
			+ "inner join task_activity ta on ta.task_id = t.id "
			+ "inner join team t2 on dt.team_id = t2.id "
			+ "inner join activity a on a.id = ta.activity_id "
			+ "inner join brand b on b.id = a.brand_id "
			+ "inner join shop s on s.id = t.shop_id "
			+ "inner join project pr on pr.id = dt.project_id "
			+ "where b.id in :idsBrand "
			+ "and dt.\"date\" >= :initialDate "
			+ "and  dt.\"date\" <= :finalDate"
			+ " and a.description like 'PESQUISA%' "
			+ "and ta.situation = 'completa' "
			+ "and t.\"type\" <> 'Remanejada' "
			+ "and  dt.situation <> 'CANCELADO' "
			+ "and (CASE WHEN COALESCE(:idsProject,null) is not null THEN pr.id IN (:idsProject) ELSE true END) ;",nativeQuery = true)
	
   Integer getCountActivityDoingByBrand(@Param(value ="initialDate")  LocalDate initialDate,@Param(value ="finalDate") LocalDate finalDate, @Param(value ="idsBrand") List<Long> idsBrand, @Param(value ="idsProject") List<Long> idsProject);

	@Query(value = "select count(a) from data_task dt "
			+ "inner join data_task_tasks dtt on dtt.data_task_id = dt.id "
			+ "inner join task t on dtt.tasks_id = t.id "
			+ "inner join task_activity ta on ta.task_id = t.id "
			+ "inner join team t2 on dt.team_id = t2.id "
			+ "inner join activity a on a.id = ta.activity_id "
			+ "inner join brand b on b.id = a.brand_id "
			+ "inner join shop s on s.id = t.shop_id "
			+ "inner join project pr on pr.id = dt.project_id "
			+ "where b.id in :idsBrand "
			+ "and dt.\"date\" >= :initialDate "
			+ "and  dt.\"date\" <= :finalDate"
			+ " and a.description like 'PESQUISA%' "
			+ " and ta.situation  = 'sem historico' "
			+ "and t.\"type\"  <> 'Remanejada' "
			+ "and  dt.situation <> 'CANCELADO' "
			+ "and (CASE WHEN COALESCE(:idsProject,null) is not null THEN pr.id IN (:idsProject) ELSE true END) ;",nativeQuery = true)
	Integer getCountActivityMissingByBrand(@Param(value ="initialDate")  LocalDate initialDate,@Param(value ="finalDate") LocalDate finalDate, @Param(value ="idsBrand") List<Long> idsBrand, @Param(value ="idsProject") List<Long> idsProject);
	
	@Query(value="select count(a) from data_task dt "
			+ "inner join data_task_tasks dtt on dtt.data_task_id = dt.id "
			+ "inner join task t on dtt.tasks_id = t.id "
			+ "inner join task_activity ta on ta.task_id = t.id "
			+ "inner join team t2 on dt.team_id = t2.id "
			+ "inner join activity a on a.id = ta.activity_id "
			+ "inner join brand b on b.id = a.brand_id "
			+ "inner join shop s on s.id = t.shop_id "
			+ "inner join project pr on pr.id = dt.project_id "
			+ "where b.id in :idsBrand "
			+ "and dt.\"date\" >= :initialDate "
			+ "and  dt.\"date\" <= :finalDate"
			+ " and a.description like 'PESQUISA%' "
			+ "and ta.situation = 'completa' "
			+ "and t.\"type\" <> 'Remanejada' "
			+ "and  dt.situation <> 'CANCELADO' "
			+ "and (CASE WHEN COALESCE(:idsProject,null) is not null THEN pr.id IN (:idsProject) ELSE true END) "
			+ "and (CASE WHEN COALESCE(:idsShop,null) is not null THEN s.id IN (:idsShop) ELSE true END) ",nativeQuery = true)
	Integer getCountActivityCompleteBetweenDateByBrand(@Param(value ="initialDate")  LocalDate initialDate , @Param(value ="finalDate")  LocalDate finalDate ,  @Param(value ="idsBrand") List<Long> idsBrand, @Param(value ="idsProject") List<Long> idsProject, @Param(value ="idsShop") List<Long> idsShop);
	
	
	@Query(value = "select count(a) from data_task dt "
			+ "inner join data_task_tasks dtt on dtt.data_task_id = dt.id "
			+ "inner join task t on dtt.tasks_id = t.id "
			+ "inner join task_activity ta on ta.task_id = t.id "
			+ "inner join team t2 on dt.team_id = t2.id "
			+ "inner join activity a on a.id = ta.activity_id "
			+ "inner join brand b on b.id = a.brand_id "
			+ "inner join shop s on s.id = t.shop_id "
			+ "inner join project pr on pr.id = dt.project_id "
			+ "where b.id in :idsBrand "
			+ "and dt.\"date\" >= :initialDate "
			+ "and  dt.\"date\" <= :finalDate"
			+ " and a.description like 'PESQUISA%' "
			+ " and ta.situation  = 'sem historico' "
			+ "and t.\"type\"  <> 'Remanejada' "
			+ "and  dt.situation <> 'CANCELADO' "
			+ "and (CASE WHEN COALESCE(:idsProject,null) is not null THEN pr.id IN (:idsProject) ELSE true END) "
			+ "and (CASE WHEN COALESCE(:idsShop,null) is not null THEN s.id IN (:idsShop) ELSE true END)",nativeQuery = true)
	Integer getCountActivityMissingBetweenDateByBrand(@Param(value ="initialDate")  LocalDate initialDate , @Param(value ="finalDate")  LocalDate finalDate ,  @Param(value ="idsBrand") List<Long> idsBrand, @Param(value ="idsProject") List<Long> idsProject, @Param(value ="idsShop") List<Long> idsShop);


	@Query(value = "select p.\"name\", t2.name as team, count(a) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join promoter p on p.id = dt.promoter_id where dt.\"date\"= :date and  p.team_id= :idTeam and a.description like 'PESQUISA%' and ta.situation = 'completa' and t.\"type\" <> 'Remanejada' and dt.situation = 'INCOMPLETO' group by p.\"name\", t2.name",nativeQuery = true)
    List<Object[]> getCountActivityCompleteByTeamAndPromoter(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);

    @Query(value = "select p.\"name\", t2.name as team, count(a) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join promoter p on p.id = dt.promoter_id where dt.\"date\"= :date and  p.team_id= :idTeam and a.description like 'PESQUISA%' and ta.situation = 'sem historico' and t.\"type\" <> 'Remanejada' and dt.situation = 'INCOMPLETO' group by p.\"name\", t2.name",nativeQuery = true)
   List<Object[]> getCountActivityMissingByTeamAndPromoter(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);

   @Query(value = "select dt.\"date\",s.\"name\" as shop ,b.\"name\" as brand, ta.situation, ta.\"_end\"from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join shop s on s.id = t.shop_id inner join brand b on b.id = a.brand_id where dt.\"date\" <= :end and dt.\"date\" >= :start and dt.project = :project and a.description like 'PESQUISA%' and b.\"name\" is not null  order by b.\"name\" asc, s.\"name\"  asc"
			, nativeQuery = true)
	List<String[]> getRealizadovsProgramado(@Param(value = "start") LocalDate start,@Param(value = "end")  LocalDate end,@Param(value = "project") String project );

	@Query(value = "select distinct  dt.\"date\",pr.name, b.\"name\" as brand, s.\"name\" as shop , case ta.situation when 'completa' then 'COMPLETA' when 'sem historico' then 'NÃO REALIZADO' when 'cancelada' then 'CANCELADA' END AS status \r\n"
			+ "from operation.data_task dt\r\n"
			+ "inner join operation.data_task_tasks dtt on dtt.data_task_id = dt.id \r\n"
			+ "inner join operation.task t on dtt.tasks_id = t.id\r\n"
			+ "inner join operation.task_activity ta on ta.task_id = t.id\r\n"
			+ "inner join operation.team t2 on dt.team_id = t2.id\r\n"
			+ "inner join operation.activity a on a.id = ta.activity_id\r\n"
			+ "inner join operation.shop s on s.id = t.shop_id \r\n"
			+ "inner join operation.project pr on pr.id = dt.project_id \r\n"
			+ "inner join operation.brand b on  b.id = a.brand_id \r\n"
			+ "where b.id in :idsBrand  and dt.\"date\" >= :initialDate and dt.\"date\" <= :finalDate \r\n"
			+ "and (CASE WHEN COALESCE(:idsProject,null) is not null THEN pr.id IN (:idsProject) ELSE true END) "
			+ "and (CASE WHEN COALESCE(:idsShop,null) is not null THEN s.id IN (:idsShop) ELSE true END)", nativeQuery = true)
	List<String[]> getPrevistoRealizadoToReport(@Param(value = "initialDate") LocalDate initialDate,@Param(value = "finalDate")  LocalDate finalDate, @Param(value = "idsBrand") List<Long> idsBrand,@Param(value = "idsShop")List<Long> namesShop, @Param(value ="idsProject") List<Long> idsProject);
}
