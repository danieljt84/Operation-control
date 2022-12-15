package com.controller.finance;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.controller.dto.finance.ActivityDTO;
import com.controller.dto.finance.DataActivityDTO;
import com.controller.dto.operation.BrandDTO;
import com.controller.dto.operation.ShopDTO;
import com.controller.form.DataActivityForm;
import com.model.finance.DataActivity;
import com.service.finance.DataActivityService;
import com.service.operation.ActivityService;
import com.service.operation.ShopService;

@RestController
@RequestMapping("/dataactivity")
public class DataActivityController {
	
	@Autowired
	DataActivityService dataActivityService;
	@Autowired
	ActivityService activityService;
	@Autowired
	ShopService shopService;
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public ResponseEntity save(@RequestBody DataActivityForm dataActivityForm) {
		DataActivity dataActivity = new DataActivity();
		BeanUtils.copyProperties(dataActivityForm, dataActivity);
		dataActivity.setShop(shopService.findById(dataActivityForm.getShopId()));
		dataActivity.setActivity(activityService.findById(dataActivityForm.getActivityId()));
		dataActivityService.save(dataActivity);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.PUT)
	public ResponseEntity update(@RequestBody DataActivityForm dataActivityForm) {
		DataActivity dataActivity = new DataActivity();
		BeanUtils.copyProperties(dataActivityForm, dataActivity);
		dataActivity.setShop(shopService.findById(dataActivityForm.getShopId()));
		dataActivity.setActivity(activityService.findById(dataActivityForm.getActivityId()));
		dataActivityService.update(dataActivity);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ResponseEntity findAll() {
		var datasActivity =  dataActivityService.findAll();
		List<DataActivityDTO> dtos = new ArrayList<>();
		for(DataActivity dataActivity: datasActivity) {
			DataActivityDTO dataActivityDTO = new DataActivityDTO();
			ShopDTO shopDTO = new ShopDTO();
			ActivityDTO activityDTO = new ActivityDTO();
			BrandDTO brandDTO = new BrandDTO();
			BeanUtils.copyProperties(dataActivity, dataActivityDTO);
			BeanUtils.copyProperties(dataActivity.getShop(), shopDTO);
			BeanUtils.copyProperties(dataActivity.getActivity(), activityDTO);
			BeanUtils.copyProperties(dataActivity.getActivity().getBrand(), brandDTO);
			activityDTO.setBrand(brandDTO);
			dataActivityDTO.setShop(shopDTO);
			dataActivityDTO.setActivity(activityDTO);
			dtos.add(dataActivityDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
	
	public ResponseEntity findByFilter(@RequestBody Filter) {
		
	}

}
