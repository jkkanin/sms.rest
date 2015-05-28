package com.objectfrontier.sms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.objectfrontier.sms.model.Message;

public class ExcelGen {

	public static String createExcel(List<Message> messages) {

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee Data");

		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "PHONE", "MESSAGE", "MASKS", "TIMESTAMPS" });

		int index = 2;
		for (Message message : messages) {

			data.put(String.valueOf(index), new Object[] { message.getNumber(), message.getMessage(), "", "" });
			index++;
		}

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {

			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {

				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
				}
			}
		}
		try {
			// Write the workbook in file system
			File tempFile = File.createTempFile(String.valueOf(new Date().getTime()), ".xls");
			FileOutputStream out = new FileOutputStream(tempFile);
			workbook.write(out);
			out.close();
			System.out.println(tempFile.getName() + " written successfully on disk.");
			return tempFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
