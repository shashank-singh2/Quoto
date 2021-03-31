package mainUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {

	/**
	 * get property value from file passed in parameters
	 * 
	 * @param key
	 * @param filepath
	 * @return String
	 */

	public static String getPropertyFromFile(String key, String filepath) {
		Properties prop = new Properties();
		FileInputStream fis;
		String value = null;
		try {
			fis = new FileInputStream(new File(filepath));
			prop.load(fis);
			value = prop.getProperty(key);
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (value);

	}

	/**
	 * It will return property value from default config.properties file based on
	 * key
	 * 
	 * @param key
	 * @return String
	 */
	public static String getPropertyFromFile(String key) {
		Properties prop = new Properties();
		FileInputStream fis;
		String value = null;
		try {
			fis = new FileInputStream(new File(Global_VARS.propfile));
			prop.load(fis);
			value = prop.getProperty(key);
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (value);

	}

}
