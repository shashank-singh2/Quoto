package testScripts;


import org.testng.annotations.DataProvider;

import mainUtilities.ExcelUtilities;
import mainUtilities.Global_VARS;

public class DataProviderClass {

	@DataProvider(name = "NS_Data")
	public static String[][] dataNS() {
		String[][] arr = null;
	
			arr = ExcelUtilities.dataProvider("NSDataProvider", Global_VARS.TestData_Quoto_xlsx);			
			
		return arr;
	}
	
	@DataProvider(name = "IS_Data")
	public static String[][] dataIS() {
		String[][] arr = null;
	
			arr = ExcelUtilities.dataProvider("ISDataProvider", Global_VARS.TestData_Quoto_xlsx);			
			
		return arr;
	}


}
