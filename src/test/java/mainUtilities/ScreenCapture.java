package mainUtilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenCapture {

	
	
	public static void getScreen(String methodName, WebDriver driver) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
		Date date = new Date();
		try {
			
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			File destFile = new File(Global_VARS.BITMAP_PATH +  dateFormat.format(date)+ "_"+methodName + "_failed.png");
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
