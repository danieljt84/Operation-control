package com.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.model.Promoter;
import com.service.operation.DataTaskService;
import com.service.operation.ExcelService;
import com.service.operation.PromoterService;

@Controller
public class RoutineController {

	@Autowired
	ConsumerController consumerController;
	@Autowired
	ExcelService excelService;
	@Autowired
	PromoterService promoterService;
	@Autowired
	DataTaskService dataTaskService;
	private static Logger logger = LoggerFactory.getLogger(RoutineController.class);

	public static boolean isBeforeMin() {
		LocalTime agora = LocalTime.now();
		LocalTime limite = LocalTime.parse("23:59", DateTimeFormatter.ISO_TIME);
		if (agora.isBefore(limite)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isAfterMax() {
		LocalTime agora = LocalTime.now();
		LocalTime limite = LocalTime.parse("07:00", DateTimeFormatter.ISO_TIME);
		if (agora.isAfter(limite)) {
			return true;
		} else {
			return false;
		}
	}

}
