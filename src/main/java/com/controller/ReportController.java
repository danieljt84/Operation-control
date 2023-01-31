package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Brand;
import com.service.operation.BrandService;
import com.service.operation.DataTaskService;
import com.service.operation.ExcelService;

@RestController
@RequestMapping(value = "/report")
public class ReportController {
	
	@Autowired
	ExcelService excelService;
	@Autowired
	DataTaskService dataTaskService;	
	@Autowired
	BrandService brandService;
	
	@GetMapping
	@RequestMapping(value = "/cargahoraria")
	public ResponseEntity getCargaHorariaByProject(@RequestParam String project,@RequestParam String dtFinal,@RequestParam String dtInicial) {
		return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getReportDTOs(dtInicial,dtFinal,project));
	}
	@GetMapping
	@RequestMapping(value = "/percent")
	public ResponseEntity getPercentByTeamAndDate(@RequestParam String date, @RequestParam String team) {
		return ResponseEntity.status(HttpStatus.OK).body(dataTaskService.getPercentByTeam(date,team));
	}
	@GetMapping
	@RequestMapping("/resumooperacao")
	public ResponseEntity getResumoOperacao(@RequestParam String start,@RequestParam String end) {
        HttpHeaders headers = new HttpHeaders();

        try {
        	return ResponseEntity
            		.ok()
            		.headers(headers)
            		.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            		.body(excelService.createExcel(start, end));
        }catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	@RequestMapping("/previstorealizado")
	public ResponseEntity getPrevistoRealizado(@RequestParam(name = "idBrand",required = false) Long idBrand,@RequestParam  String initialDate,@RequestParam String finalDate) {
        HttpHeaders headers = new HttpHeaders();
		try {
			Brand brand = brandService.findById(idBrand);
			List<String[]> datas = dataTaskService.getPrevistoRealizaoToReport(brand,LocalDate.parse(initialDate), LocalDate.parse(finalDate));
		    byte[] excelFile = excelService.createExcelPrevistoRealizado(datas);
		    
		    return ResponseEntity
            		.ok()
            		.headers(headers)
            		.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            		.body(excelFile);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
