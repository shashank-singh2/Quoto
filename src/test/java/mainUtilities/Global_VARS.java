package mainUtilities;

import java.io.File;

/**
 * Global Variable :Class AppDefaults , Class Defaults, OutputPath Defaults
 * Timeout Defaults
 * 
 */
public class Global_VARS {
	//target app defaults
	
	public static final String BROWSER = "firefox";
	public static final String PLATFORM = "Windows10";
	public static final String ENVIRONMENT = "local";
	public static final String DEF_BROWSER = null;
	public static final String DEF_ENVIRONMENT = null;
	public static final String PROPS_PATH = null;

	//driver class defaults
	public static final String propfile = ".\\config.properties";
	public static final String SE_PROPS = new File(propfile).getAbsolutePath();
	
	//resources
	public static final String TEST_RESOURCES_PATH = "src/test/java/resources/";
	public static final String log4jFile = TEST_RESOURCES_PATH+"log4j.properties";
	public static final String TEST_DATA = TEST_RESOURCES_PATH +"testData/";
	public static final String TestData_Quoto_xlsx = TEST_DATA + "TestData_Quoto.xlsx";
	
	
	//test output path defaults
	public static final String TEST_OUTPUT_PATH = "testOutput/";
	public static final String LOGFILE_PATH = TEST_OUTPUT_PATH + "logs/";
	public static final String REPORT_PATH = TEST_OUTPUT_PATH + "Reports/";
	public static final String BITMAP_PATH = TEST_OUTPUT_PATH + "Bitmaps/";
	
	//timeout defaults
	public static final int TIMEOUT_MINUTE = 60;
	public static final int TIMEOUT_SECOND = 1;
	public static final int TIMEOUT_ZERO = 0;
    public static final int TIMEOUT_ELEMENT = 15;
    public static final int TIMEOUT_STATIC_SHORT = 3000;
    public static final int TIMEOUT_ELEMENT_SHORT = 3;
    public static final int TIMEOUT_STATIC_LONG = 8000;
	

}
