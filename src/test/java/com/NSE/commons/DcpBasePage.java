package com.NSE.commons;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.*;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import com.tr.commons.BaseClass;

public class DcpBasePage extends BaseClass {

	public WebDriver driver = getDriver();

	public DcpBasePage(String jsonPath) {

		String oldJsonPath = "";

		try {
			if (!oldJsonPath.equals(jsonPath)) {
				loadJson(jsonPath);
				oldJsonPath = jsonPath;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected static String getLocator(String locatorKey, String locatorType) {
		Object locatorObject;
		locatorObject = ((HashMap) ((HashMap) getJsonMap().get(locatorKey)).get(platformType)).get(locatorType);
		return locatorObject.toString();
	}

	protected static Set getLocatorType(String locatorKey) {
		HashMap locatorObject = null;
		try {
			locatorObject = (HashMap) ((HashMap) getJsonMap().get(locatorKey)).get(platformType);
			if (locatorObject == null) {
				throw new NoSuchElementException("Locator object for key '" + locatorKey + "' not found.");
			}
		} catch (NullPointerException e) {
			BaseClass.log.error("Locator object for key '" + locatorKey + "' not found.");
			throw new NoSuchElementException("Locator object for key '" + locatorKey + "' not found.");
		}
		return locatorObject.keySet();
	}

	public void clickUsingJavascriptByWebElement(WebElement ele) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", ele);
	}

	protected static String getXpath(String locatorKey) {
		return getLocator(locatorKey, "xpath");
	}

	protected static String getId(String locatorKey) {
		return getLocator(locatorKey, "id");
	}

	protected static String getCss(String locatorKey) {
		return getLocator(locatorKey, "css");
	}

	protected static String getName(String locatorKey) {
		return getLocator(locatorKey, "name");
	}

	protected static String getPartialTextLink(String locatorKey) {
		return getLocator(locatorKey, "partialText");
	}

	public WebElement getElementByXpath(String locator) {
		WebElement element;
		String locatorId = getLocator(locator, "xpath");
		element = driver.findElement(By.xpath(locatorId));
		return element;
	}

	protected WebElement getElementById(String locatorKey) {
		String locatorId = getLocator(locatorKey, "id");
		return driver.findElement(By.id(locatorId));
	}

	protected WebElement getElementByName(String locatorKey) {
		String locatorId = getLocator(locatorKey, "name");
		return driver.findElement(By.name(locatorId));
	}

	protected WebElement getElementByCSS(String locatorKey) {
		String locatorId = getLocator(locatorKey, "css");
		return driver.findElement(By.cssSelector(locatorId));
	}

	private static void sendKeys(WebElement element, String text) {
		try {
			element.sendKeys(text);
			log.info("Text entered is: " + text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to clear the text in the text box
	 * 
	 * @param element
	 * @createdby Ishwarya
	 */
	private static void clearText(WebElement element) {
		try {
			element.clear();
			log.info("Cleared text box");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to clear the text in the text box
	 * 
	 * @param locaterKey
	 * @createdby Ishwarya
	 */
	public void clearTextOfTheElement(String locaterKey) {
		Set locatorType = getLocatorType(locaterKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locaterKey, "id", 45);
				waitUntilElementClickable(locaterKey, "id", 45);
				clearText(getElementById(locaterKey));
				log.info("Cleared text at element " + getElementById(locaterKey) + "successfully");
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locaterKey, "css", 45);
				waitUntilElementClickable(locaterKey, "css", 45);
				clearText(getElementByCSS(locaterKey));
				log.info("Cleared text at element " + getElementByCSS(locaterKey) + "successfully");
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locaterKey, "name", 45);
				waitUntilElementClickable(locaterKey, "name", 45);
				clearText(getElementByName(locaterKey));
				log.info("Cleared text at element " + getElementByName(locaterKey) + "successfully");
				break;
			} else {
				waitUntilVisible(locaterKey, "xpath", 45);
				waitUntilElementClickable(locaterKey, "xpath", 45);
				clearText(getElementByXpath(locaterKey));
				log.info("Cleared text at element " + getElementByXpath(locaterKey) + "successfully");
				break;
			}
		}
	}

	private static void clickElement(WebElement element) {
		try {
			element.click();
			log.info("Click operation is performed on:" + element);
		} catch (ElementClickInterceptedException error) {
			log.info(error);
		}
	}

	private static void selectValue(WebElement element, String text) {
		try {
			Select se = new Select(element);
			se.selectByValue(text);
			log.info("Value/option selected: " + text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean waitUntilVisible(String locator, String locatorType, int maxWaitSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSeconds));
		ThreadLocal<WebDriverWait> tlWait = new ThreadLocal<WebDriverWait>();
		tlWait.set(wait);
		boolean found = false;
		if (locatorType.equalsIgnoreCase("xpath")) {
			found = tlWait.get().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(locator))))
					.isDisplayed();
			log.info("Element " + getXpath(locator) + " is in visible state now ");
		} else if (locatorType.equalsIgnoreCase("id")) {
			found = tlWait.get().until(ExpectedConditions.visibilityOfElementLocated(By.id(getId(locator))))
					.isDisplayed();
			log.info("Element " + getId(locator) + " is in visible state now ");
		} else if (locatorType.equalsIgnoreCase("css")) {
			found = tlWait.get().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(getCss(locator))))
					.isDisplayed();
			log.info("Element " + getCss(locator) + " is in visible state now ");
		} else if (locatorType.equalsIgnoreCase("name")) {
			found = tlWait.get().until(ExpectedConditions.visibilityOfElementLocated(By.name(getName(locator))))
					.isDisplayed();
			log.info("Element " + getName(locator) + " is in visible state now ");
		} else if (locatorType.equalsIgnoreCase("partialText")) {
			tlWait.get().until(
					ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(getPartialTextLink(locator))))
					.isDisplayed();
			log.info("Element " + getPartialTextLink(locator) + " is in visible state now ");
		}
		tlWait.remove();
		return found;
	}

	public Map<String, String> gatherData(String... columnNames) {
		Map<String, String> columnValueMap = new HashMap<>();
		for (String columnName : columnNames) {
			try {
				Field field = this.getClass().getDeclaredField(columnName);
				field.setAccessible(true);
				Object value = field.get(this);
				if (value != null) {
					columnValueMap.put(columnName, value.toString());
				}
			} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("Error accessing field: " + columnName, e);
			}
		}
		return columnValueMap;
	}

	public void waitUntilElementClickable(String locator, String locatorType, int maxWaitSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSeconds));
		ThreadLocal<WebDriverWait> tlWait = new ThreadLocal<WebDriverWait>();
		tlWait.set(wait);
		if (locatorType.equalsIgnoreCase("xpath")) {
			tlWait.get().until(ExpectedConditions.elementToBeClickable(By.xpath(getXpath(locator)))).isEnabled();
			log.info("Element " + getXpath(locator) + " is in clickable state now ");
		} else if (locatorType.equalsIgnoreCase("id")) {
			tlWait.get().until(ExpectedConditions.elementToBeClickable(By.id(getId(locator)))).isEnabled();
			log.info("Element " + getId(locator) + " is in clickable state now ");
		} else if (locatorType.equalsIgnoreCase("css")) {
			tlWait.get().until(ExpectedConditions.elementToBeClickable(By.cssSelector(getCss(locator)))).isEnabled();
			log.info("Element " + getCss(locator) + " is in clickable state now ");
		} else if (locatorType.equalsIgnoreCase("name")) {
			tlWait.get().until(ExpectedConditions.elementToBeClickable(By.name(getName(locator)))).isEnabled();
			log.info("Element " + getName(locator) + " is in clickable state now ");
		} else if (locatorType.equalsIgnoreCase("partialText")) {
			tlWait.get().until(ExpectedConditions.elementToBeClickable(By.partialLinkText(getPartialTextLink(locator))))
					.isEnabled();
			log.info("Element " + getPartialTextLink(locator) + " is in clickable state now ");
		}
		tlWait.remove();
	}

	public void waitForElementToBeClickable(String locator) {
		waitUntilElementClickable(locator, "xpath", 45);
	}

	public void waitForPageLoad() {

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("loaded")
						|| ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(45));
			ThreadLocal<WebDriverWait> tlWait = new ThreadLocal<WebDriverWait>();
			tlWait.set(wait);
			tlWait.get().until(expectation);
			tlWait.remove();
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}

	}

	public void waitUntilElementNotVisible(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilNotVisible(locatorKey, "id", 45);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilNotVisible(locatorKey, "css", 45);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilNotVisible(locatorKey, "name", 45);
				break;
			} else {
				waitUntilNotVisible(locatorKey, "xpath", 45);
				break;
			}
		}
	}

	public boolean waitUntilNotVisible(String locator, String locatorType, int maxWaitSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSeconds));
		ThreadLocal<WebDriverWait> tlWait = new ThreadLocal<WebDriverWait>();
		tlWait.set(wait);
		boolean notFound = false;
		if (locatorType.equalsIgnoreCase("xpath")) {
			notFound = tlWait.get().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(getXpath(locator))));
			log.info("Element " + getXpath(locator) + " is not in visible state now ");
		} else if (locatorType.equalsIgnoreCase("id")) {
			notFound = tlWait.get().until(ExpectedConditions.invisibilityOfElementLocated(By.id(getId(locator))));
			log.info("Element " + getId(locator) + " is not in visible state now ");
		} else if (locatorType.equalsIgnoreCase("css")) {
			notFound = tlWait.get()
					.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(getCss(locator))));
			log.info("Element " + getCss(locator) + " is not in visible state now ");
		} else if (locatorType.equalsIgnoreCase("name")) {
			notFound = tlWait.get().until(ExpectedConditions.invisibilityOfElementLocated(By.name(getName(locator))));
			log.info("Element " + getName(locator) + " is not in visible state now ");
		} else if (locatorType.equalsIgnoreCase("partialText")) {
			tlWait.get().until(
					ExpectedConditions.invisibilityOfElementLocated(By.partialLinkText(getPartialTextLink(locator))));
			log.info("Element " + getPartialTextLink(locator) + " is not in visible state now ");
		}
		tlWait.remove();
		return notFound;
	}

	public void clickOnElement(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				waitUntilElementClickable(locatorKey, "id", 45);
				scrollToElement(getElementById(locatorKey));
				clickElement(getElementById(locatorKey));
				// log.info("Element " + getElementById(locatorKey) + " is Clicked now");
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				waitUntilElementClickable(locatorKey, "css", 45);
				scrollToElement(getElementByCSS(locatorKey));
				clickElement(getElementByCSS(locatorKey));
				// log.info("Element " + getElementByCSS(locatorKey) + " is Clicked now ");
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				waitUntilElementClickable(locatorKey, "name", 45);
				scrollToElement(getElementByName(locatorKey));
				clickElement(getElementByName(locatorKey));
				// log.info("Element " + getElementByName(locatorKey) + " is Clicked now ");
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				waitUntilElementClickable(locatorKey, "xpath", 45);
				scrollToElement(getElementByXpath(locatorKey));
				clickElement(getElementByXpath(locatorKey));
				// log.info("Element " + getElementByXpath(locatorKey) + " is Clicked now ");
				break;
			}
		}

	}

	public void selectValueInDropdown(String locatorKey, String text) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				waitUntilElementClickable(locatorKey, "id", 45);
				selectValue(getElementById(locatorKey), text);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				waitUntilElementClickable(locatorKey, "css", 45);
				selectValue(getElementByCSS(locatorKey), text);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				waitUntilElementClickable(locatorKey, "name", 45);
				selectValue(getElementByName(locatorKey), text);
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				waitUntilElementClickable(locatorKey, "xpath", 45);
				selectValue(getElementByXpath(locatorKey), text);
				break;
			}
		}

	}

	public void sendKeysTotheElement(String locaterKey, String text) {
		Set locatorType = getLocatorType(locaterKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locaterKey, "id", 45);
				waitUntilElementClickable(locaterKey, "id", 45);
				scrollToElement(getElementById(locaterKey));
				sendKeys(getElementById(locaterKey), text);
				log.info("Keys are sent to element " + getElementById(locaterKey) + "successfully");
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locaterKey, "css", 45);
				waitUntilElementClickable(locaterKey, "css", 45);
				scrollToElement(getElementByCSS(locaterKey));
				sendKeys(getElementByCSS(locaterKey), text);
				log.info("Keys are sent to element " + getElementByCSS(locaterKey) + "successfully");
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locaterKey, "name", 45);
				waitUntilElementClickable(locaterKey, "name", 45);
				scrollToElement(getElementByName(locaterKey));
				sendKeys(getElementByName(locaterKey), text);
				log.info("Keys are sent to element " + getElementByName(locaterKey) + "successfully");
				break;
			} else {
				waitUntilVisible(locaterKey, "xpath", 45);
				waitUntilElementClickable(locaterKey, "xpath", 45);
				scrollToElement(getElementByXpath(locaterKey));
				sendKeys(getElementByXpath(locaterKey), text);
				log.info("Keys are sent to element " + getElementByXpath(locaterKey) + "successfully");
				break;
			}
		}
	}

	/**
	 * This method is used to send keys to the element using javascript executor
	 * 
	 * @param locaterKey
	 * @param text
	 * @createdby Ishwarya
	 */
	public void sendKeysTotheElementUsingJS(String locaterKey, String text) {
		Set locatorType = getLocatorType(locaterKey);
		Iterator it = locatorType.iterator();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			WebElement element = null;

			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locaterKey, "id", 45);
				waitUntilElementClickable(locaterKey, "id", 45);
				element = getElementById(locaterKey);
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locaterKey, "css", 45);
				waitUntilElementClickable(locaterKey, "css", 45);
				element = getElementByCSS(locaterKey);
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locaterKey, "name", 45);
				waitUntilElementClickable(locaterKey, "name", 45);
				element = getElementByName(locaterKey);
			} else {
				waitUntilVisible(locaterKey, "xpath", 45);
				waitUntilElementClickable(locaterKey, "xpath", 45);
				element = getElementByXpath(locaterKey);
			}

			if (element != null) {
				js.executeScript("arguments[0].value='" + text + "';", element);
				log.info("Keys are sent to element " + element + " successfully");
				break;
			}
		}

	}

	public static void selectValue_Visibletext(WebElement element, String text) {
		try {
			Select se = new Select(element);
			se.selectByVisibleText(text);
			log.info("Value/option received for selection is: " + text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectValueInDropdown_Visibletext(String locatorKey, String text) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				waitUntilElementClickable(locatorKey, "id", 45);
				selectValue_Visibletext(getElementById(locatorKey), text);
				log.info("Selected the element " + text + "  by Visible Text successfully.");
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				waitUntilElementClickable(locatorKey, "css", 45);
				selectValue_Visibletext(getElementByCSS(locatorKey), text);
				log.info("Selected the element " + text + "  by Visible Text successfully.");
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				waitUntilElementClickable(locatorKey, "name", 45);
				selectValue_Visibletext(getElementByName(locatorKey), text);
				log.info("Selected the element " + text + "  by Visible Text successfully.");
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				waitUntilElementClickable(locatorKey, "xpath", 45);
				selectValue_Visibletext(getElementByXpath(locatorKey), text);
				log.info("Selected the element " + text + "  by Visible Text successfully.");
				break;
			}
		}

	}

	public static String getElementTextby_Element(WebElement element) {

		log.info("Text Obtained from Webelement" + element.getText());
		return element.getText();
	}

	public static String getAttributeby_Element(WebElement element, String attributeName) {

		log.info("Text Obtained from Webelement" + element.getAttribute(attributeName));
		return element.getAttribute(attributeName);
	}

	public String get_Title() {
		String title = "Title";
		try {
			title = driver.getTitle();
			log.info("Title of the webpage obtained is" + title);
		} catch (Exception error) {

			log.info("Error occurred while fetching title of webpage " + error);
		}
		return title;
	}

	public void switchFrame(WebElement element) {
		try {
			driver.switchTo().frame(element);
			log.info("Switching to:" + element);
		} catch (ElementClickInterceptedException error) {
			log.info(error);
		}
	}

	public void switchToFrame(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				switchFrame(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				switchFrame(getElementByXpath(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				switchFrame(getElementByCSS(locatorKey));
				break;
			} else {
				switchFrame(getElementByName(locatorKey));
				break;
			}
		}
	}

	public void switchFrame_Byindex(int index) {

		driver.switchTo().frame(index);
		log.info("Switched to frame by Text/id " + String.valueOf(index));
	}

	public void switchto_DefaultContent() {

		driver.switchTo().defaultContent();
		log.info("Switched to default content");
	}

	/**
	 * This method is used to double click on the element
	 * 
	 * @createdby Ishwarya
	 */
	public void double_click(WebElement element) {
		try {
			Actions act = new Actions(driver);
			act.doubleClick(element).perform();
			log.info("DoubleClick operation is performed on:" + element);
		} catch (ElementClickInterceptedException error) {
			log.info(error);
		}
	}

	/**
	 * This method is used to double click on the element
	 * 
	 * @createdby Ishwarya
	 */
	public void doubleClickOnElement(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				waitUntilElementClickable(locatorKey, "id", 45);
				scrollToElement(getElementById(locatorKey));
				double_click(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				waitUntilElementClickable(locatorKey, "css", 45);
				scrollToElement(getElementByCSS(locatorKey));
				double_click(getElementByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				waitUntilElementClickable(locatorKey, "name", 45);
				scrollToElement(getElementByName(locatorKey));
				double_click(getElementByName(locatorKey));
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				waitUntilElementClickable(locatorKey, "xpath", 45);
				scrollToElement(getElementByXpath(locatorKey));
				double_click(getElementByXpath(locatorKey));
				break;
			}
		}

	}

	public String getMainWindowHandle() {
		waitForPageLoad();
		String mainWindowHandle = driver.getWindowHandle();
		return mainWindowHandle;

	}

	public void switchToParentWindow(String mainWindowHandle) {
		driver.switchTo().window(mainWindowHandle);
	}

	public void switchToChildWindow() {

		waitForPageLoad();
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		String childWindow = null;
		while (iterator.hasNext()) {
			childWindow = iterator.next();
		}

		driver.switchTo().window(childWindow);
	}

	/**
	 * This method is used to get element by parameterized xpath
	 * 
	 * @createdby Ishwarya
	 */
	public WebElement getElementByParameterisedXpath(String locatorKey, String parameter, String replaceValue) {
		String locatorId = getLocator(locatorKey, "xpath");
		locatorId = locatorId.replace(parameter, replaceValue);
		log.info("In getParameterisedXpath, xpath for an element is: " + locatorId);
		WebElement element = driver.findElement(By.xpath(locatorId));
		log.info("Element " + locatorKey + " with xpath " + " : " + locatorId + " is found");
		return element;
	}

	/**
	 * This method is used to click on the element by parameterized xpath
	 * 
	 * @createdby Ishwarya
	 */
	public void clickOnTheParameterisedXpath(String locatorKey, String parameter, String replaceValue) {
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("xpath")) {
				scrollToElement(getElementByParameterisedXpath(locatorKey, parameter, replaceValue));
				clickElement(getElementByParameterisedXpath(locatorKey, parameter, replaceValue));
				log.info("The Parameterised Xpath element is clicked");
				break;
			}
		}
	}

	public void waitUntilElementVisible(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				break;
			}
		}
	}

	public void pressEnterKeyOnElement(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				pressEnterKey(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				pressEnterKey(getElementByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				pressEnterKey(getElementByName(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				pressEnterKey(getElementByXpath(locatorKey));
				break;
			}
		}
	}

	public String getTextOfElement(String locatorKey) {

		String text = null;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				scrollToElement(getElementById(locatorKey));
				text = getElementTextby_Element(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				scrollToElement(getElementByCSS(locatorKey));
				text = getElementTextby_Element(getElementByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				scrollToElement(getElementByName(locatorKey));
				text = getElementTextby_Element(getElementByName(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				scrollToElement(getElementByXpath(locatorKey));
				text = getElementTextby_Element(getElementByXpath(locatorKey));
				break;
			}
		}
		return text;
	}

	public void pressEnterKey(WebElement element) {

		element.sendKeys(Keys.ENTER);
		log.info("Enter Key is triggered");

	}

	public void refreshPage() {

		driver.navigate().refresh();
		waitForPageLoad();
		log.info("Page is refreshed");
	}

	public void navigateToLink(String url) {

		driver.get(url);
		log.info("Page " + url + "is being loaded...");
	}

	public List<WebElement> getElementsByXpath(String locator) {
		String locatorId = getLocator(locator, "xpath");
		return driver.findElements(By.xpath(locatorId));
	}

	protected List<WebElement> getElementsById(String locatorKey) {
		String locatorId = getLocator(locatorKey, "id");
		return driver.findElements(By.id(locatorId));
	}

	protected List<WebElement> getElementsByName(String locatorKey) {
		String locatorId = getLocator(locatorKey, "name");
		return driver.findElements(By.name(locatorId));
	}

	protected List<WebElement> getElementsByCSS(String locatorKey) {
		String locatorId = getLocator(locatorKey, "css");
		return driver.findElements(By.cssSelector(locatorId));
	}

	public int getSizeOfElement(String locatorKey) {
		int size = 0;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				size = getSize(getElementsById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				size = getSize(getElementsByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				size = getSize(getElementsByName(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				size = getSize(getElementsByXpath(locatorKey));
				break;
			}
		}

		return size;

	}

	public int getSize(List<WebElement> element) {

		int size = element.size();
		return size;
	}

	/**
	 * This method is used to send keys to the element
	 * 
	 * @createdby Ishwarya
	 */
	public void sendKeysToParameterisedXpath(String locatorKey, String parameter, String replaceValue, String text) {
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("xpath")) {
				sendKeys(getElementByParameterisedXpath(locatorKey, parameter, replaceValue), text);
				log.info("The Parameterised Xpath element is clicked");
				break;
			}
		}
	}

	/**
	 * This method is used to scroll to the element using javascript executor
	 *
	 * @param element
	 */
	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * This method is used to scroll to the particular element
	 *
	 * @param locatorKey
	 */
	public void scrollToParticularElement(String locatorKey) {
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				scrollToElement(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				scrollToElement(getElementByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				scrollToElement(getElementByName(locatorKey));
				break;
			} else {
				scrollToElement(getElementByXpath(locatorKey));
				break;
			}
		}

	}

	public void scrollElement(String locatorKey, int xpixels, int ypixels) {
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				scroll(getElementById(locatorKey), xpixels, ypixels);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				scroll(getElementByCSS(locatorKey), xpixels, ypixels);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				scroll(getElementByName(locatorKey), xpixels, ypixels);
				break;
			} else {
				scroll(getElementByXpath(locatorKey), xpixels, ypixels);
				break;
			}
		}

	}

	public void scroll(WebElement element, int xpixels, int ypixels) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollBy(" + xpixels + "," + ypixels + ")", element);

	}

	/**
	 * This method is used to scroll to the location using javascript executor
	 * 
	 * @createdby Ishwarya
	 */
	public void scrollToLocation(int xpixels, int ypixels) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + xpixels + "," + ypixels + ")");
	}

	/*
	 * This method is used to click on the element using the javascript executor
	 * 
	 * created by Ishwarya
	 */
	public void clickElementUsingJSScript(String locatorKey) {
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				clickUsingJSScript(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				clickUsingJSScript(getElementByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				clickUsingJSScript(getElementByName(locatorKey));
				break;
			} else {
				clickUsingJSScript(getElementByXpath(locatorKey));
				break;
			}
		}

	}

	/**
	 * This method is used to click on the element using the javascript
	 * 
	 * created by Ishwarya
	 */
	public void clickUsingJSScript(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);

	}

	public void selectByTextInDropdown(String locatorKey, String text) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				waitUntilElementClickable(locatorKey, "id", 45);
				selectByText(getElementById(locatorKey), text);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				waitUntilElementClickable(locatorKey, "css", 45);
				selectByText(getElementByCSS(locatorKey), text);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				waitUntilElementClickable(locatorKey, "name", 45);
				selectByText(getElementByName(locatorKey), text);
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				waitUntilElementClickable(locatorKey, "xpath", 45);
				selectByText(getElementByXpath(locatorKey), text);
				break;
			}
		}

	}

	public static void selectByText(WebElement element, String text) {
		try {
			Select se = new Select(element);
			se.selectByVisibleText(text);
			log.info("Value/option selected by Text method: " + text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectByTextParameterisedXpath(String locatorKey, String parameter, String replaceValue, String text) {
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		selectByText(getElementByParameterisedXpath(locatorKey, parameter, replaceValue), text);
		log.info("The Parameterised Xpath element is selected from the dropdown");

	}

	public List<WebElement> getDropdownOptions(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		List<WebElement> list = null;
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				list = getDropdownOptionsOfElement(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				list = getDropdownOptionsOfElement(getElementByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				list = getDropdownOptionsOfElement(getElementByName(locatorKey));
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				list = getDropdownOptionsOfElement(getElementByXpath(locatorKey));
				break;
			}
		}

		return list;

	}

	public List<WebElement> getDropdownOptionsOfElement(WebElement webElement) {

		Select dropdownOptions = new Select(webElement);
		List<WebElement> list = dropdownOptions.getOptions();

		return list;

	}

	/**
	 * This method is used to get the text of parameterised xpath element
	 * 
	 * @createdby Ishwarya
	 */
	public String getTextOfParameterisedXpathElement(String locatorKey, String parameter, String replaceValue) {

		String text = null;
		String locatorId = getLocator(locatorKey, "xpath");
		locatorId = locatorId.replace(parameter, replaceValue);
		log.info("In getParameterisedXpath, xpath for an element is: " + locatorId);
		text = driver.findElement(By.xpath(locatorId)).getText();
		log.info("Element " + locatorKey + " with xpath " + " : " + locatorId + " is found");

		return text;
	}

	public String getHrefLink(String locatorKey) {

		String href = null;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				href = getLink(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				href = getLink(getElementByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				href = getLink(getElementByName(locatorKey));
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				href = getLink(getElementByXpath(locatorKey));
				break;
			}
		}
		return href;
	}

	public String getLink(WebElement element) {

		return element.getAttribute("href");
	}

	public boolean checkElementIfVisible(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		boolean status = false;
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				String locatorId = getLocator(locatorKey, "xpath");

				return driver.findElement(By.xpath(locatorId)).isDisplayed();

			} else if (locatorVal.equalsIgnoreCase("css")) {
				String locatorId = getLocator(locatorKey, "xpath");
				return driver.findElement(By.xpath(locatorId)).isDisplayed();

			} else if (locatorVal.equalsIgnoreCase("name")) {
				String locatorId = getLocator(locatorKey, "xpath");
				return driver.findElement(By.xpath(locatorId)).isDisplayed();

			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				String locatorId = getLocator(locatorKey, "xpath");
				return driver.findElement(By.xpath(locatorId)).isDisplayed();

			}
		}
		return false;

	}

	public boolean checkParameterisedElementIfVisible(String locatorKey, String parameter, String replaceValue) {

		boolean element = false;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("xpath")) {
				String locatorId = getLocator(locatorKey, "xpath");
				locatorId = locatorId.replace(parameter, replaceValue);
				log.info("In getParameterisedXpath, xpath for an element is: " + locatorId);
				element = driver.findElement(By.xpath(locatorId)).isDisplayed();
				// element = checkElement(getElementByParameterisedXpath(locatorKey, parameter,
				// replaceValue));
				break;
			}
		}
		if (element)
			return true;
		else
			return false;
	}

	public void mouseHoverOnElement(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				mouseHover(getElementById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				mouseHover(getElementByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				mouseHover(getElementByName(locatorKey));
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				mouseHover(getElementByXpath(locatorKey));
				break;
			}
		}

	}

	public void mouseHover(WebElement element) {

		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
	}

	public int checkElementIfVisibleBasedOnSize(String locatorKey) {

		int elementSize = 0;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				elementSize = getSize(getElementsById(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				elementSize = getSize(getElementsByCSS(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				elementSize = getSize(getElementsByName(locatorKey));
				break;
			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				elementSize = getSize(getElementsByXpath(locatorKey));
				break;
			}
		}
		return elementSize;
	}

	public List<WebElement> getElementsByParameterisedXpath(String locatorKey, String parameter, String replaceValue) {
		String locatorId = getLocator(locatorKey, "xpath");
		locatorId = locatorId.replace(parameter, replaceValue);
		log.info("In getParameterisedXpath, xpath for an element is: " + locatorId);
		List<WebElement> element = driver.findElements(By.xpath(locatorId));
		log.info("Element " + locatorKey + " with xpath " + " : " + locatorId + " is found");
		return element;
	}

	public List<WebElement> getElements(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		List<WebElement> element = null;
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				element = getElementsById(locatorKey);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				element = getElementsByCSS(locatorKey);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				element = getElementsByName(locatorKey);
				break;
			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				element = getElementsByXpath(locatorKey);
				break;
			}
		}
		return element;

	}

	public WebElement getElementByXpathUsingLocatorId(String locatorId) {

		return driver.findElement(By.xpath(locatorId));
	}

	public void launchNewTab() {

		driver.switchTo().newWindow(WindowType.TAB);

	}

	/**
	 * This method is to get the Attribute value based on locaterKey of a given
	 * attribute
	 * 
	 * @param locatorKey, attribute
	 * @return Attribute value
	 * @created by Ishwarya
	 */
	public String getAttributeValueOfGivenAttribute(String locatorKey, String attribute) {

		String placeholder = null;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				scrollToElement(getElementById(locatorKey));
				placeholder = getAttributeValue(getElementById(locatorKey), attribute);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				scrollToElement(getElementByCSS(locatorKey));
				placeholder = getAttributeValue(getElementByCSS(locatorKey), attribute);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				scrollToElement(getElementByName(locatorKey));
				placeholder = getAttributeValue(getElementByName(locatorKey), attribute);
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				scrollToElement(getElementByXpath(locatorKey));
				placeholder = getAttributeValue(getElementByXpath(locatorKey), attribute);
				break;
			}
		}
		return placeholder;
	}

	/**
	 * This method is to get the required AttributeValue of a WebElement
	 * 
	 * @param element, attributeName
	 * @return Attribute value of a WebElement
	 * @created by Ishwarya
	 */
	public String getAttributeValue(WebElement element, String attributeName) {
		return element.getAttribute(attributeName);
	}

	public void deleteCookies() {
		driver.manage().deleteAllCookies();
		log.info("Cookies are deleted successfully");
	}

	/**
	 * This method is used to wait for the fixedTime
	 * 
	 * @param maxWaitSeconds in seconds
	 * @created by ishwarya
	 */
	public void waitForFixedTime(int maxWaitSeconds) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSeconds));
		ExpectedCondition<Boolean> waitCondition = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				return false; // forces to wait for given time
			}
		};
		try {
			wait.until(waitCondition);
		} catch (Exception e) {
			// this block ignores the timeout exception
			log.info("Successfully completed waiting for " + maxWaitSeconds + " seconds");
		}
	}

	/**
	 * Method to check if list is empty
	 * 
	 * @param locatorKey
	 * @return
	 * @created by ishwarya
	 */
	public boolean isListEmpty(String locatorKey) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		List<WebElement> element = null;

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				element = getElementsById(locatorKey);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				element = getElementsByCSS(locatorKey);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				element = getElementsByName(locatorKey);
				break;
			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				element = getElementsByXpath(locatorKey);
				break;
			}
		}
		if (element.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to get the href link of the parameterised xpath element
	 * 
	 * @param locatorKey, parameter, replaceValue
	 * @return
	 * @created by Ishwarya
	 */
	public boolean isParameterisedListEmpty(String locatorKey, String parameter, String replaceValue) {

		List<WebElement> element = null;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("xpath")) {
				String locatorId = getLocator(locatorKey, "xpath");
				locatorId = locatorId.replace(parameter, replaceValue);
				log.info("In getParameterisedXpath, xpath for an element is: " + locatorId);
				element = getElementsByParameterisedXpath(locatorKey, parameter, replaceValue);
				break;
			}
		}
		if (element.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to press Enter key on the element using javascript
	 * 
	 * @param locatorKey, parameter, replaceValue
	 * @return
	 * @created by Ishwarya
	 */
	public void pressEnterKeyOnElementUsingJS(String locatorKey) {
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			WebElement element = null;

			if (locatorVal.equalsIgnoreCase("id")) {
				element = getElementById(locatorKey);
			} else if (locatorVal.equalsIgnoreCase("css")) {
				element = getElementByCSS(locatorKey);
			} else if (locatorVal.equalsIgnoreCase("name")) {
				element = getElementByName(locatorKey);
			} else if (locatorVal.equalsIgnoreCase("xpath")) {
				element = getElementByXpath(locatorKey);
			}

			if (element != null) {
				js.executeScript(
						"arguments[0].focus(); arguments[0].dispatchEvent(new KeyboardEvent('keydown', {'key':'Enter'}));",
						element);
				break;
			}
		}
	}

	/**
	 * This method is used to get the href link of the parameterised xpath element
	 * 
	 * @param locatorKey
	 * @param parameter
	 * @param replaceValue
	 * @return
	 * @throws InterruptedException
	 */
	public String getHrefLinkOfParameterisedXpath(String locatorKey, String parameter, String replaceValue) {

		String href = null;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("xpath")) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				href = getLink(getElementByParameterisedXpath(locatorKey, parameter, replaceValue));
				break;
			}
		}
		return href;
	}

	/**
	 * This method is to get the current URL
	 * 
	 * @return String currentURL
	 * @created by Ishwarya
	 */
	public String getURL() {

		waitForPageLoad();
		String currentURL = driver.getCurrentUrl();
		log.info("The current URL is: " + currentURL);
		return currentURL;
	}

	/**
	 * This method is used to select the value from a dropdown by index value
	 * 
	 * @param element
	 * @return
	 * @created by Ishwarya
	 */
	public static void selectValueByIndex(WebElement element, int index) {
		try {
			Select se = new Select(element);
			se.selectByIndex(index);
			log.info("Selected Index: " + index + " successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to select the value from a dropdown by index value
	 * 
	 * @param locatorKey
	 * @return
	 * @created by Ishwarya
	 */
	public void selectValueInDropdownByIndex(String locatorKey, int index) {

		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilVisible(locatorKey, "id", 45);
				waitUntilElementClickable(locatorKey, "id", 45);
				selectValueByIndex(getElementById(locatorKey), index);
				log.info("Selected the element " + index + "  by Visible Text successfully.");
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilVisible(locatorKey, "css", 45);
				waitUntilElementClickable(locatorKey, "css", 45);
				selectValueByIndex(getElementByCSS(locatorKey), index);
				log.info("Selected the element " + index + "  by Visible Text successfully.");
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilVisible(locatorKey, "name", 45);
				waitUntilElementClickable(locatorKey, "name", 45);
				selectValueByIndex(getElementByName(locatorKey), index);
				log.info("Selected the element " + index + "  by Visible Text successfully.");
				break;
			} else {
				waitUntilVisible(locatorKey, "xpath", 45);
				waitUntilElementClickable(locatorKey, "xpath", 45);
				selectValueByIndex(getElementByXpath(locatorKey), index);
				log.info("Selected the element " + index + "  by Visible Text successfully.");
				break;
			}
		}
	}

	/**
	 * This method is used to wait until the element text changes
	 * 
	 * @param locator, initialText
	 * @param
	 */
	public void waitUntilElementTextChanges(String locator, String initialText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		wait.until((ExpectedCondition<Boolean>) driver -> {
			WebElement element = driver.findElement(By.xpath(locator));
			String currentText = element.getText();
			return !currentText.equals(initialText);
		});
	}

	/**
	 * This method is used to get the dynamic xpath element using the locatorKey
	 * 
	 * @param locatorKey
	 * @return
	 * @created by Ishwarya
	 */
	public By getDynamicXpath(String locatorKey, String... replacements) {

		String xpathTemplate = getLocator(locatorKey, "xpath");
		String xpath = String.format(xpathTemplate, (Object[]) replacements);
		log.info("Genertated xpath: " + xpath);
		return By.xpath(xpath);
	}

	/**
	 * This method is used to get text of the dynamic xpath element
	 * 
	 * @param locatorKey, replacements
	 * @created by Ishwarya
	 */
	public String getTextOfDynamicXpathElement(String locatorKey, String... replacements) {

		String text = "";
		By xpathSelector = getDynamicXpath(locatorKey, replacements);
		WebElement element = driver.findElement(xpathSelector);
		if (element != null) {
			text = element.getText();
			log.info("The Parameterised Xpath element text is: " + text);
		}
		return text;
	}

	/**
	 * This method is used to get size of the dynamic xpath element
	 * 
	 * @param locatorKey, replacements
	 * @created by Ishwarya
	 */
	public int getSizeOfDynamicXpath(String locatorKey, String... replacements) {

		int size = 0;
		By xpathSelector = getDynamicXpath(locatorKey, replacements);
		List<WebElement> element = driver.findElements(xpathSelector);
		if (element != null) {
			size = getSize(element);
			log.info("The Parameterised Xpath element is clicked");
		}
		return size;
	}

	/**
	 * This method is used to get size of the dynamic xpath element
	 * 
	 * @param locatorKey, replacements
	 * @created by Ishwarya
	 */
	public int getSizeOfTheParameterisedXpath(String locatorKey, String parameter, String replaceValue) {

		int size = 0;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();
			if (locatorVal.equalsIgnoreCase("xpath")) {
				size = getSize(getElementsByParameterisedXpath(locatorKey, parameter, replaceValue));
				log.info("The Parameterised Xpath element size is: " + size);
				break;
			}
		}
		return size;
	}

	/**
	 * This method is to get the title of the opened tab
	 * 
	 * @return Title of the new tab
	 * @created by Ishwarya
	 */
	public String getTitleOfNewTab(boolean... closeTab) {

		// get window handlers as list
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		// switch to new tab
		driver.switchTo().window(browserTabs.get(browserTabs.size() - 1));
		waitForPageLoad();
		String title = get_Title();

		if (closeTab.length > 0 && closeTab[0] == false) {
			log.info("Title of tab is :: " + title);
		} else {
			log.info("Title of tab is :: " + title);
			// then close tab and get back
			closeNewTab();
			waitForPageLoad();
		}
		return title;
	}

	/**
	 * This method is to close the newly opened tab
	 * 
	 * @created by Ishwarya
	 */
	public void closeNewTab(int... index) {

		waitForPageLoad();
		// get window handlers as list
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(browserTabs.get(browserTabs.size() - 1));
		driver.close();

		if (index.length != 0)
			driver.switchTo().window(browserTabs.get(index[0]));
		else
			driver.switchTo().window(browserTabs.get(browserTabs.size() - 2));
		log.info("Closed tab " + (browserTabs.size() - 1) + " and switched to tab " + (browserTabs.size() - 2));
	}

	/**
	 * This method is to get the URL of the new opened tab
	 * 
	 * @return URL of the new tab
	 * @created by Ishwarya
	 */
	public String getUrlOfNewTab(boolean... closeTab) {

		// get window handlers as list
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		// switch to new tab
		driver.switchTo().window(browserTabs.get(browserTabs.size() - 1));
		waitForPageLoad();
		String url = driver.getCurrentUrl();

		if (closeTab.length > 0 && closeTab[0] == false) {
			log.info("URL of tab is :: " + url);
		} else {
			log.info("URL of tab is :: " + url);
			// then close tab and get back
			closeNewTab();
			waitForPageLoad();
		}
		return url;
	}

	/**
	 * This method is to open the link in new tab
	 * 
	 * @param
	 * @created by Ishwarya
	 */

	public String clickLinkAndGetUrlOfNewTab(String locatorKey, String parameter, String replaceValue) {

		WebElement linkElement = getElementByParameterisedXpath(locatorKey, parameter, replaceValue);
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).click(linkElement).keyUp(Keys.CONTROL).build().perform();

		// waiting for new tab to open
		waitForFixedTime(2);
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		// switch to new tab
		driver.switchTo().window(browserTabs.get(browserTabs.size() - 1));
		waitForPageLoad();
		String url = driver.getCurrentUrl();
