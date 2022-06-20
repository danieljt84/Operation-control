package com.service;

import java.io.Console;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

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
import com.util.Status;
import com.util.model.AjudaDeCusto;
import com.util.model.InfoPercentual;
import com.util.model.RelacaoDias;

@Service
public class DataTaskService {

	@Autowired
	DataTaskRepository dataTaskRepository;
	@Autowired
	HolydayService holydayService;

	// Verifica se existe no banco de dados, caso não, salva o mesmo
	public void checkAndSaveDataTask(DataTask dataTask) {
		dataTaskRepository.findIdDataTask(dataTask).ifPresentOrElse(id -> {
			dataTask.setId(id);
			dataTaskRepository.saveAndFlush(dataTask);
		}, () -> dataTaskRepository.saveAndFlush(dataTask));
	}

	// Transforma o objeto para CargaHorariaDTO
	public List<CargaHorariaDTO> getReportDTOs(String start, String end, String project) {
		List<CargaHorariaDTO> dtos = new ArrayList<CargaHorariaDTO>();
		for (Object[] object : convertToReportByProject(start, end, project)) {
			CargaHorariaDTO dto = new CargaHorariaDTO((String) object[0], (String) object[1], (String) object[2],
					(String) object[3], (String) object[4]);
			dtos.add(dto);
		}
		return dtos;
	}

	// busca no banco de dados por grade dos promotores e situacao incompleto
	public List<DataTask> findByShopAndGradeAndSituationEqualsIncompleto(String shop, String grade) throws Exception {
		try {
			return dataTaskRepository.findByShopAndGradeAndSituationEqualsIncompleto(grade, shop);
		} catch (Exception e) {
			throw new Exception("ERRO NO CARREGAMENTO DA LISTA");
		}
	}

	public void generatePercentual(String date) {
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<DataTask> datas = dataTaskRepository.findByDate(localDate);
		for(DataTask data : datas) {
			InfoPercentual infoPercentual = new InfoPercentual();
			data.getTasks().stream().map(t ->t.getActivities()).findAny().ifPresentOrElse(list-> {
				infoPercentual.setCountActivities((long) list.size());
				
			},
					() -> System.out.println("u"));
			infoPercentual.setCountActivities(null);
		}
	}

	// gera a duracao total da DataTask
	public void generateDurationDataTask(DataTask dataTask) {
		List<LocalDateTime> starts = filterStart(dataTask);
		List<LocalDateTime> ends = filterEnds(dataTask);
		dataTask.setDuration(subtractDateToDuration(getMostNewDate(starts), getMostOldDate(ends)));
	}

