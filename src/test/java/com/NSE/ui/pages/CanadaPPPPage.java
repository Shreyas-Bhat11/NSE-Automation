package com.NSE.ui.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import com.tr.commons.ReadJson;
import com.tr.commons.extentListeners.ExtentLogger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;

public class CanadaPPPPage extends DcpBasePage {

	public static String randomProductSelected = "", productPrice = "", productDescription = "", productDate = "",
			publisher = "", productFormat = "", productAuthor = "";

	/**
	 * JSON file name used for validation
	 */
	static String ppPageDataFileName = "CanadaPPPPageData.json";

	public CanadaPPPPage() {
		super("locatorsDefinition/CanadaPPPPage.json");
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * This method click on random product in the first page of search results
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaPPPPage clickOnRandomProduct() {

		ExtentLogger.info(
				"Clicking on the random product in the first page of search results and fetching product details");
		int size = getSizeOfElement("SearchedProductHeaderListCount");
		int random = Utility.generateNumberFromRange(1, size);
		randomProductSelected = getTextOfParameterisedXpathElement("SearchedProductHeader", "PLACEHOLDER",
				String.valueOf(random));
		if (isParameterisedListEmpty("SearchedProductStartingPrice", "PLACEHOLDER", randomProductSelected)) {
			productPrice = "0.00";
		} else {
			productPrice = getTextOfParameterisedXpathElement("SearchedProductStartingPrice", "PLACEHOLDER",
					randomProductSelected);
		}
		productDescription = getTextOfParameterisedXpathElement("SearchedProductDescription", "PLACEHOLDER",
				String.valueOf(random));
		if (isParameterisedListEmpty("SearchedProductDate", "PLACEHOLDER", randomProductSelected)) {
			productDate = "";
		} else {
			productDate = getTextOfParameterisedXpathElement("SearchedProductDate", "PLACEHOLDER",
					randomProductSelected);
		}
		ExtentLogger.info("Selected the product: " + randomProductSelected);
		ExtentLogger.info("Product Price: " + productPrice);
		ExtentLogger.info("Product Description: " + productDescription);
		ExtentLogger.info("Product Date: " + productDate);
		if (!isParameterisedListEmpty("SearchedProductPublisher", "PLACEHOLDER", String.valueOf(random))) {
			publisher = getTextOfParameterisedXpathElement("SearchedProductPublisher", "PLACEHOLDER",
					String.valueOf(random));
			ExtentLogger.info("Product Publisher: " + publisher);
		}
		if (!isParameterisedListEmpty("SearchedProductAuthor", "PLACEHOLDER", String.valueOf(random))) {
			productAuthor = getTextOfParameterisedXpathElement("SearchedProductAuthor", "PLACEHOLDER",
					String.valueOf(random));
			ExtentLogger.info("Product Author: " + productAuthor);
		}
		clickOnTheParameterisedXpath("SearchedProductHeader", "PLACEHOLDER", String.valueOf(random));
		waitForPageLoad();
//		PRODUCT NAME IN URL IS NOT DISPLAYED CORRECTLY, FE TEAM NEEDS TO CONFIRM
//		Utility.validateUIDataField("validateFilterSection", "IsProductNameDisplayedInURL", getURL(),
//				randomProductSelected.replaceAll("[^a-zA-Z0-9']", "-").toLowerCase().replace("--", "-"), "UI_UI", true,
//				"Contains", "ProductURL", "TitleOfProduct");

		return this;
	}

	/**
	 * This method click on random product in the first page of search results
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaPPPPage openAvailableProduct() {

		ExtentLogger.info(
				"Clicking on the random product in the first page of search results and fetching product details");
		int size = getSizeOfElement("SearchedProductHeaderListCount");
		for (int i = 1; i <= size; i++) {
			randomProductSelected = getTextOfParameterisedXpathElement("SearchedProductHeader", "PLACEHOLDER",
					String.valueOf(i));
			if (isParameterisedListEmpty("SearchedProductStartingPrice", "PLACEHOLDER", randomProductSelected)) {
				productPrice = "0.00";
			} else {
				productPrice = getTextOfParameterisedXpathElement("SearchedProductStartingPrice", "PLACEHOLDER",
						randomProductSelected);
			}
			productDescription = getTextOfParameterisedXpathElement("SearchedProductDescription", "PLACEHOLDER",
					String.valueOf(i));
			if (isParameterisedListEmpty("SearchedProductDate", "PLACEHOLDER", randomProductSelected)) {
				productDate = "";
			} else {
				productDate = getTextOfParameterisedXpathElement("SearchedProductDate", "PLACEHOLDER",
						randomProductSelected);
			}
			ExtentLogger.info("Selected the product: " + randomProductSelected);
			ExtentLogger.info("Product Price: " + productPrice);
			ExtentLogger.info("Product Description: " + productDescription);
			ExtentLogger.info("Product Date: " + productDate);
			if (!isParameterisedListEmpty("SearchedProductPublisher", "PLACEHOLDER", String.valueOf(i))) {
				publisher = getTextOfParameterisedXpathElement("SearchedProductPublisher", "PLACEHOLDER",
						String.valueOf(i));
				ExtentLogger.info("Product Publisher: " + publisher);
			}
			if (!isParameterisedListEmpty("SearchedProductAuthor", "PLACEHOLDER", String.valueOf(i))) {
				productAuthor = getTextOfParameterisedXpathElement("SearchedProductAuthor", "PLACEHOLDER",
						String.valueOf(i));
				ExtentLogger.info("Product Author: " + productAuthor);
			}
			clickOnTheParameterisedXpath("SearchedProductHeader", "PLACEHOLDER", String.valueOf(i));
			waitForPageLoad();
			waitForFixedTime(2);
			if (isListEmpty("ProductUnavailableErrorMessage") == true) {
				ExtentLogger.info("Product " + randomProductSelected + " is available");
				return this;
			} else {
				ExtentLogger.info(
						"Product " + randomProductSelected + " is not available, navigating back to search results");
				navigateBack();
				waitForPageLoad();
			}
		}
//		PRODUCT NAME IN URL IS NOT DISPLAYED CORRECTLY, FE TEAM NEEDS TO CONFIRM
//		Utility.validateUIDataField("validateFilterSection", "IsProductNameDisplayedInURL", getURL(),
//				randomProductSelected.replaceAll("[^a-zA-Z0-9']", "-").toLowerCase().replace("--", "-"), "UI_UI", true,
//				"Contains", "ProductURL", "TitleOfProduct");

		return this;
	}

	/**
	 * This method is to search any word in the search bar
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaPPPPage enterInSearchBar(String searchWord) {

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
	 * This method is to validate the filter section in the PPP page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaPPPPage validateFilterSection(String language) {

		ExtentLogger.info("********** Validating the filter section in the PPP page **********");
		Utility.validateUIDataField("validateFilterSection", "SearchHeading", getTextOfElement("SearchHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "SEARCH_HEADING_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateFilterSection", "FilterHeading", getTextOfElement("FilterHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "FILTER_HEADING_" + language), "UI", true, "Contains");
		Utility.validateUIDataField("validateFilterSection", "FilterJurisdictionHeading",
				getTextOfElement("FilterJurisdictionHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateFilterSection", "FilterProductFormatHeading",
				getTextOfElement("FilterProductFormatHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "PRODUCT_FORMAT_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateFilterSection", "FilterPracticeAreaHeading",
				getTextOfElement("FilterPracticeAreaHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "PRACTICE_AREA_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateFilterSection", "FilterPurchaseOptionsHeading",
				getTextOfElement("FilterPurchaseOptionsHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "PURCHASE_OPTIONS_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateFilterSection", "FilterPublisherHeading",
				getTextOfElement("FilterPublisherHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "PUBLISHER_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateFilterSection", "FilterAuthorHeading",
				getTextOfElement("FilterAuthorHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "AUTHOR_" + language), "UI", true, "Equals");

		// validate expanding and collapsing of the juridiction section
		Utility.validateUIDataField("validateFilterSection", "FilterJurisdictionArrowCollapsed",
				getAttributeValueOfGivenAttribute("FilterJurisdictionArrow", "title"),
				ReadJson.getStringValue(ppPageDataFileName, "COLLAPSED_ARROW_TEXT_" + language), "UI", true,
				"Contains");
		clickOnElement("FilterJurisdictionArrow");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateFilterSection", "FilterJurisdictionArrowExpanded",
				getAttributeValueOfGivenAttribute("FilterJurisdictionArrow", "title"),
				ReadJson.getStringValue(ppPageDataFileName, "EXPANDED_ARROW_TEXT_" + language), "UI", true, "Contains");
		clickOnElement("FilterJurisdictionArrow");

		return this;
	}

	/**
	 * This method is to validate the sort section in the PPP page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public CanadaPPPPage validateSortSection(String language) {

		ExtentLogger.info("********** Validating the sort section in the PPP page **********");
		Utility.validateUIDataField("validateSortSection", "SortHeading", getTextOfElement("SortHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "SORT_HEADING_" + language), "UI", true, "Equals");
		Utility.validateUIDataField("validateSortSection", "SortDropDownArrowIspresent",
				!isListEmpty("SortDropDownArrow"), true, "UI", true, "Equals");

		clickOnElement("SortDropDownArrow");
		for (int sortOptions = 1; sortOptions <= getSizeOfElement("SortOptionCount"); sortOptions++) {
			Utility.validateUIDataField("validateSortSection", "SortOption" + sortOptions,
					getTextOfParameterisedXpathElement("SortOptions", "PLACEHOLDER", String.valueOf(sortOptions)),
					ReadJson.getStringValue(ppPageDataFileName, "SORT_DROPDOWN_LIST_" + sortOptions + "_" + language),
					"UI", true, "Equals");
		}
		return this;
	}

	/**
	 * This method is to select each drop down option in the sort by drop down and
	 * validate the product list
	 * 
	 * @created by Ishwarya
	 */
	public CanadaPPPPage selectSortByDropDownOptionAndValidateProductList(String language) {

		if (isListEmpty("SortDropDownArrowExpanded")) {
			clickOnElement("SortDropDownArrow");
		}
		for (int sortOptions = 1; sortOptions <= getSizeOfElement("SortOptionCount"); sortOptions++) {

			if (isListEmpty("SortDropDownArrowExpanded")) {
				clickOnElement("SortDropDownArrow");
			}
			String selectedOption = getTextOfParameterisedXpathElement("SortOptions", "PLACEHOLDER",
					String.valueOf(sortOptions));
			clickOnTheParameterisedXpath("SortOptions", "PLACEHOLDER", String.valueOf(sortOptions));
			waitForFixedTime(2);

			ExtentLogger.info("Selected the option: " + selectedOption);

			if (!selectedOption
					.contains(ReadJson.getStringValue(ppPageDataFileName, "SORT_DROPDOWN_LIST_1_" + language))) {
				ArrayList<String> productName = new ArrayList<>();
				ArrayList<String> productPrice = new ArrayList<>();
				ArrayList<String> productDate = new ArrayList<>();

				for (int i = 1; i <= getSizeOfElement("ProductCountInSearchResult"); i++) {
					productName.add(getTextOfParameterisedXpathElement("ProductTitleInSearchResult", "PLACEHOLDER",
							String.valueOf(i)));
					if (isParameterisedListEmpty("SearchedProductStartingPrice", "PLACEHOLDER",
							String.valueOf(i)) == false) {
						productPrice.add(getTextOfParameterisedXpathElement("SearchedProductStartingPrice",
								"PLACEHOLDER", String.valueOf(i)).replaceAll("[^0-9.]", ""));
					}
					if (isParameterisedListEmpty("ProductDateInSearchResult", "PLACEHOLDER",
							String.valueOf(i)) == false) {
						productDate.add(getTextOfParameterisedXpathElement("ProductDateInSearchResult", "PLACEHOLDER",
								String.valueOf(i)));
					}
				}

				if (selectedOption
						.contains(ReadJson.getStringValue(ppPageDataFileName, "SORT_DROPDOWN_LIST_4_" + language))) {
					Utility.validateUIDataField("selectSortByDropDownOptionAndValidateProductList",
							"IsProductListSortedInAscendingOrder", isListAscendingText(productName), true, "UI", true,
							"Equals");
				} else if (selectedOption
						.contains(ReadJson.getStringValue(ppPageDataFileName, "SORT_DROPDOWN_LIST_5_" + language))) {
					Utility.validateUIDataField("selectSortByDropDownOptionAndValidateProductList",
							"IsProductListSortedInDescendingOrder", isListAscendingText(productName) == false, true,
							"UI", true, "Equals");
				} else if (selectedOption
						.contains(ReadJson.getStringValue(ppPageDataFileName, "SORT_DROPDOWN_LIST_6_" + language))) {
					Utility.validateUIDataField("selectSortByDropDownOptionAndValidateProductList",
							"IsProductListSortedInDecendingOrderByPrice",
							isListAscendingNumerical(productPrice) == false, true, "UI", true, "Equals");
				} else if (selectedOption
						.contains(ReadJson.getStringValue(ppPageDataFileName, "SORT_DROPDOWN_LIST_7_" + language))) {
					Utility.validateUIDataField("selectSortByDropDownOptionAndValidateProductList",
							"IsProductListSortedInAscendingOrderByPrice", isListAscendingNumerical(productPrice), true,
							"UI", true, "Equals");
				} else if (selectedOption
						.contains(ReadJson.getStringValue(ppPageDataFileName, "SORT_DROPDOWN_LIST_3_" + language))) {
					Utility.validateUIDataField("selectSortByDropDownOptionAndValidateProductList",
							"IsProductListSortedInAscendingOrderByDate", isListAscendingDate(productDate), true, "UI",
							true, "Equals");
				} else if (selectedOption
						.contains(ReadJson.getStringValue(ppPageDataFileName, "SORT_DROPDOWN_LIST_2_" + language))) {
					Utility.validateUIDataField("selectSortByDropDownOptionAndValidateProductList",
							"IsProductListSortedInAscendingOrderByDate", isListAscendingDate(productDate) == false,
							true, "UI", true, "Equals");
				}
			}
		}
		return this;

	}

	private boolean isListAscendingDate(ArrayList<String> productDate) {
		for (int i = 0; i < productDate.size() - 1; i++) {
			String date1 = productDate.get(i);
			String date2 = productDate.get(i + 1);
			if (date1.compareTo(date2) > 0) {
				return false;
			}
		}
		return true;
	}

	private boolean isListAscendingNumerical(ArrayList<String> productPrice) {
		for (int i = 0; i < productPrice.size() - 1; i++) {
			double num1 = Double.parseDouble(productPrice.get(i));
			double num2 = Double.parseDouble(productPrice.get(i + 1));

			if (num1 > num2) {
				return false;
			}
		}
		return true;
	}

	private boolean isListAscendingText(ArrayList<String> productName) {
		for (int i = 0; i < productName.size() - 1; i++) {
			if (productName.get(i).compareTo(productName.get(i + 1)) > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method is to validate the product details is present in the PPP page
	 * 
	 * @created by Ishwarya
	 */
	public CanadaPPPPage validateProductDetailsIsPresent(String language) {

		ExtentLogger.info("********** Validating the product details in the PPP page **********");
		Utility.validateUIDataField("validateFirstProductDetails", "IsBookImagePresentForFirstProduct",
				!isListEmpty("SortDropDownArrow"), true, "UI", true, "Equals");

		for (int i = 1; i <= getSizeOfElement("ProductCountInSearchResult"); i++) {
			Utility.validateUIDataField("validateFirstProductDetails",
					"IsBookImagePresentForProduct_" + i + "_" + language,
					!isParameterisedListEmpty("ProductImagesInSearchResult", "PLACEHOLDER", String.valueOf(i)), true,
					"UI", true, "Equals");
			Utility.validateUIDataField("validateFirstProductDetails",
					"IsBookTitlePresentForProduct_" + i + "_" + language,
					!isParameterisedListEmpty("ProductTitleInSearchResult", "PLACEHOLDER", String.valueOf(i)), true,
					"UI", true, "Equals");
			Utility.validateUIDataField("validateFirstProductDetails",
					"IsBookDatePresentForProduct_" + i + "_" + language,
					!isParameterisedListEmpty("ProductDateInSearchResult", "PLACEHOLDER", String.valueOf(i)), true,
					"UI", true, "Equals");
			Utility.validateUIDataField("validateFirstProductDetails",
					"IsBookDescriptionPresentForProduct_" + i + "_" + language,
					!isParameterisedListEmpty("ProductDescriptionInSearchResult", "PLACEHOLDER", String.valueOf(i)),
					true, "UI", true, "Equals");
			Utility.validateUIDataField("validateFirstProductDetails",
					"IsBookFormatHeadingPresentForProduct_" + i + "_" + language,
					!isParameterisedListEmpty("ProductFormatHeadingInSearchResult", "PLACEHOLDER", String.valueOf(i)),
					true, "UI", true, "Equals");
			Utility.validateUIDataField("validateFirstProductDetails",
					"IsBookFormatValuePresentForProduct_" + i + "_" + language,
					!isParameterisedListEmpty("ProductFormatValueInSearchResult", "PLACEHOLDER", String.valueOf(i)),
					true, "UI", true, "Equals");
		}
		return this;
	}

	/**
	 * This method is to validate the product details is present in the PPP page
	 * 
	 * @created by Ishwarya
	 */
	public CanadaPPPPage validatePaginationsIsPresent(String language) {

		ExtentLogger.info("********** Validating the pagination section in the PPP page **********");
		Utility.validateUIDataField("validatePaginationsIsPresent", "IsPaginationPresent", !isListEmpty("Pagination"),
				true, "UI", true, "Equals");
		Utility.validateUIDataField("validatePaginationsIsPresent", "IsPaginationNextButtonPresent",
				!isListEmpty("PaginationNextButton"), true, "UI", true, "Equals");
		Utility.validateUIDataField("validatePaginationsIsPresent", "PaginationResultsPerPageHeading",
				getTextOfElement("PaginationResultsPerPageHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "RESULTS_PER_PAGE_HEADING_" + language), "UI", true,
				"Contains");

		for (int i = 1; i < getSizeOfElement("PaginationProductsPerPage"); i++) {

			Utility.validateUIDataField("validatePaginationsIsPresent", "PaginationProductsPerPageOption" + i + "0",
					getTextOfParameterisedXpathElement("PaginationProductsPerPageCount", "PLACEHOLDER",
							String.valueOf(i)),
					ReadJson.getStringValue(ppPageDataFileName, "PAGES_" + i + "0_" + language), "UI", true, "Equals");
		}
		return this;
	}
}