@testest
@uitest
Feature: Webhook Crud
  As a mortal
  I want certain IFTTT events to trigger when I die

  Background:
    Given "/?login" is open
    Given A user with email "example@mail.com" and password "password" exists
    When Enter Email "example@mail.com", password "password" and login
    Then Login "succeeds"
    And "/webhook" is open
    And Webhook is not configured

  Scenario Outline: 01 - Create valid webhook action
    When I enter "<apikey>" into field with id "apikey"
    And I click on element with id "addNewEventButton"
    And I enter event "<eventname>" at position 0
    And I click on element with id "submitButton"
    Then I "do" see event "<eventname>" in table
    And I see "<apikey>" in input field with id "apikey"

    Examples:
      | apikey        | eventname     |
      | cool_api_key  | test          |
      | another_key   | case_of_death |

  Scenario Outline: 02 - Create multiple webhook actions
    When I enter "<apikey>" into field with id "apikey"
    And I click on element with id "addNewEventButton"
    And I enter event "<eventname_1>" at position 0
    And I click on element with id "submitButton"
    Then I "do" see event "<eventname_1>" in table
    And I see "<apikey>" in input field with id "apikey"
    Then I click on element with id "addNewEventButton"
    And I enter event "<eventname_2>" at position 1
    And I click on element with id "addNewEventButton"
    And I enter event "<eventname_3>" at position 2
    And I click on element with id "submitButton"
    Then I "do" see event "<eventname_1>" in table
    And I "do" see event "<eventname_2>" in table
    And I "do" see event "<eventname_3>" in table
    And I see "<apikey>" in input field with id "apikey"

    Examples:
      | apikey        | eventname_1   | eventname_2   | eventname_3 |
      | cool_api_key  | test          | test2         | test3       |
      | another_key   | case_of_death | event_on_death| do_you_dead |

  Scenario Outline: 03 - Change webhook action
    When I enter "<apikey>" into field with id "apikey"
    And I click on element with id "addNewEventButton"
    And I enter event "<eventname_1>" at position 0
    And I click on element with id "submitButton"
    Then I "do" see event "<eventname_1>" in table
    And I see "<apikey>" in input field with id "apikey"
    Then I click on element with id "addNewEventButton"
    And I enter event "<eventname_2>" at position 1
    And I click on element with id "submitButton"
    Then I "do" see event "<eventname_1>" in table
    And I "do" see event "<eventname_2>" in table
    And I see "<apikey>" in input field with id "apikey"
    Then I clear event at position 0
    And I enter event "<eventname_3>" at position 0
    And I click on element with id "submitButton"
    Then I "do" see event "<eventname_2>" in table
    And I "do" see event "<eventname_3>" in table
    And I "do not" see event "<eventname_1>" in table

    Examples:
      | apikey        | eventname_1   | eventname_2   | eventname_3 |
      | cool_api_key  | test          | test2         | test3       |
      | another_key   | case_of_death | event_on_death| do_you_dead |

  Scenario Outline: 04 - Delete one Webhook Action
    When I enter "<apikey>" into field with id "apikey"
    And I click on element with id "addNewEventButton"
    And I enter event "<eventname_1>" at position 0
    And I click on element with id "addNewEventButton"
    And I enter event "<eventname_2>" at position 1
    And I click on element with id "submitButton"
    Then I "do" see event "<eventname_1>" in table
    And I "do" see event "<eventname_2>" in table
    And I see "<apikey>" in input field with id "apikey"
    Then I clear event at position 0
    And I click on element with id "submitButton"
    Then I see "<apikey>" in input field with id "apikey"
    And I "do not" see event "<eventname_1>" in table
    And I "do" see event "<eventname_2>" in table

    Examples:
      | apikey        | eventname_1   | eventname_2   |
      | cool_api_key  | test          | test2         |
      | another_key   | case_of_death | event_on_death|

  Scenario Outline: 05 - Delete all Webhook Actions
    When I enter "<apikey>" into field with id "apikey"
    And I click on element with id "addNewEventButton"
    And I enter event "<eventname_1>" at position 0
    And I click on element with id "addNewEventButton"
    And I enter event "<eventname_2>" at position 1
    And I click on element with id "submitButton"
    Then I "do" see event "<eventname_1>" in table
    And I "do" see event "<eventname_2>" in table
    And I see "<apikey>" in input field with id "apikey"
    Then I click on element with id "deleteAllButton"
    Then I see "" in input field with id "apikey"
    And I "do not" see event "<eventname_1>" in table
    And I "do not" see event "<eventname_2>" in table

    Examples:
      | apikey        | eventname_1   | eventname_2   | 
      | cool_api_key  | test          | test2         | 
      | another_key   | case_of_death | event_on_death| 
