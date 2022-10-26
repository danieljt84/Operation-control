package com.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import com.model.Activity;
import com.model.DataTask;
import com.model.Project;
import com.model.Promoter;
import com.model.Task;
import com.repository.DataTaskRepository;
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
	private static Logger logger = LoggerFactory.getLogger(RoutineController.class);
	
	public ConsumerController(ConfigurableApplicationContext context) {
		this.context = context;
	}

	@Scheduled(fixedDelay =3600000, initialDelay = 10000)
	public void routine() {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(3);
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		for (int i = 0; i <= daysBetween; i++) {
			for (ProjectAdapter project : ProjectAdapter.values()) {
				List<DataTask> datas = apiService.getResume(project.getDescription(), startDate.plusDays(i));
				for (DataTask data : datas) {
					dataTaskService.eliminateChecksDataTaskAndSetDuration(data);
					dataTaskService.calculateInfoDataTask(data);
					dataTaskService.defSituationDataTask(data);
					dataTaskService.checkAndSaveDataTask(data);
				}
			}
		}
	}
	
	private static boolean isBeforeMin() {
		LocalTime agora = LocalTime.now();
		LocalTime limite = LocalTime.parse("23:59", DateTimeFormatter.ISO_TIME);
		if (agora.isBefore(limite)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isAfterMax() {
		LocalTime agora = LocalTime.now();
		LocalTime limite = LocalTime.parse("07:00", DateTimeFormatter.ISO_TIME);
		if (agora.isAfter(limite)) {
			return true;
		} else {
			return false;
		}
	}
}
