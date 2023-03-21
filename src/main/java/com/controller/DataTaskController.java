package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.controller.dto.filter.FilterActivationDTO;
import com.controller.form.filter.FilterForm;
import com.model.Brand;
import com.model.Project;
import com.repository.operation.DataTaskRepository;
import com.service.auth.TokenService;
import com.service.operation.BrandService;
import com.service.operation.DataTaskService;
import com.service.operation.TeamService;

@Controller
@RequestMapping("/datatask")
public class DataTaskController {
	
	@Autowired
	DataTaskService dataTaskService;
	@Autowired
	TeamService teamService;
	@Autowired
	BrandService brandService;
	@Autowired
	DataTaskRepository dataTaskRepository;
	

	
	@GetMapping
	@RequestMapping("/countactivitycomplete")
	public ResponseEntity getCountActivityCompleteByTeam(@RequestParam String date) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteByTeam(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), teamService.getAll()));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping
	@RequestMapping("/countactivitycompletebypromoter")
	public ResponseEntity getCountActivityCompleteByTeamAndPromoter(@RequestParam String date) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteByPromoterByTeam(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), teamService.getAll()));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping
	@RequestMapping("/countactivitycompletebybrand")
	public ResponseEntity getCountActivityCompleteByBrand(@RequestParam String date,@RequestParam(name = "idsBrand") List<Long> idsBrand,@RequestParam(name = "idsProject",required = false) List<Long> idsProject) {
		try {
				return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteByBrand(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), idsBrand,(idsProject!=null)? idsProject : null));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping
	@RequestMapping("/countactivitydoingbybrand")
	public ResponseEntity getCountActivityDoingByBrand(@RequestParam String date,@RequestParam(name = "idsBrand") List<Long> idsBrand,@RequestParam(name = "idsProject",required = false) List<Long> idsProject) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityDoingByBrand(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), idsBrand,(idsProject!=null)? idsProject : null));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping
	@RequestMapping("/countactivitymissingbybrand")
	public ResponseEntity getCountActivityMissingByBrand(@RequestParam String date,@RequestParam(name = "idsBrand") List<Long> idsBrand,@RequestParam(name = "idsProject",required = false) List<Long> idsProject) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityMissingByBrand(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), idsBrand,(idsProject!=null)? idsProject : null));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/countactivitycompletebetweendatebybrand",method = RequestMethod.POST)
	public ResponseEntity getCountActivityCompleteBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate, @RequestParam(name = "idsBrand") List<Long> idsBrand,@RequestBody(required = false)  FilterForm filter) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteBetweenDateByBrand(LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), idsBrand,filter));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/countactivitymissingbetweendatebybrand",method = RequestMethod.POST)
	public ResponseEntity getCountActivityMissingBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate, @RequestParam(name = "idsBrand") List<Long> idsBrand,@RequestBody(required = false)  FilterForm filter) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityMissingBetweenDateByBrand(LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), idsBrand,filter));

		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/countactivitycompletewithdatebetweendatebybrand",method = RequestMethod.POST)
	public ResponseEntity getCountActivityWithDateCompleteBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate, @RequestParam(name = "idsBrand") List<Long> idsBrand,@RequestBody(required = false) FilterForm filter) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteWithDateBetweenDateByBrand(LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), idsBrand,filter));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	

	
	@PostMapping
	@RequestMapping("/previstorealizado")
	public ResponseEntity getPrevistoRealizado(@RequestParam String date,@RequestParam(name = "idsBrand") List<Long> idsBrand,FilterActivationDTO filter) {
		var today = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<Brand> brands = idsBrand.stream().map(id -> brandService.findById(id)).collect(Collectors.toList());
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getPrevistoVsrealizado(today.withDayOfMonth(1),today,null));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
