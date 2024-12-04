package com.NSE.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.NSE.ui.pages.CanadaPDPPage;
import com.NSE.ui.pages.CheckOutPage;
import com.NSE.ui.pages.OrderConfirmationPage;
import com.aventstack.extentreports.Status;
import com.github.javafaker.Faker;
import com.tr.commons.BaseClass;
import com.tr.commons.extentListeners.ExtentLogger;

public class Utility extends BaseClass {

	public static Map<String, String> dataMap = null, testdataMap = null;
	private static final String[] BELOW_TWENTY = { "", "one", "two", "three", "four", "five", "six", "seven", "eight",
			"nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen",
			"nineteen" },
			TENS = { "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" },
			THOUSANDS = { "", "thousand", "million", "billion" };
	public static HashMap<String, HashMap<String, Object>> uiDataMap = new HashMap<>();

	public static HashMap<String, Long> testCaseExecutionTime = new HashMap<>();
	public static long executionTimeDifference = 0;

	/**
	 * This method is initialize the test by setting up the test data
	 * 
	 * @created by Ishwarya
	 */
	public static void testDataSetup(String excelFilePath, String masterDataSheetName, String testDataSheetName,
			String testCaseName, String url, String environment) {

		Map<Integer, Map<String, String>> excelFileMaps = new HashMap<Integer, Map<String, String>>();
		excelFileMaps = ExcelReader.getTestDataFromExcel(excelFilePath, testDataSheetName, testCaseName);

		for (Map.Entry<Integer, Map<String, String>> entry : excelFileMaps.entrySet()) {
			testdataMap = entry.getValue();
		}
		String ccNumber = "";
		if (testdataMap.get("CreditCard") == null) {
			ccNumber = "";
		} else {
			ccNumber = testdataMap.get("CreditCard");
		}
		Map<Integer, Map<String, String>> excelFileMap = new HashMap<Integer, Map<String, String>>();
		excelFileMap = ExcelReader.getMasterDataFromExcel(excelFilePath, masterDataSheetName, url, environment,
				ccNumber);

		for (Map.Entry<Integer, Map<String, String>> entry : excelFileMap.entrySet()) {
			dataMap = entry.getValue();
		}

		logger.get().info("Master Data Map is: " + dataMap);
		logger.get().info("Test Data Map is: " + testdataMap);
	}

	/**
	 * This method is used to convert number to word
	 * 
	 * @created by Ishwarya
	 */
	public static String convertNumberToWord(int number) {
		if (number == 0) {
			return "zero";
		}
		String words = "";
		int index = 0;
		while (number > 0) {
			if (number % 1000 != 0) {
				words = convertNumberToWordHelper(number % 1000) + THOUSANDS[index] + " " + words;
			}
			number /= 1000;
			index++;
		}
		return words.trim();
	}

	/**
	 * This method is used to convert number to word helper
	 * 
	 * @created by Ishwarya
	 */
	private static String convertNumberToWordHelper(int number) {
		if (number == 0) {
			return "";
		} else if (number < 20) {
			return BELOW_TWENTY[number] + " ";
		} else if (number < 100) {
			return TENS[number / 10] + " " + convertNumberToWordHelper(number % 10);
		} else {
			return BELOW_TWENTY[number / 100] + " hundred " + convertNumberToWordHelper(number % 100);
		}
	}

	/**
	 * This method is used to generate log message
	 * 
	 * @created by Ishwarya
	 */
	private static <DataType> String generateLogMessage(String status, String fieldName, DataType actualValue,
			DataType expectedValue, String validationType, String... replacements) {
		if (validationType.equalsIgnoreCase("UI")) {
			return "<details><summary><i><font> Validation of " + fieldName + " " + status + " </font></i>"
					+ "</summary>" + "<pre>" + "<b>UI field Value: </b>" + actualValue + "<br /><b>Expected Value: </b>"
					+ expectedValue + "</pre>" + "</details> \n";
		} else if (validationType.equalsIgnoreCase("UI_UI")) {
			return String.format("<details><summary><i><font> Validation of " + fieldName + " " + status
					+ " </font></i>" + "</summary>" + "<pre>" + "<b>%s: </b>" + actualValue + "<br /><b>%s: </b>"
					+ expectedValue + "</pre>" + "</details> \n", replacements[0], replacements[1]);
		} else if (validationType.equalsIgnoreCase("UI_SFDC")) {
			return "<details><summary><i><font> Validation of " + fieldName + " " + status + " </font></i>"
					+ "</summary>" + "<pre>" + "<b>UI field Value: </b>" + actualValue + "<br /><b>SFDC Value: </b>"
					+ expectedValue + "</pre>" + "</details> \n";
		} else if (validationType.equalsIgnoreCase("SFDC")) {
			return "<details><summary><i><font> Validation of " + fieldName + " " + status + " </font></i>"
					+ "</summary>" + "<pre>" + "<b>SFDC field Value: </b>" + actualValue
					+ "<br /><b>Expected Value: </b>" + expectedValue + "</pre>" + "</details> \n";
		} else {
			return "Invalid Validation Type";
		}
	}

