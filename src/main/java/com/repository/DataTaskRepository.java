package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.DataTask;
import com.model.Promoter;

public interface DataTaskRepository extends JpaRepository<DataTask, Long>{

}
