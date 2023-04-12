package com.service.operation;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.model.Project;
import com.model.Promoter;
import com.repository.operation.DataTaskRepository;
import com.util.model.AjudaDeCusto;

@Component
public class ExcelService {

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	@Autowired
	DataTaskRepository dataTaskRepository;
	@Autowired
	DataTaskService dataTaskService;
	private XSSFRow row;
	private XSSFCell cell;
	private XSSFCellStyle styleGray;
	private XSSFCellStyle styleGreen;
	private XSSFCellStyle styleRed;
	private XSSFCellStyle styleBlack;
	private XSSFCellStyle styleOrange;
	private FileOutputStream fileOutputStream;

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

	public byte[] createExcelPrevistoRealizado(List<String[]> datas) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int cont = 1;

		XSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("DATA");
		rowhead.createCell(1).setCellValue("LOJA");
		rowhead.createCell(2).setCellValue("STATUS");

		for (String[] data : datas) {
			XSSFRow row = sheet.createRow((short) cont++);
			row.createCell(0).setCellValue(data[0]);
			row.createCell(1).setCellValue(data[1]);
			row.createCell(2).setCellValue(data[2]);
		}
		try {
			workbook.write(bos);
			workbook.close();
		} catch (Exception e) {
		}
		return bos.toByteArray();
	}

	public byte[] createExcelRealizadoVsProgramado(List<String[]> datas, LocalDate initialDate, LocalDate finalDate)
			throws Exception {
		workbook = new XSSFWorkbook();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		getCellGreen();
		getCellGrey();
		getCellRed();
		getCellBlack();
		getCellOrange();
		getFontWhiteBold();
		long count = ChronoUnit.DAYS.between(initialDate, finalDate);
		sheet = workbook.createSheet();
		int lastrow = 0;
		row = sheet.createRow(lastrow++);
		cell = row.createCell(0);
		cell.setCellStyle(styleBlack);
		cell.setCellValue("PROJETO");
		cell = row.createCell(1);
		cell.setCellStyle(styleBlack);
		cell.setCellValue("MARCA");
		cell = row.createCell(2);
		cell.setCellStyle(styleBlack);
		cell.setCellValue("LOJA");
		for (int i = 3; (i - 3) <= count; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(styleBlack);
			cell.setCellValue(initialDate.plusDays(i - 3).format(DateTimeFormatter.ofPattern("dd/MM")));
		}
		// Capturo todas as marcas e lojas
		List<String> projects = datas.stream().map(element -> element[1]).distinct().collect(Collectors.toList());
		
		for(String project: projects) {
			List<String> brands = datas.stream().map(element -> element[2]).distinct().collect(Collectors.toList());

			for (String brand : brands) {
				var shops = datas.stream().filter(data -> data[1].equals(project) && data[2].equals(brand)).map(element -> element[3]).distinct()
						.collect(Collectors.toList());
				for (String shop : shops) {
					var datas_filter = datas.stream().filter(data -> data[1].equals(project) && data[2].equals(brand) && data[3].equals(shop))
							.collect(Collectors.toList());
					row = sheet.createRow(lastrow++);
					row.createCell(0).setCellValue(project);
					row.createCell(1).setCellValue(brand);
					row.createCell(2).setCellValue(shop);
					for (int i = 3; (i - 3) <= count; i++) {
						String date = initialDate.plusDays(i - 3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						Optional<String[]> data_filter;
						var filters = datas_filter.stream().filter(data -> data[0].equals(date))
								.collect(Collectors.toList());
						if (filters.size() == 1) {
							data_filter = Optional.of(filters.get(0));

						} else {
							if (filters.size() > 1) {
								data_filter = filters.stream().filter(element -> element[4].equals("COMPLETA")).findFirst();
								if (data_filter.isEmpty()) {
									data_filter = filters.stream().filter(element -> element[4].equals("NÃO REALIZADO"))
											.findFirst();
									if (data_filter.isEmpty()) {
										data_filter = filters.stream().filter(element -> element[4].equals("CANCELADA"))
												.findFirst();
									}
								}
							} else {
								data_filter = Optional.empty();
							}
						}

						if (data_filter.isEmpty()) {
							cell = row.createCell(i);
							cell.setCellStyle(this.styleGray);
							cell.setCellValue("NÃO PROGRAMADO");
						} else {
							if (data_filter.get()[4].contains("COMPLETA")) {
									cell = row.createCell(i);
									cell.setCellStyle(this.styleGreen);
									cell.setCellValue("REALIZADO");
									continue;
							}
							if (data_filter.get()[4].contains("NÃO REALIZADO")) {
								cell = row.createCell(i);
								cell.setCellStyle(this.styleRed);
								cell.setCellValue("NÃO REALIZADO");
								continue;

							}
							if (data_filter.get()[4].contains("CANCELADA")) {
								cell = row.createCell(i);
								cell.setCellValue("CANCELADA");
								continue;

							}
						}

					}
				}
			}
		}
		try {
			workbook.write(bos);
			workbook.close();
		} catch (Exception e) {
		}
		return bos.toByteArray();
	}

	private void getCellGrey() {
		this.styleGray = this.workbook.createCellStyle();
		this.styleGray.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		this.styleGray.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.styleGray.setAlignment(HorizontalAlignment.CENTER);
	}

	private void getCellGreen() {
		this.styleGreen = this.workbook.createCellStyle();
		this.styleGreen.setFillForegroundColor(new XSSFColor(new Color(0, 176, 80)));
		this.styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.styleGreen.setAlignment(HorizontalAlignment.CENTER);
		this.styleGreen.setFont(getFontWhiteBold());

	}

	private void getCellRed() {
		this.styleRed = this.workbook.createCellStyle();
		this.styleRed.setFillForegroundColor(new XSSFColor(Color.red));
		this.styleRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.styleRed.setAlignment(HorizontalAlignment.CENTER);
	}

	private void getCellOrange() {
		this.styleOrange = this.workbook.createCellStyle();
		this.styleOrange.setFillForegroundColor(new XSSFColor(Color.orange));
		this.styleOrange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.styleOrange.setAlignment(HorizontalAlignment.CENTER);
	}

	private void getCellBlack() {
		this.styleBlack = this.workbook.createCellStyle();
		this.styleBlack.setFillForegroundColor(new XSSFColor(java.awt.Color.BLACK));
		this.styleBlack.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.styleBlack.setAlignment(HorizontalAlignment.CENTER);
		this.styleBlack.setFont(getFontWhiteBold());
	}

	private XSSFFont getFontWhiteBold() {
		var fontBold = this.workbook.createFont();
		fontBold.setBold(true);
		fontBold.setColor(IndexedColors.WHITE.getIndex());
		return fontBold;
	}

	private XSSFFont getFontBold() {
		var fontBold = this.workbook.createFont();
		fontBold.setBold(true);
		fontBold.setColor(IndexedColors.BLACK.getIndex());
		return fontBold;
	}
}
