package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Activity;
import com.model.Brand;
import com.repository.ActivityRepository;

@Service
public class ActivityService {
	
	@Autowired
	ActivityRepository activityRepository;
	
	
	public Activity check(String description, Long idSystem, Brand brand) {
		if(description != null && idSystem != null) {
			Activity activity = activityRepository.findByDescriptionOrIdSystem(description, idSystem);
			if(activity==null) {
				activity = new Activity();
				activity.setBrand(brand);
				activity.setDescription(description.toUpperCase());
				activity.setIdSystem(idSystem);
				activity = save(activity);
			}
			return activity;
		}
		return null;
	}
	
	
	public Activity save(Activity activity) {
		try {
			return activityRepository.save(activity);
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
