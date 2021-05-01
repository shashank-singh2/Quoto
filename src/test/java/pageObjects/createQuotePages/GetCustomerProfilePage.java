package pageObjects.createQuotePages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;

public class GetCustomerProfilePage extends ViewEditQuote_BasePage {

	WebDriver driver;

	@FindBy(xpath="//div[@class='modal-content']//qto-customer-profile-popup")
	private WebElement customerProfilePopup;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='card']/h5")
	private List<WebElement> popup_Section_cards;
	@FindBy(xpath = "//div[@class='modal-content']//qto-customer-profile-popup//div[contains(@class,'row')]")
	private WebElement header_CusomerName;
	@FindBy(xpath = "//div[@class='modal-content']//qto-customer-profile-popup//button[@id='populateOrder']")
	private WebElement b_PopulateCheckedFields;
	@FindBy(xpath = "//div[@class='modal-content']//qto-customer-profile-popup//button[@id='cancelOrder']")
	private WebElement b_Cancel;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='card']/h5/following-sibling::div//p-checkbox")
	private List<WebElement> c_CustProfAllChkBx;
	@FindBy(xpath="//div[@class='modal-content']//div[@class='card']//h5[text()='Customer']/..//div[contains(@class,'card-text')]")
	private List<WebElement> customerSectionValues;

	public GetCustomerProfilePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}
	
	public void verifyCustomerProfilePopup() {
		try {
			Syncronization.waitFor(customerProfilePopup, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
			Assert.assertTrue(customerProfilePopup.isDisplayed(),"Customer profile popup not displayed");
			Reporter.log("Customer profile popup displayed succesfully");
		}
		catch(Exception e) {
			b_Ok_Errorpopup.click();
			Reporter.log("Global Error displayed while clicking get Customer Profile popup. Popup Dismised. ");
		}
	}

	public void verifyNumberOfSection(int expectedNumber) {
		int section = popup_Section_cards.size();
		Assert.assertEquals(section, expectedNumber,
				"number of sections in Get Customer Order is not correct as expected");
		Reporter.log("Number of sections present in Get Customer Profile popup is :" + section + ". ");
	}

	/**
	 * this method will check the value of contracting party in Get Customer profile
	 * popup Header.
	 * 
	 * @param customer_ContractingParty : Pass this argument from field in
	 *                                  Quotesummary Page
	 */
	public void verifyHeaderValueSingleCustomer(String customer_ContractingParty, String ic01) {

		Assert.assertEquals(header_CusomerName.getText(), customer_ContractingParty + "-" + ic01);
		Reporter.log("Customer Name and Customer Contracting party displaying correctly in GetCustProfile Popup. ");
	}

	public void clickPopulateCheckedFields() {
		try {
			b_PopulateCheckedFields.click();
		} catch (Exception e) {
			if (p_ErrorPopup.isDisplayed()) {

//				b_CopyToClipBoard.click();
//				b_Cancel_Copy2Clipboard.click();
				Reporter.log("following Error Displayed on populating values in GetCustProfile : "
						+ t_Message_errorPopup.getText() + ". ");
				b_Ok_Errorpopup.click();
				b_Cancel.click();
				Assert.assertTrue(false);
			}
		}
	}

/*	----- Currently these two method can not be used as cannot fetch values from general tab quote summary text input fields.
	public Map<String, String> getCustomerSectionValues() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i=0; i<customerSectionValues.size(); i=i+2) {
			String key = customerSectionValues.get(i).getText();
			String value = customerSectionValues.get(i+1).getText();
			map.put(key, value );
				
			}
		return map;

	}
	
	public void verifyCustomerSectionValues(String CustomerRequestorName, String CustomerRequestorMail) {
		Map<String, String> map = getCustomerSectionValues();
		Assert.assertEquals(map.get("Customer Requestor Name"), CustomerRequestorName,"Customer requestor name not populated correctly");
		Reporter.log("Customer requestor name  populated correctly. ");
		Assert.assertEquals(map.get("Customer Requestor Mail"), CustomerRequestorMail, "Customer requestor email not populated correctly.");
		Reporter.log("Customer requestor Email  populated correctly. ");
		
	}
	----- */

	/**
	 * This method will first select all the checkbox present on Get Customer profile popup. 
	 * Then will verify wether checkbox checked properly
	 */
	public void clickAllChkBx2SelectAndVerify() {
		int chksz = c_CustProfAllChkBx.size();
		for (int i=0;i<chksz;i++) {
			if (!c_CustProfAllChkBx.get(i).isSelected()) {
				c_CustProfAllChkBx.get(i).click();
			}
		}
		for (int j=0;j<chksz;j++) {
			Assert.assertTrue(c_CustProfAllChkBx.get(j).isSelected(), "CheckBox in GetCustomer profile not getting selected. ");
			Reporter.log("Verified that All the checkbox are checked in CustProfile popup. " );
		}

	}

}
