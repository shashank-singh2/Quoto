package testScripts;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.paulhammant.ngwebdriver.NgWebDriver;

import mainUtilities.BaseLib;
import mainUtilities.ExcelUtilities;
import mainUtilities.GetCurrentClassAndMethod;
import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;
import pageObjects.createQuotePages.CreateQuotePage;
import pageObjects.createQuotePages.GetCustomerOrderPage;
import pageObjects.createQuotePages.GetCustomerProfilePage;
import pageObjects.createQuotePages.GetSitePopUpPage;
import pageObjects.createQuotePages.ReassignPage;
import pageObjects.createQuotePages.ServiceInformationTab.ServiceInformationPage;
import pageObjects.createQuotePages.generalTab.General_QuoteSummaryPage;
import pageObjects.createQuotePages.generalTab.General_SiteAndContractForQuote;
import pageObjects.dashBoardPages.HomePage;
import pageObjects.dashBoardPages.LoginPage;
import pageObjects.dashBoardPages.MyQuotesTabPage;

public class NS_Quote_ValidationTest extends BaseLib {

	String classN = this.getClass().getName();

	String className = classN.substring(classN.lastIndexOf('.') + 1, classN.length());
//	public static Logger log = LogManager.getLogger(Test.class.getName());

	
	@BeforeClass(alwaysRun = true)
	@Parameters({"browser", "environment"})
	public void beforeClass(String browserName, String envName) {
		initializeBrowser(browserName, envName);
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		HomePage hp = new HomePage(driver);
		if (result.isSuccess()) {

			hp.clickQuotoLogo();
		} else {
			Reporter.log("Home page Quoto Logo not clickable hence directly hitting URL. ");
			driver.navigate().refresh();
			hp.verifyHomePage();
			System.out.println("	Reporter.log(\"Home page Quoto Logo not clickable hence directly hitting URL\");");
		}
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		tearDown();
	}
	
	

	@Test()
	public void openApplication_Test(ITestContext context) throws Exception {

		// getting method name to pass in TestData Excel sheet
		String methodName = GetCurrentClassAndMethod.getMethod();

		// getting test data from Excel sheet to login and open application
		Map<String, String> data = ExcelUtilities.getDataUsingColumnName(className, methodName);
		String userName = data.get("User");
		System.out.println("user is : " + userName);

		// logging into application to display Home page
		LoginPage lp = new LoginPage(driver);
		HomePage hp = lp.clickOnUserToLogin(userName);

		// Verifying Home page with Title and Quoto Logo

		hp.verifyHomePage();

		Reporter.log("Current Active Tab is : " + hp.getCurrentActiveTab() + ". ");

	}

	@Test(dependsOnMethods = "openApplication_Test")
	public void createNSQuote_Test(ITestContext context) throws Exception {

		// getting method name to pass in TestData Excel sheet
		String methodName = GetCurrentClassAndMethod.getMethod();

		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);

		// verifying if My quotes Active or not
		mQuotes.verifyMyQuotesActive();
		CreateQuotePage crtqt = mQuotes.clickOnNS_Button();

		// getting test data from Excel sheet
		Map<String, String> data = ExcelUtilities.getDataUsingColumnName(className, methodName);
//		int cpc = Integer.parseInt(data.get("contractingPartyCustomer"));

		// creating Quote
		General_QuoteSummaryPage qtSmry = crtqt.createNSQuote(data.get("quoteRequestType"), data.get("quoteType"),
				data.get("contractingPartyCustomer"), data.get("serviceType"), data.get("stm_PM_Scheduling_required"),
				data.get("global_Customer_Programs_Quote"));

		// get quote id
		Syncronization.waitFor(qtSmry.getQuoteID(), Global_VARS.TIMEOUT_ELEMENT, driver);
		String quoteID = qtSmry.getQuoteIDText();
		Reporter.log("Quote ID is : " + quoteID + ". ");
		context.setAttribute("QuoteID", quoteID);

		// writing QuoteID in Excel sheet to use further
		ExcelUtilities.writeDatausingColumnName(className, methodName, "QuoteID", quoteID);

		// pressingQuit
		qtSmry.clickQuitButton();

		// Waiting to load elements by verifying first record
		Syncronization.waitFor(mQuotes.getNewQuote(quoteID), Global_VARS.TIMEOUT_MINUTE, driver);

