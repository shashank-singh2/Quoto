package mainUtilities;

import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

/**
 * Syncronization class to wait for a designated time period using WebDriverWait
 * class and specific Condition
 * 
 * @author ssing155
 *
 */
public class Syncronization {

	/**
	 * waitFor method to wait (static Locator) .refreshed method called on
	 * ExpectedConditions is used to wait till VISIBILITY of element to avoid
	 * "StaleElementsRefrenceException" type Failure
	 */
	public static void waitFor(WebElement element, int timer, WebDriver driver) {

		// Wait for the static element to appear

		Wait<WebDriver> exists = new WebDriverWait(driver, timer).withMessage("element not visible");
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
	

	}

	/**
	 * waitFor method to wait (static Locator) .refreshed method called on
	 * ExpectedConditions is used to wait for element to be clickable to avoid
	 * "StaleElementsRefrenceException" type Failure
	 */
	public static void waitForClickable(WebElement element, int timer, WebDriver driver) {

		// Wait for the static element to appear
		Wait<WebDriver> exists = new WebDriverWait(driver, timer).withMessage("Element not clickable");

		exists.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));

	}

	/**
	 * Overloading waitFor method to use Dynamic Locator
	 */
	public static void waitFor(By by, int timer, WebDriver driver) {

		// Wait for dynamic element to appear
		WebDriverWait exists = new WebDriverWait(driver, timer);

		// examples: By.id(<id>), By.name(<name>), By.className(<class>),
		// By.xpath(locator), By.cssSelector(css)
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
	}

	/**
	 * waitforGone method wait to a designated perios before throwing exception if
	 * element still exists
	 */
	public static void waitForGone(By by, int timer, WebDriver driver) {

		// Wait for dynamic element to disappear
		WebDriverWait exists = new WebDriverWait(driver, timer);

		// examples: By.id(<id>), By.name(<name>), By.className(<class>),
		// By.xpath(locator), By.cssSelector(css)
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOfElementLocated(by)));
	}

	public static void waitForURL(String url, int timer, WebDriver driver) {
		WebDriverWait exists = new WebDriverWait(driver, timer);
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.urlContains(url)));
	}

	/**
	 * waitFor method to wait up to title is not found
	 */
	public static void waitFor(String title, int timer, WebDriver driver) {
		WebDriverWait exists = new WebDriverWait(driver, timer);
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.titleContains(title)));
	}

}
