package pageObjects.dashBoardPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;
import pageObjects.createQuotePages.CreateQuotePage;

public class MyQuotesTabPage {
	
//	private static MyQuotesTabPage instance = null;

	WebDriver driver;
	@FindBy(xpath = "//ul[@class='navbar-nav']//a[contains(@class,'nav-link') and text()='My Quotes']")
	private WebElement myQuotesTab;
	@FindBy(xpath = "//section[@id='fixed_form']//button[contains(text(),'NS/VOICE') and @title='Network Services for non-France based customers']")
	private WebElement buttonNS;
	@FindBy(xpath = "//section[@id='fixed_form']//button[contains(text(),'IS') and @title='Integration Services (for all customers)']")
	private WebElement buttonIS;
	@FindBy(xpath = "//section[@id='fixed_form']//button[contains(text(),'FRNS') and @title='Network Services for France-based customers']")
	private WebElement buttonFRNS;
	@FindBy(xpath = "//div[@class='ag-body-container']/div[@row-index='0']/div[@col-id='quoteId']")
	private WebElement firstRecord;

	@FindBy(xpath = "//div[@class='ag-header-container']/div[@class='ag-header-row'][1]/div")
	private List<WebElement> column_HeaderNameList;

	public MyQuotesTabPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/* not using Singleton approach as not success when runnning multple class
	public static MyQuotesTabPage getInstance(WebDriver driver) {
		
		if (instance ==null) {
			instance = new MyQuotesTabPage(driver);
		}
		return instance;
	}
	*/

	public void clickOnMyQuotes() {
		myQuotesTab.click();
	}

	/**
	 * Assert if MyQuotes tab is Active or not
	 */
	public void verifyMyQuotesActive() {

		Assert.assertEquals(myQuotesTab.getAttribute("class"), "nav-link active", "My Quotes tab is not active. ");
		Reporter.log("My Quotes Tab is active. ");
		System.out.println("Reporter.log(\"My Quotes Tab is active. );");

	}

	/**
	 * This method will return QuoteID coulmn of first row
	 * 
	 * @return first row in dashboard
	 */
	public WebElement getFirstRecord() {
		return firstRecord;
	}
	
	public WebElement getNewQuote(String quoteID) {
		WebElement newQuote = driver.findElement(By.xpath("//div[@class='ag-body-container']/div[@row-index='0']/div[@col-id='quoteId' and text()='"+quoteID+"']"));
		return newQuote;
	}
	

	/**
	 * This Method will click on NS/VOICE button to create quote
	 * 
	 * @return object of CreateQuotePage
	 */
	public CreateQuotePage clickOnNS_Button() {
		Syncronization.waitFor(getFirstRecord(), Global_VARS.TIMEOUT_ELEMENT, driver);
		buttonNS.click();
		Reporter.log("Clicked on NS/VOICE button to create Quote. ");
		return (new CreateQuotePage(driver));
	}
	
	public CreateQuotePage clickonIS_Button() {
		Syncronization.waitFor(getFirstRecord(), Global_VARS.TIMEOUT_ELEMENT, driver);
		buttonIS.click();
		Reporter.log("Clicked on IS button to create Quote. ");
		return (new CreateQuotePage(driver));
	}
	/**
	 * This Method will click on FRNS button to create quote
	 * 
	 * @return object of CreateQuotePage
	 */
	public CreateQuotePage clickOnFRNS_Button() {
		Syncronization.waitFor(getFirstRecord(), Global_VARS.TIMEOUT_ELEMENT, driver);
		buttonFRNS.click();
		Reporter.log("Clicked on FRNS button to create Quote. ");
		return (new CreateQuotePage(driver));
	}

	/**
	 * 
	 * @param columnHeaderName
	 * @return i+1
	 */
	private int getColumnHeaderNameIndex(String columnHeaderName) {

		int sizeCol = column_HeaderNameList.size();


		System.out.println("Sizecol: " + sizeCol);
		int i;
		String colId = null;

		for (int j = 0; j < sizeCol; j++) {
			WebElement column_Header = column_HeaderNameList.get(j);
			if (column_Header.getAttribute("title").equalsIgnoreCase(columnHeaderName)) {
				colId = column_Header.getAttribute("col-id");
				break;
			}
		}

		for (i = 0; i < sizeCol; i++) {
			WebElement column_Header = column_HeaderNameList.get(i);
			if (column_Header.getAttribute("col-id").equalsIgnoreCase(colId)) {
				i = i + 1;
				break;
			}
		}
		return i;
	}

	/**
	 * This Method will return WebElement of TEXT field of Column name on dashboard
	 * 
	 * @param columnHeaderName
	 * @return WebElement
	 */
	public WebElement getCol_TextField(String columnHeaderName) {
		Syncronization.waitFor(getFirstRecord(), Global_VARS.TIMEOUT_ELEMENT, driver);
//		try {
//			Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		int colIndex = getColumnHeaderNameIndex(columnHeaderName);
		WebElement col = driver.findElement(By.xpath(
				"//div[@class='ag-header-container']/div[@class='ag-header-row'][2]/div[@class='ag-header-cell']["
						+ colIndex + "]//input[@ref='eColumnFloatingFilter']"));
		return col;
	}

	/**
	 * This Method will return WebElement of Header field of Column name on
	 * dashboard
	 * 
	 * @param columnHeaderName
	 * @return WebElement
	 */
	public WebElement getCol_Header(String columnHeaderName) {
		Syncronization.waitFor(getFirstRecord(), Global_VARS.TIMEOUT_ELEMENT, driver);
//		try {
//			Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		int size = column_HeaderNameList.size();

		WebElement column_Header = null;
		for (int j = 1; j < size; j++) {
			column_Header = column_HeaderNameList.get(j);
			if (column_Header.getAttribute("title").equalsIgnoreCase(columnHeaderName)) {
				break;
			}
		}

		return column_Header;

	}
	/**
	 * This method will Search and click on Quote ID passed as argument from my quotes tab to open ViewEdit quote page
	 * note: If using IContextTest to pass parameters from another method then pass below code as argument as quoteID
	 *  ---> clickQuoteIDSearched((String) context.getAttribute("QuoteID"))
	 * 
	 * @param quoteID
	 */
	public void searchAndClickOnQuoteID2Open(String quoteID) {
		getCol_TextField("ID").sendKeys(quoteID + Keys.ENTER);
		getNewQuote(quoteID).click();
		
	}

}
