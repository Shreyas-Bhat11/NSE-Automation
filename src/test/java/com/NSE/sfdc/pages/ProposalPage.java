package com.NSE.sfdc.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import com.tr.commons.extentListeners.ExtentLogger;

import org.openqa.selenium.support.PageFactory;

public class ProposalPage extends DcpBasePage {

	public static String quoteID, approvalStage, digitallySignedDate, currency, sapOrderStatus, quoteType,
			orderConfirmationContact, approvalSegment, paymentOption, paymentStatus, digitalQuoteType, occEmail,
			occName,
			// initializing the proposal page url here as it has to be used in same test
			// case twice
			proposalPageUrl = "";
	public static boolean soldToAcctSSDNotBlank, billToAcctSSDNotBlank, shipToAcctSSDNotBlank, payerAcctSSDNotBlank,
			accountIsBlank, cardType, cardAuthorizationNumber, cardExpirationDate;

	public ProposalPage() {
		super("locatorsDefinitionSFDC/ProposalPage.json");
		PageFactory.initElements(getDriver(), this);
	}

	public ProposalPage(String reset) {
		super("locatorsDefinitionSFDC/ProposalPage.json");
		soldToAcctSSDNotBlank = false;
		billToAcctSSDNotBlank = false;
		shipToAcctSSDNotBlank = false;
		payerAcctSSDNotBlank = false;
		accountIsBlank = false;
		cardType = false;
		cardAuthorizationNumber = false;
		cardExpirationDate = false;
		quoteID = "";
		approvalStage = "";
		digitallySignedDate = "";
		currency = "";
		sapOrderStatus = "";
		quoteType = "";
		orderConfirmationContact = "";
		approvalSegment = "";
		paymentOption = "";
		paymentStatus = "";
		digitalQuoteType = "";
		occEmail = "";
		occName = "";
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * This method is fetch data from proposal page after placing order
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public ProposalPage getProposalPageDataAfterPlaceOrder() {

		quoteID = getTextOfElement("QuoteID");
		approvalStage = getTextOfElement("ApprovalStage");
		soldToAcctSSDNotBlank = !isListEmpty("SoldToAcctSSD");
		billToAcctSSDNotBlank = !isListEmpty("BillToAcctSSD");
		shipToAcctSSDNotBlank = !isListEmpty("ShipToAcctSSD");
		accountIsBlank = !isListEmpty("Account");
		currency = getTextOfElement("Currency");
		digitallySignedDate = getTextOfElement("DigitallySignedDate");
		quoteType = getTextOfElement("QuoteType");
		orderConfirmationContact = getTextOfElement("OrderConfirmationContact");
		approvalSegment = getTextOfElement("ApprovalSegment");
		paymentOption = getTextOfElement("PaymentOption");
		paymentStatus = getTextOfElement("PaymentStatus");
		cardType = !getTextOfElement("CardType").equalsIgnoreCase(" ");
		cardAuthorizationNumber = !getTextOfElement("CardAuthorizationNumber").equalsIgnoreCase(" ");
		cardExpirationDate = !getTextOfElement("CardExpirationDate").equalsIgnoreCase(" ");
		digitalQuoteType = getTextOfElement("DigitalQuoteType");
		occEmail = getTextOfElement("OCCEmail");
		occName = getTextOfElement("OCCName");
		refreshPage();
		sapOrderStatus = getTextOfElement("SAPOrderStatus");

		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !quoteID.isEmpty(),
				"Captured Quote ID from SFDC: <b>" + quoteID, "Quote ID is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !approvalStage.isEmpty(),
				"Captured Approval Stage from SFDC: <b>" + approvalStage, "Approval Stage is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", soldToAcctSSDNotBlank == true,
				"Is SoldToAcctSSD Present in SFDC: <b>" + soldToAcctSSDNotBlank, "SoldToAcctSSD is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", billToAcctSSDNotBlank == true,
				"Is BillToAcctSSD Present in SFDC: <b>" + billToAcctSSDNotBlank, "BillToAcctSSD is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", shipToAcctSSDNotBlank == true,
				"Is ShipToAcctSSD Present in SFDC: <b>" + shipToAcctSSDNotBlank, "ShipToAcctSSD is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", accountIsBlank == true,
				"Is account Present in SFDC: <b>" + accountIsBlank, "Account is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !currency.isEmpty(),
				"Captured Currency from SFDC: <b>" + currency, "Currency is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !quoteType.isEmpty(),
				"Captured Quote Type from SFDC: <b>" + quoteType, "Quote Type is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !sapOrderStatus.isEmpty(),
				"Captured SAP Order Status from SFDC: <b>" + sapOrderStatus, "SAP Order Status is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !approvalSegment.isEmpty(),
				"Captured Approval Segment from SFDC: <b>" + approvalSegment, "Approval Segment is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !orderConfirmationContact.isEmpty(),
				"Captured Order Confirmation Contact from SFDC: <b>" + orderConfirmationContact,
				"Order Confirmation Contact is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !paymentOption.isEmpty(),
				"Captured Payment Option from SFDC: <b>" + paymentOption, "Payment Option is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !paymentStatus.isEmpty(),
				"Captured Payment Status from SFDC: <b>" + paymentStatus, "Payment Status is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !digitalQuoteType.isEmpty(),
				"Captured Digital Quote Type from SFDC: <b>" + digitalQuoteType, "Digital Quote Type is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !occEmail.isEmpty(),
				"Captured OCC Email from SFDC: <b>" + occEmail, "OCC Email is Empty", false);
		Utility.logGenerator("getProposalPageDataAfterPlaceOrder", !occName.isEmpty(),
				"Captured OCC Name from SFDC: <b>" + occName, "OCC Name is Empty", false);
		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", true,
				"Is Card Type present in SFDC: <b>" + cardType, "Card Type cannot be fetched", false);
		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", true,
				"Is Card Authorization Number present in SFDC: <b>" + cardAuthorizationNumber,
				"Card Authorization Number cannot be fetched", false);
		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", true,
				"Is Card Expiration Date present in SFDC: <b>" + cardExpirationDate,
				"Card Expiration Date cannot be fetched", false);
		return this;
	}

	/**
	 * This method is to click on Configuration link and open product configuration
	 * page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public ProposalPage clickOnConfig() {

		clickOnElement("ConfigurationLink");
		clickOnElement("ConfigurationName");
		waitUntilElementNotVisible("ConfigurationName");

		Utility.logGenerator("clickOnConfig", isListEmpty("ConfigurationName"),
				"Navigated to Product Configuration page" + occName, "Unable to navigate to product configuration page",
				false);
		return this;
	}

	/**
	 * This method is to get the proposal page url
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public ProposalPage getProposalPageUrl() {

		proposalPageUrl = getURL();
		ExtentLogger.info("Proposal Page URL: " + proposalPageUrl);
		return this;
	}

	/**
	 * This method is to navigate to proposal page
	 * 
	 * @param url
	 * @return
	 * @created by Ishwarya
	 */
	public ProposalPage navigateToProposalPage(String url) {

		navigateToLink(url);
		waitForPageLoad();
		Utility.logGenerator("navigateToProposalPage", getURL().equals(url), "Successfully navigated to Proposal page",
				"Could not navigate to Proposal page", true);
		return this;
	}

	/**
	 * This method is to click on Account link
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public ProposalPage clickOnAccount() {

		clickOnElement("Account");
		waitForPageLoad();
		Utility.logGenerator("clickOnAccount", isListEmpty("Account") == true,
				"Clicked on Account and navigated to Account page successfully.", "Could not navigate to Account page",
				true);
		return this;
	}
}