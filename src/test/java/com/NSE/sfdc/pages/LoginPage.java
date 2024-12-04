package com.NSE.sfdc.pages;

import org.openqa.selenium.support.PageFactory;

import com.NSE.commons.DcpBasePage;
import com.tr.commons.extentListeners.ExtentLogger;

public class LoginPage extends DcpBasePage {

	public LoginPage() {
		super("locatorsDefinitionSFDC/LoginPage.json");
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * This method is to click on the UserName TextBox
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public LoginPage clickOnUserNameTextBox() {

		waitForPageLoad();
		logger.get().info("Navigated to the Salesforce Login Page");
		clickOnElement("UserName");
		return this;
	}

	/**
	 * This method is to enter the UserName
	 * 
	 * @param String username
	 * @return
	 * @created by Ishwarya
	 */
	public LoginPage enterUserName(String username) {

		sendKeysTotheElement("UserName", username);
		ExtentLogger.pass("Username entered successfully", true);
		return this;
	}

	/**
	 * This method is to click on the Password TextBox
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public LoginPage clickOnPasswordTextBox() {

		clickOnElement("Password");
		ExtentLogger.info("Clicked on Password TextBox");
		return this;
	}

	/**
	 * This method is to enter the password
	 * 
	 * @param String password
	 * @return
	 * @created by Ishwarya
	 */
	public LoginPage enterPassword(String password) {

		sendKeysTotheElement("Password", password);
		ExtentLogger.pass("Password entered successfully", true);
		return this;
	}

	/**
	 * This method is to click on the login button
	 * 
	 * @return
	 * @created by Ishwarya
	 */
	public LoginPage clickOnLoginButton() {

		clickOnElement("LoginButton");
		ExtentLogger.pass("Clicked on Login Button and Navigated to the Salesforce HomePage", true);
		waitForPageLoad();
		return this;
	}
}