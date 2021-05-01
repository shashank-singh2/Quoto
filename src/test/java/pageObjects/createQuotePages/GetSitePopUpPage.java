package pageObjects.createQuotePages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;

public class GetSitePopUpPage extends ViewEditQuote_BasePage {

	WebDriver driver;

	@FindBy(xpath = "//div[@class='modal-content']//select[@id='countryName']")
	private WebElement d_CountryName;
	@FindBy(id = "find")
	private WebElement b_Find;
	@FindBy(id = "populatelocal")
	private WebElement b_PopulateLocalSite;
	@FindBy(xpath = "//div[@class='ag-body-container']/div[contains(@class,'ag-row')]/div[@col-id='siteID']")
	private List<WebElement> siteRows;
	@FindBy(xpath = "//qto-sites-popup//div[@class='ag-body-container']//div[@title='Preview site']")
	private List<WebElement> b_previewSiteAll;

	// Preview Site
	@FindBy(xpath = "//qto-sites-preview-popup")
	private WebElement b_PreviewSite_PopUp;
	@FindBy(xpath = "//qto-sites-preview-popup//button[@id='populatelocal']")
	private WebElement b_PopulateLocalSite_Preview;
	@FindBy(xpath = "//qto-sites-preview-popup//button[@id='cancel']")
	private WebElement b_Cancel_Preview;
	@FindBy(xpath = "//qto-sites-preview-popup//div[@class='card-body']//span")
	private List<WebElement> t_AllFields_previewsite;

	/**
	 * this will come when clicking find without select country name
	 */
	@FindBy(xpath = "//div[@class='modal-content']//div[contains(text(),'Please select Country or Orange Site Reference')]")
	private WebElement t_SelectCountryPopup;
	@FindBy(xpath = "//div[@class='modal-content']//div[contains(text(),'Please select Country or Orange Site Reference')]/..//button[contains(text(),'Ok')]")
	private WebElement b_OkonCountryPopUp;

	@FindBy(xpath = "//div[@class='modal-content']//label[@for='errorMessage' and contains(text(),'No site found')]")
	private WebElement t_errorMessage;
	@FindBy(xpath = "//div[@class='modal-content']//button[@id='cancel']")
	private WebElement b_Cancel;

	// WARNING POPUP for Change Quote Type
	@FindBy(xpath = "//div[@class='modal-content']/qto-qto-popup//h4[text()='Warning']/../following-sibling::div[contains(@class,'modal-footer')]/button[contains(text(),'Proceed')]")
	private WebElement b_Proceed_Warning_site4Order;

	public GetSitePopUpPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void setCountryName(String countryName) {

		Select sel = new Select(d_CountryName);
		String valueObject = driver
				.findElement(By.xpath("//select[@id='countryName']/option[contains(text(),'" + countryName + "')]"))
				.getAttribute("value");
		sel.selectByValue(valueObject);
	}

	/**
	 * This will click on Find button and Verify its appearance from Find to Edit
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
			Assert.assertTrue(findTextAfterClick.trim().equalsIgnoreCase("find"),
					"find button text not correct After click");
			Reporter.log("Edit Button Succesfully changes to Find after click. ");
		} else {
			Reporter.log("Find Button text not correct");
		}
	}

	public WebElement getPopulateLocalSite() {
		return b_PopulateLocalSite;
	}

	public List<WebElement> getSiteRows() {
		return siteRows;
	}

	/**
	 * this will Verify the Visiblity of records in grid and select First record.
	 * And if error Message or Error popup displays it will cancel and fail the
	 * script.
	 */
	public void verify_Click_OnSiteRecord() {
		Assert.assertTrue(!b_PopulateLocalSite.isEnabled(),
				"Populate Local site is not disabled even before record display. ");
		Reporter.log("Populate Local site is disabled before record display. ");
		int row = 0;

		try {
			Syncronization.waitFor(siteRows.get(row), Global_VARS.TIMEOUT_ELEMENT, driver);
			Assert.assertNotNull(siteRows, "Data in Site search not displayed");
			Reporter.log("Data displayed in Find Site. ");
			Assert.assertTrue(!b_PopulateLocalSite.isEnabled(),
					"Populate Local site is not disabled before record selection. ");
			Reporter.log("Populate Site Button is disabled before record selection. ");
			siteRows.get(row).click();
			Assert.assertTrue(b_PopulateLocalSite.isEnabled(),
					"Populate Local site is not ENABLED after record selection. ");
			Reporter.log("Populate Site Button is ENABLED after record selection. ");
		} catch (Exception e) {
			try {
				if (t_errorMessage.isDisplayed()) {
					Reporter.log("Site not Found with following message : " + t_errorMessage.getText() + ". ");
					b_Cancel.click();
					Assert.assertTrue(false);
				}
			} catch (Exception e1) {
				if (p_ErrorPopup.isDisplayed()) {

//					b_CopyToClipBoard.click();
//					b_Cancel_Copy2Clipboard.click();
					Reporter.log("Site not Found with following message : " + t_Message_errorPopup.getText() + ". ");
					b_Cancel_ErrorPopup.click();
					b_Cancel.click();
					Assert.assertTrue(false);
				}

			}

		}

//		for (int i = 0; i < siteRows.size();i++) {
//
//			String row = siteRows.get(i).getAttribute("col-id");
//			if (row != null)
//				siteRows.get(i).click();
//			break;
//
//		}

	}

	public void verifyFindWithoutCountry() {
		b_Find.click();
		Assert.assertTrue(t_SelectCountryPopup.isDisplayed(), "Country Confirmation Popup not displayed. ");
		Reporter.log("Country Confirmation popup displayed when clicked find without selecting country. ");
		b_OkonCountryPopUp.click();
	}

	public Map<String, String> verify_click_SitePreviewPopup_Populate() {
		b_previewSiteAll.get(0).click();
		Assert.assertTrue(b_PreviewSite_PopUp.isDisplayed(), "Preview site popup not displayed");
		Reporter.log("Preview site popup displayed after click on preview button");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < t_AllFields_previewsite.size(); i = i + 2) {
			String key = t_AllFields_previewsite.get(i).getText();
			String value = t_AllFields_previewsite.get(i + 1).getText();
			map.put(key, value);
		}

		b_PopulateLocalSite_Preview.click();
		return map;

	}

	/**
	 * This method will click on Proceed button in Warning PopUp displayed After
	 * Clicking Populate site to ask for Site and Previous Gold Order/Offer should be
	 * same
	 * 
	 *Before clicking proceed button it will check the Quote type should be of CHANGE
	 * 
	 * @param quoteType
	 */
	public void clickProceed_Warning_SiteOrder(String quoteType) {
		if (quoteType.equalsIgnoreCase("CHANGE")) {
			try {
				b_Proceed_Warning_site4Order.click();
			} catch (Exception e) {

			}
		}
	}

}
