package com.NSE.ui.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.ExcelReader;
import com.NSE.commons.Utility;
import com.tr.commons.ReadJson;
import com.tr.commons.extentListeners.ExtentLogger;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class OrderConfirmationPage extends DcpBasePage {

	public static String orderNumber = "";

	// Variables to store JSON data
	static String orderConfirmationPageDataFileName = "OrderConfirmationPageData.json";

	public OrderConfirmationPage() {
		super("locatorsDefinition/OrderConfirmationPage.json");
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method to verify if the user has landed on the Order Confirmation page
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public OrderConfirmationPage verifyLandedOnOrderConfirmationPage() {

		ExtentLogger.info("Verifying if the user has landed on the Order Confirmation page");
		waitForPageLoad();
		waitUntilElementVisible("OrderDetailsHeading");
		ExtentLogger.pass("Page Title is: " + get_Title(), true);
		Utility.validateUIDataField("verifyLandedOnOrderConfirmationPage", "OrderConfirmationPageTitle", get_Title(),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "OC_PAGE_TITLE"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method to verify the first name in the order confirmation page
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public OrderConfirmationPage verifyFirstNameInOrderConfirmation(String coPageFirstName) {

		ExtentLogger.info("Verifying the data in the Order Confirmation page");
		waitUntilElementVisible("OrderConfirmationMessage");
		Utility.validateUIDataField("verifyFirstNameInOrderConfirmation", "OrderConfirmationMessage",
				getTextOfElement("OrderConfirmationMessage"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "THANK_YOU_MESSAGE").replace("NAME",
						coPageFirstName),
				"UI", true, "Equals");
		Utility.validateUIDataField("verifyFirstNameInOrderConfirmation", "OrderConfirmationNote",
				getTextOfElement("OrderConfirmationNote"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CONFIRMATION_NOTE"), "UI", true, "Equals");
		Utility.validateUIDataField("verifyFirstNameInOrderConfirmation", "IsOrderConfirmationIconPresent",
				!isListEmpty("OrderConfirmationIcon"), true, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method to verify the order details in the order confirmation page, if
	 * canada estore pass boolean true in parameter
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public OrderConfirmationPage getOrderNumber(Boolean... isCanadaEstore) {

		waitUntilElementVisible("OrderNumber");
		String quoteFromUI = getTextOfElement("OrderNumber");
		orderNumber = Utility.getStingUsingPattern(quoteFromUI, "Q-.*");
		log.info("Order Number is: " + orderNumber);
		ExtentLogger.info("Order Number is: " + orderNumber);
		Utility.validateUIDataField("getOrderNumber", "FormatOfOrderNumberTextInSecondSectionIsCorrect",
				getTextOfElement("OrderNumber"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "ORDER_NUMBER_TEXT"), "UI", true,
				"Contains");
		if (isCanadaEstore.length > 0 && isCanadaEstore[0] == true) {
			Utility.validateUIDataField("getOrderNumber", "CanadaOrderReviewHeading",
					getTextOfElement("CanadaOrderReviewHeading"),
					ReadJson.getStringValue(orderConfirmationPageDataFileName, "CANADA_ORDER_REVIEW_HEADING"), "UI",
					true, "Contains");
		}
		return this;
	}

	/**
	 * This method is used to enter the order number in the excel sheet
	 * 
	 * @return
	 * @created by saleem
	 */
	public OrderConfirmationPage enterMultipleValuesInExcel(String excelFilePath, String testDataSheetName,
			String testcasename, String... columnNames) {

		Map<String, String> columnValueMap = gatherData(columnNames);
		ExcelReader.writeDataToExcelBasedOnTestName(excelFilePath, testDataSheetName, testcasename, columnValueMap);
		ExtentLogger.info("Entered details in excel");
		return this;
	}

	/**
	 * This method to verify the currency details in the order confirmation page
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public OrderConfirmationPage verifyCurrency(String currency, String symbol) {

		ExtentLogger.info("Verifying the currency in the Order Confirmation page");
		waitUntilElementVisible("OrderDetailsHeading");
		Utility.validateUIDataField("verifyCurrency", "TotalCurrencyCode", getTextOfElement("TotalCurrency"), currency,
				"UI", true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "SubTotalCurrencyCode", getTextOfElement("SubTotalCurrency"),
				currency, "UI", true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "SubTotalSymbol", getTextOfElement("SubTotal"), symbol, "UI",
				true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "EstimatedTaxSymbol", getTextOfElement("EstimatedTax"), symbol,
				"UI", true, "Contains");
		Utility.validateUIDataField("verifyCurrency", "EstimatedTotalSymbol", getTextOfElement("EstimatedTotal"),
				symbol, "UI", true, "Contains");
		ExtentLogger.info("Currency are displayed successfully");
		return this;
	}

	/**
	 * This method to verify the price details in the order confirmation page
	 * 
	 * @return
	 * @modified by Ishwarya Yunus
	 */
	public OrderConfirmationPage verifyPrice(String ppPageTotalPriceInString, String coPageEstimatedTaxInString,
			String coPageEstimatedTotalInString, boolean... isCCCFlow) {

		ExtentLogger.info("Verifying the prices in the Order Confirmation page");
		waitUntilElementVisible("SubTotal");
		String SubTotal = getTextOfElement("SubTotal");
		if (isCCCFlow.length > 0 && isCCCFlow[0] == true) {
			SubTotal = SubTotal.replace("$", "");
		}
		Utility.validateUIDataField("verifyPrice", "SubTotal", SubTotal, ppPageTotalPriceInString, "UI_UI", true,
				"Equals", "OrderConfirmationPageSubtotal", "PPPPageSubTotal");
		Utility.validateUIDataField("verifyPrice", "EstimatedTax", getTextOfElement("EstimatedTax"),
				coPageEstimatedTaxInString, "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "EstimatedTotal", getTextOfElement("EstimatedTotal"),
				coPageEstimatedTotalInString, "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "EstimatedTotalPerMonthText",
				getTextOfElement("EstimatedTotalMonthText"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "PER_MONTH_TEXT"), "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "DueDateText", getTextOfElement("DueDateText"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "DUE_DATE_TEXT"), "UI", true, "Contains");
		Utility.validateUIDataField("verifyPrice", "DueDateToolTip", getTextOfElement("DueDateToolTip"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "DUE_DATE_TOOLTIP"), "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "EstimatedTaxNote", getTextOfElement("EstimatedTaxNote"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "ESTIMATED_TAX_NOTE"), "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "SubTotalHeading", getTextOfElement("SubTotalHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "SUBTOTAL_HEADING"), "UI", true, "Contains");
		Utility.validateUIDataField("verifyPrice", "EstimatedTaxHeading", getTextOfElement("EstimatedTaxHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "ESTIMATED_TAX_HEADING"), "UI", true,
				"Contains");
		Utility.validateUIDataField("verifyPrice", "EstimatedTotalHeading", getTextOfElement("EstimatedTotalHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "ESTIMATED_TOTAL_HEADING"), "UI", true,
				"Contains");
		ExtentLogger.info("Prices are correct");
		return this;
	}

	/**
	 * This method to verify the price details in the order confirmation page
	 *
	 * @return
	 * @modified by Yunus
	 */
	public OrderConfirmationPage verifyPriceCCC(String ppPageTotalPriceInString, String coPageEstimatedTaxInString,
			String coPageEstimatedTotalInString) {

		ExtentLogger.info("Verifying the prices in the Order Confirmation page");
		waitUntilElementVisible("SubTotal");
		Utility.validateUIDataField("verifyPrice", "SubTotal", getTextOfElement("SubTotal"), ppPageTotalPriceInString,
				"UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "EstimatedTax", getTextOfElement("EstimatedTaxCCC"),
				coPageEstimatedTaxInString, "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "EstimatedTotal", getTextOfElement("EstimatedTotalCCC"),
				coPageEstimatedTotalInString, "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "EstimatedTotalPerMonthText",
				getTextOfElement("EstimatedTotalMonthText"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "PER_MONTH_TEXT"), "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "DueDateText", getTextOfElement("DueDateText"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "DUE_DATE_TEXT"), "UI", true, "Contains");
		Utility.validateUIDataField("verifyPrice", "DueDateToolTip", getTextOfElement("DueDateToolTip"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "DUE_DATE_TOOLTIP"), "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "EstimatedTaxNote", getTextOfElement("EstimatedTaxNote"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "ESTIMATED_TAX_NOTE"), "UI", true, "Equals");
		Utility.validateUIDataField("verifyPrice", "SubTotalHeading", getTextOfElement("SubTotalHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "SUBTOTAL_HEADING"), "UI", true, "Contains");
		Utility.validateUIDataField("verifyPrice", "EstimatedTaxHeading", getTextOfElement("EstimatedTaxHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "ESTIMATED_TAX_HEADING"), "UI", true,
				"Contains");
		Utility.validateUIDataField("verifyPrice", "EstimatedTotalHeading", getTextOfElement("EstimatedTotalHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "ESTIMATED_TOTAL_HEADING"), "UI", true,
				"Contains");
		ExtentLogger.info("Prices are correct");
		return this;
	}

	/**
	 * This method to validate the what is next section data
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public OrderConfirmationPage validateWhatIsNextSection() {

		ExtentLogger.info("Validating the What is next section in the Order Confirmation page");
		Utility.validateUIDataField("validateWhatIsNextSection", "WhatIsNextHeading",
				getTextOfElement("WhatIsNextHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "WHATS_NEXT_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateWhatIsNextSection", "WhatIsNextList1", getTextOfElement("WhatIsNextList1"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "WHATS_NEXT_LIST1"), "UI", true, "Equals");
		Utility.validateUIDataField("validateWhatIsNextSection", "WhatIsNextList2", getTextOfElement("WhatIsNextList2"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "WHATS_NEXT_LIST2"), "UI", true, "Equals");
		Utility.validateUIDataField("validateWhatIsNextSection", "WhatIsNextList3", getTextOfElement("WhatIsNextList3"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "WHATS_NEXT_LIST3"), "UI", true, "Equals");
		return this;
	}

	/**
	 * This method to validate the what is next section data for existing customer
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public OrderConfirmationPage validateWhatIsNextSectionExistingCustomer() {

		ExtentLogger.info("Validating the What is next section for existing customer in the Order Confirmation page");
		Utility.validateUIDataField("validateWhatIsNextSection", "WhatIsNextHeading",
				getTextOfElement("WhatIsNextHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "WHATS_NEXT_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateWhatIsNextSection", "WhatIsNextList1",
				getTextOfElement("WhatIsNextListExistingCustomer1"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "WHATS_NEXT_LIST1_EC"), "UI", true,
				"Equals");
		Utility.validateUIDataField("validateWhatIsNextSection", "WhatIsNextList2",
				getTextOfElement("WhatIsNextListExistingCustomer2"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "WHATS_NEXT_LIST2_EC"), "UI", true,
				"Equals");
		return this;
	}

	/**
	 * This method to validate the contact us banner
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public OrderConfirmationPage validateContactUsBanner() {

		ExtentLogger.info("Validating the Contact Us banner in the Order Confirmation page");
		Utility.validateUIDataField("validateContactUsBanner", "ContactUsHeading", getTextOfElement("ContactUsHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CONTACT_US_HEADING"), "UI", true, "Equals");

		Utility.validateUIDataField("validateContactUsBanner", "ContactAgentHeading",
				getTextOfElement("SpeakWithAgentHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CONTACT_AGENT_HEADING"), "UI", true,
				"Equals");
		Utility.validateUIDataField("validateContactUsBanner", "ContactAgentBody",
				getTextOfElement("SpeakWithAgentBody"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CONTACT_AGENT_BODY"), "UI", true, "Equals");
		Utility.validateUIDataField("validateContactUsBanner", "ContactAgentIconText",
				getTextOfElement("SpeakWithAgentIconText"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CONTACT_AGENT_ICON_TEXT"), "UI", true,
				"Equals");
		Utility.validateUIDataField("validateContactUsBanner", "IsContactAgentIconPresent",
				!isListEmpty("SpeakWithAgentIcon"), true, "UI", true, "Equals");

		Utility.validateUIDataField("validateContactUsBanner", "SendMessageHeading",
				getTextOfElement("SendMessageHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "MESSAGE_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateContactUsBanner", "SendMessageBody", getTextOfElement("SendMessageBody"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "MESSAGE_BODY"), "UI", true, "Equals");
		Utility.validateUIDataField("validateContactUsBanner", "SendMessageIconText",
				getTextOfElement("SendMessageIconText"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "MESSAGE_ICON_TEXT"), "UI", true, "Equals");
		Utility.validateUIDataField("validateContactUsBanner", "IsSendMessageIconPresent",
				!isListEmpty("SendMessageIcon"), true, "UI", true, "Equals");

		Utility.validateUIDataField("validateContactUsBanner", "ChatOnlineHeading",
				getTextOfElement("ChatOnlineHeading"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CHAT_HEADING"), "UI", true, "Equals");
		Utility.validateUIDataField("validateContactUsBanner", "ChatOnlineBody", getTextOfElement("ChatOnlineBody"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CHAT_BODY"), "UI", true, "Equals");
		Utility.validateUIDataField("validateContactUsBanner", "ChatOnlineIconText",
				getTextOfElement("ChatOnlineIconText"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CHAT_ICON_TEXT"), "UI", true, "Equals");
		Utility.validateUIDataField("validateContactUsBanner", "IsChatOnlineIconPresent",
				!isListEmpty("ChatOnlineIcon"), true, "UI", true, "Equals");

		return this;
	}

	public OrderConfirmationPage validateSurveyInCanadaEstore() {

		ExtentLogger.info("Validating the survey in Canada estore");
		Utility.validateUIDataField("validateSurveyInCanadaEstore", "CanadaSurveyLink",
				getTextOfElement("CanadaSurveyLink"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "CANADA_SURVEY_TEXT"), "UI", true, "Equals");

		clickOnElement("CanadaSurveyLink");
		// wait till a new window is opened
		waitForFixedTime(2);
		switchToNewWindow();
		waitUntilElementVisible("CanadaSurveyLogo");

		String[] questions = new String[] { "QUESTION_1", "QUESTION_2", "QUESTION_3" };
		for (int questionNumber = 0; questionNumber < questions.length; questionNumber++) {
			String question = ReadJson.getStringValue(orderConfirmationPageDataFileName, questions[questionNumber]);
			Utility.validateUIDataField("validateSurveyInCanadaEstore", "Question_" + (questionNumber + 1),
					getTextOfParameterisedXpathElement("CanadaSurveyQuestions", "PLACEHOLDER",
							String.valueOf(questionNumber + 1)),
					question, "UI", true, "Equals");
		}

		String[] mcqAnswers = new String[] { "ANS_1", "ANS_2", "ANS_3", "ANS_4", "ANS_5" };
		for (int questionNumber = 1; questionNumber <= 2; questionNumber++) {
			for (int ansOption = 0; ansOption < mcqAnswers.length; ansOption++) {
				String answer = ReadJson.getStringValue(orderConfirmationPageDataFileName,
						"Q_" + questionNumber + mcqAnswers[ansOption]);
				Utility.validateUIDataField("validateSurveyInCanadaEstore",
						"Question_" + questionNumber + "_AnswerOption_" + (ansOption + 1),
						getTextOfDynamicXpathElement("CanadaSurveyAnswers", String.valueOf(questionNumber),
								String.valueOf(ansOption + 1)),
						answer, "UI", true, "Equals");
			}
		}

		Utility.validateUIDataField("validateSurveyInCanadaEstore", "Question_3_AnswerOption_1",
				getTextOfParameterisedXpathElement("CanadaSurveyQ3Answers", "PLACEHOLDER", "1"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "Q3_ANS1"), "UI", true, "Equals");
		Utility.validateUIDataField("validateSurveyInCanadaEstore", "Question_3_AnswerOption_2",
				getTextOfParameterisedXpathElement("CanadaSurveyQ3Answers", "PLACEHOLDER", "2"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "Q3_ANS2"), "UI", true, "Equals");
		Utility.validateUIDataField("validateSurveyInCanadaEstore", "ISQuestion3TextBoxPresent",
				!isListEmpty("CanadaSurveyQ3TextBox"), true, "UI", true, "Equals");

		Utility.validateUIDataField("validateSurveyInCanadaEstore", "FeedbackQuestion",
				getTextOfElement("CanadaSurveyFeedbackQuestion"),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "FEEDBACK_QUESTION"), "UI", true, "Equals");
		Utility.validateUIDataField("validateSurveyInCanadaEstore", "ISCanadaSurveyFeedbackAnswerPresent",
				!isListEmpty("CanadaSurveyFeedbackAnswer"), true, "UI", true, "Equals");
		return this;
	}

	/**
	 * This method to fill the survey in Canada estore
	 * 
	 * @return
	 * @modified by Ishwarya
	 */
	public OrderConfirmationPage fillSurveyForCanadaEstore() {

		log.info("Filling the survey in Canada estore");
		switchToNewWindow();
		int randomOption = Utility.generateNumberFromRange(1, 5);
		// selecting random option for question 1
		clickOnDynamicXpath("CanadaSurveyAnswers", "1", String.valueOf(randomOption));
		// selecting random option for question 2
		clickOnDynamicXpath("CanadaSurveyAnswers", "2", String.valueOf(randomOption));
		// selecting no option for question 3
		clickOnTheParameterisedXpath("CanadaSurveyQ3Answers", "PLACEHOLDER", "1");
		clickOnElement("CanadaSurveyNextButton");
		waitForPageLoad();
		Utility.validateUIDataField("fillSurveyForCanadaEstore", "FeedBackSubmitMessage",
				getTextOfElement("FeedBackSubmitMessage").replace("\n", " "),
				ReadJson.getStringValue(orderConfirmationPageDataFileName, "FEEDBACK_SUBMITTED_MSG"), "UI", true,
				"Equals");
		closeNewWindow();
		return this;
	}
}