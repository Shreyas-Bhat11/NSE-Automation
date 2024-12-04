package com.NSE.ui.testcases;

import com.NSE.ui.pages.HeaderFooterPage;
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

public class TC023_Additional_USL_Navigat_WLEdge_Classic_and_viseversa_From_PPPage extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC023_TestcaseName");
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

	@Test(groups = { "tc:591178" }, description = "Additional_USL_Navigat_WLEdge_Classic_and_viseversa_From_PPPage")
	public void Additional_USL_Navigat_WLEdge_Classic_and_viseversa_From_PPPage() {

		ExtentLogger.info("<b>***** Navigating to West Law Edge Page then to Classic page and Visevarse *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie();

		PPPage pppage = new PPPage();
		pppage.validateNavigationFromWLEdgeToClassic();

		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.closeCookie();

		PPPage pppage2 = new PPPage();
		pppage2.validateNavigationFromWLClassicToEdge();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		closeDriver();
	}
}