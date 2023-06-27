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

Feature: Welcome Screen

  @Welcome @Demorun
  
  Scenario: Enter wrong MPIN, Verify forgot my passcode - Ok and Recover flows, Enter correct MPIN and verify flow is navigated to Dashboard
  
  Given I launch the application
	When I am on the Welcome screen
  Then I enter a  Wrong MPIN
  Then Error message is shown

  When I Click on Forgot my passcode and click Ok button
  Then I am on the Welcome screen
  Then I Click on Forgot my passcode and click recover button
  Then Recover wallet Enter Email address screen is displayed
  And Support screen is displayed and flow is navigated back to Welcome Screen
  Then I am on the Welcome screen

  #Then remove the wrongly entered MPIN
  When I Enter the correct MPIN
  Then flow is navigated to the Dashboard Homescreen
  Then quit the application

#    Examples
#       Departure  Arrivals 
#       DBX        JFK      
