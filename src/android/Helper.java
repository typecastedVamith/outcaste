package android;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import io.appium.java_client.TouchAction;
import static java.time.Duration.ofSeconds;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import io.appium.java_client.android.AndroidBatteryInfo;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;

/**
 *  General helper class for swipe , scroll , taking screenshot , change orientation
 *  close, launch rotate set clocks
 */

public class Helper {
	
	@SuppressWarnings("rawtypes")
	private AndroidDriver androiddriver;
	
	Helper(@SuppressWarnings("rawtypes") AndroidDriver androiddriver){		
		this.androiddriver = androiddriver;
	}
	
	public void scrollToTextView(String text){
		androiddriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));");
	}
	
	public void swipeElements(WebElement from, WebElement To, int duration){
		@SuppressWarnings("rawtypes")
		TouchAction t=new TouchAction(androiddriver);
		t.longPress(longPressOptions().withElement(element(from)).withDuration(ofSeconds(2))).moveTo(element(To)).release().perform();
	}
		
	public void takeScreenshot(String path){
		try {
			FileUtils.copyFile(((TakesScreenshot)androiddriver).getScreenshotAs(OutputType.FILE), new File(path+"temp.png"));
		}
		 catch (IOException e){ 	
		}		
	}
	
	public void changeDeviceOrentation(ScreenOrientation orentation){
		androiddriver.rotate(orentation);
	}
	
	public void sendAppToBackground(int duration){
		androiddriver.runAppInBackground(Duration.ofSeconds(duration));
	}
	
	public void launchTestApp(){
		androiddriver.launchApp();
	}
	
	
	public void closeTestApp(){
		androiddriver.closeApp();
	}
	
	public void rotate(int x, int y, int z){
		androiddriver.rotate(new DeviceRotation(x, y, z));
	}
	
	public void setToAeroplane(){
		try{
			androiddriver.toggleAirplaneMode();
		}
		catch(java.lang.SecurityException e){
			// not able to set to aeroplane mode
		}
	}
	
	public void toggledata(){
		androiddriver.toggleData();
		androiddriver.toggleLocationServices();
		androiddriver.toggleWifi();

	}
	
	public void swipeDirection() {
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(androiddriver);
		@SuppressWarnings("unused")
		Dimension dimensions = androiddriver.manage().window().getSize();
		action.press(PointOption.point(20,304))
			  .waitAction(new WaitOptions()
			  .withDuration(Duration.ofMillis(1000)))
			  .moveTo(PointOption.point(20, 976))
			  .release()
			  .perform();	
	}
	
	public double BatteryLevel() {
		  AndroidBatteryInfo batteryInfo = androiddriver.getBatteryInfo();
		  return batteryInfo.getLevel()*100;
	}
	
	
	public boolean setTime(int hour, int min) {		
		androiddriver.findElement(By.xpath("//*[@content-desc="+hour+"]")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {		
			e.printStackTrace();
		}
		androiddriver.findElement(By.xpath("//*[@content-desc="+min+"]")).click();

		
		return false;
	}
	
	public void longPress(org.openqa.selenium.By from, int duration) {
		 new AndroidTouchAction(androiddriver)
		 .longPress(longPressOptions()
		 .withElement(element(androiddriver.findElement(from)))
		 .withDuration(ofSeconds(duration)))
		 .release()
		 .perform();
	}
	
	public void slidersleekSetMax() {
		  WebElement slider=androiddriver.findElementByClassName("android.widget.SeekBar");
		  int xAxisStartPoint = slider.getLocation().getX();
		  int xAxisEndPoint = xAxisStartPoint + slider.getSize().getWidth();
		  int yAxis = slider.getLocation().getY();
		  TouchAction act=new TouchAction(androiddriver);
		  //pressed x axis & y axis of seekbar and move seekbar till the end
		  act.press(PointOption.point(xAxisStartPoint,yAxis)).moveTo(PointOption.point(xAxisEndPoint-1,yAxis)).release().perform();
	}
	
	public void slidersleekSetMin() {
		  WebElement slider=androiddriver.findElementByClassName("android.widget.SeekBar");
		  int xAxisStartPoint = slider.getLocation().getX();
		  int xAxisEndPoint = xAxisStartPoint + slider.getSize().getWidth();
		  int yAxis = slider.getLocation().getY();
		  TouchAction act=new TouchAction(androiddriver);
		  //pressed x axis & y axis of seekbar and move seekbar till the end
		  act.press(PointOption.point(xAxisStartPoint,yAxis)).moveTo(PointOption.point(xAxisStartPoint+1,yAxis)).release().perform();
	}
	
	public Boolean isCheckBoxChecked(org.openqa.selenium.By chkbox) {
		return Boolean.parseBoolean(androiddriver.findElement(By.id("io.appium.android.apis:id/hideGoneCB")).getAttribute("checked"));
	}
}	


