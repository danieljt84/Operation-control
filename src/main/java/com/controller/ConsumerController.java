package com.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import com.model.Activity;
import com.model.DataTask;
import com.model.Project;
import com.model.Promoter;
import com.model.Task;
import com.repository.DataTaskRepository;
import com.repository.DataTaskRepositoryImp;
import com.repository.PromoterRepository;
import com.service.DataTaskService;
import com.service.ExcelService;
import com.service.api.ApiEmployeeService;
import com.service.api.ApiLaivonService;
import com.util.ProjectAdapter;

@Component
public class ConsumerController {

	ConfigurableApplicationContext context;
	@Autowired
	DataTaskRepositoryImp dataTaskRepositoryImp;
	@Autowired
	DataTaskRepository dataTaskRepository;
	@Autowired
	PromoterRepository promoterRepository;
	@Autowired
	DataTaskService dataTaskService;
	@Autowired
	ApiLaivonService apiService;
	@Autowired
	ApiEmployeeService apiEmployeeService;
	@Autowired
	ExcelService excelService;

	public ConsumerController(ConfigurableApplicationContext context) {
		this.context = context;
	}

	public void routine() {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.withDayOfMonth(1);
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		for (int i = 0; i <= daysBetween; i++) {
			for (ProjectAdapter project : ProjectAdapter.values()) {
				List<DataTask> datas = apiService.getResume(project.getDescription(), startDate.plusDays(i));
				System.out.println(i);
				for (DataTask data : datas) {
					eliminateChecksDataTaskAndSetDuration(data);
					calculateInfoDataTask(data);
					defSituationDataTask(data);
					dataTaskRepositoryImp.checkDataTask(data);
				}
			}
		}
	}

	public void createExcel(String start, String end) {
		excelService.createExcelAjudaDeCusto(start,end);
	}

	// Elimino as atividades decheckout da lista e somo a duração das atividades
	private  void eliminateChecksDataTaskAndSetDuration(DataTask dataTask) {
		for (Task task : dataTask.getTasks()) {
			List<Activity> filterActivities = task.getActivities().stream()
					.filter(activity -> !activity.getDescription().equals("Check Out")).collect(Collectors.toList());
			for (Activity activity : filterActivities) {
				activity.setDuration(subtractDateTime(activity.getStart(), activity.getEnd()));
			}
			task.setActivities(filterActivities);
			task.setDuration(
					sumTime(task.getActivities().stream().map(t -> t.getDuration()).collect(Collectors.toList())));
			calculateInfoTask(task);
			defSituationTask(task);
		}
	}

