package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	private ResponseEntity<byte[]>  createResponseEntity(byte[] report,String filename) {
		return ResponseEntity.ok() 
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
        .body(report);
	}
	        

}
