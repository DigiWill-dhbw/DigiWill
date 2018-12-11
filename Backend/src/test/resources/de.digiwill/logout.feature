@uitest
Feature: Logout
  As a mortal
  I want to logout from my registered account

  Background:
    Given "/login" is open
    And The user "test_user_email_crud@digiwill.de" with the password "Blabla42!" is logged in

  Scenario: 01 - Simple logout
    And Clicking in header "Logout" on Mainpage
    Then Logout was successful
