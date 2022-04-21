package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.Promoter;

@Repository
public interface PromoterRepository extends JpaRepository<Promoter, Long> {
	
	Promoter findByName(String name);
	@Query(value = "select p from Promoter p, Team t where p.team.id = t.id and t.name=:nameTeam")
	List<Promoter> findByNameTeam(@Param("nameTeam") String nameTeam);
}
