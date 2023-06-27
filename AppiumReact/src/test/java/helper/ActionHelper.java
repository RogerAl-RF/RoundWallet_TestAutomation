package helper;

import helper.AllEnums.*;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class ActionHelper {
	DriverRunner driverRunnerObject = DriverRunner.getInstanceOfDriverRunner();
	AppiumDriver driver = driverRunnerObject.getDriver();

	SoftAssert softassert = new SoftAssert();
	// Clicks a button and throws an exception if it cannot locate with the given
	// locator..
	public void clickButton(LocateBy by, String locator) {

		try {
			switch (by.toString()) {
			case "accessibilityId":
				driver.findElementByAccessibilityId(locator).click();

				break;

			case "xpath":
				driver.findElementByXPath(locator).click();
				break;

			case "id":
				driver.findElementById(locator).click();
				break;

			case "class":
				driver.findElementByClassName(locator).click();
				break;

			default:
				System.out.println("Please pass correct find criteria: " + by);
				return;
			}

			System.out.println("Clicked on -- " + locator);
		}

		catch (Exception e) {
			System.out.println(e);
			System.out.println("Element not found with given locater enum: " + by + " or locator: " + locator
					+ "\nVerify if you are at the correct flow! FAILED");

		}

	}

	// clickButton overload to throw an exception if element is not found! Useful
	// for swiping to find the element if not on screen

	public void clickButton(LocateBy by, String locator, boolean... excThrow) throws Exception {
		try {
			switch (by.toString()) {
			case "accessibilityId":
				driver.findElementByAccessibilityId(locator).click();

				break;

			case "xpath":
				driver.findElementByXPath(locator).click();
				break;

			case "id":
				driver.findElementById(locator).click();
				break;

			case "class":
				driver.findElementByClassName(locator).click();
				break;

			default:
				System.out.println("Please pass correct find criteria: " + by);
				return;
			}

			System.out.println("Clicked on -- " + locator);
		}

		catch (Exception e) {

			if (excThrow[0] == true)
				throw e;

			System.out.println(e);
			System.out.println("Element not found with given locater enum: " + by + " or locator: " + locator
					+ "\nVerify if you are at the correct flow! FAILED");
		}

	}
	//getsText from a given element and returns it as String, 
	//Returns empty String if element is not found 
	public String getElementText(String locator)
	{
		String text="";
		try {
			text = driver.findElementByAccessibilityId(locator).getText();
			System.out.println("Text fetched from element: " + text);
		} 
		catch (NoSuchElementException e) {
			e.printStackTrace();
			System.out.println("Failure, Coldnt find the element with locator: " + locator);
		}
		return text;
	}
	
	//gets text from given element locator and takes partial string to
	//verify if it is contained inside the text that is retrieved.
	
	public boolean getAndVerifyText(String textElementLocator, String shouldContain)
	{
		String elementText=getElementText(textElementLocator);
		if (elementText.isBlank())
		{
			System.out.println("Text fetched is blank, FAILED");
			softassert.fail("String is blank");
			softassert.assertAll();
			return false;
		}
		
		if(elementText.contains(shouldContain))
		{
		System.out.println(elementText+" has text: "+shouldContain+" ,PASSED");
		return true;
		}
		
		else
		{
			System.out.println(elementText+" does not have text: "+shouldContain+" ,FAILED");
			softassert.fail("Element does not contain expected text");
			softassert.assertAll();
			return false;
		}
		
	}
	
	//verifyButton
	public void verifyButtonIsEnabled(String locator)
	{
		try {
			boolean flag;
			flag=driver.findElementByAccessibilityId(locator).isEnabled();
			System.out.println(flag);
			Assert.assertEquals(flag, true,"Button is enabled - "+ locator);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.out.println("Failure, " + locator);
			softassert.fail("Button not found");
		}
		
		softassert.assertAll();
		
	}
//If an element is not visible on screen, the method tries to swipe a given list 4 times to find and click the element

	public void clickAssetBySwiping(String assetLocator, String swipeElementLocator, String swipeDirection) {
		int catchFlag = 0;
		while (true) {
			try {
				clickButton(LocateBy.AID, assetLocator, true);
				System.out.println("Element found and clicked- "+assetLocator+", PASSED");
				break;
			} catch (Exception e) {
				++catchFlag;
				if (catchFlag == 5) {
					System.out.println("Failed to locate- " + assetLocator + " element even after swiping "
							+ swipeDirection + " 4 times!, FAILED");
					System.out.println(e);
					return;
				}

				System.out.println("Swiping " + swipeDirection + " to find the element!, WARNING");
				swipe(LocateBy.AID, swipeElementLocator, swipeDirection);
			}

		}

	}

	// Verifies the given locator title isDisplayed and throws an exception if it is
	// not..
	public void verifyAtFlowByTitle(String locator, String flowName) {
		try {
			if (driver.findElementByAccessibilityId(locator).isDisplayed()) {
				System.out.println("Flow at " + flowName + ", PASSED");
			} else
				System.out.println(locator + " is not displayed, FAILURE");

		}

		catch (Exception e) {
			System.out.println(e);
			System.out.println(locator + " does not exist, \nFlow not on " + flowName + " UI! FAILURE");
		}
	}

	// Sends keys i.e Enters text and throws an exception if it cannot locate
	// textBox with the given locator..
	public void setText(LocateBy by, String locator, String text) {
		try {
			switch (by.toString()) {
			case "accessibilityId":
				driver.findElementByAccessibilityId(locator).sendKeys(text);
				break;

			case "xpath":
				driver.findElementByXPath(locator).sendKeys(text);
				break;

			case "id":
				driver.findElementById(locator).sendKeys(text);
				break;

			case "class":
				driver.findElementByClassName(locator).sendKeys(text);
				break;

			default:
				System.out.println("Please pass correct find criteria: " + by);
				return;
			}
			System.out.println("Sent Keys-- " + text + "\nto-- " + locator);

		}

		catch (Exception e) {
			System.out.println(e);
			System.out.println("Element not found with given locater enum: " + by + " or locator: " + locator
					+ "\nVerify if you are at the correct flow! \n Couldnt set text: " + text + "FAILED");
		}
	}
	

	
	//searches for an input and selects the first card
	public void setSearchAndSelectCards(String inputLocator, String input)
	{
		try {
		setText(LocateBy.AID, inputLocator, input);
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Failure! Couldn't set Text!");
		}
		try {
		clickButton(LocateBy.AID, "Card: Asset 1");
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Could not find card to click after search! FAILED");
		}
	}
	
	//searches for an input and selects the given number of cards
	public void setSearchAndSelectCards(String inputLocator, String input, int n)
	{
		int i=1;
		try {
		setText(LocateBy.AID, inputLocator, input);
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Failure! Couldn't set Text!");
		}
		try {
		for(i=1;i<=n;i++)
		clickButton(LocateBy.AID, "Card: Asset "+i,true);
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Could not find card - "+i+" to click after search! FAILED");
		}
	}
	
	
	public int getTotalNumberOfElementsInList(structureType st, ElementType et, String containsContentDescOfXpath)
	{
		int numberOfElementsInList = 0;
		try {
//		numberOfElementsInList = Integer.valueOf(driver
//				.findElementByXPath("//android.widget." + orientation.toString() + "[contains(@content-desc,'"
//						+ containsContentDescOfXpath + "')]")
//				.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());
			
			numberOfElementsInList = Integer.valueOf(driver
			.findElementByXPath("//android."+ st.toString() + et.toString() + "[contains(@content-desc,'"
					+ containsContentDescOfXpath + "')]")
			.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());
			
		System.out.println("\n"+numberOfElementsInList);
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Couldnt find the list with given Structure/Element type, contentDesc: "+st+"\n"+et+"\n"+containsContentDescOfXpath);
		}
		return numberOfElementsInList;
	}


	//Navigate to Tab
	public void navigateToTab(Tab tabName)
	{
		try {
			clickButton(LocateBy.AID,"Tab: "+tabName,true);
		}
		catch(Exception e)
		{
			System.out.println("Failure! Could not click on Tab: "+tabName);
			System.out.print(e);
			
		}
	}
	
	//Click by bound location
	public PointOption clickByCoordinates(String bounds)
	{
		System.out.println(bounds);
		String bound[] = bounds.replaceAll("[^0-9]", " ").replaceAll("  ", " ").trim().split(" ");
		int[] xy = new int[bound.length];

		for (int i = 0; i < bound.length; i++) {
			xy[i] = Integer.valueOf(bound[i]);
			System.out.println(xy[i]);
		}

		int x = ((xy[2] - xy[0]) / 2) + xy[0];
		int y = ((xy[3] - xy[1]) / 2) + xy[1];
		
		return new PointOption().withCoordinates(x,y);
	}
	
	public void clickByPointOption(PointOption p)
	{
		TouchAction action = new TouchAction(driver);
		action.press(p).release().perform();
		System.out.println("Pressed Element with location");
	}
	
	// Presses and holds an element for 2 seconds..
	public void longPress(String locator) {
		MobileElement pressElement = (MobileElement) driver.findElementByAccessibilityId(locator);
		LongPressOptions lpOptions = new LongPressOptions();
		lpOptions.withDuration(Duration.ofSeconds(2)).withElement(ElementOption.element(pressElement));

		TouchAction action = new TouchAction(driver);
		action.longPress(lpOptions).release().perform();
		System.out.println("Long Pressed Element");
	}

	// Swipes any element in given direction - left,right,up,down
	public void swipe(LocateBy by, String locator, String swipeDirection) {
		String bounds = "";

		try {
			switch (by.toString()) {
			case "accessibilityId":
				bounds = driver.findElementByAccessibilityId(locator).getAttribute("bounds");
				break;

			case "xpath":
				bounds = driver.findElementByXPath(locator).getAttribute("bounds");
				break;

			case "id":
				bounds = driver.findElementById(locator).getAttribute("bounds");
				break;

			case "class":
				bounds = driver.findElementByClassName(locator).getAttribute("bounds");
				break;

			default:
				System.out.println("Please pass correct find criteria: " + by);
				return;
			}
			System.out.println("Stored bounds of: " + locator);
		}

		catch (Exception e) {
			System.out.println(e);
			System.out.println(
					" Couldnt swipe " + swipeDirection + ". FAILED\nElement not found with given locater enum: " + by
							+ " or locator: " + locator + "\nVerify if you are at the correct flow!");
			return;
		}

		System.out.println(bounds);
		String bound[] = bounds.replaceAll("[^0-9]", " ").replaceAll("  ", " ").trim().split(" ");
		int[] xy = new int[bound.length];

		for (int i = 0; i < bound.length; i++) {
			xy[i] = Integer.valueOf(bound[i]);
			System.out.println(xy[i]);
		}

		int x = ((xy[2] - xy[0]) / 2) + xy[0];
		int y = ((xy[3] - xy[1]) / 2) + xy[1];
		int startX, startY, endX, endY;

		System.out.println("X:" + x + " Y: " + y);

		// handles any case if its one of 4 directions
		if (swipeDirection.equalsIgnoreCase("Left")) {
			startX = x + 300;
			startY = y;
			endX = x - 500;
			endY = y;
		}

		else if (swipeDirection.equalsIgnoreCase("Right")) {
			startX = x - 500;
			startY = y;
			endX = x + 300;
			endY = y;
		}
	//clicks top first then swipes down
		else if (swipeDirection.equalsIgnoreCase("Down")) {
			startX = x;
			startY = y - 300;
			endX = x;
			endY = y + 500;
		}
	//clicks down first then swipes up
		else if (swipeDirection.equalsIgnoreCase("Up")) {
			startX = x;
			startY = y + 100;
			endX = x;
			endY = y - 200;
		}

		else {
			System.out.println("Warning! Correct direction not entered. Swiping Left!");
			startX = x + 300;
			startY = y;
			endX = x - 500;
			endY = y;
		}

		System.out.println("Direction is " + swipeDirection);

		TouchAction action = new TouchAction(driver);
		action.press(new PointOption().withCoordinates(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
				.moveTo(new PointOption().withCoordinates(endX, endY)).release().perform();
		System.out.println("Swiped list Element " + swipeDirection);

	}
}
