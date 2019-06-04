@uitest
@mailtest
Feature: Reset password
  As a mortal
  I want to reset my password

  Background:
    Given "/" is open

  Scenario Outline: 01 - Password reset successful
    Given A user with email "<email>" and password "<password>" exists
    And I click on element with id "loginButtonHeader"
    And I click on element with id "passwordRecoveryLink"
    When I enter "<email>" into field with id "passwordRecoveryEmailInput"
    And I click on element with id "passwordRecoveryButton"
    Then I'm on page "/"
    And I open password recovery mail with account "<email>"
    Then I'm on page with url containing "callback"
    And I enter "<new_password>" into field with id "passwordInput"
    And I enter "<new_password>" into field with id "passwordInput_rep"
    And I click on element with id "resetButton"
    Then I'm on page "/"
    And I click on element with id "loginButtonHeader"
    When Enter Email "<email>", password "<new_password>" and login
    Then Login "succeeds"

    Examples:
      | email             | password       | new_password |
      | example@127.0.0.1 | validPa33word! | n3wP4$$w0rd  |
      | test@127.0.0.1    | Test123$       | T35t123$     |

    Scenario Outline: 02 - User does not exist
      And I click on element with id "loginButtonHeader"
      And I click on element with id "passwordRecoveryLink"
      When I enter "<email>" into field with id "passwordRecoveryEmailInput"
      And I click on element with id "passwordRecoveryButton"
      Then I'm on page "/"
      Examples:
        | email             | password       |
        | example@127.0.0.1 | validPa33word! |
        | test@127.0.0.1    | Test123$       |

