package com.NSE.sfdc.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductConfigurationPage extends DcpBasePage {

	public static String approvalSegment, status;
	public static boolean totalingGroups, couponCode;
	public static int summaryGroupsCount, lineItemIdCount;
	public static List<String> summaryGroupsNames = null, lineItemIds = null;

	public ProductConfigurationPage() {
		super("locatorsDefinitionSFDC/ProductConfigurationPage.json");
		approvalSegment = "";
		status = "";
		totalingGroups = false;
		couponCode = false;
		summaryGroupsCount = 0;
		lineItemIdCount = 0;
		summaryGroupsNames = new ArrayList<String>();
		lineItemIds = new ArrayList<String>();
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method is used to get the data from Product Configuration page after
	 * order place
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public ProductConfigurationPage getProductConfigPageDataAfterPlaceOrder() {

		approvalSegment = getTextOfElement("ApprovalSegment");
		status = getTextOfElement("Status");
		totalingGroups = !isListEmpty("TotalingGroups");
		couponCode = !isListEmpty("CouponCodes");
		summaryGroupsCount = getSizeOfElement("SummaryGroups");
		for (int i = 1; i <= summaryGroupsCount; i++) {
			summaryGroupsNames
					.add(getTextOfParameterisedXpathElement("SummaryGroupsNames", "PLACEHOLDER", String.valueOf(i)));
			Collections.sort(summaryGroupsNames);
		}
		lineItemIdCount = getSizeOfElement("LineItemIdLink");
		for (int i = 1; i <= lineItemIdCount; i++) {
			lineItemIds.add(getHrefLinkOfParameterisedXpath("LineItemIds", "PLACEHOLDER", String.valueOf(i)));
		}

		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", !approvalSegment.isEmpty(),
				"Captured Approval Segment from SFDC: <b>" + approvalSegment, "Approval Segment is Empty", false);
		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", !status.isEmpty(),
				"Captured Status from SFDC: <b>" + status, "Status is Empty", false);
		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", totalingGroups == true,
				"Is Total grouping section present in SFDC: <b>" + totalingGroups, "Total grouping section is Empty",
				false);
		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", true,
				"Is Coupon code present in SFDC: <b>" + couponCode, "Coupon code cannot be fetched", false);
		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", summaryGroupsCount > 0,
				"Captured Summary Groups Names list from SFDC: <b>" + summaryGroupsNames, "Summary Groups is Empty",
				false);
		Utility.logGenerator("getProductConfigPageDataAfterPlaceOrder", lineItemIdCount > 0,
				"Captured Line Item IDs list from SFDC: <b>" + lineItemIds, "Line Item IDs is Empty", false);

		return this;
	}
}