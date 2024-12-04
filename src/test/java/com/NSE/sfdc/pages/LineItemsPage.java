package com.NSE.sfdc.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;

public class LineItemsPage extends DcpBasePage {

	// productName is key and the details are stored in map
	public static HashMap<String, HashMap<String, Object>> lineItemDetails = null;

	public LineItemsPage() {

		super("locatorsDefinitionSFDC/LineItemsPage.json");
		lineItemDetails = new HashMap<>();
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method is used to get the data from Line item page of each line item
	 * present in product configuration page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public LineItemsPage getLineItemDetails(List<String> lineItemIds) {

		for (int i = 0; i < lineItemIds.size(); i++) {

			String approvalSegment = "", numberOfAttorney = "", netPrice = "", quantity = "", contractTerm = "",
					productName = "", lineStatus = "", pricingStatus = "";
			boolean hasIncentives, couponCode;

			navigateToLink(lineItemIds.get(i));
			waitForPageLoad();
			HashMap<String, Object> lineItemData = new HashMap<>();
			approvalSegment = getTextOfElement("ApprovalSegment");
//			numberOfAttorney = getTextOfElement("NumberOfAttorney");
			netPrice = getTextOfElement("NetPrice");
			quantity = getTextOfElement("Quantity").replaceFirst("\\..*", "");
			contractTerm = getTextOfElement("ContractTerm");
			productName = getTextOfElement("ProductName");
			lineStatus = getTextOfElement("LineStatus");
			pricingStatus = getTextOfElement("PricingStatus");
			couponCode = !getTextOfElement("CouponCode").equalsIgnoreCase(" ");
			hasIncentives = getAttributeValueOfGivenAttribute("HasIncentives", "alt").equalsIgnoreCase("Not Checked")
					? false
					: true;
			lineItemData.put("ApprovalSegment", approvalSegment);
//			lineItemData.put("NumberOfAttorney", numberOfAttorney);
			lineItemData.put("NetPrice", netPrice);
			lineItemData.put("Quantity", quantity);
			lineItemData.put("ContractTerm", contractTerm);
			lineItemData.put("ProductName", productName);
			lineItemData.put("LineStatus", lineStatus);
			lineItemData.put("PricingStatus", pricingStatus);
			lineItemData.put("CouponCode", String.valueOf(couponCode));
			lineItemData.put("HasIncentives", String.valueOf(hasIncentives));

			Utility.logGenerator("getLineItemDetails", !approvalSegment.isEmpty(),
					"Captured Approval Segment from SFDC: <b>" + approvalSegment, "Approval Segment is Empty", false);
//			Utility.logGenerator("getLineItemDetails", !numberOfAttorney.isEmpty(),
//					"Captured Number Of Attorney from SFDC: <b>" + numberOfAttorney, "Number Of Attorney is Empty",
//					false);
			Utility.logGenerator("getLineItemDetails", !netPrice.isEmpty(),
					"Captured NetPrice from SFDC: <b>" + netPrice, "NetPrice is Empty", false);
			Utility.logGenerator("getLineItemDetails", !quantity.isEmpty(),
					"Captured Quantity from SFDC: <b>" + quantity, "Quantity is Empty", false);
			Utility.logGenerator("getLineItemDetails", !contractTerm.isEmpty(),
					"Captured Contract Term from SFDC: <b>" + contractTerm, "Contract Term is Empty", false);
			Utility.logGenerator("getLineItemDetails", !productName.isEmpty(),
					"Captured Product Name from SFDC: <b>" + productName, "Product Name is Empty", false);
			Utility.logGenerator("getLineItemDetails", !lineStatus.isEmpty(),
					"Captured Line Status from SFDC: <b>" + lineStatus, "Line Status is Empty", false);
			Utility.logGenerator("getLineItemDetails", !pricingStatus.isEmpty(),
					"Captured Pricing Status from SFDC: <b>" + pricingStatus, "Pricing Status is Empty", false);
			Utility.logGenerator("getLineItemDetails", true, "Is Has Incentives checked in SFDC: <b>" + hasIncentives,
					"Has Incentives cannot be fetched", false);
			Utility.logGenerator("getLineItemDetails", true, "Is Coupon Code Present in SFDC: <b>" + couponCode,
					"Has Coupon Code cannot be fetched", false);
			lineItemDetails.put(productName, lineItemData);
		}
		Utility.logGenerator("getLineItemDetails", !lineItemDetails.isEmpty(),
				"Captured Line Item Details from SFDC: <b>" + lineItemDetails, "Line Item Details cannot be fetched",
				false);
		return this;
	}
}