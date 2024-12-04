package com.NSE.ui.testcases;

import com.NSE.commons.Utility;
import com.NSE.ui.pages.CheckOutPage;
import com.NSE.ui.pages.HeaderFooterPage;
import com.NSE.ui.pages.OnePassPage;
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

import java.util.Map;

public class TC003_E2E_USL_Active_Trial_WLEdge_LawFirm_BestPlan_with_addOn_AmexCard extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC003_TestcaseName");
	static String environment = ReadProperties.getConfig("ENVIRONMENT");
	static String url = ReadProperties.getConfig("USL_WLE");

	@BeforeClass
	public void initTest(ITestContext test) throws Exception {

		Utility.testDataSetup(excelFilePath, masterDataSheetName, testDataSheetName, testCaseName, url, environment);
		if (Utility.testdataMap.get("Execute").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping tests as the exceute value is \"No\" in test data excel sheet");
		}
		initDriver(Utility.dataMap.get(url));
	}

	@Test(description = "E2E_USL_Active_Trial_WLEdge_LawFirm_BestPlan_with_addOn_AmexCard")
	public void E2E_USL_Active_Trial_WLEdge_LawFirm_BestPlan_with_addOn_AmexCard() throws Exception {
		Map<String, String> testdataMap = Utility.testdataMap;
		String sectorFromExcel = testdataMap.get("Sector");
		String attorneyFromExcel = testdataMap.get("Attorneys");
		String actualAttorneysFromExcel = testdataMap.get("ActualAttorneys");
		String planDurationFromExcel = testdataMap.get("PlanDuration");
		String planFromExcel = testdataMap.get("Plan");
		String jurisdictionFromExcel = testdataMap.get("Jurisdiction");
		String ActiveTrialsEmail = testdataMap.get("Existing_Email");
		String ActiveTrialsUserName = testdataMap.get("Existing_UserName");
		String ActiveTrialsPassword = testdataMap.get("Existing_Password");
		String CreditCard = Utility.dataMap.get(testdataMap.get("CreditCard"));
		String addOnsFromExcel = testdataMap.get("NumberOfAddOn");

		ExtentLogger.info("<b>***** Capturing values from Practical Law Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie().fetchAndValidateHeaderData().fetchAndValidateFooterData();

		PPPage pppage = new PPPage();
		pppage.verifyLandedOnWestLawEdgePage().selectSector(sectorFromExcel).selectSector(sectorFromExcel)
				.selectJurisdiction(jurisdictionFromExcel).inputNumberOfAttorneys(attorneyFromExcel)
				.validateActualNumberOfAttorneys(actualAttorneysFromExcel, sectorFromExcel)
				.selectPlanDuration(planDurationFromExcel).selectPlan(planFromExcel).getPlanDetails()
				.addAddOns(addOnsFromExcel, planFromExcel, sectorFromExcel).validateTotalPrice()
				.verifyCurrency("$", false).clickOnGoToCheckout();

		ExtentLogger.info("<b>***** Capturing values from Checkout Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.fetchAndValidateFooterData();

		CheckOutPage checkoutPage = new CheckOutPage();
		checkoutPage.verifyLandedOnCheckoutPage().enterFirstName().enterLastName()
				.enterExistingEmailID(ActiveTrialsEmail).enterPhoneNumber().enterOrganizationName()
				.selectType(sectorFromExcel).enterAddress().verifySubTotalInOrderSummary(PPPage.totalPriceInString)
				.getEstimatedTaxInOrderSummary().verifyEstimatedTotalInOrderSummary(PPPage.totalPlanPrice)
				.verifyCurrency("USD", "$").verifyPlanDurationInCart(planDurationFromExcel)
				.verifyNumberOfAttorneysInCart(actualAttorneysFromExcel).clickOnContinueToPayment();

		ExtentLogger.info("<b>***** Capturing values from OnePass Page and performing UI validations *****</b>");
		OnePassPage onePassPage = new OnePassPage();
		onePassPage.clickOnSignIn().userName(ActiveTrialsUserName).password(ActiveTrialsPassword).clickOnSignInButton();

		CheckOutPage checkoutPage1 = new CheckOutPage();
		checkoutPage1.getFirstName().clickOnContinueToPayment().selectCreditCard().enterCardHolderName()
				.enterCardNumber(CreditCard).selectMonth().selectYear().enterSecurityCode(4).clickOnAuthorizeCheckBox()
				.clickOnPlaceOrder();

		ExtentLogger
				.info("<b>***** Capturing values from Order Confirmation Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage3 = new HeaderFooterPage();
		headerFooterPage3.fetchAndValidateHeaderData().fetchAndValidateFooterData();

		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		orderConfirmationPage.verifyLandedOnOrderConfirmationPage()
				.verifyFirstNameInOrderConfirmation(CheckOutPage.firstnameFromTextBox).getOrderNumber()
				.verifyCurrency("USD", "$")
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