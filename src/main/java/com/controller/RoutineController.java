package com.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import com.model.Promoter;
import com.service.DataTaskService;
import com.service.ExcelService;
import com.service.PromoterService;

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
	
	//@EventListener(ApplicationReadyEvent.class)
	public void run() throws InterruptedException {
		while(true) {
			if(isBeforeMin() && isAfterMax()) {
				consumerController.routine();
			}
			Thread.sleep(12000000);
		}
	}
	public void run2() {
		excelService.createExcelAjudaDeCusto("01/07/2022", "31/07/2022");
	}	
	public void run3() {
		consumerController.routine();
	}
	
	public void testeInfo() {
		dataTaskService.generatePercentual("2022-06-13");
	}
	public void testePlanilha() {
		List<Promoter> promoters = promoterService.findAll();
		promoters.forEach(promoter -> excelService.findInfoInExcelPlanilhaCusto(promoter));
		System.out.println(promoters);
	}
	
	public static boolean isBeforeMin(){
		LocalTime agora = LocalTime.now();
		LocalTime limite =  LocalTime.parse("23:59", 
	            DateTimeFormatter.ISO_TIME);
		if(agora.isBefore(limite)){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isAfterMax(){
		LocalTime agora = LocalTime.now();
		LocalTime limite =  LocalTime.parse("09:00", 
	            DateTimeFormatter.ISO_TIME);
		if(agora.isAfter(limite)){
			return true;
		}else {
			return false;
		}
	}

}
