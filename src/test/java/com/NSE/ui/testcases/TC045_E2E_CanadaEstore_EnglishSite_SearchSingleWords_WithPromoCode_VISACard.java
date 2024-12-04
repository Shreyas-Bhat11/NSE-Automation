package com.NSE.ui.testcases;

import com.NSE.commons.Utility;
import com.NSE.ui.pages.CanadaCartPage;
import com.NSE.ui.pages.CanadaHomePage;
import com.NSE.ui.pages.CanadaPDPPage;
import com.NSE.ui.pages.CanadaPPPPage;
import com.NSE.ui.pages.CheckOutPage;
import com.NSE.ui.pages.HeaderFooterPage;
import com.NSE.ui.pages.OrderConfirmationPage;

import com.tr.commons.BaseClass;
import com.tr.commons.ReadProperties;
import com.tr.commons.extentListeners.ExtentLogger;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class TC045_E2E_CanadaEstore_EnglishSite_SearchSingleWords_WithPromoCode_VISACard extends BaseClass {

	static String excelFilePath = ReadProperties.getConfig("DATASHEET_PATH");
	static String masterDataSheetName = ReadProperties.getConfig("NS_MASTERDATA_SHEETNAME");
	static String testDataSheetName = ReadProperties.getConfig("NS_TESTDATA_SHEETNAME");
	static String testCaseName = ReadProperties.getConfig("NS_TC045_TestcaseName");
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

	@Test(groups = { "tc:591058" }, description = "E2E_CanadaEstore_EnglishSite_Search2Words_WithPromoCode_VISACard")
	public void E2E_CanadaEstore_EnglishSite_Search2Words_WithPromoCode_VISACard() {

		Map<String, String> testdataMap = Utility.testdataMap;
		String languageFromExcel = testdataMap.get("Language");
		String creditCardFromExcel = Utility.dataMap.get(testdataMap.get("CreditCard"));
		String searchWordFromExcel = testdataMap.get("SearchWord");
		String promoCodeFromExcel = testdataMap.get("PromoCode");
		String invalidPromoCodeFromExcel = testdataMap.get("InvalidPromoCode");
		String productQuantityFromExcel = testdataMap.get("ProductQuantity");
		String updatedProductQuantityFromExcel = testdataMap.get("UpdatedProductQuantity");

		ExtentLogger.info("<b>***** Capturing values from Home page of Canada e-store site *****</b>");
		HeaderFooterPage headerFooterPage = new HeaderFooterPage();
		headerFooterPage.closeCookie();

		CanadaHomePage canadaHomePage = new CanadaHomePage();
		canadaHomePage.validateShopNowButton(languageFromExcel);

		ExtentLogger.info("<b>***** Searching in PPP page Canada e-store site *****</b>");
		CanadaPPPPage canadaPPPPage = new CanadaPPPPage();
		canadaPPPPage.validateFilterSection(languageFromExcel).validateSortSection(languageFromExcel)
				.validateProductDetailsIsPresent(languageFromExcel).validatePaginationsIsPresent(languageFromExcel)
				.enterInSearchBar(searchWordFromExcel).clickOnRandomProduct();

		ExtentLogger.info("<b>***** Adding product to cart and landing to Cart page in Canada e-store site *****</b>");
		CanadaPDPPage canadaPDPPage = new CanadaPDPPage();
		canadaPDPPage
				.validateProductDetails(CanadaPPPPage.randomProductSelected, CanadaPPPPage.productPrice,
						CanadaPPPPage.productDescription, CanadaPPPPage.productDate, CanadaPPPPage.publisher,
						CanadaPPPPage.productAuthor, languageFromExcel)
				.enterProductQuantity(productQuantityFromExcel).getProductQuantity().getProductFormatAndPrice()
				.getProductName().selectSmartSaverOption().clickOnAddToCartButton()
				.validateCartPopUpForSingleProduct(languageFromExcel, CanadaPDPPage.quantity,
						CanadaPDPPage.productFormat, CanadaPDPPage.productPrice, CanadaPPPPage.randomProductSelected,
						CanadaPDPPage.productName)
				.getPurchaseOptionFromCart().clickOnViewCartButton();

		ExtentLogger.info("<b>***** Landing on checkout page from cart page in Canada e-store site *****</b>");
		CanadaCartPage canadaCartPage = new CanadaCartPage();
		canadaCartPage
				.validateCardPageForSingleProduct(languageFromExcel, CanadaPPPPage.randomProductSelected,
						CanadaPDPPage.productName, CanadaPDPPage.quantity, CanadaPDPPage.productFormat,
						CanadaPDPPage.productPrice, CanadaPDPPage.purchaseOption)
				.enterQuantityForProduct(updatedProductQuantityFromExcel)
				.validatePromoCodeSection(languageFromExcel, promoCodeFromExcel, invalidPromoCodeFromExcel)
				.clickOnCheckoutButton().clickOnContinueAsGuestButton();

		ExtentLogger.info("<b>***** Filling deatils in Checkout page in Canada e-store site *****</b>");
		CheckOutPage checkOutPage = new CheckOutPage();
		checkOutPage.verifyLandedOnCheckoutPage().enterFirstName().enterLastName().enterEmail().enterPhoneNumber()
				.enterOrganizationName().enterAddress().clickOnContinueToShipping()
				.clickOnContinueToPaymentForCanadaEstore().enterCardHolderName().enterCardNumber(creditCardFromExcel)
				.selectMonth().selectYear().enterSecurityCode(3).clickOnAuthorizeCheckBox().clickOnPlaceOrder();

		ExtentLogger.info("<b>***** Validating Order Confirmation page in Canada e-store site *****</b>");
		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		orderConfirmationPage.verifyLandedOnOrderConfirmationPage()
				.verifyFirstNameInOrderConfirmation(CheckOutPage.firstName).getOrderNumber(true)
				.validateSurveyInCanadaEstore().fillSurveyForCanadaEstore();

		Utility.storeUIData(testCaseName);
		Utility.storeExecutionTime(testCaseName);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		closeDriver();
	}
}