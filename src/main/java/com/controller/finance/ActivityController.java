package com.controller.finance;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.controller.dto.finance.ActivityDTO;
import com.service.operation.ActivityService;

@RequestMapping("/activity")
@RestController
public class ActivityController {
	
	@Autowired
	ActivityService activityService;
	@Autowired
	ModelMapper modelMapper;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ResponseEntity list() {
		List<ActivityDTO> dtos = activityService.findAll().stream().map(activity -> modelMapper.map(activity, ActivityDTO.class)).collect(Collectors.toList());
	    return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
}
