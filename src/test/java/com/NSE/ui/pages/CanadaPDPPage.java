package com.NSE.ui.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import com.tr.commons.ReadJson;
import com.tr.commons.extentListeners.ExtentLogger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;

public class CanadaPDPPage extends DcpBasePage {

	public static String quantity = "", productFormat = "", productPrice = "", productName = "", purchaseOption = "";
	/**
	 * JSON file name used for validation
	 */
	static String pdpPageDataFileName = "CanadaPDPPageData.json";

	public CanadaPDPPage() {
		super("locatorsDefinition/CanadaPDPPage.json");
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * This method validate product details in PDP page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaPDPPage validateProductDetails(String productTitle, String productPrice, String productDescription,
			String productDate, String publisher, String productAuthor, String language) {

		ExtentLogger.info("********** Validating product details in PDP page **********");
		// Validating the product details from PPP Page
		Utility.validateUIDataField("validateProductDetails", "ProductTitleInPDPPage", getTextOfElement("ProductTitle"),
				productTitle, "UI_UI", true, "Equals", "ProductTitleFromPDPPage", "ProductTitleFromPPPPage");

		String pdpPrices = "";
		for (int bookFormats = 1; bookFormats <= getSizeOfElement("BookFormatCount"); bookFormats++) {
			pdpPrices = getTextOfParameterisedXpathElement("BookPrices", "PLACEHOLDER", String.valueOf(bookFormats))
					+ " ";
		}
		// Removing special characters from prices
		float pdpPriceFloat = Float.parseFloat(pdpPrices.replaceAll("[^0-9.]", ""));
		float pppPriceFloat = Float.parseFloat(productPrice.replaceAll("[^0-9.]", ""));

		Utility.validateUIDataField("validateProductDetails", "PPPProductPriceIsLessThanOrEqualToPDPPageProductPrice",
				(pppPriceFloat <= pdpPriceFloat || pdpPrices.trim().equals(productPrice.trim())), true, "UI", true,
				"Equals");
		waitForElementToBeClickable("ProductDetailsTab");
		clickOnElement("ProductDetailsTab");
		waitUntilElementVisible("ProductDate");
		Utility.validateUIDataField("validateProductDetails", "IsProductDescriptionPresentInPDPPage",
				!isListEmpty("ProductDescription"), true, "UI", true, "Equals");
		Utility.validateUIDataField("validateProductDetails", "ProductDateInPDPPage", getTextOfElement("ProductDate"),
				productDate, "UI_UI", true, "Equals", "ProductDateFromPDPPage", "ProductDateFromPPPPage");
		if (publisher.equals("")) {
			Utility.validateUIDataField("validateProductDetails", "PublisherInPDPPage", getTextOfElement("Publisher"),
					publisher, "UI_UI", true, "Equals", "PublisherFromPDPPage", "PublisherFromPPPPage");
		}
		if (productAuthor.equals("")) {
			Utility.validateUIDataField("validateProductDetails", "ProductAuthorInPDPPage",
					getTextOfElement("ProductAuthor"),
					ReadJson.getStringValue(pdpPageDataFileName, "AUTHOR_HEADING_" + language) + productAuthor, "UI_UI",
					true, "Equals", "ProductAuthorFromPDPPage", "ProductAuthorFromPPPPage");
		}
		Utility.validateUIDataField("validateProductDetails", "IsProductImagePresentInPDPPage",
				!isListEmpty("ProductImage"), true, "UI", true, "Equals");

		// Validations in product details section
		Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionQuantityHeading",
				getTextOfElement("ProductDetailsSectionQuantity"),
				ReadJson.getStringValue(pdpPageDataFileName, "QUANTITY_HEADING_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateProductDetails", "IsQuantityDecreaseButtonPresentInPDPPage",
				!isListEmpty("ProductDetailsSectionDecQuantityButton"), true, "UI", true, "Equals");
		Utility.validateUIDataField("validateProductDetails", "IsQuantityIncreaseButtonPresentInPDPPage",
				!isListEmpty("ProductDetailsSectionIncQuantityButton"), true, "UI", true, "Equals");
		Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionAvailabilityHeading",
				getTextOfElement("ProductDetailsSectionAvailabilityHeading"),
				ReadJson.getStringValue(pdpPageDataFileName, "AVAILABILITY_HEADING_" + language), "UI", true, "Equals");

		if (!isListEmpty("ProductDetailsSectionPurchasingOption1")) {
			Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionPurchaseOptionHeading",
					getTextOfElement("ProductDetailsSectionPurchaseOptionHeading"),
					ReadJson.getStringValue(pdpPageDataFileName, "PURCHASING_OPTION_HEADING_" + language), "UI", true,
					"Equals");
			Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionPurchasingOption1",
					getTextOfElement("ProductDetailsSectionPurchasingOption1"),
					ReadJson.getStringValue(pdpPageDataFileName, "PURCHASING_OPTION_1_" + language), "UI", true,
					"Equals");
			Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionPurchasingCheckBoxText",
					getTextOfElement("ProductDetailsSectionPurchasingCheckBoxText"),
					ReadJson.getStringValue(pdpPageDataFileName, "PURCHASING_OPTION_1_CHECKBOX_TEXT_" + language), "UI",
					true, "Equals");
			Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionPurchasingOption1SubText",
					getTextOfElement("ProductDetailsSectionPurchasingOption1SubText"),
					ReadJson.getStringValue(pdpPageDataFileName, "PURCHASING_OPTION_1_TEXT_" + language), "UI", true,
					"Equals");

			Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionPurchasingOption2",
					getTextOfElement("ProductDetailsSectionPurchasingOption2"),
					ReadJson.getStringValue(pdpPageDataFileName, "PURCHASING_OPTION_2_" + language), "UI", true,
					"Equals");
			clickOnElement("ProductDetailsSectionPurchasingOption1");
			Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionUpdateTextForOption1",
					getTextOfElement("ProductDetailsSectionUpdateText"),
					ReadJson.getStringValue(pdpPageDataFileName, "PURCHASING_OPTION_UPDATE_TEXT_" + language), "UI",
					true, "Equals");
			clickOnElement("ProductDetailsSectionPurchasingOption2");
			Utility.validateUIDataField("validateProductDetails", "IsUpdateTextNotPresentForOption2",
					isListEmpty("ProductDetailsSectionUpdateText"), true, "UI", true, "Equals");
		} else {
			Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionPurchaseOptionHeading",
					getTextOfElement("ProductDetailsSectionPurchaseOptionHeading"),
					ReadJson.getStringValue(pdpPageDataFileName, "PURCHASING_OPTION_HEADING_" + language)
							.replace("ptions", "ption"),
					"UI", true, "Equals");
			Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionPurchasingOption",
					getTextOfElement("ProductDetailsSectionPurchasingOption"),
					ReadJson.getStringValue(pdpPageDataFileName, "PURCHASING_OPTION_2_" + language), "UI", true,
					"Equals");
		}
		Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionDefaultQuantity",
				getAttributeValueOfGivenAttribute("ProductDetailsSectionQuantityInput", "value"),
				ReadJson.getStringValue(pdpPageDataFileName, "DEFAULT_QUANTITY_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionMinimumQuantity",
				getAttributeValueOfGivenAttribute("ProductDetailsSectionQuantityInput", "min"),
				ReadJson.getStringValue(pdpPageDataFileName, "MIN_QUANTITY_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionMaximumQuantity",
				getAttributeValueOfGivenAttribute("ProductDetailsSectionQuantityInput", "max"),
				ReadJson.getStringValue(pdpPageDataFileName, "MAX_QUANTITY_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateProductDetails", "IsAddToCartButtonPresent",
				!isListEmpty("ProductDetailsSectionAddToCart"), true, "UI", true, "Equals");
		Utility.validateUIDataField("validateProductDetails", "ProductDetailsSectionAddToCartButtonText",
				getTextOfElement("ProductDetailsSectionAddToCart"),
				ReadJson.getStringValue(pdpPageDataFileName, "ADD_TO_CART_" + language), "UI", true, "Equals");
//BELOW CODE NEED CONFIRMATION FROM BE TEAM
		// Utility.validateUIDataField("validateProductDetails", "IsTOCIconPresent",
		// !isListEmpty("TOCIcon"), true, "UI",
//				true, "Equals");
//		Utility.validateUIDataField("validateProductDetails", "TOCText", getTextOfElement("TOCText"),
//				ReadJson.getStringValue(pdpPageDataFileName, "TOC_HEADING_" + language), "UI", true, "Equals");
//		Utility.validateUIDataField("validateProductDetails", "TOCSubText", getTextOfElement("TOCSubText"),
//				ReadJson.getStringValue(pdpPageDataFileName, "TOC_SUBTEXT_" + language), "UI", true, "Equals");
//		Utility.validateUIDataField("validateProductDetails", "IsIndexIconPresent", !isListEmpty("IndexIcon"), true,
//				"UI", true, "Equals");
//		Utility.validateUIDataField("validateProductDetails", "IndexText", getTextOfElement("IndexText"),
//				ReadJson.getStringValue(pdpPageDataFileName, "INDEX_HEADING_" + language), "UI", true, "Equals");
//		Utility.validateUIDataField("validateProductDetails", "IndexSubText", getTextOfElement("IndexSubText"),
//				ReadJson.getStringValue(pdpPageDataFileName, "INDEX_SUBTEXT_" + language), "UI", true, "Equals");

		return this;
	}

	/**
	 * This method validate click on add to cart button in PDP page
	 * 
	 * @created by Ishwarya
	 */
	public CanadaPDPPage clickOnAddToCartButton() {

		ExtentLogger.info("Clicking on Add to Cart button in PDP page");
		clickOnElement("ProductDetailsSectionAddToCart");
		// wait for popup to load and if spinner is present wait for it to disappear
		waitForFixedTime(2);
		waitUntilElementVisible("CartPopup");

		Utility.validateUIDataField("clickOnAddToCartButton", "IsCartPopUpDisplayed", !isListEmpty("CartPopup"), true,
				"UI", true, "Equals");
		return this;
	}

	/**
	 * This method is to select the smart saver option in PDP page
	 * 
	 * @created by Ishwarya
	 */
	public CanadaPDPPage selectSmartSaverOption() {

		if (!isListEmpty("ProductDetailsSectionPurchasingCheckBoxText")) {
			ExtentLogger.info("Selecting Smart Saver option in PDP page if present");
			scrollToParticularElement("ProductDetailsSectionPurchasingCheckBoxText");
			clickOnElement("ProductDetailsSectionPurchasingOption1");
			clickOnElement("ProductDetailsSectionPurchasingCheckBoxText");
			ExtentLogger.pass("Smart Saver option selected successfully in PDP page", true);
			waitForFixedTime(2);
		}
		return this;
	}

	/**
	 * This method validate cart popup in PDP page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaPDPPage validateCartPopUpForSingleProduct(String language, String quantity, String format,
			String price, String productHeadingFromPPPPage, String productHeadingFromPDPPage) {

		ExtentLogger.info("********** Validating Cart Popup in PDP page **********");
		// item instead of items if quantity is 1
		String expectedHeading = ReadJson.getStringValue(pdpPageDataFileName, "CART_HEADING_" + language)
				.replace("QUANTITY", quantity);
		if (quantity.equals("1")) {
			expectedHeading = expectedHeading.substring(0, expectedHeading.length() - 1);
		}
		Utility.validateUIDataField("validateCartPopUp", "CartPopupHeading", getTextOfElement("CartPopupHeading"),
				expectedHeading, "UI", true, "Equals");

		Utility.validateUIDataField("validateCartPopUp", "IsViewCartButtonPresentInCartPopup",
				!isListEmpty("CartPopupViewCartButton"), true, "UI", true, "Equals");
		Utility.validateUIDataField("validateCartPopUp", "ViewCartButtonText",
				getTextOfElement("CartPopupViewCartButton"),
				ReadJson.getStringValue(pdpPageDataFileName, "VIEW_CART_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateCartPopUp", "IsCartPopupCloseButtonPresentInCartPopup",
				!isListEmpty("CartPopupClose"), true, "UI", true, "Equals");
//BELOW 2 LINES NEED CONFIRMATION FROM FE TEAM - PRODUCT NAMES ARE NOT IN RIGHT FORMAT OR THE FORMAT IS NOT PROVIDED BY FE TEAM
//		String productHeadingInCartPopup = getTextOfElement("CartPopupProductHeading");
//		Utility.validateUIDataField("validateCartPopUp", "CartPopupProductHeadingWithPPPPage",
//				(productHeadingFromPPPPage + ", " + format).trim(), productHeadingInCartPopup.trim(), "UI_UI", true,
//				"Conatins", "ProductHeadingFromPPPPage", "ProductHeadingFromCartPage");
//		Utility.validateUIDataField("validateCartPopUp", "CartPopupProductHeadingWithPDPPage",
//				(productHeadingFromPDPPage + ", " + format).trim(), productHeadingInCartPopup.trim(), "UI_UI", true,
//				"Conatins", "ProductHeadingFromPPPPage", "ProductHeadingFromCartPage");
		Utility.validateUIDataField("validateCartPopUp", "CartPopupProductQuantity",
				getTextOfElement("CartPopupProductQuantity"),
				ReadJson.getStringValue(pdpPageDataFileName, "CART_POPUP_QUANTITY_" + language) + quantity, "UI", true,
				"Equals");
		Utility.validateUIDataField("validateCartPopUp", "CartPopupProductFormat",
				getTextOfElement("CartPopupProductFormat"), format, "UI", true, "Equals");
		Utility.validateUIDataField("validateCartPopUp", "CartPopupProductPrice",
				getTextOfElement("CartPopupProductPrice"), price, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method click on view cart button in cart popup in PDP page
	 * 
	 * @created by Ishwarya
	 */
	public CanadaPDPPage clickOnViewCartButton() {

		ExtentLogger.info("Clicking on View Cart button in PDP page");
		if (isListEmpty("CartPopupViewCartButton")) {
			clickOnElement("CartIcon");
		}
		clickElementUsingJSScript("CartPopupViewCartButton");
		waitForPageLoad();
		Utility.logGenerator("clickOnViewCartButton", isListEmpty("CartPopupViewCartButton") == true,
				"Navigated to cart page.", "Unable to navigate to cart page.", null);
		return this;
	}

	/**
	 * This method is to fetch the product quantity in PDP page
	 */
	public CanadaPDPPage getProductQuantity() {

		quantity = getAttributeValueOfGivenAttribute("ProductDetailsSectionQuantityInput", "value");
		ExtentLogger.info("Product quantity fetched from PDP page: " + quantity);
		return this;
	}

	/**
	 * This method is to fetch product format and price in PDP page
	 */
	public CanadaPDPPage getProductFormatAndPrice() {

		int bookFormats = getSizeOfElement("BookFormatCount");
		int random = Utility.generateNumberFromRange(1, bookFormats);
		clickOnTheParameterisedXpath("BookFormats", "PLACEHOLDER", String.valueOf(random));
		productFormat = getTextOfParameterisedXpathElement("BookFormats", "PLACEHOLDER", String.valueOf(random));
		productPrice = getTextOfParameterisedXpathElement("BookPrices", "PLACEHOLDER", String.valueOf(random));
		ExtentLogger.info("Product format fetched from PDP page: " + productFormat);
		ExtentLogger.info("Product price fetched from PDP page: " + productPrice);
		return this;
	}

	/**
	 * This method is to fetch product name from PDP page
	 */
	public CanadaPDPPage getProductName() {

		productName = getTextOfElement("ProductTitle");
		ExtentLogger.info("Product name fetched from PDP page: " + productName);
		return this;
	}

	/**
	 * This method is to fetch product name from PDP page
	 */
	public CanadaPDPPage getPurchaseOptionFromCart() {

		purchaseOption = getTextOfElement("CartPopupPurchaseOption");
		ExtentLogger.info("Product name fetched from PDP page: " + productName);
		return this;
	}

	public CanadaPDPPage enterProductQuantity(String quantity) {

		doubleClickOnElement("ProductDetailsSectionQuantityInput");
		waitForFixedTime(1);
		sendKeysTotheElement("ProductDetailsSectionQuantityInput", quantity);
		ExtentLogger.info("Product quantity " + quantity + " entered successfully in PDP page");
		return this;
	}
}