package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
