package pageObjects.createQuotePages.ServiceInformationTab;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import mainUtilities.Global_VARS;
import mainUtilities.Syncronization;
import pageObjects.createQuotePages.ViewEditQuote_BasePage;

public class ServiceInformationPage extends ViewEditQuote_BasePage {

//	private static ServiceInformationPage instance = null;
	WebDriver driver;

	@FindBy(id = "mandatoryFilterButton")
	private WebElement b_MandatoryFilter;

	// All Editable as per presence of VOQ checkbox
	@FindBy(xpath = "//div[@class='ag-body-viewport-wrapper']//qto-quote-check-box-renderer/input/../../..")
	private List<WebElement> f_AllEditableFields_SI;

	// Customer label box
	@FindBy(xpath = "//div[@class='ag-body-viewport-wrapper']//qto-customer-label-editor/input")
	private WebElement t_CustomerLabel_editorAfterClick;

	// Value box
	@FindBy(xpath = "//div[@class='ag-body-viewport-wrapper']//qto-value-combo-box-editor//select")
	private WebElement d_ValueBox_ComboAfterClick;
	@FindBy(xpath = "//div[@class='ag-body-viewport-wrapper']//qto-value-text-input-editor//input")
	private WebElement t_ValueBox_InputAfterClick;
	@FindBy(xpath = "//div[@class='ag-body-viewport-wrapper']//qto-value-number-input-editor//input")
	private WebElement t_ValueBox_Number_InputAfterClick;

	// LeadTime Value Box
	@FindBy(xpath = "//div[@class='ag-body-viewport-wrapper']//qto-value-lead-time-editor//input")
	private WebElement t_LeadTime_ValueText_AfterClick;
	@FindBy(xpath = "//div[@class='ag-body-viewport-wrapper']//qto-value-lead-time-editor//select")
	private WebElement d_LeadTime_ValueSelect_AfterClick;

	public ServiceInformationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/* not using Singleton approach as not success when runnning multple class
	public static ServiceInformationPage getInstance(WebDriver driver) {
		if (instance == null) {
			instance = new ServiceInformationPage(driver);
		}
		return instance;
	}
*/
	
	public void verifyServiceInformationActive() {
		Assert.assertEquals(getServiceinformationTabLocator().getAttribute("class"), "nav-link active",
				"Service Information tab is not active");
		Reporter.log("Service information tab is active");
	}

	/**
	 * this will verify if Mandatory filter text is Off, if yes then Click and
	 * verify after click should change to ON
	 */
	public void click_MandatoryFilter_On() {
		if (b_MandatoryFilter.getText().trim().equalsIgnoreCase("Mandatory Filter Off")) {
			b_MandatoryFilter.click();
			Assert.assertTrue(b_MandatoryFilter.getText().trim().equalsIgnoreCase("Mandatory Filter On"),
					"Mandatory filter not changed from Off to On");
			Reporter.log("Mandatory filter Succesfully changed from Off to On");
		} else {
			Reporter.log("mandatory Filter is already ON");
		}

	}

	/**
	 * this will verify if Mandatory filter text is On, if yes then Click and verify
	 * after click should change to OFF
	 */
	public void click_MandatoryFilter_Off() {
		if (b_MandatoryFilter.getText().trim().equalsIgnoreCase("Mandatory Filter On")) {
			b_MandatoryFilter.click();
			Assert.assertTrue(b_MandatoryFilter.getText().trim().equalsIgnoreCase("Mandatory Filter Off"),
					"Mandatory filter not changed from ON to OFF");
			Reporter.log("Mandatory filter Succesfully changed from ON to OFF");
		} else {
			Reporter.log("mandatory Filter is already OFF");
		}

	}



	/**
	 * this method will Click Mandatory Filter to set to ON and enter all the mandatory Customer Label Fields as per arguments passed
	 * @param option
	 * @param custLabelvalue
	 */
	public void set_CustomerLabel_MandatoryElement(String option, String custLabelvalue) {
		click_MandatoryFilter_On();
		int len = f_AllEditableFields_SI.size();
		System.out.println("Total Mandatory fields are : " + len);
		for (int i = 0; i < len; i++) {

			String rowId = f_AllEditableFields_SI.get(i).getAttribute("row-id");
			System.out.println("Rw ID for Cust label: " + rowId);
			// getting Customer label field before click
			WebElement custLabel = driver.findElement(
					By.xpath("//div[@class='ag-body-viewport-wrapper']//div[@class='ag-body-container']/div[@row-id='"
							+ rowId + "']/div[contains(@col-id,'option" + option + "_customerLabelColumn')]//div"));
			Actions act = new Actions(driver);
			act.moveToElement(custLabel).click().build().perform();

			try {
// Checking if Customer label field can accept input values
				if (t_CustomerLabel_editorAfterClick.isDisplayed()) {

					t_CustomerLabel_editorAfterClick.sendKeys(Keys.CONTROL + "a");
					t_CustomerLabel_editorAfterClick.sendKeys(Keys.DELETE);
					t_CustomerLabel_editorAfterClick.sendKeys(custLabelvalue + "_" + i);
					Reporter.log("Value entered in " + rowId + " field : " + custLabelvalue + "_" + i);

				}
			} catch (Exception e) {
				Reporter.log("Customer Label for field with " + rowId + " is not editable. ");
				continue;

			}

		}
	}

