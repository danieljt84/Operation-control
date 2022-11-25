package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
	
	Activity findByDescriptionOrIdSystem(String description, Long idSystem);

}
