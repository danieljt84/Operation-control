package com.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
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
			System.out.println("ATUALIZADO EM: "+LocalDateTime.now().toString());
			Thread.sleep(12000000);
		}
	}
	
	public void run2() {
		try {
			excelService.createExcel("01/09/2022", "31/09/2022");
		System.out.println("oi");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		LocalTime limite =  LocalTime.parse("07:00", 
	            DateTimeFormatter.ISO_TIME);
		if(agora.isAfter(limite)){
			return true;
		}else {
			return false;
		}
	}

}
