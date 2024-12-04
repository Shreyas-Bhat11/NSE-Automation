package com.NSE.sfdc.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends DcpBasePage {

	public HomePage() {
		super("locatorsDefinitionSFDC/HomePage.json");
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method is to search for the created quote id and open the same
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public HomePage openQuoteId(String quoteId) {

		sendKeysTotheElement("SearchBox", quoteId);
		clickOnElement("SearchBoxButton");
		clickOnTheParameterisedXpath("QuoteIDLink", "PLACEHOLDER", quoteId);
		waitForPageLoad();
		Utility.logGenerator("openQuoteId", !isListEmpty("QuoteIdInProposalPage") == true, "Proposal Page is displayed",
				"Proposal page is not displayed", true);
		return this;
	}
}