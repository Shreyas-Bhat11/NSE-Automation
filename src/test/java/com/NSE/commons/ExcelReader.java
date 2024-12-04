package com.NSE.commons;

import com.tr.commons.BaseClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

	/**
	 * This method is used to get the test data from excel sheet
	 * 
	 * @param filepath, sheetname, Testcasename
	 * @return
	 * @created by Ishwarya
	 */
	public static Map<Integer, Map<String, String>> getTestDataFromExcel(String filepath, String sheetname,
			String Testcasename) {

		Map<Integer, Map<String, String>> excelFileMap = new HashMap<Integer, Map<String, String>>();
		try {
			FileInputStream fis = new FileInputStream(filepath);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetname);

			BaseClass.log.info("sheetName " + sheet.getSheetName());
			int lastRow = sheet.getLastRowNum();
			BaseClass.log.info("row " + lastRow);

			Map<String, String> dataMap = new HashMap<String, String>();
			for (int i = 1; i <= lastRow; i++) {

				Row row = sheet.getRow(0);
				Row valuerow = sheet.getRow(i);
				Cell value = null;

				try {
					value = valuerow.getCell(0);

				} catch (Exception e) {
					BaseClass.log.error("Exception caught here as the cell values were null.");
					break;

				}
				if (value.toString().equalsIgnoreCase(Testcasename)) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						Cell data_key = row.getCell(j);
						Row testcaserow = sheet.getRow(i);
						Cell data_value = testcaserow.getCell(j);
						BaseClass.log.info(j + "data_key: " + data_key + ":" + data_value);
						String Key = data_key.getStringCellValue().trim();
						try {
							String Value = data_value.getStringCellValue().trim();
							dataMap.put(Key, Value);
							Map<String, String> tempMap = new HashMap<String, String>();
							tempMap.putAll(dataMap);
							excelFileMap.put(i, tempMap);
						} catch (NullPointerException e) {
							BaseClass.log.error("Exception caught here as the cell values were null.");
						}
					}
				}
			}
			workbook.close();
			fis.close();
			BaseClass.log.info("Final data map obtained size: " + excelFileMap.size());

		} catch (Exception e) {
			BaseClass.log.error("Error reading Excel file: " + e.getMessage());
		}
		return excelFileMap;
	}

	/**
	 * This method is used to get the master data from excel sheet
	 * 
	 * @param filepath, sheetname, url, environment
	 * @return
	 * @created by Ishwarya
	 */
	public static Map<Integer, Map<String, String>> getMasterDataFromExcel(String filepath, String sheetname,
			String url, String environment, String creditCardType) {

		Map<Integer, Map<String, String>> excelFileMap = new HashMap<Integer, Map<String, String>>();

		try {
			FileInputStream fis = new FileInputStream(filepath);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetname);
			BaseClass.log.info("sheet " + sheet.getSheetName());
			int lastRow = sheet.getLastRowNum();
			BaseClass.log.info("row " + lastRow);
			if (creditCardType != null && creditCardType.contains(" ")) {
				creditCardType = creditCardType.trim();
			}
			// Get the column index of the url
			Row headerRow = sheet.getRow(0);
			int urlColIndex = -1;
			Map<String, String> dataMap = new HashMap<String, String>();
			for (int j = 1; j < headerRow.getLastCellNum(); j++) {
				Cell cell = headerRow.getCell(j);
				String columnHeadingValue = cell.getStringCellValue();
				if (!url.equals("")) {
					if (creditCardType != null || !creditCardType.equals("")) {
						if (columnHeadingValue.trim().equalsIgnoreCase(url)
								|| columnHeadingValue.equalsIgnoreCase("SFDC User ID")
								|| columnHeadingValue.equalsIgnoreCase("SFDC User password")
								|| columnHeadingValue.equalsIgnoreCase(creditCardType)) {
							urlColIndex = j;

							// Iterate over each row

							int rowNumber = 0;
							for (int i = 1; i <= lastRow; i++) {
								Row row = sheet.getRow(i);
								Cell envCell = row.getCell(0);
								String envValue = envCell.getStringCellValue().trim();
								if (envValue.equalsIgnoreCase(environment)) {
									Cell urlValueCell = row.getCell(urlColIndex);
									String urlValues = urlValueCell.getStringCellValue().trim();
									Cell data_key = headerRow.getCell(urlColIndex);
									String Key = data_key.getStringCellValue().trim();
									dataMap.put(Key, urlValues);
									rowNumber = i;
								}
							}
							excelFileMap.put(rowNumber, dataMap);
						}
					} else {
						if (columnHeadingValue.trim().equalsIgnoreCase(url)
								|| columnHeadingValue.equalsIgnoreCase("SFDC User ID")
								|| columnHeadingValue.equalsIgnoreCase("SFDC User password")) {
							urlColIndex = j;

							// Iterate over each row
							int rowNumber = 0;
							for (int i = 1; i <= lastRow; i++) {
								Row row = sheet.getRow(i);
								Cell envCell = row.getCell(0);
								String envValue = envCell.getStringCellValue().trim();
								if (envValue.equalsIgnoreCase(environment)) {
									Cell urlValueCell = row.getCell(urlColIndex);
									String urlValues = urlValueCell.getStringCellValue().trim();
									Cell data_key = headerRow.getCell(urlColIndex);
									String Key = data_key.getStringCellValue().trim();
									dataMap.put(Key, urlValues);
									rowNumber = i;
								}
							}
							excelFileMap.put(rowNumber, dataMap);
						}
					}
				} else {
					if (creditCardType != null || !creditCardType.equals("")) {
						if (columnHeadingValue.equalsIgnoreCase("SFDC User ID")
								|| columnHeadingValue.equalsIgnoreCase("SFDC User password")
								|| columnHeadingValue.equalsIgnoreCase(creditCardType)) {
							urlColIndex = j;

							// Iterate over each row

							int rowNumber = 0;
							for (int i = 1; i <= lastRow; i++) {
								Row row = sheet.getRow(i);
								Cell envCell = row.getCell(0);
								String envValue = envCell.getStringCellValue().trim();
								if (envValue.equalsIgnoreCase(environment)) {
									Cell urlValueCell = row.getCell(urlColIndex);
									String urlValues = urlValueCell.getStringCellValue().trim();
									Cell data_key = headerRow.getCell(urlColIndex);
									String Key = data_key.getStringCellValue().trim();
									dataMap.put(Key, urlValues);
									rowNumber = i;
								}
							}
							excelFileMap.put(rowNumber, dataMap);
						}
					} else {
						if (columnHeadingValue.equalsIgnoreCase("SFDC User ID")
								|| columnHeadingValue.equalsIgnoreCase("SFDC User password")) {
							urlColIndex = j;

							// Iterate over each row

							int rowNumber = 0;
							for (int i = 1; i <= lastRow; i++) {
								Row row = sheet.getRow(i);
								Cell envCell = row.getCell(0);
								String envValue = envCell.getStringCellValue().trim();
								if (envValue.equalsIgnoreCase(environment)) {
									Cell urlValueCell = row.getCell(urlColIndex);
									String urlValues = urlValueCell.getStringCellValue().trim();
									Cell data_key = headerRow.getCell(urlColIndex);
									String Key = data_key.getStringCellValue().trim();
									dataMap.put(Key, urlValues);
									rowNumber = i;
								}
							}
							excelFileMap.put(rowNumber, dataMap);
						}

					}
				}
			}

			workbook.close();
			fis.close();
			BaseClass.log.info("Final data map obtained size: " + excelFileMap.size());

		} catch (Exception e) {
			BaseClass.log.error("Error reading Excel file: " + e.getMessage());
		}
		return excelFileMap;
	}

	/**
	 * This method is used to set the test data in excel sheet
	 * 
	 * @param filepath, sheetname, Testcasename, Dataset
	 * @return
	 * @created by Saleem
	 * @modified by Ishwarya
	 */
	public static void writeDataToExcelBasedOnTestName(String filepath, String sheetname, String testcasename,
			Map<String, String> columnValueMap) {
		try {
			FileInputStream fis = new FileInputStream(filepath);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetname);

			int lastRow = sheet.getLastRowNum();
			Map<String, Integer> columnIndices = new HashMap<>();

			// Find target columns based on the column names
			Row headerRow = sheet.getRow(0);
			for (Cell cell : headerRow) {
				String columnName = cell.getStringCellValue().trim();
				if (columnValueMap.containsKey(columnName)) {
					columnIndices.put(columnName, cell.getColumnIndex());
				}
			}

			// Ensure all specified columns exist in the sheet
			for (String columnName : columnValueMap.keySet()) {
				if (!columnIndices.containsKey(columnName)) {
					throw new IllegalArgumentException("Column " + columnName + " not found in sheet " + sheetname);
				}
			}

			// Find the target row based on testcasename and dataset
			for (int i = 1; i <= lastRow; i++) {
				Row row = sheet.getRow(i);
				Cell testCaseCell = row.getCell(0);

				if (testCaseCell.getStringCellValue().trim().equalsIgnoreCase(testcasename)) {

					// Set values for each specified column
					for (Map.Entry<String, String> entry : columnValueMap.entrySet()) {
						String columnName = entry.getKey();
						String valueToWrite = entry.getValue();

						int targetColumn = columnIndices.get(columnName);
						Cell targetCell = row.getCell(targetColumn);
						if (targetCell == null) {
							targetCell = row.createCell(targetColumn);
						}
						targetCell.setCellValue(valueToWrite);
					}
					break;
				}
			}

			// Write the changes to the file
			fis.close();
			FileOutputStream fos = new FileOutputStream(filepath);
			workbook.write(fos);
			workbook.close();
			fos.close();
		} catch (IOException e) {
			BaseClass.log.error("Error writing to Excel file: " + e.getMessage());
		}
	}

