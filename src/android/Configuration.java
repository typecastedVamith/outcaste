package android;
import java.io.FileReader;
import java.io.IOException; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

/**
 * This class is to read standard configuration for android like device name
 * This is also used to pass credentials & configuration from external file to project
 * Throws null if key doesn't exists or pass wrong key
 */

public class Configuration {
	public static String userName() {
		return parseTestData("userName");
	}
	
	public static String password() {
		return parseTestData("password");
	}
	
	public static String deviceName() {
		return parseTestData("deviceName");
	}
	
	public static String productSearch() {
		return parseTestData("productSearch");
	}
	
	public static String appActivity() {
		return parseTestData("activity");
	}
	
	public static String appPackage() {
		return parseTestData("package");
	}
	

	@SuppressWarnings("unused")
	private static String parseTestData(String key) {
        try {
			Object obj = new JSONParser().parse(new FileReader(System.getProperty("user.dir")+"\\TestData\\testdata.json"));
	        JSONObject jdata = (JSONObject) obj;
	        return (String) jdata.get(key); 
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			// path is wrong or moved
		}
		return null; 
	}

}
