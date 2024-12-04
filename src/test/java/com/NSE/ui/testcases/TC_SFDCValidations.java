package com.NSE.ui.testcases;

import com.tr.commons.BaseClass;
import com.tr.commons.ReadJson;
import com.tr.commons.ReadProperties;
import com.tr.commons.extentListeners.ExtentLogger;
import com.NSE.commons.Utility;

import com.NSE.sfdc.pages.AccountPage;
import com.NSE.sfdc.pages.HomePage;
import com.NSE.sfdc.pages.LeadsPage;
import com.NSE.sfdc.pages.LineItemsPage;
import com.NSE.sfdc.pages.LoginPage;
import com.NSE.sfdc.pages.ProductConfigurationPage;
import com.NSE.sfdc.pages.ProposalPage;
import com.NSE.sfdc.pages.TrailsPage;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TC_SFDCValidations extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName;
	static String environment = ReadProperties.getConfig("ENVIRONMENT");
	static String url = "";
	HashMap<String, HashMap<String, Object>> uiDataMap = Utility.uiDataMap;
	HashMap<String, Object> uiDataForTestCase = new HashMap<>();

	// Variables to store UI data
	static String uiQuoteID = "", expectedApprovalStage = "", expectedSapOrderStatus = "", expectedQuoteType = "",
			uiOrderConfirmationContact = "", expectedPaymentOption = "", expectedPaymentStatus = "", uiOCCName = "",
			uiOCCEmail = "", expectedDigitalQuoteType = "", expectedStatus = "", exceptedSummaryGroupsNames = "",
			expectedNoOfAttorney = "", expectedContractTerm = "", expectedLineStatus = "", expectedPricingStatus = "",
			expectedApprovalSegment = "", expectedLeadStatus = "", uiPhoneNumber = "", expectedOperatingUnit = "",
			expectedLineOfBusiness = "", expectedHighLevelCustomerType = "",
			expectedFrozenMarketCurrentYearSegmentL1 = "";
	static HashMap<String, Long> testCaseExecutionTime;

	static int uiLineItemIdCount = 0;
	static List<String> uiProductNameList = new ArrayList<>();
	// Variables to store JSON data
	static String sfdcDataFileName = "SfdcData.json";

	@BeforeClass
	@Parameters({ "TestCaseID" })
	public void initTest(ITestContext test, String testCaseID) throws Exception {
		testCaseName = ReadProperties.getConfig(testCaseID);
		Utility.testDataSetup(excelFilePath, masterDataSheetName, testDataSheetName, testCaseName, url, environment);
		if (Utility.testdataMap.get("Execute").equalsIgnoreCase("Yes") && uiDataMap.isEmpty() == false
				&& uiDataMap.containsKey(testCaseName) == true) {
			uiDataForTestCase = Utility.retriveUIData(uiDataMap, testCaseName);
			testCaseExecutionTime = Utility.testCaseExecutionTime;
			initDriver(ReadProperties.getConfig("WEB_URL"));
		} else {
			ExtentLogger.skip(
					"Skipping tests as the exceute value is 'No' in test data excel sheet and the ui data is not available for the test case");
			throw new SkipException(
					"Skipping tests as the exceute value is 'No' in test data excel sheet and the ui data is not available for the test case");
		}
	}

	@Test(description = "TC_SFDCValidations")
	public void TC_SFDCValidations() {

		LoginPage loginPage = new LoginPage();
		Utility.retrieveTimeDiffrence(testCaseExecutionTime, testCaseName);
		if (Utility.executionTimeDifference > 0) {
			log.info("Waiting for " + Utility.executionTimeDifference + " seconds...");
			Utility.log.info("Execution time difference is: " + Utility.executionTimeDifference);
			loginPage.waitForFixedTime((int) (Utility.executionTimeDifference));
		}
		getExpectedValues();
		String userId = Utility.dataMap.get("SFDC User ID");
		String userPassword = Utility.dataMap.get("SFDC User password");

		ExtentLogger.info("<b>***** Capturing values from SFDC Pages *****</b>");
		// Navigate to Login Page
		LoginPage loginPage1 = new LoginPage();
		loginPage1.clickOnUserNameTextBox().enterUserName(userId).clickOnPasswordTextBox().enterPassword(userPassword)
				.clickOnLoginButton();

		HomePage HomePage = new HomePage();
		HomePage.openQuoteId(uiQuoteID);

		ProposalPage proposalPage = new ProposalPage();
		proposalPage.getProposalPageUrl().getProposalPageDataAfterPlaceOrder().clickOnConfig();

		ProductConfigurationPage productConfigurationPage = new ProductConfigurationPage();
		productConfigurationPage.getProductConfigPageDataAfterPlaceOrder();

		LineItemsPage lineItemsPage = new LineItemsPage();
		lineItemsPage.getLineItemDetails(ProductConfigurationPage.lineItemIds);

		ProposalPage proposalPage2 = new ProposalPage();
		proposalPage2.navigateToProposalPage(ProposalPage.proposalPageUrl).clickOnAccount();

		AccountPage accountPage = new AccountPage();
		accountPage.openEnterpriseTrailsLink();

		TrailsPage trialsPage = new TrailsPage();
		trialsPage.getTrailsDataAfterPlaceOrder().clickOnLeads();

		LeadsPage leadsPage = new LeadsPage();
		leadsPage.getLeadsDataAfterPlaceOrder();

		verifyProposalPageData();
		verifyProductConfigurationPageData();
		verifyLineItemsPageData();
		// THERE IS A KNOWN ISSUE IN THE TRAILS PAGE SO COMMENTING OUT THE CODE
		verifyTrailsPageData();
		verifyLeadsPageData();

	}

	/**
	 * This method is to get the expected values from JSON file or generate expected
	 * values
	 * 
	 * @created by Ishwarya
	 */
	private void getExpectedValues() {

		String organizationType = Utility.testdataMap.get("OrganizationType");
		String sectorFromExcel = Utility.testdataMap.get("Sector");

		if (sectorFromExcel.equalsIgnoreCase("Law Firm")) {
			expectedFrozenMarketCurrentYearSegmentL1 = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_SEGMENT_L1_1");
			expectedLineOfBusiness = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_LOB_1");
			if (organizationType.equalsIgnoreCase(ReadJson.getStringValue(sfdcDataFileName, "USL_LAW_FIRM_1"))
					|| organizationType.equalsIgnoreCase(ReadJson.getStringValue(sfdcDataFileName, "USL_LAW_FIRM_2"))) {

				expectedApprovalSegment = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_APPROVAL_SEGMENT_1");
				expectedOperatingUnit = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_OPERATING_UNIT_1");
				expectedHighLevelCustomerType = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_HLCT_1");
			} else if (organizationType.equalsIgnoreCase(ReadJson.getStringValue(sfdcDataFileName, "USL_LAW_FIRM_3"))) {
				expectedApprovalSegment = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_APPROVAL_SEGMENT_2");
				expectedOperatingUnit = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_OPERATING_UNIT_2");
				expectedHighLevelCustomerType = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_HLCT_2");
			} else {
				expectedApprovalSegment = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_APPROVAL_SEGMENT_2");
				expectedOperatingUnit = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_OPERATING_UNIT_2");
				expectedHighLevelCustomerType = ReadJson.getStringValue(sfdcDataFileName, "USL_LF_HLCT_3");
			}
		} else if (sectorFromExcel.equalsIgnoreCase("General Councel")) {
			expectedApprovalSegment = ReadJson.getStringValue(sfdcDataFileName, "USL_GC_APPROVAL_SEGMENT_1");
			expectedHighLevelCustomerType = ReadJson.getStringValue(sfdcDataFileName, "USL_GC_HLCT_1");
			expectedFrozenMarketCurrentYearSegmentL1 = ReadJson.getStringValue(sfdcDataFileName, "USL_GC_SEGMENT_L1_1");
			if (organizationType.equalsIgnoreCase(ReadJson.getStringValue(sfdcDataFileName, "USL_GC_1"))) {
				expectedOperatingUnit = ReadJson.getStringValue(sfdcDataFileName, "USL_GC_OPERATING_UNIT_1");
				expectedLineOfBusiness = ReadJson.getStringValue(sfdcDataFileName, "USL_GC_LOB_1");
			} else {
				expectedOperatingUnit = ReadJson.getStringValue(sfdcDataFileName, "USL_GC_OPERATING_UNIT_2");
				expectedLineOfBusiness = ReadJson.getStringValue(sfdcDataFileName, "USL_GC_LOB_2");
			}
		} else {
			expectedApprovalSegment = ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_APPROVAL_SEGMENT_1");
			expectedOperatingUnit = ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_OPERATING_UNIT_1");
			expectedLineOfBusiness = ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_LOB_1");
			expectedFrozenMarketCurrentYearSegmentL1 = ReadJson.getStringValue(sfdcDataFileName,
					"USL_GOV_SEGMENT_L1_1");
			if (organizationType.equalsIgnoreCase(ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_1"))
					|| organizationType.equalsIgnoreCase(ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_2"))
					|| organizationType.equalsIgnoreCase(ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_3"))
					|| organizationType.equalsIgnoreCase(ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_4"))) {

				expectedHighLevelCustomerType = ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_HLCT_1");
			} else {
				expectedHighLevelCustomerType = ReadJson.getStringValue(sfdcDataFileName, "USL_GOV_HLCT_2");
			}
		}

		uiQuoteID = uiDataForTestCase.get("OrderNumber").toString();
		expectedApprovalStage = ReadJson.getStringValue(sfdcDataFileName, "APPROVAL_STAGE");
		expectedQuoteType = ReadJson.getStringValue(sfdcDataFileName, "QUOTE_TYPE");
		expectedSapOrderStatus = ReadJson.getStringValue(sfdcDataFileName, "SAP_ORDER_STATUS");
		uiOrderConfirmationContact = uiDataForTestCase.get("FirstName") + " " + uiDataForTestCase.get("LastName");
		expectedPaymentOption = ReadJson.getStringValue(sfdcDataFileName, "PAYMENT_OPTION");
		expectedPaymentStatus = ReadJson.getStringValue(sfdcDataFileName, "PAYMENT_STATUS");
		expectedDigitalQuoteType = ReadJson.getStringValue(sfdcDataFileName, "DIGITAL_QUOTE_TYPE");
		uiOCCName = uiDataForTestCase.get("FirstName").toString();
		uiOCCEmail = uiDataForTestCase.get("Email").toString().toLowerCase();
		expectedStatus = ReadJson.getStringValue(sfdcDataFileName, "PCP_STATUS");
		exceptedSummaryGroupsNames = ReadJson.getStringValue(sfdcDataFileName, "PCP_SUMMARY_GROUPS_NAMES");
		uiLineItemIdCount = 1;
		expectedNoOfAttorney = (Utility.testdataMap.get("ActualAttorneys")).replaceFirst("\\..*", "");
		if (Utility.testdataMap.get("DurationPlanSummary") == null
				|| Utility.testdataMap.get("DurationPlanSummary").trim().equals("")) {
			expectedContractTerm = Utility.toTitleCase(Utility.testdataMap.get("PlanDuration"));
		} else {
			expectedContractTerm = Utility.toTitleCase(Utility.testdataMap.get("DurationPlanSummary"));
		}
		expectedLineStatus = ReadJson.getStringValue(sfdcDataFileName, "LI_LINE_STATUS");
		expectedPricingStatus = ReadJson.getStringValue(sfdcDataFileName, "LI_PRICING_STATUS");
		uiProductNameList = (List<String>) uiDataForTestCase.get("ProductNameList");
		expectedLeadStatus = ReadJson.getStringValue(sfdcDataFileName, "LEAD_STATUS");
		uiPhoneNumber = uiDataForTestCase.get("PhoneNumber").toString();
	}

	/**
	 * This method is to verify the data from proposal page
	 * 
	 * @created by Ishwarya
	 */
	private void verifyProposalPageData() {

		ExtentLogger.info("<b>***** Validation for data fetched from Proposal Page *****</b>");

		Utility.validateUIDataField("verifyProposalPageData", "ApprovalSegment", ProposalPage.approvalSegment,
				expectedApprovalSegment, "SFDC", false, "Contains");
		Utility.validateUIDataField("verifyProposalPageData", "QuoteID", uiQuoteID, ProposalPage.quoteID, "UI_SFDC",
				false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "ApprovalStage", ProposalPage.approvalStage,
				expectedApprovalStage, "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "SoldToAcctSSDNotBlank",
				ProposalPage.soldToAcctSSDNotBlank, true, "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "BillToAcctSSDNotBlank",
				ProposalPage.billToAcctSSDNotBlank, true, "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "ShipToAcctSSDNotBlank",
				ProposalPage.shipToAcctSSDNotBlank, true, "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "AccountIsBlank", ProposalPage.accountIsBlank, true,
				"SFDC", false, "Equals");

		boolean digitallySignedDate = (!ProposalPage.digitallySignedDate.equals(" ")) ? true : false;
		Utility.validateUIDataField("verifyProposalPageData", "IsDigitallySignedDateNotBlank", digitallySignedDate,
				true, "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "QuoteType", ProposalPage.quoteType, expectedQuoteType,
				"SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "OrderConfirmationContactName",
				Utility.toTitleCase(uiOrderConfirmationContact),
				Utility.toTitleCase(ProposalPage.orderConfirmationContact), "UI_SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "PaymentOption", ProposalPage.paymentOption,
				expectedPaymentOption, "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "PaymentStatus", ProposalPage.paymentStatus,
				expectedPaymentStatus, "SFDC", false, "Equals");
		// for payment type as bank account the below 3 validations should be false
//		Utility.validateUIDataField("verifyProposalPageData", "IsCardTypeEmpty", ProposalPage.cardType == false, true,
//				"SFDC", false, "Equals");
//		Utility.validateUIDataField("verifyProposalPageData", "IsCardAuthorizationNumberEmpty",
//				ProposalPage.cardAuthorizationNumber == false, true, "SFDC", false, "Equals");
//		Utility.validateUIDataField("verifyProposalPageData", "IsCardExpirationDateEmpty",
//				ProposalPage.cardExpirationDate == false, true, "SFDC", false, "Equals");

		Utility.validateUIDataField("verifyProposalPageData", "DigitalQuoteType", ProposalPage.digitalQuoteType,
				expectedDigitalQuoteType, "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "OCCName", uiOCCName, ProposalPage.occName, "UI_SFDC",
				false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "OCCEmail", uiOCCEmail, ProposalPage.occEmail, "UI_SFDC",
				false, "Equals");
		Utility.validateUIDataField("verifyProposalPageData", "SAPOrderStatus", ProposalPage.sapOrderStatus,
				expectedSapOrderStatus, "SFDC", false, "Equals");
	}

	/**
	 * This method is to verify the data from product configuration page
	 * 
	 * @created by Ishwarya
	 */
	private void verifyProductConfigurationPageData() {

		ExtentLogger.info("<b>***** Validation for data fetched from Product Configration Page *****</b>");
		// NEED APPROVAL SEGMENT MAPPING FROM BE TEAM
		Utility.validateUIDataField("verifyProductConfigurationPageData", "ApprovalSegment",
				ProductConfigurationPage.approvalSegment, expectedApprovalSegment, "SFDC", false, "Contains");
		Utility.validateUIDataField("verifyProductConfigurationPageData", "Status", ProductConfigurationPage.status,
				expectedStatus, "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyProductConfigurationPageData", "TotalingGroups",
				ProductConfigurationPage.totalingGroups, true, "SFDC", false, "Equals");
		// coupon code is not present if coupon is entered in UI
		Utility.validateUIDataField("verifyProductConfigurationPageData", "IsCouponCodePresent",
				ProductConfigurationPage.couponCode, true, "SFDC", false, "Equals");
		String sfdcSummaryGroupsNames = "";
		for (String name : ProductConfigurationPage.summaryGroupsNames) {
			sfdcSummaryGroupsNames = sfdcSummaryGroupsNames + ", " + name;
		}
		sfdcSummaryGroupsNames = sfdcSummaryGroupsNames.substring(2, sfdcSummaryGroupsNames.length());
		Utility.validateUIDataField("verifyProposalPageData", "SummaryGroupsNames", sfdcSummaryGroupsNames,
				exceptedSummaryGroupsNames, "SFDC", false, "Equals");
		// if no add on then the count is 1
		Utility.validateUIDataField("verifyProductConfigurationPageData", "LineItemIdCount", uiLineItemIdCount,
				ProductConfigurationPage.lineItemIdCount, "UI_SFDC", false, "Equals");
	}

	/**
	 * This method is to verify the data from line items page
	 * 
	 * @created by Ishwarya
	 */
	private void verifyLineItemsPageData() {

		ExtentLogger.info("<b>***** Validation for data fetched from Line Item Page *****</b>");

		for (int i = 0; i < uiProductNameList.size(); i++) {

			String uiProductName = uiProductNameList.get(i);
			if (LineItemsPage.lineItemDetails.get(uiProductName) != null) {
				Map<String, Object> productDetailsMap = LineItemsPage.lineItemDetails.get(uiProductName);
				String approvalSegment = productDetailsMap.get("ApprovalSegment").toString();
				String netPrice = productDetailsMap.get("NetPrice").toString();
				String quantity = productDetailsMap.get("Quantity").toString();
				String contractTerm = productDetailsMap.get("ContractTerm").toString();
				String lineStatus = productDetailsMap.get("LineStatus").toString();
				String pricingStatus = productDetailsMap.get("PricingStatus").toString();
				String couponCode = productDetailsMap.get("CouponCode").toString();
				String hasIncentives = productDetailsMap.get("HasIncentives").toString();
				String productName = productDetailsMap.get("ProductName").toString();

				ExtentLogger.info("<b>***** Validation of Product details of: " + uiProductName + " *****</b>");

				Utility.validateUIDataField("verifyProposalPageData", "ProductName", uiProductName, productName,
						"UI_SFDC", false, "Equals");
				// NEED APPROVAL SEGMENT MAPPING FROM BE TEAM
				Utility.validateUIDataField("verifyProposalPageData", "ApprovalSegment", approvalSegment,
						expectedApprovalSegment, "SFDC", false, "Contains");
				Utility.validateUIDataField("verifyProposalPageData", "NetPrice", netPrice, expectedNoOfAttorney,
						"SFDC", false, "Equals");
				Utility.validateUIDataField("verifyProposalPageData", "Quantity", quantity, expectedNoOfAttorney,
						"SFDC", false, "Equals");
				Utility.validateUIDataField("verifyProposalPageData", "ContractTerm", contractTerm,
						expectedContractTerm, "SFDC", false, "Equals");
				Utility.validateUIDataField("verifyProposalPageData", "LineStatus", lineStatus, expectedLineStatus,
						"SFDC", false, "Equals");
				Utility.validateUIDataField("verifyProposalPageData", "PricingStatus", pricingStatus,
						expectedPricingStatus, "SFDC", false, "Equals");
				// when coupon is not entered in UI
				Utility.validateUIDataField("verifyProposalPageData", "CouponCode", couponCode, "false", "SFDC", false,
						"Equals");
				Utility.validateUIDataField("verifyProposalPageData", "HasIncentives", hasIncentives, "false", "SFDC",
						false, "Equals");
			}
		}
	}

	/**
	 * This method is to verify the data from trails page
	 * 
	 * @created by Ishwarya
	 */
	private void verifyTrailsPageData() {

		ExtentLogger.info("<b>***** Validation for data fetched from Trails Page *****</b>");

		Utility.validateUIDataField("verifyTrailsPageData", "TitleNameLinkCount", AccountPage.titleNameLinkCount, 1,
				"SFDC", false, "Equals");
		Utility.validateUIDataField("verifyTrailsPageData", "TrailStatus", TrailsPage.trailStatus,
				ReadJson.getStringValue(sfdcDataFileName, "TRAILS_STATUS"), "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyTrailsPageData", "ConvertToSaleChecked", TrailsPage.convertToSaleChecked,
				ReadJson.getStringValue(sfdcDataFileName, "TRAILS_CHECKED"), "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyTrailsPageData", "IsLeadConvertedChecked", TrailsPage.isLeadConvertedChecked,
				ReadJson.getStringValue(sfdcDataFileName, "TRAILS_CHECKED"), "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyTrailsPageData", "InstantEntitlementChecked",
				TrailsPage.instantEntitlementChecked, ReadJson.getStringValue(sfdcDataFileName, "TRAILS_CHECKED"),
				"SFDC", false, "Equals");
		Utility.validateUIDataField("verifyTrailsPageData", "EntitlementStatusDescription",
				TrailsPage.entitlementStatusDescription,
				ReadJson.getStringValue(sfdcDataFileName, "ENTITLEMENT_CHECKED"), "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyTrailsPageData", "EntitlementStatus", TrailsPage.entitlementStatus,
				ReadJson.getStringValue(sfdcDataFileName, "ENTITLEMENT_STATUS"), "SFDC", false, "Equals");
	}

	/**
	 * This method is to verify the data from leads page
	 * 
	 * @created by Ishwarya
	 */
	private void verifyLeadsPageData() {

		ExtentLogger.info("<b>***** Validation for data fetched from Leads Page *****</b>");

		Utility.validateUIDataField("verifyLeadsPageData", "LeadsStatus", LeadsPage.leadsStatus, expectedLeadStatus,
				"SFDC", false, "Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "OwnerName", LeadsPage.ownerName, "", "SFDC", false,
				"Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "Name", LeadsPage.name, uiOrderConfirmationContact, "SFDC",
				false, "Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "AccountName", LeadsPage.accountName, "", "SFDC", false,
				"Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "Description", LeadsPage.description, "", "SFDC", false,
				"Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "Email", LeadsPage.email, uiOCCEmail, "SFDC", false,
				"Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "Phone", LeadsPage.phone, uiPhoneNumber, "SFDC", false,
				"Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "Mobile", LeadsPage.mobile, "", "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "OperatingUnit", LeadsPage.operatingUnit, "", "SFDC", false,
				"Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "LineOfBusiness", LeadsPage.lineOfBusiness, "", "SFDC",
				false, "Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "FormOrganizationType", LeadsPage.formOrganizationType, "",
				"SFDC", false, "Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "BURegion", LeadsPage.buRegion, "", "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "FrozenMarketCurrentYearSegmentL1",
				LeadsPage.frozenMarketCurrentYearSegmentL1, "", "SFDC", false, "Equals");
		Utility.validateUIDataField("verifyLeadsPageData", "HighLevelCustomerType", LeadsPage.highLevelCustomerType, "",
				"SFDC", false, "Equals");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}