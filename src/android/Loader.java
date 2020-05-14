package android;


import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;



public class Loader {

	@Test 
	 public static void main(String[] args) throws IOException, InterruptedException{	
		/*
		 * Setup of appium
		 * 
		 */
		
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\StartNode.bat");
		//File appPath = new File("src");
		//File apk = new File(appPath,"original.apk");
		DesiredCapabilities AppiumClient  = new DesiredCapabilities();
		AppiumClient.setCapability(MobileCapabilityType.DEVICE_NAME, Configuration.deviceName());
		AppiumClient.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 40000);
		AppiumClient.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");
		AppiumClient.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		AppiumClient.setCapability("resetKeyboard", "true");
		AppiumClient.setCapability("unicodeKeyboard", true);
		AppiumClient.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
	//	AppiumClient.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());
		AndroidDriver<AndroidElement> androidDriver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),AppiumClient);
		androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//	androidDriver.startActivity(new Activity(AndroidUIObjects.sleepmapperPackage, AndroidUIObjects.sleepmapperActivity));
		
		Helper helper = new Helper(androidDriver);
		UI ui = new UI(androidDriver);
		WaitExt waitfor = new WaitExt(androidDriver);
		
		ui.click("Login", "ExistingCustomerLoginButton", "id", 0);	
		ui.click("Login", "ExistingCustomerEnterMobileno", "className", 0);	
		waitfor.WaitForElementToDisappear("Login", "ProgressBar", "id");
		ui.setData("Login", "ExistingCustomerEnterMobileno", "className",Configuration.userName());
		ui.click("Login", "ExistingCustomerCotinue", "className", 0);	
		waitfor.WaitForScreenToBeClickable("Login", "ExistingCustomerEnterPwd", "className");
		ui.setData("Login", "ExistingCustomerEnterPwd", "className", Configuration.password());
		ui.click("Login", "ExistingCustomerCotinue", "className", 0);	
		waitfor.waitForElementToBePresent("Login", "VoiceSerch", "id");
		ui.click("Login", "SearchProduct", "className", 0);	
		ui.setData("Login", "SearchProduct", "className", "65 inch tv");
		ui.keyActionEnter();
		ui.click("Login", "PrimeEligible", "xpath", 0);	
		
		  java.util.List<AndroidElement> items = androidDriver.findElements(By.xpath("//android.view.View"));
		  java.util.List<AndroidElement> checkoutProduct = new ArrayList<AndroidElement>();
		  
		  HashMap<String,String> productInfo = new HashMap<String,String>();
			 System.out.println(items.size());	

		  for (AndroidElement products : items) {
		            if(products.getText().contains(Configuration.productSearch())) {
		            	checkoutProduct.add(products);
		            }
	            }
	  
		  for(int i =0;i<checkoutProduct.size();i++) {
			  if(i==0) productInfo.put("Name", checkoutProduct.get(0).getText());	
			  else {
				  	productInfo.put("Price",checkoutProduct.get(i).getText().substring(checkoutProduct.get(i).getText().indexOf("₹"), checkoutProduct.get(i).getText().indexOf("M.R.P.")));
				  	productInfo.put("Descpn", checkoutProduct.get(i).getText().toLowerCase().replace(" ", ""));
			  }  
		  }
		  
		  // inside the edit button
		  checkoutProduct.get(0).click();
	       assertTrue(ui.extractText("Login", "CheckoutPrice", "xpath").equals(productInfo.get("Price")));
	       assertTrue(ui.extractText("Login", "CheckoutProductInfo", "xpath").equals(productInfo.get("Name")));
	        
	}	
	
}
