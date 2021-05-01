package mainUtilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateDriver {

	// instance of singleton class
	private static  CreateDriver instance = null;

	private WebDriver driver;

	// Constructor
	private CreateDriver() {
		System.setProperty("webdriver.chrome.driver", "./exefiles/chromedriver.exe");
		driver = new ChromeDriver();
	}

	// TO create instance of class
	public static CreateDriver getInstance() {
		if (instance == null) {
			instance = new CreateDriver();
		}
		return instance;
	}

	// To get driver
	public WebDriver getDriver() {
		return driver;
	}

}
