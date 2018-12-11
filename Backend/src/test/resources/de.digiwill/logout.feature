Feature: Logout
  As a mortal
  I want to logout from my registered account

  Background:
    Given "/login" is open

  Scenario Outline: Simple logout
    When Clicking profile icon
    Then Menu is visible
    When Clicking "Logout"
    Then Logout was successful
