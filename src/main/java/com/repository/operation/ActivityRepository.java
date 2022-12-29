package com.repository.operation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Activity;
import com.model.Shop;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
	
	Activity findByDescriptionOrIdSystem(String description, Long idSystem);
}
