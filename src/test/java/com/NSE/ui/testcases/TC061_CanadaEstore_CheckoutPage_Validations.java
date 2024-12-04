package com.NSE.ui.testcases;

import com.NSE.commons.Utility;
import com.NSE.ui.pages.CanadaCartPage;
import com.NSE.ui.pages.CanadaHomePage;
import com.NSE.ui.pages.CanadaPDPPage;
import com.NSE.ui.pages.CanadaPPPPage;
import com.NSE.ui.pages.CheckOutPage;
import com.NSE.ui.pages.HeaderFooterPage;
import com.NSE.ui.pages.OrderConfirmationPage;

import com.tr.commons.BaseClass;
import com.tr.commons.ReadProperties;
import com.tr.commons.extentListeners.ExtentLogger;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class TC061_CanadaEstore_CheckoutPage_Validations extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC063_TestcaseName");
	static String environment = ReadProperties.getConfig("ENVIRONMENT");
	static String url = ReadProperties.getConfig("CANADA_EN");

	@BeforeClass
	public void initTest(ITestContext test) throws Exception {

		Utility.testDataSetup(excelFilePath, masterDataSheetName, testDataSheetName, testCaseName, url, environment);
		if (Utility.testdataMap.get("Execute").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping tests as the exceute value is \"No\" in test data excel sheet");
		}
		initDriver(Utility.dataMap.get(url));
	}

	@Test(groups = { "tc:671967" }, description = "CanadaEstore_CheckoutPage_Validations")
	public void CanadaEstore_CheckoutPage_Validations() {

		Map<String, String> testdataMap = Utility.testdataMap;
		String languageFromExcel = testdataMap.get("Language");
		String creditCardFromExcel = Utility.dataMap.get(testdataMap.get("CreditCard"));

		ExtentLogger.info("<b>***** Capturing values from Home page of Canada e-store site *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie();

		CanadaHomePage canadaHomePage = new CanadaHomePage();
		canadaHomePage.validateShopNowButton(languageFromExcel);

		ExtentLogger.info("<b>***** Searching in PPP page Canada e-store site *****</b>");
		CanadaPPPPage canadaPPPPage = new CanadaPPPPage();
		canadaPPPPage.enterInSearchBar("government").clickOnRandomProduct();

		ExtentLogger.info("<b>***** Adding product to cart and landing to Cart page in Canada e-store site *****</b>");
		CanadaPDPPage canadaPDPPage = new CanadaPDPPage();
		canadaPDPPage.selectSmartSaverOption().clickOnAddToCartButton().clickOnViewCartButton();

		ExtentLogger.info("<b>***** Landing on checkout page from cart page in Canada e-store site *****</b>");
		CanadaCartPage canadaCartPage = new CanadaCartPage();
		canadaCartPage.clickOnCheckoutButton().clickOnContinueAsGuestButton();

		ExtentLogger.info("<b>***** Validating deatils in Checkout page in Canada e-store site *****</b>");

		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.validateBooksLinkInHeader().validateCanadaSiteFooterData();

		CheckOutPage checkOutPage = new CheckOutPage();
		checkOutPage.verifyLandedOnCheckoutPage().validateCanadaOrderSummaryInStep1("", "").enterFirstName()
				.enterLastName().enterEmail().enterPhoneNumber().enterOrganizationName().enterAddress()
				.clickOnContinueToShipping().clickOnContinueToPayment().enterCardHolderName()
				.enterCardNumber(creditCardFromExcel).selectMonth().selectYear().enterSecurityCode(3)
				.clickOnAuthorizeCheckBox().clickOnPlaceOrder();

		ExtentLogger.info("<b>***** Validating Order Confirmation page in Canada e-store site *****</b>");
		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		orderConfirmationPage.verifyLandedOnOrderConfirmationPage()
				.verifyFirstNameInOrderConfirmation(CheckOutPage.firstName).getOrderNumber(true)
				.validateSurveyInCanadaEstore().fillSurveyForCanadaEstore();

		Utility.storeUIData(testCaseName);
		Utility.storeExecutionTime(testCaseName);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}