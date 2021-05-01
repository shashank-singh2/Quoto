package sampleTest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;

import com.paulhammant.ngwebdriver.NgWebDriver;

import mainUtilities.BaseLib;
import mainUtilities.EventHandler;
import mainUtilities.ExcelUtilities;
import mainUtilities.GetCurrentClassAndMethod;
import mainUtilities.GetProperties;
import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;
import pageObjects.createQuotePages.CreateQuotePage;
import pageObjects.createQuotePages.GetSitePopUpPage;
import pageObjects.createQuotePages.ServiceInformationTab.ServiceInformationPage;
import pageObjects.createQuotePages.generalTab.General_QuoteSummaryPage;
import pageObjects.dashBoardPages.HomePage;
import pageObjects.dashBoardPages.LoginPage;
import pageObjects.dashBoardPages.MyQuotesTabPage;

public class Test {

	public static Logger log = LogManager.getLogger(Test.class.getName());

	@org.testng.annotations.Test
	public static void testRandom() {
		System.setProperty("webdriver.chrome.driver", GetProperties.getPropertyFromFile("chrome.driver"));
		ChromeDriver wdriver = new ChromeDriver();

		EventFiringWebDriver driver = new EventFiringWebDriver(wdriver);

		EventHandler handler = new EventHandler();
		driver.register(handler);

		NgWebDriver ngWebDriver = new NgWebDriver(driver);
		ngWebDriver.waitForAngularRequestsToFinish();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Global_VARS.TIMEOUT_ELEMENT, TimeUnit.SECONDS);
		driver.get(GetProperties.getPropertyFromFile("UAT2url"));
		LoginPage lp = new LoginPage(driver);
		HomePage hp = lp.clickOnUserToLogin();
		hp.verifyHomePage();

		MyQuotesTabPage mQuotes = new MyQuotesTabPage(driver);

		// verifying if My quotes Active or not
		mQuotes.verifyMyQuotesActive();

//		mQuotes.getCol_TextField("ID").sendKeys("438345" + Keys.ENTER);

		mQuotes.searchAndClickOnQuoteID2Open("438863");
		General_QuoteSummaryPage qtSmry = new General_QuoteSummaryPage(driver);
		Syncronization.waitForClickable(qtSmry.getServiceinformationTabLocator(), Global_VARS.TIMEOUT_ELEMENT_SHORT, driver);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServiceInformationPage si = qtSmry.clickServiceInformationTab();
		si.verifyServiceInformationActive();
		si.click_MandatoryFilter_On();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> data = ExcelUtilities.getDataUsingColumnName("NS_Quote_ValidationTest",
				"serviceInformationWithMandatoryField_Test");

		si.set_CustomerLabel_MandatoryElement(data.get("SI_CustomerLabel_Opt_0"), data.get("custLabelvalue_Opt_0"));
		si.set_CustomerLabel_MandatoryElement(data.get("SI_CustomerLabel_Opt_1"), data.get("custLabelvalue_Opt_1"));
		si.click_MandatoryFilter_Off();
		si.set_Value_MandatoryElement(data.get("SI_Value_Opt_1"), data.get("defIndex_opt_1"),
				data.get("defvalue_opt_1"), data.get("defNumValue_opt1"), data.get("leadTimeType_opt_1"),
				data.get("leadTimeDays_opt_1"));

//		si.clickSave();

//		CreateQuotePage crtqt = mQuotes.clickOnNS_Button();
//		
//		
//		//getting test data from Excel sheet
//		Map<String, String> data = ExcelUtilities.getDataUsingColumnName("NS_Quote_ValidationTest", "createNSQuote" );
//		int cpc = Integer.parseInt(data.get("contractingPartyCustomer"));	
//		
//		//creating Quote
//		General_QuoteSummaryPage qtSmry=crtqt.createNSQuote(data.get("quoteRequestType"), data.get("quoteType"), cpc, data.get("serviceType"), 
//				data.get("stm_PM_Scheduling_required"), data.get("global_Customer_Programs_Quote"));
//		
//		//get quote id 
//		String quoteID = qtSmry.getQuoteID();
//		Reporter.log("Quote ID is : "+quoteID);
//		
//		//writing QuoteID in Excel sheet to use further
//		ExcelUtilities.writeDatausingColumnName("NS_Quote_ValidationTest", "createNSQuote", "QuoteID", quoteID);
//		
//		qtSmry.clickQuitButton();
//		System.out.println("clicked on OK warning");
//		

//	    // Going to Home Page
//		hp.clickQuotoLogo();
//		
//		
//		List<WebElement> column_HeaderNameList= driver.findElements(By.xpath("//div[@class='ag-header-container']/div[@class='ag-header-row'][1]/div"));
//		int sizeCol = column_HeaderNameList.size();
//		System.out.println(sizeCol);
//		System.out.println ("lis of WE :  " + column_HeaderNameList.toString());

//		driver.findElement(By.xpath("//section[@id='fixed_form']//button[contains(text(),'NS/VOICE') and @title='Network Services for non-France based customers']")).click();

	}

}
