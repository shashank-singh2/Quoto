package pageObjects.createQuotePages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import freemarker.core.ReturnInstruction.Return;
import mainUtilities.CommonFunctions;
import mainUtilities.Global_VARS;
import mainUtilities.ScreenCapture;
import mainUtilities.Syncronization;
import pageObjects.createQuotePages.ServiceInformationTab.ServiceInformationPage;

public abstract class ViewEditQuote_BasePage {

	WebDriver driver;

	// Header
	@FindBy(xpath = "//header[@id='header']//span[text()='Welcome']/following-sibling::a[@title='My Profile']")
	private WebElement t_profileName;

	// headerTabs
	@FindBy(xpath = "//ul[@class='navbar-nav']//a[@title='Service Information']")
	private WebElement serviceInformationTab;

	@FindBy(xpath = "//button[contains(text(),'Quit')]")
	private WebElement b_Quit;
	@FindBy(xpath = "//div[@class='row edit-view-footer fixed-bottom p-1']//button[contains(text(),'Save')]")
	private WebElement b_Save;
	@FindBy(xpath = "//div[@class='row edit-view-footer fixed-bottom p-1']//button[contains(text(),'Get Site')]")
	private WebElement b_GetSite;
	@FindBy(id = "getCustomerOrderButton")
	private WebElement b_GetCustomerOrder;
	@FindBy(id = "customerProfileButton")
	private WebElement b_GetCustomerProfile;
	@FindBy(xpath = "//div[@class='row edit-view-footer fixed-bottom p-1']//button[contains(text(),'Reassign')]")
	private WebElement b_Reassign;
	@FindBy(xpath = "//div[@class='modal-content']/qto-team-popup")
	private WebElement p_Reassign;
	@FindBy(xpath = "//div[@class='row edit-view-footer fixed-bottom p-1']//button[contains(text(),'Submit')]")
	private WebElement b_Submit;

	// Warning Popup on Quit
	@FindBy(xpath = "//h4[text()='Warning']/../..")
	private WebElement warningPopup;
	@FindBy(xpath = "//h4[text()='Warning']/../following-sibling::div/button[contains(text(),'Ok')]")
	private WebElement b_Warn_Ok;

	// Warning Popup on Reassign
	@FindBy(xpath = "//h4[text()='WARNING']/../..")
	private WebElement warningPopup_Ys_No;
	@FindBy(xpath = "//h4[text()='WARNING']/../following-sibling::div/button[contains(text(),'Yes')]")
	private WebElement warnPopup_Yes;

	// Error Popup
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[contains(text(),' Error')]")
	public WebElement p_ErrorPopup;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[contains(text(),' Error')]/../..//button[contains(text(),'Cancel')]")
	public WebElement b_Cancel_ErrorPopup;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[contains(text(),' Error')]/../../div[@class='modal-body']//div[contains(@class,'wrapping-words')]")
	public WebElement t_Message_errorPopup;
	@FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-header']/h4[contains(text(),' Error')]/../..//button[contains(text(),'Ok')]")
	public WebElement b_Ok_Errorpopup;
	@FindBy(xpath = "//div[@class='modal-content']//div[contains(@class,'modal-footer')]/button[contains(text(),'Copy To Clipboard')]")
	private WebElement b_CopyToClipBoard;
	@FindBy(xpath = "//div[@class='modal-content']//article[contains(@class,'modal-header')]/h4[contains(text(),'Message successfully copied into the clipboard')]")
	private WebElement p_MessageCopySuccess;
	@FindBy(xpath = "//div[@class='modal-content']//article[contains(@class,'modal-header')]/h4[contains(text(),'successfully copied')]/../..//button[contains(text(),'Cancel')]")
	private WebElement b_Cancel_Copy2Clipboard;

	public ViewEditQuote_BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getQuitButton() {
		return b_Quit;
	}

	public WebElement getWarningPopup() {
		return warningPopup;
	}

	public WebElement getOkButn_warningPopup() {
		return b_Warn_Ok;
	}

	/**
	 * This will click on Quit button and click Ok on Warning popup in try block
	 */
	public void clickQuitButton() {
		Syncronization.waitForClickable(b_Quit, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		b_Quit.click();
		try {
			if (warningPopup.isDisplayed()) {
				b_Warn_Ok.click();
			}
		}

		catch (Exception e) {
			CommonFunctions.alertAccept(driver);
		}
	}

	public GetSitePopUpPage clickGetSite() {
		Syncronization.waitForClickable(b_GetSite, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		b_GetSite.click();
		return (new GetSitePopUpPage(driver));
	}

	/**
	 * It will click OK on error pop up only if displayed.
	 * 
	 * @throws NoSuchElementException
	 */
	public void clickOkErrorPopup() throws NoSuchElementException {

		
		if (b_Ok_Errorpopup.isDisplayed()) {
			b_Ok_Errorpopup.click();
		}

	}

	/**
	 * It will click CANCEL on error pop up only if displayed, and print its
	 * message.
	 * 
	 * @throws NoSuchElementException
	 */
	public void clickCancel_ErrorPopup(int time_Seconds) throws NoSuchElementException {

		
		if (p_ErrorPopup.isDisplayed()) {
			Reporter.log("Error Popup Displayed with following message : " + t_Message_errorPopup.getText());
//			ScreenCapture.getScreen("verify_ClickOnRecord_withOrderNumber", driver);
			b_Cancel_ErrorPopup.click();
		}

	}

	public GetCustomerOrderPage clickGetCustomerOrder() {
		Syncronization.waitForClickable(b_GetCustomerOrder, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		b_GetCustomerOrder.click();

//		try {
//			clickOkErrorPopup();
//
//		} catch (NoSuchElementException e) {
//			Reporter.log("Global Error Not displayed on clicking Get Customer order. ");
//		}
		return (new GetCustomerOrderPage(driver));
	}

	public GetCustomerProfilePage clickGetCustomerProfile() {
		Syncronization.waitForClickable(b_GetCustomerProfile, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);

		b_GetCustomerProfile.click();

//		try {
//			clickOkErrorPopup();
//			// We can add soft assert here
//
//		} catch (NoSuchElementException e) {
//			Reporter.log("Global Error Not displayed on Clicking Get Customer profile. ");
//		}
		return (new GetCustomerProfilePage(driver));
	}

	public ReassignPage clickReassign() {
		Syncronization.waitForClickable(b_Reassign, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		b_Reassign.click();
//		try {
//			warnPopup_Yes.click();
//			Syncronization.waitFor(p_Reassign, Global_VARS.TIMEOUT_MINUTE, driver);
//
//		} catch (Exception e) {
//			Reporter.log("WARNING popup on Reassign not displayed");
//
//		}

		return (new ReassignPage(driver));
	}

	public void clickSave() {
		Syncronization.waitForClickable(b_Save, Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		b_Save.click();
		Syncronization.waitForClickable(b_Submit, Global_VARS.TIMEOUT_MINUTE, driver);
		Reporter.log("Clicked on save buton. ");
	}

	public String getProfileName() {
		return t_profileName.getText();
	}

	public WebElement getServiceinformationTabLocator() {
		return serviceInformationTab;
	}

	public ServiceInformationPage clickServiceInformationTab() {
		serviceInformationTab.click();
		return (new ServiceInformationPage(driver));
	}

	public WebElement getSubmitButon() {
		return b_Submit;
	}

}