	/**
	 * this method will Click Mandatory Filter to set to ON and enter all the mandatory Value Fields as per arguments passed 
	 * @param option
	 * @param defIndex
	 * @param defvalue
	 * @param defNumValue
	 * @param leadTimeType
	 * @param leadTimeDays
	 */
	public void set_Value_MandatoryElement(String option, String defIndex, String defvalue, String defNumValue,
			String leadTimeType, String leadTimeDays) {
		click_MandatoryFilter_On();
		int len = f_AllEditableFields_SI.size();
		System.out.println("Total mandatory fields are : " + len);
		for (int i = 0; i < len; i++) {

			String rowId = f_AllEditableFields_SI.get(i).getAttribute("row-id");
			System.out.println("Rw ID is for Value : " + rowId);

			List<WebElement> v_fields = driver
					.findElements(By.xpath("//div[@class='ag-body-viewport-wrapper']//div[@row-id='" + rowId
							+ "']/div[@col-id='option" + option + "_valueColumn']//child::*"));

				String tag_beforeclick = v_fields.get(v_fields.size() - 1).getTagName();
				if (tag_beforeclick.equalsIgnoreCase("div")) {
					// getting Value field before click
					WebElement valueField = v_fields.get(1);
					Actions act = new Actions(driver);
					act.moveToElement(valueField).click().build().perform();
					v_fields = driver.findElements(By.xpath("//div[@class='ag-body-viewport-wrapper']//div[@row-id='"
							+ rowId + "']/div[@col-id='option" + option + "_valueColumn']//child::*"));
//			 comparing row ID to separate the operations
					if (rowId.equalsIgnoreCase("leadtime")) {

						if (leadTimeType.equalsIgnoreCase("Manual")) {
							System.out.println("BEFORE laed time value entered");
							t_LeadTime_ValueText_AfterClick.sendKeys("" + leadTimeDays);
							System.out.println("laed time value entered");
							Reporter.log("Value Entered in " + rowId + " value field : " + leadTimeDays);
							System.out.println("Value Entered in " + rowId + " value field : " + leadTimeDays);

						} else {

							System.out.println("NSIDE lead time select laed time value entered");
							Select sel = new Select(d_LeadTime_ValueSelect_AfterClick);
							sel.selectByValue(leadTimeType);
							WebElement selectedVal = sel.getFirstSelectedOption();
							Reporter.log("Value Selected in " + rowId + " value field : " + selectedVal.getText());
						}
					}

					else {

						String tag = v_fields.get(0).getTagName();

						if (tag.equalsIgnoreCase("qto-value-combo-box-editor")) {

							Select sel1 = new Select(d_ValueBox_ComboAfterClick);
							int defI = Integer.parseInt(defIndex);
							sel1.selectByIndex(defI);
							WebElement selectedVal = sel1.getFirstSelectedOption();
							Reporter.log("Value Selected in " + rowId + " Value field : " + selectedVal.getText());

						}

						else if (tag.equalsIgnoreCase("qto-value-text-input-editor")) {
							t_ValueBox_InputAfterClick.click();
							t_ValueBox_InputAfterClick.sendKeys(Keys.CONTROL + "a");
							t_ValueBox_InputAfterClick.sendKeys(Keys.DELETE);
							t_ValueBox_InputAfterClick.sendKeys(defvalue + "_" + i);
							Reporter.log("Value entered in " + rowId + " value field : " + defvalue + "_" + i);
						} else if (tag.equalsIgnoreCase("qto-value-number-input-editor")) {
							t_ValueBox_Number_InputAfterClick.click();
							t_ValueBox_Number_InputAfterClick.sendKeys(Keys.CONTROL + "a");
							t_ValueBox_Number_InputAfterClick.sendKeys(Keys.DELETE);
							t_ValueBox_Number_InputAfterClick.sendKeys("" + defNumValue + i);

						}

					}

				} else {
					continue;
				}

		}
	}

}
