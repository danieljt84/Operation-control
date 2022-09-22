package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.DataTaskService;
import com.service.ExcelService;

@RestController
@RequestMapping(value = "/report")
public class RelatorioController {
	
	@Autowired
	ExcelService excelService;
	@Autowired
	DataTaskService dataTaskService;	
	
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

	
	
	private ResponseEntity<byte[]>  createResponseEntity(byte[] report,String filename) {
		return ResponseEntity.ok() 
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
        .body(report);
	}
	        

}
