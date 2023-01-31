package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.controller.form.operation.FilterForm;
import com.model.Brand;
import com.service.operation.BrandService;
import com.service.operation.DataTaskService;
import com.service.operation.TeamService;
import com.util.ProjectAdapter;

@Controller
@RequestMapping("/datatask")
public class DataTaskController {
	
	@Autowired
	DataTaskService dataTaskService;
	@Autowired
	TeamService teamService;
	@Autowired
	BrandService brandService;
	
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
	public ResponseEntity getCountActivityCompleteByBrand(@RequestParam String date,@RequestParam(name = "idBrand") Long idBrand) {
		try {
			Brand brand = brandService.findById(idBrand);
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteByBrand(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/countactivitycompletebetweendatebybrand",method = RequestMethod.GET)
	public ResponseEntity getCountActivityCompleteBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate, @RequestParam(name = "idBrand") Long idBrand) {
		try {
			Brand brand = brandService.findById(idBrand);
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteBetweenDateByBrand(LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/countactivitycompletebetweendatebybrand",method = RequestMethod.POST)
	public ResponseEntity getCountActivityCompleteBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate, @RequestParam(name = "idBrand") Long idBrand, @RequestBody FilterForm filterForm) {
		try {
			Brand brand = brandService.findById(idBrand);
			if(filterForm.getFilter()!= null) {
				return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteBetweenDateByBrand(LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand,filterForm.getFilter()));
			}else {
				return getCountActivityCompleteBetweenDateByBrand(initialDate,finalDate,idBrand);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/countactivitycompletewithdatebetweendatebybrand",method = RequestMethod.GET)
	public ResponseEntity getCountActivityWithDateCompleteBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate, @RequestParam(name = "idBrand") Long idBrand) {
		try {
			Brand brand = brandService.findById(idBrand);
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityCompleteWithDateBetweenDateByBrand(LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/countactivitycompletewithdatebetweendatebybrand",method = RequestMethod.POST)
	public ResponseEntity getCountActivityWithDateCompleteBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate, @RequestParam(name = "idBrand") Long idBrand,@RequestBody FilterForm filterForm) {
		try {
			Brand brand = brandService.findById(idBrand);
			if(filterForm.getFilter()!= null) {
				
			}
			return getCountActivityWithDateCompleteBetweenDateByBrand(initialDate,finalDate,idBrand);
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping
	@RequestMapping("/countactivitymissingbybrand")
	public ResponseEntity getCountActivityMissingByBrand(@RequestParam String date,@RequestParam(name = "idBrand") Long idBrand) {
		try {
			Brand brand = brandService.findById(idBrand);
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityMissingByBrand(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping
	@RequestMapping("/countactivitydoingbybrand")
	public ResponseEntity getCountActivityDoingByBrand(@RequestParam String date,@RequestParam(name = "idBrand") Long idBrand) {
		try {
			Brand brand = brandService.findById(idBrand);
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityDoingByBrand(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/countactivitymissingbetweendatebybrand",method = RequestMethod.GET)
	public ResponseEntity getCountActivityMissingBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate,@RequestParam(name = "idBrand") Long idBrand) {
		try {
			Brand brand = brandService.findById(idBrand);
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityMissingBetweenDateByBrand(LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(value="/countactivitymissingbetweendatebybrand",method = RequestMethod.POST)
	public ResponseEntity getCountActivityMissingBetweenDateByBrand(@RequestParam String initialDate,@RequestParam String finalDate, @RequestParam(name = "idBrand") Long idBrand,@RequestBody FilterForm filterForm) {
		try {
			Brand brand = brandService.findById(idBrand);
			if(filterForm.getFilter()!=null) {
				return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getCountActivityMissingBetweenDateByBrand(LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand,filterForm.getFilter()));
			}else {
				return getCountActivityMissingBetweenDateByBrand(initialDate,finalDate,idBrand);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping
	@RequestMapping("/previstorealizado")
	public ResponseEntity getPrevistoRealizado(@RequestParam String date) {
		var today = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getPrevistoVsrealizado(today.withDayOfMonth(1),today,ProjectAdapter.values()));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
