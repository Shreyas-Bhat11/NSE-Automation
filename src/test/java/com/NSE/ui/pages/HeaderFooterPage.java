package com.NSE.ui.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import com.tr.commons.ReadJson;
import com.tr.commons.extentListeners.ExtentLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;

public class HeaderFooterPage extends DcpBasePage {

	/**
	 * JSON file name used for validation
	 */
	static String headerFooterDataFileName = "HeaderFooterData.json";

	public HeaderFooterPage() {
		super("locatorsDefinition/HeaderFooterPage.json");
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method is used to close the cookies popup in the PP page
	 * 
	 * modified by Ishwarya
	 */
	public HeaderFooterPage closeCookie() {

		waitForPageLoad();
		if (isListEmpty("SectorDropdown")) {
			refreshPage();
		}
		waitUntilElementVisible("CloseCookies");
		clickOnElement("CloseCookies");
		waitUntilElementNotVisible("CloseCookies");
		waitForFixedTime(2);
		Utility.logGenerator("closeCookie", !checkElementIfVisible("CloseCookies"), "Clicked on Cookie Close",
				"Cookie popup is not closed", true);
		return this;
	}

	/**
	 * This method is used to fetch and validate the header data
	 * 
	 * @created by Ishwarya
	 */
	public HeaderFooterPage fetchAndValidateHeaderData() {

		ExtentLogger.info("Validating the Header Data");
		waitUntilElementVisible("HeaderLogo");
		Utility.validateUIDataField("fetchAndValidateHeaderData", "IsHeaderLogoPresent", !isListEmpty("HeaderLogo"),
				true, "UI", true, "Equals");
		String headerNavigations = "";
		for (int i = 1; i <= getSizeOfElement("HeaderNavigationsCount"); i++) {
			headerNavigations = headerNavigations
					+ getTextOfParameterisedXpathElement("HeaderNavigations", "PLACEHOLDER", String.valueOf(i)) + ", ";
		}
		headerNavigations = headerNavigations.substring(0, headerNavigations.length() - 2);
		Utility.validateUIDataField("fetchAndValidateHeaderData", "HeaderNavigations", headerNavigations,
				ReadJson.getStringValue(headerFooterDataFileName, "HEADER_NAVIGATIONS"), "UI", true, "Equals");
//		Utility.validateUIDataField("fetchAndValidateHeaderData", "IsHeaderCartIconPresent",
//				!isListEmpty("HeaderCartIcon"), true, "UI", true, "Equals");
//		Utility.validateUIDataField("fetchAndValidateHeaderData", "IsHeaderSearchIconPresent",
//				!isListEmpty("HeaderSearchIcon"), true, "UI", true, "Equals");
//		Utility.validateUIDataField("fetchAndValidateHeaderData", "IsHeaderAppsIconPresent",
//				!isListEmpty("HeaderAppsIcon"), true, "UI", true, "Equals");
////		Utility.validateUIDataField("fetchAndValidateHeaderData", "IsHeaderUserIconPresent",
////				!isListEmpty("HeaderUserIcon"), true, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to fetch and validate the footer data
	 * 
	 * @created by Ishwarya
	 */
	public HeaderFooterPage fetchAndValidateFooterData() {

		ExtentLogger.info("Validating the Footer Data");
		waitUntilElementVisible("FooterLogo");
		Utility.validateUIDataField("fetchAndValidateHeaderFooterData", "IsFooterLogoPresent",
				!isListEmpty("FooterLogo"), true, "UI", true, "Equals");
		String footerNavigations = "";
		for (int i = 1; i <= getSizeOfElement("GlobalFooterItemsCount"); i++) {
			footerNavigations = footerNavigations
					+ getTextOfParameterisedXpathElement("GlobalFooterItems", "PLACEHOLDER", String.valueOf(i)) + ", ";
		}
		footerNavigations = footerNavigations.substring(0, footerNavigations.length() - 2);
		Utility.validateUIDataField("fetchAndValidateHeaderFooterData", "FooterItems", footerNavigations,
				ReadJson.getStringValue(headerFooterDataFileName, "FOOTER_ITEMS"), "UI", true, "Equals");

		return this;
	}

	/**
	 * This method is used to validate the Books link in the header for canada site
	 * 
	 * @created by Ishwarya
	 */
	public HeaderFooterPage validateBooksLinkInHeader() {

		ExtentLogger.info("Validating the Books Link in Header");
		waitUntilElementVisible("BooksLink");
		clickOnElement("BooksLink");
		waitUntilElementVisible("BooksLinkMenuHeaderCount");
		int booksLinkHeaderCount = getSizeOfElement("BooksLinkMenuHeaderCount");
		Utility.validateUIDataField("validateBooksLinkInHeader", "BooksLinkHeaderCount",
				String.valueOf(booksLinkHeaderCount),
				ReadJson.getStringValue(headerFooterDataFileName, "BOOKS_LINK_HEADER_COUNT"), "UI", true, "Equals");

		String[] booksLinkHeaders = new String[] { "BOOK_LINK_MENU_HEADER_1", "BOOK_LINK_MENU_HEADER_2" };
		for (int i = 0; i < booksLinkHeaders.length; i++) {
			validateOption(i + 1, booksLinkHeaders[i], "BooksLinkMenuHeader", "validateBooksLinkInHeader");
		}

		String[] BooksLinkMenuSectionsHeadings = new String[] { "BOOK_LINK_MENU_SECTION_HEADER_1",
				"BOOK_LINK_MENU_SECTION_HEADER_2" };
		for (int i = 0; i < BooksLinkMenuSectionsHeadings.length - 1; i++) {
			validateOption(i + 1, BooksLinkMenuSectionsHeadings[i], "BooksLinkMenuSectionsHeading",
					"validateBooksLinkInHeader");
		}

		String[] BooksLinkMenuFirstSections = new String[] { "BOOK_LINK_MENU_FIRST_SECTION_ITEM_1",
				"BOOK_LINK_MENU_FIRST_SECTION_ITEM_2", "BOOK_LINK_MENU_FIRST_SECTION_ITEM_3" };
		for (int i = 0; i < BooksLinkMenuFirstSections.length; i++) {
			validateOption(i + 1, BooksLinkMenuFirstSections[i], "BooksLinkMenuFirstSectionsItems",
					"validateBooksLinkInHeader");
		}

		String[] BooksLinkMenuSecondSections = new String[] { "BOOK_LINK_MENU_SECOND_SECTION_ITEM_1",
				"BOOK_LINK_MENU_SECOND_SECTION_ITEM_2", "BOOK_LINK_MENU_SECOND_SECTION_ITEM_3" };
		for (int i = 0; i < BooksLinkMenuSecondSections.length; i++) {
			validateOption(i + 1, BooksLinkMenuSecondSections[i], "BooksLinkMenuSecondSectionsItems",
					"validateBooksLinkInHeader");
		}

		String[] BooksLinkMenuThirdSections = new String[] { "BOOK_LINK_MENU_THIRD_SECTION_ITEM_1",
				"BOOK_LINK_MENU_THIRD_SECTION_ITEM_2", "BOOK_LINK_MENU_THIRD_SECTION_ITEM_3",
				"BOOK_LINK_MENU_THIRD_SECTION_ITEM_4", "BOOK_LINK_MENU_THIRD_SECTION_ITEM_5",
				"BOOK_LINK_MENU_THIRD_SECTION_ITEM_6" };
		for (int i = 0; i < BooksLinkMenuThirdSections.length; i++) {
			validateOption(i + 1, BooksLinkMenuThirdSections[i], "BooksLinkMenuThirdSectionsItems",
					"validateBooksLinkInHeader");
		}
		// Links validation for first section of the books list menu
		String booksLinkMenuFirstSectionsLinks = "";
		List<String> booksLinkMenuFirstSectionsLinksList = new ArrayList<>();
		for (int i = 0; i < BooksLinkMenuFirstSections.length; i++) {
			String currentURL = clickLinkAndGetUrlOfNewTab("BooksLinkMenuFirstSectionsItems", "PLACEHOLDER",
					String.valueOf(i + 1));
			booksLinkMenuFirstSectionsLinksList.add(currentURL);
			booksLinkMenuFirstSectionsLinks = booksLinkMenuFirstSectionsLinks + currentURL + ", ";
		}
		Collections.sort(booksLinkMenuFirstSectionsLinksList);
		booksLinkMenuFirstSectionsLinks = booksLinkMenuFirstSectionsLinks.substring(0,
				booksLinkMenuFirstSectionsLinks.length() - 2);
//		Utility.validateUIDataField("validateBooksLinkInHeader", "BooksMenuFirstSectionsLinksList",
//				booksLinkMenuFirstSectionsLinksList.toString(),
//				ReadJson.getStringValue(headerFooterDataFileName, "BOOK_LINK_MENU_FIRST_SECTION_LINKS"), "UI", true,
//				"Equals");
		Utility.validateUIDataField("validateBooksLinkInHeader", "BooksMenuFirstSectionsLinks",
				booksLinkMenuFirstSectionsLinksList.toString(),
				ReadJson.getStringValue(headerFooterDataFileName, "BOOK_LINK_MENU_FIRST_SECTION_LINKS"), "UI", true,
				"Equals");
		// Links validation for second section of the books list menu
		String booksLinkMenuSecondSectionsLinks = "";
		for (int i = 0; i < BooksLinkMenuSecondSections.length; i++) {
			String currentURL = clickLinkAndGetUrlOfNewTab("BooksLinkMenuSecondSectionsItems", "PLACEHOLDER",
					String.valueOf(i + 1));
			booksLinkMenuSecondSectionsLinks = booksLinkMenuSecondSectionsLinks + currentURL + ", ";
		}
		booksLinkMenuSecondSectionsLinks = booksLinkMenuSecondSectionsLinks.substring(0,
				booksLinkMenuSecondSectionsLinks.length() - 2);
		Utility.validateUIDataField("validateBooksLinkInHeader", "BooksMenuSecondSectionsLinks",
				booksLinkMenuSecondSectionsLinks,
				ReadJson.getStringValue(headerFooterDataFileName, "BOOK_LINK_MENU_SECOND_SECTION_LINKS"), "UI", true,
				"Equals");
		// Links validation for third section of the books list menu
		String booksLinkMenuThirdSectionsLinks = "";
		for (int i = 0; i < BooksLinkMenuThirdSections.length; i++) {
			String currentURL = clickLinkAndGetUrlOfNewTab("BooksLinkMenuThirdSectionsItems", "PLACEHOLDER",
					String.valueOf(i + 1));
			booksLinkMenuThirdSectionsLinks = booksLinkMenuThirdSectionsLinks + currentURL + ", ";
		}
		booksLinkMenuThirdSectionsLinks = booksLinkMenuThirdSectionsLinks.substring(0,
				booksLinkMenuThirdSectionsLinks.length() - 2);
		Utility.validateUIDataField("validateBooksLinkInHeader", "BooksMenuThirdSectionsLinks",
				booksLinkMenuThirdSectionsLinks,
				ReadJson.getStringValue(headerFooterDataFileName, "BOOK_LINK_MENU_THIRD_SECTION_LINKS"), "UI", true,
				"Equals");

		return this;
	}

	/**
	 * This method is a helper method of validateBooksLinkInHeader() to validate the
	 * books links
	 * 
	 * @param index, optionKey
	 * @created by Ishwarya
	 */
	private void validateOption(int index, String optionKey, String validationName, String methodName) {
		Utility.validateUIDataField(methodName, validationName + " Option " + index,
				getTextOfParameterisedXpathElement(validationName, "PLACEHOLDER", String.valueOf(index)).trim(),
				ReadJson.getStringValue(headerFooterDataFileName, optionKey), "UI", true, "equals");
	}

	/**
	 * This method is used to fetch and validate the footer data
	 * 
	 * @created by Ishwarya
	 */
	public HeaderFooterPage validateCanadaSiteFooterData() {

		ExtentLogger.info("Validating the Footer Data");
		scrollToParticularElement("FooterLogo");
		Utility.validateUIDataField("validateCanadaSiteFooterData", "IsFooterLogoPresent", !isListEmpty("FooterLogo"),
				true, "UI", true, "Equals");
		String footerNavigations = "";
		String footerNavigationsLinks = "";
		for (int i = 1; i <= getSizeOfElement("GlobalFooterItemsCount"); i++) {
			footerNavigations = footerNavigations
					+ getTextOfParameterisedXpathElement("GlobalFooterItems", "PLACEHOLDER", String.valueOf(i)) + ", ";
			clickOnTheParameterisedXpath("GlobalFooterItems", "PLACEHOLDER", String.valueOf(i));
			footerNavigationsLinks = footerNavigationsLinks + getUrlOfNewTab() + ", ";
		}
		footerNavigations = footerNavigations.substring(0, footerNavigations.length() - 2);
		footerNavigationsLinks = footerNavigationsLinks.substring(0, footerNavigationsLinks.length() - 2);
		Utility.validateUIDataField("fetchAndValidateHeaderFooterData", "FooterItems", footerNavigations,
				ReadJson.getStringValue(headerFooterDataFileName, "FOOTER_ITEMS_CANADA"), "UI", true, "Equals");
		Utility.validateUIDataField("fetchAndValidateHeaderFooterData", "FooterNavigationsLinks",
				footerNavigationsLinks, ReadJson.getStringValue(headerFooterDataFileName, "FOOTER_ITEMS_LINKS_CANADA"),
				"UI", true, "Equals");

		String[] footerMenuHeading = new String[] { "FOOTER_MENU_HEADING_1", "FOOTER_MENU_HEADING_2",
				"FOOTER_MENU_HEADING_3", "FOOTER_MENU_HEADING_4" };
		for (int i = 0; i < footerMenuHeading.length; i++) {
			validateOption(i + 1, footerMenuHeading[i], "FooterMenuHeader", "validateCanadaSiteFooterData");
		}

		String[] footerMenu1Items = new String[] { "FOOTER_MENU_FIRST_SECTION_ITEM_1",
				"FOOTER_MENU_FIRST_SECTION_ITEM_2", "FOOTER_MENU_FIRST_SECTION_ITEM_3",
				"FOOTER_MENU_FIRST_SECTION_ITEM_4", "FOOTER_MENU_FIRST_SECTION_ITEM_5",
				"FOOTER_MENU_FIRST_SECTION_ITEM_6" };
		for (int i = 0; i < footerMenu1Items.length; i++) {
			validateOption(i + 1, footerMenu1Items[i], "FooterMenuFirstList", "validateCanadaSiteFooterData");
		}

		String[] footerMenu2Items = new String[] { "FOOTER_MENU_SECOND_SECTION_ITEM_1",
				"FOOTER_MENU_SECOND_SECTION_ITEM_2", "FOOTER_MENU_SECOND_SECTION_ITEM_3",
				"FOOTER_MENU_SECOND_SECTION_ITEM_4", "FOOTER_MENU_SECOND_SECTION_ITEM_5",
				"FOOTER_MENU_SECOND_SECTION_ITEM_6", "FOOTER_MENU_SECOND_SECTION_ITEM_7",
				"FOOTER_MENU_SECOND_SECTION_ITEM_8" };
		for (int i = 0; i < footerMenu2Items.length; i++) {
			validateOption(i + 1, footerMenu2Items[i], "FooterMenuSecondList", "validateCanadaSiteFooterData");
		}

		String[] footerMenu3Items = new String[] { "FOOTER_MENU_THIRD_SECTION_ITEM_1",
				"FOOTER_MENU_THIRD_SECTION_ITEM_2", "FOOTER_MENU_THIRD_SECTION_ITEM_3",
				"FOOTER_MENU_THIRD_SECTION_ITEM_4" };
		for (int i = 0; i < footerMenu3Items.length; i++) {
			validateOption(i + 1, footerMenu3Items[i], "FooterMenuThirdList", "validateCanadaSiteFooterData");
		}

		String[] footerMenu4Items = new String[] { "FOOTER_MENU_FORTH_SECTION_ITEM_1",
				"FOOTER_MENU_FORTH_SECTION_ITEM_2", "FOOTER_MENU_FORTH_SECTION_ITEM_3",
				"FOOTER_MENU_FORTH_SECTION_ITEM_4", "FOOTER_MENU_FORTH_SECTION_ITEM_5",
				"FOOTER_MENU_FORTH_SECTION_ITEM_6" };
		for (int i = 0; i < footerMenu4Items.length; i++) {
			validateOption(i + 1, footerMenu4Items[i], "FooterMenuForthList", "validateCanadaSiteFooterData");
		}

		for (int i = 1; i <= getSizeOfElement("FooterMenuForthListIconsCounts"); i++) {

			Utility.validateUIDataField("validateCanadaSiteFooterData", "FooterMenuForthListIcons" + i,
					!isParameterisedListEmpty("FooterMenuForthListIcons", "PLACEHOLDER", String.valueOf(i)), true, "UI",
					true, "Equals");
		}

		// Links validation for footer menu 1 links
		String footerMenu1ListOfLinks = "";
		for (int i = 0; i < footerMenu1Items.length; i++) {
			String currentURL = clickLinkAndGetUrlOfNewTab("FooterMenuFirstList", "PLACEHOLDER", String.valueOf(i + 1));
			footerMenu1ListOfLinks = footerMenu1ListOfLinks + currentURL + ", ";
		}
		footerMenu1ListOfLinks = footerMenu1ListOfLinks.substring(0, footerMenu1ListOfLinks.length() - 2);
		Utility.validateUIDataField("validateCanadaSiteFooterData", "FooterMenuFirstListLinks", footerMenu1ListOfLinks,
				ReadJson.getStringValue(headerFooterDataFileName, "FOOTER_MENU_FIRST_SECTION_ITEM_LINKS"), "UI", true,
				"Equals");
		// Links validation for footer menu 2 links
		String footerMenu2ListOfLinks = "";
		for (int i = 0; i < footerMenu2Items.length; i++) {
			String currentURL = clickLinkAndGetUrlOfNewTab("FooterMenuSecondList", "PLACEHOLDER",
					String.valueOf(i + 1));
			footerMenu2ListOfLinks = footerMenu2ListOfLinks + currentURL + ", ";
		}
		footerMenu2ListOfLinks = footerMenu2ListOfLinks.substring(0, footerMenu2ListOfLinks.length() - 2);
		Utility.validateUIDataField("validateCanadaSiteFooterData", "FooterMenuSecondListLinks", footerMenu2ListOfLinks,
				ReadJson.getStringValue(headerFooterDataFileName, "FOOTER_MENU_SECOND_SECTION_ITEM_LINKS"), "UI", true,
				"Equals");
		// Links validation for footer menu 3 links
		String footerMenu3ListOfLinks = "";
		for (int i = 0; i < footerMenu3Items.length; i++) {
			String currentURL = clickLinkAndGetUrlOfNewTab("FooterMenuThirdList", "PLACEHOLDER", String.valueOf(i + 1));
			footerMenu3ListOfLinks = footerMenu3ListOfLinks + currentURL + ", ";
		}
		footerMenu3ListOfLinks = footerMenu3ListOfLinks.substring(0, footerMenu3ListOfLinks.length() - 2);
		Utility.validateUIDataField("validateCanadaSiteFooterData", "FooterMenuThirdListLinks", footerMenu3ListOfLinks,
				ReadJson.getStringValue(headerFooterDataFileName, "FOOTER_MENU_THIRD_SECTION_ITEM_LINKS"), "UI", true,
				"Equals");
		// Links validation for footer menu 3 links
		String footerMenu4ListOfLinks = "";
		for (int i = 0; i < footerMenu4Items.length; i++) {
			String currentURL = clickLinkAndGetUrlOfNewTab("FooterMenuForthList", "PLACEHOLDER", String.valueOf(i + 1));
			footerMenu4ListOfLinks = footerMenu4ListOfLinks + currentURL + ", ";
		}
		footerMenu4ListOfLinks = footerMenu4ListOfLinks.substring(0, footerMenu4ListOfLinks.length() - 2);
		Utility.validateUIDataField("validateCanadaSiteFooterData", "FooterMenuForthListLinks", footerMenu4ListOfLinks,
				ReadJson.getStringValue(headerFooterDataFileName, "FOOTER_MENU_FORTH_SECTION_ITEM_LINKS"), "UI", true,
				"Equals");

		return this;
	}
}