@uitest
@mailtest
Feature: Register
  As a mortal
  I want to register a new account

  Background:
    Given "/register" is open

  Scenario Outline: 01 - Registration successful
    When I enter "<email>" into field with id "emailInput"
    And I enter "<password>" into field with id "passwordInput"
    And I enter "<passwordRep>" into field with id "passwordInput_rep"
    And I enter "<firstName>" into field with id "firstName"
    And I enter "<surName>" into field with id "surName"
    And I enter "<birthday>" into field with id "birthday"
    And I click on element with id "registerButton"
    Then I'm on page "/"
    And I open verification mail with account "<email>"
    Then I'm on page with url containing "<result>"

    Examples:
      | email             | password       | passwordRep    | firstName | surName | birthday | result    |
      | example@127.0.0.1 | validPa33word! | validPa33word! | Tester    | McTest  | 04201990 | callback |
      | test@127.0.0.1    | Test123$       | Test123$       | Martin    | Muller  | 01011990 | callback |

  Scenario Outline: 02 - Registration unsuccessful
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
      | example@mail.com | password       | password       | Tester    | McTest  | 01011990 | /register |
      | example@mail.com | validPa33word! | VALIDPa33word? | Tester    | McTest  | 01011990 | /register |
      | example-mail.com | validPa33word! | validPa33word! | Tester    | McTest  | 01011990 | /register |