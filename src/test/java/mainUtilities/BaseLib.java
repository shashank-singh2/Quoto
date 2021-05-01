package mainUtilities;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.paulhammant.ngwebdriver.NgWebDriver;

public class BaseLib {
	WebDriver wdriver;
	public EventFiringWebDriver driver;
	EventHandler handler;

//	@BeforeClass(alwaysRun = true)
//	@Parameters({"browser", "environment"})

	public void initializeBrowser( String browserName, String envName) {
		if (browserName.equalsIgnoreCase("Firefox")) {
			String key = "webdriver.gecko.driver";
			String value = GetProperties.getPropertyFromFile("gecko.driver");
			System.setProperty(key, value);
			wdriver = new FirefoxDriver();
			Reporter.log("Firefox launched", true);
		} else if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", GetProperties.getPropertyFromFile("chrome.driver"));
			wdriver = new ChromeDriver();
			Reporter.log("Chrome launched", true);
		} else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", GetProperties.getPropertyFromFile("IE.driver"));
			
			// using "DesiredCapabilities" class to ignore IE browser security settings and
			// Zoom setting.
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			InternetExplorerOptions ieOpt = new InternetExplorerOptions(ieCapabilities);
			wdriver = new InternetExplorerDriver(ieOpt);
			Reporter.log("IE launched", true);
		}

		driver = new EventFiringWebDriver(wdriver);

		handler = new EventHandler();
		driver.register(handler);
	
//		
//		NgWebDriver ngWebDriver = new NgWebDriver(driver);
//		ngWebDriver.waitForAngularRequestsToFinish();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Global_VARS.TIMEOUT_ELEMENT, TimeUnit.SECONDS);

//		driver.manage().timeouts().pageLoadTimeout(Global_VARS.TIMEOUT_ELEMENT, TimeUnit.SECONDS);

		driver.get(GetProperties.getPropertyFromFile(envName));
		Reporter.log("Navigating to url", true);

	}

	/*
	@AfterMethod
	public void postCondition() {
		if (result.isSuccess()){
			Reporter.log("Script Passed", true);
		}
		else{
			String fileName = result.getMethod().getMethodName();
			ScreenshotLib sLib = new ScreenshotLib();
			sLib.getScreenshot(driver, fileName);
		}
		driver.manage().deleteAllCookies();
		
		
	}
	*/

	
	
//	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.unregister(handler);
		driver.close();
		Reporter.log("Browser Closed", true);
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	
	/*
	@AfterTest(alwaysRun = true)
	public void afterTest() {
		try {

			if ( driver != null) {
			driver.unregister(handler);
			driver.close();
			driver.quit();
			}
			try {
				finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (NoSuchSessionException e) {
			Reporter.log("All driver are closed");
		}

	
	}

*/

}



