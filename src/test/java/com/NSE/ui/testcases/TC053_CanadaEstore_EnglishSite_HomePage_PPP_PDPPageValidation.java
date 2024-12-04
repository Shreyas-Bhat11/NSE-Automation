package com.NSE.ui.testcases;

import com.NSE.commons.Utility;
import com.NSE.ui.pages.CanadaHomePage;
import com.NSE.ui.pages.CanadaPDPPage;
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

public class TC053_CanadaEstore_EnglishSite_HomePage_PPP_PDPPageValidation extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC053_TestcaseName");
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

	@Test(groups = { "tc:670778" }, description = "CanadaEstore_EnglishSite_HomePage_PPP_PDPPageValidation")
	public void CanadaEstore_EnglishSite_HomePage_PPP_PDPPageValidation() {

		Map<String, String> testdataMap = Utility.testdataMap;
		String languageFromExcel = testdataMap.get("Language");

		ExtentLogger.info("<b>***** Capturing values from Home page of Canada e-store site *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie().validateBooksLinkInHeader().validateCanadaSiteFooterData();
		CanadaHomePage canadaHomePage = new CanadaHomePage();
		canadaHomePage.validateBanner(languageFromExcel).validateShopNowButton(languageFromExcel);

		ExtentLogger.info("<b>***** Capturing values from PPP page of Canada e-store site *****</b>");
		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.validateBooksLinkInHeader().validateCanadaSiteFooterData();
		CanadaPPPPage canadaPPPPage = new CanadaPPPPage();
		canadaPPPPage.validateFilterSection(languageFromExcel).validateSortSection(languageFromExcel)
				.validateProductDetailsIsPresent(languageFromExcel).validatePaginationsIsPresent(languageFromExcel)
				.enterInSearchBar("government").clickOnRandomProduct();

		HeaderFooterPage headerFooterPage3 = new HeaderFooterPage();
		headerFooterPage3.validateBooksLinkInHeader().validateCanadaSiteFooterData();

		CanadaPDPPage canadaPDPPage = new CanadaPDPPage();
		canadaPDPPage.validateProductDetails(CanadaPPPPage.randomProductSelected, CanadaPPPPage.productPrice,
				CanadaPPPPage.productDescription, CanadaPPPPage.productDate, CanadaPPPPage.publisher,
				CanadaPPPPage.productAuthor, languageFromExcel);

		Utility.storeUIData(testCaseName);
		Utility.storeExecutionTime(testCaseName);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}