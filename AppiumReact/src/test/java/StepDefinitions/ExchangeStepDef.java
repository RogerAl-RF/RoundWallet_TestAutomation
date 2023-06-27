package StepDefinitions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import Pages.Portfolio_Exchange;
import helper.ActionHelper;
import helper.AllEnums.LocateBy;
import helper.DriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ExchangeStepDef {
	ActionHelper helper = new ActionHelper();
	Portfolio_Exchange obj=new Portfolio_Exchange();

	DriverRunner driverRunnerObject = DriverRunner.getInstanceOfDriverRunner();
	AppiumDriver driver = driverRunnerObject.getDriver();
	
	@Then("I navigate to portfolio and print available cash balance")
	public void i_navigate_to_portfolio_and_print_available_cash_balance() {
		try {
			Thread.sleep(Duration.ofSeconds(30));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	obj.verifyAtPortfolio();
	}
	@Then("I view all owned assets on portfolio UI and print them")
	public void i_view_all_owned_assets_on_portfolio_ui_and_print_them() {
	obj.getOwnedAssetValue();
	}
	@When("I click the exchange button and verify i am on exchange flow")
	public void i_click_the_exchange_button_and_verify_i_am_on_exchange_flow() {
	obj.clickAndVerifyExchange();
	}
	@Then("I enter the Exchange From Asset as {string} and Exchange To Asset as {string}")
	public void i_enter_the_exchange_from_asset_as_and_exchange_to_asset_as(String string, String string2) {
	obj.selectAnAsset(string, string2);
	}
	@When("I input {string} value as {string} then check that quote value is generated, insufficient asset is shown and refreshquote button is disabled")
	@Then("I input {string} value as {string} then check that quote value is generated, portfolio AssetValue and ExchangeOwnedAsset Value is equal")
	public void i_input_value_as_then_check_that_quote_value_is_generated_insufficient_asset_is_shown_and_refreshquote_button_is_disabled(String string, String input) {
	obj.exchangeVerifications(string, input);
	}
	@When("I click cancel")
	public void i_click_cancel_button_cancel() {
	    helper.clickButton(LocateBy.AID, "Button: Cancel");
	}
	@Then("I am navigated back to the portfolio page")
	public void i_am_navigated_back_to_the_portfolio_page() {
		 helper.verifyAtFlowByTitle("Button: Network", "Portfolio");
	}

	@When("I click {int}% button then input field is populated with {int}% of owned amount")
	public void i_click_button_then_input_field_is_populated_with_of_owned_amount(Integer int1, Integer int2) {
	obj.verifyPercentButtonExchange(int1);
	}
	
	@When("I Verify the confirm button exists also the refresh button exists")
	public void verify_refresh_and_confirm_buttons()
	{
		obj.verifyRefreshConfirmButtons();
	}
	
	@When("I click on refresh button to refresh the quote, then click confirm and execute to complete the exchange")
	public void i_click_on_refresh_button_to_refresh_the_quote_then_click_confirm_and_execute_to_complete_the_exchange() throws InterruptedException {
		System.out.println("Click 10 now!");
		//helper.clickButton(LocateBy.AID, "Button: Refresh Quote");
		MobileElement rb = (MobileElement)driver.findElementByAccessibilityId("Button: Refresh Quote");
		//String bounds = rb.getAttribute("bounds");
		//PointOption p = helper.clickByCoordinates(bounds);
	//	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		System.out.println("implict wait set to 1");
		System.out.println(driver.getPageSource());
		rb.click();
		System.out.println(""+driver.getPageSource());
	//	System.out.println("Waiting for 3 seconds to click confirm");
	//	Thread.sleep(Duration.ofSeconds(3));
	//	helper.clickByPointOption(p);
		
		//helper.clickByCoordinates(bounds);
		
	//	driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS);
		//driver.findElementByXpath("//android.widget." + "[contains(@content-desc,'"+ containsContentDescOfXpath + "')]").click;

//		helper.clickButton(LocateBy.AID, "Button: Refresh Quote");
//		System.out.println(driver.getPageSource());
	}
	@Then("I Verify title completing exchange has completing and the message And Verify title exchange successfull and has successfull message")
	public void i_verify_title_completing_exchange_has_completing_and_the_message() {
		obj.verifyExchangeCompletion();
	}

	@When("I Click the button link in exchange successful window to open the activity tab")
	public void i_click_the_button_link_in_exchange_successful_window_to_open_the_activity_tab() {
		obj.verifyViewDetailsInExchangeCompletion();
	}
	@Then("I am on Activity tab and I swipe down to refresh and verify the record exist and is in completed state")
	public void i_am_on_activity_tab_and_i_swipe_down_to_refresh_and_verify_the_record_exist_and_is_in_completed_state() {

	}
}
