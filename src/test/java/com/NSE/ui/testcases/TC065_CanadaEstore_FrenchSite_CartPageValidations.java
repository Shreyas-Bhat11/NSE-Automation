package com.NSE.ui.testcases;

import com.NSE.commons.Utility;
import com.NSE.ui.pages.CanadaCartPage;
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

public class TC065_CanadaEstore_FrenchSite_CartPageValidations extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC060_TestcaseName");
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

	@Test(groups = { "tc:670778" }, description = "CanadaEstore_EnglishSite_CartPageValidations")
	public void CanadaEstore_EnglishSite_CartPageValidations() {

		Map<String, String> testdataMap = Utility.testdataMap;
		String languageFromExcel = testdataMap.get("Language");
		String promoCodeFromExcel = testdataMap.get("PromoCode");
		String invalidPromoCodeFromExcel = testdataMap.get("InvalidPromoCode");

		ExtentLogger.info("<b>***** Launching Canada e-store site *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie();

		ExtentLogger.info("<b>***** Naviagting to PPP Page from Home page of Canada e-store site *****</b>");
		CanadaHomePage canadaHomePage = new CanadaHomePage();
		canadaHomePage.validateShopNowButton(languageFromExcel);

		ExtentLogger.info("<b>***** Selecting a random product to reach PDP Page of Canada e-store site *****</b>");
		CanadaPPPPage canadaPPPPage = new CanadaPPPPage();
		canadaPPPPage.enterInSearchBar("government").clickOnRandomProduct();

		ExtentLogger.info("<b>***** Validating cart popup in PDP Page of Canada e-store site *****</b>");
		CanadaPDPPage canadaPDPPage = new CanadaPDPPage();
		canadaPDPPage.getProductQuantity().getProductFormatAndPrice().getProductName().selectSmartSaverOption()
				.clickOnAddToCartButton()
				.validateCartPopUpForSingleProduct(languageFromExcel, CanadaPDPPage.quantity,
						CanadaPDPPage.productFormat, CanadaPDPPage.productPrice, CanadaPPPPage.randomProductSelected,
						CanadaPDPPage.productName)
				.clickOnViewCartButton();

		ExtentLogger.info("<b>***** Validating cart page in Canada e-store site *****</b>");
		HeaderFooterPage headerFooterPage2 = new HeaderFooterPage();
		headerFooterPage2.validateBooksLinkInHeader().validateCanadaSiteFooterData();

		CanadaCartPage canadaCartPage = new CanadaCartPage();
		canadaCartPage
				.validateCardPageForSingleProduct(languageFromExcel, CanadaPPPPage.randomProductSelected,
						CanadaPDPPage.productName, CanadaPDPPage.quantity, CanadaPDPPage.productFormat,
						CanadaPDPPage.productPrice, CanadaPDPPage.purchaseOption)
				.validatePromoCodeSection(languageFromExcel, promoCodeFromExcel, invalidPromoCodeFromExcel)
				.clickOnCheckoutButton();

		Utility.storeUIData(testCaseName);
		Utility.storeExecutionTime(testCaseName);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}