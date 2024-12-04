package com.NSE.sfdc.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import org.openqa.selenium.support.PageFactory;

public class TrailsPage extends DcpBasePage {

	public static String trailStatus, convertToSaleChecked, isLeadConvertedChecked, instantEntitlementChecked,
			entitlementStatusDescription, entitlementStatus, leads;

	public TrailsPage() {
		super("locatorsDefinitionSFDC/TrailsPage.json");
		trailStatus = "";
		convertToSaleChecked = "";
		isLeadConvertedChecked = "";
		instantEntitlementChecked = "";
		entitlementStatusDescription = "";
		entitlementStatus = "";
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method is used to fetch data from Trails page after place order
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public TrailsPage getTrailsDataAfterPlaceOrder() {

		trailStatus = getTextOfElement("TrailStatus");
		convertToSaleChecked = getAttributeValueOfGivenAttribute("ConvertToSale", "alt"); // alt = Checked
		isLeadConvertedChecked = getAttributeValueOfGivenAttribute("IsLeadConverted", "alt"); // alt = Checked
		instantEntitlementChecked = getAttributeValueOfGivenAttribute("InstantEntitlement", "alt");
		entitlementStatus = getTextOfElement("EntitlementStatus");
		entitlementStatusDescription = getTextOfElement("EntitlementStatusDescription");

		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !trailStatus.isEmpty(),
				"Captured Trail Status from SFDC: <b>" + trailStatus, "Trail Status is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !convertToSaleChecked.isEmpty(),
				"Captured ConvertToSaleChecked from SFDC: <b>" + convertToSaleChecked, "ConvertToSaleChecked is Empty",
				false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !isLeadConvertedChecked.isEmpty(),
				"Captured IsLeadConverted from SFDC: <b>" + isLeadConvertedChecked, "IsLeadConverted is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !instantEntitlementChecked.isEmpty(),
				"Captured InstantEntitlement from SFDC: <b>" + instantEntitlementChecked, "InstantEntitlement is Empty",
				false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !entitlementStatus.isEmpty(),
				"Captured EntitlementStatus from SFDC: <b>" + entitlementStatus, "EntitlementStatus is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !entitlementStatusDescription.isEmpty(),
				"Captured EntitlementStatusDescription from SFDC: <b>" + entitlementStatusDescription,
				"EntitlementStatusDescription is Empty", false);
		return this;
	}

	/**
	 * This method is used to click on leads
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public TrailsPage clickOnLeads() {

		leads = getTextOfElement("Leads");
		Utility.logGenerator("clickOnLeads", leads != null || leads.equals(""), "Captured Leads from SFDC: <b>" + leads,
				"Leads is Empty", true);

		clickOnElement("Leads");
		waitForPageLoad();
		Utility.logGenerator("clickOnLeads", isListEmpty("Leads") == true,
				"Clicked on leads and navigated to leads page successfully.", "Could not navigate to leads page", true);
		return this;
	}
}