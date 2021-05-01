package pageObjects.createQuotePages.generalTab;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import mainUtilities.Global_VARS;
import pageObjects.createQuotePages.ViewEditQuote_BasePage;

public class General_QuoteSummaryPage extends ViewEditQuote_BasePage{

//	private static General_QuoteSummaryPage instance = null;
	WebDriver driver;
	@FindBy (xpath ="//ul[@role='tablist']//a[@id='tab_0' and contains(text(),'Quote Summary')]")
	private WebElement quote_Summary_Tab;
	@FindBy (xpath = "//div[@title='Id of the quote']//span")
	private WebElement quoteID;
	@FindBy (xpath = "//div[@title='Type of the quote']//span")
	private WebElement quoteType;
	
	
	//Customer section
	@FindBy (xpath="//div[@class='card-header' and text()='Customer']/..//label[@for='contractingPartyCustomer']/..//qto-label-customer-widget/div")
	private WebElement t_Customer_contractingParty;
	@FindBy (xpath ="//div[@class='card-header' and text()='Customer']/..//label[@for='contractingPartyIc01']/..//qto-label-widget/span")
	private WebElement t_IC01;
	
	//QuoteActors section
	@FindBy (xpath="//div[@class='card-header' and text()='Quote Actors']/..//label[@for='primaryQOName']/..//qto-label-widget/span")
	private WebElement t_QuoteOwnerValue;
	@FindBy (xpath="//div[@class='card-header' and text()='Quote Actors']/..//label[@for='primaryTDTName']/..//qto-label-widget/span")
	private WebElement t_TechnicalDesignValue;
	@FindBy (xpath="//div[@class='card-header' and text()='Quote Actors']/..//label[@for='primarySTMName']/..//qto-label-widget/span")
	private WebElement t_ServiceTransitionManagerrValue;
	@FindBy (xpath="//div[@class='card-header' and text()='Quote Actors']/..//label[@for='primaryODMName']/..//qto-label-widget/span")
	private WebElement t_OrderDeliveryManagerValue;
	@FindBy (xpath="//div[@class='card-header' and text()='Quote Actors']/..//label[@for='primaryOEName']/..//qto-label-widget/span")
	private WebElement t_OrderEntryValue;
	
	// 

	
	public General_QuoteSummaryPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/* not using Singleton approach as not success when runnning multple class
	public static General_QuoteSummaryPage getInstance(WebDriver driver) {
		if (instance==null) {
			instance = new General_QuoteSummaryPage(driver);
		}
		return instance;
	}
	*/
	
	public String getQuoteIDText() {
		String qID = quoteID.getText();
		Reporter.log("Quote ID generated. ");
		return qID;
		
	}
	
	public String getQuoteTypeText() {
		String qtype=quoteType.getText();
		return qtype;
	}
	public WebElement getQuoteID() {
		return quoteID;
	}
	
	public void clickQuoteSummaryTab() {
		quote_Summary_Tab.click();
	}
	public String getCustomer_ContractingParty() {
		return t_Customer_contractingParty.getText();
	}
	public String getQuoteOwnerValue() {
		return t_QuoteOwnerValue.getText();
	}
	public String getIC01() {
		return t_IC01.getText();
	}
	
	public void verifyQuoteActorsValue(String userName) throws InterruptedException {
		Thread.sleep(Global_VARS.TIMEOUT_STATIC_LONG);
		Assert.assertEquals(getQuoteOwnerValue(), userName);
		Reporter.log("Value in Quote Actors QO is correct as per user name passed. ");
		Assert.assertEquals(t_TechnicalDesignValue.getText(), userName);
		Reporter.log("Value in Quote Actors TDT is correct as per user name passed. ");
		Assert.assertEquals(t_ServiceTransitionManagerrValue.getText(), userName);
		Reporter.log("Value in Quote Actors STM is correct as per user name passed. ");
		Assert.assertEquals(t_OrderDeliveryManagerValue.getText(), userName);
		Reporter.log("Value in Quote Actors ODM is correct as per user name passed. ");
		Assert.assertEquals(t_OrderEntryValue.getText(), userName);
		Reporter.log("Value in Quote Actors OE is correct as per user name passed. ");
		
	}
}


