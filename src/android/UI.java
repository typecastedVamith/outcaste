package android;

import java.io.File;
import java.util.List;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

/**
 * This class is dedicated to user actions like click,double click set text,
 *  It read data from object repository which is an XML file where identifiers saved
 */

public class UI {

	private AndroidDriver driver;
	private int clickWaitDuration = 10;
	UI(AndroidDriver driver){
		this.driver = driver;

	}
	UI(){
	}

	// Multiple clicks on passed webElement
	public void click(String screen, String Element, String locatorType, int index, int clickCount) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(locator(screen,Element,locatorType)));
		if(clickCount==2) new Actions(driver).doubleClick(locator(screen,Element,locatorType)).perform();	
		else new Actions(driver).doubleClick(locator(screen,Element,locatorType)).click().perform();	
		}
		
	// Click action to passed webElement/type
	public void click(String screen, String Element,String locatorType, int index) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(locator(screen,Element,locatorType)));
		locator(screen,Element,locatorType).click();
	}
	
	// Set text value to the passed webElement
	public void setData(String screen,String Element,String locatorType,String input) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.elementToBeClickable(locator(screen,Element,locatorType)));
	locator(screen,Element,locatorType).sendKeys(input);
	}
	
	// Keyboard action enter
	@SuppressWarnings("unchecked")
	public void keyActionEnter() {
		((AndroidDriver<AndroidElement>) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
	}
	
	
	// Extract text from webElements
	public String extractText(String screen,String Element,String locatorType) {
		return locator(screen,Element,locatorType).getText();
	}
		
	// returns webElement based on type(id/x-path/class/text) to the caller
	@SuppressWarnings("unchecked")
	private WebElement locator(String screen,String Ele, String ltr) {
		File inputFile = new File(System.getProperty("user.dir")+"\\ObjectRepository\\Login.xml");
        List<Node> nodes;
		try {
			nodes = new SAXReader().read( inputFile ).getRootElement().selectNodes("/class/"+Ele);
			 for (Node node : nodes) {
			              
				 switch(ltr){
				 case "id":  return driver.findElement(By.id(node.valueOf("@"+ltr)));
				 case "className": return driver.findElement(By.className(node.valueOf("@"+ltr)));
				 case "name": return driver.findElement(By.name(node.valueOf("@"+ltr)));
				 case "xpath": return driver.findElement(By.name(node.valueOf("@"+ltr)));
				 }	 
			}
		} 
		catch (DocumentException e) {
				e.printStackTrace();
		}
		return null;
}
}
