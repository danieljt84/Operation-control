package com.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.model.Promoter;
import com.repository.DataTaskRepository;
import com.util.model.AjudaDeCusto;

@Component
public class ExcelService {

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	@Autowired
	DataTaskRepository dataTaskRepository;
	@Autowired
	DataTaskService dataTaskService;

	public ExcelService() {
	}

	public byte[] createExcel(String start, String end) throws IOException {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("DATA");
		rowhead.createCell(1).setCellValue("TIME");
		rowhead.createCell(2).setCellValue("PROMOTER");
		rowhead.createCell(3).setCellValue("SITUACAO");
		rowhead.createCell(4).setCellValue("CARGA HORARIA");
		int cont = 1;
		for (Object[] data : dataTaskService.convertToReport(start, end)) {
			XSSFRow row = sheet.createRow((short) cont);
			row.createCell(0).setCellValue((String) data[0]);
			row.createCell(1).setCellValue((String) data[1]);
			row.createCell(2).setCellValue((String) data[2]);
			row.createCell(3).setCellValue((String) data[3]);
			row.createCell(4).setCellValue((String) data[4]);
			cont++;
		}
		workbook.write(bos);
		workbook.close();
		return bos.toByteArray();
	}

	public byte[] createExcelByte(String date) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
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
			int cont = 1;
			for (Object[] data : dataTaskService.convertToReport(date, date)) {
				XSSFRow row = sheet.createRow((short) cont);
				row.createCell(0).setCellValue((String) data[0]);
				row.createCell(1).setCellValue((String) data[1]);
				row.createCell(2).setCellValue((String) data[2]);
				row.createCell(3).setCellValue((String) data[3]);
				row.createCell(4).setCellValue((String) data[4]);
				cont++;
			}
			workbook.write(bos);
			workbook.close();
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	public void createExcelAjudaDeCusto(String start, String end) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		try {
			XSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("TIME");
			rowhead.createCell(1).setCellValue("PROMOTER");
			rowhead.createCell(2).setCellValue("MÉDIA");
			rowhead.createCell(3).setCellValue("VT");
			rowhead.createCell(4).setCellValue("SEG");
			rowhead.createCell(5).setCellValue("TER");
			rowhead.createCell(6).setCellValue("QUA");
			rowhead.createCell(7).setCellValue("QUI");
			rowhead.createCell(8).setCellValue("SEX");
			rowhead.createCell(9).setCellValue("SAB");
			rowhead.createCell(10).setCellValue("DIAS TRABALHADOS");
			rowhead.createCell(11).setCellValue("DIAS UTÉIS");
			rowhead.createCell(12).setCellValue("TOTAL VT");
			rowhead.createCell(13).setCellValue("DESCONTO VT");
			rowhead.createCell(15).setCellValue("VA");
			rowhead.createCell(16).setCellValue("DIAS DESCONTO VA");
			rowhead.createCell(17).setCellValue("TOTAL VA");
			rowhead.createCell(18).setCellValue("DESCONTO VA");
			int cont = 1;
			for (AjudaDeCusto data : dataTaskService.convertToReportAjudaDeCusto(start, end)) {
				XSSFRow row = sheet.createRow((short) cont);
				row.createCell(0).setCellValue(data.getPromoter().getTeam().getName());
				row.createCell(1).setCellValue(data.getPromoter().getName());
				if (data.getPromoter().getMediaPassagem() != null) {
					row.createCell(2).setCellValue(data.getPromoter().getMediaPassagem());
				}
				row.createCell(4).setCellValue(data.getRelacaoDias().getSegundasFeitas());
				row.createCell(5).setCellValue(data.getRelacaoDias().getTercasFeitas());
				row.createCell(6).setCellValue(data.getRelacaoDias().getQuartasFeitas());
				row.createCell(7).setCellValue(data.getRelacaoDias().getQuintasFeitas());
				row.createCell(8).setCellValue(data.getRelacaoDias().getSextasFeitas());
				row.createCell(9).setCellValue(data.getRelacaoDias().getSabadosFeitas());
				row.createCell(10).setCellValue(data.getRelacaoDias().getDiasCompletos());
				row.createCell(11).setCellValue(
						data.getRelacaoDias().getDiasCompletos() + data.getRelacaoDias().getDiasNaoFeitos());
				if (data.getPromoter().getMediaPassagem() == null) {
					row.createCell(12).setCellValue("SEM PASSAGEM CADASTRADA");
				} else {
					row.createCell(12).setCellValue(
							data.getRelacaoDias().getDiasCompletos() * data.getPromoter().getMediaPassagem());
					row.createCell(13).setCellValue(
							data.getRelacaoDias().getDiasNaoFeitos() * data.getPromoter().getMediaPassagem());
				}
				row.createCell(16).setCellValue(data.getDiasDescontoVA() + data.getRelacaoDias().getDiasNaoFeitos());
				row.createCell(17)
						.setCellValue((data.getDiasDescontoVA() + data.getRelacaoDias().getDiasNaoFeitos()) * 19);
				row.createCell(18).setCellValue(data.getDiasDescontoVA() * 19);
				cont++;
			}
			FileOutputStream fileOut = new FileOutputStream("C:\\Users\\4P\\Documents\\RESUMO OPERACAO.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			System.out.println("Your excel file has been generated!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findInfoInExcelPlanilhaCusto(Promoter promoter) {
		int numRow = sheet.getLastRowNum();
		for (int i = 0; i <= numRow; i++) {
			Optional<XSSFCell> nome = Optional.ofNullable(sheet.getRow(i).getCell(5));
			Optional<XSSFCell> cpf = Optional.ofNullable(sheet.getRow(i).getCell(7));
			if (nome.isPresent()) {
				if (nome.get().getStringCellValue().equals(promoter.getName())) {
					promoter.setEnterprise(sheet.getRow(i).getCell(2).getStringCellValue());
					promoter.setMediaPassagem(sheet.getRow(i).getCell(8).getNumericCellValue());
					break;
				}
				if (cpf.get().getStringCellValue().equals(promoter.getCpf())) {
					promoter.setEnterprise(sheet.getRow(i).getCell(2).getStringCellValue());
					promoter.setMediaPassagem(sheet.getRow(i).getCell(8).getNumericCellValue());
					break;
				} else {
					promoter.setMediaPassagem(0.0);
					promoter.setEnterprise("NÃO ENCONTRADA");
				}
			}

		}
	}

}
