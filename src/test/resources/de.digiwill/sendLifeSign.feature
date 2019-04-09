@uitest
Feature: Send Life Sign
  As a mortal
  I want to send a life sign

  Background:
    Given "/?login" is open
    Given A user with email "example@mail.com" and password "password" exists
    When Enter Email "example@mail.com", password "password" and login
    Then Login "succeeds"

  Scenario: Send valid life sign
    When Clicking in header "Send life sign" on Mainpage
    Then User with email "example@mail.com" has sent a Life sign