package com.NSE.ui.testcases;

import com.NSE.ui.pages.CheckOutPage;
import com.NSE.ui.pages.HeaderFooterPage;
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

public class TC108_E2E_USL_WLEdge_LawFirm_BestPlan_Add2AddOns_DiscoverCard extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC108_TestcaseName");
	static String environment = ReadProperties.getConfig("ENVIRONMENT");
	static String url = ReadProperties.getConfig("USL_WLE");

	@BeforeClass
	public void initTest(ITestContext test) throws Exception {

		Utility.testDataSetup(excelFilePath, masterDataSheetName, testDataSheetName, testCaseName, url, environment);
		if (Utility.testdataMap.get("Execute").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping tests as the exceute value is 'No' in test data excel sheet");
		}
		initDriver(Utility.dataMap.get(url));
	}

	@Test(groups = { "tc:831153" }, description = "E2E_USL_WLEdge_LawFirm_BestPlan_Add2AddOns_DiscoverCard")
	public void E2E_USL_WLEdge_LawFirm_BestPlan_Add2AddOns_DiscoverCard() {
		Map<String, String> testdataMap = Utility.testdataMap;
		String sectorFromExcel = testdataMap.get("Sector");
		String actualAttorneysFromExcel = testdataMap.get("ActualAttorneys");
		String planDurationFromExcel = testdataMap.get("PlanDuration");
		String planFromExcel = testdataMap.get("Plan");
		String bankAccountNumberFromExcel = testdataMap.get("BankAccountNumber");
		String routingNumberFromExcel = testdataMap.get("RoutingNumber");
		String jurisdictionFromExcel = testdataMap.get("Jurisdiction");
		String addOnsFromExcel = testdataMap.get("NumberOfAddOn");

		ExtentLogger.info("<b>***** Capturing values from West Law Edge Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie().fetchAndValidateHeaderData().fetchAndValidateFooterData();

		PPPage pppage = new PPPage();
		pppage.verifyLandedOnWestLawEdgePage().validateWLEdgeBanner().validateDefaultSector().validateDefaultPlan()
				.validateJurisdictionIsPresent(sectorFromExcel).selectSector(sectorFromExcel)
				.validateSector(sectorFromExcel).selectJurisdiction(jurisdictionFromExcel)
				.validateJurisdiction(jurisdictionFromExcel).inputNumberOfAttorneys(actualAttorneysFromExcel)
				.validateActualNumberOfAttorneys(actualAttorneysFromExcel, sectorFromExcel)
				.selectPlanDuration(planDurationFromExcel).validatePlanDuration(sectorFromExcel, planDurationFromExcel)
				.selectPlan(planFromExcel).addAddOns(addOnsFromExcel, planFromExcel, sectorFromExcel).getPlanDetails()
				// need to change the huriction approach save the random valur somethwere and
				// then use it, and yunush needs to check pricing part
				.validatePlanSummaryWithAddOnsSection(sectorFromExcel, actualAttorneysFromExcel, planDurationFromExcel,
						jurisdictionFromExcel, planFromExcel)
				.validateTotalPrice().verifyCurrency("$", false).validateFAQSection().validatePPPageFooterSection()
				.clickOnGoToCheckout();

		ExtentLogger.info("<b>***** Capturing values from Checkout Order Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.fetchAndValidateFooterData();

		CheckOutPage checkoutPage = new CheckOutPage();
		checkoutPage.verifyLandedOnCheckoutPage().enterFirstName().enterLastName().enterEmail().enterPhoneNumber()
				.enterOrganizationName().selectType(sectorFromExcel).enterAddress()
				.verifySubTotalInOrderSummary(PPPage.totalPriceInString).getEstimatedTaxInOrderSummary()
				.verifyEstimatedTotalInOrderSummary(PPPage.totalPlanPrice).verifyCurrency("USD", "$")
//				.validatePlanNameInCartItem(jurisdictionFromExcel, sectorFromExcel)
				.verifyPlanDurationInCart(planDurationFromExcel).verifyNumberOfAttorneysInCart(actualAttorneysFromExcel)
				.clickOnContinueToPayment().enterBankAccountNumber(bankAccountNumberFromExcel)
				.enterRoutingNumber(routingNumberFromExcel)
				.enterMultipleValuesInExcel(excelFilePath, testDataSheetName, testCaseName, "firstName", "lastName")
				.clickOnAuthorizeCheckBox().getProductNamesInCartItem().clickOnPlaceOrder();

		ExtentLogger.info("<b>***** Capturing values from Confirm Order Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage3 = new HeaderFooterPage();
		headerFooterPage3.fetchAndValidateHeaderData().fetchAndValidateFooterData();

		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		orderConfirmationPage.verifyLandedOnOrderConfirmationPage().validateContactUsBanner()
				.verifyFirstNameInOrderConfirmation(CheckOutPage.firstName).getOrderNumber().verifyCurrency("USD", "$")
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