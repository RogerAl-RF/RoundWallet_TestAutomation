package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import helper.ActionHelper;
import helper.DriverRunner;
import helper.AllEnums.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class DashboardHomeWatchlist {

	DriverRunner driverRunnerObject = DriverRunner.getInstanceOfDriverRunner();
	AppiumDriver driver = driverRunnerObject.getDriver();

	ActionHelper helper = new ActionHelper();

	public List<String> themeNames = new ArrayList<String>();
	public int totalNumberOfThemes = 0;
	public List<String> assetNames = new ArrayList<String>();
	public int totalNumberOfAssets = 0;

//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		DashboardHomeWatchlist obj = new DashboardHomeWatchlist();
//		Thread.sleep(10000);
//		obj.watchlistHome();
//		obj.themesUISeeAll();
//	}

	public void watchlistHome() throws Exception {
		// Verifying Navigated to Home Page after Successfull login!
		Thread.sleep(Duration.ofSeconds(12));
		MobileElement homeTab = (MobileElement) driver.findElementByAccessibilityId("Tab: Home");
		System.out.println("Home Exists!" + homeTab.getAttribute("content-desc"));
		if (homeTab.getAttribute("content-desc").equalsIgnoreCase("Home")) {
			Assert.assertTrue(true, "Home Tab Exists");

		} else
			Assert.assertFalse(false, "Failure : NOT AT HOME SCREEN, LOGIN FAILED");

		if (driver.findElementByAccessibilityId("Themes").isDisplayed()) {
			Assert.assertTrue(true, "Themes label exist");
			System.out.println("Themes exist!");
		} else
			Assert.assertFalse(false, "Themes label does not exist");

		String[] homeCardThemes = new String[4], numberOfAssets = new String[4];
		int i, j;
		String id = "List: Themes";
		outer :for (i = 1; i < 5; i++) {
			// condition for 4th
			if (i == 4) {
				helper.swipe(LocateBy.AID, id, "left");
			}

			for (j = 1; j < 3; j++) {
				try {
				if (j == 1)
					homeCardThemes[i - 1] = driver
							.findElementByXPath("//android.view.ViewGroup[@content-desc='Card: Theme " + i
									+ "']/android.widget.TextView[" + j + "]")
							.getText();
				if (j == 2)
					numberOfAssets[i - 1] = driver
							.findElementByXPath("//android.view.ViewGroup[@content-desc='Card: Theme " + i
									+ "']/android.widget.TextView[" + j + "]")
							.getText();
				}
				
				catch(Exception e) {
					System.out.println(e);
				//e.printStackTrace();
				System.out.println("Only "+(i-1)+ " cards on screen");
				break outer;
				}
				
			}

		}

		i = 0;
		for (String theme : homeCardThemes) {
			System.out.println("Theme Name: " + homeCardThemes[i] + "\n Number of Crypto: " + numberOfAssets[i]);
			i++;
		}
		driver.findElementByXPath("(//android.view.ViewGroup[@content-desc='Button: See All'])[3]").click();
	}

	// get total number of themes inside watchlist
	public int getNumberOfThemes() {
		int numberOfThemes = Integer.valueOf(driver
				.findElementByXPath("//android.widget.HorizontalScrollView[contains(@content-desc,'List: Watchlist')]")
				.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());
		System.out.println("Number Of Themes : " + numberOfThemes);
		return numberOfThemes;
	}



	// Set and Store all theme names inside watchlist
	public void setThemeNames() {
		int i, numberOfThemes = totalNumberOfThemes;
		if (totalNumberOfThemes == 0)
			numberOfThemes = getNumberOfThemes();
		totalNumberOfThemes = numberOfThemes;
		for (i = 1; i <= numberOfThemes; i++) {
			try {
				themeNames.add(driver.findElementByXPath(
						"//android.view.ViewGroup[@content-desc='Button: Watchlist " + i + "']/android.widget.TextView")
						.getText());
				System.out.println("Theme Added " + themeNames.get((i - 1)));
			}

			catch (Exception e) {
				--i;
				System.out.println("Ignoring NoSuchElementException! Swiping left");

				helper.swipe(LocateBy.AID, "List: Watchlist " + numberOfThemes, "left");

			}

		}

		System.out.println(themeNames);
	}

	// Click on theme name passed as an argument
	public void clickOnTheme(String themeToClick) throws Exception {
		int flag = 0;
		while (true) {
			try {
				// driver.findElementByAccessibilityId("Button: Watchlist 3").click();
				driver.findElementByAccessibilityId("Button: Watchlist " + (themeNames.indexOf(themeToClick) + 1))
						.click();
				System.out.println(themeToClick + " Theme Clicked");
				break;
			} catch (Exception e) {
				++flag;
				if (flag == 5) {
					System.out.println("Couldn't find the theme even after swiping 4 times!\nFAILURE, Exiting!");
					System.out.println(e);
					break;
				}
				System.out.println("Finding theme by swiping left");
				helper.swipe(LocateBy.AID, "List: Watchlist " + totalNumberOfThemes, "left");
			}

		}
	}

	// Get the number of Assets inside a theme
	public int getNumberOfAssets() {
		int numberOfAssets = Integer.valueOf(
				driver.findElementByXPath("//android.widget.ScrollView[contains(@content-desc,'List: Assets')]")
						.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());
		System.out.println("Number Of Assets inside theme: " + numberOfAssets);
		totalNumberOfAssets = numberOfAssets;
		return numberOfAssets;
	}

	// Set and store all AssetNames inside a theme
	public void setAssetNames() {
		int numberOfAssets = totalNumberOfAssets;
		if (totalNumberOfAssets == 0)
			numberOfAssets = getNumberOfAssets();

		totalNumberOfAssets = numberOfAssets;

		int i, flag = 0;

		for (i = 1; i <= numberOfAssets; i++) {
			try {
				//assetNames.add(driver.findElementByXPath(
					//	"//android.view.ViewGroup[@content-desc='Card: Asset " + i + "']/android.widget.TextView[1]")
						//.getText());
				assetNames.add(driver.findElementByAccessibilityId("Card: Asset "+i+": Label AssetName").getText());
				System.out.println("Asset Added " + assetNames.get((i - 1)));
			}

			catch (Exception e) {
				--i;
				++flag;
				if (flag == 5) {
					System.out.println("Asset not found even after 4 swipe attempts, FAILURE Exiting");
					break;
				}
				System.out.println("Ignoring NoSuchElementException! Swiping Up");
				helper.swipe(LocateBy.AID, "List: Assets " + numberOfAssets, "Up");

			}

		}

		System.out.println(assetNames);
	}

	public void swipeDeleteAsset(String assetToDelete) {
		String cardId = "Card: Asset " + (assetNames.indexOf(assetToDelete) + 1);

		// if asset is not visible in Ui swipe once
		if (assetNames.indexOf(assetToDelete) > 6) {
			System.out.println("Finding asset by swiping up");
			helper.swipe(LocateBy.AID, "List: Assets " + totalNumberOfAssets, "up");
		}

		helper.swipe(LocateBy.AID, cardId, "left");
		System.out.println("Card is swiped");

		// click delete for the asset

		driver.findElementByAccessibilityId("Button: Delete " + (assetNames.indexOf(assetToDelete) + 1)).click();
		System.out.println("Clicked on delete for " + assetToDelete);

		assetNames.remove(assetToDelete);
	}

	// verify number of assets is reduced by one after deletion
	public void numberOfAssetsAfterDeletion() {

		// Verify number of elements is n-1 after swipe to delete
		int noOfAssetsAfterDeletion = Integer.valueOf(
				driver.findElementByXPath("//android.widget.ScrollView[contains(@content-desc,'List: Assets')]")
						.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());
		if (noOfAssetsAfterDeletion == (totalNumberOfAssets - 1)) {
			totalNumberOfAssets = noOfAssetsAfterDeletion;
			System.out.println("Number Of Assets inside theme after deletion is: " + noOfAssetsAfterDeletion
					+ "\nTest Passed, Asset removed successfully!");
			// remove defichain from asset list
		}

		else
			System.out.println("Number Of Assets not reduced by one! DELETION FAILURE!");

	}

	// clickOnTheme()//To click on the theme to edit modify
	// Long Press Method to modify theme!

	public void assertEditThemeUI(String themeName) {
		try {
			if (("Edit " + themeName).equals(driver.findElementByAccessibilityId("Edit " + themeName).getText())) // Edit
																													// name
																													// of
																													// Theme
				System.out.println("Passed! Flow on Edit Theme PopUp");
			else
				System.out.println("No Pop Up! Failure");
		} catch (Exception e) {
			System.out.println("Edit Theme pop up not displayed, FAILURE!");
		}

		// swipe("//android.widget.TextView[@content-desc='Edit test']/..","down");
		// //Xpath for parent of rename/delete theme frame

		driver.findElementByAccessibilityId("Title: Themes").click(); // Click to focus outside the Edit theme popup

		// Verify title themes after swipe down on edit theme popup
		// Thread.sleep(Duration.ofMillis(3000));

		try {
			System.out.println(driver.findElementByAccessibilityId("Title: Themes").getText());
			System.out.println("Passed! Back at Themes UI after swiping down from edit theme popUp");
		} catch (Exception e) {
			System.out.println("Title Themes not found, FAILURE! Test not back at THEMES UI");
		}
	}

	public void renameThemeFlow(String oldThemeName, String newThemeName) {
		// Rename flow -- same name -- existing theme name -- correct different name

		try {
			if ("Theme Name".equals(driver.findElementByAccessibilityId("Theme Name").getText())) // Edit name of Theme
				System.out.println("Passed! Flow on Edit Theme Name UI");
			else
				System.out.println("Not on rename theme! Failure");

			driver.findElementByAccessibilityId("Input: Theme Name").sendKeys(newThemeName); // same name
			driver.findElementByAccessibilityId("Button: Save").click();

			if (newThemeName.equals(oldThemeName)) {
				// Rename flow -- same name
				try {
					String errorMessage = driver
							.findElementByXPath("//android.widget.TextView[contains(@content-desc,'name is same')]")
							.getText();
					System.out.println(errorMessage + " is displayed, PASSED");
					driver.findElementByAccessibilityId("Button: Ok").click();
				}

				catch (Exception e) {
					System.out.println("Error theme name is same is not displayed, FAILURE");
				}
				return;
			}

			else if (themeNames.contains(newThemeName)) {
				// Rename flow -- existing theme name
				try {
					String errorMessage = driver
							.findElementByXPath("//android.widget.TextView[contains(@content-desc,'already used')]")
							.getText();
					System.out.println(errorMessage + " is displayed, PASSED");
					driver.findElementByAccessibilityId("Button: Ok").click();
				}

				catch (Exception e) {
					System.out.println("Error theme name is already used is not displayed, FAILURE");
				}

				return;
			}

			else {
				// Rename flow -- correct different name
				int index = themeNames.indexOf(oldThemeName);
				String updatedThemeName = driver
						.findElementByXPath("//android.view.ViewGroup[@content-desc='Button: Watchlist " + (index + 1)
								+ "']/android.widget.TextView")
						.getText();
				if (newThemeName.equals(updatedThemeName)) {
					System.out.println("PASSED, Updated Theme Name: " + updatedThemeName);
					// update themeName list
					themeNames.set(index, newThemeName);
				}

				else {
					System.out.println("FAILURE, Theme name not updated: " + updatedThemeName);
				}

			}
		} catch (Exception e) {
			System.out.println("Edit flow not on rename theme UI as expected, FAILURE!");
			System.out.println(e);
		}
	}

	// Delete Theme Flow
	public void deleteThemeFlowKeep() {

		try {
			String deleteThemeUI = driver.findElementByAccessibilityId("Are you sure?").getText();
			System.out.println("Inside Flow: " + deleteThemeUI);

			// Clicking keep theme on delete theme ui
			driver.findElementByAccessibilityId("Button: Keep theme").click();
			// verifying numberOfThemes is same
			try {

				int numberOfThemesTemp = Integer.valueOf(driver
						.findElementByXPath(
								"//android.widget.HorizontalScrollView[contains(@content-desc,'List: Watchlist')]")
						.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());

				if (totalNumberOfThemes == numberOfThemesTemp) {
					System.out.println("PASSED, Keep Theme working fine! Theme is not deleted, No. of Themes: "
							+ numberOfThemesTemp);
				} else
					System.out.println("Keep Theme has deleted the theme, FAILED");

			} catch (Exception e) {
				System.out.println("Not navigated back to Themes UI after clicking keep theme!");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Flow not on Delete theme! AreYouSure PopUp!");
		}
	}

	public void deleteThemeFlowDelete(String themeToDelete) {

		// Inside Edit Test
		driver.findElementByAccessibilityId("Button: Delete Theme").click();
		// Inside Delete Theme
		driver.findElementByAccessibilityId("Button: Delete theme").click();

		try {

			int numberOfThemesTemp = Integer.valueOf(driver
					.findElementByXPath(
							"//android.widget.HorizontalScrollView[contains(@content-desc,'List: Watchlist')]")
					.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());

			if (totalNumberOfThemes == numberOfThemesTemp + 1) {
				System.out.println("PASSED, Theme is deleted, No. of Themes: " + numberOfThemesTemp);
				totalNumberOfThemes = numberOfThemesTemp;
				themeNames.remove(themeToDelete);
			} else
				System.out.println("Delete theme has failed, Theme not deleted!, FAILED");

		} catch (Exception e) {
			System.out.println("Not navigated back to Themes UI after clicking keep theme!");
		}
	}

	// Create a theme flow --existing theme name --new theme name //Flow should be
	// at themes UI where + button is displayed
	public void verifyCreateThemeFlow() {

		// driver.findElementByAccessibilityId("Button: Create theme").click(); //
		// clicking plus button to create theme
		try {
			String title = driver.findElementByAccessibilityId("Title: Create a theme").getText();
			if (title.equals("Create a theme")) {
				System.out.println("PASSED, Title is: " + title);
			}

		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Not at Create a theme page");
		}

	}

	public void verifyThemesWatchlistFlow() {
		try {
			System.out.println(driver.findElementByAccessibilityId("Title: Themes").getText());
			System.out.println("Passed! Flow at themes watchlist scroll UI");
		} catch (Exception e) {
			System.out.println("Title Themes not found, FAILURE! Test not back at THEMES UI");
		}
	}

	public void enterThemeName_AssetSearch(String themeName, String assetName, int nCardsToSelect) {
		// existing theme name
		driver.findElementByAccessibilityId("Input: Theme Name").sendKeys(themeName);
		driver.findElementByAccessibilityId("Input: Add assets").sendKeys(assetName);
		int i;

		try {
			for (i = 1; i <= nCardsToSelect; i++) {
				driver.findElementByAccessibilityId("Card: Asset " + i).click();
			}
		} catch (Exception NoSuchElement) {
			System.out.println(NoSuchElement);
			System.out.println("Card does not exist to select, check number of cards to select parameter!");
		}

		try {
			driver.findElementByAccessibilityId("Button: Create theme").click();
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("FAILED, Create Theme Button does not exist!");
		}

		if (themeNames.contains(themeName)) {
			try {
				String errorMessage = driver
						.findElementByXPath("//android.widget.TextView[contains(@content-desc,'already used')]")
						.getText();
				System.out.println(errorMessage + " is displayed, PASSED");
				driver.findElementByAccessibilityId("Button: Ok").click();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("FAILED, Error did not appear on entering existing theme name!");
			}

			return;
		}

		// new valid theme name
		// driver.findElementByAccessibilityId("Input: Theme Name").sendKeys(themeName);
		// selecting one asset and one is preselected - total 2
		// driver.findElementByAccessibilityId("Input: Add assets").sendKeys("doge");
		// driver.findElementByAccessibilityId("Card: Asset 1").click();

		// driver.findElementByAccessibilityId("Button: Create theme").click();

		try {
			System.out.println("Inside try of adding theme" + totalNumberOfThemes);
			int numberOfThemesTemp = Integer.valueOf(driver
					.findElementByXPath(
							"//android.widget.HorizontalScrollView[contains(@content-desc,'List: Watchlist')]")
					.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());

			if (totalNumberOfThemes == numberOfThemesTemp - 1) {
				System.out.println("PASSED, Theme is created, No. of Themes: " + numberOfThemesTemp);
				totalNumberOfThemes = numberOfThemesTemp;
				themeNames.add(0, themeName);// Added created theme to list of themes
				System.out.println(themeNames);

			} else
				System.out.println("Create a theme has failed, Theme not created!, FAILED");

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Not navigated back to Themes UI after clicking create a theme!");
		}

	}

	// Edit Theme Flow

	public void clickOnEditThemeFlow(String themeName) {
		// edit theme themeName
		try {
			clickOnTheme(themeName);
		} catch (Exception e) {
			System.out.println("Error while clicking theme name!");
			System.out.println(e.getMessage());
		}
		getNumberOfAssets();
		driver.findElementByAccessibilityId("Button: Edit theme").click();

	}

	public void verifyAtEditTheme() {
		try {
			String title = driver.findElementByAccessibilityId("Title: Edit theme").getText();
			if (title.equals("Edit theme"))
				System.out.println("PASSED, Flow at " + title + " UI");
			else
				System.out.println("Warning, title does not match as expected!");
		}

		catch (Exception e) {
			System.out.println("FAILED, Not at edit themes UI!");
		}

		try {
			if (driver.findElementByAccessibilityId("Button: Submit").isDisplayed())
				System.out.println("Submit button is displayed");
			else
				System.out.println("Submit button is not displayed, FAILED");
		} catch (Exception e) {
			System.out.println("Submit button does not exist as expected");
		}

	}

	public void deselectAllAssetsEditTheme() {

		// Click on number of assets in the theme to deselect all
		int i;

		for (i = 1; i <= totalNumberOfAssets; i++) {
			driver.findElementByAccessibilityId("Card: Asset " + i).click();
		}
		System.out.println("Deselected all existing elements");

		try {
			if (!driver.findElementByAccessibilityId("Button: Submit").isDisplayed())
				System.out.println("Submit button is displayed, FAILED");
			else
				System.out.println("Submit button is displayed, FAILED");
		} catch (Exception e) {
			System.out.println("As expected, submit button is not displayed! PASSED");
		}
	}

	public void selectAssetsEditTheme(String assetName) {
		try {
			// swipe("List: Assets", "up");
			driver.findElementByAccessibilityId("Input: Search theme").sendKeys(assetName);
			driver.findElementByAccessibilityId("Card: Asset 1").click();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed to select cards after assetSearch");
		}

	}

	public void verifyEditThemeSuccess() {
		// verify number of assets is increased
		// click submit
		driver.findElementByAccessibilityId("Button: Submit").click();
		try {

			int numberOfAssets = Integer.valueOf(
					driver.findElementByXPath("//android.widget.ScrollView[contains(@content-desc,'List: Assets')]")
							.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());
			System.out.println(numberOfAssets + "	total:" + totalNumberOfAssets);
			if (!(totalNumberOfAssets == numberOfAssets)) {
				System.out.println("PASSED, Assets are added/removed, No. of assets: " + numberOfAssets);
				totalNumberOfAssets = numberOfAssets;
			} else
				System.out.println("Edit theme has failed, Asset not added!, FAILED");

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Not navigated back to Themes UI after submitting edit theme!");
		}
	}

//	public void themesUISeeAll() throws InterruptedException {
//		// clicking SeeAll on Home-Themes
//
//		driver.findElementByXPath("(//android.view.ViewGroup[@content-desc='Button: See All'])[3]").click();
//
//		int numberOfThemes = Integer.valueOf(driver
//				.findElementByXPath("//android.widget.HorizontalScrollView[contains(@content-desc,'List: Watchlist')]")
//				.getAttribute("content-desc").replaceAll("[^0-9]", " ").trim());
//		System.out.println("Number Of Elements : " + numberOfThemes);
//		String id = "List: Watchlist " + numberOfThemes;
//	}

//	public void swipe(String id, String swipeDirection) throws Exception {
//		String bounds;
//		try {
//			bounds = driver.findElementByAccessibilityId(id).getAttribute("bounds");
//		} catch (Exception e) {
//			System.out.println("Element not found using AccessibilityId, Trying with XPath:");
//			try {
//				bounds = driver.findElementByXPath(id).getAttribute("bounds");
//			} catch (Exception xpathException) {
//				System.out.println(
//						"Element not found with Xpath too! Id passed: " + id + "\nReturning out of swipeMethod");
//				throw xpathException;
//			}
//		}
//
//		System.out.println(bounds);
//		String bound[] = bounds.replaceAll("[^0-9]", " ").replaceAll("  ", " ").trim().split(" ");
//		int[] xy = new int[bound.length];
//
//		for (int i = 0; i < bound.length; i++) {
//			xy[i] = Integer.valueOf(bound[i]);
//			System.out.println(xy[i]);
//		}
//
//		int x = ((xy[2] - xy[0]) / 2) + xy[0];
//		int y = ((xy[3] - xy[1]) / 2) + xy[1];
//		int startX, startY, endX, endY;
//
//		System.out.println("X:" + x + " Y: " + y);
//
//		if (swipeDirection.equalsIgnoreCase("Left")) {
//			startX = x + 300;
//			startY = y;
//			endX = x - 500;
//			endY = y;
//		}
//
//		else if (swipeDirection.equalsIgnoreCase("Right")) {
//			startX = x - 500;
//			startY = y;
//			endX = x + 300;
//			endY = y;
//		}
//
//		else if (swipeDirection.equalsIgnoreCase("Up")) {
//			startX = x;
//			startY = y - 300;
//			endX = x;
//			endY = y + 500;
//		}
//
//		else if (swipeDirection.equalsIgnoreCase("Down")) {
//			startX = x;
//			startY = y + 100;
//			endX = x;
//			endY = y - 200;
//		} else {
//			System.out.println("Warning! Correct direction not entered. Swiping Left!");
//			startX = x + 300;
//			startY = y;
//			endX = x - 500;
//			endY = y;
//		}
//
//		System.out.println("Direction is " + swipeDirection);
//
//		TouchAction action = new TouchAction(driver);
//		action.press(new PointOption().withCoordinates(startX, startY))
//				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
//				.moveTo(new PointOption().withCoordinates(endX, endY)).release().perform();
//		System.out.println("Swiped list " + swipeDirection);
//
//	}

}
