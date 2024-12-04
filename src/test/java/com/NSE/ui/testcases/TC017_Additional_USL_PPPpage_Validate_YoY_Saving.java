package com.NSE.ui.testcases;

import com.NSE.commons.Utility;
import com.NSE.ui.pages.HeaderFooterPage;
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

public class TC017_Additional_USL_PPPpage_Validate_YoY_Saving extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC017_TestcaseName");
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

	@Test(description = "Additional_USL_PPPpage_Validate_YoY_Saving")
	public void Additional_USL_PPPpage_Validate_YoY_Saving() {
		Map<String, String> testdataMap = Utility.testdataMap;
		String sectorFromExcel = testdataMap.get("Sector");
		String actualAttorneysFromExcel = testdataMap.get("ActualAttorneys");
		String planDurationFromExcel = testdataMap.get("PlanDuration");
		String planFromExcel = testdataMap.get("Plan");
		String jurisdictionFromExcel = testdataMap.get("Jurisdiction");

		ExtentLogger.info("<b>***** Capturing values from Practical Law Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie().fetchAndValidateHeaderData().fetchAndValidateFooterData();

		PPPage pppage = new PPPage();
		pppage.verifyLandedOnWestLawClassicPage().validateWLClassicBanner().validateDefaultSector()
				.selectSector(sectorFromExcel).validateSector(sectorFromExcel).selectJurisdiction(jurisdictionFromExcel)
				.validateJurisdiction(jurisdictionFromExcel).inputNumberOfAttorneys(actualAttorneysFromExcel)
				.validateActualNumberOfAttorneys(actualAttorneysFromExcel, sectorFromExcel)
				.selectPlanDuration(planDurationFromExcel).selectPlan(planFromExcel).getPlanDetails()
				.valiadateViewPriceMatrix(jurisdictionFromExcel, sectorFromExcel, actualAttorneysFromExcel);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}