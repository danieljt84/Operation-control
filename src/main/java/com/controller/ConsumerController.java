package com.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.controller.api.LaivonApiController;
import com.model.DataTask;
import com.service.api.ApiEmployeeService;
import com.service.operation.DataTaskService;
import com.service.operation.ExcelService;
import com.service.operation.PromoterService;
import com.util.ProjectAdapter;

@Component
public class ConsumerController {

	ConfigurableApplicationContext context;
	@Autowired
	DataTaskService dataTaskService;
	@Autowired
	PromoterService promoterService;
	@Autowired
	LaivonApiController laivonApiController;
	@Autowired
	ApiEmployeeService apiEmployeeService;
	@Autowired
	ExcelService excelService;
	private static Logger logger = LoggerFactory.getLogger(RoutineController.class);
	
	public ConsumerController(ConfigurableApplicationContext context) {
		this.context = context;
	}
    
	//@Scheduled(fixedDelay =3600000, initialDelay = 10000)
	public void run() {
		if(isBeforeMin() && isAfterMax()) {
			routineToConsumer(7);
			logger.info("BASE ATUALIZADA EM: " + LocalDateTime.now().toString());
		}
	}
	
	//@Scheduled(cron = "* 0 23 * * *")
	public void run2() {
		routineToConsumer(3);
		logger.info("BASE DE 3 DIAS ANTERIORES ATUALIZADA EM: " + LocalDateTime.now().toString());
	}
	
	private void routineToConsumer(long daysToSubtract) {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(daysToSubtract);
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		Set<Long> idsPromoters = new HashSet<>();
		for (int i = 0; i <= daysBetween; i++) {
			for (ProjectAdapter project : ProjectAdapter.values()) {
				List<DataTask> datas = laivonApiController.convertJSONinDataOperation(laivonApiController.getJSON(project.getDescription(), startDate.plusDays(i)));
				for (DataTask data : datas) {
					dataTaskService.eliminateChecksDataTaskAndSetDuration(data);
					dataTaskService.calculateInfoDataTask(data);
					dataTaskService.defSituationDataTask(data);
					dataTaskService.checkAndSaveDataTask(data);
					idsPromoters.add(data.getId());
				}
			}
		}
		promoterService.updateStatusToInativo(idsPromoters);
	}
	
	private static boolean isBeforeMin() {
		LocalTime agora = LocalTime.now();
		LocalTime limite = LocalTime.parse("22:00", DateTimeFormatter.ISO_TIME);
		if (agora.isBefore(limite)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isAfterMax() {
		LocalTime agora = LocalTime.now();
		LocalTime limite = LocalTime.parse("08:00", DateTimeFormatter.ISO_TIME);
		if (agora.isAfter(limite)) {
			return true;
		} else {
			return false;
		}
	}
}
