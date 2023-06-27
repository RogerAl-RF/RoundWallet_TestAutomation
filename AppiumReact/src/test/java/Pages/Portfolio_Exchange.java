package Pages;

import static org.testng.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;

import helper.ActionHelper;
import helper.DriverRunner;
import helper.AllEnums.*;
import io.appium.java_client.AppiumDriver;

public class Portfolio_Exchange {

	DriverRunner driverRunnerObject = DriverRunner.getInstanceOfDriverRunner();
	AppiumDriver driver = driverRunnerObject.getDriver();

	ActionHelper helper = new ActionHelper();
	double cashBalance = 0.0;
	HashMap<String, Double> ownedAssetsAndValue = new HashMap<String, Double>();

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(Duration.ofSeconds(100));
		Portfolio_Exchange pe = new Portfolio_Exchange();
		pe.verifyAtPortfolio();
		pe.getOwnedAssetValue();
		pe.clickAndVerifyExchange();
		pe.selectAnAsset("Tether", "Sushi");
		// pe.exchangeVerifications("Tether","999");
		pe.verifyPercentButtonExchange(10);
		System.out.println("Test End");
	}
	
	public void verifyExchangeCompletion()
	{
		helper.getAndVerifyText("Title: ExchangeCompletionTitle", "Completing");
		helper.getAndVerifyText("Label: ExchangeCompletionMessage", "take a few seconds");
		
		helper.getAndVerifyText("Title: ExchangeCompletionTitle", "Exchange successful");
		helper.getAndVerifyText("Label: ExchangeCompletionMessage","complete");

	}
	
	public void verifyViewDetailsInExchangeCompletion(){
		
	try {
		helper.clickButton(LocateBy.AID, "Button: ExchangeCompletionDetails",true);
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Button to go to activity and view details, Not found in completion screen!");
		fail();
	}	
		}
	
	public void verifyRefreshConfirmButtons()
	{
		//helper.verifyButtonIsEnabled("Button: ConfirmAndExecute");
		helper.verifyButtonIsEnabled("Button: Refresh Quote");
	}
	
	public void verifyAtPortfolio() {
		// navigating to portfolio tab
		helper.navigateToTab(Tab.Portfolio);
		// Verifying flow is at Portfolio Tab
		helper.verifyAtFlowByTitle("Button: Network", "Portfolio");
		System.out.println("CashBalance for account is: " + getCashBalance());

	}
	
	public double getCashBalance() {
		// gets and returns cashbalance from portfolio page, if not returns existing
		// cashBalance value
		try {
			double cb = Double.parseDouble(
					driver.findElementByAccessibilityId("Label: Cash Balance Value").getText().substring(1));
			cashBalance = cb;
			return cb;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Warning, Not at portfolio page or Cash balance does not exist!");
			return cashBalance;
		}

	}

	public void clickAndVerifyExchange() {
//click exchange button
		helper.clickButton(LocateBy.AID, "Button: Exchange");
//verify exchange title
		helper.verifyAtFlowByTitle("Title: Exchange", "Exchange");
	}

	// clickOn Select: From
	public void selectAnAsset(String fromAssetName, String toAssetName) {
		// Clicking Select From
		helper.clickButton(LocateBy.AID, "Select: From");
		// verifying at select an asset
		helper.verifyAtFlowByTitle("Title: Select an Asset", "SelectAnAsset");
		// setting fromassetname
		helper.setSearchAndSelectCards("Input: Search Asset", fromAssetName);
		// verifying back at exchange!
		helper.verifyAtFlowByTitle("Title: Exchange", "Exchange");
		// Clicking Select To
		helper.clickButton(LocateBy.AID, "Select: To");
		// setting toassetname
		helper.setSearchAndSelectCards("Input: Search Asset", toAssetName);
	}

	// For both owned and not owned Asset first verify exchangeQuoteValue is
	// generated
	// if owned value is 0 verify refresh button is not accessible and Insufficient
	// asset label is displayed!

	public void exchangeVerifications(String fromAssetName, String fromAssetValue) {
		int flagOwnedAsset = 0, flagOwnedValueIsLess = 0;

		helper.setText(LocateBy.AID, "Input: From", fromAssetValue);
		verifyAndReturnExchangeQuoteValue();

		double ownedAssetAmount = Double.parseDouble(
				driver.findElementByAccessibilityId("Label: Owned Value").getText().replaceAll("[^0-9.]", ""));

		// flags to determine if the asset is owned and if yes then, is the passed Value
		// less than what is owned
		if (ownedAssetsAndValue.containsKey(fromAssetName)) {
			flagOwnedAsset = 1;

			if (ownedAssetsAndValue.get(fromAssetName) < (Double.parseDouble(fromAssetValue)))
				flagOwnedValueIsLess = 1;
		}

		// if asset is owned, cross check values on portfolio UI and Exchange UI
		if (flagOwnedAsset == 1)
			verifyPortfolioAndQuoteOwnedValue(ownedAssetAmount, fromAssetName);

		// if ownedAmount is 0 i.e asset is not owned || if asset is owned but value
		// Owned is less than demanded to be exchanged
		// then verify insufficient asset label and that refresh quote button is
		// disabled.
		if (ownedAssetAmount == 0 || flagOwnedValueIsLess == 1) {
			verifyInsufficient(fromAssetName);
			if (!Boolean
					.parseBoolean(driver.findElementByAccessibilityId("Button: Refresh Quote").getAttribute("focused")))
				System.out.println("Refresh Button is not focused and is disabled! PASSED");
			else
				System.out.println("Refresh Button is focused and enabled, which is not expected ,FAILED!");
		}

	}

	public double verifyAndReturnExchangeQuoteValue() {
		double exchangeToQuoteValue = 0.0;

		try {
			exchangeToQuoteValue = Double.parseDouble(driver.findElementByAccessibilityId("Label: To").getText());
		}

		catch (Exception e) {
			System.out.println(e);
			System.out.println("Could not find the quote value in exchange to Label! FAILED");
		}

		if (exchangeToQuoteValue > 0.0)
			System.out.println("Quote Value is greater than 0 " + exchangeToQuoteValue + " ,PASSED");
		else
			System.out.println(
					"Quote Value is not greater than 0 " + exchangeToQuoteValue + " , which is not expected! FAILED");

		return exchangeToQuoteValue;
	}

	public void verifyPortfolioAndQuoteOwnedValue(Double ownedOnExchange, String fromAssetName) {

		System.out.println("Owned value of " + fromAssetName + " in exchange: " + ownedOnExchange);
		System.out.println(
				"Owned value of " + fromAssetName + " in portfolio: " + ownedAssetsAndValue.get(fromAssetName));

		// using double.compare to verify both values are equal!
		if (Double.compare(ownedOnExchange, ownedAssetsAndValue.get(fromAssetName)) == 0)
			System.out.println("Verification successfull, both values match! PASSED");
		else
			System.out.println("Verification unsuccessfull, values are different on both UIs! FAILED");
	}

	public void verifyInsufficient(String fromAssetName) {
		try {
			System.out.println(
					driver.findElementByXPath("//android.widget.TextView[contains(@text,'Insufficient')]").getText()+" , PASSED!");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Insufficient " + fromAssetName + ", NOT FOUND! FAILED!");
		}
	}

	public void verifyPercentButtonExchange(int percent) {
		double ownedAssetAmount = Double.parseDouble(
				driver.findElementByAccessibilityId("Label: Owned Value").getText().replaceAll("[^0-9.]", ""));
		System.out.println(ownedAssetAmount);
		try {
			helper.clickButton(LocateBy.AID, "Button: " + percent + "%", true);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("FAILED, Couldn't find given percentage button!");
		}
		//helper.verifyButtonIsEnabled("Button: ConfirmAndExecute");
		double inputLabelValue = Double.parseDouble(driver.findElementByAccessibilityId("Input: From").getText());
		
		double calcOwnedPercentValue = (ownedAssetAmount * (percent * 0.01));
		System.out.println("Not Rounded: "+calcOwnedPercentValue);
		
		//rounding down to 6 places after decimal
		BigDecimal bd = new BigDecimal(Double.toString(calcOwnedPercentValue));
		bd = bd.setScale(6, RoundingMode.DOWN);
		calcOwnedPercentValue=bd.doubleValue();
		
		System.out.println("After rounding down: "+calcOwnedPercentValue);

		if (Double.compare(inputLabelValue, calcOwnedPercentValue) == 0)
			System.out.println("Calculated Amount in Input: From label is correct: " + inputLabelValue + ", PASSED!");

		else
			System.out.println("FAILED, Calculated Amount: " + calcOwnedPercentValue
					+ " is not equal to value displayed: " + inputLabelValue);

	}

	// gets and stores all the assets inside portfolio UI, all owned assets and
	// their values!
	public void getOwnedAssetValue() {
		int numberOfAssetsOwned, i, catchFlag = 0;
		numberOfAssetsOwned = helper.getTotalNumberOfElementsInList(structureType.view, ElementType.ViewGroup,
				"List: Assets");
	
		for (i = 1; i <= numberOfAssetsOwned; i++) {
			try {
				ownedAssetsAndValue
						.put(driver.findElementByAccessibilityId("Card: Asset " + i + ": Label AssetName").getText(),
								Double.parseDouble(driver
										.findElementByAccessibilityId("Card: Asset " + i + ": Label AssetDescription")
										.getText().replaceAll("[^0-9.]", "")));
			}
	
			catch (Exception e) {
				++catchFlag;
				--i;
				if (catchFlag == 3) {
					System.out
							.println("Failed to find given number of elements, even after swiping up 2 times!, FAILED");
					System.out.println(e);
					return;
				}
	
				System.out.println("Swiping up to find the element!, WARNING");
				helper.swipe(LocateBy.AID, "List: Assets " + numberOfAssetsOwned, "up");
			}
		}
	
		System.out.println(
				"\n Following are all the owned assets with their corresponding value: \n" + ownedAssetsAndValue);
	}
}
