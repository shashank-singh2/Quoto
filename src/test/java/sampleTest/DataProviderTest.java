package sampleTest;

import java.io.IOException;
//import test.ExcelDataProvider;
import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import mainUtilities.ExcelUtilities;

public class DataProviderTest {

	String classN = this.getClass().getName();

	String className = classN.substring(classN.lastIndexOf('.') + 1, classN.length());

	@Test(dataProvider = "data")
	public void dpTest(String a, String b, String c, String d, String e, String f, String g) throws IOException {

		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
		System.out.println(g);

	}// dpTest method

	@DataProvider(name = "data")
	public String[][] dataP() {
		String[][] arr = null;
		try {
			arr = ExcelUtilities.getDataForDataProvider(className, "createNSQuote_Test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arr;
	}

}// class
