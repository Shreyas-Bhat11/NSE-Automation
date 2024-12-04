package com.NSE.ui.testcases;

import com.NSE.commons.Utility;
import com.NSE.ui.pages.CheckOutPage;
import com.NSE.ui.pages.HeaderFooterPage;
import com.NSE.ui.pages.OrderConfirmationPage;
import com.NSE.ui.pages.PPPage;
import com.tr.commons.BaseClass;
import com.tr.commons.ReadProperties;
import com.tr.commons.extentListeners.ExtentLogger;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Map;

public class TC132_CCC_GeneralCounsel_Manual_NetNewCustomer_BankAccount extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC132_TestcaseName");
	static String environment = ReadProperties.getConfig("ENVIRONMENT");
	static String url = ReadProperties.getConfig("ccc");

	@BeforeClass
	public void initTest(ITestContext test) throws Exception {

		Utility.testDataSetup(excelFilePath, masterDataSheetName, testDataSheetName, testCaseName, url, environment);
		if (Utility.testdataMap.get("Execute").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping tests as the exceute value is \"No\" in test data excel sheet");
		}
		initDriver(Utility.dataMap.get(url));
	}

	@Test(groups = {"tc=947868"}, description = "CoCounsel Core General Counsel Net New Customer payment through BankAccount")
	public void CCC_GeneralCounsel_Manual_NetNewCustomer_BankAccount() throws IOException, UnsupportedFlavorException {
		Map<String, String> testdataMap = Utility.testdataMap;
		String sectorFromExcel = testdataMap.get("Sector");
		String actualAttorneysFromExcel = testdataMap.get("ActualAttorneys");
		String planDurationFromExcel = testdataMap.get("PlanDuration");
		String bankAccountNumberFromExcel = testdataMap.get("BankAccountNumber");
		String routingNumberFromExcel = testdataMap.get("RoutingNumber");


		ExtentLogger.info("<b>***** Capturing values from Practical Law Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie().fetchAndValidateHeaderData().fetchAndValidateFooterData();

		PPPage pppage = new PPPage();
		pppage.verifyCoCounselCorePage().selectSector(sectorFromExcel)
				.inputNumberOfSeats(actualAttorneysFromExcel)
				.validateActualNumberOfSeatsCCC(actualAttorneysFromExcel, sectorFromExcel)
				.selectPlanDuration(planDurationFromExcel)
				.validateShareLink()
				.planDetailCCC(planDurationFromExcel)
				.clickOnGoToCheckout();

		ExtentLogger.info("<b>***** Capturing values from Checkout Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.fetchAndValidateFooterData();

		CheckOutPage checkoutPage = new CheckOutPage();
		checkoutPage.verifyLandedOnCheckoutPage().enterFirstName().enterLastName().enterEmail().enterPhoneNumber()
				.enterOrganizationName().selectType(sectorFromExcel).enterAddress()
				.verifySubTotalInOrderSummary(PPPage.totalPriceInString).getEstimatedTaxInOrderSummary()
				.verifyEstimatedTotalInOrderSummaryCCC(PPPage.totalPriceInString).verifyCurrency("USD", "$")
				.verifyNumberOfSeatsInCart(actualAttorneysFromExcel).clickOnContinueToPayment()
				.enterBankAccountNumber(bankAccountNumberFromExcel).enterRoutingNumber(routingNumberFromExcel)
				.clickOnAuthorizeCheckBox().clickOnPlaceOrder();

		ExtentLogger
				.info("<b>***** Capturing values from Order Confirmation Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage3 = new HeaderFooterPage();
		headerFooterPage3.fetchAndValidateHeaderData().fetchAndValidateFooterData();

		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		orderConfirmationPage.verifyLandedOnOrderConfirmationPage()
				.verifyFirstNameInOrderConfirmation(CheckOutPage.firstName).getOrderNumber().verifyCurrency("USD", "$")
				.verifyPrice(PPPage.totalPriceInString, CheckOutPage.estimatedTaxInString,
						CheckOutPage.estimatedTotalInString)
				.enterMultipleValuesInExcel(excelFilePath, testDataSheetName, testCaseName, "orderNumber");

		Utility.storeUIData(testCaseName);
		Utility.storeExecutionTime(testCaseName);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}