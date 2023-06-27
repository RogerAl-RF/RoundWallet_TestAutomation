package helper;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class DriverRunner {

	private static DriverRunner instanceOfDriverRunner = null;
	public static  AppiumDriver<MobileElement> driver;
	//private AndroidDriver driver;
	
//Restrict by using private, so other classes can not create object of driver	
 private DriverRunner()

	{
	DesiredCapabilities capabilities = new DesiredCapabilities();
    //capabilities.setCapability("appium:deviceName","7f8a8176"); //personal device
    //capabilities.setCapability("appium:deviceName","emulator-5554");		//Emulator
    capabilities.setCapability("appium:deviceName","emulator-5556");	
    capabilities.setCapability("appium:platformName","Android");
   // capabilities.setCapability("appium:appPackage","com.l2wallet");
    capabilities.setCapability("appium:appPackage","com.callstack.reactnativepaperexample");
    capabilities.setCapability("appium:noReset","true");
    capabilities.setCapability("appium:appActivity",".MainActivity");
    capabilities.setCapability("appium:automationName","UiAutomator2");
    
    
    try {
    //Initializing driver object
    //driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
    	driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
    //driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 5000);
    System.out.println("Application launched");
    }
    catch (Exception e) {
        System.out.println("URL EXCEPTION WHILE INITIALIZING DRIVER\n" + e);
    }

    //Thread.sleep(8000);

    //implicit max timeout for all elements
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
}
	
 
//Will return the existing driver if driver is already instantiated, if not then it will create a new Driver
public static DriverRunner getInstanceOfDriverRunner()
{
	if(instanceOfDriverRunner==null)
		instanceOfDriverRunner = new DriverRunner();
	
		return instanceOfDriverRunner;
}


//get the driver
//public AppiumDriver<AndroidElement> getDriver()
//{
//return driver;	
//}
public AppiumDriver<MobileElement> getDriver()
{
return driver;	
}

}
