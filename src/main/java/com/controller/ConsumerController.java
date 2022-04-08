package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import com.model.Activity;
import com.model.DataTask;
import com.model.Task;
import com.repository.DataTaskRepository;
import com.repository.DataTaskRepositoryImp;
import com.service.ApiService;
@Component
public class ConsumerController {

	ConfigurableApplicationContext context;
	@Autowired
	DataTaskRepositoryImp dataTaskRepositoryImp;
	@Autowired
	DataTaskRepository dataTaskRepository;

	public ConsumerController(ConfigurableApplicationContext context) {
		this.context = context;
	}

    @Transactional(noRollbackFor = UnexpectedRollbackException.class)
	public void routine() {
		ApiService apiService = context.getBean(ApiService.class);
		List<DataTask> datas = apiService.getResume(context);
		for (DataTask data : datas) {
			eliminateChecks(data);
			calculateInfoDataTask(data);
		}
		for(DataTask dataTask:datas) {
			dataTaskRepositoryImp.checkDataTask(dataTask);
		}
	}

	// Elimino as atividades de checkin e checkout da lista
	private static void eliminateChecks(DataTask dataTask) {
		for (Task task : dataTask.getTasks()) {
			List<Activity> filterActivities = task.getActivities().stream()
					.filter(activity -> !activity.getDescription().equals("Check Out")
							&& !activity.getDescription().equals("Check In"))
					.collect(Collectors.toList());
			task.setActivities(filterActivities);
			calculateInfoTask(task);
		}
	}

	// Após filtragem, calcula as atividades canceladas,realizadas e totalizada
	private static void calculateInfoDataTask(DataTask dataTask) {
		dataTask.setTaskTotal(dataTask.getTaskTotal() - 1);
		int done = dataTask.getTasks().stream().filter(task -> task.getSituation().equals("Retornada de Campo"))
				.collect(Collectors.toList()).size();
		int cancelled = dataTask.getTasks().stream().filter(task -> task.getSituation().equals("Cancelada"))
				.collect(Collectors.toList()).size();
		dataTask.setTaskDone(done);
		dataTask.setTaskCanceled(cancelled);

	}

	// Após filtragem, calcula as atividades canceladas,realizadas e totalizada
	private static void calculateInfoTask(Task task) {
		task.setActivityTotal(task.getActivities().size());
		int done = task.getActivities().stream().filter(activity -> activity.getSituation().equals("completa"))
				.collect(Collectors.toList()).size();
		int missing = task.getActivities().stream().filter(activity -> activity.getSituation().equals("sem historico"))
				.collect(Collectors.toList()).size();
		task.setActivityDone(done);
		task.setActivityMissing(missing);
	}

}
