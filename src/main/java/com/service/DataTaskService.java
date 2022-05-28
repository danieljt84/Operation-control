package com.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.dto.CargaHorariaDTO;
import com.model.Activity;
import com.model.DataTask;
import com.model.Holyday;
import com.model.Project;
import com.model.Promoter;
import com.model.Task;
import com.repository.DataTaskRepository;
import com.util.AjudaDeCusto;
import com.util.RelacaoDias;

@Service
public class DataTaskService {

	@Autowired
	DataTaskRepository dataTaskRepository;
	@Autowired
	HolydayService holydayService;
	
	public List<CargaHorariaDTO> getReportDTOs(String start , String end,String project){
		List<CargaHorariaDTO> dtos = new ArrayList();
		for(Object[] object : convertToReport(start, end,project)) {
			CargaHorariaDTO dto = new CargaHorariaDTO((String) object[0],(String) object[1],(String) object[2],
					(String) object[3],(String) object[4]);
			dtos.add(dto);
		}
		return dtos;
	}

	public List<DataTask> findByShopAndGradeAndSituationEqualsIncompleto(String shop, String grade) throws Exception {
		try {
			return dataTaskRepository.findByShopAndGradeAndSituationEqualsIncompleto(grade, shop);
		} catch (Exception e) {
			throw new Exception("ERRO NO CARREGAMENTO DA LISTA");
		}
	}

	public void generateDurationDataTask(DataTask dataTask) {
		List<LocalDateTime> starts = filterStart(dataTask);
		List<LocalDateTime> ends = filterEnds(dataTask);
		dataTask.setDuration(subtractDateToDuration(getMostNewDate(starts), getMostOldDate(ends)));
	}

	public List<Object[]> convertToReport(String _start, String _end,String project) {
		LocalDate start = LocalDate.parse(_start, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate end = LocalDate.parse(_end, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		List<DataTask> datas = dataTaskRepository.findByDateBetweenAndProject(start, end,project);
		List<Object[]> objects = new ArrayList<Object[]>();

		for (DataTask dataTask : datas) {
			Object[] arr = new Object[5];
			arr[0] = dataTask.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			arr[1] = dataTask.getTeam().getName();
			arr[2] = dataTask.getPromoter().getName();
			arr[3] = dataTask.getSituation();
			/*
			 * List<LocalDateTime> starts = filterStart(dataTask); List<LocalDateTime> ends
			 * = filterEnds(dataTask); arr[4] = subtractDate(getMostNewDate(starts),
			 * getMostOldDate(ends));
			 */
			if (dataTask.getDuration() != null) {
				arr[4] = dataTask.getDuration().toHours() + ":" + dataTask.getDuration().toMinutesPart() + ":"
						+ dataTask.getDuration().toSecondsPart();
			} else {
				arr[4] = null;
			}
			objects.add(arr);
		}
		return objects;
	}

	public List<AjudaDeCusto> convertToReportAjudaDeCusto(String _start, String _end) {
		LocalDate start = LocalDate.parse(_start, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate end = LocalDate.parse(_end, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		List<DataTask> datas = dataTaskRepository.findByDateBetween(start, end);
		datas = filterWithOutASunday(datas);
		List<Object[]> objects = new ArrayList<Object[]>();
		List<Promoter> promoters = datas.stream().map(data -> data.getPromoter()).distinct()
				.collect(Collectors.toList());
		List<Holyday> holydays = holydayService.getHolydaysBetween(start, end);
		List<AjudaDeCusto> ajudaDeCustos = new ArrayList<AjudaDeCusto>();
		long workingDays = generateWorkingsDays(start, end);
		for (Promoter promoter : promoters) {
			Object[] arr = new Object[11];
			List<DataTask> promoter_DataTasks = datas.stream()
					.filter(dataTask -> dataTask.getPromoter().getId() == promoter.getId())
					.collect(Collectors.toList());
			RelacaoDias relacaoDias = generateRelacaoDias(promoter_DataTasks);
			AjudaDeCusto ajudaDeCusto = new AjudaDeCusto();
			ajudaDeCusto.setPromoter(promoter);
			ajudaDeCusto.setRelacaoDias(relacaoDias);
			ajudaDeCusto.setDiasUteis(generateWorkingsDaysWithOutHolidays(workingDays, holydays, promoter.getTeam().getProject()));
			
			ajudaDeCusto.setDiasDescontoVA(countDescontoVA(promoter_DataTasks));
			ajudaDeCustos.add(ajudaDeCusto);
		}
		return ajudaDeCustos;
	}

	public int countDescontoVA(List<DataTask> datas) {
		int count = 0;
		for (DataTask data : datas) {
			if (data.getDuration() != null) {
				if (data.getDuration().toHours() <= 4l && data.getDuration().toMinutesPart() <= 30l) {
					count++;
				}
			}
		}
		return count;
	}

	private List<DataTask> filterWithOutASunday(List<DataTask> datas) {
		return datas.stream().filter(data -> !data.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY))
				.collect(Collectors.toList());
	}

	private int generateWorkingsDaysWithOutHolidays(long workingDays, List<Holyday> holydays, Project project) {
		int countHolydays = 0;
		for (Holyday _holyday : holydays) {
			for (Project _project : _holyday.getProjects()) {
				if (_project.equals(project))
					countHolydays++;
			}
		}
		return(int) workingDays - countHolydays;
	}

	private long generateWorkingsDays(LocalDate start, LocalDate end) {
		return (ChronoUnit.DAYS.between(start, end) + 1) - countSundays(start, end);
	}

	private long countSundays(LocalDate start, LocalDate end) {
		LocalDate currentDay = start;
		long count = 0;
		while (currentDay.isBefore(end) || currentDay.isEqual(end)) {
			if (currentDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				count++;
			}
			currentDay = currentDay.plusDays(1L);
		}
		return count;
	}

	public RelacaoDias generateRelacaoDias(List<DataTask> datas) {
		RelacaoDias relacaoDias = new RelacaoDias();
		List<DataTask> monday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 1).collect(Collectors.toList());
		List<DataTask> tuesday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 2).collect(Collectors.toList());
		List<DataTask> wednesday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 3).collect(Collectors.toList());
		List<DataTask> thursday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 4).collect(Collectors.toList());
		List<DataTask> friday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 5).collect(Collectors.toList());
		List<DataTask> saturday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 6).collect(Collectors.toList());

		relacaoDias.setSegundasFeitas(monday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size());
		relacaoDias.setTercasFeitas(tuesday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size());
		relacaoDias.setQuartasFeitas(wednesday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size());
		relacaoDias.setQuintasFeitas(thursday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size());
		relacaoDias.setSextasFeitas(friday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size());
		relacaoDias.setSabadosFeitas(saturday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size());
		relacaoDias.setDiasCompletos(datas.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size());
		return relacaoDias;
	}

	public String subtractDate(LocalDateTime first, LocalDateTime second) {
		try {
			Duration duration = Duration.between(first, second);
			long hours = duration.toHours();
			int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
			int seconds = (int) (duration.getSeconds() % 60);
			return hours + ":" + minutes + ":" + seconds;
		} catch (Exception e) {
			return null;
		}
	}

	public Duration subtractDateToDuration(LocalDateTime first, LocalDateTime second) {
		try {
			Duration duration = Duration.between(first, second);
			return duration;
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