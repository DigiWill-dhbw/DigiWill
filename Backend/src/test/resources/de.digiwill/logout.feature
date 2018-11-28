Feature:
  As a mortal
  I want to logout from my registered account

  Background:
    Given User is logged in and on the action overview page

  Scenario Outline: Simple logout
    When Clicking profile icon
    Then Menu is visible
    When Clicking "Logout"
    Then Logout was successful
