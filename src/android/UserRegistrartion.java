package android;

import io.appium.java_client.android.AndroidDriver;

public class UserRegistrartion {
	
	private AndroidDriver driver;
	
	UserRegistrartion(AndroidDriver driver){
		this.driver = driver;
	}

	public void logAsValidUser() {
		
		UI ui = new UI(driver);
		WaitExt waitfor = new WaitExt(driver);
		
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
	}
}
