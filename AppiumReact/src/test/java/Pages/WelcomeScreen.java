package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import junit.framework.Assert;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import helper.ActionHelper;
import helper.AllEnums.LocateBy;
import helper.DriverRunner;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WelcomeScreen {
	
	public static ExtentSparkReporter spark;
	public static ExtentReports extent;
	public static ExtentTest testCase;
	static ExtentTest node;
	
	ActionHelper helper = new ActionHelper();
	 
	DriverRunner driverRunnerObject=DriverRunner.getInstanceOfDriverRunner();
	AppiumDriver<MobileElement> driver = driverRunnerObject.getDriver();
	 
     final int[] wrongPin = {2,2,4,1,2,1};//incorrect pin
     final int[] correctPin = {2,2,4,1,2,2};//Correct pin
     
     public WelcomeScreen()
     {
 		spark = new ExtentSparkReporter("C:\\Users\\Roger\\eclipse-workspace\\AppiumReact\\target\\extentReports");
 		spark.config().setDocumentTitle("RoundFinance");
 		extent=new ExtentReports();
 		extent.attachReporter(spark);
 		
 		testCase=extent.createTest(this.getClass().getName());
     }
	
 public void launchRoundApp()
	{
		node=testCase.createNode("Launch Method");
		node.log(Status.PASS,"Successfull Launched");
	 //driver = driverRunnerObject.getDriver();
	 System.out.println("Driver is Created! Application is launched!");
	}
	

public void verifyAtWelcome()
{
    //Verifying flow is at Welcome Screen
     MobileElement welElement = (MobileElement) driver.findElementByAccessibilityId("Description");
     if (welElement.getText().equalsIgnoreCase("Enter your 6 digit MPIN"))
        System.out.println("Test at Welcome Screen");
//    else
//        System.out.println("NOT AT WELCOME SCREEN");
	
    Assert.assertEquals("Not At Welcome Screen", welElement.getText(), "Enter your 6 digit MPIN");
	
}


public void enterPin(int Pin[], String pin) {
	node=testCase.createNode("Launch Method");
	node.log(Status.PASS,"Entering Correct MPIN");
//for(char c:pin.toCharArray())
//{
//	driver.findElementByAccessibilityId("Keyboard key: "+c).click();
//	System.out.println("Tap "+ c);
//}
// Entering Pin
	
MobileElement el;
MobileElement parent = (MobileElement) driver.findElementByXPath("//android.view.ViewGroup[@content-desc='Keyboard key: 1']/..");
System.out.println("Got Parent");
List<MobileElement> li = driver.findElementsByXPath("//*[starts-with(@content-desc, 'Keyboard key')]");

for(int i=0;i<6;i++) {
    			//driver.findElementByXPath("//android.widget.TextView[contains(@text, '"+wrongPin[i]+"')]").click();
//    el=(MobileElement)driver.findElementByAccessibilityId("Keyboard key: "+Pin[i]);
	//el=(MobileElement)parent.findElementByAccessibilityId("Keyboard key: "+Pin[i]);
   // el.click();
	li.get(Pin[i]-1).click();
    System.out.println("Tap "+ Pin[i]);
}
//	driver.executeDriverScript(
////			"await driver.setImplicitTimeout(6000);\n"
//            "await (await driver.$('~Keyboard key: 2')).click();\n"
//			+ "await (await driver.$('~Keyboard key: 2')).click();\n"
//			+ "await (await driver.$('~Keyboard key: 4')).click();\n"
//			+ "await (await driver.$('~Keyboard key: 1')).click();\n"
//			+ "await (await driver.$('~Keyboard key: 2')).click();\n"
//			+ "await (await driver.$('~Keyboard key: 2')).click();\n"
//			);
//    
//    
	
	//uiautomator
//	
//	WebElement element = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"Keyboard key: 2\")");
//	element.click();
//	element.click();
//}
}

public void verifyIncorrectPinError()
{
    //Verifying Incorrect Pin Error exists
	System.out.println("Inside Verify Incorrect");
    MobileElement errElement=(MobileElement) driver.findElementByAccessibilityId("Error");
    MobileElement errDescElement=(MobileElement) driver.findElementByAccessibilityId("Wrong MPIN");

    if(errElement.getText().equalsIgnoreCase("Error") && errDescElement.getText().equalsIgnoreCase("Wrong MPIN")) {
        System.out.println("Wrong MPIN error exists");
        driver.findElementByAccessibilityId("Button: Ok").click();
        System.out.println("Error Button Ok Clicked");
    }
    else
        System.out.println("Failure : Error not shown on wrong MPIN");


	}

public void testApp()
{
	driver.findElementByXPath("//android.widget.Button[contains(@resource-id, 'icon-button')]").click();
	driver.navigate().back();
	helper.swipe(LocateBy.ID, "android:id/content", "up");
	driver.findElementByXPath("//android.widget.Button[contains(text(), 'TextInput')]").click();
}

public void forgotPasscodePopupOk()
{
	//Checking Forgot Password appears and has two buttons -- Verify Clicking Ok -- Verify Clicking Recover
    driver.findElementByAccessibilityId("Button: Forgot your passcode?").click();
    MobileElement errorForget=(MobileElement) driver.findElementByAccessibilityId("Forget password");
    if (errorForget.getText().equalsIgnoreCase("Forget password"))
        System.out.println("Forget Password popup shown");
    else
        System.out.println("Forget Password popup not shown");	
    
    //Click Ok
    driver.findElementByAccessibilityId("Button: Ok").click();
} 
    //--break method here into two - Ok and Recover
    
    //Checking if Clicking Ok button takes us back to the Welcome screen
    //verifyAtWelcome(driver);//Verifying flow is at Welcome Screen
public void forgotPasscodePopupRecover()
{
    driver.findElementByAccessibilityId("Button: Forgot your passcode?").click();
    //Click Recover now
    driver.findElementByAccessibilityId("Button: Recover").click();
}

public void verifyForgotPassRecover()
{
    //Checking if Recover button takes us to enter an email page 
	//-- Support Displayed -- Do this later 
	//-- Verify back at entering email -- Navigate back to Welcome Screen
    if (driver.findElementByAccessibilityId("Input: Enter your email address").isDisplayed());
    System.out.println("Enter Email is displayed");	
    
}
public void verifyForgotPassRecoverSupport()
{
    driver.findElementByAccessibilityId("Button: Forgotten your email?").click();
    //verify support exists and click do this later0

     if(driver.findElementByAccessibilityId("Support").isDisplayed())
         System.out.println("Support is displayed");
     else
         System.out.println("Support screen not displayed");

     //click do this later
     driver.findElementByAccessibilityId("Button: Do this later").click();
     //verify back at Enter Email
     if (driver.findElementByAccessibilityId("Input: Enter your email address").isDisplayed());
     System.out.println("Enter Email is displayed");	
 
     //Click Back
     driver.findElementByAccessibilityId("Button: Back").click();;
     //Navigate back to welcome screen
}


public void removePin()
{
    //Removing Incorrect Pin
    //MobileElement backspace=(MobileElement) driver.findElementByXPath("//android.widget.TextView[contains(@resource-id, 'iconIcon')]");

    int n=0;
    while(n<6) {
    	driver.findElementByAccessibilityId("Keyboard key: delete").click();
        n++;
    }	
}

public void verifyAtHome()
{
    //Verifying Navigated to Home Page after Successfull login!

	try {
		
		driver.findElementByAccessibilityId("Tab: Home").click();
		System.out.println("Home Tab Exists, PASSED");
	}
	
	
	catch(Exception NoSuchElement)
	{
		System.out.println(NoSuchElement);
		System.out.println("NOT AT HOME SCREEN, Test FAILED");
	}

}

public void quitApplication()
{
	node=testCase.createNode("Quit Method");
	node.log(Status.PASS,"Quitting!");
	System.out.println("Exiting the App!");
	driver.quit();	
	extent.flush();
	  try
	  {
		  String url = "C:\\Users\\Roger\\eclipse-workspace\\AppiumReact\\target\\extentReports";
		  File file = new File(url);
	  
	  Desktop.getDesktop().browse(file.toURI());
	  }
	  
	  catch (IOException e)
	  {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	  }
}

}