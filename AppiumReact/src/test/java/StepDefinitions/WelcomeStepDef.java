package StepDefinitions;



import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Pages.WelcomeScreen;
import helper.DriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WelcomeStepDef {

	WelcomeScreen welcomeObj = new WelcomeScreen();
    final int[] wrongPin = {2,2,4,1,2,1};//incorrect pin
    final int[] correctPin = {2,2,4,1,2,2};//Correct pin
	
	@Given("I launch the application")
	public void i_launch_the_application() {
	    // Launch Application
		welcomeObj.launchRoundApp();
	}

	@When("I am on the Welcome screen")
	public void i_am_on_the_welcome_screen() {
		welcomeObj.verifyAtWelcome();
	}

	@Then("I enter a  Wrong MPIN")
	public void i_enter_a_wrong_mpin() {
		welcomeObj.enterPin(wrongPin,"224121"); //Entering WrongPin
	}

	@Then("Error message is shown")
	public void error_message_is_shown() {
		welcomeObj.verifyIncorrectPinError();//Verifying Incorrect Pin Error exists
	}
	
	@When("I Click on Forgot my passcode and click Ok button")
	public void i_click_on_forgot_my_passcode_and_click_ok_button() {
		welcomeObj.forgotPasscodePopupOk();
	}

	@Then("I Click on Forgot my passcode and click recover button")
	public void i_click_on_forgot_my_passcode_and_click_recover_button() {
		welcomeObj.forgotPasscodePopupRecover();
	}

	@Then("Recover wallet Enter Email address screen is displayed")
	public void recover_wallet_enter_email_address_screen_is_displayed() {
		welcomeObj.verifyForgotPassRecover();
	}

	@Then("Support screen is displayed and flow is navigated back to Welcome Screen")
	public void support_screen_is_displayed_and_flow_is_navigated_back_to_welcome_screen() {
		welcomeObj.verifyForgotPassRecoverSupport();
	}

	@Then("remove the wrongly entered MPIN")
	public void remove_the_wrongly_entered_mpin() {
		welcomeObj.removePin();
	}

	@When("I Enter the correct MPIN")
	public void i_enter_the_correct_mpin() {

		welcomeObj.enterPin(correctPin,"224122"); //Entering CorrectPin
	}

	@Then("flow is navigated to the Dashboard Homescreen")
	public void flow_is_navigated_to_the_dashboard_homescreen() {
		welcomeObj.verifyAtHome();
	}

	@Then("quit the application")
	public void quit_the_application() throws InterruptedException {
		//Waiting for 30 seconds before quitting the application

		
		Thread.sleep(30000);;
		welcomeObj.quitApplication();
	}

	@Given("I click anything")
	public void i_click_anything() {
	    // Launch Application
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		welcomeObj.testApp();
	}

}
