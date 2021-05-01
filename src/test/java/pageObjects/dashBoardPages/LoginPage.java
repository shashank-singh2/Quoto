package pageObjects.dashBoardPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;

import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;

public class LoginPage {

	public WebDriver driver;
	
	@FindBy(xpath = "//li[contains(text(),'Angular')]//a[contains(@href,'JZZV0083')]")
	private WebElement usernameGautam;
	
	@FindBy (xpath=" //li[contains(text(),'Angular Application')]")
	private WebElement all_users;

	
	

	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Clicks on User: Gautam Gambhir
	 * @param driver
	 * @return Object of HomePage
	 */
	public HomePage clickOnUserToLogin() {
		usernameGautam.click();
		return (new HomePage(driver));

	}

	/**
	 * Clicks on User ID passed in method to open angular application
	 * 
	 * @param driver
	 * @param userId
	 * @return object of HomePage
	 */
	public HomePage clickOnUserToLogin(String userId) {
		Syncronization.waitForClickable(all_users, Global_VARS.TIMEOUT_ELEMENT, driver);
		driver.findElement(By.xpath("//li[contains(text(),'Angular')]//a[contains(@href,'"+userId+"')]")).click();
		Reporter.log("Looged into Application by user : " + userId);
		return (new HomePage(driver));

	}

}
