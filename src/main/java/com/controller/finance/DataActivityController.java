package com.controller.finance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.controller.dto.finance.ActivityDTO;
import com.controller.dto.finance.DataActivityDTO;
import com.controller.dto.operation.BrandDTO;
import com.controller.dto.operation.ShopDTO;
import com.controller.form.DataActivityForm;
import com.controller.form.FilterDataTableDataActivityForm;
import com.model.Activity;
import com.model.Shop;
import com.model.finance.DataActivity;
import com.service.finance.DataActivityService;
import com.service.operation.ActivityService;
import com.service.operation.BrandService;
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
	@Autowired
	BrandService brandService;
	@Autowired
	ModelMapper modelMapper;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity save(@RequestBody DataActivityForm dataActivityForm) {
		DataActivity dataActivity = new DataActivity();
		BeanUtils.copyProperties(dataActivityForm, dataActivity);
		dataActivity.setShop(shopService.findById(dataActivityForm.getShopId()));
		dataActivity.setActivity(activityService.findById(dataActivityForm.getActivityId()));
		dataActivityService.save(dataActivity);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity update(@RequestBody DataActivityForm dataActivityForm) {
		DataActivity dataActivity = new DataActivity();
		BeanUtils.copyProperties(dataActivityForm, dataActivity);
		dataActivity.setShop(shopService.findById(dataActivityForm.getShopId()));
		dataActivity.setActivity(activityService.findById(dataActivityForm.getActivityId()));
		dataActivityService.update(dataActivity);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity findAll() {
		var datasActivity = dataActivityService.findAll();
		List<DataActivityDTO> dtos = new ArrayList<>();
		for (DataActivity dataActivity : datasActivity) {
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

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity findByFilter(@RequestBody FilterDataTableDataActivityForm form) {
		var datas = dataActivityService.findByFilter(form);
		List<DataActivityDTO> dtos = new ArrayList<>();
		for (BigInteger idDataActivity : datas) {
			DataActivityDTO dataActvityDTO = new DataActivityDTO();
			ActivityDTO activityDTO = new ActivityDTO();
			ShopDTO shopDTO = new ShopDTO();
			BrandDTO brandDTO = new BrandDTO();
			DataActivity dataActivity = dataActivityService.findById(idDataActivity.longValue());
			BeanUtils.copyProperties(dataActivity, dataActvityDTO);
			BeanUtils.copyProperties(shopService.findById(dataActivity.getShop().getId()), shopDTO);
			BeanUtils.copyProperties(activityService.findById(dataActivity.getActivity().getId()), activityDTO);
			BeanUtils.copyProperties(brandService.findById(dataActivity.getActivity().getBrand().getId()), brandDTO);
			activityDTO.setBrand(brandDTO);
			dataActvityDTO.setActivity(activityDTO);
			dataActvityDTO.setShop(shopDTO);
			dtos.add(dataActvityDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@RequestMapping(value = "/shop", method = RequestMethod.POST)
	public ResponseEntity getShopsByActivity(@RequestBody List<Long> activityIds) {
		List<ShopDTO> dtos = dataActivityService.getShopsByActivity(activityIds).stream()
				.map(shop -> modelMapper.map(shop, ShopDTO.class)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
}
