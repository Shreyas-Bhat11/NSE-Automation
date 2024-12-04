package com.NSE.ui.testcases;

import com.NSE.ui.pages.CheckOutPage;
import com.NSE.ui.pages.HeaderFooterPage;
import com.NSE.ui.pages.OnePassPage;
import com.NSE.ui.pages.OrderConfirmationPage;
import com.NSE.ui.pages.PPPage;
import com.tr.commons.BaseClass;
import com.tr.commons.ReadProperties;
import com.tr.commons.extentListeners.ExtentLogger;
import com.NSE.commons.Utility;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class TC018_E2E_USL_PL_GeneralCounsel_GoodPlan_AmexCard extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC018_TestcaseName");
	static String environment = ReadProperties.getConfig("ENVIRONMENT");
	static String url = ReadProperties.getConfig("USL_PL");

	@BeforeClass
	public void initTest(ITestContext test) throws Exception {

		Utility.testDataSetup(excelFilePath, masterDataSheetName, testDataSheetName, testCaseName, url, environment);
		if (Utility.testdataMap.get("Execute").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping tests as the exceute value is \"No\" in test data excel sheet");
		}
		initDriver(Utility.dataMap.get(url));
	}

	@Test(description = "TC018_E2E_USL_PL_GeneralCounsel_GoodPlan_AmexCard")
	public void E2E_USL_PL_GeneralCounsel_GoodPlan_AmexCard() {
		Map<String, String> testdataMap = Utility.testdataMap;
		String sectorFromExcel = testdataMap.get("Sector");
		String actualAttorneysFromExcel = testdataMap.get("ActualAttorneys");
		String planDurationFromExcel = testdataMap.get("PlanDuration");
		String planFromExcel = testdataMap.get("Plan");
		String userNameFromExcel = testdataMap.get("Existing_UserName");
		String userPasswordFromExcel = testdataMap.get("Existing_Password");
		String creditCardFromExcel = Utility.dataMap.get(testdataMap.get("CreditCard"));
		String emailActiveTrialsFromExcel = testdataMap.get("Existing_Email");
		String planSummaryDurationFromExcel = testdataMap.get("DurationPlanSummary");

		ExtentLogger.info("<b>***** Capturing values from Practical Law Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie().fetchAndValidateHeaderData().fetchAndValidateFooterData();

		PPPage pppage = new PPPage();
		pppage.verifyLandedOnPracticalLawPage().validatePLBanner().validateDefaultSector().validateDefaultPlan()
				.validateDefaultAttorneyAndTerm().validateJurisdictionIsPresent(sectorFromExcel)
				.selectSector(sectorFromExcel).validateSector(sectorFromExcel)
				.inputNumberOfSeats(actualAttorneysFromExcel).validateActualNumberOfSeats(actualAttorneysFromExcel)
				.selectPlanDuration(planDurationFromExcel).validatePlanDuration(sectorFromExcel, planDurationFromExcel)
				.selectPlan(planFromExcel).changeDurationInPlanSummarySection(planSummaryDurationFromExcel)
				.getPlanDetails()
				.validatePlanSummaryWithoutAddOnsSection(sectorFromExcel, actualAttorneysFromExcel,
						planSummaryDurationFromExcel)
				.validateTotalPrice().verifyCurrency("$", false).validateFAQSection().validatePPPageFooterSection()
				.clickOnGoToCheckout();

		ExtentLogger.info("<b>***** Capturing values from Checkout Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.fetchAndValidateFooterData();

		CheckOutPage checkoutPage = new CheckOutPage();
		checkoutPage.verifyLandedOnCheckoutPage().enterFirstName().enterLastName()
				.enterExistingEmailID(emailActiveTrialsFromExcel).enterPhoneNumber().enterOrganizationName()
				.selectType(sectorFromExcel).enterAddress().verifySubTotalInOrderSummary(PPPage.totalPriceInString)
				.getEstimatedTaxInOrderSummary().verifyEstimatedTotalInOrderSummary(PPPage.totalPlanPrice)
				.verifyCurrency("USD", "$")
//				.validatePlanNameInCartItem(testdataMap.get("Jurisdiction"), sectorFromExcel)
				.verifyPlanDurationInCart(planSummaryDurationFromExcel)
				.verifyNumberOfSeatsInCart(actualAttorneysFromExcel).clickOnContinueToPayment();

		ExtentLogger.info("<b>***** Capturing values from OnePass Page and performing UI validations *****</b>");
		OnePassPage onePassPage = new OnePassPage();
		onePassPage.clickOnSignIn().userName(userNameFromExcel).password(userPasswordFromExcel).clickOnSignInButton();

		ExtentLogger.info("<b>***** Capturing values from Checkout Page and performing UI validations *****</b>");
		CheckOutPage checkoutPage1 = new CheckOutPage();
		checkoutPage1.getFirstName().clickOnContinueToPayment().selectCreditCard().enterCardHolderName()
				.enterCardNumber(creditCardFromExcel).selectMonth().selectYear().enterSecurityCode(4)
				.clickOnAuthorizeCheckBox().getProductNamesInCartItem().clickOnPlaceOrder();

		ExtentLogger.info(
				"<b>***** Capturing values from Confirm Order Page Crad section and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage3 = new HeaderFooterPage();
		headerFooterPage3.fetchAndValidateHeaderData().fetchAndValidateFooterData();

		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		orderConfirmationPage.verifyLandedOnOrderConfirmationPage().validateContactUsBanner()
				.verifyFirstNameInOrderConfirmation(CheckOutPage.firstnameFromTextBox).getOrderNumber()
				.verifyCurrency("USD", "$")
				.verifyPrice(PPPage.totalPriceInString, CheckOutPage.estimatedTaxInString,
						CheckOutPage.estimatedTotalInString)
				.validateWhatIsNextSection()
				.enterMultipleValuesInExcel(excelFilePath, testDataSheetName, testCaseName, "orderNumber");

		Utility.storeUIData(testCaseName);
		Utility.storeExecutionTime(testCaseName);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		closeDriver();
	}
}