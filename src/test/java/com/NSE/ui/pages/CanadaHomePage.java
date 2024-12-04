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

public class CanadaHomePage extends DcpBasePage {

	/**
	 * JSON file name used for validation
	 */
	static String homePageDataFileName = "CanadaHomePageData.json";

	public CanadaHomePage() {
		super("locatorsDefinition/CanadaHomePage.json");
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * This method is to search any word in the search bar
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaHomePage enterInSearchBar(String searchWord) {

		waitUntilElementVisible("SearchBox");
		sendKeysTotheElement("SearchBox", searchWord);
		ExtentLogger.info("Entered the word in the search bar: " + searchWord);
		clickOnElement("SearchButton");
		waitForPageLoad();
		Utility.logGenerator("enterInSearchBar", getURL().contains(searchWord.split(" ")[0]),
				"The search results are displayed for " + searchWord, "Unable to find the search results", true);
		return this;
	}

	/**
	 * This method validate shop now button
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaHomePage validateShopNowButton(String language) {

		ExtentLogger.info("Clicking on the Shop Now Button");
		Utility.validateUIDataField("validateShopNowButton", "ShopNowButtonText", getTextOfElement("ShopNowButton"),
				ReadJson.getStringValue(homePageDataFileName, "SHOP_NOW_BUTTON_" + language), "UI", true, "Equals");
		clickOnElement("ShopNowButton");
		waitForPageLoad();
		Utility.logGenerator("validateShopNowButton", getURL().contains("search"), "Navigated to PPP Page",
				"Unable to navigate to PPP Page", true);
		return this;
	}

	/**
	 * This method validate banner texts
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaHomePage validateBanner(String language) {

		ExtentLogger.info("********** Validating the banner texts **********");
		Utility.validateUIDataField("validateBanner", "BannerHeading", getTextOfElement("BannerHeading"),
				ReadJson.getStringValue(homePageDataFileName, "BANNER_HEADING_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateBanner", "BannerSubheading", getTextOfElement("BannerSubheading"),
				ReadJson.getStringValue(homePageDataFileName, "BANNER_SUBHEADING_" + language), "UI", true, "Equals");
		return this;
	}
}