package com.NSE.ui.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import com.tr.commons.ReadJson;
import com.tr.commons.extentListeners.ExtentLogger;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;

public class PPPage extends DcpBasePage {

	public static double totalPlanPrice = 0.00, planPrice = 0, addonPrice = 0;
	public static String totalPriceInString = "", addOnName = "", planPriceInString = "", planName = "", plan = "",
			planCategoryName = "", brandName = "", addonPriceInString = "", sector = "",
			randomSelectedjuridictionName = "";

	private int numberOfAddOns, jurisdictionDropdownCount = 0;
	/**
	 * JSON file name used for validation
	 */
	static String ppPageDataFileName = "PPPageData.json";

	public PPPage() {
		super("locatorsDefinition/PPPage.json");
		// totalPlanPrice = 0.00;
		planPrice = 0;
		addonPrice = 0;
		// totalPriceInString = "";
		addOnName = "";
		planPriceInString = "";
		planName = "";
		plan = "";
		planCategoryName = "";
		brandName = "";
		addonPriceInString = "";
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * This method is used to verify if the user has landed on the west Law edge
	 * page
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public PPPage verifyLandedOnWestLawEdgePage() {

		waitForPageLoad();
		// deleteCookies();
		ExtentLogger.info("West Law Edge Page Title is: " + get_Title());
		waitUntilElementVisible("WestlawEdgeHeader");
		Utility.validateUIDataField("verifyLandedOnWestLawEdgePage", "WestLawEdgePageTitle", get_Title(),
				ReadJson.getStringValue(ppPageDataFileName, "WESTEDGE_PAGE_HEADING"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to validate the Practical law banner content
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validatePLBanner() {

		ExtentLogger.info("Validating Practical Law Banner");
		waitUntilElementVisible("BannerHeading");
		Utility.validateUIDataField("validatePLBanner", "BannerHeading", getTextOfElement("BannerHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "PL_BANNER_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePLBanner", "BannerTitle", getTextOfElement("BannerTitle"),
				ReadJson.getStringValue(ppPageDataFileName, "PL_BANNER_TITLE"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePLBanner", "BannerDescription", getTextOfElement("BannerDescription"),
				ReadJson.getStringValue(ppPageDataFileName, "PL_BANNER_DESCRIPTION"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to validate the West law edge banner content
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateWLEdgeBanner() {

		ExtentLogger.info("Validating West Law Edge Banner");
		waitUntilElementVisible("BannerHeading");
		Utility.validateUIDataField("validateWLEdgeBanner", "BannerHeading", getTextOfElement("BannerHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "WL_EDGE_BANNER_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateWLEdgeBanner", "BannerTitle", getTextOfElement("BannerTitle"),
				ReadJson.getStringValue(ppPageDataFileName, "WL_EDGE_BANNER_TITLE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateWLEdgeBanner", "BannerDescription", getTextOfElement("BannerDescription"),
				ReadJson.getStringValue(ppPageDataFileName, "WL_EDGE_BANNER_DESCRIPTION"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to validate the West law classic banner content
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateWLClassicBanner() {

		ExtentLogger.info("Validating West Law Classic Banner");
		waitUntilElementVisible("BannerHeading");
		Utility.validateUIDataField("validateWLEdgeBanner", "BannerHeading", getTextOfElement("BannerHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "WL_CLASSIC_BANNER_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateWLEdgeBanner", "BannerTitle", getTextOfElement("BannerTitle"),
				ReadJson.getStringValue(ppPageDataFileName, "WL_CLASSIC_BANNER_TITLE"), "UI", true, "Equals");
		Utility.validateUIDataField("validateWLEdgeBanner", "BannerDescription", getTextOfElement("BannerDescription"),
				ReadJson.getStringValue(ppPageDataFileName, "WL_CLASSIC_BANNER_DESCRIPTION"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to verify if the user has landed on the west Law classic
	 * from west Law edge page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateNavigationFromWLEdgeToClassic() {

		waitForPageLoad();
		deleteCookies();
		ExtentLogger.info("West Law Edge Page Title is: " + get_Title());
		scrollToParticularElement("WLEdgeOrClassicLink");
		Utility.validateUIDataField("validateNavigationFromWLEdgeToClassic", "WestLawClassicLink",
				getTextOfElement("WLEdgeOrClassicLink"), ReadJson.getStringValue(ppPageDataFileName, "WL_CLASSIC_LINK"),
				"UI", true, "Equals");
		String linkText = ReadJson.getStringValue(ppPageDataFileName, "WL_CLASSIC_LINK_TEXT").replaceFirst("(?s)\\..*",
				".");
		Utility.validateUIDataField("validateNavigationFromWLEdgeToClassic", "WestLawClassicLinkText",
				getTextOfElement("WLEdgeOrClassicLinkText"), linkText, "UI", true, "Contains");
		clickOnElement("WLEdgeOrClassicLink");
		waitForPageLoad();
		Utility.logGenerator("validateNavigationFromWLEdgeToClassic", getURL().contains("classic"),
				"WL Classic page is loaded", "WL Classic page is loaded", true);
		Utility.validateUIDataField("validateNavigationFromWLEdgeToClassic", "WestLawClassicPageTitle", get_Title(),
				ReadJson.getStringValue(ppPageDataFileName, "WESTCLASSIC_PAGE_HEADING"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to verify if the user has landed on the west law edge
	 * from west Law classic page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateNavigationFromWLClassicToEdge() {

		waitForPageLoad();
		deleteCookies();
		ExtentLogger.info("West Law Classic Page Title is: " + get_Title());
		scrollToParticularElement("WLEdgeOrClassicLink");
		Utility.validateUIDataField("validateNavigationFromWLClassicToEdge", "WestLawEdgeLink",
				getTextOfElement("WLEdgeOrClassicLink"), ReadJson.getStringValue(ppPageDataFileName, "WL_EDGE_LINK"),
				"UI", true, "Equals");
		String linkText = ReadJson.getStringValue(ppPageDataFileName, "WL_EDGE_LINK_TEXT").replaceFirst("(?s)\\..*",
				".");
		Utility.validateUIDataField("validateNavigationFromWLClassicToEdge", "WestLawEdgeLinkText",
				getTextOfElement("WLEdgeOrClassicLinkText"), linkText, "UI", true, "Contains");
		clickOnElement("WLEdgeOrClassicLink");
		waitForPageLoad();
		Utility.logGenerator("validateNavigationFromWLClassicToEdge", getURL().contains("edge"),
				"WL Edge page is loaded", "WL Edge page is loaded", true);
		Utility.validateUIDataField("verifyLandedOnWestLawEdgePage", "WestLawEdgePageTitle", get_Title(),
				ReadJson.getStringValue(ppPageDataFileName, "WESTEDGE_PAGE_HEADING"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to verify if the user has landed on the Practical Law
	 * page
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage verifyLandedOnPracticalLawPage() {

		waitForPageLoad();
		deleteCookies();
		ExtentLogger.info("Practical Law Page Title is: " + get_Title());
		waitUntilElementVisible("PracticalLawHeader");
		Utility.validateUIDataField("verifyLandedOnPracticalLawPage", "PracticalLawPageTitle", get_Title(),
				ReadJson.getStringValue(ppPageDataFileName, "PRACTICALLAW_PAGE_HEADING"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to verify if the user has landed on the west Law edge
	 * page
	 * 
	 * @return
	 * @created by Yunus
	 * @modified by Ishwarya
	 */
	public PPPage verifyLandedOnWestLawClassicPage() {

		waitForPageLoad();
		ExtentLogger.info("West Law Classic Page Title is: " + get_Title());
		waitUntilElementVisible("WestlawClassicHeader");
		Utility.validateUIDataField("verifyLandedOnWestLawEdgePage", "WestLawClassicPageTitle", get_Title(),
				ReadJson.getStringValue(ppPageDataFileName, "WESTCLASSIC_PAGE_HEADING"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to select the sector from dropdown based on the sector
	 * parameter passed
	 * 
	 * @return
	 * @created by Ishwarya
	 */

	public PPPage selectSector(String sector) {

		waitUntilElementVisible("SectorDropdown");
		clickOnElement("SectorDropdown");
		Utility.logGenerator("selectSector", !isParameterisedListEmpty("SectorDropdownOption", "PLACEHOLDER", sector),
				"Clicked on Sector Drop Down",
				"Can't clicked on Sector Drop Down button or Sector Drop Down button is not displayed", true);
		clickOnTheParameterisedXpath("SectorDropdownOption", "PLACEHOLDER", sector);
		ExtentLogger.info("Selected Sector: " + sector);
		PPPage.sector = sector;
		waitForFixedTime(4);
		return this;
	}

	/**
	 * This method is used to validate the error message for General counsel sector
	 * parameter passed
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage errorMessageValidateAttorneyForGCSector(){
		waitUntilElementVisible("SectorDropdown");
		clickOnElement("SectorDropdown");
		Utility.logGenerator("selectSector", !isParameterisedListEmpty("SectorDropdownOption", "PLACEHOLDER", "Corporation"),
				"Clicked on Sector Drop Down",
				"Can't clicked on Sector Drop Down button or Sector Drop Down button is not displayed", true);
		clickOnTheParameterisedXpath("SectorDropdownOption", "PLACEHOLDER", "Corporation");
		ExtentLogger.info("Selected Sector: General Counsel");
		PPPage.sector = "General Counsel";
		waitForFixedTime(4);
		waitUntilElementVisible("FirstNumberOfSeatsTextBox");
		clickOnElement("FirstNumberOfSeatsTextBox");
		doubleClickOnElement("FirstNumberOfSeatsTextBox");
		waitForFixedTime(1);
		sendKeysTotheElement("FirstNumberOfSeatsTextBox", "6");
		waitForFixedTime(1);
		ExtentLogger.info("Entered Number of Seats: " + "6");
		clickOnElement("SectorHeading");
		Utility.validateUIDataField("errorMessageValidateAttorneyForGCSector", "ErrorMessageForNumberOfAttorneys",
				getTextOfElement("ErrorMessageForNumberOfAttorneys"), "ErrorMessage_GC", "UI", true, "Equals");

		return this;

	}

	/**
	 * This method is used to validate the error message for Government sector
	 * parameter passed
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage errorMessageValidateAttorneyForGovtSector(){
		waitUntilElementVisible("SectorDropdown");
		clickOnElement("SectorDropdown");
		Utility.logGenerator("selectSector", !isParameterisedListEmpty("SectorDropdownOption", "PLACEHOLDER", "Government"),
				"Clicked on Sector Drop Down",
				"Can't clicked on Sector Drop Down button or Sector Drop Down button is not displayed", true);
		clickOnTheParameterisedXpath("SectorDropdownOption", "PLACEHOLDER", "Government");
		ExtentLogger.info("Selected Sector: Government");
		PPPage.sector = "Government";
		waitForFixedTime(4);
		waitUntilElementVisible("FirstNumberOfAttorneysTextBox");
		clickOnElement("FirstNumberOfAttorneysTextBox");
		doubleClickOnElement("FirstNumberOfAttorneysTextBox");
		waitForFixedTime(1);
		sendKeysTotheElement("FirstNumberOfAttorneysTextBox", "6");
		waitForFixedTime(1);
		ExtentLogger.info("Entered Number of Seats: " + "6");
		clickOnElement("SectorHeading");
		waitUntilElementVisible("ErrorMessageForNumberOfAttorneys");
		Utility.validateUIDataField("errorMessageValidateAttorneyForGovtSector", "ErrorMessageForNumberOfAttorneys",
				getTextOfElement("ErrorMessageForNumberOfAttorneys"), "ErrorMessage_Govt", "UI", true, "Equals");

		return this;
	}

	/**
	 * This method is used to validate the sector selected
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateSector(String sector) {

		scrollToLocation(0, -400);
		Utility.validateUIDataField("validateSector", "SectorHeading", getTextOfElement("SectorHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "SECTOR_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateSector", "SelectedValueInSectorDropdown",
				getTextOfElement("SelectedValueInSectorDropdown"), sector, "UI", true, "Contains");
		return this;
	}

	/**
	 * This method is used to input the number of attorneys
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage inputNumberOfAttorneys(String numberOfAttorneys) {

		waitForFixedTime(2);
		waitUntilElementVisible("FirstNumberOfAttorneysTextBox");
		clickOnElement("FirstNumberOfAttorneysTextBox");
		doubleClickOnElement("FirstNumberOfAttorneysTextBox");
		waitForFixedTime(1);
		sendKeysTotheElement("FirstNumberOfAttorneysTextBox", numberOfAttorneys);
		waitForFixedTime(1);
		ExtentLogger.info("Entered Number of Seats: " + numberOfAttorneys);
		clickOnElement("SectorHeading");
		return this;
	}

	/**
	 * This method is for Error validation max attorney for law firm
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage errorMessageValidationMaxAttorneyLawFirm(){
		waitUntilElementVisible("ErrorMessageForNumberOfAttorneys");
		Utility.validateUIDataField("validateErrorMessageForNumberOfAttorneys", "ErrorMessageForNumberOfAttorneys",
				getTextOfElement("ErrorMessageForNumberOfAttorneys"), "ErrorMessageLawFirm", "UI", true, "Equals");

		return this;
	}

	/**
	 * This method is used to input the number of seats
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage inputNumberOfSeats(String numberOfSeats) {

		waitForFixedTime(2);
		waitUntilElementVisible("FirstNumberOfSeatsTextBox");
		clickOnElement("FirstNumberOfSeatsTextBox");
		doubleClickOnElement("FirstNumberOfSeatsTextBox");
		waitForFixedTime(1);
		sendKeysTotheElement("FirstNumberOfSeatsTextBox", numberOfSeats);
		waitForFixedTime(1);
		ExtentLogger.info("Entered Number of Seats: " + numberOfSeats);
		clickOnElement("SectorHeading");
		return this;
	}

	/**
	 * This method is used to validate the error message for the number of seats
	 * 
	 * @param errorMessage
	 * @created by Ishwarya
	 */
	public PPPage validateErrorMessageForNumberOfSeats(String errorMessage) {

		ExtentLogger.info("Validating Error Message for Number of Seats Field");
		waitUntilElementVisible("ErrorMessageForNumberOfSeats");
		Utility.validateUIDataField("validateErrorMessageForNumberOfSeats", "ErrorMessageForNumberOfSeats",
				getTextOfElement("ErrorMessageForNumberOfSeats"), errorMessage, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to validate the actual number of seats entered
	 * 
	 * @param seats
	 * @created by Ishwarya
	 */
	public PPPage validateActualNumberOfSeats(String seats) {

		ExtentLogger.info("Validating Actual Number of Seats");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateActualNumberOfSeats", "NumberOfSeatsHeading",
				getTextOfElement("NumberOfSeatsHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "NO_OF_SEATS_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateActualNumberOfSeats", "NumberOfSeatsSubHeading",
				getTextOfElement("NumberOfSeatsSubheading"),
				ReadJson.getStringValue(ppPageDataFileName, "NO_OF_SEATS_SUBHEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateActualNumberOfSeats", "NumberOfSeats",
				getAttributeValueOfGivenAttribute("FirstNumberOfSeatsTextBox", "value"), seats, "UI", true, "Equals");

		return this;
	}

	/**
	 * This method is used to validate the error message for the number of attorneys
	 * 
	 * @param errorMessage
	 * @created by Ishwarya
	 */
	public PPPage validateErrorMessageForNumberOfAttorney(String errorMessage) {

		ExtentLogger.info("Validating Error Message for Number of Attorneys Field");
		waitUntilElementVisible("ErrorMessageForNumberOfAttorneys");
		Utility.validateUIDataField("validateErrorMessageForNumberOfAttorneys", "ErrorMessageForNumberOfAttorneys",
				getTextOfElement("ErrorMessageForNumberOfAttorneys"), errorMessage, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to validate the actual number of attorneys entered
	 * 
	 * @param attorney
	 * @created by Ishwarya
	 */
	public PPPage validateActualNumberOfAttorneys(String attorney, String sector) {

		ExtentLogger.info("Validating Actual Number of Attorneys");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneyHeading",
				getTextOfElement("NumberOfSeatsHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_HEADING"), "UI", true, "Equals");
		if (sector.equals("Law firm")) {

			Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneySubHeading",
					getTextOfElement("NumberOfSeatsSubheading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_SUBHEADING_FIRM"), "UI", true,
					"Equals");
		} else {
			Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneySubHeading",
					getTextOfElement("NumberOfSeatsSubheading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_SUBHEADING"), "UI", true, "Equals");
		}
		Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorney",
				getAttributeValueOfGivenAttribute("FirstNumberOfAttorneysTextBox", "value"), attorney, "UI", true,
				"Equals");

		return this;
	}

	/**
	 * This method is used to select the plan based on the plan name
	 * 
	 * @param plan
	 * @created by Ishwarya
	 */
	public PPPage selectPlan(String plan) {

		Map<String, String> planMap = new HashMap<>();
		planMap.put("Good Plan", "SelectGoodPlan");
		planMap.put("Better Plan", "SelectBetterPlan");
		planMap.put("Best Plan", "SelectBestPlan");

		for (Map.Entry<String, String> planNameEntry : planMap.entrySet()) {
			if (plan.contains(planNameEntry.getKey())) {
				String planName = planNameEntry.getValue();
				if (!isListEmpty("Selected" + planName.replace("Select", "")) == true) {
					ExtentLogger.info("Selected Plan: " + plan);
					break;
				} else {
					waitUntilElementVisible(planName);
					clickOnElement(planName);
					ExtentLogger.info("Selected Plan: " + plan);
					waitUntilElementVisible("Selected" + planName.replace("Select", ""));
					break;
				}
			}
		}
		PPPage.plan = plan;
		return this;
	}

	/**
	 * This method is used to select the plan based on the plan name
	 *
	 * @param plan
	 * @created by Ishwarya
	 */
	public PPPage selectPlanValidation(String plan) {

		Map<String, String> planMap = new HashMap<>();
		planMap.put("Good Plan", "SelectGoodPlan");
		planMap.put("Better Plan", "SelectBetterPlan");
		planMap.put("Best Plan", "SelectBestPlan");

		for (Map.Entry<String, String> entry : planMap.entrySet()) {
			if (plan.contains(entry.getKey())) {
				String planName = entry.getValue();
				waitUntilElementVisible(planName);
				scrollToParticularElement(planName);
				clickOnElement(planName);
				ExtentLogger.info("Selected Plan: " + plan);
				break;
			}
		}
		PPPage.plan = plan;
		return this;
	}

	/**
	 * This method is used to select the plan based on the plan name
	 *
	 * @created by Ishwarya
	 */
	public PPPage validateDefaultAttorneyAndTerm() {

		Map<String, String> planMap = new HashMap<>();
		planMap.put("Good Plan", "SelectGoodPlan");
		planMap.put("Better Plan", "SelectBetterPlan");
		planMap.put("Best Plan", "SelectBestPlan");

		for (Map.Entry<String, String> planNameEntry : planMap.entrySet()) {

			String planName = planNameEntry.getValue();
			waitUntilElementVisible(planName);
			scrollToParticularElement(planName);
			clickOnElement(planName);
			ExtentLogger.info("Selected Plan: " + planName);
			String selectedPlanButton = planName.replace("Select", "Selected");
			waitUntilElementVisible(selectedPlanButton);
			// the default attorney value should be 1 and contract ter should be 3 years for
			// PL, wl edge and classic
			Utility.validateUIDataField("validateDefaultPlan", "DefaultAttroeyFor" + planName.replace("Select", ""),
					getAttributeValueOfGivenAttribute("FirstNumberOfSeatsTextBox", "value"), "1", "UI", true, "Equals");
			Utility.validateUIDataField("validateDefaultPlan",
					"DefaultContractTermFor" + planName.replace("Select", ""), getTextOfElement("SelectedContractTerm"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_OPTION3"), "UI", true, "Equals");
		}
		return this;
	}

	/**
	 * This method is used to validate the default plan
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateDefaultPlan() {

		ExtentLogger.info("Validating Default Plan");
		scrollToParticularElement("SelectedBestPlan");
		Utility.validateUIDataField("validateDefaultPlan", "IsDefaultPlanIsBestPlan", !isListEmpty("SelectedBestPlan"),
				true, "UI", true, "Equals");
		String url = getURL();
		if (url.contains("practical")) {
			Utility.validateUIDataField("validateDefaultPlan", "DefaultPlanGenAIText",
					getTextOfElement("DefaultPlanGenAIText"),
					ReadJson.getStringValue(ppPageDataFileName, "GEN_AI_TEXT"), "UI", true, "Equals");

			Utility.validateUIDataField("validateDefaultPlan", "MultinationalToolTip",
					!isListEmpty("MultinationalToolTip"), true, "UI", true, "Equals");
			Utility.validateUIDataField("validateDefaultPlan", "MultinationalLabel",
					getTextOfElement("MultinationalLabel"),
					ReadJson.getStringValue(ppPageDataFileName, "MULTINATIONAL_LABEL"), "UI", true, "Equals");
			clickOnElement("MultinationalToolTip");
			Utility.validateUIDataField("validateDefaultPlan", "MultinationalToolTipText",
					getTextOfElement("MultinationalToolTipText"),
					ReadJson.getStringValue(ppPageDataFileName, "MULTINATIONAL_TOOLTIP_TEXT"), "UI", true, "Equals");
		}
		return this;
	}

	/**
	 * This method is used to select the plan duration based on the plan duration
	 * 
	 * @param planDuration
	 * @created by Ishwarya
	 */
	public PPPage selectPlanDuration(String planDuration) {

		// wait till the plan names get updated
		waitForFixedTime(3);
		Map<String, String> planMap = new HashMap<>();
		planMap.put("1 year", "OneYearPlan");
		planMap.put("2 year", "TwoYearPlan");
		planMap.put("3 year", "ThreeYearPlan");

		for (Map.Entry<String, String> planDurationEntry : planMap.entrySet()) {
			if (planDuration.contains(planDurationEntry.getKey())) {
				plan = planDurationEntry.getValue();
				waitUntilElementVisible(plan);
				clickOnElement(plan);
				ExtentLogger.info("Selected Plan Duration: " + planDuration);
				break;
			}
		}
		return this;
	}

	/**
	 * This method is used to validate the price strike off for the plans for every
	 * plan duration
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateIfPriceStrikeOff() {
		Map<String, String> planMap = new HashMap<>();
		planMap.put("1 year", "OneYearPlan");
		planMap.put("2 year", "TwoYearPlan");
		planMap.put("3 year", "ThreeYearPlan");

		Map<String, String> planColumnMap = new HashMap<>();
		planColumnMap.put("1", "GoodPlan");
		planColumnMap.put("2", "BetterPlan");
		planColumnMap.put("3", "BestPlan");

		for (Map.Entry<String, String> planDurationEntry : planMap.entrySet()) {
			String planContractYear = planDurationEntry.getValue();
			waitUntilElementVisible(planContractYear);
			clickOnElement(planContractYear);
			ExtentLogger.info("Selected Plan Duration: " + planContractYear);

			boolean isOneYearPlan = planContractYear.contains("1");
			int columnCount = getSizeOfElement("PlanComparisionTableColumnCount");

			for (int planNameNumber = 1; planNameNumber <= columnCount; planNameNumber++) {
				String planColumnName = planColumnMap.get(String.valueOf(planNameNumber));
				boolean isListEmpty = isParameterisedListEmpty("PlanComparisionStrikeOffPrice", "PLACEHOLDER",
						String.valueOf(planNameNumber));

				if (planContractYear.contains("OneYear")) {
					Utility.validateUIDataField("validateIfPriceStrikeOff",
							"IsPriceStrikeOffNotPresentFor" + planContractYear + planColumnName, isListEmpty, true,
							"UI", true, "Equals");
				} else {
					Utility.validateUIDataField("validateIfPriceStrikeOff",
							"IsPriceStrikeOffPresentFor" + planContractYear + planColumnName, !isListEmpty, true, "UI",
							true, "Equals");
				}
			}
			break;
		}
		return this;
	}

	/**
	 * This method is used to validate the plan duration section
	 * 
	 * @param sectorName
	 * @param planDuration
	 * @created by Ishwarya
	 */
	public PPPage validatePlanDuration(String sectorName, String planDuration) {

		ExtentLogger.info("Validating Plan Duration Section");
		if (sectorName.equals("Law firm")) {
			Utility.validateUIDataField("validatePlanDuration", "PlanDurationHeading",
					getTextOfElement("PlanDurationHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_HEADING").replace("SECTORNAME",
							sectorName),
					"UI", true, "Equals");
		} else {
			Utility.validateUIDataField("validatePlanDuration", "PlanDurationHeading",
					getTextOfElement("PlanDurationHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_HEADING").replace("SECTORNAME",
							Utility.toTitleCase(sectorName)),
					"UI", true, "Equals");
		}
		Utility.validateUIDataField("validatePlanDuration", "PlanDurationHeading",
				getTextOfElement("PlanDurationSubHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_SUBHEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanDuration", "PlanDurationOption1", getTextOfElement("OneYearPlan"),
				ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_OPTION1"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanDuration", "PlanDurationOption2", getTextOfElement("TwoYearPlan"),
				ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_OPTION2"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanDuration", "PlanDurationOption3", getTextOfElement("ThreeYearPlan"),
				ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_OPTION3"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanDuration", "Is" + plan + "Selected",
				getAttributeValueOfGivenAttribute(plan, "aria-selected"), "true", "UI", true, "Equals");

		return this;
	}

	/**
	 * This method is used to get the plan details after selecting the plan
	 * 
	 * @creaated by Ishwarya
	 */
	public PPPage getPlanDetails() {

		// waiting for 2 sec to let the price update
		waitForFixedTime(3);
		waitUntilElementVisible("PlanPrice");
		scrollToParticularElement("PlanPrice");
		planPriceInString = getTextOfElement("PlanPrice");
		ExtentLogger.info("Plan Price is: " + planPriceInString);
		planPrice = Double.parseDouble(planPriceInString.replaceAll("[^0-9.]", ""));
		ExtentLogger.info("Plan Price in integer is: " + planPrice);
		brandName = getTextOfElement("BrandName");
		String[] words = brandName.toLowerCase().split(" ");
		StringBuilder titleCase = new StringBuilder();

		for (String word : words) {
			if (titleCase.length() > 0) {
				titleCase.append(" ");
			}

			titleCase.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
		}
		String requiredBrandName = "";
		requiredBrandName = titleCase.toString();
		planCategoryName = getTextOfElement("PlanCategoryName");
		planName = requiredBrandName + " " + planCategoryName;
		ExtentLogger.pass("Plan Name is: " + planName, true);
		ExtentLogger.pass("Brand Name is: " + brandName, true);
		ExtentLogger.pass("Plan Category Name is: " + planCategoryName, true);
		return this;
	}

	/**
	 * This method is used to validate plan summary without add ons and jurisdiction
	 * Method for PL(all sectors) and WE,WC(only general counsel sector)
	 *
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validatePlanSummaryWithoutAddOnsSection(String sector, String attorney, String duration) {

		ExtentLogger.info("Validating Plan Summary Without Add Ons Section");
		String url = getURL();
		if (url.contains("practical")) {
			waitUntilElementVisible("PlanSummary");
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryHeading",
					getTextOfElement("PlanSummary"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_SUMMARY_HEADING"), "UI", true, "Equals");

			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummarySubHeading",
					getTextOfElement("PlanSummarySubHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_SUMMARY_SUBHEADING"), "UI", true, "Equals");
		} else {
			waitUntilElementVisible("PlanSummaryWithoutJurisdictionEdgeClassic");
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryHeading",
					getTextOfElement("PlanSummaryWithoutJurisdictionEdgeClassic"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_SUMMARY_HEADING"), "UI", true, "Equals");
		}
		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection",
				"PlanSummaryNumberOfAttorneysOrSeatsTextBox",
				getAttributeValueOfGivenAttribute("PlanSummaryNumberOfAttorneysOrSeatsTextBox", "value"), attorney,
				"UI", true, "Equals");
		String attributeValue = getAttributeValueOfGivenAttribute("PlanSummaryPlanDuration", "data-placeholder");
		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryPlanDuration",
				Utility.removeSpecialChars(attributeValue), Utility.toTitleCase(duration), "UI", true, "Contains");
		if (sector.contains("General")) {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryNoOfSeatsHeading",
					getTextOfElement("PlanSummaryNoOfSeatsHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_SEATS_HEADING"), "UI", true, "Equals");
		} else {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryNoOfSeatsHeading",
					getTextOfElement("PlanSummaryNoOfSeatsHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_HEADING"), "UI", true, "Equals");
		}
		if (url.contains("practical")) {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryNoOfSeatsSubHeading",
					getTextOfElement("PlanSummaryNoOfSeatsSubHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "SUMMARY_NO_OF_SEATS_SUBHEADING"), "UI", true,
					"Equals");
		}
		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryDurationHeading",
				getTextOfElement("PlanSummaryDurationHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_SUBHEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection",
				"IsPlanSummaryDurationHeadingToolTipPresent", !isListEmpty("PlanSummaryDurationHeadingToolTip"), true,
				"UI", true, "Equals");
		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryDurationSubHeading",
				getTextOfElement("PlanSummaryDurationSubHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "SUMMARY_PLAN_DURATION_SUBHEADING"), "UI", true, "Equals");

		int numberOfAttorneys = Integer.parseInt(attorney);
		String expectedShortSummary = ReadJson.getStringValue(ppPageDataFileName, "SHORT_SUMMARY")
				.replace("SEATS", Utility.convertNumberToWord(numberOfAttorneys))
				.replace("CONTRACTTERM", duration.split(" ")[0]);
		if (numberOfAttorneys == 1) {
			expectedShortSummary = expectedShortSummary.replace("users", "user");
		}
		if (url.contains("practical")) {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryPanelHeading",
					getTextOfElement("PlanSummaryPanelHeading"),
					Utility.toTitleCase(brandName) + " " + planCategoryName, "UI", true, "Equals");
		} else {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryPanelHeading",
					getTextOfElement("PlanSummaryPanelHeadingWEOrWC"),
					(Utility.toTitleCase(brandName) + " " + planCategoryName).replace(",", " -"), "UI", true, "Equals");
		}

		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryShortSummary",
				getTextOfElement("PlanSummaryShortSummary"), expectedShortSummary, "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryPlanPrice",
				getTextOfElement("PlanSummaryPlanPrice"), planPriceInString, "UI", true, "Equals");
		if (sector.contains("General") || sector.contains("Law")) {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryNegotiationHeading",
					getTextOfElement("PlanSummaryNegotiationHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_SUMMARY_NEGOTIATION_HEADING"), "UI", true,
					"Equals");
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryNegotiationText",
					getTextOfElement("PlanSummaryNegotiationText"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_SUMMARY_NEGOTIATION_TEXT"), "UI", true, "Equals");
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection",
					"IsPlanSummaryNegotiationIconPresent", !isListEmpty("PlanSummaryNegotiationIcon"), true, "UI", true,
					"Equals");
		} else {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection",
					"IsPlanSummaryNegotiationIconNotPresent", isListEmpty("PlanSummaryNegotiationIcon"), true, "UI",
					true, "Equals");
		}
		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "CPITextMessage",
				getTextOfElement("CPITextMessage"), ReadJson.getStringValue(ppPageDataFileName, "CPI_TEXT"), "UI", true,
				"Equals");
		return this;
	}

	/**
	 * This method is used to validate plan summary with add ons and jurisdiction
	 * method for WE and WC (Law firm and government)
	 *
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validatePlanSummaryWithAddOnsSection(String sector, String attorney, String duration,
			String jurisdiction, String plan) {

		ExtentLogger.info("Validating Plan Summary With Add Ons Section");
		waitUntilElementVisible("PlanSummaryHeadingWhenJurisdictionPresent");

		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryHeading",
				getTextOfElement("PlanSummaryHeadingWhenJurisdictionPresent"),
				ReadJson.getStringValue(ppPageDataFileName, "PLAN_SUMMARY_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryAttorey",
				getAttributeValueOfGivenAttribute("PlanSummaryNumberOfAttorneysOrSeatsTextBox", "value"), attorney,
				"UI", true, "Equals");
		if (jurisdiction != null && plan.equalsIgnoreCase("Good Plan")) {
			Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryJurisdiction",
					getTextOfElement("PlanSummaryJurisdiction"), jurisdiction, "UI", true, "Contains");
		}
//		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryPlanPrice",
//				getTextOfElement("PlanSummaryPlanPrice"), planPriceInString, "UI", true, "Equals");
		String attributeValue = getAttributeValueOfGivenAttribute("PlanSummaryPlanDuration", "data-placeholder");
		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryPlanDuration",
				Utility.removeSpecialChars(attributeValue), Utility.toTitleCase(duration), "UI", true, "Contains");
		if (sector.contains("General")) {
			Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryNoOfSeatsHeading",
					getTextOfElement("PlanSummaryNoOfSeatsHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_SEATS_HEADING"), "UI", true, "Equals");
		} else {
			Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryNoOfAttorneyHeading",
					getTextOfElement("PlanSummaryNoOfSeatsHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_HEADING"), "UI", true, "Equals");
		}
		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryDurationHeading",
				getTextOfElement("PlanSummaryDurationHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "PLAN_DURATION_SUBHEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection",
				"IsPlanSummaryDurationHeadingToolTipPresent", !isListEmpty("PlanSummaryDurationHeadingToolTip"), true,
				"UI", true, "Equals");
		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryDurationSubHeading",
				getTextOfElement("PlanSummaryDurationSubHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "SUMMARY_PLAN_DURATION_SUBHEADING"), "UI", true, "Equals");

		int numberOfAttorneys = Integer.parseInt(attorney);
		String expectedShortSummary = ReadJson.getStringValue(ppPageDataFileName, "SHORT_SUMMARY")
				.replace("SEATS", Utility.convertNumberToWord(numberOfAttorneys))
				.replace("CONTRACTTERM", duration.split(" ")[0]);
		if (numberOfAttorneys == 1) {
			expectedShortSummary = expectedShortSummary.replace("users", "user");
		}

		String expectedPanelHeading;
		if (plan.equalsIgnoreCase("Good Plan")) {
			// in case we give a random jurisdiction, then the input jurisdiction for this
			// method will be empty
			if (!jurisdiction.trim().equals("")) {
				expectedPanelHeading = (Utility.toTitleCase(brandName) + " " + planCategoryName).replace(",", "")
						.replace(jurisdiction, "- " + jurisdiction);
			} else {
				expectedPanelHeading = (Utility.toTitleCase(brandName) + " " + planCategoryName).replace(",", "");
			}

		} else {
			if (!jurisdiction.trim().equals("")) {
				expectedPanelHeading = (Utility.toTitleCase(brandName) + " " + planCategoryName).replace(jurisdiction,
						"- " + jurisdiction);
			} else {
				expectedPanelHeading = (Utility.toTitleCase(brandName) + " " + planCategoryName);
			}
		}

		double totalPriceValue = planPrice + addonPrice;
		BigDecimal totalPriceBigDecimal = new BigDecimal(totalPriceValue).setScale(2, RoundingMode.UP);
		String totalPrice = totalPriceBigDecimal.toString();
		Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryPanelHeading",
				getTextOfElement("PlanSummaryPanelHeadingWhenJurisdictionPresent"), expectedPanelHeading, "UI", true,
				"Equals");
		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryShortSummary",
				getTextOfElement("PlanSummaryShortSummary"), expectedShortSummary, "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "PlanSummaryPlanPrice",
				getTextOfElement("PlanSummaryPlanPrice").replaceAll("[^0-9.]", ""), totalPrice, "UI", true, "Equals");
		Utility.validateUIDataField("validatePlanSummaryWithAddOnsSection", "CPITextMessage",
				getTextOfElement("CPITextMessage"), ReadJson.getStringValue(ppPageDataFileName, "CPI_TEXT"), "UI", true,
				"Equals");

		if (sector.contains("General") || sector.contains("Law")) {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryNegotiationHeading",
					getTextOfElement("PlanSummaryNegotiationHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_SUMMARY_NEGOTIATION_HEADING"), "UI", true,
					"Equals");
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection", "PlanSummaryNegotiationText",
					getTextOfElement("PlanSummaryNegotiationText"),
					ReadJson.getStringValue(ppPageDataFileName, "PLAN_SUMMARY_NEGOTIATION_TEXT"), "UI", true, "Equals");
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection",
					"IsPlanSummaryNegotiationIconPresent", !isListEmpty("PlanSummaryNegotiationIcon"), true, "UI", true,
					"Equals");
		} else {
			Utility.validateUIDataField("validatePlanSummaryWithoutAddOnsSection",
					"IsPlanSummaryNegotiationIconNotPresent", isListEmpty("PlanSummaryNegotiationIcon"), true, "UI",
					true, "Equals");
		}
		return this;
	}

	/**
	 * This method is to change the duration in the plan summary section
	 *
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage changeDurationInPlanSummarySection(String duration) {

		ExtentLogger.info("Changing Duration In Plan Summary Section");
		waitForFixedTime(2);
		scrollToParticularElement("PlanSummaryPlanDuration");
		clickOnElement("PlanSummaryPlanDuration");
		clickOnTheParameterisedXpath("PlanSummaryPlanDurationOption", "PLACEHOLDER", Utility.toTitleCase(duration));
		waitForFixedTime(3);
		String durationInPlanSummary = getTextOfElement("PlanSummaryPlanDurationText").replaceAll("\\s+$", "");
		String expectedDuration = duration.replace(" ", "-").replace("s", "") + " term.";
		Utility.logGenerator("changeDurationInPlanSummarySection",
				durationInPlanSummary.equalsIgnoreCase(expectedDuration), "Duration changed to " + duration,
				"Can't change the duration or duration is not displayed", true);
		Utility.validateUIDataField("changeDurationInPlanSummarySection", "PlanSummaryPlanDurationText",
				durationInPlanSummary.toLowerCase().trim(), expectedDuration.toLowerCase().trim(), "UI", true,
				"contains");
		return this;
	}

	/**
	 * This method is used to add the add ons in plan summary section
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage addAddOns(String addOnsCount, String plan, String sector) {

		ExtentLogger.info("Adding Add Ons");
		int numberOfAddOns = Integer.parseInt(addOnsCount);
		this.numberOfAddOns = numberOfAddOns;
		waitForFixedTime(3);
		waitUntilElementVisible("SelectFirstAddOn");
		if (sector.trim().equalsIgnoreCase("Law firm")) {
			if (plan.trim().equalsIgnoreCase("Good Plan")) {
				waitUntilElementVisible("SelectFirstAddOn");
				String firstAddonPrice = "";
				for (int i = 1; i <= numberOfAddOns; i++) {
					addOnName = getTextOfElement("SelectFirstAddOn");
					waitForFixedTime(3);
					clickOnElement("SelectFirstAddOn");
					if (i == 1) {
						addonPriceInString = getTextOfElement("SelectedFirstAddOnPrice");
						firstAddonPrice = addonPriceInString;
						Utility.validateUIDataField("addOnselection", "SelectedFirstAddOn", addOnName,
								getTextOfElement("SelectedFirstAddOnName"), "UI", true, "Contains");
						ExtentLogger.info("Addon selected is visible in plan summary section");
						Utility.validateUIDataField("addOnselection", "SelectedFirstAddOnPrice", addonPriceInString,
								getTextOfElement("SelectedFirstAddOnPrice"), "UI", true, "Equals");
						ExtentLogger.info("Addon price in plan summary section is validated successfully");
					} else if (i == 2) {
						addonPriceInString = getTextOfElement("SelectSecondAddOnPrice");
						Utility.validateUIDataField("addOnselection", "SelectedSecondAddOn", addOnName,
								getTextOfElement("SelectedSecondAddOn"), "UI", true, "Contains");
						ExtentLogger.info("Addon selected is visible in plan summary section");
						Utility.validateUIDataField("addOnselection", "SelectedSecondAddOnPrice", addonPriceInString,
								getTextOfElement("SelectedSecondAddOnPrice"), "UI", true, "Equals");
						ExtentLogger.info("Addon price in plan summary section is validated successfully");
					} else if (i == 3) {
						addonPriceInString = getTextOfElement("SelectThirdAddOnPrice");
						Utility.validateUIDataField("addOnselection", "SelectedThirdAddOn", addOnName,
								getTextOfElement("SelectedThirdAddOn"), "UI", true, "Contains");
						ExtentLogger.info("Addon selected is visible in plan summary section");
						Utility.validateUIDataField("addOnselection", "SelectedThirdAddOnPrice", addonPriceInString,
								getTextOfElement("SelectedThirdAddOnPrice"), "UI", true, "Equals");
						ExtentLogger.info("Addon price in plan summary section is validated successfully");
					}
					addonPrice += Double.parseDouble(addonPriceInString.replaceAll("[^0-9.]", ""));
					ExtentLogger.info("Total Addon Price in integer is: " + addonPrice);
				}
				Utility.validateUIDataField("addOnselection", "SelectedNumberOfAddons",
						numberOfAddOns + " add-ons selected", getTextOfElement("SelectedNumberOfAddons"), "UI", true,
						"Equals");
				Utility.validateUIDataField("addOnselection", "SelectedNumberOfAddons",
						numberOfAddOns + " add-ons selected", getTextOfElement("SelectedNumberOfAddons"), "UI", true,
						"Equals");
				ExtentLogger.info("Addon numbers in plan summary validated successfully");
			} else if (sector.trim().equalsIgnoreCase("Law firm")) {
				String firstAddonPrice = "";
				for (int i = 1; i <= numberOfAddOns; i++) {
					addOnName = getTextOfElement("SelectFirstAddOn");
					waitForFixedTime(3);
					clickOnElement("SelectFirstAddOn");
					if (i == 1) {
						addonPriceInString = getTextOfElement("SelectedFirstAddOnPrice");
						firstAddonPrice = addonPriceInString;
						Utility.validateUIDataField("addOnselection", "SelectedFirstAddOn", addOnName,
								getTextOfElement("SelectedFirstAddOnName"), "UI", true, "Contains");
						ExtentLogger.info("Addon selected is visible in plan summary section");
						Utility.validateUIDataField("addOnselection", "SelectedFirstAddOnPrice", addonPriceInString,
								getTextOfElement("SelectedFirstAddOnPrice"), "UI", true, "Equals");
						ExtentLogger.info("Addon price in plan summary section is validated successfully");
					} else if (i == 2) {
						addonPriceInString = getTextOfElement("SelectSecondAddOnPrice");
						Utility.validateUIDataField("addOnselection", "SelectedSecondAddOn", addOnName,
								getTextOfElement("SelectedSecondAddOn"), "UI", true, "Contains");
						ExtentLogger.info("Addon selected is visible in plan summary section");
						Utility.validateUIDataField("addOnselection", "SelectedSecondAddOnPrice", addonPriceInString,
								getTextOfElement("SelectedSecondAddOnPrice"), "UI", true, "Equals");
						ExtentLogger.info("Addon price in plan summary section is validated successfully");
					}
					addonPrice += Double.parseDouble(addonPriceInString.replaceAll("[^0-9.]", ""));
					ExtentLogger.info("Total Addon Price in integer is: " + addonPrice);
				}
				Utility.validateUIDataField("addOnselection", "SelectedNumberOfAddons",
						numberOfAddOns + " add-ons selected", getTextOfElement("SelectedNumberOfAddons"), "UI", true,
						"Equals");
				Utility.validateUIDataField("addOnselection", "SelectedNumberOfAddons",
						numberOfAddOns + " add-ons selected", getTextOfElement("SelectedNumberOfAddons"), "UI", true,
						"Equals");
				ExtentLogger.info("Addon numbers in plan summary validated successfully");
			}
		} else {
			String firstAddonPrice = "";
			for (int i = 1; i <= numberOfAddOns; i++) {
				addOnName = getTextOfElement("SelectFirstAddOn");
				waitForFixedTime(3);
				clickOnElement("SelectFirstAddOn");
				if (i == 1) {
					addonPriceInString = getTextOfElement("SelectedFirstAddOnPrice");
					firstAddonPrice = addonPriceInString;
					Utility.validateUIDataField("addOnselection", "SelectedFirstAddOn", addOnName,
							getTextOfElement("SelectedFirstAddOnName"), "UI", true, "Contains");
					ExtentLogger.info("Addon selected is visible in plan summary section");
					Utility.validateUIDataField("addOnselection", "SelectedFirstAddOnPrice", addonPriceInString,
							getTextOfElement("SelectedFirstAddOnPrice"), "UI", true, "Equals");
					ExtentLogger.info("Addon price in plan summary section is validated successfully");
				} else if (i == 2) {
					addonPriceInString = getTextOfElement("SelectSecondAddOnPrice");
					Utility.validateUIDataField("addOnselection", "SelectedSecondAddOn", addOnName,
							getTextOfElement("SelectedSecondAddOn"), "UI", true, "Contains");
					ExtentLogger.info("Addon selected is visible in plan summary section");
					Utility.validateUIDataField("addOnselection", "SelectedSecondAddOnPrice", addonPriceInString,
							getTextOfElement("SelectedSecondAddOnPrice"), "UI", true, "Equals");
					ExtentLogger.info("Addon price in plan summary section is validated successfully");
				}
				addonPrice += Double.parseDouble(addonPriceInString.replaceAll("[^0-9.]", ""));
				ExtentLogger.info("Total Addon Price in integer is: " + addonPrice);
			}
			Utility.validateUIDataField("addOnselection", "SelectedNumberOfAddons",
					numberOfAddOns + " add-ons selected", getTextOfElement("SelectedNumberOfAddons"), "UI", true,
					"Equals");
			Utility.validateUIDataField("addOnselection", "SelectedNumberOfAddons",
					numberOfAddOns + " add-ons selected", getTextOfElement("SelectedNumberOfAddons"), "UI", true,
					"Equals");
			ExtentLogger.info("Addon numbers in plan summary validated successfully");
		}
		return this;
	}

	/**
	 * This method is to remove add ons in plan summary section
	 *
	 * @created by Yunus
	 */
	public PPPage removeAddons(int removeAddons, String numberOfAddOns) {

		ExtentLogger.info("Removing Add Ons");
		int numberOfAddOn = Integer.parseInt(numberOfAddOns);
		if (numberOfAddOn >= removeAddons) {
			int sizOfRemoveButton = getSizeOfElement("RemoveAddonButtons");
			scrollToParticularElement("RemoveFirstAddon");
			waitUntilElementVisible("RemoveFirstAddon");
			clickOnElement("RemoveFirstAddon");
			waitUntilParameterizedElementNotVisible("RemoveAddonButton", "PLACEHOLDER",
					String.valueOf(sizOfRemoveButton));
			String subtractedAddonPriceInString = getTextOfElement("RemovedAddOnPrice");
			ExtentLogger.pass("Removed Addon Price is: " + subtractedAddonPriceInString, true);
			double subtractedAddonPrice = Double.parseDouble(subtractedAddonPriceInString.replaceAll("[^0-9.]", ""));
			ExtentLogger.info("Total Addon Price in integer is: " + addonPrice);
			addonPrice = addonPrice - subtractedAddonPrice;
			addonPrice = Math.round(addonPrice * 100.0) / 100.0;
			ExtentLogger.info("Total Addon Price in integer is: " + addonPrice);
			Utility.validateUIDataField("addOnselection", "RemovedAddOnPrice", subtractedAddonPriceInString,
					getTextOfElement("RemovedAddOnPrice"), "UI", true, "Equals");
			int remainingAddOns = numberOfAddOn - removeAddons;
			Utility.validateUIDataField("addOnselection", "SelectedNumberOfAddons",
					remainingAddOns + " add-ons selected", getTextOfElement("SelectedNumberOfAddons"), "UI", true,
					"Equals");
			ExtentLogger.info("Addon numbers in plan summary validated successfully");
		} else {
			ExtentLogger.info("Addons are not available to remove");
		}
		return this;
	}

	/**
	 * This method is used to validate the attorney error message in plan summary
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateAttorneyErrorMessageInPlanSummary(String sector, String attorney,
			String attroneyErrorMessage) {

		ExtentLogger.info("Validating Attorney Error Message In Plan Summary");
		int maxAttorneys = 0;
		String url = getURL();

		if (url.contains("practical")) {

			if (sector.equalsIgnoreCase("General Counsel")) {
				maxAttorneys = 5;
			} else {
				maxAttorneys = 10;
			}
		} else {
			if (sector.equalsIgnoreCase("General Counsel")) {
				maxAttorneys = 5;
			} else if (sector.equalsIgnoreCase("Government")) {
				maxAttorneys = 2;
			} else {
				maxAttorneys = 10;
			}
		}
		validateAttorneyErrorMessageHelper(attorney, maxAttorneys, attroneyErrorMessage);
		doubleClickOnElement("PlanSummaryNumberOfAttorneysOrSeatsTextBox");
		waitForFixedTime(1);
		sendKeysTotheElement("PlanSummaryNumberOfAttorneysOrSeatsTextBox", attorney);
		waitForFixedTime(1);
		clickOnElement("PlanSummaryNoOfSeatsHeading");
		// waiting for 2 sec to let the price update
		waitForFixedTime(2);
		return this;
	}

	/**
	 * This method is used to validate the attorney error message in plan summary
	 * helper method
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	private void validateAttorneyErrorMessageHelper(String attorney, int maxAttorneys, String errorMessageKey) {

		int attorneyDiff = maxAttorneys - Integer.parseInt(attorney);
		// clicking on the + button till the max attorney value is reached
		for (int attroneyNumber = 1; attroneyNumber <= attorneyDiff; attroneyNumber++) {
			scrollToParticularElement("PlanSummaryIncreaseButton");
			clickOnElement("PlanSummaryIncreaseButton");
			waitForFixedTime(2);
		}
		clickOnElement("PlanSummaryIncreaseButton");
		Utility.validateUIDataField("validateAttorneyErrorMessageInPlanSummary", "PlanSummaryMaxAttorneyErrorMessage",
				getTextOfElement("PlanSummaryMaxAttorneyErrorMessage"), errorMessageKey, "UI", true, "Equals");
	}

	/**
	 * This method is used to validate total price
	 * 
	 * @return modified by Ishwarya
	 */
	public PPPage validateTotalPrice() {

		totalPlanPrice = planPrice + addonPrice;
		// Round totalPlanPrice to two decimal places
		totalPlanPrice = Math.round(totalPlanPrice * 100.0) / 100.0;
		totalPriceInString = getTextOfElement("TotalPrice");
		Utility.validateUIDataField("validateTotalPrice", "PlanSummaryPlanPrice",
				Double.parseDouble(totalPriceInString.replaceAll("[^0-9.]", "")), totalPlanPrice, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to click on 'go to checkout' button
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage clickOnGoToCheckout() {

		// Example usage in a test case
		scrollToParticularElement("GoToCheckoutButton");
		waitUntilElementVisible("GoToCheckoutButton");
		clickOnElement("GoToCheckoutButton");
		waitUntilElementNotVisible("GoToCheckoutButton");
		Utility.logGenerator("clickOnGoToCheckout", isListEmpty("GoToCheckoutButton"), "Clicked on Go to Checkout",
				"Can't clicked on Go to Checkout button or Go to Checkout button is not displayed", true);
		return this;
	}

	/**
	 * This method is used to validate the currency details in the PP page
	 * 
	 * modified by Ishwarya
	 */
	public PPPage verifyCurrency(String symbol, boolean addOn) {

		ExtentLogger.info("Verifying Currency in PPP Page");
		Utility.validateUIDataField("verifyCurrency", "TotalPriceSymbol", getTextOfElement("TotalPrice"), symbol, "UI",
				true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "PlanPriceSymbol", getTextOfElement("PlanPrice"), symbol, "UI",
				true, "Contains");
		if (addOn) {
			Utility.validateUIDataField("verifyCurrency", "SelectedFirstAddOnPriceSymbol",
					getTextOfElement("SelectedFirstAddOnPrice"), symbol, "UI", true, "Contains");
		}
		ExtentLogger.pass("Symbol is: " + symbol, true);
		return this;
	}

	/**
	 * This method is used to currency details in the PPP page without add on
	 * 
	 * @param symbol
	 * @param addOn
	 * @created by Ishwarya
	 */
	public PPPage verifyCurrency_NoAddOn(String symbol, boolean addOn) {

		ExtentLogger.info("Verifying Currency in PPP Page");
		Utility.validateUIDataField("verifyCurrency", "TotalPriceSymbol", getTextOfElement("TotalPrice"), symbol, "UI",
				true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "PlanPriceSymbol", getTextOfElement("PlanPrice"), symbol, "UI",
				true, "Contains");
		if (addOn) {
			Utility.validateUIDataField("verifyCurrency", "SelectedFirstAddOnPriceSymbol",
					getTextOfElement("SelectedFirstAddOnPrice"), symbol, "UI", true, "Contains");
		}
		ExtentLogger.pass("Symbol is: " + symbol, true);
		return this;
	}

	/**
	 * This method is used to select the jurisdiction from dropdown
	 * 
	 * @created by Ishwarya
	 */
	public PPPage selectJurisdiction(String juridiction) {

		waitUntilElementVisible("JurisdictionDropdown");
		clickOnElement("JurisdictionDropdown");
		ExtentLogger.pass("Clicked on Jurisdiction Drop Down", true);
		jurisdictionDropdownCount = getSizeOfElement("JurisdictionDropdownCount");
		if (juridiction == null || juridiction.trim().equalsIgnoreCase("")) {
			int random = Utility.generateNumberFromRange(1, jurisdictionDropdownCount);
			randomSelectedjuridictionName = getTextOfParameterisedXpathElement("JurisdictionOptionByIndex",
					"PLACEHOLDER", String.valueOf(random));
			clickOnTheParameterisedXpath("JurisdictionDropdownOption", "PLACEHOLDER", randomSelectedjuridictionName);
		} else {
			clickOnTheParameterisedXpath("JurisdictionDropdownOption", "PLACEHOLDER", juridiction);
		}
		ExtentLogger.pass("Selected Jurisdiction: " + juridiction, true);
		return this;
	}

	/**
	 * This method is used to validate the jurisdiction selected
	 * 
	 * @created by Ishwarya
	 */
	public PPPage validateJurisdiction(String juridiction) {

		ExtentLogger.info("Validating Jurisdiction");
		scrollToLocation(0, -400);
		Utility.validateUIDataField("validateJurisdiction", "JurisdictionDropdownCount",
				String.valueOf(jurisdictionDropdownCount),
				ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_COUNT"), "UI", true, "Equals");
		Utility.validateUIDataField("validateJurisdiction", "JuridictionHeading",
				getTextOfElement("JurisdictionHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_HEADING"), "UI", true, "Equals");
		if (juridiction == null || juridiction.trim().equalsIgnoreCase("")) {
			Utility.validateUIDataField("validateJurisdiction", "SelectedValueInSectorDropdownTextShouldNotBeSelectOne",
					getTextOfElement("SelectedValueInJurisdictionDropdown"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_DEFAULT_OPTION"), "UI", true,
					"NotEquals");
			Utility.validateUIDataField("validateJurisdiction", "SelectedValueInSectorDropdown",
					getTextOfElement("SelectedValueInJurisdictionDropdown"), randomSelectedjuridictionName, "UI", true,
					"Equals");
		} else {
			Utility.validateUIDataField("validateJurisdiction", "SelectedValueInSectorDropdown",
					getTextOfElement("SelectedValueInJurisdictionDropdown"), juridiction, "UI", true, "Equals");
		}
		return this;
	}

	/**
	 * This method is used to validate the default sector in the sector dropdown
	 * 
	 * @created by Ishwarya
	 */
	public PPPage validateDefaultSector() {

		ExtentLogger.info("Validating Default Sector");
		String url = getURL();
		if (url.contains("practical-law")) {

			Utility.validateUIDataField("ValidatePreSelectedSector", "DefaultSector",
					getTextOfElement("SelectedValueInSectorDropdown"),
					ReadJson.getStringValue(ppPageDataFileName, "SECTOR_GC"), "UI", true, "Equals");
		} else {
			Utility.validateUIDataField("ValidatePreSelectedSector", "DefaultSector",
					getTextOfElement("SelectedValueInSectorDropdown"),
					ReadJson.getStringValue(ppPageDataFileName, "SECTOR_LF"), "UI", true, "Equals");
		}
		for (int sectorDropdownOptionNumber = 1; sectorDropdownOptionNumber <= getSizeOfElement(
				"SectorDropdownOptionCount"); sectorDropdownOptionNumber++) {
			Utility.validateUIDataField("validateDefaultSector",
					"SectorDropdownOption " + sectorDropdownOptionNumber + " Text",
					getTextOfParameterisedXpathElement("SectorDropdownOptionText", "PLACEHOLDER",
							String.valueOf(sectorDropdownOptionNumber)),
					ReadJson.getStringValue(ppPageDataFileName, "SECTOR_OPTION_TEXT_" + sectorDropdownOptionNumber),
					"UI", true, "Equals");
			Utility.validateUIDataField("validateDefaultSector",
					"SectorDropdownOption " + sectorDropdownOptionNumber + " AdditionalText",
					getTextOfParameterisedXpathElement("SectorDropdownOptionAdditionalText", "PLACEHOLDER",
							String.valueOf(sectorDropdownOptionNumber)),
					ReadJson.getStringValue(ppPageDataFileName,
							"SECTOR_OPTION_ADDITIONAL_TEXT_" + sectorDropdownOptionNumber),
					"UI", true, "Equals");
		}
		return this;
	}

	/**
	 * This method is used to validate the FAQ section
	 * 
	 * @created by Ishwarya
	 */
	public PPPage validateFAQSection() {

		ExtentLogger.info("Validating FAQ Section");
		scrollToParticularElement("FAQHeading");
		Utility.validateUIDataField("validateFAQSection", "FAQHeading", getTextOfElement("FAQHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "FAQ_HEADING"), "UI", true, "Equals");
		for (int faqSectionNumber = 1; faqSectionNumber <= getSizeOfElement("FAQListCount"); faqSectionNumber++) {
			Utility.validateUIDataField("validateFAQSection", "FAQQuestion" + faqSectionNumber,
					getTextOfParameterisedXpathElement("FAQQuestion", "PLACEHOLDER", String.valueOf(faqSectionNumber)),
					ReadJson.getStringValue(ppPageDataFileName, "FAQ_QUESTION_" + faqSectionNumber), "UI", true,
					"Equals");
			clickOnTheParameterisedXpath("FAQQuestion", "PLACEHOLDER", String.valueOf(faqSectionNumber));
			waitUntilElementVisible("FAQAnswer");
			Utility.validateUIDataField("validateFAQSection", "FAQAnswer" + faqSectionNumber,
					getTextOfElement("FAQAnswer"),
					ReadJson.getStringValue(ppPageDataFileName, "FAQ_ANSWER_" + faqSectionNumber), "UI", true,
					"Equals");
			clickOnTheParameterisedXpath("FAQQuestion", "PLACEHOLDER", String.valueOf(faqSectionNumber));
			waitUntilElementNotVisible("FAQAnswer");
		}
		return this;
	}

	/**
	 * This method is used to validate PPpage footer section
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validatePPPageFooterSection() {

		ExtentLogger.info("Validating PPP Page Footer Section");
		scrollToParticularElement("FooterNavigationsHeadingsCount");
		for (int footerNavigationsHeaderNumber = 1; footerNavigationsHeaderNumber <= getSizeOfElement(
				"FooterNavigationsHeadingsCount"); footerNavigationsHeaderNumber++) {
			Utility.validateUIDataField("validatePPPageFooterSection",
					"FooterNavigationsHeading" + footerNavigationsHeaderNumber,
					getTextOfParameterisedXpathElement("FooterNavigationsHeadings", "PLACEHOLDER",
							String.valueOf(footerNavigationsHeaderNumber)),
					ReadJson.getStringValue(ppPageDataFileName,
							"FOOTER_NAV_HEADING_" + (footerNavigationsHeaderNumber)),
					"UI", true, "Equals");
			for (int footerNavigationLinkNumber = 1; footerNavigationLinkNumber <= getSizeOfTheParameterisedXpath(
					"FooterNavigationsListCount", "PLACEHOLDER",
					String.valueOf(footerNavigationsHeaderNumber)); footerNavigationLinkNumber++) {
				Utility.validateUIDataField("validatePPPageFooterSection",
						"FooterNavigationsItem" + footerNavigationLinkNumber + "UnderHeading"
								+ footerNavigationsHeaderNumber,
						getTextOfDynamicXpathElement("FooterNavigationsListItems",
								String.valueOf(footerNavigationsHeaderNumber),
								String.valueOf(footerNavigationLinkNumber)),
						ReadJson.getStringValue(ppPageDataFileName, "FOOTER_NAV_HEADER_" + footerNavigationsHeaderNumber
								+ "_ITEM_" + footerNavigationLinkNumber),
						"UI", true, "Equals");
			}
		}
		return this;
	}

	/**
	 * This method is used to validate the jurisdiction is present for the selected
	 * sectors in west law edge and west law classic
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateJurisdictionIsPresent(String sector) {

		ExtentLogger.info("Validating Jurisdiction Is Present");
		clickOnElement("SectorDropdown");
		String url = getURL();

		for (int sectorDropdownNumber = 1; sectorDropdownNumber <= getSizeOfElement(
				"SectorDropdownOptionCount"); sectorDropdownNumber++) {
			clickOnTheParameterisedXpath("SectorDropdownOptionText", "PLACEHOLDER",
					String.valueOf(sectorDropdownNumber));

			if (url.contains("practical-law") || sector.contains("General")) {
				Utility.validateUIDataField("validateJurisdictionIsPresent", "IsJurisdictionNotPresent",
						isListEmpty("JurisdictionDropdown"), true, "UI", true, "Equals");
			} else {
				Utility.validateUIDataField("validateJurisdictionIsPresent", "IsJurisdictionPresent",
						!isListEmpty("JurisdictionDropdown"), true, "UI", true, "Equals");
				Utility.validateUIDataField("validateJurisdictionIsPresent", "JurisdictionDropdownDefaultOption",
						getTextOfElement("JurisdictionDropdown"),
						ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_DEFAULT"), "UI", true, "Equals");
			}
		}
		return this;
	}

	/**
	 * This method is used to validate the error message if good plan is selected
	 * for law firm or any plan is selected for government in west law edge and west
	 * law classic
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateJurisdictionErrorMessage(String sector, String errorMessageKey) {

		ExtentLogger.info("Validating Jurisdiction Error Message");
		if (sector.equalsIgnoreCase("Law firm")) {
			selectPlan("Good Plan");
			Utility.validateUIDataField("validateJurisdictionErrorMessage",
					"JurisdictionErrorMessageForGoodPlanLawFirm", getTextOfElement("JurisdictionErrorMessage"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_ERROR"), "UI", true, "Equals");
		} else {
			selectPlan("Better Plan");
			Utility.validateUIDataField("validateJurisdictionErrorMessage",
					"JurisdictionErrorMessageForBetterPlanGovernment", getTextOfElement("JurisdictionErrorMessage"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_ERROR"), "UI", true, "Equals");
			selectPlan("Best Plan");
			Utility.validateUIDataField("validateJurisdictionErrorMessage",
					"JurisdictionErrorMessageForBestPlanGovernment", getTextOfElement("JurisdictionErrorMessage"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_ERROR"), "UI", true, "Equals");
			selectPlan("Good Plan");
			Utility.validateUIDataField("validateJurisdictionErrorMessage",
					"JurisdictionErrorMessageForGoodPlanGovernment", getTextOfElement("JurisdictionErrorMessage"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_ERROR"), "UI", true, "Equals");
		}
		return this;
	}

	public PPPage validateAddonIsPresent() {

		ExtentLogger.info("Validating Addon Is Present");
		selectSector("General Counsel");
		selectPlan("Good Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonNotPresentForGeneralCounselGoodPlan",
				isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");
		selectSector("General Counsel");
		selectPlan("Better Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonNotPresentForGeneralCounselBetterPlan",
				isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");
		selectSector("General Counsel");
		selectPlan("Best Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonNotPresentForGeneralCounselBestPlan",
				isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");
		selectSector("Government");
		selectPlan("Good Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonPresentForGovernmentGoodPlan",
				!isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");
		selectSector("Government");
		selectPlan("Better Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonPresentForGovernmentBetterPlan",
				!isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");
		selectSector("Government");
		selectPlan("Best Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonNotPresentForGovernmentBestPlan",
				isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");

		selectSector("Law Firm");
		selectPlan("Good Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonPresentForLawFirmGoodPlan",
				!isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");
		selectSector("Law Firm");
		selectPlan("Better Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonPresentForLawFirmBetterPlan",
				!isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");
		selectSector("Law Firm");
		selectPlan("Best Plan");
		Utility.validateUIDataField("validateAddonIsPresent", "IsAddonPresentForLawFirmBestPlan",
				!isListEmpty("SelectFirstAddOn"), true, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to validate the Gen AI feature in the plan table section
	 * method for PL General Counsel
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateGenAIFeatureInTable() {

		ExtentLogger.info("Validating Gen AI Feature In Table");
		scrollToParticularElement("DefaultPlanGenAIText");
		Utility.validateUIDataField("validateDefaultPlan", "DefaultPlanGenAIText",
				getTextOfElement("DefaultPlanGenAIText"), ReadJson.getStringValue(ppPageDataFileName, "GEN_AI_TEXT"),
				"UI", true, "Equals");

		clickOnElement("GenAIHeadingInTable");
		scrollToParticularElement("GenAILabelInTable");
		Utility.validateUIDataField("validateGenAIFeatureInTable", "GenAIHeadingInTable",
				getTextOfElement("GenAIHeadingInTable"),
				ReadJson.getStringValue(ppPageDataFileName, "GEN_AI_FEATURE_HEADING"), "UI", true, "Contains");
		Utility.validateUIDataField("validateGenAIFeatureInTable", "GenAILabelInTable",
				getTextOfElement("GenAILabelInTable"),
				ReadJson.getStringValue(ppPageDataFileName, "GEN_AI_FEATURE_LABEL"), "UI", true, "Equals");
		Utility.validateUIDataField("validateGenAIFeatureInTable", "GenAIPlatformCategoryInTable",
				getTextOfElement("GenAIPlatformCategoryInTable"),
				ReadJson.getStringValue(ppPageDataFileName, "GEN_AI_PLATFORM_CATEGORY"), "UI", true, "Equals");
		// BELOW CODE IS NOT IN TC JUST ADDITIONAL VALIDATIONS
//		clickOnElement("GenAIPlatformCategoryInTable");
//		waitUntilElementVisible("FeatureDescriptionModuleHeading");
//		Utility.validateUIDataField("validateGenAIFeatureInTable", "FeatureDescriptionModuleHeading",
//				getTextOfElement("FeatureDescriptionModuleHeading"),
//				ReadJson.getStringValue(ppPageDataFileName, "GEN_AI_FEATURE_MODULE_HEADING"), "UI", true, "Equals");
//		Utility.validateUIDataField("validateGenAIFeatureInTable", "FeatureDescriptionModuleSubheading",
//				getTextOfElement("FeatureDescriptionModuleSubheading"),
//				ReadJson.getStringValue(ppPageDataFileName, "GEN_AI_FEATURE_MODULE_SUBHEADING"), "UI", true, "Equals");
//		Utility.validateUIDataField("validateGenAIFeatureInTable", "FeatureDescriptionModuleContent",
//				getTextOfElement("FeatureDescriptionModuleContent"),
//				ReadJson.getStringValue(ppPageDataFileName, "GEN_AI_FEATURE_MODULE_BODY"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to validate View Price Matrix method for PPPpage
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage valiadateViewPriceMatrix(String jurisdiction, String scetor, String Attorney) {

		ExtentLogger.info("Validating View Price Matrix");
		String plansummary = getTextOfElement("PlanSummaryPanelHeadingWEOrWC").split("-")[0].trim();
		String priceMartixHeader = plansummary + ", " + jurisdiction + ", Enterprise access, " + scetor;
		String subHeading = "Pricing is based on " + Attorney + " users";
		clickOnElement("ViewPriceMatrix");
		waitUntilElementVisible("PriceMatrixHeading");
		Utility.validateUIDataField("valiadateViewPriceMatrix", "PriceMatrixHeading",
				getTextOfElement("PriceMatrixHeading"), priceMartixHeader, "UI", true, "Equals");
		Utility.validateUIDataField("valiadateViewPriceMatrix", "PriceMatrixSubHeading",
				getTextOfElement("PriceMatrixSubHeading"), subHeading, "UI", true, "Equals");
		scrollToParticularElement("SubscriptionsAnnualPrices");
		Utility.validateUIDataField("valiadateViewPriceMatrix", "SubscriptionsAnnualPrices",
				getTextOfElement("SubscriptionsAnnualPrices"),
				ReadJson.getStringValue(ppPageDataFileName, "PRICE_MATRIX_CONTENT"), "UI", true, "Equals");
		for (int i = 1; i <= getSizeOfElement("YearPlan"); i++) {
			if (i == 1) {
				Utility.validateUIDataField("valiadateViewPriceMatrix", "LiableForThiredYearPlan",
						getTextOfParameterisedXpathElement("YearPlanLiable", "PLACEHOLDER", String.valueOf(i)),
						ReadJson.getStringValue(ppPageDataFileName, "YEAR_PLANLiable"), "UI", true, "Equals");
				Utility.validateUIDataField("valiadateViewPriceMatrix", "ThiredYearPlan",
						getTextOfParameterisedXpathElement("PlanNoOfYears", "PLACEHOLDER", String.valueOf(i)),
						ReadJson.getStringValue(ppPageDataFileName, "Third_YEAR"), "UI", true, "Equals");
			} else if (i == 2) {
				Utility.validateUIDataField("valiadateViewPriceMatrix", "SecondYearPlan",
						getTextOfParameterisedXpathElement("PlanNoOfYears", "PLACEHOLDER", String.valueOf(i)),
						ReadJson.getStringValue(ppPageDataFileName, "Second_YEAR"), "UI", true, "Equals");

			} else if (i == 3) {
				Utility.validateUIDataField("valiadateViewPriceMatrix", "FirstYearPlan",
						getTextOfParameterisedXpathElement("PlanNoOfYears", "PLACEHOLDER", String.valueOf(i)),
						ReadJson.getStringValue(ppPageDataFileName, "First_YEAR"), "UI", true, "Equals");
			}
		}
		clickOnElement("ClosePriceMatrixTable");
		return this;
	}

	/**
	 * This method is used to validate the error message if good plan is selected
	 * for law firm or any plan is selected for government in west law edge and west
	 * law classic
	 *
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage validateJurisdictionErrorMessage(String sector) {

		ExtentLogger.info("Validating Jurisdiction Error Message");
		if (sector.equalsIgnoreCase("Law firm")) {
			selectPlanValidation("Good Plan");
			Utility.validateUIDataField("validateJurisdictionErrorMessage",
					"JurisdictionErrorMessageForGoodPlanLawFirm", getTextOfElement("JurisdictionErrorMessage"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_ERROR"), "UI", true, "Equals");
		} else {
			selectPlanValidation("Better Plan");
			Utility.validateUIDataField("validateJurisdictionErrorMessage",
					"JurisdictionErrorMessageForBetterPlanGovernment", getTextOfElement("JurisdictionErrorMessage"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_ERROR"), "UI", true, "Equals");
			selectPlanValidation("Best Plan");
			Utility.validateUIDataField("validateJurisdictionErrorMessage",
					"JurisdictionErrorMessageForBestPlanGovernment", getTextOfElement("JurisdictionErrorMessage"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_ERROR"), "UI", true, "Equals");
			selectPlanValidation("Good Plan");
			Utility.validateUIDataField("validateJurisdictionErrorMessage",
					"JurisdictionErrorMessageForGoodPlanGovernment", getTextOfElement("JurisdictionErrorMessage"),
					ReadJson.getStringValue(ppPageDataFileName, "JURISDICTION_ERROR"), "UI", true, "Equals");
		}
		return this;
	}

	/**
	 * This method is used to validate the error message if good plan is selected
	 * for law firm or any plan is selected for government in west law edge and west
	 * law classic
	 *
	 * @return
	 * @created by Ishwarya
	 */
	public PPPage selectMultinatonalPlan() {

		ExtentLogger.info("Selecting Multinational Plan");
		waitUntilElementVisible("MultinationalPlanCheckBox");
		clickOnElement("MultinationalPlanCheckBox");
		// validate the MultinationalPlanCheckBoxLabel
		Utility.validateUIDataField("selectMultinatonalPlan", "MultinationalPlanCheckBoxLabel",
				getTextOfElement("MultinationalPlanCheckBoxLabel"),
				ReadJson.getStringValue(ppPageDataFileName, "MULTINATIONAL_PLAN_CHECKBOX_LABEL"), "UI", true, "Equals");
		waitForFixedTime(3);
		for (int i = 1; i <= getSizeOfElement("planerHeader"); i++) {
			if (i == 1) {
				Utility.validateUIDataField("selectMultinatonalPlan", "planerHeader" + i,
						getTextOfParameterisedXpathElement("planerHeaderLiable", "PLACEHOLDER", String.valueOf(i)),
						ReadJson.getStringValue(ppPageDataFileName, "PLANER_HEADER_" + i), "UI", true, "Equals");
			} else if (i == 2) {
				Utility.validateUIDataField("selectMultinatonalPlan", "planerHeader" + i,
						getTextOfParameterisedXpathElement("planerHeaderLiable", "PLACEHOLDER", String.valueOf(i)),
						ReadJson.getStringValue(ppPageDataFileName, "PLANER_HEADER_" + i), "UI", true, "Equals");
			} else if (i == 3) {
				Utility.validateUIDataField("selectMultinatonalPlan", "planerHeader" + i,
						getTextOfParameterisedXpathElement("planerHeaderLiable", "PLACEHOLDER", String.valueOf(i)),
						ReadJson.getStringValue(ppPageDataFileName, "PLANER_HEADER_" + i), "UI", true, "Equals");
			}
		}
		return this;
	}

	/**
	 * This method is used to validate the Header of CoCounsel Core Plans and
	 * Pricing
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage verifyCoCounselCorePage() {

		waitForPageLoad();
		ExtentLogger.info("CoCounsel Core Plans and Pricing" + get_Title());
		waitUntilElementVisible("CoCounselCoreHeader");
		Utility.validateUIDataField("verifyCoCounselCorePage", "CoCounselCorePageTitle", get_Title(),
				ReadJson.getStringValue(ppPageDataFileName, "CoCounse_PAGE_HEADING"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method is used to validate the Total price for CoCounsel Core Plans and
	 * Pricing
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage planDetailCCC(String duration) {

		ExtentLogger.info("Validating Plan Detail for CoCounsel Core Plans and Pricing");
		waitForFixedTime(2);
		scrollToParticularElement("planeDetailHeading");
		Utility.validateUIDataField("planeDetailCCC", "planeDetailHeading", getTextOfElement("planeDetailHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "Plane_Detail_HEADING"), "UI", true, "Equals");
		totalPriceInString = "";
		if (duration.contains("1 year")) {

			String price = getTextOfElement("Price1Year");
			String price1 = price.replaceAll("[^0-9.]", "");
			double priceValue = Double.parseDouble(price1);
// Format the price value to two decimal places without the dollar sign
			NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
			numberFormat.setMinimumFractionDigits(2);
			numberFormat.setMaximumFractionDigits(2);
			totalPriceInString = numberFormat.format(priceValue);
			ExtentLogger.info("Price for 1 year is: " + totalPriceInString);
		} else {
			String totalprice = getTextOfElement("PricePerYear");
			String totalprice1 = totalprice.replaceAll("[^0-9.]", "");
			totalPriceInString = String.valueOf(Double.parseDouble(totalprice1));
			ExtentLogger.info("Total Price is: " + totalPriceInString);
		}
		return this;
	}

	/**
	 * This method is used to validate the Number of Attorneys for CoCounsel Core
	 * Plans and Pricing
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage validateActualNumberOfAttorneysCCC(String attorney, String sector) {

		ExtentLogger.info("Validating Actual Number of Attorneys for CoCounsel Core Plans and Pricing");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneyHeading",
				getTextOfElement("NumberOfAttorneysHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_HEADING"), "UI", true, "Equals");
		if (sector.equals("Law firm")) {
			Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneySubHeading",
					getTextOfElement("NumberOfAttorneysSubHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_SUBHEADING_FIRM_COCOUNSEL"), "UI", true,
					"Equals");
		} else if (sector.equals("Government")) {
			Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneySubHeading",
					getTextOfElement("NumberOfSeatsSubheading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_SUBHEADING_Govt_CCC"), "UI", true,
					"Equals");
		} else if (sector.equals("General Counsel")) {
			Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneySubHeading",
					getTextOfElement("NumberOfAttorneysSubHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_SUBHEADING_GC_CCC"), "UI", true,
					"Equals");
		}
		Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorney",
				getAttributeValueOfGivenAttribute("FirstNumberOfAttorneysTextBox", "value"), attorney, "UI", true,
				"Equals");
		return this;
	}
	/**
	 * This method is used to validate the Number of Attorneys for CoCounsel Core
	 * Plans and Pricing
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage validateActualNumberOfSeatsCCC(String attorney, String sector) {

		ExtentLogger.info("Validating Actual Number of Attorneys for CoCounsel Core Plans and Pricing");
		waitForFixedTime(2);
		Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneyHeading",
				getTextOfElement("NumberOfAttorneysHeading"),
				ReadJson.getStringValue(ppPageDataFileName, "NO_OF_SEATS_HEADING"), "UI", true, "Equals");
		if (sector.equals("Law firm")) {
			Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneySubHeading",
					getTextOfElement("NumberOfAttorneysSubHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_SUBHEADING_FIRM_COCOUNSEL"), "UI", true,
					"Equals");
		} else if (sector.equals("Government")) {
			Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneySubHeading",
					getTextOfElement("NumberOfSeatsSubheading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_SUBHEADING_Govt_CCC"), "UI", true,
					"Equals");
		} else if (sector.equals("General Counsel")) {
			Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorneySubHeading",
					getTextOfElement("NumberOfAttorneysSubHeading"),
					ReadJson.getStringValue(ppPageDataFileName, "NO_OF_ATTORNEY_SUBHEADING_GC_CCC"), "UI", true,
					"Equals");
		}
		Utility.validateUIDataField("validateActualNumberOfAttorneys", "NumberOfAttorney",
				getAttributeValueOfGivenAttribute("FirstNumberOfSeatsTextBox", "value"), attorney, "UI", true,
				"Equals");
		return this;
	}

	/**
	 * This method is used to validate the Number of Attorneys for CoCounsel Core
	 * Plans and Pricing
	 *
	 * @return
	 * @created by Yunus
	 */
	public PPPage validateShareLink() throws IOException, UnsupportedFlavorException {

		waitForFixedTime(2);
		scrollToParticularElement("ShareLink");
		clickOnElement("ShareLink");
		waitForFixedTime(2);// waiting for 2 sec to get the link
		clickOnElement("CopiedLink");
		waitForFixedTime(2);
		openNewWindow();
		switchToWindow(1);
		openLinkInNewTab();
		verifyCoCounselCorePage();
		switchToWindow(0);
		waitForFixedTime(4);
		switchToDefaultWindow();
		return this;
	}
}