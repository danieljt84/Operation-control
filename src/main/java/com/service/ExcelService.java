package com.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.model.Promoter;
import com.repository.DataTaskRepository;

@Component
public class ExcelService {

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	@Autowired
	DataTaskRepository dataTaskRepository;
	@Autowired
	DataTaskService dataTaskService;

	public void createExcel() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		try {
			XSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("DATA");
			rowhead.createCell(1).setCellValue("TIME");
			rowhead.createCell(2).setCellValue("PROMOTER");
			rowhead.createCell(3).setCellValue("SITUACAO");
			rowhead.createCell(4).setCellValue("CARGA HORARIA");
			rowhead.createCell(5).setCellValue("VT");
			rowhead.createCell(6).setCellValue("SEG");
			rowhead.createCell(7).setCellValue("TER");
			rowhead.createCell(8).setCellValue("QUA");
			rowhead.createCell(9).setCellValue("QUI");
			rowhead.createCell(10).setCellValue("SEX");
			rowhead.createCell(11).setCellValue("SAB");
			rowhead.createCell(12).setCellValue("TOTAL VT");
			rowhead.createCell(13).setCellValue("DESCONTO");
            int cont =1;
			for (Object[] data : dataTaskService.convertToReport()) {
				XSSFRow row = sheet.createRow((short) cont);
				row.createCell(0).setCellValue((String) data[0]);
				row.createCell(1).setCellValue((String) data[1]);
				row.createCell(2).setCellValue((String) data[2]);
				row.createCell(3).setCellValue((String) data[3]);	
				row.createCell(4).setCellValue((String) data[4]);
				cont++;		
			}
			FileOutputStream fileOut = new FileOutputStream("C:\\Users\\I7\\Documents\\download-edge\\NewExcelFile.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			System.out.println("Your excel file has been generated!");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public byte[] createExcelByte() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		try {
			XSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("DATA");
			rowhead.createCell(1).setCellValue("TIME");
			rowhead.createCell(2).setCellValue("PROMOTER");
			rowhead.createCell(3).setCellValue("SITUACAO");
			rowhead.createCell(4).setCellValue("CARGA HORARIA");
			rowhead.createCell(5).setCellValue("VT");
			rowhead.createCell(6).setCellValue("SEG");
			rowhead.createCell(7).setCellValue("TER");
			rowhead.createCell(8).setCellValue("QUA");
			rowhead.createCell(9).setCellValue("QUI");
			rowhead.createCell(10).setCellValue("SEX");
			rowhead.createCell(11).setCellValue("SAB");
			rowhead.createCell(12).setCellValue("TOTAL VT");
			rowhead.createCell(13).setCellValue("DESCONTO");
            int cont =1;
			for (Object[] data : dataTaskService.convertToReport()) {
				XSSFRow row = sheet.createRow((short) cont);
				row.createCell(0).setCellValue((String) data[0]);
				row.createCell(1).setCellValue((String) data[1]);
				row.createCell(2).setCellValue((String) data[2]);
				row.createCell(3).setCellValue((String) data[3]);	
				row.createCell(4).setCellValue((String) data[4]);
				cont++;		
			}
			 ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			bos.close();
			workbook.close();
			return bos.toByteArray();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public void createExcelAjudaDeCusto() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		try {
			XSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(1).setCellValue("TIME");
			rowhead.createCell(2).setCellValue("PROMOTER");
			rowhead.createCell(5).setCellValue("VT");
			rowhead.createCell(6).setCellValue("SEG");
			rowhead.createCell(7).setCellValue("TER");
			rowhead.createCell(8).setCellValue("QUA");
			rowhead.createCell(9).setCellValue("QUI");
			rowhead.createCell(10).setCellValue("SEX");
			rowhead.createCell(11).setCellValue("SAB");
			rowhead.createCell(12).setCellValue("TOTAL VT");
			rowhead.createCell(13).setCellValue("DESCONTO");
            int cont =1;
            for (Object[] data : dataTaskService.convertToReportAjudaDeCusto()) {
				XSSFRow row = sheet.createRow((short) cont);
				row.createCell(0).setCellValue(((Promoter) data[0]).getTeam().getName());
				row.createCell(1).setCellValue(((Promoter) data[0]).getName());
				row.createCell(3).setCellValue((Integer) data[1]);
				row.createCell(4).setCellValue((Integer) data[2]);	
				row.createCell(5).setCellValue((Integer) data[3]);
				row.createCell(6).setCellValue((Integer) data[4]);
				row.createCell(7).setCellValue((Integer) data[5]);
				row.createCell(8).setCellValue((Integer) data[6]);

				cont++;		
			}
			FileOutputStream fileOut = new FileOutputStream("C:\\Users\\I7\\Documents\\download-edge\\NewExcelFile.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			System.out.println("Your excel file has been generated!");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