//		String title = get_Title();

		// then close tab and get back
		closeNewTab();
		waitForPageLoad();
		return url;
	}

	/**
	 * This Method is to navigate back
	 * 
	 * @created by ishwarya
	 */
	public void navigateBack() {
		driver.navigate().back();
		waitForPageLoad();
		log.info("Page is navigated back");
	}

	/**
	 * This method is to get the attribute of parameterized xpath
	 * 
	 * @param locatorKey, parameter, replaceValue, attribute
	 * @return String attributeValue
	 * @created by Ishwarya
	 */
	public String attributeOfParameterisedElement(String locatorKey, String parameter, String replaceValue,
			String attribute) {

		String attributeValue = null;
		Set locatorType = getLocatorType(locatorKey);
		Iterator it = locatorType.iterator();

		while (it.hasNext()) {
			String locatorVal = it.next().toString();

			if (locatorVal.equalsIgnoreCase("xpath")) {
				String locatorId = getLocator(locatorKey, "xpath");
				locatorId = locatorId.replace(parameter, replaceValue);
				WebElement element = driver.findElement(By.xpath(locatorId));
				attributeValue = element.getAttribute(attribute);
				break;
			}
		}
		return attributeValue;
	}

	/*
	 * This method is used to get the text of the dynamic xpath element replacements
	 * 
	 * @created by Yunus
	 */
	public void switchToWindow(int windowIndex) {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(windowIndex));
	}

	/**
	 * This method is used to get the text of the dynamic xpath element replacements
	 * 
	 * @created by Yunus
	 */
	public void openNewWindow() {
		((JavascriptExecutor) driver).executeScript("window.open()");
	}

	/**
	 * This method is used to get the text of the dynamic xpath element replacements
	 * 
	 * @created by Yunus
	 */
	public void openLinkInNewTab() throws UnsupportedFlavorException, IOException {
		String copiedLink = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);

		// Validate the URL
		if (copiedLink == null || !copiedLink.startsWith("http")) {
			throw new IllegalArgumentException("Invalid URL: " + copiedLink);
		}
		// Open a new tab and navigate to the copied link
		// driver.switchTo().newWindow(WindowType.TAB);
		driver.get(copiedLink);
	}

	/**
	 * This method is used to get the text of the dynamic xpath element replacements
	 * 
	 * @created by Yunus
	 */
	public void switchToDefaultWindow() {
		driver.switchTo().defaultContent();
	}

	/**
	 * This method is to switch to the new opened window
	 * 
	 * @return URL of the new window
	 * @created by Ishwarya
	 */
	public void switchToNewWindow() {

		waitForFixedTime(2);
		// get window handlers as list
		List<String> browserWindows = new ArrayList<String>(driver.getWindowHandles());
		// switch to new window
		driver.switchTo().window(browserWindows.get(browserWindows.size() - 1));
		waitForPageLoad();
		log.info("Switched to new window.");
	}

	/**
	 * This method is to close the newly opened window and switch back to the
	 * original window
	 * 
	 * @created by Ishwarya
	 */
	public void closeNewWindow() {

		// get window handlers as list
		List<String> browserWindows = new ArrayList<String>(driver.getWindowHandles());

		// close new window
		driver.switchTo().window(browserWindows.get(browserWindows.size() - 1)).close();

		// switch back to the original window
		driver.switchTo().window(browserWindows.get(0));
		log.info("Closed window and switched back to the original window");
	}

	/**
	 * This method is to close the newly opened window and switch back to the
	 * original window
	 * 
	 * @created by Ishwarya
	 */
	public void clickOnDynamicXpath(String locatorKey, String... replacements) {

		By xpathSelector = getDynamicXpath(locatorKey, replacements);
		WebElement element = driver.findElement(xpathSelector);
		if (element != null) {
			element.click();
			log.info("The Parameterised Xpath element is clicked");
		}
	}

	/**
	 * This method is to wait until the element is not visible
	 * 
	 * @param locator, locatorType, maxWaitSeconds
	 * @return
	 * @created by Ishwarya
	 */
	public void waitUntilParameterizedElementNotVisible(String locatorKey, String parameter, String replaceValue) {
		Set<String> locatorType = getLocatorType(locatorKey);
		Iterator<String> it = locatorType.iterator();
		while (it.hasNext()) {
			String locatorVal = it.next();
			if (locatorVal.equalsIgnoreCase("id")) {
				waitUntilParameterizedNotVisible(locatorKey, "id", parameter, replaceValue, 45);
				break;
			} else if (locatorVal.equalsIgnoreCase("css")) {
				waitUntilParameterizedNotVisible(locatorKey, "css", parameter, replaceValue, 45);
				break;
			} else if (locatorVal.equalsIgnoreCase("name")) {
				waitUntilParameterizedNotVisible(locatorKey, "name", parameter, replaceValue, 45);
				break;
			} else {
				waitUntilParameterizedNotVisible(locatorKey, "xpath", parameter, replaceValue, 45);
				break;
			}
		}
	}

	/**
	 * This method is to wait until the element is not visible
	 * 
	 * @param locator, locatorType, maxWaitSeconds
	 * @return
	 * @created by Ishwarya
	 */
	private boolean waitUntilParameterizedNotVisible(String locator, String locatorType, String parameter,
			String replaceValue, int maxWaitSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSeconds));
		ThreadLocal<WebDriverWait> tlWait = new ThreadLocal<>();
		tlWait.set(wait);
		boolean notFound = false;

		String parameterizedLocator = replaceParameter(locator, parameter, replaceValue);

		if (locatorType.equalsIgnoreCase("xpath")) {
			notFound = tlWait.get()
					.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(getXpath(parameterizedLocator))));
			log.info("Element " + getXpath(parameterizedLocator) + " is not in visible state now ");
		} else if (locatorType.equalsIgnoreCase("id")) {
			notFound = tlWait.get()
					.until(ExpectedConditions.invisibilityOfElementLocated(By.id(getId(parameterizedLocator))));
			log.info("Element " + getId(parameterizedLocator) + " is not in visible state now ");
		} else if (locatorType.equalsIgnoreCase("css")) {
			notFound = tlWait.get().until(
					ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(getCss(parameterizedLocator))));
			log.info("Element " + getCss(parameterizedLocator) + " is not in visible state now ");
		} else if (locatorType.equalsIgnoreCase("name")) {
			notFound = tlWait.get()
					.until(ExpectedConditions.invisibilityOfElementLocated(By.name(getName(parameterizedLocator))));
			log.info("Element " + getName(parameterizedLocator) + " is not in visible state now ");
		} else if (locatorType.equalsIgnoreCase("partialText")) {
			notFound = tlWait.get().until(ExpectedConditions
					.invisibilityOfElementLocated(By.partialLinkText(getPartialTextLink(parameterizedLocator))));
			log.info("Element " + getPartialTextLink(parameterizedLocator) + " is not in visible state now ");
		}
		tlWait.remove();
		return notFound;
	}

	/**
	 * This method is to replace the parameter in the locator
	 * 
	 * @param locator, parameter, replaceValue
	 * @return
	 * @created by Ishwarya
	 */
	private String replaceParameter(String locator, String parameter, String replaceValue) {
		return locator.replace(parameter, replaceValue);
	}

}