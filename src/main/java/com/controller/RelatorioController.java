package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.ExcelService;

@RestController
@RequestMapping(value = "/report")
public class RelatorioController {
	
	@Autowired
	ExcelService excelService;
	
	@GetMapping
	@RequestMapping(value = "/diario")
	public ResponseEntity getRelatorioDiario() {
		return createResponseEntity(excelService.createExcelByte(), "RELATORIO DIARIO");
	}
	
	private ResponseEntity<byte[]>  createResponseEntity(byte[] report,String filename) {
		return ResponseEntity.ok() 
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
        .body(report);
	}
	        

}
