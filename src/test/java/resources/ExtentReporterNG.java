package resources;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporterNG implements IReporter {

	private static final String OUTPUT_FOLDER = "test-output/";
	private static final String FILE_NAME = "Extent.html";

	private ExtentReports extent;
	private ExtentHtmlReporter htmlReporter;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

		init();
		extent = new ExtentReports(outputDirectory + File.separator + "ExtentReportsTestNG.html", true);
//           extent.attachReporter(htmlReporter);
//         extent.setReportUsesManualConfiguration(true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}

		for (String s : Reporter.getOutput()) {
			extent.setTestRunnerOutput(s);
		}

		extent.flush();
	}

	private void init() {
		htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
		htmlReporter.config().setDocumentTitle("ExtentReports - Created by TestNG Listener");
		htmlReporter.config().setReportName("ExtentReports - Created by TestNG Listener");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setTheme(Theme.STANDARD);

	}

	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				/*
				 * test.getTest(). = getTime(result.getStartMillis()); test.getTest().endedTime
				 * = getTime(result.getEndMillis());
				 */

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				String message = "Test " + status.toString().toLowerCase() + "ed";

				if (result.getThrowable() != null) {
					message = result.getThrowable().getMessage();

					test.log(status, message);
				}

				else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
				}

				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));

//                extent.endTest(test);
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}