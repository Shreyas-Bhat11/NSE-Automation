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

public class CanadaCartPage extends DcpBasePage {

	/**
	 * JSON file name used for validation
	 */
	static String cartPageDataFileName = "CanadaCartPageData.json";

	public CanadaCartPage() {
		super("locatorsDefinition/CanadaCartPage.json");
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * This method is to validate the cart page
	 * 
	 * @created by Ishwarya
	 */
	public CanadaCartPage validateCardPageForSingleProduct(String language, String productHeadingFromPPPPage,
			String productHeadingFromPDPPage, String quantity, String format, String price, String purchaseOption) {

		ExtentLogger.info("********** Validating the cart page **********");
		// common validations
		Utility.validateUIDataField("validateCardPage", "IsCartIconPresent", !isListEmpty("CartIcon"), true, "UI", true,
				"Equals");
		Utility.validateUIDataField("validateCardPage", "CartHeading", getTextOfElement("CartPageHeading"),
				ReadJson.getStringValue(cartPageDataFileName, "CART_HEADING_" + language).replace("QUANTITY", quantity),
				"UI", true, "Equals");
		Utility.validateUIDataField("validateCardPage", "PaginationLabel", getTextOfElement("PaginationLabel").trim(),
				ReadJson.getStringValue(cartPageDataFileName, "RESULTS_PER_PAGE_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateCardPage", "DefaultPaginationNumber",
				getTextOfElement("PaginationDropdownTextBox"),
				ReadJson.getStringValue(cartPageDataFileName, "DEFAULT_PAGINATION_" + language), "UI", true, "Equals");
		clickOnElement("PaginationDropdownTextBox");
		for (int i = 1; i <= getSizeOfElement("PaginationDropdownCount"); i++) {
			Utility.validateUIDataField("validateCardPage", "PaginationDropdownOptions" + i,
					getTextOfParameterisedXpathElement("PaginationDropdownOptions", "PLACEHOLDER", String.valueOf(i)),
					String.valueOf(i * 5), "UI", true, "Equals");
		}

		// quantity box validations
		for (int i = 1; i <= getSizeOfElement("NumberOfProducts"); i++) {

			String currentQuantity = attributeOfParameterisedElement("ProductQuantities", "PLACEHOLDER",
					String.valueOf(i), "value");
			Utility.validateUIDataField("validateCardPage", "MinimumQuantityOfProduct_" + i,
					attributeOfParameterisedElement("ProductQuantities", "PLACEHOLDER", String.valueOf(i), "min"), "1",
					"UI", true, "Equals");
			Utility.validateUIDataField("validateCardPage", "MaximumQuantityOfProduct_" + i,
					attributeOfParameterisedElement("ProductQuantities", "PLACEHOLDER", String.valueOf(i), "max"), "99",
					"UI", true, "Equals");
			waitUntilElementVisible("AddButtonInQuantity");
			waitForFixedTime(2);
			clickElementUsingJSScript("AddButtonInQuantity");
			// waiting for quantity to be updated
			waitForFixedTime(2);
			Utility.validateUIDataField("validateCardPage", "QuantityAfterClickingAddButton",
					attributeOfParameterisedElement("ProductQuantities", "PLACEHOLDER", String.valueOf(i), "value"),
					String.valueOf(Integer.parseInt(currentQuantity) + 1), "UI", true, "Equals");
			clickElementUsingJSScript("RemoveButtonInQuantity");
			// waiting for quantity to be updated
			waitForFixedTime(2);
			Utility.validateUIDataField("validateCardPage", "QuantityAfterClickingSubtractButton",
					attributeOfParameterisedElement("ProductQuantities", "PLACEHOLDER", String.valueOf(i), "value"),
					currentQuantity, "UI", true, "Equals");

			// add validations for error message by adding 100 products

		}

		// product details validations
		Utility.validateUIDataField("validateCardPage", "ProductHeading", getTextOfElement("ProductHeading"),
				productHeadingFromPPPPage + ", " + format, "UI", true, "Contains");
		Utility.validateUIDataField("validateCartPopUp", "ProductCardIconIsPresent", !isListEmpty("NumberOfProducts"),
				true, "UI", true, "Equals");
		Utility.validateUIDataField("validateCardPage", "NumberOfProducts",
				getAttributeValueOfGivenAttribute("NumberOfProducts", "value"), quantity, "UI", true, "Equals");
		Utility.validateUIDataField("validateCardPage", "SingleProductFormat", getTextOfElement("SingleProductFormat"),
				format, "UI", true, "Contains");
		Utility.validateUIDataField("validateCardPage", "SingleProductPrice", getTextOfElement("SingleProductPrice"),
				price, "UI", true, "Equals");
		Utility.validateUIDataField("validateCardPage", "SingleProductPurchaseOption",
				getTextOfElement("SingleProductPurchaseOption"), purchaseOption, "UI", true, "Equals");

		ExtentLogger.info("********** Validating the order summary page in cart page **********");
		// order summary validations
		float totalProductPrice = 0;
		for (int i = 1; i <= getSizeOfElement("ProductSubTotalPriceCount"); i++) {

			String stringPrice = getTextOfParameterisedXpathElement("ProductSubTotalPrice", "PLACEHOLDER",
					String.valueOf(i));
			stringPrice = stringPrice.replaceAll("[^0-9.]", "").trim();
			float priceOfProduct = Float.parseFloat(stringPrice);
			totalProductPrice = totalProductPrice + priceOfProduct;
		}

		Utility.validateUIDataField("validateCardPage", "TotalPriceInOrderSummary",
				"$" + Utility.formatFloatTo2DigitNumber(totalProductPrice),
				getTextOfElement("TotalPriceInOrderSummary").replaceAll("[^0-9.$]", "").trim(), "UI_UI", true, "Equals", "Total of sub total prices",
				"Total price in order summary");
		Utility.validateUIDataField("validateCardPage", "OrderSummaryHeading", getTextOfElement("OrderSummaryHeading"),
				ReadJson.getStringValue(cartPageDataFileName, "ORDER_SUMMARY_HEADING_" + language), "UI", true,
				"Equals");
		Utility.validateUIDataField("validateCardPage", "OrderSummaryEstTotalText",
				getTextOfElement("OrderSummaryEstimationText"),
				ReadJson.getStringValue(cartPageDataFileName, "ORDER_SUMMARY_EST_HEADING_" + language), "UI", true,
				"Equals");
		Utility.validateUIDataField("validateCardPage", "OrderSummaryTaxText", getTextOfElement("OrderSummaryTaxText"),
				ReadJson.getStringValue(cartPageDataFileName, "ORDER_SUMMARY_TEXT_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateCardPage", "OrderSummaryCheckoutButton",
				getTextOfElement("OrderSummaryCheckoutButton"),
				ReadJson.getStringValue(cartPageDataFileName, "CHECKOUT_BUTTON_" + language), "UI", true, "Equals");

		return this;
	}

	/**
	 * This method is to click on checkout
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaCartPage clickOnCheckoutButton() {

		ExtentLogger.info("********** Clicking on checkout button **********");
		clickOnElement("OrderSummaryCheckoutButton");
		Utility.validateUIDataField("clickOnCheckoutButton", "IsSignInPopupDisplayed",
				!isListEmpty("SignInPopupHeading"), true, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is to signin popup in cart page
	 * 
	 * @created by Ishwarya
	 */
	public CanadaCartPage validateSignInPopup() {

		ExtentLogger.info("********** Validating the sign in popup **********");
		Utility.validateUIDataField("validateSignInPopup", "SignInPopupHeading", getTextOfElement("SignInPopupHeading"),
				ReadJson.getStringValue(cartPageDataFileName, ""), "UI", true, "Equals");

		return this;
	}

	/**
	 * This method is to click on continue as guest button
	 * 
	 * @created by Ishwarya
	 */
	public CanadaCartPage clickOnContinueAsGuestButton() {

		ExtentLogger.info("********** Clicking on continue as guest button **********");
		clickOnElement("SignInPopupGuestLoginButton");
		waitForPageLoad();
		Utility.logGenerator("clickOnContinueAsGuestButton", isListEmpty("SignInPopupGuestLoginButton") == true,
				"Navigated to checkout page.", "unable to navigate to checkout page", true);
		return this;
	}

	/**
	 * This method is to validate promo code section
	 * 
	 * @created by Ishwarya
	 */
	public CanadaCartPage validatePromoCodeSection(String language, String promoCode, String invalidPromoCode) {

		ExtentLogger.info("********** Validating promo code section in cart page **********");

		Utility.validateUIDataField("validatePromoCodeSection", "PromoCodeCollapsedText",
				getTextOfElement("PromoCodeCollapsedText"),
				ReadJson.getStringValue(cartPageDataFileName, "PROMO_CODE_HEADING_" + language), "UI", true, "Equals");
		clickOnElement("PromoCodeCollapsedText");
		Utility.validateUIDataField("validatePromoCodeSection", "PromoCodeExpandedText",
				getTextOfElement("PromoCodeExpandedText"),
				ReadJson.getStringValue(cartPageDataFileName, "PROMO_CODE_HEADING_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validatePromoCodeSection", "PromoCodeInputFieldHeading",
				getTextOfElement("PromoCodeInputFieldHeading"),
				ReadJson.getStringValue(cartPageDataFileName, "PROMO_CODE_INPUT_HEADING_" + language), "UI", true,
				"Equals");
		Utility.validateUIDataField("validatePromoCodeSection", "IsPromoCodeInputFieldPresent",
				!isListEmpty("PromoCodeInputField"), true, "UI", true, "Equals");
		doubleClickOnElement("PromoCodeInputField");
		sendKeysTotheElement("PromoCodeInputField", invalidPromoCode);
		clickOnElement("PromoCodeApplyButton");
		waitUntilElementNotVisible("PromoCodeSpinning");
		Utility.validateUIDataField("validatePromoCodeSection", "PromoCodeErrorMessage",
				getTextOfElement("PromoCodeErrorMessage"),
				ReadJson.getStringValue(cartPageDataFileName, "PROMO_CODE_ERROR_MESSAGE_" + language), "UI", true,
				"Equals");

		// 10OFF coupon should work only for 5-9 products in total
		int quantity = 0;
		for (int noOfProducts = 1; noOfProducts <= getSizeOfElement("NumberOfProducts"); noOfProducts++) {
			quantity = quantity + Integer.valueOf(attributeOfParameterisedElement("ProductQuantities", "PLACEHOLDER",
					String.valueOf(noOfProducts), "value"));
		}
		if (quantity >= 5 && quantity <= 9) {
			doubleClickOnElement("PromoCodeInputField");
			sendKeysTotheElement("PromoCodeInputField", promoCode);
			clickOnElement("PromoCodeApplyButton");
			waitUntilElementNotVisible("PromoCodeSpinning");
			Utility.validateUIDataField("validatePromoCodeSection", "PromoCodeAppliedHeader",
					getTextOfElement("PromoCodeAppliedHeader"),
					ReadJson.getStringValue(cartPageDataFileName, "PROMO_APPLIED_" + language), "UI", true, "Equals");
			Utility.validateUIDataField("validatePromoCodeSection", "PromoCodeAppliedMessage",
					getTextOfElement("PromoCodeAppliedMessage"),
					ReadJson.getStringValue(cartPageDataFileName, "PROMO_10_OFF_" + language), "UI", true, "Equals");
		} else {
			Utility.validateUIDataField("validatePromoCodeSection", "IsPromoCodeAppliedTextNotPresent",
					isListEmpty("PromoCodeAppliedMessage"), true, "UI", true, "Equals");
		}

		// add saved price and updated price code for products and order summary
		return this;
	}

	public CanadaCartPage enterQuantityForProduct(String quantity) {

		doubleClickOnElement("NumberOfProducts");
		waitForFixedTime(1);
		sendKeysTotheElement("NumberOfProducts", quantity);
		ExtentLogger.info("Entered quantity for the product as: " + quantity);
		return this;
	}
}