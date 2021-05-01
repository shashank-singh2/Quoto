package pageObjects.createQuotePages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;
import pageObjects.createQuotePages.generalTab.General_QuoteSummaryPage;

public class CreateQuotePage {
	/**
	 * d_ : dropdown, t_ : textfiled, r_ : radiobutton, c_ : checkbox, b_: button,
	 * i_ : icon
	 */

	WebDriver driver;
	@FindBy(xpath = "//qto-edit-quote-view//a[@id='test' and text()='General']")
	private WebElement generaltab_CreateQuote;
	@FindBy(id = "requestType")
	private WebElement t_QuoteRequestType;
	@FindBy(id = "type")
	private WebElement t_QuoteType;
	@FindBy(id = "contractingPartyCustomer")
	private WebElement d_ContractingPartyCustomer;
	@FindBy(id = "endUserCustomer")
	private WebElement d_Customer_End_User_Name;
	@FindBy(id = "createButton")
	private WebElement b_CreateButton;
	@FindBy(id = "typeahead-focus")
	private WebElement t_ServiceType;
	@FindBy(xpath = "//input[@id='typeahead-focus']/following-sibling::ngb-typeahead-window//span")
	private WebElement d_ServiceType_Value;
	@FindBy(xpath = "//label[@for='productName']/following-sibling::div//input")
	private WebElement t_ServiceType_IS;
	@FindBy(xpath = "//label[@for='productName']/following-sibling::div//input/following-sibling::ngb-typeahead-window//span")
	private WebElement d_ServiceType_IS_Value;
	@FindBy(id = "productOption")
	private WebElement d_ServiceOption_IS;
	@FindBy(id = "businessOpportunity")
	private WebElement d_STM_PM_Scheduling_required_NS;
	@FindBy(id = "lpQuote")
	private WebElement d_Global_Customer_Programs_Quote;
	@FindBy(id = "orderingType")
	private WebElement d_orderingType_FR;
	@FindBy(id = "billingSubAccountNumberFr")
	private WebElement t_SubAccountNumber;

	// CustomerTeam selection Popup
	@FindBy(xpath = " //div[@class='modal-content']/qto-customer-team-selection-popup")
	private WebElement p_CustomerTeamSelection;
	@FindBy(xpath = " //div[@class='modal-content']/qto-customer-team-selection-popup//button[@id='okButton']")
	private WebElement b_Ok_CustTeamPopup;

	public CreateQuotePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void verifyGeneraltabActive() {
		Assert.assertTrue(generaltab_CreateQuote.getAttribute("class").equalsIgnoreCase("nav-link active"));
		Reporter.log("Create Quote page displayed and general tab is active");
	}

	public void setQuoteRequestType(String quoteRequestType) {
		Select sel = new Select(this.t_QuoteRequestType);
		sel.selectByValue(quoteRequestType);
	}

	public void setQuoteType(String quoteType) {
		Select sel = new Select(this.t_QuoteType);
		sel.selectByValue(quoteType);
	}

	/**
	 * 
	 * @param value either Yes or No
	 */
	public void setSTM_PM_Scheduling_required(String value) {
		Select sel = new Select(d_STM_PM_Scheduling_required_NS);
		sel.selectByValue(value);
	}

	/**
	 * 
	 * @param value either Yes or No
	 */
	public void setGlobal_Customer_Programs_Quote(String value) {
		Select sel = new Select(d_Global_Customer_Programs_Quote);
		sel.selectByValue(value);
	}

	/**
	 * This will select contracting party customer via index
	 * 
	 * @param contractingPartyCustomer_Index
	 */
	public void setContractingPartyCustomerByIndex(int contractingPartyCustomer_Index) {
		Select sel = new Select(this.d_ContractingPartyCustomer);
		// contractingPartyCustomerIndex.
		sel.selectByIndex(contractingPartyCustomer_Index);
	}

	public void setContractingPartyCustomerByValue(String contractingPartyCustomer) {
		Select sel = new Select(this.d_ContractingPartyCustomer);
		String value = driver.findElement(By.xpath(
				"//select[@id='contractingPartyCustomer']/option[contains(text(),'" + contractingPartyCustomer + "')]"))
				.getAttribute("value");
		sel.selectByValue(value);
	}

