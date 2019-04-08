@skip
Feature: Sign of life daemon
  As the Server
  I want to make sure all actions are triggered when necessary
  and all reminders are sent out when necessary

  Background:
    Given "/login" is open
    Given A user with email "example@mail.com" and password "password" exists
    When Enter Email "example@mail.com", password "password" and login
    Then Login "succeeds"
    And a duration is set in profile settings

  Scenario: 01 - Get a reminder
    When Counter is started
    And Counter is expired
    And I have navigated to main page
    Then I should see a reminder in the notification area

  Scenario: 02 - Trigger configured activities
    When Counter is started
    And Counter is expired
    And I have ignored all reminders
    Then I can not login anymore
    And The configured activities should be triggered