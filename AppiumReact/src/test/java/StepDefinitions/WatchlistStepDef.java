package StepDefinitions;

import java.util.ArrayList;
import java.util.List;

import Pages.DashboardHomeWatchlist;
import helper.ActionHelper;
import helper.DriverRunner;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class WatchlistStepDef {
	ActionHelper helper = new ActionHelper();
	DashboardHomeWatchlist obj=new DashboardHomeWatchlist();
	 //List<String> themeNames=new ArrayList<String>();
	 //int totalNumberOfThemes=0;
	 //List<String> assetNames=new ArrayList<String>();
	 //int totalNumberOfAssets=0;
		DriverRunner driverRunnerObject = DriverRunner.getInstanceOfDriverRunner();
		AppiumDriver driver = driverRunnerObject.getDriver();
	
	@Then("I verify and scroll through Themes visible on Homescreen and click See All")
	public void i_verify_and_scroll_through_themes_visible_on_homescreen() throws Exception {
		obj.watchlistHome();
	}

	@Then("All watchlists are asserted and scrolled")
	public void all_watchlists_are_asserted_and_scrolled() {
		obj.getNumberOfThemes();
		obj.setThemeNames();
	}

	@When("I click the plus button to create a theme")
	public void i_click_the_plus_button_to_create_a_theme() {
		driver.findElementByAccessibilityId("Button: Create theme").click(); // clicking plus button to create theme
	}

	@Then("I am on create a theme flow")
	public void i_am_on_create_a_theme_flow()
	{
		obj.verifyCreateThemeFlow();	
	}
	
	@When("I enter an existing theme name {string} assetName {string} select {int} asset I get already used error")
	public void i_enter_an_existing_theme_name_asset_name_select_asset_i_get_already_used_error(String themeName, String assetName, Integer numberOfAssetsToSelect) {
		obj.enterThemeName_AssetSearch(themeName, assetName, numberOfAssetsToSelect);
	}

	@When("I create a theme named {string} assetName {string} select {int} assets then number of themes is incremented")
	public void i_create_a_theme_named_asset_name_select_assets_then_number_of_themes_is_incremented(String themeName, String assetName, Integer numberOfAssetsToSelect) {
		obj.enterThemeName_AssetSearch(themeName, assetName, numberOfAssetsToSelect);
	}

	@Then("I am on the watchlist themes screen")
	public void i_am_on_the_watchlist_themes_screen() {
	 obj.verifyThemesWatchlistFlow();
	}

	@When("I click on watchlist theme name {string} and get number of Assets in the watchlist")
	public void i_click_on_watchlist_theme_name_and_get_number_of_assets_in_the_watchlist(String themeName) throws Exception {
		driver.navigate().back();
		driver.findElementByXPath("(//android.view.ViewGroup[@content-desc='Button: See All'])[3]").click();
		obj.clickOnTheme(themeName);
		obj.getNumberOfAssets();
		obj.setAssetNames();
	}

	@When("I swipe left on asset {string} and click on Delete X Button")
	public void i_swipe_left_on_asset_and_click_on_delete_x_button(String assetName) {
	    obj.swipeDeleteAsset(assetName);
	}

	@Then("the asset is removed from the watchlist and number of assets is decremented")
	public void the_asset_is_removed_from_the_watchlist_and_number_of_assets_is_decremented() {
		obj.numberOfAssetsAfterDeletion();
	}

	@When("I click on Edit Theme in {string} watchlist")
	public void i_click_on_edit_theme_in_watchlist(String themeName) {
		obj.clickOnEditThemeFlow(themeName);
		obj.verifyAtEditTheme();
	}

	@Then("I deselect all existing assets and verify submit button does not exist")
	public void i_deselect_all_existing_assets_and_verify_submit_button_does_not_exist() {
	    obj.deselectAllAssetsEditTheme();
	}

	@Then("I search for {string} and select the first asset")
	public void i_search_for_and_select_the_first_asset(String assetSearch) {
		obj.selectAssetsEditTheme(assetSearch);
	}

	@Then("I click Submit and verify the theme is updated and number of assets are reduced to one")
	public void i_click_submit_and_verify_the_theme_is_updated_and_number_of_assets_are_reduced_to_one() {
		obj.verifyEditThemeSuccess();	//clicks submit and verifies
	}

	@When("I long press watchlist {string}")
	public void i_long_press_watchlist(String watchlistName) {
		System.out.println(watchlistName+"\n"+obj.themeNames);
		
		if(watchlistName.equals(obj.themeNames.get(0)))
			System.out.println(watchlistName+" is equal to "+obj.themeNames.get(0));
		else
			System.out.println(watchlistName+" not equal to "+obj.themeNames.get(0));
		
		System.out.println(obj.themeNames.indexOf(watchlistName) + 1);
		helper.longPress("Button: Watchlist " + (obj.themeNames.indexOf(watchlistName) + 1));
	}

	@Then("I get a popup with title Edit {string} and on losing focus I am navigated back to the watchlist themes page")
	public void i_get_a_popup_with_title_edit_and_on_losing_focus_i_am_navigated_back_to_the_watchlist_themes_page(String themeName) {
		obj.assertEditThemeUI(themeName);
		
	}
	
	@When("I long press watchlist {string} and click on rename")
	public void i_long_press_watchlist_and_click_on_rename(String watchlistName) {
	helper.longPress("Button: Watchlist " + (obj.themeNames.indexOf(watchlistName) + 1));
	driver.findElementByAccessibilityId("Button: Rename Theme").click();
	}

	@When("I enter the new name for {string} as {string} then I get an error saying name is same as previous")
	public void i_enter_the_new_name_for_as_then_i_get_an_error_saying_name_is_same_as_previous(String string, String string2) {
	  obj.renameThemeFlow(string, string2);
	}

	@When("I enter the new name for {string} as {string} then I get an error saying name is already used")
	public void i_enter_the_new_name_for_as_then_i_get_an_error_saying_name_is_already_used(String string, String string2) {
		  obj.renameThemeFlow(string, string2);
	}

	@When("I enter the new name for {string} as {string} then the theme is renamed successfully and navigated back to watchlists")
	public void i_enter_the_new_name_for_as_then_the_theme_is_renamed_successfully_and_navigated_back_to_watchlists(String string, String string2) {
		  obj.renameThemeFlow(string, string2);
	}

	@Then("I click on delete button in Edit RFTest popup")
	public void i_click_on_delete_button1_in_edit_rftest_poopup() {
		driver.findElementByAccessibilityId("Button: Delete Theme").click();
	}

	@Then("I am on are you sure? pop up and verify keep theme feature works fine")
	public void i_am_on_are_you_sure_pop_up_and_veriyf_keep_theme_feature_works_fine() {
	obj.deleteThemeFlowKeep();
	}

	@Then("I click on delete button and I click delete again to confirm {string} is deleted and verify number of themes is decremented")
	public void i_click_on_delete_button_and_i_click_delete_again_to_confirm_is_deleted_and_verify_number_of_themes_is_decremented(String themeToDelete) {
    obj.deleteThemeFlowDelete(themeToDelete);
}

}