	public void setEndUserCustomer(String endUserCustomer) {
		Select sel = new Select(this.d_Customer_End_User_Name);
		String value = driver
				.findElement(
						By.xpath("//select[@id='endUserCustomer']/option[contains(text(),'" + endUserCustomer + "')]"))
				.getAttribute("value");
		sel.selectByValue(value);

	}

	public void setServiceType(String serviceType) {
		Actions act = new Actions(driver);
		act.click(t_ServiceType);
		this.t_ServiceType.sendKeys(serviceType);
		System.out.println(" this is test run");
		t_ServiceType.click();
		act.click(t_ServiceType);
		Syncronization.waitFor(d_ServiceType_Value, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		d_ServiceType_Value.click();
	}

	public void setServiceType_IS(String serviceType) {
		this.t_ServiceType_IS.sendKeys(serviceType);
		Actions act = new Actions(driver);
		act.click(t_ServiceType_IS);
		System.out.println(" this is test run");
//		t_ServiceType_IS.click();
		Syncronization.waitFor(d_ServiceType_IS_Value, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		d_ServiceType_IS_Value.click();
	}

	public void setServiceOption_IS(String serviceOption) {
		Select sel = new Select(d_ServiceOption_IS);
		sel.selectByValue(serviceOption);

	}

	public void setOrderingType(String orderingType) {
		Select sel = new Select(d_orderingType_FR);
		sel.selectByValue(orderingType);
	}

	public void setSubAccountNumber(String satin) {
		t_SubAccountNumber.sendKeys(satin);
	}

	/**
	 * this will wait for Create button to be clickable and click.
	 * After click it will return object of General_QuoteSummaryPage
	 * Also click on team slection Popup if Displayed after clicking create.
	 * 
	 * @return object of General_QuoteSummaryPage
	 */
	public General_QuoteSummaryPage clickOnCreateButton() {
		Syncronization.waitForClickable(b_CreateButton, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		Actions act = new Actions(driver);
		act.click(b_CreateButton).build().perform();
		System.out.println(" clicke don create button");
		System.out.println(b_CreateButton.isEnabled());
		
		try {
			if(b_CreateButton.isEnabled()) {
			b_CreateButton.click();}
		}
		catch(Exception e) {
			System.out.println(" Click button not clickable");
		}
		try {

//			Syncronization.waitFor(p_CustomerTeamSelection, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
//			if (p_CustomerTeamSelection.isDisplayed()) {
//			b_Ok_CustTeamPopup.click();
//			}
			
			WebDriverWait wait = new WebDriverWait(driver, Global_VARS.TIMEOUT_ELEMENT_SHORT);
			WebElement button = wait.until(ExpectedConditions.elementToBeClickable(b_Ok_CustTeamPopup));
			button.click();
			
		
		} catch (Exception e) {
//			try {
//				
////				Syncronization.waitFor(Qsmry.getQuoteID(), Global_VARS.TIMEOUT_ELEMENT, driver);
//				
//
//			} catch (NoSuchElementException e1) {
//
//			}
//
		}
		return (new General_QuoteSummaryPage(driver));
	}

	/**
	 * This Method will Create NS Quote by entering values as below and wait till
	 * Submit button get enabled/clickable in View Edit quote after clicking Create button
	 * 
	 * @param quoteRequestType
	 * @param quoteType
	 * @param contractingPartyCustomer
	 * @param serviceType
	 * @param STM_PM_Scheduling_required
	 * @param Global_Customer_Programs_Quote
	 * @return QuoteSummary_Page
	 * 
	 */
	public General_QuoteSummaryPage createNSQuote(String quoteRequestType, String quoteType,
			String contractingPartyCustomer, String serviceType, String stm_PM_Scheduling_required,
			String global_Customer_Programs_Quote) {
		Syncronization.waitForClickable(generaltab_CreateQuote, Global_VARS.TIMEOUT_ELEMENT, driver);
		verifyGeneraltabActive();
		this.setQuoteRequestType(quoteRequestType);
		Reporter.log("Value Entered in Request Type. ");
		this.setQuoteType(quoteType);
		Reporter.log("Value Entered in Quote Type. ");
		this.setContractingPartyCustomerByValue(contractingPartyCustomer);
		Reporter.log("Value Selected in Contracting party field. ");
		this.setServiceType(serviceType);
		Reporter.log("Value Entered in Service Type. ");
		this.setSTM_PM_Scheduling_required(stm_PM_Scheduling_required);
		Reporter.log("Value Entered in STM/PM Scheduling reqd field. ");
		this.setGlobal_Customer_Programs_Quote(global_Customer_Programs_Quote);
		Reporter.log("Value Entered in GPA field. ");
//		Thread.sleep(Global_VARS.TIMEOUT_SYNC_STATIC);
		General_QuoteSummaryPage qtSmry = clickOnCreateButton();
		Reporter.log("Clicked On Create Button. ");
		Syncronization.waitForClickable(qtSmry.getSubmitButon(), Global_VARS.TIMEOUT_MINUTE, driver);
		return qtSmry;

	}

	/**
	 * To create IS Quote : This will enter all values in create Quote page and
	 * Click on Create
	 * 
	 * @param quoteRequestType
	 * @param quoteType
	 * @param global_Customer_Programs_Quote
	 * @param contractingPartyCustomer
	 * @param endUserCustomer
	 * @param serviceType
	 * @param serviceOption
	 * @return General_QuoteSummaryPage
	 */
	public General_QuoteSummaryPage createISQuote(String quoteRequestType, String quoteType,
			String global_Customer_Programs_Quote, String contractingPartyCustomer, String endUserCustomer,
			String serviceType, String serviceOption) {

		Syncronization.waitForClickable(generaltab_CreateQuote, Global_VARS.TIMEOUT_ELEMENT, driver);
		verifyGeneraltabActive();
		this.setQuoteRequestType(quoteRequestType);
		Reporter.log("Value Entered in Request Type. ");
		this.setQuoteType(quoteType);
		Reporter.log("Value Entered in Quote Type. ");
		this.setGlobal_Customer_Programs_Quote(global_Customer_Programs_Quote);
		Reporter.log("Value Entered in GPA field. ");
		this.setContractingPartyCustomerByValue(contractingPartyCustomer);
		Reporter.log("Value Selected in Contracting party field. ");
		setEndUserCustomer(endUserCustomer);
		Reporter.log("Value Selected in endUserCustomer field. ");
		this.setServiceType_IS(serviceType);
		Reporter.log("Value Entered in Service Type. ");
		setServiceOption_IS(serviceOption);
		Reporter.log("Value Entered in Service option. ");

		General_QuoteSummaryPage qtSmry = clickOnCreateButton();
		Reporter.log("Clicked On Create Button. ");
		return qtSmry;

	}

	public General_QuoteSummaryPage createFRNSQuote(String quoteRequestType, String quoteType,
			String global_Customer_Programs_Quote, String orderingType, String contractingPartyCustomer,
			String endUserCustomer, String satin, String serviceType) {

		Syncronization.waitForClickable(generaltab_CreateQuote, Global_VARS.TIMEOUT_ELEMENT, driver);
		verifyGeneraltabActive();
		this.setQuoteRequestType(quoteRequestType);
		Reporter.log("Value Entered in Request Type. ");
		this.setQuoteType(quoteType);
		Reporter.log("Value Entered in Quote Type. ");
		this.setGlobal_Customer_Programs_Quote(global_Customer_Programs_Quote);
		Reporter.log("Value Entered in GPA field. ");
		setOrderingType(orderingType);
		Reporter.log("Value selected in OrdringType field. ");
		setSubAccountNumber(satin);
		Reporter.log("Value Entered in SATIN NUMBER field. ");
		this.setContractingPartyCustomerByValue(contractingPartyCustomer);
		Reporter.log("Value Selected in Contracting party field. ");
		setEndUserCustomer(endUserCustomer);
		Reporter.log("Value Selected in endUserCustomer field. ");
		this.setServiceType(serviceType);
		Reporter.log("Value Entered in Service Type. ");

		General_QuoteSummaryPage qtSmry = clickOnCreateButton();
		Reporter.log("Clicked On Create Button. ");
		return qtSmry;

	}

}
