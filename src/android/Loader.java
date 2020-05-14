package android;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;



public class Loader {

	 public static void main(String[] args) throws IOException, InterruptedException{	
		// Has to be run externally as a batch	
		//Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\StartNode.bat");
		
		// Initialization client for android
		File appPath = new File("src");
		File apk = new File(appPath,"original.apk");
		DesiredCapabilities AppiumClient  = new DesiredCapabilities();
		AppiumClient.setCapability(MobileCapabilityType.DEVICE_NAME, Configuration.deviceName());
		AppiumClient.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 40000);
		AppiumClient.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");
		AppiumClient.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		AppiumClient.setCapability("resetKeyboard", "true");
		AppiumClient.setCapability("unicodeKeyboard", true);
		AppiumClient.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
		AppiumClient.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());
		AndroidDriver<AndroidElement> androidDriver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),AppiumClient);
		androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		androidDriver.startActivity(new Activity(Configuration.appPackage(), Configuration.appActivity()));

		
		// login as a valid user with credentials in credentials json
		new UserRegistrartion(androidDriver).logAsValidUser();
		new CheckoutProduct(androidDriver).checkoutSpecifiedProduct(); 
	        
	}	
}
