package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Team findByName(String name);
}
