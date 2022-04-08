package com.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.catalina.core.ApplicationContext;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalTimeConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.model.Activity;
import com.model.DataTask;
import com.model.Promoter;
import com.model.Task;
import com.repository.PromoterRepository;
import com.repository.ShopRepository;

@Service
public class ApiService {

	RestTemplate restTemplate;
	ObjectMapper objectMapper;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	PromoterRepository promoterRepository;
	String project;

	public List<DataTask> getResume(ConfigurableApplicationContext context) {
		objectMapper = new ObjectMapper();
		restTemplate = new RestTemplate();
		String response = null;
		String urlLaivon = "https://back2-dashboard.laivon.com/io/api/v1/4p/resume";
		JSONObject bodyJSON = new JSONObject();
		project = "4pmktcfixo";
		bodyJSON.put("password", "-LwaViadoec5");
		bodyJSON.put("tokenapi", "5156d8bc-ecb5-4cf4-9139-decbb26c2365");
		bodyJSON.put("dateInit", "2022-04-08");
		bodyJSON.put("dateEnd", "2022-04-08");
		bodyJSON.put("enviroment", project);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		try {
			HttpEntity<String> request = new HttpEntity<String>(bodyJSON.toString(), headers);
			response = restTemplate.postForEntity(urlLaivon, request, String.class).getBody();
		} catch (Exception e) {
			System.err.println("ERRO DE CONEXÃO");
			getResume(context);
		}

		try {
			JsonNode root = objectMapper.readTree(response);
			return convertJSONToDataOperation(root);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<DataTask> convertJSONToDataOperation(JsonNode root) {
		List<DataTask> datasTask = new ArrayList<DataTask>();

		for (JsonNode dado : root.path("dados")) {
			String namePromoter = dado.path("agente").asText();
			Promoter promoter = promoterRepository.findByName(namePromoter);
			if (promoter == null)
				promoter = new Promoter(namePromoter);
			String nameTeam = dado.path("equipe").asText();
			for (JsonNode node_tasks : dado.path("dados_agente")) {
				DataTask dataTask = new DataTask();
				dataTask.setProject(project);
				dataTask.setTeam(nameTeam);
				dataTask.setPromoter(promoter);
				dataTask.setSituation(node_tasks.path("situação").asText());
				dataTask.setTaskTotal(node_tasks.path("tarefas totais").asInt());
				dataTask.setDate(
						LocalDate.parse(node_tasks.path("data").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				dataTask.setTaskDone(node_tasks.path("tarefas executadas").asInt());
				dataTask.setTaskCanceled(node_tasks.path("tarefas canceladas").asInt());
				dataTask.setSituation(node_tasks.path("situação").asText());
				List<Task> tasks = new ArrayList<>();
				for(JsonNode node_task : node_tasks.path("dados_tarefa")) {
					Task task = new Task();
					task.setShop(shopRepository.findByName(node_task.path("local").asText()));
					task.setActivityTotal(node_task.path("atividades totais").asInt());
					task.setActivityDone(node_task.path("atividades executadas").asInt());
					task.setActivityMissing(node_task.path("atividades não executadas").asInt());
					task.setSituation(node_task.path("situacao").asText());
					Task taskAux = task;
					List<Activity> activities = new ArrayList<>();
					for(JsonNode node_activities: node_task.path("dados_atividade")) {
						Activity activity = new Activity();
						activity.setDescription(node_activities.path("descricao").asText());
						activity.setSituation(node_activities.path("situação").asText());
						activity.setStart(convertLocalDate(node_activities.path("inicio").asText()));
						activity.setEnd(convertLocalDate(node_activities.path("fim").asText()));
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

	public LocalDateTime convertLocalDate(String date) {
		if (!date.equals("")) {
			Instant instant = Instant.parse(date);
			return LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
		}
		return null;
	}

}
