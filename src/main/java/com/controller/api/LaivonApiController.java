package com.controller.api;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Activity;
import com.model.Brand;
import com.model.DataTask;
import com.model.Project;
import com.model.Promoter;
import com.model.Shop;
import com.model.Task;
import com.model.Task_Activity;
import com.model.Team;
import com.repository.operation.Task_ActivityRepositoy;
import com.repository.operation.TeamRepositoryImp;
import com.service.operation.ActivityService;
import com.service.operation.BrandService;
import com.service.operation.DataTaskService;
import com.service.operation.ProjectService;
import com.service.operation.PromoterService;
import com.service.operation.ShopService;
import com.service.operation.TaskService;
import com.util.PropertiesReader;

@Controller
public class LaivonApiController {

	RestTemplate restTemplate;
	ObjectMapper objectMapper;
	@Autowired
	ShopService shopService;
	@Autowired
	BrandService brandService;
	@Autowired
	TeamRepositoryImp teamRepository;
	@Autowired
	PromoterService promoterService;
	Project project;
	@Autowired
	ActivityService activityService;
	@Autowired
	DataTaskService dataTaskService;
	@Autowired
	TaskService taskService;
	@Autowired
	ProjectService projectService;
	@Autowired
	Task_ActivityRepositoy task_ActivityRepositoy;

	public JsonNode getJSON(Project _project, LocalDate date) {
		while (true) {
			objectMapper = new ObjectMapper();
			restTemplate = new RestTemplate();
			String response = null;
			project = _project;
			String urlLaivon = PropertiesReader.getProp().getProperty("api.url");
			JSONObject bodyJSON = new JSONObject();
			bodyJSON.put("tokenapi", PropertiesReader.getProp().getProperty("api.token"));
			bodyJSON.put("dateInit", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			bodyJSON.put("dateEnd", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			bodyJSON.put("enviroment", _project.getNameApi());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			try {
				HttpEntity<String> request = new HttpEntity<String>(bodyJSON.toString(), headers);
				response = restTemplate
						.postForEntity(urlLaivon,request, String.class)
						.getBody();
				JsonNode root = objectMapper.readTree(response);
				return root;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public List<DataTask> convertJSONinDataOperation(JsonNode root) {
		List<DataTask> datasTask = new ArrayList<DataTask>();
		for (JsonNode dado : root.path("dados")) {
			String namePromoter = dado.path("agente").asText();
			Team team = teamRepository.checkTeam(dado.path("equipe").asText());
			String cpf = dado.path("agente_matricula").asText();
			long idSystem = dado.path("agente_id").asLong();
			Promoter promoter = promoterService.checkPromoter(namePromoter, idSystem);
			promoterService.updateIfHasUpdate(promoter, team, cpf, idSystem);
			for (JsonNode node_tasks : dado.path("dados_agente")) {
				DataTask dataTask;
				try {
					dataTask = dataTaskService.findByPromoterAndDate(promoter, LocalDate
							.parse(node_tasks.path("data").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					dataTask = new DataTask();
				}
				dataTask.setProject(projectService.check(project));
				dataTask.setTeam(team);
				dataTask.setPromoter(promoter);
				dataTask.setSituation(node_tasks.path("situação").asText());
				dataTask.setTaskTotal(node_tasks.path("tarefas totais").asInt());
				dataTask.setDate(
						LocalDate.parse(node_tasks.path("data").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				dataTask.setTaskDone(node_tasks.path("tarefas executadas").asInt());
				dataTask.setTaskCanceled(node_tasks.path("tarefas canceladas").asInt());
				dataTask.setSituation(node_tasks.path("situacao").asText());
				dataTask.setTasks(new ArrayList<>());
				for (JsonNode node_task : node_tasks.path("dados_tarefa")) {
					Shop shop = shopService.checkShop(node_task.path("local").asText(),
							node_task.path("local_id").asLong(), project);
					/*
					 * if (dataTask.getTasks() != null) { task = dataTask.getTasks().stream()
					 * .filter(element -> element.getShop()!=null &&
					 * element.getShop().getId().equals(shop.getId()) &&
					 * element.get.equals(convertTime(node_task.path("inicio").asText())))
					 * .findAny().map(element -> element).orElse(new Task());
					 * 
					 * } else { dataTask.setTasks(new ArrayList<>()); task = new Task(); }
					 */
					Task task = new Task();

					task.setShop(shop);
					task.setActivityTotal(node_task.path("atividades totais").asInt());
					task.setActivityDone(node_task.path("atividades executadas").asInt());
					task.setActivityMissing(node_task.path("atividades não executadas").asInt());
					task.setSituation(node_task.path("situacao").asText());
					task.setStart(convertTime(node_task.path("inicio").asText()));
					task.setEnd(convertTime(node_task.path("fim").asText()));
					task.setType(node_task.path("tipo").asText());
					for (JsonNode node_activities : node_task.path("dados_atividade")) {
						Brand brand = brandService
								.checkBrand(this.getNameBrand(node_activities.path("descricao").asText()));
						Activity activity = activityService.check(node_activities.path("descricao").asText(),
								node_activities.path("id").asLong(), brand, project);
						if (activity != null) {
							Task_Activity task_Activity;
							if (task.getTask_Activities() != null) {
								task_Activity = task.getTask_Activities().stream()
										.filter(element -> element.getTask().getId() != null
												&& element.getTask().getId().equals(task.getId()))
										.findAny().map(element -> element).orElse(new Task_Activity());
							} else {
								task.setTask_Activities(new ArrayList<>());
								task_Activity = new Task_Activity();
							}
							task_Activity.setActivity(activity);
							task_Activity.setStart(convertLocalDate(node_activities.path("inicio").asText()));
							task_Activity.setEnd(convertLocalDate(node_activities.path("fim").asText()));
							task_Activity.setSituation(node_activities.path("situação").asText());
							task_Activity.setTask(task);
							task.getTask_Activities().add(task_Activity);
						}
					}
					dataTask.getTasks().add(task);
				}
				datasTask.add(dataTask);
			}
		}
		return datasTask;
	}

	public LocalTime convertTime(String time) {
		if (!time.equals("")) {
			return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
		}
		return null;
	}

	public LocalDateTime convertLocalDate(String date) {
		if (!date.equals("")) {
			Instant instant = Instant.parse(date);
			return LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
		}
		return null;
	}

	public String getNameBrand(String description) {
		try {
			if (description.contains("- ")) {
				return description.split("- ")[1];
			} else {
				return description.split("Pesquisa ")[1];
			}
		} catch (Exception e) {
			return null;
		}
	}

}
