package android;

import static org.testng.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.By;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class CheckoutProduct {

private AndroidDriver driver;
	
	CheckoutProduct(AndroidDriver driver){
		this.driver = driver;
	}
	
	public void checkoutSpecifiedProduct() {
		
		UI ui = new UI(driver);
		
		 java.util.List<AndroidElement> items = driver.findElements(By.xpath("//android.view.View"));
		  java.util.List<AndroidElement> checkoutProduct = new ArrayList<AndroidElement>();
		  HashMap<String,String> productInfo = new HashMap<String,String>();
		 
		  // Save product details(Item name/Description) to a list as identifiers are not uniquely recognizable 
		  for (AndroidElement products : items) {
		            if(products.getText().contains(Configuration.productSearch())) {
		            	checkoutProduct.add(products);
		            }
	            }
	  
		  // Save name/Description & price to a hashmap - this is to assert
		  for(int i =0;i<checkoutProduct.size();i++) {
			  if(i==0) productInfo.put("Name", checkoutProduct.get(0).getText());	
			  else {
				  	productInfo.put("Price",checkoutProduct.get(i).getText().substring(checkoutProduct.get(i).getText().indexOf("₹"), checkoutProduct.get(i).getText().indexOf("M.R.P.")));
				  	productInfo.put("Descpn", checkoutProduct.get(i).getText().toLowerCase().replace(" ", ""));
			  }  
		  }
		  
		  // Tap on product & assert it with that of product list page
		  new Helper(driver).scrollToTextView(Configuration.productSearch());
		  checkoutProduct.get(0).click();
	       assertTrue(ui.extractText("Login", "CheckoutPrice", "xpath").equals(productInfo.get("Price")));
	       assertTrue(ui.extractText("Login", "CheckoutProductInfo", "xpath").equals(productInfo.get("Name")));
		
	}
	
}
