package com.repository.operation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	@Query(value = "select t from Task t where t.id not in (select dts.id from DataTask dt join dt.tasks dts)")
	List<Task> findWithOutDataTask();

}
