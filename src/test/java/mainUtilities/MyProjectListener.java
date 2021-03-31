package mainUtilities;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.utils.FileUtil;

public class MyProjectListener implements ITestListener, IRetryAnalyzer {
	public static int iPassCount = 0;
	public static int iFailCount = 0;
	public static int iSkipCount = 0;
	Logger log;
	long startTime, stopTime;

	public MyProjectListener() {
		DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_hh_mm_ss");
		Date date = new Date();
		System.setProperty("Long TimeDate", dateFormat.format(date));
//		log = Logger.getLogger(this.getClass());
		// or
		// actiLog=Logger.getLogger(MyProjectListners.class);

	}

	@Override
	public void onTestStart(ITestResult result) {
		log.info("Test script execution has been started: " + new Timestamp(new Date().getTime()));
		log.info(result.getName() + ": this is method started: ");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		iPassCount++;
		System.out.println("Test Pass: " + iPassCount);
		// .MyProjectListener.class...

		log.info("test script Name: '" + result.getName() + "' /Status =====> PASS :)");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		iFailCount++;
		System.out.println("Test Failure: " + iFailCount);
		Reporter.log("Test Failure: " + iFailCount);
		String methodName = result.getName();
		log.info("test has been FAILED : XXXXXX " + methodName + "  XXXXXXX");
		Object object = result.getInstance();
	
		BaseLib obj = (BaseLib) object;
//		or
//		BaseLib obj =(BaseLib) result.getInstance();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
		Date date = new Date();
		try {
			String fileName = result.getMethod().getMethodName();//.replace("Test", "");
			TakesScreenshot screenshot = (TakesScreenshot) obj.driver;
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			File destFile = new File(Global_VARS.BITMAP_PATH +  dateFormat.format(date)+ "_"+fileName + ".png");
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		iSkipCount++;
		System.out.println("Test Skipped: " + iSkipCount);
		Reporter.log("Test Skipped: " + iSkipCount);
		log.info("Test has been Skipped: " + result.getName());

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.info("Test Failed within success percentage");

	}

	@Override
	public void onStart(ITestContext context) {
		startTime = System.currentTimeMillis();
		
		//Setting Logs On Start of Execution
		log = Logger.getLogger(this.getClass());
		BasicConfigurator.configure();
		PropertyConfigurator.configure(Global_VARS.log4jFile);
		log.info("Test Suite Execution started: " + new Timestamp(new Date().getTime()));
	}

	@Override
	public void onFinish(ITestContext context) {
		stopTime = System.currentTimeMillis();
		log.info("Test Suit execution finished: " + new Timestamp(new Date().getTime()));
		log.info("Total time for test suite execution: " + (double) (stopTime - startTime) / 1000 + " seconds");
	}

	/*
	 * Retry Method will keep on executing failed TC when returning true
	 */

	int count = 0;
	int retryLimit = 0;

	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub

		if (count < retryLimit) {
			count++;
			return true;
		}

		return false;
	}

//	@Override
//	public void onStart(ISuite suite) {
//		Reporter.log("suite: I suite listener", true);
//		
//	}
//
//	@Override
//	public void onFinish(ISuite suite) {
//		// TODO Auto-generated method stub
//		
//	}

}
