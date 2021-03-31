package mainUtilities;

import org.openqa.selenium.WebDriver;

public class CommonFunctions {

	public static void alertAccept(WebDriver driver) {
		while (true) {
			try {
//				Thread.sleep(Global_VARS.TIMEOUT_STATIC_SHORT);
				driver.switchTo().alert().accept();
				System.out.println("in try Accepting Alert");

			} catch (Exception e) {
				System.out.println("in catch when Alert not displayed");
				break;
			}
		}

	}

	public static void alertDismiss(WebDriver driver) {
		while (true) {
			try {
				driver.switchTo().alert().dismiss();
			} catch (Exception e) {
				break;
			}

		}

	}

}
