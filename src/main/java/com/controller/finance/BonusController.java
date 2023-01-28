package com.controller.finance;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.controller.dto.finance.BonusDTO;
import com.controller.form.finance.BonusForm;
import com.model.finance.Bonus;
import com.model.finance.BonusPerDayInWeek;
import com.model.finance.BonusPerPrice;
import com.service.finance.BonusService;

@RestController
@RequestMapping("/bonus")
public class BonusController {

	@Autowired
	BonusService bonusService;
	@Autowired
	ModelMapper modelMapper;
	
	@RequestMapping(value = "/save" ,method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody BonusForm bonusForm) {
		if(bonusForm.getDaysInWeek()!=null) {
			BonusPerDayInWeek bonusPerDayInWeek = modelMapper.map(bonusForm, BonusPerDayInWeek.class);
			bonusService.save(bonusPerDayInWeek);
		}
		if(bonusForm.getPrice()!=null) {
			BonusPerPrice bonusPerPrice = modelMapper.map(bonusForm, BonusPerPrice.class);
			bonusService.save(bonusPerPrice);
		}
		return null;
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity findById(@PathVariable Long id) {
		BonusDTO dto = modelMapper.map(bonusService.findById(id),BonusDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ResponseEntity findAll() {
		List<BonusDTO> dtos = bonusService.findAll().stream().map(bonus -> modelMapper.map(bonus, BonusDTO.class)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
}