	// Transforma obejos para o envio de relatorio, busca por data e Projeto
	public List<Object[]> convertToReportByProject(String _start, String _end, String project) {
		LocalDate start = LocalDate.parse(_start, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate end = LocalDate.parse(_end, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		List<DataTask> datas = dataTaskRepository.findByDateBetweenAndProject(start, end, project);
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

	// Transforma obejos para o envio de relatorio, busca por data
	public List<Object[]> convertToReport(String _start, String _end) {
		LocalDate start = LocalDate.parse(_start, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate end = LocalDate.parse(_end, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		List<DataTask> datas = dataTaskRepository.findByDateBetweenAndPromoterStatus(start, end, Status.ATIVO);
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

	// Transforma obejos para o envio de relatorio da ajuda de custo, busca por data
	// e Projeto
	public List<AjudaDeCusto> convertToReportAjudaDeCusto(String _start, String _end) {
		LocalDate start = LocalDate.parse(_start, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate end = LocalDate.parse(_end, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		List<DataTask> datas = dataTaskRepository.findByDateBetweenAndPromoterStatus(start, end, Status.ATIVO);
		datas = filterWithOutASunday(datas);
		List<Promoter> promoters = datas.stream().map(data -> data.getPromoter()).distinct()
				.collect(Collectors.toList());
		List<Holyday> holydays = holydayService.getHolydaysBetween(start, end);
		List<AjudaDeCusto> ajudaDeCustos = new ArrayList<AjudaDeCusto>();
		for (Promoter promoter : promoters) {
			if (promoter.getName().equals("ASSAÍ  PILARES - GRADE 2 - IGOR SUNDIN VASCONCELLOS")) {
				System.out.println("oi");
			}
			List<DataTask> promoter_DataTasks = datas.stream()
					.filter(dataTask -> dataTask.getPromoter().getId() == promoter.getId())
					.collect(Collectors.toList());
			promoter_DataTasks = filterWithOutASunday(promoter_DataTasks);
			promoter_DataTasks = filterWithOutHolyday(promoter_DataTasks, holydays);
			RelacaoDias relacaoDias = generateRelacaoDias(promoter_DataTasks);
			AjudaDeCusto ajudaDeCusto = new AjudaDeCusto();
			ajudaDeCusto.setPromoter(promoter);
			ajudaDeCusto.setRelacaoDias(relacaoDias);
			ajudaDeCusto.setDiasDescontoVA(countDescontoVA(promoter_DataTasks));
			ajudaDeCustos.add(ajudaDeCusto);
		}
		return ajudaDeCustos;
	}

	// Retorna o valor de dias para desconto do VA, usando a logica de horas
	// trabalhadas
	// menos de 4horas e meia, desconta
	public int countDescontoVA(List<DataTask> datas) {
		int count = 0;
		for (DataTask data : datas) {
			if (data.getDate().getDayOfMonth() == 3) {
				System.out.println(3);
			}
			if (data.getDuration() != null) {
				if (data.getDuration().toHours() < 4l) {
					count++;
					continue;
				}
				if (data.getDuration().toHours() == 4l) {
					if (data.getDuration().toMinutesPart() <= 30l) {
						count++;
						continue;
					}
				}

			}

		}
		return count;
	}

	// retira os domigos da lista de DataTask
	private List<DataTask> filterWithOutASunday(List<DataTask> datas) {
		return datas.stream().filter(data -> !data.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY))
				.collect(Collectors.toList());
	}

	// Retira os feriados da lista de DataTask
	private List<DataTask> filterWithOutHolyday(List<DataTask> datas, List<Holyday> holydays) {

		for (Holyday holyday : holydays) {
			for (Project project : holyday.getProjects()) {
				if (project.toString().equals(datas.stream().map(data -> data.getProject()).findFirst().get()))
					datas = datas.stream().filter(data -> !holyday.getDate().isEqual(data.getDate()))
							.collect(Collectors.toList());
			}
		}
		return datas;
	}

	// Gera uma RelacaoDia, no qual informa os dias trabalhados por dia da semana e
	// o total
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
		relacaoDias.setDiasNaoFeitos(datas.stream()
				.filter(data -> data.getSituation().equals("NÃO REALIZADO") || data.getSituation().equals("CANCELADO"))
				.collect(Collectors.toList()).size());
		return relacaoDias;
	}

	// Subtrai duas datas e retorna, em texto, o tempo
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

	// Subtrai duas datas e retorna o tempo
	public Duration subtractDateToDuration(LocalDateTime first, LocalDateTime second) {
		try {
			Duration duration = Duration.between(first, second);
			return duration;
		} catch (Exception e) {
			return null;
		}
	}

	// Retorna a data mais antiga de uma lista de LocalDateTime
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

	// Retorna a data mais nova de uma lista de LocalDateTime
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

	// retorna as primeiras atividades de cada Activity
	public List<LocalDateTime> filterStart(DataTask dataTask) {
		List<LocalDateTime> starts = new ArrayList<LocalDateTime>();

		for (Task task : dataTask.getTasks()) {
			for (Activity activity : task.getActivities()) {
				starts.add(activity.getStart());
			}
		}
		return starts;
	}

	// retorna as ultimas atividades de cada Activity
	public List<LocalDateTime> filterEnds(DataTask dataTask) {
		List<LocalDateTime> ends = new ArrayList<LocalDateTime>();

		for (Task task : dataTask.getTasks()) {
			for (Activity activity : task.getActivities()) {
				ends.add(activity.getEnd());
			}
		}
		return ends;
	}

	// Elemina as atividade de "check-out" e define a duracao da DataTask
	public void eliminateChecksDataTaskAndSetDuration(DataTask dataTask) {
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

	// Soma o tempo de uma lista de LocalTime
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

	// Calcula o numero de tarefas por status e insere no DataTask
	public void calculateInfoDataTask(DataTask dataTask) {
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
		generateDurationDataTask(dataTask);
	}

	// Calcula o numero de atividades por status e insere na Task
	public void calculateInfoTask(Task task) {
		task.setActivityTotal(task.getActivities().size());
		int done = task.getActivities().stream().filter(activity -> activity.getSituation().equals("completa"))
				.collect(Collectors.toList()).size();
		int missing = task.getActivities().stream().filter(activity -> activity.getSituation().equals("sem historico"))
				.collect(Collectors.toList()).size();
		task.setActivityDone(done);
		task.setActivityMissing(missing);
	}

	// define o situacao da DataTask
	public void defSituationDataTask(DataTask dataTask) {
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

	// define o situacao da task
	public void defSituationTask(Task task) {
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

}