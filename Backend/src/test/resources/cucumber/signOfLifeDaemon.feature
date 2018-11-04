Feature: Sign of life daemon

  Background:
    Given The user is logged in
    And a duration is set in profile settings

  Scenario: Get a reminder
    When Counter is started
    And Counter is expired
    And I have navigated to main page
    Then I should see a reminder in the notification area

  Scenario: Trigger configured activities
    When Counter is started
    And Counter is expired
    And I have ignored all reminders
    Then I can not login anymore
    And The configured activities should be triggered