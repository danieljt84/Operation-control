package com.service.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Activity;
import com.model.Brand;
import com.model.Project;
import com.model.Task;
import com.repository.operation.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;
	
	
	public Task save(Task task) {
		return taskRepository.saveAndFlush(task);
	}
	public void saveAll(List<Task> tasks) {
		taskRepository.saveAll(tasks);
	}
	
	public Task get(Long id) {
		return taskRepository.getById(id);
	}
	
	public void deleteAllTasksWithOutDataTask() {
       List<Task> tasks = taskRepository.findWithOutDataTask();
       taskRepository.deleteAll(tasks);
	}

}
