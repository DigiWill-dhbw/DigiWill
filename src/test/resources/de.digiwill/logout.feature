@uitest
Feature: Logout
  As a mortal
  I want to logout from my registered account

  Background:
    Given "/login" is open
    Given A user with email "example@mail.com" and password "password" exists
    When Enter Email "example@mail.com", password "password" and login
    Then Login "succeeds"

  Scenario: 01 - Simple logout
    And Clicking in header "Logout" on Mainpage
    Then Logout was successful
