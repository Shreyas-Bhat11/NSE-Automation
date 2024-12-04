package com.NSE.sfdc.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import org.openqa.selenium.support.PageFactory;

public class AccountPage extends DcpBasePage {

	public static int titleNameLinkCount;

	public AccountPage() {
		super("locatorsDefinitionSFDC/AccountPage.json");
		titleNameLinkCount = 0;
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method is used to fetch data from Account page after place order
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public AccountPage openEnterpriseTrailsLink() {

		scrollToParticularElement("TitleNameLink");
		titleNameLinkCount = getSizeOfElement("TitleNameLink");
		Utility.logGenerator("openEnterpriseTrailsLink", titleNameLinkCount > 0,
				"Captured TitleNameLink Count from SFDC: <b>" + titleNameLinkCount, "TitleNameLink is Empty", true);

		clickOnElement("TitleNameLink");
		waitForPageLoad();
		Utility.logGenerator("openEnterpriseTrailsLink", isListEmpty("TitleNameLink") == true,
				"Clicked on Enterprise Trails link and navigated to Enterprise Trails page successfully.",
				"Could not navigate to Enterprise Trails page", true);
		return this;
	}
}