		// searching quote in My Quotes Tab
		mQuotes.getCol_TextField("ID").sendKeys(quoteID + Keys.ENTER);
		Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
		Assert.assertEquals(mQuotes.getFirstRecord().getText(), quoteID,
				"Quote ID created not found in My Quotes tab. ");

	}

	@Test(dependsOnMethods = "createNSQuote_Test")
	public void populateGetSite_Test(ITestContext context) throws Exception {
		Map<String, String> data = ExcelUtilities.getDataUsingColumnName(className,
				GetCurrentClassAndMethod.getMethod());
		// Entering and clicking on Quote ID in myquotes dashboard Quote ID column
		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);
		mQuotes.getCol_TextField("ID").sendKeys((String) context.getAttribute("QuoteID") + Keys.ENTER);
		mQuotes.getFirstRecord().click();

		// In general tan view edit quote selecting get site
		General_QuoteSummaryPage qtSmry = new General_QuoteSummaryPage(driver);
		Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
		GetSitePopUpPage getSite = qtSmry.clickGetSite();
		String data_CountryName = data.get("countryName");

		// clciking on find button without selecting country
		getSite.verifyFindWithoutCountry();

		// Selecting country from dropdown
		getSite.setCountryName(data_CountryName);

		// Clicking find button and verifying whether changing to edit
		getSite.clickAndVerifyFindButton();

		// verifying the data displayed in grid after clicking find and selecting one
		getSite.verify_Click_OnSiteRecord();

		// clicking site preview and populate button
		Map<String, String> siteData = getSite.verify_click_SitePreviewPopup_Populate();
		getSite.clickProceed_Warning_SiteOrder(qtSmry.getQuoteTypeText());
		// Saving data
		getSite.clickSave();

		General_SiteAndContractForQuote SCFQ = new General_SiteAndContractForQuote(driver);

		// Navigating to Site and contract for quote to verify the site population
		SCFQ.clickSite_Contract_4_Quote_Tab();
		SCFQ.verifyCountryNameAfterGetSite(data_CountryName);
		SCFQ.verifyCoreSiteIDAfterGetSite(siteData.get("Core Site ID"));

	}

	@Test(dependsOnMethods = "createNSQuote_Test")
	public void verifyGetCustomerOrder_Test(ITestContext context) throws Exception {

		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);
		mQuotes.searchAndClickOnQuoteID2Open((String) context.getAttribute("QuoteID"));
		General_QuoteSummaryPage qtSmry = new General_QuoteSummaryPage(driver);
		Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
		GetCustomerOrderPage gtco = qtSmry.clickGetCustomerOrder();
		gtco.verifyGetCustomerOrderPopup();

		// clicking Find without any search criteria
		gtco.clickAndVerifyFindButton();
		// fetching the first order ID from displayed record
		String orderID = gtco.verify_ClickOnRecord_withOrderNumber();

		// searching again with order ID
		gtco.clickAndVerifyFindButton();
		gtco.getOrderNumberField().sendKeys(orderID);
		gtco.clickAndVerifyFindButton();

		// clicking and Verifying searched record to Open preview order
		String orderIDsearched = gtco.verify_ClickAndPreviewOnRecord_withOrderNumber();
		Assert.assertEquals(orderIDsearched, orderID);
		Reporter.log("Searched and displayed order ID in grid is same Get Customer Order Popup");

		// Storing the data from order preview in MAP and Clicking on Populate

		Map<String, String> gtco_data = gtco.click_PopulateQuote_OrderPreview();
		System.out.println(gtco_data);

	}

	@Test(dependsOnMethods = "createNSQuote_Test")
	public void verifyGetCustomerProfile_Test(ITestContext context) throws Exception {
		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);
		mQuotes.searchAndClickOnQuoteID2Open((String) context.getAttribute("QuoteID"));
		General_QuoteSummaryPage qtSmry = new General_QuoteSummaryPage(driver);
		Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
		GetCustomerProfilePage gcp = qtSmry.clickGetCustomerProfile();
		gcp.verifyCustomerProfilePopup();
		gcp.verifyHeaderValueSingleCustomer(qtSmry.getCustomer_ContractingParty(), qtSmry.getIC01());
		gcp.verifyNumberOfSection(4);
		gcp.clickAllChkBx2SelectAndVerify();
		gcp.clickPopulateCheckedFields();
		qtSmry.clickSave();

	}

	@Test(dependsOnMethods = "createNSQuote_Test")
	public void verifyReassign_Test(ITestContext context) throws Exception {
		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);
		mQuotes.searchAndClickOnQuoteID2Open((String) context.getAttribute("QuoteID"));
		General_QuoteSummaryPage qtSmry = new General_QuoteSummaryPage(driver);
		Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
		String pName = qtSmry.getProfileName().trim();
		String[] arrProfName = pName.split(" ");
		String profName = arrProfName[1].toUpperCase() + " " + arrProfName[0];
		System.out.println("initial :" + pName + ", later :" + profName);
		ReassignPage reassign = qtSmry.clickReassign();
		Syncronization.waitFor(reassign.getReassignPopup(), Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		reassign.reassignAllRoleToSameUser(profName);
		qtSmry.verifyQuoteActorsValue(pName);

	}

	@Test(dependsOnMethods = "createNSQuote_Test")
	public void serviceInformationWithMandatoryField_Test(ITestContext context) throws Exception {
		Map<String, String> data = ExcelUtilities.getDataUsingColumnName(className,
				GetCurrentClassAndMethod.getMethod());

		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);
		mQuotes.searchAndClickOnQuoteID2Open((String) context.getAttribute("QuoteID"));
		General_QuoteSummaryPage qSmry = new General_QuoteSummaryPage(driver);
		Syncronization.waitForClickable(qSmry.getServiceinformationTabLocator(), Global_VARS.TIMEOUT_ELEMENT, driver);
		Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
		ServiceInformationPage servInfo = qSmry.clickServiceInformationTab();
		servInfo.verifyServiceInformationActive();
		servInfo.click_MandatoryFilter_On();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		servInfo.set_CustomerLabel_MandatoryElement(data.get("SI_CustomerLabel_Opt_0"),
				data.get("custLabelvalue_Opt_0"));
		servInfo.set_CustomerLabel_MandatoryElement(data.get("SI_CustomerLabel_Opt_1"),
				data.get("custLabelvalue_Opt_1"));
		servInfo.set_Value_MandatoryElement(data.get("SI_Value_Opt_1"), data.get("defIndex_opt_1"),
				data.get("defvalue_opt_1"), data.get("defNumValue_opt1"), data.get("leadTimeType_opt_1"),
				data.get("leadTimeDays_opt_1"));
		servInfo.clickSave();

	}
}
