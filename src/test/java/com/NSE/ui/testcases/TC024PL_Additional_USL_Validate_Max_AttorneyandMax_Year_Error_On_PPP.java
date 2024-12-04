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

public class TC024PL_Additional_USL_Validate_Max_AttorneyandMax_Year_Error_On_PPP extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC024_TestcaseName_PL");
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

	@Test(groups = { "tc:591192" }, description = "Additional USL Validate Max Attorney and Max Year Error On PPP For PL")
	public void PL_Additional_USL_Validate_Max_Attorney_andMax_Year_Error_On_PPP(){
		Map<String, String> testdataMap = Utility.testdataMap;
		String sectorFromExcel = testdataMap.get("Sector");
		String actualAttorneysFromExcel = testdataMap.get("ActualAttorneys");


		ExtentLogger.info("<b>***** Capturing values from Practical Law Page and performing UI validations *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie().fetchAndValidateHeaderData().fetchAndValidateFooterData();

		PPPage pppage = new PPPage();
		pppage.verifyLandedOnPracticalLawPage()
				.selectSector(sectorFromExcel)
				.inputNumberOfAttorneys(actualAttorneysFromExcel)
				.errorMessageValidationMaxAttorneyLawFirm()
				.errorMessageValidateAttorneyForGCSector()
				.errorMessageValidateAttorneyForGovtSector();




		Utility.storeUIData(testCaseName);
		Utility.storeExecutionTime(testCaseName);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		 closeDriver();
	}
}