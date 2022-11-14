package com.service.api;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Activity;
import com.model.Brand;
import com.model.DataTask;
import com.model.Promoter;
import com.model.Shop;
import com.model.Task;
import com.model.Team;
import com.repository.BrandRepositoryImp;
import com.repository.TeamRepositoryImp;
import com.service.BrandService;
import com.service.PromoterService;
import com.service.ShopService;
import com.util.PropertiesReader;

@Service
public class ApiLaivonService {

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

	public List<DataTask> getResume(String _project,LocalDate date) {
		
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
				return convertJSONToDataOperation(root);
			} catch (Exception e) {
				
			}
		}
	}

	public List<DataTask> convertJSONToDataOperation(JsonNode root) {
		List<DataTask> datasTask = new ArrayList<DataTask>();
		for (JsonNode dado : root.path("dados")) {
			String namePromoter = dado.path("agente").asText();
			Team team = teamRepository.checkTeam(dado.path("equipe").asText());
			String cpf = dado.path("agente_matricula").asText();
			String idSystem = dado.path("agente_id").asText();
			Promoter promoter = promoterService.checkPromoter(namePromoter);
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
					Shop shop = shopService.checkShop(node_task.path("local").asText(),node_task.path("local_id").asLong());
					task.setShop(shop);
					task.setActivityTotal(node_task.path("atividades totais").asInt());
					task.setActivityDone(node_task.path("atividades executadas").asInt());
					task.setActivityMissing(node_task.path("atividades não executadas").asInt());
					task.setSituation(node_task.path("situacao").asText());
					task.setStart(convertTime(node_task.path("inicio").asText()));
					task.setEnd(convertTime(node_task.path("fim").asText()));
					task.setType(node_task.path("tipo").asText());
					List<Activity> activities = new ArrayList<>();
					for(JsonNode node_activities: node_task.path("dados_atividade")) {
						Activity activity = new Activity();
						activity.setDescription(node_activities.path("descricao").asText());
						activity.setIdSystem(node_activities.path("id").asLong());
						Brand brand = brandService.checkBrand(this.getNameBrand(node_activities.path("descricao").asText()));
						activity.setBrand(brand);
						activity.setStart(convertLocalDate(node_activities.path("inicio").asText()));
						activity.setEnd(convertLocalDate(node_activities.path("fim").asText()));
						activity.setSituation(node_activities.path("situação").asText());
						activities.add(new Activity(activity));
					}
					task.setActivities(activities);
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
