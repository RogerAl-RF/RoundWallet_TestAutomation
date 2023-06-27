#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template


Feature: Watchlist - Themes on Home Screen
	@Watchlist @Demorun1
  Scenario: Create a new theme -edit theme -rename -delete
	Given I launch the application
	When I am on the Welcome screen
	When I Enter the correct MPIN
  Then flow is navigated to the Dashboard Homescreen
  
  Then I verify and scroll through Themes visible on Homescreen and click See All
  Then All watchlists are asserted and scrolled
  When I click the plus button to create a theme
  Then I am on create a theme flow
  When I enter an existing theme name "Popular" assetName "Ethereum" select 1 asset I get already used error
  When I create a theme named "RogerTest" assetName "doge" select 1 assets then number of themes is incremented
  Then I am on the watchlist themes screen
 
  
  When I click on watchlist theme name "RogerTest" and get number of Assets in the watchlist
  And  I swipe left on asset "Dogecoin" and click on Delete X Button
  Then the asset is removed from the watchlist and number of assets is decremented
  
  When I click on Edit Theme in "RogerTest" watchlist
  Then I deselect all existing assets and verify submit button does not exist
  Then I search for "Ethereum" and select the first asset
  Then I click Submit and verify the theme is updated and number of assets are reduced to one
  
  When I long press watchlist "RogerTest"
  Then I get a popup with title Edit "RogerTest" and on losing focus I am navigated back to the watchlist themes page
  When I long press watchlist "RogerTest" and click on rename
  And  I enter the new name for "RogerTest" as "RogerTest" then I get an error saying name is same as previous
  When I enter the new name for "RogerTest" as "Popular" then I get an error saying name is already used
  When I enter the new name for "RogerTest" as "RFTest" then the theme is renamed successfully and navigated back to watchlists
  
  When I long press watchlist "RFTest"
  Then I click on delete button in Edit RFTest popup
  Then I am on are you sure? pop up and verify keep theme feature works fine
  When I long press watchlist "RFTest"
  Then I click on delete button and I click delete again to confirm "RFTest" is deleted and verify number of themes is decremented
  Then quit the application
	