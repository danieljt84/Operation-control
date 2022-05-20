package com.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoutineController {
	
	@Autowired
	ConsumerController consumerController;
	
	public void run() throws InterruptedException {
		while(true) {
			if(isBeforeMin() && isAfterMax()) {
				consumerController.routine();
			}
			Thread.sleep(3600000);
		}
		
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
		LocalTime limite =  LocalTime.parse("22:00", 
	            DateTimeFormatter.ISO_TIME);
		if(agora.isAfter(limite)){
			return true;
		}else {
			return false;
		}
	}

}
