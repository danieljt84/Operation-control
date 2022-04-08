package com.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelService {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	//Escreve o aquivo .xls
	public void write(List<Object[]> objects ) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("ATI. PENDENTES");
		
		
	}
	
}
	