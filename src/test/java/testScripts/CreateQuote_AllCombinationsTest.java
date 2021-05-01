package testScripts;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import mainUtilities.BaseLib;
import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;
import pageObjects.createQuotePages.CreateQuotePage;
import pageObjects.createQuotePages.generalTab.General_QuoteSummaryPage;
import pageObjects.dashBoardPages.HomePage;
import pageObjects.dashBoardPages.LoginPage;
import pageObjects.dashBoardPages.MyQuotesTabPage;

public class CreateQuote_AllCombinationsTest extends BaseLib {

	
	
	@BeforeClass(alwaysRun = true)
	@Parameters({"browser", "environment"})
	public void beforeClass(String browserName, String envName) {
		initializeBrowser(browserName, envName);
		loginApplication();
	}
	

	@AfterMethod
	public void afterMethod(ITestResult result) {
		HomePage hp = new HomePage(driver);
		if (!result.isSuccess()) {

			driver.navigate().refresh();
			hp.verifyHomePage();
		}

	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		tearDown();
	}


	public void loginApplication() {
		LoginPage lp = new LoginPage(driver);
		HomePage hp = lp.clickOnUserToLogin();
		hp.verifyHomePage();
	}
	
	private void quitAndVerifyInMyQuotes() {
		General_QuoteSummaryPage qSmry = new General_QuoteSummaryPage(driver);
		Syncronization.waitFor(qSmry.getQuoteID(), Global_VARS.TIMEOUT_ELEMENT, driver);
		String quoteID = qSmry.getQuoteIDText();
		qSmry.clickQuitButton();
		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);
		Syncronization.waitFor(mQuotes.getNewQuote(quoteID), Global_VARS.TIMEOUT_ELEMENT, driver);
		Assert.assertEquals(mQuotes.getFirstRecord().getText(), quoteID);
		Reporter.log("Quote is created succesfully with ID : " + quoteID + ". ");

	}

	@Test(dataProvider = "NS_Data", dataProviderClass = DataProviderClass.class)
	public void createNSQuote_Test_DP(String quoteRequestType, String quoteType, String stm_PM_Scheduling_required,
			String global_Customer_Programs_Quote, String contractingPartyCustomer, String serviceType)
			throws IOException {

		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);
		CreateQuotePage crQuote = mQuotes.clickOnNS_Button();
		crQuote.createNSQuote(quoteRequestType, quoteType.toUpperCase(), contractingPartyCustomer, serviceType,
				stm_PM_Scheduling_required, global_Customer_Programs_Quote);
		quitAndVerifyInMyQuotes();
		
		

	}

	@Test(dataProvider = "IS_Data", dataProviderClass = DataProviderClass.class)
	public void createISQuote_Test_DP(String quoteRequestType, String quoteType, String global_Customer_Programs_Quote,
			String contractingPartyCustomer, String endUserCustomer, String serviceType, String serviceOption) {

		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);
		CreateQuotePage crQuote = mQuotes.clickonIS_Button();
		crQuote.createISQuote(quoteRequestType, quoteType.toUpperCase(), global_Customer_Programs_Quote,
				contractingPartyCustomer, endUserCustomer, serviceType, serviceOption);
		quitAndVerifyInMyQuotes();
	}

}
