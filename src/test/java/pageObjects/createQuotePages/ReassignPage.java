package pageObjects.createQuotePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

public class ReassignPage extends ViewEditQuote_BasePage {
	
	WebDriver driver;
	
	@FindBy (xpath ="//div[@class='modal-content']/qto-team-popup")
	private WebElement p_ReassignPopup;
	@FindBy(xpath ="//div[@class='modal-content']/qto-team-popup//select[@id='QO']")
	private WebElement d_QO;
	@FindBy(xpath ="//div[@class='modal-content']/qto-team-popup//select[@id='TDT']")
	private WebElement d_TDT;
	@FindBy(xpath ="//div[@class='modal-content']/qto-team-popup//select[@id='OE']")
	private WebElement d_OE;
	@FindBy(xpath ="//div[@class='modal-content']/qto-team-popup//select[@id='STM']")
	private WebElement d_STM;
	@FindBy(xpath ="//div[@class='modal-content']/qto-team-popup//select[@id='ODM']")
	private WebElement d_ODM;
	@FindBy(xpath ="//div[@class='modal-content']/qto-team-popup//button[contains(text(),'Ok')]")
	private WebElement b_Ok;

	public ReassignPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getReassignPopup() {
		return p_ReassignPopup;
	}
	public void selectTDT(String userName_TDT) {
		Select sel = new Select(d_TDT);
		String valueObject = driver.findElement(By.xpath("//div[@class='modal-content']/qto-team-popup//select[@id='TDT']/option[contains(text(),'"+ userName_TDT+"')]")).getAttribute("value");
		sel.selectByValue(valueObject);
	}
	public void selectOE(String userName_OE) {
		Select sel = new Select(d_OE);
		String valueObject = driver.findElement(By.xpath("//div[@class='modal-content']/qto-team-popup//select[@id='OE']/option[contains(text(),'"+ userName_OE+"')]")).getAttribute("value");
		sel.selectByValue(valueObject);
	}
	public void selectSTM(String userName_STM) {
		Select sel = new Select(d_STM);
		String valueObject = driver.findElement(By.xpath("//div[@class='modal-content']/qto-team-popup//select[@id='STM']/option[contains(text(),'"+ userName_STM+"')]")).getAttribute("value");
		sel.selectByValue(valueObject);
	}
	public void selectODM(String userName_ODM) {
//		String availability = driver.findElement(By.xpath("//div[@class='modal-content']/qto-team-popup//select[@id='TDT']")).getAttribute("disabled");
//		if(availability = null)
		Select sel = new Select(d_ODM);
		String valueObject = driver.findElement(By.xpath("//div[@class='modal-content']/qto-team-popup//select[@id='ODM']/option[contains(text(),'"+ userName_ODM+"')]")).getAttribute("value");
		sel.selectByValue(valueObject);
	}
	/**
	 * this method will check the OK button appearence and assign all roles to single user passed as argument
	 * @param currentUserName
	 */
	public void reassignAllRoleToSameUser(String currentUserName) {
		Assert.assertTrue(!b_Ok.isEnabled());
		Reporter.log("Ok Button is disbaled on Reassign Popup when No change. ");
		selectTDT(currentUserName);
		selectOE(currentUserName);
		selectSTM(currentUserName);
		selectODM(currentUserName);
		Assert.assertTrue(b_Ok.isEnabled());
		Reporter.log("Ok Button becomes Enable on Reassign Popup after Change in Values. ");
		b_Ok.click();
	}
	
	

}
