package com.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.Promoter;

@Repository
public interface PromoterRepository extends JpaRepository<Promoter, Long> {
	
	@Query(value = "select p from Promoter p where upper(p.name) like CONCAT('%',upper(:name),'%')")
	Promoter findByName(@Param(value = "name") String name);
	@Query(value = "select p from Promoter p, Team t where p.team.id = t.id and t.name=:nameTeam")
	List<Promoter> findByNameTeam(@Param("nameTeam") String nameTeam);
	@Modifying
	@Query(value = "update Promoter p set p.status = 1 where p.id not in (:ids)")
	void updateStatusToInativo(@Param("ids") Set<Long> idPromoters);
}
