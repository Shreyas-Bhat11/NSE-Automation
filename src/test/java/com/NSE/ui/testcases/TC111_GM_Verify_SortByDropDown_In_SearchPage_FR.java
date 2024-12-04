package com.NSE.ui.testcases;

import com.NSE.commons.Utility;
import com.NSE.ui.pages.CanadaHomePage;
import com.NSE.ui.pages.CanadaPPPPage;
import com.NSE.ui.pages.HeaderFooterPage;

import com.tr.commons.BaseClass;
import com.tr.commons.ReadProperties;
import com.tr.commons.extentListeners.ExtentLogger;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class TC111_GM_Verify_SortByDropDown_In_SearchPage_FR extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC111_TestcaseName");
	static String environment = ReadProperties.getConfig("ENVIRONMENT");
	static String url = ReadProperties.getConfig("CANADA_FR");

	@BeforeClass
	public void initTest(ITestContext test) throws Exception {

		Utility.testDataSetup(excelFilePath, masterDataSheetName, testDataSheetName, testCaseName, url, environment);
		if (Utility.testdataMap.get("Execute").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping tests as the exceute value is \"No\" in test data excel sheet");
		}
		initDriver(Utility.dataMap.get(url));
	}

	@Test(groups = { "tc:874934" }, description = "GM_Verify_SortByDropDown_In_SearchPage_FR")
	public void GM_Verify_SortByDropDown_In_SearchPage_FR() {

		Map<String, String> testdataMap = Utility.testdataMap;
		String languageFromExcel = testdataMap.get("Language");

		ExtentLogger.info("<b>***** Capturing values from Home page of Canada e-store site *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie();
		CanadaHomePage canadaHomePage = new CanadaHomePage();
		canadaHomePage.validateBanner(languageFromExcel).validateShopNowButton(languageFromExcel);

		ExtentLogger.info("<b>***** Capturing values from PPP page of Canada e-store site *****</b>");
		CanadaPPPPage canadaPPPPage = new CanadaPPPPage();
		canadaPPPPage.validateSortSection(languageFromExcel)
				.selectSortByDropDownOptionAndValidateProductList(languageFromExcel);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}