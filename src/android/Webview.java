package android;

import java.util.Set;

import io.appium.java_client.android.AndroidDriver;

public class Webview {
	
private AndroidDriver androiddriver;
	
		Webview(@SuppressWarnings("rawtypes") AndroidDriver androiddriver){		
		this.androiddriver = androiddriver;
	}
		
	public boolean isActivityContainsWebview() {
		
		 Set<String> contextNames = androiddriver.getContextHandles();
		 for (String contextName : contextNames) {
			 if(contextName.contains("WEBVIEW_chrome")) return true;
		 }
		 return false;
	}
	
	public void switchToWebview() {
		
		if(isActivityContainsWebview()) {
			androiddriver.context((String) androiddriver.getContextHandles().toArray()[1]);
			System.out.println("webview"+androiddriver.getPageSource());
		}
	}
	
	
	
}
