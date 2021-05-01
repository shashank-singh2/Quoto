package pageObjects.createQuotePages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import mainUtilities.GetCurrentClassAndMethod;
import mainUtilities.Global_VARS;
import mainUtilities.ScreenCapture;
import mainUtilities.Syncronization;

public class GetCustomerOrderPage extends ViewEditQuote_BasePage {

	WebDriver driver;

	@FindBy(xpath = "//div[@class='modal-content']/qto-customer-order-popup")
	private WebElement getCustomerOrder_popup;

	@FindBy(id = "find")
	private WebElement b_Find;
	@FindBy(xpath = "//div[@class='modal-content']//select[@id='specificationCode']")
	private WebElement d_ServiceType;
	@FindBy(xpath = "//div[@class='modal-content']//select[@id='country']")
	private WebElement d_CountryName;
	@FindBy(xpath = "//div[@class='modal-content']//input[@id='installedOfferID']")
	private WebElement f_InstalledOfferID;
	@FindBy(xpath = "//div[@class='modal-content']//input[@id='customerOrderID']")
	private WebElement f_OrderNumber;
	@FindBy(id = "populateOrder")
	private WebElement b_PopulateQuote;
	@FindBy(id = "cancelOrder")
	private WebElement b_Cancel;

	@FindBy(xpath = "//div[@class='ag-body-container']/div[contains(@class,'ag-row')]/div[@col-id='installedOfferId']")
	private List<WebElement> orderRowsIntOFrID;
	@FindBy(xpath = "//div[@class='ag-body-container']/div[contains(@class,'ag-row')]/div[@col-id='customerOrderID']")
	private List<WebElement> orderRowsOrderNumber;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='ag-body-container']//div[@col-id='spyIcon']")
	private List<WebElement> spyIcon_All;

	// Order Preview Popup
	@FindBy(xpath = "//div[@class='modal-content']/qto-customer-orders-preview-popup")
	private WebElement p_OrdersPreviewPopup;
	@FindBy(xpath = "//div[@class='modal-content']/qto-customer-orders-preview-popup//button[@id='populateOrder']/em")
	private WebElement b_PopulateQuote_Orderpreview;
	@FindBy(xpath = "//qto-customer-orders-preview-popup//div[@class='card-body']//td")
	private List<WebElement> t_AllFields_Orders_preview_Popup;

	// Customer Order Fill Site popup
	@FindBy(xpath = "//div[@class='modal-content']/qto-customer-order-fill-site-popup")
	private WebElement p_FillSite_popup;
	@FindBy(xpath = "//div[@class='modal-content']/qto-customer-order-fill-site-popup/div[contains(@class,'modal-body')]")
	private WebElement t_Message_FillSitePopup;
	@FindBy(xpath = "//div[@class='modal-content']/qto-customer-order-fill-site-popup/div[@class='modal-footer']//button[@id='populatelocal']")
	private WebElement b_Populate_FillSitePopup;

	// Offer Preview popup
	@FindBy(xpath = "//div[@class='modal-content']/qto-customer-offers-preview-popup")
	private WebElement p_OffersPreviewPopup;
	@FindBy(xpath = "//div[@class='modal-content']/qto-customer-offers-preview-popup//button[@id='populateOrder']")
	private WebElement b_PopulateQuote_OffersPreview;
	@FindBy(xpath = "//qto-customer-offers-preview-popup//div[@class='card-body']//span")
	private List<WebElement> t_AllFields_Offer_Preview_Popup;

	// CORE Site Information popup
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-title']/h4[text()='Site details updated during validation with the site referential (CORE).']")
	private WebElement p_info_CORE_SiteDetails;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[contains(text(),'(CORE)')]/../..//button[contains(text(),'Ok')]")
	private WebElement b_Ok_info_CORE_Site;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[contains(text(),'(CORE)')]/../../div[@class='modal-body']//div[contains(@class,'wrapping-words')]")
	private WebElement t_Message_info_CORE_Site;

	// text Center
	@FindBy(xpath = "//div[contains(text(),'No customer offer found') and contains(@class,'text-danger')]")
	private WebElement t_ErrorMessage;
	@FindBy(xpath = "//div[contains(text(),'Customer Offer Version Details') and @class='text-center']")
	private WebElement t_CustOfferDetails_messageOnpopup;
	@FindBy(xpath = "//div[contains(text(),'Customer Order Details') and @class='text-center']")
	private WebElement t_CustOrderDetails_messageOnpopup;

	// ERROR with confirmation
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[text()='Error']")
	private WebElement p_Error_confirm;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[text()='Error']/../..//button[contains(text(),'Yes')]")
	private WebElement b_Yes_Error_confirm;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[text()='Error']/../../div[@class='modal-body']//div[contains(@class,'wrapping-words')]")
	private WebElement t_Message_Error_Confirm;

	public GetCustomerOrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void verifyGetCustomerOrderPopup() {
		Assert.assertTrue(getCustomerOrder_popup.isDisplayed(), "Get customer Order Popup Not displayed");
	}

	public void clickOnFind() {
		b_Find.click();
	}

	/**
	 * It will click on Populate Quote in Customer Order POPUP. If ANY ERROR popup
	 * displays after clicking populate, the error message will be printed and click
	 * on Cancel. Once error message appears, click Cancel on Customer Order Pop and
	 * Script will be failed
	 */
	public void clickOnPopulateOrder() {
		b_PopulateQuote.click();
		try {
			clickCancel_ErrorPopup(Global_VARS.TIMEOUT_ELEMENT_SHORT);
			b_Cancel.click();
			Assert.assertTrue(false);

		} catch (

		Exception e) {
			e.printStackTrace();

		}
	}

	public void clickOnCancel() {
		b_Cancel.click();
	}

	/**
	 * PopulateQuoteButton: In Order Preview -This method will check if button is
	 * clickable then Click
	 */
	private void clickPopulateQuoteButton() {
		Syncronization.waitForClickable(b_PopulateQuote_Orderpreview, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		Actions act = new Actions(driver);
		act.moveToElement(b_PopulateQuote_Orderpreview).click().perform();
//		b_PopulateQuote_Orderpreview.click();
	}

	/**
	 * It will click on Populate Quote in Customer Order PREVIEW. If ANY ERROR popup
	 * displays after clicking populate, the error message will be printed and click
	 * on Cancel. Once error message appears, click Cancel on Customer Order Pop and
	 * Script will be failed
	 * 
	 * @return Map<String, String> with all the data present in Order preview
	 */
	public Map<String, String> click_PopulateQuote_OrderPreview() {

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < t_AllFields_Orders_preview_Popup.size(); i = i + 2) {
			String key = t_AllFields_Orders_preview_Popup.get(i).getText();
			String value = t_AllFields_Orders_preview_Popup.get(i + 1).getText();
			map.put(key, value);
		}

		clickPopulateQuoteButton();
		try {
		
			if (p_FillSite_popup.isDisplayed()) {
				Reporter.log("Fill Site Popup Displays with dollowing message: " + t_Message_FillSitePopup + ". ");
				b_Populate_FillSitePopup.click();

			}

		} catch (Exception e1) {
			try {
				clickCancel_ErrorPopup(Global_VARS.TIMEOUT_SECOND);
				b_Cancel.click();
				Assert.assertTrue(false);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return map;
	}

	/**
	 * it will select Country name from GetCustomer Order popup dropdown
	 * 
	 * @param countryName
	 */
	public void selectCountryName(String countryName) {

		Select sel = new Select(d_CountryName);
		String valueObject = driver.findElement(By.xpath(
				"//div[@class='modal-content']//select[@id='country']/option[contains(text(),'" + countryName + "')]"))
				.getAttribute("value");
		sel.selectByValue(valueObject);

	}

	/**
	 * it will return the Order number field to be used with find
	 * 
	 * @return
	 */
	public WebElement getOrderNumberField() {
		return f_OrderNumber;
	}

	public WebElement getInstalledOfferID() {
		return f_InstalledOfferID;
	}

	/**
	 * This method will click on Find button and Assert the Text of find button
	 * before and after click if changing to Edit
	 */

	public void clickAndVerifyFindButton() {

		String findTextbeforeClick = b_Find.getText();
		b_Find.click();
		String findTextAfterClick = b_Find.getText();
		if (findTextbeforeClick.trim().equalsIgnoreCase("find")) {

			Assert.assertTrue(findTextAfterClick.trim().equalsIgnoreCase("Edit"),
					"find button text not correct after click");
			Reporter.log("Find Button Succesfully changes to EDIT aftewr click. ");
		} else if (findTextbeforeClick.trim().equalsIgnoreCase("edit")) {
			Assert.assertTrue(findTextAfterClick.trim().equalsIgnoreCase("find"), "find button text not correct");
			Reporter.log("Edit Button Succesfully changes to Find aftewr click. ");
		} else {
			Reporter.log("Find Button text not correct");
		}
	}

	/**
	 * This will click and Verify on the record having ORDER NUMBER And click on
	 * Preview button Also if, error popup displayed after clicking find It will log
	 * its error text and click on cancel
	 * 
	 * @return
	 */
	public String verify_ClickAndPreviewOnRecord_withOrderNumber() {
		String orderID = null;
		try {

			Syncronization.waitForClickable(orderRowsOrderNumber.get(0), Global_VARS.TIMEOUT_MINUTE, driver);
			Assert.assertNotNull(orderRowsOrderNumber, "Data in Order Number search not displayed");
			Reporter.log("Data Displayed in Get Customer Order Search. ");
			Assert.assertTrue(t_CustOrderDetails_messageOnpopup.isDisplayed(),
					"Custmer order Details text not appears on popup. ");
			Reporter.log("Custmer order Details popup appears. ");
			for (int i = 0; i < orderRowsOrderNumber.size(); i++) {
				if (orderRowsOrderNumber.get(i).getText() != null) {
					orderRowsOrderNumber.get(i).click();
					orderID = orderRowsOrderNumber.get(i).getText();
					spyIcon_All.get(i).click();
					Assert.assertTrue(p_OrdersPreviewPopup.isDisplayed(),
							"Preview popup is not displyed after click spy icon");
					Reporter.log("Preview Popup is displayed after click on Spy icon in Customer Order popup");
					break;
				}
			}

		} catch (Exception e) {
			try {
				clickCancel_ErrorPopup(Global_VARS.TIMEOUT_ELEMENT_SHORT);
				b_Cancel.click();
				Assert.assertTrue(false);

			} catch (Exception e1) {
				try {

					if (t_ErrorMessage.isDisplayed()) {
						Reporter.log("Order not Found with following message : " + t_ErrorMessage.getText() + ". ");
						b_Cancel.click();
						Assert.assertTrue(false);
					}
				} catch (Exception e2) {

				}

			}

		}
		return orderID;
	}

	/**
	 * This will click and Verify on the record having INSTALLED OFFER ID And click
	 * on Preview button Also if, error popup displayed after clicking find It will
	 * log its error text and click on cancel
	 * 
	 * @return
	 */
	public String verify_ClickAndPreviewOnRecord_withInstOffrID() {
		String installedOfferID = null;
		try {
			Syncronization.waitFor(orderRowsIntOFrID.get(0), Global_VARS.TIMEOUT_ELEMENT, driver);
			Assert.assertNotNull(orderRowsIntOFrID, "Data in Site search not displayed");
			Reporter.log("Data Displayed in Get Customer Order Search. ");
			for (int i = 0; i < orderRowsIntOFrID.size(); i++) {
				if (orderRowsIntOFrID.get(i).getText() != null) {
					orderRowsIntOFrID.get(i).click();
					installedOfferID = orderRowsIntOFrID.get(i).getText();
					spyIcon_All.get(i).click();
					Assert.assertTrue(p_OrdersPreviewPopup.isDisplayed(),
							"Preview popup is not displyed after click spy icon");
					Reporter.log("Preview Popup is displayed after click on Spy icon in Customer Order popup");
					break;
				}
			}

		} catch (Exception e) {
			try {
				clickCancel_ErrorPopup(Global_VARS.TIMEOUT_ELEMENT_SHORT);

				b_Cancel.click();
				Assert.assertTrue(false);

			} catch (Exception e1) {
				try {
					if (t_ErrorMessage.isDisplayed()) {
						Reporter.log("Order not Found with following message : " + t_ErrorMessage.getText() + ". ");
						b_Cancel.click();
						Assert.assertTrue(false);
					}
				} catch (Exception e2) {

				}

			}

		}
		return installedOfferID;
	}

	/**
	 * When Error popup appears for Confirmation after clicking PopulatQuote button,
	 * this method will report the error message and click on YES
	 * 
	 */
	public void clickErrorforConfirmation() {
		Reporter.log(
				"Error Confirmation Popup Displayed with following message : " + t_Message_Error_Confirm.getText());
		b_Yes_Error_confirm.click();

	}

	/**
	 * This verification will happen when 2nd popup i.e. Customer Offer details
	 * popup appears. It will verify Data in Grid, PopulateQuote Disabled/Enabled
	 * before/After selection, OfferPreview Popup after click on Spy Icon
	 * 
	 */
	public void verify_ClickOnRecordAndPreview_OfferVersion() {
		try {
			Syncronization.waitFor(t_CustOfferDetails_messageOnpopup, Global_VARS.TIMEOUT_MINUTE, driver);
			if (t_CustOfferDetails_messageOnpopup.isDisplayed()) {
				Assert.assertNotNull(orderRowsIntOFrID, "Customer Offer Version Details not displayed in popup. ");
				Reporter.log("Data Displayed in Customer Offer version and count is: " + orderRowsIntOFrID.size());

				Assert.assertTrue(!b_PopulateQuote.isEnabled(),
						"Populate Local site is not disabled before record selection. ");
				Reporter.log("Populate Site Button is disabled before record selection. ");

				orderRowsIntOFrID.get(0).click();
				Assert.assertTrue(b_PopulateQuote.isEnabled(),
						"Populate Local site is not ENABLED after record selection. ");
				Reporter.log("Populate Site Button is ENABLED before record selection. ");

				spyIcon_All.get(0).click();
				Syncronization.waitFor(p_OffersPreviewPopup, Global_VARS.TIMEOUT_MINUTE, driver);
				Assert.assertTrue(p_OffersPreviewPopup.isDisplayed(),
						"Offers Preview popup is not displyed after click spy icon");
				Reporter.log("Offers Preview Popup is displayed after click on Spy icon in Customer Order popup");
			}
		} catch (Exception e) {
			try {

				clickCancel_ErrorPopup(Global_VARS.TIMEOUT_ELEMENT_SHORT);
				b_Cancel.click();
				Assert.assertTrue(false);

			} catch (Exception e1) {
				try {
					

					if (t_ErrorMessage.isDisplayed()) {
						Reporter.log("Order not Found with following message : " + t_ErrorMessage.getText() + ". ");
						b_Cancel.click();
						Assert.assertTrue(false);
					}
				} catch (Exception e2) {

				}

			}

		}
	}

	/**
	 * This method will click on PopulateQuote in offerVersionPreview also check
	 * CORE site info popup after clicking Populate
	 * 
	 * @return Map<String,String> all the data of fields in Preview Offer version
	 *         window.
	 */
	public Map<String, String> clickPopulateQuote_OfferVersion_prev() {

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < t_AllFields_Offer_Preview_Popup.size(); i = i + 2) {
			String key = t_AllFields_Offer_Preview_Popup.get(i).getText();
			String value = t_AllFields_Offer_Preview_Popup.get(i + 1).getText();
			map.put(key, value);
		}

		b_PopulateQuote_OffersPreview.click();

		try {
			verify_clickOk_CORE_Site_info();
		} catch (Exception e) {
		}
		return map;
	}

	public void verify_clickOk_CORE_Site_info() {
		
		if (p_info_CORE_SiteDetails.isDisplayed()) {
			Reporter.log("Core Site Infor popup displayed with following message: " + t_Message_info_CORE_Site.getText()
					+ ". ");
			b_Ok_info_CORE_Site.click();
		}
	}

	/**
	 * This will click and Verify on the record having ORDER NUMBER. Also if, error
	 * popup displayed after clicking find It will log its error text and click on
	 * cancel
	 * 
	 * @return OrderID for the selected record
	 */
	public String verify_ClickOnRecord_withOrderNumber() {
		String orderID = null;
		try {
			Syncronization.waitFor(orderRowsOrderNumber.get(0), Global_VARS.TIMEOUT_ELEMENT, driver);
			Assert.assertNotNull(orderRowsOrderNumber, "Data in Site search not displayed");
			Reporter.log("Data Displayed in Get Customer Order Search. ");
			for (int i = 0; i < orderRowsOrderNumber.size(); i++) {
				if (orderRowsOrderNumber.get(i).getText() != null) {
					orderRowsOrderNumber.get(i).click();
					orderID = orderRowsOrderNumber.get(i).getText();
					System.out.println("i is: " + i + " and size is : " + orderRowsOrderNumber.size());
					break;
				}
			}

		} catch (Exception e) {
			try {


				clickCancel_ErrorPopup(Global_VARS.TIMEOUT_SECOND);
					b_Cancel.click();
					Assert.assertTrue(false);
				

			} catch (Exception e1) {
				try {
				
					if (t_ErrorMessage.isDisplayed()) {
						Reporter.log("Order not Found with following message : " + t_ErrorMessage.getText() + ". ");
						ScreenCapture.getScreen("verify_ClickOnRecord_withOrderNumber", driver);
						b_Cancel.click();
						Assert.assertTrue(false);
					}
				} catch (Exception e2) {

				}

			}

		}
		return orderID;
	}

}