	/**
	 * This method is used to validate the UI data field
	 * 
	 * @param methodName, fieldName, actualValue, expectedValue, validationType(UI,
	 *                    UI_UI, UI_SFDC), screenShot, compareType(Equals,
	 *                    Contains), replacements(pass the expected and actual text
	 *                    message for UI_UI validation)
	 * @created by Ishwarya
	 */
	public static <DataType> void validateUIDataField(String methodName, String fieldName, DataType actualValue,
			DataType expectedValue, String validationType, Boolean screenShot, String compareType,
			String... replacements) {
		try {
			boolean comparisonStatus;
			if (compareType.equalsIgnoreCase("Equals")) {
				comparisonStatus = String.valueOf(actualValue).equals(String.valueOf(expectedValue).trim());
				log.info("Comparison Status of the " + fieldName + " is: "
						+ (comparisonStatus == true ? Status.PASS : Status.FAIL));
			} else if (compareType.equalsIgnoreCase("Contains")) {
				comparisonStatus = String.valueOf(actualValue).contains(String.valueOf(expectedValue).trim());
				log.info("Comparison Status of the " + fieldName + " is: "
						+ (comparisonStatus == true ? Status.PASS : Status.FAIL));
			} else if (compareType.equalsIgnoreCase("NotEquals")) {
				comparisonStatus = !(String.valueOf(actualValue).equals(String.valueOf(expectedValue).trim()));
				log.info("Comparison Status of the " + fieldName + " is: "
						+ (comparisonStatus == true ? Status.PASS : Status.FAIL));
			} else {
				log.info("Invalid compare type");
				comparisonStatus = false;
			}

			String logMessage = generateLogMessage(comparisonStatus ? "passed" : "failed", fieldName, actualValue,
					expectedValue, validationType, replacements);
			if (comparisonStatus) {
				ExtentLogger.pass(logMessage, screenShot);
			} else {
				ExtentLogger.fail(logMessage, screenShot);
			}

			if (compareType.equalsIgnoreCase("Equals")) {
				softAssert.assertEquals(actualValue, expectedValue);
//				Assert.assertEquals(actualValue, expectedValue);
			} else if (compareType.equalsIgnoreCase("Contains")) {
				softAssert.assertTrue(String.valueOf(actualValue).contains(String.valueOf(expectedValue).trim()));
//				Assert.assertTrue(String.valueOf(actualValue).contains(String.valueOf(expectedValue).trim()));
			} else if (compareType.equalsIgnoreCase("NotEquals")) {
				softAssert.assertNotEquals(actualValue, expectedValue);
//				Assert.assertNotEquals(actualValue, expectedValue);
			}

		} catch (

		Exception e) {
			logger.get().info("Exception caught in method: " + methodName + " Error: " + e.getMessage());
		}
	}

	/**
	 * This method is used to generate logs based on conditions passed in the status
	 * parameter
	 * 
	 * @param methodName, status, passMessage, failMessage, screen
	 * @created by Ishwarya
	 */
	public static void logGenerator(String methodName, Boolean status, String passMessage, String failMessage,
			Boolean screenShot) {
		try {
			if (status) {

				ExtentLogger.pass(passMessage, screenShot);

			} else {
				ExtentLogger.fail(failMessage, screenShot);
			}
		} catch (Exception e) {
			logger.get().info("Exception caught in method: " + methodName + " Error: " + e.getMessage());
		}
	}

	/**
	 * This method is to store the data from UI flow
	 * 
	 * @created by Ishwarya
	 */
	public static void storeUIData(String testCaseName) {

		HashMap<String, Object> uiInnerMap = new HashMap<>();
		uiInnerMap.put("OrderNumber", OrderConfirmationPage.orderNumber);
		uiInnerMap.put("FirstName", CheckOutPage.firstName);
		uiInnerMap.put("LastName", CheckOutPage.lastName);
		uiInnerMap.put("Email", CheckOutPage.email);
		uiInnerMap.put("ProductNameList", CheckOutPage.productNamesList);
		uiInnerMap.put("PhoneNumber", CheckOutPage.phoneNumber);
		uiInnerMap.put("CanadaProductFormat", CanadaPDPPage.productFormat);
		uiInnerMap.put("OrganizationType", CheckOutPage.organizationType);
		uiDataMap.put(testCaseName, uiInnerMap);
		logger.get().info("Updated UI Data Map is: " + uiInnerMap);
	}

	/**
	 * This method is used to store the execution time of the test case
	 * 
	 * @param testCaseName
	 */
	public static void storeExecutionTime(String testCaseName) {

		Long currentTime = System.currentTimeMillis();
		testCaseExecutionTime.put(testCaseName, currentTime);
		logger.get().info("Test Case " + testCaseName + " executed at: " + currentTime);
	}

