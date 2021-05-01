package pageObjects.dashBoardPages;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sqlite.Function;
import org.testng.Assert;
import org.testng.Reporter;

import mainUtilities.CommonFunctions;
import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;

public class HomePage {

	public WebDriver driver;
//	private static HomePage instance = null;

	public static int np=1;

	@FindBy(xpath = "//header[@id='header']//span[@class='Quoto_Logo' and text()='Quoto']")
	private WebElement quotoLogo;

	@FindBy(xpath = "//ul[@class='navbar-nav']//a[contains(@class,'nav-link active')]")
	private WebElement currentActiveTab;
	@FindBy(xpath = "//h5[text()='Welcome to Quoto']")
	private WebElement welcomePopup;
	@FindBy(xpath = "//h5[text()='Welcome to Quoto']/../..//button[contains(text(),'Cancel')]")
	private WebElement b_WelcomePopup_Cancel;
	@FindBy(xpath = "//div[@class='modal-content']/qto-qto-news-popup")
	private WebElement p_NewsPopup;
	@FindBy(xpath = "//div[@class='modal-content']/qto-qto-news-popup//button[contains(text(),'Cancel')]")
	private WebElement b_Cancel_NewsPP;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	/* not using Singleton approach as not success when runnning multple class
	public static HomePage getInstance(WebDriver driver) {
		
//		if (!instance.equals(new HomePage(driver) )) {
		
//		if (instance == null) {

			instance = new HomePage(driver);
//		}
//		}
		return instance;
	}
	
	*/

	/**
	 * This will assert the Quoto Logo Displayed at the Top
	 */
	public void verifyQuotoLogo() {
		Assert.assertTrue((quotoLogo.getText()).equals("Quoto"), "Home page Logo not correct. ");
		Reporter.log("Quoto Logo is present. ");

	}

	public void verifyPageTitle() {
		Assert.assertTrue(driver.getTitle().equals("QtoAngularGUI"), "Title not correct. ");
	}

	/**
	 * This will the click on Quoto logo and application should refresh After
	 * refresh it will assert the Quoto Logo
	 */
	public void clickQuotoLogo() {
		Syncronization.waitForClickable(quotoLogo, Global_VARS.TIMEOUT_ELEMENT, driver);
		quotoLogo.click();
//		CommonFunctions.alertAccept(driver);
		verifyHomePage();
		Reporter.log("Application refreshed and Home page verified after clicking Quoto Logo. ");
	}

	public String getCurrentActiveTab() {
		return currentActiveTab.getText();
	}

	/**
	 * this method will Clicks on Popups and news message 
	 * verify Quoto Logo and Page Title
	 * 
	 */
	public void verifyHomePage() {
		try {
			Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonFunctions.alertAccept(driver);
//		clickWelcomePopup();
		clickNewsPopup();
		CommonFunctions.alertAccept(driver);
		Syncronization.waitFor((new MyQuotesTabPage(driver).getFirstRecord()), Global_VARS.TIMEOUT_ELEMENT, driver);
		CommonFunctions.alertAccept(driver);
		verifyQuotoLogo();
		verifyPageTitle();
		

	}

	/**
	 * This Method will click on Welcome Popup cancel button IF Displayed.
	 */
	public void clickWelcomePopup() {
		try {
			if (welcomePopup.isDisplayed()) {

				b_WelcomePopup_Cancel.click();
			}
		} catch (Exception e) {
			Reporter.log("Welcome message not displayed. ");
		}
	}

	public void clickNewsPopup() {

		try {
//			Syncronization.waitFor(p_NewsPopup, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
			if (np == 1) {

					b_Cancel_NewsPP.click();
				

			} else {
				Reporter.log("News Popup Not displayed. ");
				System.out.println("news popup not displayed");
			}
		} catch (Exception e) {
			np = 0;
			System.out.println("Inside catch to set y Zero");
			Reporter.log("News Popup Not displayed. ");
			System.out.println("news popup not displayed");
		}
	}

}
