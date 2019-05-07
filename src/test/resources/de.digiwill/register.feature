@uitest
Feature: Register
  As a mortal
  I want to register a new account

  Background:
    Given "/register" is open

  Scenario Outline: 01 - Registration
    When I enter "<email>" into field with id "emailInput"
    And I enter "<password>" into field with id "passwordInput"
    And I enter "<passwordRep>" into field with id "passwordInput_rep"
    And I enter "<firstName>" into field with id "firstName"
    And I enter "<surName>" into field with id "surName"
    And I enter "<birthday>" into field with id "birthday"
    And I click on element with id "registerButton"
    Then I'm on page "<result>"



    Examples:
      | email            | password       | passwordRep    | firstName | surName | birthday | result    |
      | example@mail.com | validPa33word! | validPa33word! | Tester    | McTest  | 1990-01-01 | /         |
      | example@mail.com | password       | password       | Tester    | McTest  | 1990-01-01 | /register |
      | example@mail.com | validPa33word! | VALIDPa33word? | Tester    | McTest  | 1990-01-01 | /register |
      | example-mail.com | validPa33word! | validPa33word! | Tester    | McTest  | 1990-01-01 | /register |