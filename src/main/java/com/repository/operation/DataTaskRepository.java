package com.repository.operation;

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
	Optional<DataTask> findByPromoterAndDate(Promoter promoter, LocalDate date);
	
	@Query(value = "select count(a) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id where t2.id = :idTeam and  dt.\"date\"=:date and a.description  like 'PESQUISA%' and ta.situation  = 'completa';",nativeQuery = true)
	Integer getCountActivityCompleteByTeam(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);

	@Query(value = "select count(a) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id where t2.id = :idTeam and  dt.\"date\"=:date and a.description  like 'PESQUISA%' and ta.situation  = 'sem historico';",nativeQuery = true)
	Integer getCountActivityMissingByTeam(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);
	
	@Query(value = "select distinct count(ta) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join brand b on a.brand_id = b.id inner join shop s on s.id = t.shop_id where dt.\"date\" >= :initialDate and b.id = :idBrand and  dt.\"date\" <= :finalDate and a.description like 'PESQUISA%' and ta.situation  = 'completa' and t.\"type\"  <> 'Remanejada' and  dt.situation <> 'CANCELADO';",nativeQuery = true)
	Integer getCountActivityCompleteByBrand(@Param(value ="initialDate")  LocalDate initialDate,@Param(value ="finalDate") LocalDate finalDate, @Param(value ="idBrand") Long idBrand);

	@Query(value = "select distinct count(ta) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join brand b on a.brand_id = b.id inner join shop s on s.id = t.shop_id where dt.\"date\" >= :initialDate and b.id = :idBrand and  dt.\"date\" <= :finalDate and a.description like 'PESQUISA%' and ta.situation  = 'sem historico' and t.\"type\"  <> 'Remanejada' and  dt.situation <> 'CANCELADO';",nativeQuery = true)
	Integer getCountActivityMissingByBrand(@Param(value ="initialDate")  LocalDate initialDate,@Param(value ="finalDate") LocalDate finalDate, @Param(value ="idBrand") Long idBrand);
	
	@Query(value = "select distinct count(ta) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id  inner join activity a on a.id = ta.activity_id inner join brand b on b.id = a.brand_id inner join shop s on s.id = t.shop_id where  dt.\"date\" >= :initialDate and b.id = :idBrand  and  dt.\"date\" <= :finalDate and a.description like 'PESQUISA%' and ta.situation = 'sem historico' and t.\"type\"  <> 'Remanejada' and  dt.situation <> 'CANCELADO';",nativeQuery = true)
	Integer getCountActivityMissingBetweenDateByBrand(@Param(value ="initialDate")  LocalDate initialDate , @Param(value ="finalDate")  LocalDate finalDate , @Param(value ="idBrand") Long idBrand);

	@Query(value = "select distinct count(ta) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id  inner join activity a on a.id = ta.activity_id inner join brand b on b.id = a.brand_id inner join shop s on s.id = t.shop_id where  dt.\"date\" >= :initialDate and b.id = :idBrand  and  dt.\"date\" <= :finalDate and a.description like 'PESQUISA%' and ta.situation = 'completa' and t.\"type\"  <> 'Remanejada' and  dt.situation <> 'CANCELADO';",nativeQuery = true)
	Integer getCountActivityCompleteBetweenDateByBrand(@Param(value ="initialDate")  LocalDate initialDate , @Param(value ="finalDate")  LocalDate finalDate , @Param(value ="idBrand") Long idBrand);
	
	@Query(value = "select distinct count(ta) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id  inner join activity a on a.id = ta.activity_id inner join brand b on b.id = a.brand_id inner join shop s on s.id = t.shop_id where b.id = :idBrand and dt.\"date\" = :date and a.description like 'PESQUISA%' and ta.situation = 'sem historico' and t.\"type\" <> 'Remanejada' and dt.situation = 'INCOMPLETO'",nativeQuery = true)
	Integer getCountActivityDoingByBrand(@Param(value ="date")  LocalDate date , @Param(value ="idBrand") Long idBrand);

	@Query(value = "select p.\"name\", t2.name as team, count(a) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join promoter p on p.id = dt.promoter_id where dt.\"date\"= :date and  p.team_id= :idTeam and a.description like 'PESQUISA%' and ta.situation = 'completa' and t.\"type\" <> 'Remanejada' and dt.situation = 'INCOMPLETO' group by p.\"name\", t2.name",nativeQuery = true)
    List<Object[]> getCountActivityCompleteByTeamAndPromoter(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);

    @Query(value = "select p.\"name\", t2.name as team, count(a) from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join promoter p on p.id = dt.promoter_id where dt.\"date\"= :date and  p.team_id= :idTeam and a.description like 'PESQUISA%' and ta.situation = 'sem historico' and t.\"type\" <> 'Remanejada' and dt.situation = 'INCOMPLETO' group by p.\"name\", t2.name",nativeQuery = true)
   List<Object[]> getCountActivityMissingByTeamAndPromoter(@Param(value ="date")  LocalDate date, @Param(value ="idTeam") Long idTeam);

   @Query(value = "select dt.\"date\",s.\"name\" as shop ,b.\"name\" as brand, ta.situation, ta.\"_end\"from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join shop s on s.id = t.shop_id inner join brand b on b.id = a.brand_id where dt.\"date\" <= :end and dt.\"date\" >= :start and dt.project = :project and a.description like 'PESQUISA%' and b.\"name\" is not null  order by b.\"name\" asc, s.\"name\"  asc"
			, nativeQuery = true)
	List<String[]> getRealizadovsProgramado(@Param(value = "start") LocalDate start,@Param(value = "end")  LocalDate end,@Param(value = "project") String project );

	@Query(value = "select distinct  dt.\"date\", s.\"name\" , case ta.situation when 'completa' then 'COMPLETA' when 'sem historico' then 'NÃO REALIZADO' END AS status from data_task dt inner join data_task_tasks dtt on dtt.data_task_id = dt.id inner join task t on dtt.tasks_id = t.id inner join task_activity ta on ta.task_id = t.id inner join team t2 on dt.team_id = t2.id inner join activity a on a.id = ta.activity_id inner join shop s on s.id = t.shop_id where a.brand_id  = :idBrand  and dt.\"date\" >= :initialDate and dt.\"date\" <= :finalDate", nativeQuery = true)
	List<String[]> getPrevistoRealizadoToReport(@Param(value = "initialDate") LocalDate start,@Param(value = "finalDate")  LocalDate end, @Param(value = "idBrand") Long idBrand);
}
