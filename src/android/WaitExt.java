package android;

import java.io.File;
import java.util.List;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
/**
 *  This class for explicitly wait 
 *	Reads Identifiers from object repository file directly
 */


public class WaitExt {
	private AndroidDriver driver;
	
	WaitExt(AndroidDriver driver){
		this.driver = driver;
	}
	
	public void waitforTextToBePresent(String screen,String Element,String locatorType) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.textToBePresentInElementValue((locator(screen,Element,locatorType)),"Search"));
	}
	public void WaitForScreenToBeClickable(String screen,String Element,String locatorType) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable((locator(screen,Element,locatorType))));
	}
	public void waitForElementToBePresent(String screen,String Element,String locatorType) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf((locator(screen,Element,locatorType))));
	}
	public void WaitForElementToDisappear(String screen,String Element,String locatorType) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(Element)));
	}
	
	@SuppressWarnings("unchecked")
	private WebElement locator(String screen,String Ele, String ltr) {
		File inputFile = new File(System.getProperty("user.dir")+"\\ObjectRepository\\testdata.json");
        List<Node> nodes;
		try {
			nodes = new SAXReader().read( inputFile ).getRootElement().selectNodes("/class/"+Ele);
			 for (Node node : nodes) {
			              
				 switch(ltr){
				 case "id":  return driver.findElement(By.id(node.valueOf("@"+ltr)));
				 case "className": return driver.findElement(By.className(node.valueOf("@"+ltr)));
				 case "name": return driver.findElement(By.name(node.valueOf("@"+ltr)));
				 }	 
			}
		} 
		catch (DocumentException e) {
				e.printStackTrace();
		}
		return null;

	}

}