//    BELOW METHODS ARE NOT USED IN THE PROJECT AND SHOULD BE MOFIDIED IF USED

//	/**
//	 * This method is used to get the test data from excel sheet
//	 * 
//	 * @param filepath, sheetname, Testcasename, Dataset, Operation, Execute_flag
//	 * @return
//	 * @created by Saleem
//	 */
//	public static Map<Integer, Map<String, String>> setMapData(String filepath, String sheetname, String Testcasename,
//			String Dataset, String Operation, String Execute_flag) {
//
//		Map<Integer, Map<String, String>> excelFileMap = new HashMap<Integer, Map<String, String>>();
//		try {
//			FileInputStream fis = new FileInputStream(filepath);
//			Workbook workbook = new XSSFWorkbook(fis);
//			Sheet sheet = workbook.getSheet(sheetname);
//			BaseClass.log.info("sheetName " + sheet.getSheetName());
//			int lastRow = sheet.getLastRowNum();
//			BaseClass.log.info("row " + lastRow);
//			Map<String, String> dataMap = new HashMap<String, String>();
//			int count = lastRow;
//			for (int i = 1; i <= lastRow; i++) {
//
//				Row row = sheet.getRow(0);
//				Cell key = row.getCell(i);
//
//				Row valuerow = sheet.getRow(i);
//				Cell value = null;
//				Cell value1 = null;
//				Cell value2 = null;
//				Cell value3 = null;
//				try {
//					value = valuerow.getCell(0);
//					value1 = valuerow.getCell(1);
//					value2 = valuerow.getCell(2);
//					value3 = valuerow.getCell(3);
//
//				} catch (Exception e) {
//					BaseClass.log.error("Exception caught here as the cell values were null.");
//					break;
//
//				}
//				if (value.toString().equalsIgnoreCase(Testcasename) && value1.toString().equalsIgnoreCase(Dataset)
//						&& value2.toString().equalsIgnoreCase(Operation)
//						&& value3.toString().equalsIgnoreCase(Execute_flag)) {
//					for (int j = 0; j < row.getLastCellNum(); j++) {
//						Cell data_key = row.getCell(j);
//						Row testcaserow = sheet.getRow(i);
//						Cell data_value = testcaserow.getCell(j);
//						String Key = data_key.getStringCellValue().trim();
//						try {
//							String Value = data_value.getStringCellValue().trim();
//							dataMap.put(Key, Value);
//							Map<String, String> tempMap = new HashMap<String, String>();
//							tempMap.putAll(dataMap);
//							excelFileMap.put(i, tempMap);
//						} catch (NullPointerException e) {
//							BaseClass.log.error("Exception caught here as the cell values were null.");
//						}
//					}
//				}
//			}
//			workbook.close();
//			fis.close();
//			BaseClass.log.info("Final data map obtained size: " + excelFileMap.size());
//		} catch (Exception e) {
//			BaseClass.log.error("Error reading Excel file: " + e.getMessage());
//		}
//		return excelFileMap;
//	}
//	
//	/**
//	 * This method is used to get the master data from excel sheet
//	 * 
//	 * @param filepath sheetname Execute_flag
//	 * @createdby saleem
//	 */
//	public static Map<Integer, Map<String, String>> getMasterData(String filepath, String sheetname,
//			String Execute_flag) {
//
//		Map<Integer, Map<String, String>> excelFileMap = new HashMap<Integer, Map<String, String>>();
//		try {
//			FileInputStream fis = new FileInputStream(filepath);
//			Workbook workbook = new XSSFWorkbook(fis);
//			Sheet sheet = workbook.getSheet(sheetname);
//			BaseClass.log.info("sheet " + sheet.getSheetName());
//			int lastRow = sheet.getLastRowNum();
//			BaseClass.log.info("row " + lastRow);
//			Map<String, String> dataMap = new HashMap<String, String>();
//			int count = lastRow;
//			for (int i = 1; i <= lastRow; i++) {
//
//				Row row = sheet.getRow(0);
//				Cell key = row.getCell(i);
//				Row valuerow = sheet.getRow(i);
//				Cell value = null;
//				Cell value1 = null;
//				Cell value2 = null;
//				Cell value3 = null;
//				try {
//					value1 = valuerow.getCell(1);
//				} catch (Exception e) {
//					BaseClass.log.error("Exception caught here as the cell values were null.");
//					break;
//
//				}
//				if (value1.toString().equalsIgnoreCase(Execute_flag)) {
//					for (int j = 0; j < row.getLastCellNum(); j++) {
//						Cell data_key = row.getCell(j);
//						Row testcaserow = sheet.getRow(i);
//						Cell data_value = testcaserow.getCell(j);
//						String Key = data_key.getStringCellValue().trim();
//						try {
//							String Value = data_value.getStringCellValue().trim();
//							dataMap.put(Key, Value);
//							Map<String, String> tempMap = new HashMap<String, String>();
//							tempMap.putAll(dataMap);
//							excelFileMap.put(i, tempMap);
//						} catch (NullPointerException e) {
//							BaseClass.log.error("Exception caught here as the cell values were null.");
//						}
//					}
//				}
//			}
//			workbook.close();
//			fis.close();
//			BaseClass.log.info("Final data map obtained size: " + excelFileMap.size());
//		} catch (Exception e) {
//			BaseClass.log.error("Error reading Excel file: " + e.getMessage());
//		}
//		return excelFileMap;
//	}
}