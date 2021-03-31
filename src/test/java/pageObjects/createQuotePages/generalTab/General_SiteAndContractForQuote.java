package pageObjects.createQuotePages.generalTab;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import pageObjects.createQuotePages.ViewEditQuote_BasePage;

public class General_SiteAndContractForQuote extends ViewEditQuote_BasePage{

	WebDriver driver;
	
	@FindBy (xpath ="//ul[@role='tablist']//a[@id='tab_1' and contains(text(),'Site and Contact for Quote')]")
	private WebElement site_Contract_4_Quote_Tab;
	@FindBy (xpath = "//label[text()='Country Name']/..//span")
	private WebElement f_CountryName;
	@FindBy (xpath = "//label[text()='Core Site ID']/..//span")
	private WebElement f_Core_Site_ID;
	
	public General_SiteAndContractForQuote(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
		// TODO Auto-generated constructor stub
	}
	
	public void clickSite_Contract_4_Quote_Tab() {
		site_Contract_4_Quote_Tab.click();
	}
	
	public void verifyCountryNameAfterGetSite(String countryName) {
		String Actual = f_CountryName.getText().trim();
		Assert.assertEquals(Actual, countryName, "Get Site did not populated CountryName correctly. ");
		Reporter.log("Country Name Correct: Site Populated Succesfully in Site And Contract for Quote. ");
	}
	public void verifyCoreSiteIDAfterGetSite(String Core_Site_ID) {
		String actual = f_Core_Site_ID.getText().trim();
		Assert.assertEquals(actual, Core_Site_ID, "Get Site did not populated Core_Site_ID correctly. ");
		Reporter.log("Core_Site_ID Name Correct: Site Populated Succesfully in Site And Contract for Quote. ");
	}
	
	
	
	

}
