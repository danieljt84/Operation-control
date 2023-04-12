package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Brand;
import com.model.Project;
import com.model.User;
import com.service.FilterService;
import com.service.auth.TokenService;
import com.service.auth.UserService;
import com.service.operation.BrandService;
import com.service.operation.ProjectService;

@RestController
@RequestMapping("/filter")
public class FilterController {

	@Autowired
	FilterService filterService;
	@Autowired
	BrandService brandService;
	@Autowired
	TokenService tokenService;
	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;

	@GetMapping("/activation")
	@Cacheable("activation")
	public ResponseEntity getAllValuesPossibleToActivation(@RequestParam String initialDate,
			@RequestParam String finalDate, @RequestParam(name ="idsBrand") List<Long> idsBrand, @RequestParam(name ="idsProject",required = false) List<Long> idsProject,@RequestHeader(name = "Authorization") String token) {
		try {
			var idsBrandUser = userService.getIdsBrandsById(tokenService.getIdUsuario(token));  
			var idsprojectUser = userService.getIdsProjectsById(tokenService.getIdUsuario(token));  
			idsBrand = idsBrand.stream().filter(element -> idsBrandUser.contains(element)).collect(Collectors.toList());
			if(idsProject!=null) idsProject = idsProject.stream().filter(element -> idsprojectUser.contains(element)).collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK)
					.body(filterService.getAllValuesPossibleToFilterToActivation(
							LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), 
							LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), idsBrand,idsProject));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


}