	// Após filtragem, calcula as atividades canceladas,realizadas e totalizada
	private void calculateInfoDataTask(DataTask dataTask) {
		int total = dataTask.getTasks().size();
		int done = dataTask.getTasks().stream().filter(task -> task.getSituation().equals("COMPLETO"))
				.collect(Collectors.toList()).size();
		int cancelled = dataTask.getTasks().stream().filter(task -> task.getSituation().equals("CANCELADA"))
				.collect(Collectors.toList()).size();
		int doing = dataTask.getTasks().stream()
				.filter(task -> task.getSituation().equals("EM CAMPO") || task.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size();
		dataTask.setTaskDone(done);
		dataTask.setTaskCanceled(cancelled);
		dataTask.setTaskDoing(doing);
		dataTask.setTaskTotal(total);
		dataTaskService.generateDurationDataTask(dataTask);
	}

	// Após filtragem, calcula as atividades canceladas,realizadas e totalizada
	private void calculateInfoTask(Task task) {
		task.setActivityTotal(task.getActivities().size());
		int done = task.getActivities().stream().filter(activity -> activity.getSituation().equals("completa"))
				.collect(Collectors.toList()).size();
		int missing = task.getActivities().stream().filter(activity -> activity.getSituation().equals("sem historico"))
				.collect(Collectors.toList()).size();
		task.setActivityDone(done);
		task.setActivityMissing(missing);
	}

	// Defino o status de toda a atividade
	private  void defSituationDataTask(DataTask dataTask) {
		if (dataTask.getTaskTotal() == dataTask.getTaskDone())
			dataTask.setSituation("COMPLETO");
		if (dataTask.getTaskDone() == 0)
			dataTask.setSituation("NÃO REALIZADO");
		if ((dataTask.getTaskTotal() > dataTask.getTaskDone() && dataTask.getTaskDone() >= 1)
				|| dataTask.getTaskTotal() == dataTask.getTaskDoing()
				|| (dataTask.getTaskDoing() + dataTask.getTaskCanceled()) == dataTask.getTaskTotal())
			dataTask.setSituation("INCOMPLETO");
		if (dataTask.getTaskCanceled() == dataTask.getTaskTotal())
			dataTask.setSituation("CANCELADO");
	}

	// Defino o status da tarefa
	private  void defSituationTask(Task task) {
		if (task.getSituation().equals("Cancelada")) {
			task.setSituation("CANCELADA");
			return;
		}
		if (task.getSituation().equals("Em Campo")) {
			task.setSituation("EM CAMPO");
			return;
		}
		if (task.getActivityTotal() == task.getActivityDone())
			task.setSituation("COMPLETO");
		if (task.getActivityTotal() > task.getActivityDone() && task.getActivityDone() >= 1)
			task.setSituation("INCOMPLETO");
		if (task.getActivityDone() == 0)
			task.setSituation("NÃO REALIZADO");
	}

	public  Duration subtractDateDuration(LocalDateTime first, LocalDateTime second) {
		try {
			Duration duration = Duration.between(first, second);
			long hours = duration.toHours();
			int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
			int seconds = (int) (duration.getSeconds() % 60);
			return duration;
		} catch (Exception e) {
			return null;
		}
	}

	public LocalTime subtractDateTime(LocalDateTime first, LocalDateTime second) {
		try {
			LocalTime tempDateTime = LocalTime.from(first);

			long hours = tempDateTime.until(second, ChronoUnit.HOURS);
			tempDateTime = tempDateTime.plusHours(hours);

			long minutes = tempDateTime.until(second, ChronoUnit.MINUTES);
			tempDateTime = tempDateTime.plusMinutes(minutes);

			long seconds = tempDateTime.until(second, ChronoUnit.SECONDS);

			return LocalTime.of((int) hours, (int) minutes, (int) seconds);
		} catch (Exception e) {
			return null;
		}
	}

	public LocalTime sumTime(List<LocalTime> datas) {
		try {
			LocalTime timeTotal = null;
			for (int i = 0; i < datas.size(); i++) {
				if (datas.get(i) != null) {
					if (timeTotal == null) {
						timeTotal = datas.get(i);
						continue;
					}
					timeTotal = timeTotal.plusHours(datas.get(i).getHour());
					timeTotal = timeTotal.plusMinutes(datas.get(i).getMinute());
					timeTotal = timeTotal.plusSeconds(datas.get(i).getMinute());
				}
			}
			return timeTotal;
		} catch (Exception e) {
			return null;
		}
	}

	public LocalDateTime getMostOldDate(List<LocalDateTime> datas) {
		LocalDateTime old = null;
		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i) != null) {
				if (old == null) {
					old = datas.get(i);
					continue;
				}
				if (!old.isAfter(datas.get(i))) {
					old = datas.get(i);
				}
			}

		}
		return old;
	}

	public LocalDateTime getMostNewDate(List<LocalDateTime> datas) {
		LocalDateTime _new = null;
		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i) != null) {
				if (_new == null) {
					_new = datas.get(i);
					continue;
				}
				if (!_new.isBefore(datas.get(i))) {
					_new = datas.get(i);
				}
			}

		}
		return _new;
	}

	public List<LocalDateTime> filterStart(DataTask dataTask) {
		List<LocalDateTime> starts = new ArrayList<LocalDateTime>();

		for (Task task : dataTask.getTasks()) {
			for (Activity activity : task.getActivities()) {
				starts.add(activity.getStart());
			}
		}
		return starts;
	}

	public List<LocalDateTime> filterEnds(DataTask dataTask) {
		List<LocalDateTime> ends = new ArrayList<LocalDateTime>();

		for (Task task : dataTask.getTasks()) {
			for (Activity activity : task.getActivities()) {
				ends.add(activity.getEnd());
			}
		}
		return ends;
	}
}