	/**
	 * This method is used to retrieve the time difference of the test execution
	 * time and the time the sdfc flow is to be started after 15 mins
	 * 
	 * @param testCaseExecutionTime
	 * @param testCaseName
	 * @return
	 * @created by Ishwarya
	 */
	public static long retrieveTimeDiffrence(HashMap<String, Long> testCaseExecutionTime, String testCaseName) {
		long storedTime = testCaseExecutionTime.get(testCaseName);
		long storedTimePlusTenMins = storedTime + TimeUnit.MINUTES.toMillis(15);
		long currentTime = System.currentTimeMillis();
		executionTimeDifference = TimeUnit.MILLISECONDS.toSeconds(storedTimePlusTenMins - currentTime);
		logger.get().info("Test Case " + testCaseName + " executed at: " + storedTime
				+ " and required time to load order status is: " + executionTimeDifference);
		return executionTimeDifference;
	}

	/**
	 * This method is used to retrieve the UI data
	 *
	 * @param dataMap,testCaseName
	 * @return
	 * @created by Ishwarya
	 */
	public static HashMap<String, Object> retriveUIData(HashMap<String, HashMap<String, Object>> dataMap,
			String testCaseName) {

		HashMap<String, Object> uiInnerMap = dataMap.get(testCaseName);
		logger.get().info("Retrived UI Data Map is: " + uiInnerMap);
		return uiInnerMap;
	}

	/**
	 * This method is used to remove special characters from the string
	 * 
	 * @created by Ishwarya
	 */
	public static String removeSpecialChars(String input) {
		return input.replaceAll("[^a-zA-Z0-9 ]", "");
	}

	/**
	 * This method is used to convert the string to title case
	 * 
	 * @created by Ishwarya
	 */
	public static String toTitleCase(String input) {
		String[] words = input.split(" ");
		StringBuilder titleCase = new StringBuilder();

		for (String word : words) {
			if (titleCase.length() > 0) {
				titleCase.append(" ");
			}

			titleCase.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase());
		}

		return titleCase.toString();
	}

	/**
	 * This method is to generate a RandomNumber within the given range
	 * 
	 * @created by Ishwarya
	 */

	public static int generateNumberFromRange(int first, int last) {
		if (first == last) {
			return first;
		}
		return ThreadLocalRandom.current().nextInt(first, last);
	}

	/**
	 * This method generates a random 3 or 4 digit number.
	 *
	 * @return a random 3 or 4 digit number
	 */
	public static int generate3Or4DigitNumber(int... count) {
		Random rand = new Random();
		if (count[0] == 3) {
			return rand.nextInt(900) + 100;
		} else if (count[0] == 4) {
			return rand.nextInt(9000) + 1000;
		} else {

			if (rand.nextBoolean()) {
				return rand.nextInt(900) + 100; // This will generate a random 3 digit number
			} else {
				return rand.nextInt(9000) + 1000; // This will generate a random 4 digit number
			}
		}
	}

	/**
	 * This method is used to format the number to the 2 digits number
	 * 
	 * @param number
	 * @return
	 */
	public static String formatTo2DigitNumber(int number) {
		return number < 10 ? "0" + number : String.valueOf(number);
	}

	/**
	 * This method is used to format the number to the 2 digits number
	 * 
	 * @param number
	 * @return
	 */
	public static String formatFloatTo2DigitNumber(float number) {
		return String.format("%.2f", number);
	}

	/**
	 * This method is used to get the string using pattern
	 * 
	 * @param inputStr,patternStr
	 * @return
	 */
	public static String getStingUsingPattern(String inputStr, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(inputStr);
		String outputStr = "";
		if (matcher.find()) {
			outputStr = matcher.group().trim();
		} else {
			log.info("No match found.");
		}
		return outputStr;
	}

	/**
	 * This method is used to generate fake data
	 * 
	 * @created by Ishwarya
	 */
	public static String getFakeString(String type) {
		Faker fake = new Faker();
		String outputString = "";
		if (type.equalsIgnoreCase("FirstName")) {
			outputString = fake.name().firstName().replace("'", "");
		} else if (type.equalsIgnoreCase("LastName")) {
			outputString = fake.name().lastName().replace("'", "");
		} else if (type.equalsIgnoreCase("PhoneNumber")) {
			outputString = fake.phoneNumber().cellPhone();
		} else if (type.equalsIgnoreCase("OrganizationName")) {
			outputString = fake.company().name().replace("'", "");
		}
		return outputString;
	}

	public class DataGenerator {
		private static final Faker faker = new Faker();

		public static String generateStringWithExactLength(String input, int length) {
			if (input.length() < length) {
				return String.format("%-" + length + "s", input).replace(' ', 'X'); // Pad with 'X' to make it the
																					// required length
			} else if (input.length() > length) {
				return input.substring(0, length); // Truncate to the required length
			}
			return input;
		}

		public static String generateFirstName(int length) {
			return generateStringWithExactLength(faker.name().firstName(), length);
		}

		public static String generateLastName(int length) {
			return generateStringWithExactLength(faker.name().lastName(), length);
		}

		public static String generateEmail(int length) {
			String email = faker.internet().emailAddress();
			return generateStringWithExactLength(email, length);
		}

		public static String generateOrganizationName(int length) {
			return generateStringWithExactLength(faker.company().name(), length);
		}
	}
}