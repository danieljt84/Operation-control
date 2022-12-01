package com.controller.api;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
import com.model.Promoter;
import com.model.Shop;
import com.model.Task;
import com.model.Task_Activity;
import com.model.Team;
import com.repository.TeamRepositoryImp;
import com.service.ActivityService;
import com.service.BrandService;
import com.service.PromoterService;
import com.service.ShopService;
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
	String project;
	@Autowired
	ActivityService activityService;
	
	public JsonNode getJSON(String _project,LocalDate date) {
		while(true) {
			objectMapper = new ObjectMapper();
			restTemplate = new RestTemplate();
			String response = null;
			project = _project;
			String urlLaivon = PropertiesReader.getProp().getProperty("api.url");
			JSONObject bodyJSON = new JSONObject();
			bodyJSON.put("password", PropertiesReader.getProp().getProperty("api.password"));
			bodyJSON.put("tokenapi",PropertiesReader.getProp().getProperty("api.token"));
			bodyJSON.put("dateInit", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			bodyJSON.put("dateEnd", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			bodyJSON.put("enviroment", _project);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			try {
				HttpEntity<String> request = new HttpEntity<String>(bodyJSON.toString(), headers);
				response = restTemplate.postForEntity(urlLaivon, request, String.class).getBody();
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
			if(namePromoter.equals("ASSAÍ ALCANTARA - HIBRIDO - MARCOS ADRIANO DA COSTA SILVA")) {
				System.out.println("ok");
			}
			Promoter promoter = promoterService.checkPromoter(namePromoter,idSystem);
			promoterService.updateIfHasUpdate(promoter,team,cpf,idSystem);
			for (JsonNode node_tasks : dado.path("dados_agente")) {
				DataTask dataTask = new DataTask();
				dataTask.setProject(project);
				dataTask.setTeam(team);
				dataTask.setPromoter(promoter);
				dataTask.setSituation(node_tasks.path("situação").asText());
				dataTask.setTaskTotal(node_tasks.path("tarefas totais").asInt());
				dataTask.setDate(
						LocalDate.parse(node_tasks.path("data").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				dataTask.setTaskDone(node_tasks.path("tarefas executadas").asInt());
				dataTask.setTaskCanceled(node_tasks.path("tarefas canceladas").asInt());
				dataTask.setSituation(node_tasks.path("situacao").asText());
				List<Task> tasks = new ArrayList<>();
				for(JsonNode node_task : node_tasks.path("dados_tarefa")) {
					Task task = new Task();
					Shop shop = shopService.checkShop(node_task.path("local").asText(),node_task.path("local_id").asLong(),project);
					task.setShop(shop);
					task.setActivityTotal(node_task.path("atividades totais").asInt());
					task.setActivityDone(node_task.path("atividades executadas").asInt());
					task.setActivityMissing(node_task.path("atividades não executadas").asInt());
					task.setSituation(node_task.path("situacao").asText());
					task.setStart(convertTime(node_task.path("inicio").asText()));
					task.setEnd(convertTime(node_task.path("fim").asText()));
					task.setType(node_task.path("tipo").asText());
					List<Task_Activity> task_Activities = new ArrayList<>();
					for(JsonNode node_activities: node_task.path("dados_atividade")) {
						Brand brand = brandService.checkBrand(this.getNameBrand(node_activities.path("descricao").asText()));
						Activity activity = activityService.check(node_activities.path("descricao").asText(), node_activities.path("id").asLong(), brand,project);
						if(activity!=null) {
							Task_Activity task_Activity = new Task_Activity();
                            task_Activity.setActivity(activity);
                            task_Activity.setTask(task);
                            task_Activity.setStart(convertLocalDate(node_activities.path("inicio").asText()));
                            task_Activity.setEnd(convertLocalDate(node_activities.path("fim").asText()));
                            task_Activity.setSituation(node_activities.path("situação").asText());
    						task_Activities.add(new Task_Activity(task_Activity));
						}
					}
					task.setTask_Activities(task_Activities);
					tasks.add(new Task(task));
				}
				dataTask.setTasks(tasks);
				datasTask.add(new DataTask(dataTask));
			}
		}
		return datasTask;
	}
	
	public LocalTime convertTime(String time) {
		if(!time.equals("")) {
			return LocalTime.parse(time,DateTimeFormatter.ofPattern("HH:mm:ss"));
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
			if(description.contains("- ")) {
				return description.split("- ")[1];
			}else {
				return description.split("Pesquisa ")[1];
			}
		}catch (Exception e) {
			return null;
		}
	}
	
}
