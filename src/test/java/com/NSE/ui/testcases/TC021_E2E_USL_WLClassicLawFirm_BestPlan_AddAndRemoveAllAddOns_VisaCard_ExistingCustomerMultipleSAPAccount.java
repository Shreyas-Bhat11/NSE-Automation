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

public class TC021_E2E_USL_WLClassicLawFirm_BestPlan_AddAndRemoveAllAddOns_VisaCard_ExistingCustomerMultipleSAPAccount
		extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC021_TestcaseName");
	static String environment = ReadProperties.getConfig("ENVIRONMENT");
	static String url = ReadProperties.getConfig("USL_WLC");

	@BeforeClass
	public void initTest(ITestContext test) throws Exception {

		Utility.testDataSetup(excelFilePath, masterDataSheetName, testDataSheetName, testCaseName, url, environment);
		if (Utility.testdataMap.get("Execute").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping tests as the exceute value is \"No\" in test data excel sheet");
		}
		initDriver(Utility.dataMap.get(url));
	}

	@Test(groups = {
			"tc:591168" }, description = "E2E_USL_WLClassicLawFirm_BestPlan_AddAndRemoveAllAddOns_VisaCard_ExistingCustomerMultipleSAPAccount")
	public void E2E_USL_WLClassicLawFirm_BestPlan_AddAndRemoveAllAddOns_VisaCard_ExistingCustomerMultipleSAPAccount()
			throws Exception {
		Map<String, String> testdataMap = Utility.testdataMap;
		String sectorFromExcel = testdataMap.get("Sector");
		String attorneyFromExcel = testdataMap.get("Attorneys");
		String actualAttorneysFromExcel = testdataMap.get("ActualAttorneys");
		String planDurationFromExcel = testdataMap.get("PlanDuration");
		String planFromExcel = testdataMap.get("Plan");
		String creditCardFromExcel = Utility.dataMap.get(testdataMap.get("CreditCard"));
		String existingCustomerUserNameFromExcel = testdataMap.get("Existing_UserName");
		String existingCustomerPasswordFromExcel = testdataMap.get("Existing_Password");
		String existingCustomerEmailFromExcel = testdataMap.get("Existing_Email");
		String jurisdictionFromExcel = testdataMap.get("Jurisdiction");
		String addOnsFromExcel = testdataMap.get("NumberOfAddOn");

		ExtentLogger.info("<b>***** Capturing values from Practical Law Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie().fetchAndValidateHeaderData().fetchAndValidateFooterData();

		PPPage pppage = new PPPage();
		pppage.verifyLandedOnWestLawClassicPage().validateDefaultSector().validateIfPriceStrikeOff()
				.validateJurisdictionIsPresent(sectorFromExcel).selectSector(sectorFromExcel)
				.validateSector(sectorFromExcel).inputNumberOfAttorneys(attorneyFromExcel)
				.validateActualNumberOfAttorneys(actualAttorneysFromExcel, sectorFromExcel)
				.selectPlanDuration(planDurationFromExcel).selectPlan(planFromExcel)
				.addAddOns("1", planFromExcel, sectorFromExcel).removeAddons(1, addOnsFromExcel).getPlanDetails()
				.validatePlanSummaryWithAddOnsSection(sectorFromExcel, actualAttorneysFromExcel, planDurationFromExcel,
						jurisdictionFromExcel, planFromExcel)
				.validateTotalPrice().verifyCurrency("$", false).clickOnGoToCheckout();

		ExtentLogger.info("<b>***** Capturing values from Checkout Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.fetchAndValidateFooterData();

		CheckOutPage checkoutPage = new CheckOutPage();
		checkoutPage.verifyLandedOnCheckoutPage().enterFirstName().enterLastName()
				.enterExistingEmailID(existingCustomerEmailFromExcel).enterPhoneNumber().enterOrganizationName()
				.selectType(sectorFromExcel).enterAddress().verifySubTotalInOrderSummary(PPPage.totalPriceInString)
				.getEstimatedTaxInOrderSummary().verifyEstimatedTotalInOrderSummary(PPPage.totalPlanPrice)
				.verifyCurrency("USD", "$").verifyPlanDurationInCart(planDurationFromExcel)
				.verifyNumberOfAttorneysInCart(actualAttorneysFromExcel).clickOnContinueToPayment();

		ExtentLogger.info("<b>***** Capturing values from OnePass Page and performing UI validations *****</b>");
		OnePassPage onePassPage = new OnePassPage();
		onePassPage.clickOnSignIn().userName(existingCustomerUserNameFromExcel)
				.password(existingCustomerPasswordFromExcel).clickOnSignInButton();

		ExtentLogger.info(
				"<b>***** Capturing values from Checkout Page payment section and performing UI validations *****</b>");
		CheckOutPage checkoutPage1 = new CheckOutPage();
		checkoutPage1.getFirstName().clickOnContinueToPayment().selectCreditCard().enterCardHolderName()
				.enterCardNumber(creditCardFromExcel).selectMonth().selectYear().enterSecurityCode(3)
				.clickOnAuthorizeCheckBox().clickOnPlaceOrder();

		ExtentLogger.info("<b>***** Capturing values from Confirm Order Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage3 = new HeaderFooterPage();
		headerFooterPage3.fetchAndValidateHeaderData().fetchAndValidateFooterData();

		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		orderConfirmationPage.verifyLandedOnOrderConfirmationPage().validateContactUsBanner()
				.verifyFirstNameInOrderConfirmation(CheckOutPage.firstnameFromTextBox).getOrderNumber()
				.verifyCurrency("USD", "$")
				.verifyPrice(PPPage.totalPriceInString, CheckOutPage.estimatedTaxInString,
						CheckOutPage.estimatedTotalInString)
				.validateWhatIsNextSectionExistingCustomer()
				.enterMultipleValuesInExcel(excelFilePath, testDataSheetName, testCaseName, "orderNumber");

		Utility.storeUIData(testCaseName);
		Utility.storeExecutionTime(testCaseName);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}