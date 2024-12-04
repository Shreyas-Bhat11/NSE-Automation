package com.NSE.ui.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.ExcelReader;
import com.NSE.commons.Utility;
import com.tr.commons.ReadJson;
import com.tr.commons.extentListeners.ExtentLogger;

import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOutPage extends DcpBasePage {

	public static String firstName = "", lastName = "", email = "", estimatedTaxInString = "",
			estimatedTotalInString = "", phoneNumber = "", organizationName = "", cardNumber = "", address = "",
			cardNameHolder = "", firstnameFromTextBox = "", organizationType = "";
	static double estimatedTax = 0, estimatedTotal = 0;
	public static List<String> productNamesList = new ArrayList<>();

	// Variables to store JSON data
	static String checkOutPageDataFileName = "CheckOutPageData.json";

	public CheckOutPage() {
		super("locatorsDefinition/CheckoutPage.json");
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method to validate if user is landed in the checkout page
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage verifyLandedOnCheckoutPage() {

		ExtentLogger.info("Validating if user is landed on checkout page");
		waitForPageLoad();
		waitUntilElementVisible("FirstNameTextBox");
		waitUntilElementVisible("CheckoutHeader");
		ExtentLogger.info("Page Title is: " + get_Title());
		Utility.logGenerator("verifyLandedOnCheckoutPage",
				get_Title().contains(ReadJson.getStringValue(checkOutPageDataFileName, "CHECKOUT_PAGE_TITLE")),
				"Landed on checkout page", "Cannot landed on checkout page", true);
		return this;
	}

	/**
	 * This method to enter first name in the checkout
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterFirstName() {

		waitForPageLoad();
		firstName = Utility.getFakeString("FirstName");
		waitUntilElementVisible("FirstNameTextBox");
		sendKeysTotheElement("FirstNameTextBox", firstName);
		ExtentLogger.info("Entered First Name: " + firstName);
		return this;
	}

	/**
	 * This method to enter first name in the checkout
	 *
	 * @modified by Yunus
	 */
	public CheckOutPage enterFirstNameCCC(String firstName) {

		waitForPageLoad();
		waitUntilElementVisible("FirstNameTextBox");
		sendKeysTotheElement("FirstNameTextBox", firstName);
		ExtentLogger.info("Entered First Name: " + firstName);
		return this;
	}

	/**
	 * This method to enter last name in the checkout
	 *
	 * @modified by Yunus
	 */
	public CheckOutPage enterLastNameCCC(String lastName) {

		waitForPageLoad();
		waitUntilElementVisible("LastNameTextBox");
		sendKeysTotheElement("LastNameTextBox", lastName);
		ExtentLogger.info("Entered Last Name: " + lastName);
		return this;
	}

	/**
	 * This method to get the first name from the text box
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage getFirstName() {

		waitForPageLoad();
		firstnameFromTextBox = getAttributeValueOfGivenAttribute("FirstNameTextBox", "value");
		ExtentLogger.info("Entered First Name: " + firstnameFromTextBox);
		firstnameFromTextBox = firstnameFromTextBox.trim();
		return this;
	}

	/**
	 * This method to validate the error messages in the first name field
	 *
	 * @created by Yunus
	 */
	public CheckOutPage validateErrorMessagesFirstName() {

		ExtentLogger.info("Validating error messages in the first name field");
		waitUntilElementVisible("FirstNameTextBox");
		clickOnElement("FirstNameTextBox");
		// waiting for the error message to display
		waitForFixedTime(2);
		clickOnElement("LastNameTextBox");
		Utility.validateUIDataField("validateErrorMessagesFirstName", "FirstNameError",
				getTextOfElement("FirstNameError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "FIRST_NAME_ERROR"), "UI", true, "Equals");
		firstName = Utility.DataGenerator.generateFirstName(37);
		sendKeysTotheElement("FirstNameTextBox", firstName);
		clickOnElement("LastNameTextBox");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateErrorMessagesFirstName", "FirstNameError",
				getTextOfElement("FirstNameErrorcount"),
				ReadJson.getStringValue(checkOutPageDataFileName, "FIRST_NAME_ERROR_COUNT"), "UI", true, "Equals");
		waitForFixedTime(2);
		clearTextOfTheElement("FirstNameTextBox");
		waitForFixedTime(3);
		firstName = Utility.DataGenerator.generateFirstName(34);
		sendKeysTotheElement("FirstNameTextBox", firstName);
		clickOnElement("LastNameTextBox");
		return this;
	}

	/**
	 * This method to validate the error messages in the last name field
	 *
	 * @created by Yunus
	 */
	public CheckOutPage validateErrorMessagesLastName() {

		ExtentLogger.info("Validating error messages in the last name field");
		waitUntilElementVisible("LastNameTextBox");
		clickOnElement("LastNameTextBox");
		// waiting for the error message to display
		waitForFixedTime(2);
		clickOnElement("EmailAddressTextBox");
		Utility.validateUIDataField("validateErrorMessagesLastName", "LastNameError", getTextOfElement("LastNameError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "LAST_NAME_ERROR"), "UI", true, "Equals");
		lastName = Utility.DataGenerator.generateLastName(37);
		sendKeysTotheElement("LastNameTextBox", lastName);
		clickOnElement("EmailAddressTextBox");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateErrorMessagesLastName", "LastNameError",
				getTextOfElement("LastNameErrorcount"),
				ReadJson.getStringValue(checkOutPageDataFileName, "LAST_NAME_ERROR_COUNT"), "UI", true, "Equals");
		waitForFixedTime(2);
		// befor entering the valid data, we need to clear the invalid data
		clearTextOfTheElement("LastNameTextBox");
		waitForFixedTime(3);
		lastName = Utility.DataGenerator.generateLastName(34);
		sendKeysTotheElement("LastNameTextBox", lastName);
		clickOnElement("EmailAddressTextBox");

		return this;
	}

	/**
	 * This method to validate the error messages in the email field
	 *
	 * @created by Yunus
	 */
	public CheckOutPage validateErrorMessagesEmail() {

		ExtentLogger.info("Validating error messages in the email field");
		waitUntilElementVisible("EmailAddressTextBox");
		clickOnElement("EmailAddressTextBox");
		waitForFixedTime(2);// waiting for the error message to display
		clickOnElement("PhoneNumberTextBox");
		Utility.validateUIDataField("validateErrorMessagesEmail", "EmailError", getTextOfElement("EmailError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "EMAIL_ERROR"), "UI", true, "Equals");
		firstName = Utility.DataGenerator.generateFirstName(230);
		email = firstName + "@yopmail.com";
		sendKeysTotheElement("EmailAddressTextBox", email);
		clickOnElement("PhoneNumberTextBox");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateErrorMessagesEmail", "EmailError", getTextOfElement("EmailErrorcount"),
				ReadJson.getStringValue(checkOutPageDataFileName, "EMAIL_ERROR_COUNT"), "UI", true, "Equals");
		waitForFixedTime(3);
		// beforE entering the valid data, we need to clear the invalid data
		clearTextOfTheElement("EmailAddressTextBox");
		// check the element is empty or not
		if (getTextOfElement("EmailAddressTextBox").isEmpty()) {
			ExtentLogger.info("Email Error message is not displayed");
		} else {
			clearTextOfTheElement("EmailAddressTextBox");
			ExtentLogger.info("Email Error message is displayed");
		}
		waitForFixedTime(3);
		clearTextOfTheElement("EmailAddressTextBox");
		String wrognDomainMail = Utility.DataGenerator.generateEmail(10) + "@lexisnexis.com";
		sendKeysTotheElement("EmailAddressTextBox", wrognDomainMail);
		waitForFixedTime(3);// waiting for the error message to display
		clickOnElement("PhoneNumberTextBox");
		waitForFixedTime(4);
		Utility.validateUIDataField("validateErrorMessagesEmail", "EmailError", getTextOfElement("EmailErrorDomin"),
				ReadJson.getStringValue(checkOutPageDataFileName, "EMAIL_ERROR_Domin"), "UI", true, "Equals");
		waitForFixedTime(2);
		clearTextOfTheElement("EmailAddressTextBox");
		waitForFixedTime(3);
		lastName = Utility.DataGenerator.generateLastName(219);
		email = lastName + "@yopmail.com";
		sendKeysTotheElement("EmailAddressTextBox", email);
		clickOnElement("PhoneNumberTextBox");
		return this;
	}

	/**
	 * This method to validate the error messages in the email field for Blocked
	 * Domain
	 *
	 * @created by Yunus
	 */
	public CheckOutPage validateBlockedMailDomin() {
		ExtentLogger.info("Validating error messages in the email field");
		clearTextOfTheElement("EmailAddressTextBox");
		String wrognDomainMail = Utility.DataGenerator.generateEmail(10) + "@lexisnexis.com";
		sendKeysTotheElement("EmailAddressTextBox", wrognDomainMail);
		waitForFixedTime(3);// waiting for the error message to display
		clickOnElement("PhoneNumberTextBox");
		waitForFixedTime(4);
		Utility.validateUIDataField("validateErrorMessagesEmail", "EmailError", getTextOfElement("EmailErrorDomin"),
				ReadJson.getStringValue(checkOutPageDataFileName, "EMAIL_ERROR_Domin"), "UI", true, "Equals");
		waitForFixedTime(2);
		clearTextOfTheElement("EmailAddressTextBox");
		return this;
	}

	/**
	 * This method to validate the error messages in the phone number field
	 *
	 * @created by Yunus
	 */
	public CheckOutPage validateErrorMessagesPhoneNumber() {

		ExtentLogger.info("Validating error messages in the phone number field");
		waitUntilElementVisible("PhoneNumberTextBox");
		clickOnElement("PhoneNumberTextBox");
		waitForFixedTime(2);
		clickOnElement("OrganizationNameTextBox");
		Utility.validateUIDataField("validateErrorMessagesPhoneNumber", "PhoneNumberError",
				getTextOfElement("PhoneNumberError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "PHONE_NUMBER_ERROR"), "UI", true, "Equals");
		phoneNumber = Utility.getFakeString("PhoneNumber");
		sendKeysTotheElement("PhoneNumberTextBox", phoneNumber);
		return this;
	}

	/**
	 * This method to validate the error messages in the organization name field
	 *
	 * @created by Yunus
	 */
	public CheckOutPage validateErrorMessagesOrganizationName() {

		ExtentLogger.info("Validating error messages in the organization name field");
		waitUntilElementVisible("OrganizationNameTextBox");
		clickOnElement("OrganizationNameTextBox");
		waitForFixedTime(2);
		clickOnElement("OrganizationTypeDropdown");
		Utility.validateUIDataField("validateErrorMessagesOrganizationName", "OrganizationNameError",
				getTextOfElement("OrganizationNameError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ORGANIZATION_ERROR"), "UI", true, "Equals");
		organizationName = Utility.DataGenerator.generateOrganizationName(36);
		sendKeysTotheElement("OrganizationNameTextBox", organizationName);
		clickOnElement("OrganizationTypeDropdown");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateErrorMessagesOrganizationName", "OrganizationNameError",
				getTextOfElement("OrganizationNameError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ORGANIZATION_NAME_ERROR_COUNT"), "UI", true,
				"Equals");
		waitForFixedTime(2);
		clearTextOfTheElement("OrganizationNameTextBox");
		waitForFixedTime(3);
		organizationName = Utility.DataGenerator.generateOrganizationName(34);
		sendKeysTotheElement("OrganizationNameTextBox", organizationName);
		clickOnElement("OrganizationTypeDropdown");
		return this;
	}

	/**
	 * This method to validate the error messages in the organization type field
	 *
	 * @created by Yunus
	 */
	public CheckOutPage validateErrorMessagesOrganizationType() {

		ExtentLogger.info("Validating error messages in the organization type field");
		waitUntilElementVisible("OrganizationTypeDropdown");
		clickOnElement("OrganizationTypeDropdown");
		waitForFixedTime(2);
		clickOnElement("AddressTextBox");
		Utility.validateUIDataField("validateErrorMessagesOrganizationType", "OrganizationTypeError",
				getTextOfElement("OrganizationTypeError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ORGANIZATION_TYPE_ERROR"), "UI", true, "Equals");
		selectValueInDropdownByIndex("OrganizationTypeDropdown", 1);
		return this;
	}

	/**
	 * This method to validate the error messages in the address field
	 *
	 * @created by Yunus
	 */
	public CheckOutPage validateErrorMessagesAddress() {

		ExtentLogger.info("Validating error messages in the address field");
		waitUntilElementVisible("AddressTextBox");
		clickOnElement("AddressTextBox");
		waitForFixedTime(2);
		clickOnElement("OrganizationNameTextBox");
		Utility.validateUIDataField("validateErrorMessagesAddress", "AddressError", getTextOfElement("AddressError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ADDRESS_ERROR"), "UI", true, "Equals");
		Utility.validateUIDataField("validateErrorMessagesAddress", "AddressError",
				getTextOfElement("AddressErrorBanner"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ADDRESS_ERROR_Banner"), "UI", true, "Equals");
		sendKeysTotheElement("AddressTextBox", "123");
		waitForFixedTime(3);
		clickOnElement("EnterAddressManual");
		waitForFixedTime(3);
		clickOnElement("EnterFloor");
		String Apt_Suite_Floor = Utility.DataGenerator.generateOrganizationName(256);
		sendKeysTotheElement("EnterFloor", Apt_Suite_Floor);
		clickOnElement("EnterCity");
		waitForFixedTime(3);
		Utility.validateUIDataField("validateErrorMessagesAddress", "AddressError",
				getTextOfElement("FloorAddressError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "Floor_ADDRESS_ERROR"), "UI", true, "Equals");
		clickOnElement("EnterFloor");
		clearTextOfTheElement("EnterFloor");
		sendKeysTotheElement("EnterFloor", "UNIT 1");
		waitForFixedTime(3);
		clickOnElement("EnterCity");
		clearTextOfTheElement("EnterCity");
		clickOnElement("EnterFloor");
		waitForFixedTime(3);
		Utility.validateUIDataField("validateErrorMessagesAddress", "AddressError",
				getTextOfElement("CityAddressError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "City_ADDRESS_ERROR"), "UI", true, "Equals");
		clickOnElement("EnterCity");
		sendKeysTotheElement("EnterCity", "Mountain View");
		clickOnElement("EnterState");
		clickOnElement("EnterFloor");
		waitForFixedTime(3);
		Utility.validateUIDataField("validateErrorMessagesAddress", "AddressError",
				getTextOfElement("StateAddressError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "State_ADDRESS_ERROR"), "UI", true, "Equals");
		clickOnElement("EnterState");
		selectByTextInDropdown("EnterState", "California");
		waitForFixedTime(2);
		clickOnElement("EnterZipCode");
		clickOnElement("EnterFloor");
		waitForFixedTime(3);
		Utility.validateUIDataField("validateErrorMessagesAddress", "AddressError",
				getTextOfElement("ZipCodeAddressError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ZipCode_ADDRESS_ERROR"), "UI", true, "Equals");
		clickOnElement("EnterZipCode");
		sendKeysTotheElement("EnterZipCode", "1000");
		clickOnElement("EnterFloor");
		waitForFixedTime(3);
		Utility.validateUIDataField("validateErrorMessagesAddress", "AddressError",
				getTextOfElement("ZipCodeAddressdigitError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ZipCode_ADDRESS_ERROR_Digit"), "UI", true, "Equals");
		clearTextOfTheElement("EnterZipCode");
		sendKeysTotheElement("EnterZipCode", "85653-9544");
		return this;
	}

	/**
	 * This method to enter multiple values in the checkout page
	 * 
	 * @created by Saleem
	 */
	public CheckOutPage enterMultipleValuesInExcel(String excelFilePath, String testDataSheetName, String testcasename,
			String... columnNames) {
		Map<String, String> columnValueMap = gatherData(columnNames);
		ExcelReader.writeDataToExcelBasedOnTestName(excelFilePath, testDataSheetName, testcasename, columnValueMap);
		ExtentLogger.pass("Entered details in excel", false);
		return this;
	}

	/**
	 * This method to enter last name in the checkout page
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterLastName() {

		waitUntilElementVisible("LastNameTextBox");
		lastName = Utility.getFakeString("LastName");
		sendKeysTotheElement("LastNameTextBox", lastName);
		ExtentLogger.info("Entered Last Name: " + lastName);
		return this;
	}

	/**
	 * This method to enter email in the checkout page
	 *
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterExistingEmailID(String email_id) {

		waitUntilElementVisible("EmailAddressTextBox");
		sendKeysTotheElement("EmailAddressTextBox", email_id);
		ExtentLogger.info("Entered Email:" + email_id);
		return this;
	}

	/**
	 * This method to enter email in the checkout page
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterEmail() {

		email = firstName + lastName + "@yopmail.com";
		waitUntilElementVisible("EmailAddressTextBox");
		sendKeysTotheElement("EmailAddressTextBox", email);
		ExtentLogger.info("Entered Email: " + email);
		return this;
	}

	/**
	 * This method to enter phone number in the checkout page
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterPhoneNumber() {

		waitUntilElementVisible("PhoneNumberTextBox");
		phoneNumber = Utility.getFakeString("PhoneNumber");
		sendKeysTotheElement("PhoneNumberTextBox", phoneNumber);
		ExtentLogger.info("Entered Phone Number: " + phoneNumber);
		return this;
	}

	/**
	 * This method to enter organization name in the checkout page
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterOrganizationName() {

		waitUntilElementVisible("OrganizationNameTextBox");
		organizationName = Utility.getFakeString("OrganizationName");
		sendKeysTotheElement("OrganizationNameTextBox", organizationName);
		ExtentLogger.info("Entered Organization Name: " + organizationName);
		return this;
	}

	/**
	 * This method to select the organization type
	 * 
	 * @param sector
	 * @created by Ishwarya
	 */
	public CheckOutPage selectType(String sector) {

		ExtentLogger.info("Selecting an Organization Type");
		waitUntilElementVisible("OrganizationTypeDropdown");

		Map<String, String[]> sectorOptions = new HashMap<>();
		sectorOptions.put("General counsel",
				new String[] { "GENERAL_COUNSEL_OPTION_1", "GENERAL_COUNSEL_OPTION_2", "GENERAL_COUNSEL_OPTION_3" });
		sectorOptions.put("Law firm",
				new String[] { "LAW_FIRM_OPTION_1", "LAW_FIRM_OPTION_2", "LAW_FIRM_OPTION_3", "LAW_FIRM_OPTION_4" });
		sectorOptions.put("Government",
				new String[] { "GOVERNMENT_OPTION_1", "GOVERNMENT_OPTION_2", "GOVERNMENT_OPTION_3",
						"GOVERNMENT_OPTION_4", "GOVERNMENT_OPTION_5", "GOVERNMENT_OPTION_6", "GOVERNMENT_OPTION_7" });
		sectorOptions.put("Corporation",
				new String[] { "CORPORATION_OPTION_1", "CORPORATION_OPTION_2", "CORPORATION_OPTION_3"});
		String[] options = sectorOptions.getOrDefault(sector, new String[] {});

		clickOnElement("OrganizationTypeDropdown");
		for (int i = 0; i < options.length; i++) {
			validateOption(i + 1, options[i]);
		}

		int randomIndex = Utility.generateNumberFromRange(1, options.length);
		organizationType = options[randomIndex];
		selectValueInDropdownByIndex("OrganizationTypeDropdown", randomIndex);
		ExtentLogger.info("Selected an Organization Type");
		waitForFixedTime(2);
		return this;
	}

	/**
	 * This method is a helper method of selectType() to validate the options
	 * 
	 * @param index, optionKey
	 * @created by Ishwarya
	 */
	private void validateOption(int index, String optionKey) {

		Utility.validateUIDataField("selectType", "OrganizationType Option " + index,
				getTextOfParameterisedXpathElement("OrganizationTypeDropdownOption", "PLACEHOLDER",
						String.valueOf(index + 1)),
				ReadJson.getStringValue(checkOutPageDataFileName, optionKey), "UI", true, "Contains");
	}

	/**
	 * This method to enter address in the checkout page
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterAddress() {

		ExtentLogger.info("Entering Address in the checkout page");
		scrollToLocation(0, 300);
		waitForFixedTime(2);
		waitUntilElementVisible("AddressTextBox");
		sendKeysTotheElement("AddressTextBox", "3258");
		waitForFixedTime(3);
		clickOnElement("AddressSuggestion");
		waitForFixedTime(3);
		clickOnElement("AddressSuggestion");
		waitForFixedTime(3);
		address = getTextOfElement("AddressEnteredFirstLine") + " " + getTextOfElement("AddressEnteredSecondLine");
		ExtentLogger.info("Entered Address: " + address);
		return this;
	}

	/**
	 * This method to enter address manual in the checkout page
	 *
	 * @modified by Yunus
	 */
	public CheckOutPage enterAddressManual() {

		ExtentLogger.info("Entering Address in the checkout page");
		scrollToLocation(0, 300);
		waitForFixedTime(2);
		waitUntilElementVisible("AddressTextBox");
		sendKeysTotheElement("AddressTextBox", "123");
		waitUntilElementVisible("EnterAddressManual");
		clickOnElement("EnterAddressManual");
		waitForFixedTime(1);
		waitUntilElementVisible("EnterFloor");
		String Apt_Suite_Floor = Utility.DataGenerator.generateOrganizationName(8);
		sendKeysTotheElement("EnterFloor", Apt_Suite_Floor);
		waitForFixedTime(1);
		waitUntilElementVisible("EnterCity");
		clearTextOfTheElement("EnterCity");
		waitForFixedTime(2);
		sendKeysTotheElement("EnterCity", "Mountain View");
		waitForFixedTime(1);
		waitUntilElementVisible("EnterState");
		selectByTextInDropdown("EnterState", "California");
		waitForFixedTime(1);
		waitUntilElementVisible("EnterZipCode");
		sendKeysTotheElement("EnterZipCode", "23456");
		clearTextOfTheElement("EnterCity");
		waitForFixedTime(3);
		return this;
	}

	/**
	 * This method to enter address in the checkout page
	 *
	 * @modified by Yunus
	 */
	public CheckOutPage enterStateAddress(String state) {

		ExtentLogger.info("Entering Address in the checkout page with state");
		scrollToLocation(0, 300);
		waitForFixedTime(2);
		waitUntilElementVisible("AddressTextBox");
		sendKeysTotheElement("AddressTextBox", state);
		waitForFixedTime(3);
		clickOnElement("AddressSuggestion");
		waitForFixedTime(3);
		clickOnElement("AddressSuggestion");
		waitForFixedTime(3);
		if (state.contains("Texas") || state.contains("Wyoming") || state.contains("New Hampshire")
				|| state.contains("NEW JERSEY") || state.contains("NEW MEXICO") || state.contains("NEW YORK")
				|| state.contains("NORTH CAROLINA") || state.contains("NORTH DAKOTA") || state.contains("RHODE ISLAND")
				|| state.contains("SOUTH CAROLINA") || state.contains("SOUTH DAKOTA") || state.contains("WEST VIRGINIA")
				|| state.contains("WISCONSIN") || state.contains("WYOMING")) {
			address = getTextOfElement("EnteredAddress") + " " + getTextOfElement("EnteredCity") + " "
					+ getTextOfElement("EnteredState") + " " + getTextOfElement("EnteredZipCode");
			ExtentLogger.info("Entered Address: " + address);
		} else {
			address = getTextOfElement("AddressEnteredFirstLine") + " " + getTextOfElement("AddressEnteredSecondLine");
			ExtentLogger.info("Entered Address: " + address);
		}
		return this;
	}

	/**
	 * This method to verify the sub total in order summary
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage verifySubTotalInOrderSummary(String ppPageTotalPriceInString) {

		waitUntilElementVisible("SubTotal");
		Utility.validateUIDataField("verifySubTotalInOrderSummary", "SubTotalInOrderSummary",
				getTextOfElement("SubTotal"), ppPageTotalPriceInString, "UI", true, "Contains");
		return this;
	}

	/**
	 * This method to get the estimated tax in the order summary
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage getEstimatedTaxInOrderSummary() {

		waitForFixedTime(8);
		if (!isListEmpty("EstimatedTax")) {
			estimatedTaxInString = getTextOfElement("EstimatedTax");
			String discard = estimatedTaxInString.replaceAll("[^0-9.]", "");
			estimatedTax = Double.parseDouble(discard);
			ExtentLogger.info("Estimated Tax in Order Summary: " + estimatedTax);
		} else {
			estimatedTax = 0;
			ExtentLogger.info("Estimated Tax in Order Summary: " + estimatedTax);
		}
		return this;
	}

	/**
	 * This method to verify the estimated tax in order summary
	 * 
	 * @modified by Ishwarya, Yunush
	 */
	public CheckOutPage verifyEstimatedTotalInOrderSummary(double ppPageTotalPlanPrice) {

		waitUntilElementVisible("EstimatedTotal");
		estimatedTotalInString = getTextOfElement("EstimatedTotal");
		String discard = estimatedTotalInString.replaceAll("[^0-9.]", "");
		estimatedTotal = Double.parseDouble(discard);
		estimatedTax = getTextOfElement("EstimatedTax").isEmpty() ? 0 : estimatedTax;
		BigDecimal totalPriceWithTax = BigDecimal.valueOf(ppPageTotalPlanPrice).add(BigDecimal.valueOf(estimatedTax));
		BigDecimal estimatedTotalBD = BigDecimal.valueOf(estimatedTotal);
		BigDecimal roundedTotalPriceWithTax = totalPriceWithTax.setScale(2, RoundingMode.HALF_UP);
		BigDecimal roundedEstimatedTotalBD = estimatedTotalBD.setScale(2, RoundingMode.HALF_UP);
		ExtentLogger.info("Estimated Total in Order Summary: " + estimatedTotalInString);
		Utility.validateUIDataField("verifyEstimatedTotalInOrderSummary", "EstimatedTotalInOrderSummary",
				roundedEstimatedTotalBD, roundedTotalPriceWithTax, "UI", true, "Contains");
		return this;
	}

	/**
	 * This method to click on continue to payment button
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage clickOnContinueToPayment() {

		ExtentLogger.info("Clicking on Continue to Payment Button");
		waitUntilElementVisible("ContinueToPaymentButton");
		clickOnElement("ContinueToPaymentButton");
		waitForFixedTime(10);
		Utility.logGenerator("clickOnContinueToPayment", !isListEmpty("BankAccountNumber") == true,
				"Clicked on Continue to payment Button", "Cannot click on Continue to payment Button", true);
		return this;
	}

	/**
	 * This method to check the error validation of the place order button
	 *
	 * @created by Yunus
	 */
	public CheckOutPage checkPlaceHolder() {

		ExtentLogger.info("Checking the error validation of the place order button");
		waitForFixedTime(3);
		scrollToParticularElement("DisablePlaceOrderButton");
		checkElementIfVisible("DisablePlaceOrderButton");
		if (checkElementIfVisible("DisablePlaceOrderButton")) {
			ExtentLogger.info("Place Order Button is visible");
			Utility.logGenerator("checkPlaceHolder", !isListEmpty("DisablePlaceOrderButton") == true,
					"Place Order Button is not clickable", "Place Order Button is  clickable", true);
		} else {
			ExtentLogger.fail("Place Order Button is clickable");
		}
		return this;
	}

	/**
	 * This method to enter bank account number
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterBankAccountNumber(String bankAccountNumber) {

		scrollToLocation(0, 400);
		waitForFixedTime(2);
		waitUntilElementVisible("BankAccountNumber");
		sendKeysTotheElement("BankAccountNumber", bankAccountNumber);
		ExtentLogger.info("Entered Bank Account Number: " + bankAccountNumber);

		return this;
	}

	/**
	 * This method to validate the error messages in the bank account number field
	 *
	 * @created by Yunus
	 */
	public CheckOutPage errorValidateBankAccountNumber(String bankAccountNumber) {

		ExtentLogger.info("Validating error messages in the bank account number field");
		scrollToLocation(0, 400);
		waitForFixedTime(2);
		waitUntilElementVisible("BankAccountNumber");
		checkElementIfVisible("BankAccountNumber");
		checkElementIfVisible("RoutingNumber");
		waitForFixedTime(2);
		Utility.validateUIDataField("errorValidateBankAccountNumber", "BankAccountNumberError",
				getTextOfElement("BankAccountNumberError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "BANK_ACCOUNT_NUMBER_ERROR"), "UI", true, "Equals");
		sendKeysTotheElement("BankAccountNumber", "123");
		clickOnElement("RoutingNumber");
		waitForFixedTime(3);
		Utility.validateUIDataField("errorValidateBankAccountNumber", "BankAccountNumberError",
				getTextOfElement("BankAccountNumberError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "BANK_ACCOUNT_NUMBER_ERROR"), "UI", true, "Equals");
		clearTextOfTheElement("BankAccountNumber");
		waitForFixedTime(3);
		sendKeysTotheElement("BankAccountNumber", "123456789012345678");
		clickOnElement("RoutingNumber");
		waitForFixedTime(3);
		Utility.validateUIDataField("errorValidateBankAccountNumber", "BankAccountNumberError",
				getTextOfElement("BankAccountNumberError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "BANK_ACCOUNT_NUMBER_ERROR"), "UI", true, "Equals");
		clearTextOfTheElement("BankAccountNumber");
		waitForFixedTime(3);
		sendKeysTotheElement("BankAccountNumber", bankAccountNumber);
		ExtentLogger.info("Entered Bank Account Number: 123456789");
		waitForFixedTime(2);
		ExtentLogger.info("Entered Bank Account Number: " + bankAccountNumber);
		return this;
	}

	/**
	 * This method to validate the error messages in the routing number field
	 * 
	 * @created by Yunus
	 */
	public CheckOutPage errorValidateRoutingNumber(String routingNumber) {

		ExtentLogger.info("Validating error messages in the routing number field");
		waitForFixedTime(2);
		waitUntilElementVisible("RoutingNumber");
		checkElementIfVisible("RoutingNumber");
		clickOnElement("RoutingNumber");
		clickOnElement("BankAccountNumber");
		waitForFixedTime(2);
		Utility.validateUIDataField("errorValidateRoutingNumber", "RoutingNumberError",
				getTextOfElement("RoutingNumberError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ROUTING_NUMBER_ERROR"), "UI", true, "Equals");
		sendKeysTotheElement("RoutingNumber", "123");
		clickOnElement("BankAccountNumber");
		waitForFixedTime(3);
		Utility.validateUIDataField("errorValidateRoutingNumber", "RoutingNumberError",
				getTextOfElement("RoutingNumberError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ROUTING_NUMBER_ERROR"), "UI", true, "Equals");
		clearTextOfTheElement("RoutingNumber");
		waitForFixedTime(3);
		sendKeysTotheElement("RoutingNumber", "123456789012345678");
		clickOnElement("BankAccountNumber");
		waitForFixedTime(3);
		Utility.validateUIDataField("errorValidateRoutingNumber", "RoutingNumberError",
				getTextOfElement("RoutingNumberError"),
				ReadJson.getStringValue(checkOutPageDataFileName, "ROUTING_NUMBER_ERROR"), "UI", true, "Equals");
		clearTextOfTheElement("RoutingNumber");
		waitForFixedTime(3);
		sendKeysTotheElement("RoutingNumber", routingNumber);
		ExtentLogger.info("Entered Routing Number: 123456789");
		waitForFixedTime(2);
		ExtentLogger.info("Entered Routing Number: " + routingNumber);
		return this;
	}

	/**
	 * This method to enter routing number
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage enterRoutingNumber(String routingNumber) {

		waitUntilElementVisible("RoutingNumber");
		sendKeysTotheElement("RoutingNumber", routingNumber);
		ExtentLogger.info("Entered Routing Number: " + routingNumber);
		return this;
	}

	/**
	 * This method to verify currency in the checkout
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage verifyCurrency(String currency, String symbol) {

		ExtentLogger.info("Verifying Currency in the Checkout Page");
		waitUntilElementVisible("TotalCurrency");
		Utility.validateUIDataField("verifyCurrency", "TotalCurrencyCode", getTextOfElement("TotalCurrency"), currency,
				"UI", true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "SubTotalCurrencyCode", getTextOfElement("SubTotalCurrency"),
				currency, "UI", true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "SubTotalSymbol", getTextOfElement("SubTotal"), symbol, "UI",
				true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "EstimatedTaxSymbol", getTextOfElement("EstimatedTax"), symbol,
				"UI", true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "EstimatedTotalSymbol", getTextOfElement("EstimatedTotal"),
				symbol, "UI", true, "Contains");

		ExtentLogger.info("Currency is: " + currency);
		return this;
	}

	/**
	 * This method to click on authorized checkbox
	 * 
	 * @modified by Ishwarya, Yunus
	 */
	public CheckOutPage clickOnAuthorizeCheckBox() {

		waitForFixedTime(2);
		waitUntilElementVisible("AuthorizeCheckbox");
		scrollToParticularElement("AuthorizeCheckbox");
		clickOnElement("AuthorizeCheckbox");
		Utility.logGenerator("clickOnAuthorizeCheckBox", !isListEmpty("AuthorizeCheckbox") == true,
				"Clicked on Authorizecheckbox", "Cannot click on Authorizecheckbox", true);
		waitForFixedTime(2);
		return this;
	}

	/**
	 * This method to check the error validation of the authorize checkbox
	 * 
	 * @created by Yunus
	 */
	public CheckOutPage errorValidateAuthorizeCheckBox() {

		ExtentLogger.info("Checking the error validation of the authorize checkbox");
		waitForFixedTime(2);
		waitUntilElementVisible("AuthorizeCheckbox");
		scrollToParticularElement("PlaceOrderButton");
		if (checkElementIfVisible("DisablePlaceOrderButton")) {
			ExtentLogger.info("Place Order Button is visible");
			Utility.logGenerator("checkPlaceHolder", isListEmpty("DisablePlaceOrderButton") == false,
					"Place Order Button is not clickable", "Place Order Button is  clickable", true);
		} else {
			ExtentLogger.fail("Place Order Button is clickable");
		}
		waitForFixedTime(2);
		clickOnElement("AuthorizeCheckbox");
		waitForFixedTime(2);
		return this;
	}

	/**
	 * This method to click on place order button
	 * 
	 * @modified by Ishwarya and Yunus
	 */
	public CheckOutPage clickOnPlaceOrder() {

		waitUntilElementVisible("PlaceOrderButton");
		clickOnElement("PlaceOrderButton");
		waitUntilElementNotVisible("PlaceOrderButton");
		waitForPageLoad();
		Utility.logGenerator("clickOnPlaceOrder", !isListEmpty("PlaceOrderButton") == false,
				"Clicked on Place Order Button", "Cannot click on Place Order Button", true);
		return this;
	}

	/**
	 * This method to get the product names in the cart item
	 * 
	 * @created by Ishwarya
	 */
	public CheckOutPage getProductNamesInCartItem() {

		waitUntilElementVisible("PlanNameInCart");
		String productNameInCart = getTextOfElement("PlanNameInCart");
		productNamesList.add(productNameInCart);
		if (!isListEmpty("AddOnNameInCart")) {
			for (int i = 1; i < getElements("AddOnNameInCart").size(); i++) {
				String addOnName = getTextOfParameterisedXpathElement("AddOnNameInCart", "PLACEHOLDER",
						String.valueOf(i));
				productNamesList.add(addOnName);
			}
		}
		ExtentLogger.info("Product Names in Cart Item: " + productNamesList);
		return this;
	}

	/**
	 * This method to validate the plan duration
	 * 
	 * @created by Ishwarya
	 */
	public CheckOutPage verifyPlanDurationInCart(String planDuration) {

		waitUntilElementVisible("PlanDurationInCart");
		Utility.validateUIDataField("verifyPlanDurationInCart", "PlanDurationInCart",
				getTextOfElement("PlanDurationInCart"), Utility.toTitleCase(planDuration.trim()), "UI", true, "Equals");

		return this;
	}

	/**
	 * This method to validate the number of attorneys in the cart
	 * 
	 * @created by Ishwarya
	 */
	public CheckOutPage verifyNumberOfAttorneysInCart(String numberOfAttorneys) {

		waitUntilElementVisible("AttorneyNumberInCart");
		Utility.validateUIDataField("verifyNumberOfAttorneysInCart", "AttorneyNumberInCart",
				getTextOfElement("AttorneyNumberInCart"), numberOfAttorneys, "UI", true, "Equals");

		return this;
	}

	/**
	 * This method to validate the number of seats in the cart
	 * 
	 * @created by Ishwarya
	 */
	public CheckOutPage verifyNumberOfSeatsInCart(String numberOfSeats) {

		waitUntilElementVisible("SeatNumberInCart");
		Utility.validateUIDataField("verifyNumberOfSeatsInCart", "SeatNumberInCart",
				getTextOfElement("SeatNumberInCart"), numberOfSeats, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method to select the credit card option
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage selectCreditCard() {

		waitForFixedTime(3);
		waitUntilElementVisible("CreditCardRadioButton");
		clickElementUsingJSScript("CreditCardRadioButton");
		ExtentLogger.info("Selected Credit Card");
		return this;
	}

	/**
	 * This method to enter card holder name
	 * 
	 * @modified Ishwarya
	 */
	public CheckOutPage enterCardHolderName() {

		waitForFixedTime(3);
		waitUntilElementVisible("CardIframe");
		scrollToParticularElement("CardIframe");
		switchToFrame("CardIframe");
		waitUntilElementVisible("NameOnCard");
		cardNameHolder = Utility.getFakeString("FirstName");
		sendKeysTotheElement("NameOnCard", cardNameHolder);
		ExtentLogger.info("Entered Card Number: " + cardNameHolder);
		switchto_DefaultContent();
		return this;
	}

	/**
	 * This method to enter card Card Number
	 * 
	 * @modified Ishwarya
	 */
	public CheckOutPage enterCardNumber(String creditCard) {

		switchToFrame("CardIframe");
		waitUntilElementVisible("CardNumber");
		sendKeysTotheElement("CardNumber", creditCard.trim());
		ExtentLogger.info("Entered Card Number: " + creditCard);
		cardNumber = creditCard;
		switchto_DefaultContent();
		return this;
	}

	/**
	 * This method to enter year in the card section
	 * 
	 * @modified Ishwarya
	 */
	public CheckOutPage selectYear() {

		LocalDate currentDate = LocalDate.now();
		int currentYear = currentDate.getYear();
		int futureyear = Utility.generateNumberFromRange(currentYear + 1, currentYear + 9);
		switchToFrame("CardIframe");
		selectValueInDropdown("Year", String.valueOf(futureyear));
		switchto_DefaultContent();
		return this;
	}

	/**
	 * This method to enter month in the card section
	 * 
	 * @modified Ishwarya
	 */
	public CheckOutPage selectMonth() {

		String futuremonth = Utility.formatTo2DigitNumber(Utility.generateNumberFromRange(1, 12));
		switchToFrame("CardIframe");
		selectValueInDropdown("Month", futuremonth);
		switchto_DefaultContent();
		return this;
	}

	/**
	 * This method to enter Security Code in the card section
	 * 
	 * @modified Ishwarya
	 */
	public CheckOutPage enterSecurityCode(int n) {

		int code = Utility.generate3Or4DigitNumber(n);
		switchToFrame("CardIframe");
		waitUntilElementVisible("SecurityCode");
		clickOnElement("SecurityCode");
		sendKeysTotheElement("SecurityCode", String.valueOf(code));
		switchto_DefaultContent();
		return this;
	}

	/**
	 * This method to verify Estimated Total InOrder Summary for Cocouncel
	 *
	 * @modified Yunus
	 */
	public CheckOutPage verifyEstimatedTotalInOrderSummaryCCC(String ppPageTotalPlanPrice) {

		ExtentLogger.info("Verifying Estimated Total In Order Summary for Cocounsel");
		waitUntilElementVisible("EstimatedTotalCCC");
		estimatedTotalInString = getTextOfElement("EstimatedTotalCCC");
		String discard = estimatedTotalInString.replaceAll("[^0-9.]", "");
		estimatedTotal = Double.parseDouble(discard);
		estimatedTax = getTextOfElement("EstimatedTaxCCC").isEmpty() ? 0 : estimatedTax;
		// Convert ppPageTotalPlanPrice to double
		double ppPageTotalPlanPriceDouble = Double.parseDouble(ppPageTotalPlanPrice.replaceAll("[^0-9.]", ""));
		BigDecimal totalPriceWithTax = BigDecimal.valueOf(ppPageTotalPlanPriceDouble)
				.add(BigDecimal.valueOf(estimatedTax));
		BigDecimal estimatedTotalBD = BigDecimal.valueOf(estimatedTotal);
		BigDecimal roundedTotalPriceWithTax = totalPriceWithTax.setScale(2, RoundingMode.HALF_UP);
		BigDecimal roundedEstimatedTotalBD = estimatedTotalBD.setScale(2, RoundingMode.HALF_UP);
		ExtentLogger.info("Estimated Total in Order Summary: " + estimatedTotalInString);
		Utility.validateUIDataField("verifyEstimatedTotalInOrderSummary", "EstimatedTotalInOrderSummary",
				roundedEstimatedTotalBD, roundedTotalPriceWithTax, "UI", true, "Contains");

		return this;
	}

	/**
	 * This method to verify item details section in checkout page for canada estore
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage validateItemDetailsCanadaEstore(String productName, String quantity) {

		ExtentLogger.info("Validating Item Details in Canada Estore");
		Utility.validateUIDataField("validateItemDetailsCanadaEstore", "ProductNameInCart",
				getTextOfElement("ProductNameInCart"), productName, "UI", true, "Equals");
		Utility.validateUIDataField("validateItemDetailsCanadaEstore", "ProductQuantityInCart",
				getTextOfElement("ProductQuantityInCart"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CART_QNT").replace("QNT", quantity), "UI", true,
				"Equals");
		Utility.validateUIDataField("validateItemDetailsCanadaEstore", "IsProductIconPresenInCart",
				!isListEmpty("ProductIconInCart"), true, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method to click on continue to shipping button
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage clickOnContinueToShipping() {

		waitUntilElementVisible("ContinueToShippingButton");
		clickOnElement("ContinueToShippingButton");
		waitForFixedTime(3);
		Utility.logGenerator("clickOnContinueToShipping", !isListEmpty("ShippingAddressHeading") == true,
				"Clicked on Continue to shipping Button", "Cannot click on Continue to shipping Button", true);
		return this;
	}

	public CheckOutPage validateShippingSection() {

		waitUntilElementVisible("CanadaShippingAddressHeading");
		Utility.validateUIDataField("validateShippingSection", "CanadaShippingHeading",
				getTextOfElement("CanadaShippingHeading"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_SHIPPING_ADDRESS_HEADING"), "UI", true,
				"Equals");
		Utility.validateUIDataField("validateShippingSection", "CanadaShippingAddressHeading",
				getTextOfElement("CanadaShippingAddressHeading"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_SHIPPING_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateShippingSection", "CanadaAddressHeading",
				getTextOfElement("CanadaAddressHeading"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_ADDRESS_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateShippingSection", "CanadaAddressSubText",
				getTextOfElement("CanadaAddressHeading"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_ADDRESS_SUBTEXT"), "UI", true, "Equals");
		Utility.validateUIDataField("validateShippingSection", "CanadaAddressOption1",
				getTextOfElement("CanadaAddressOption1"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_ADDRESS_SUBTEXT"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method to validate the order summary in the checkout page
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage validateCanadaOrderSummaryInStep1(String price, String quantity) {

		ExtentLogger.info("Validating Order Summary in Canada Estore before entering information");

		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaOrderSummaryHeading",
				getTextOfElement("CanadaOrderSummaryHeading"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_ORDER_SUMMARY_HEADING"), "UI", true,
				"Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaOrderSummaryItemsQuantity",
				getTextOfElement("CanadaOrderSummaryItemsQuantity"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_ORDER_SUMMARY_ITEMS_QNT").replace("QUANTITY",
						quantity),
				"UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaSubtotalTitle",
				getTextOfElement("CanadaSubtotalTitle"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_SUBTOTAL_TITLE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaSubtotalValue",
				getTextOfElement("CanadaSubtotalValue"), price, "UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaTaxTitle",
				getTextOfElement("CanadaTaxTitle"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_TAX_TITLE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaTaxValue",
				getTextOfElement("CanadaTaxValue"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_TAX_EMPTY_VALUE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaShippingTitle",
				getTextOfElement("CanadaShippingTitle"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_SHIPPING_TITLE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaShippingValue",
				getTextOfElement("CanadaShippingValue"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_SHIPPING_FREE_VALUE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaShippingValue",
				getTextOfElement("CanadaShippingValue"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_SHIPPING_FREE_VALUE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaOrderSummaryTotalHeading",
				getTextOfElement("CanadaOrderSummaryTotalHeading"),
				ReadJson.getStringValue(checkOutPageDataFileName, "CANADA_TOTAL_TITLE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateCanadaOrderSummaryInStep1", "CanadaOrderSummaryTotalValue",
				getTextOfElement("CanadaOrderSummaryTotalValue"), price, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method to validate the order summary in the checkout page
	 * 
	 * @modified by Ynush
	 */
	public CheckOutPage clickOnContinueWithOutSignIn() {

		waitUntilElementVisible("ContinueWithOutSignIn");
		clickOnElement("ContinueWithOutSignIn");
		return this;
	}

	/**
	 * This method to click on continue to payment button
	 * 
	 * @modified by Ishwarya
	 */
	public CheckOutPage clickOnContinueToPaymentForCanadaEstore() {

		ExtentLogger.info("Clicking on Continue to Payment Button");
		waitUntilElementVisible("ContinueToPaymentButton");
		clickOnElement("ContinueToPaymentButton");
		waitForFixedTime(10);
		Utility.logGenerator("clickOnContinueToPayment", !isListEmpty("CardIframe") == true,
				"Clicked on Continue to payment Button", "Cannot click on Continue to payment Button", true);
		return this;
	}

	// BELOW METHODS ARE CREATED AND NEED TO MODIFY WHEN USED IN THE TEST CASES

//	// GET A DIFFRENT LOGIC FOR PPPAGE.PLANNAME
//	public CheckoutPage validatePlanNameInCartItem(String jurisdiction, String sector) {
//		waitUntilElementVisible("PlanNameInCart");
//		if (jurisdiction.isEmpty() && jurisdiction.equals("")) {
//			Assert.assertTrue(getTextOfElement("PlanNameInCart").contains(PPPage.planName));
//		} else {
//			log.info(PPPage.planName.split(jurisdiction)[0].trim() + ", " + jurisdiction);
//			Assert.assertTrue(getTextOfElement("PlanNameInCart")
//					.contains(PPPage.planName.split(jurisdiction)[0].trim() + ", " + jurisdiction));
//		}
//		Assert.assertTrue(getTextOfElement("PlanNameInCart").contains(", " + sector));
//		ExtentLogger.pass("Plan Name in Cart Item: " + PPPage.planName, true);
//		return this;
//	}

	/*
	 * public CheckOutPage validatePlanNameInCartItem_betterPlan(PPPage ppPage,
	 * String jurisdiction, String sector) throws Exception {
	 * waitUntilVisible("PlanNameInCart", "xpath", 60);
	 * System.out.println(ppPage.planName);
	 * System.out.println(ppPage.planName.split(jurisdiction)[0].trim());
	 * 
	 * System.out.println(ppPage.planName.split(jurisdiction)[0].trim()+", "
	 * +jurisdiction);
	 * Assert.assertTrue(getElementTextby_Element(getElementByXpath("PlanNameInCart"
	 * )).contains(ppPage.planName.split(jurisdiction)[0].trim()));
	 * Assert.assertTrue(getElementTextby_Element(getElementByXpath("PlanNameInCart"
	 * )).contains(", "+sector)); ExtentLogger.pass("Plan Name in Cart Item: " +
	 * ppPage.planName, true); return this; }
	 */

}