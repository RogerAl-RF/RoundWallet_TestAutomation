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

Feature: Exchange of Assets
	@Exchange @Demorun2
  Scenario: Verify Exchange Of Assets Flow! Porfolio and Exchange cross verification, Submitting Exchange, Verifying Exchange is Disabled
	Given I launch the application
	When I am on the Welcome screen
	When I Enter the correct MPIN
  Then flow is navigated to the Dashboard Homescreen
  
  Then I navigate to portfolio and print available cash balance
  Then I view all owned assets on portfolio UI and print them
  When I click the exchange button and verify i am on exchange flow
  Then I enter the Exchange From Asset as "Dai" and Exchange To Asset as "Sushi"
  When I input "Dai" value as "999" then check that quote value is generated, insufficient asset is shown and refreshquote button is disabled 
  When I click cancel
  Then I am navigated back to the portfolio page
  
  When I click the exchange button and verify i am on exchange flow
  Then I enter the Exchange From Asset as "USD Coin" and Exchange To Asset as "Wrapped Bitcoin"
  Then I input "USD Coin" value as "1" then check that quote value is generated, portfolio AssetValue and ExchangeOwnedAsset Value is equal
  
  When I click 75% button then input field is populated with 75% of owned amount
  When I click 10% button then input field is populated with 10% of owned amount
  Then I Verify the confirm button exists also the refresh button exists
  When I click on refresh button to refresh the quote, then click confirm and execute to complete the exchange
  Then I Verify title completing exchange has completing and the message And Verify title exchange successfull and has successfull message
  When I Click the button link in exchange successful window to open the activity tab
  Then I am on Activity tab and I swipe down to refresh and verify the record exist and is in completed state
  Then quit the application
  