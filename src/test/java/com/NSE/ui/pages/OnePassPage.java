package com.NSE.ui.pages;

import com.NSE.commons.DcpBasePage;
import com.NSE.commons.Utility;
import org.openqa.selenium.support.PageFactory;

public class OnePassPage extends DcpBasePage {
	public OnePassPage() {
		super("locatorsDefinition/OnePassPage.json");
		PageFactory.initElements(getDriver(), this);

	}

	/**
	 * This method clicks on the SignIn button
	 *
	 * @created by Yunus
	 */
	public OnePassPage clickOnSignIn() {

		waitForPageLoad();
		waitUntilElementVisible("SignIn");
		clickOnElement("SignIn");
		Utility.logGenerator("clickOnSignIn", isListEmpty("SignIn"), "Clicked on SignIn button",
				"Can't clicked on SignIn button or Go to SignIn button is not displayed", true);

		return this;
	}

	/**
	 * This method to enter the user name
	 *
	 * @created by Yunus
	 */
	public OnePassPage userName(String userName) {

		waitForPageLoad();
		waitUntilElementVisible("UserName");
		sendKeysTotheElement("UserName", userName);
		Utility.logGenerator("userName", getTextOfElement("UserName") != null, "Entered the user name",
				"Can't entered the user name or UserName field is not displayed", true);
		return this;
	}

	/**
	 * This method to enter the password
	 *
	 * @created by Yunus
	 */
	public OnePassPage password(String password) {

		waitForPageLoad();
		waitUntilElementVisible("Password");
		sendKeysTotheElement("Password", password);
		Utility.logGenerator("password", getTextOfElement("Password") != null, "Entered the password",
				"Can't entered the password or Password field is not displayed", true);
		return this;
	}

	/**
	 * This method to click on the sign in button
	 *
	 * @created by Yunus
	 */
	public OnePassPage clickOnSignInButton() {

		waitForPageLoad();
		waitUntilElementVisible("LoginButton");
		clickOnElement("LoginButton");
		Utility.logGenerator("clickOnSignInButton", isListEmpty("LoginButton"), "Clicked on Sign-in button",
				"Can't clicked on Sign-in button or Sign-in button is not displayed", true);
		return this;
	}

	/**
	 * This method to click on the continue button
	 *
	 * @created by Yunus
	 * @modified by Ishwarya
	 */
	public OnePassPage clickoncontinue() {

		waitUntilElementVisible("ContinueToPaymentButtonAT");
		clickOnElement("ContinueToPaymentButtonAT");
		waitUntilElementNotVisible("ContinueToPaymentButtonAT");
		Utility.logGenerator("clickoncontinue", checkElementIfVisible("ContinueToPaymentButtonAT") == false,
				"Clicked on Continue to payment Button",
				"Can't clicked on Continue to payment Button or Continue to payment Button is not displayed", true);
		return this;
	}

	/**
	 * This method to click on the continue without sign in button
	 *
	 * @created by Yunus
	 * @modified by Ishwarya
	 */
	public OnePassPage clickOnContinueWithOutSignIn() {

		waitUntilElementVisible("ContinueWithOutSignIn");
		clickOnElement("ContinueWithOutSignIn");
		Utility.logGenerator("clickOnContinueWithOutSignIn", isListEmpty("ContinueWithOutSignIn"),
				"Clicked on Continue With-Out Sign In to payment Button",
				"Can't clicked on Continue With-Out Sign In to payment Button or Continue With-Out Sign In to payment Button is not displayed",
				true);
		return this;
	}
}