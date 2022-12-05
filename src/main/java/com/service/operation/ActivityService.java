package com.service.operation;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Activity;
import com.model.Brand;
import com.model.Project;
import com.model.finance.DataActivity;
import com.repository.operation.ActivityRepository;

@Service
public class ActivityService {
	
	@Autowired
	ActivityRepository activityRepository;
	
	
	public Activity check(String description, Long idSystem, Brand brand,String project) {
		if(description != null && idSystem != null) {
			Activity activity = activityRepository.findByDescriptionOrIdSystem(description, idSystem);
			if(activity==null) {
				activity = new Activity();
				activity.setBrand(brand);
				activity.setDescription(description.toUpperCase());
				activity.setIdSystem(idSystem);
				activity.setProject(Project.getEnum(project));
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
	
	public Activity findById(Long id) {
		Optional<Activity> optional = activityRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new EntityNotFoundException("ATIVIDADE -" + id + "- NÃO ENCONTRADO");
		}
	}

}
