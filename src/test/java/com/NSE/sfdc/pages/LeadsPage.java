package com.NSE.sfdc.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import org.openqa.selenium.support.PageFactory;

public class LeadsPage extends DcpBasePage {

	public static String leadsStatus, ownerName, name, accountName, description, email, phone, mobile, operatingUnit,
			lineOfBusiness, formOrganizationType, buRegion, frozenMarketCurrentYearSegmentL1, highLevelCustomerType;

	public LeadsPage() {
		super("locatorsDefinitionSFDC/LeadsPage.json");

		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method is used to fetch data from Leads page after place order
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public LeadsPage getLeadsDataAfterPlaceOrder() {

		leadsStatus = getTextOfElement("LeadsStatus");
		ownerName = getTextOfElement("OwnerName");
		name = getTextOfElement("Name");
		accountName = getTextOfElement("AccountName");
		description = getTextOfElement("Description");
		email = getTextOfElement("Email");
		phone = getTextOfElement("Phone");
		mobile = getTextOfElement("Mobile");
		operatingUnit = getTextOfElement("OperatingUnit");
		lineOfBusiness = getTextOfElement("LineOfBusiness");
		formOrganizationType = getTextOfElement("FormOrganizationType");
		buRegion = getTextOfElement("BURegion");
		frozenMarketCurrentYearSegmentL1 = getTextOfElement("FrozenMarketCurrentYearSegmentL1");
		highLevelCustomerType = getTextOfElement("HighLevelCustomerType");

		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !leadsStatus.isEmpty(),
				"Captured Lead Status from SFDC: <b>" + leadsStatus, "Lead Status is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !ownerName.isEmpty(),
				"Captured Owner Name from SFDC: <b>" + ownerName, "Owner Name is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !name.isEmpty(), "Captured Name from SFDC: <b>" + name,
				"Name is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !accountName.isEmpty(),
				"Captured Account Name from SFDC: <b>" + accountName, "Account Name is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !description.isEmpty(),
				"Captured Description from SFDC: <b>" + description, "Description is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !email.isEmpty(), "Captured Email from SFDC: <b>" + email,
				"Email is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !phone.isEmpty(), "Captured Phone from SFDC: <b>" + phone,
				"Phone is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !mobile.isEmpty(),
				"Captured Mobile from SFDC: <b>" + mobile, "Mobile is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !operatingUnit.isEmpty(),
				"Captured Operating Unit from SFDC: <b>" + operatingUnit, "Operating Unit is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !lineOfBusiness.isEmpty(),
				"Captured Line Of Business from SFDC: <b>" + lineOfBusiness, "Line Of Business is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !formOrganizationType.isEmpty(),
				"Captured Form Organization Type from SFDC: <b>" + formOrganizationType,
				"Form Organization Type is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !buRegion.isEmpty(),
				"Captured BU Region from SFDC: <b>" + buRegion, "BU Region is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !frozenMarketCurrentYearSegmentL1.isEmpty(),
				"Captured Frozen Market Current Year Segment L1 from SFDC: <b>" + frozenMarketCurrentYearSegmentL1,
				"Frozen Market Current Year Segment L1 is Empty", false);
		Utility.logGenerator("getTrailsDataAfterPlaceOrder", !highLevelCustomerType.isEmpty(),
				"Captured High Level Customer Type from SFDC: <b>" + highLevelCustomerType,
				"High Level Customer Type is Empty", false);
		return this;
	}
}