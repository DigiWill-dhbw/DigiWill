@uitest
Feature: Admin User Overview
  As an admin
  I want to be able to view and interact with all registered users

  Background:
    Given "/?login" is open
    Given An admin with email "example@mail.com" and password "password" exists
    When Enter Email "example@mail.com", password "password" and login
    Then Login "succeeds"

  Scenario Outline: 01 - Overview
    Given A user with email "<email_1>", password "password", first name "<firstName_1>" and surname "<surName_1>" exists
    And A user with email "<email_2>", password "password", first name "<firstName_2>" and surname "<surName_2>" exists
    When "/admin/overview/users" is open
    Then The admin overview has 3 users
    And A user with email "<email_2>", first name "<firstName_2>" and surname "<surName_2>" is displayed
    And A user with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed

    Examples:
      | email_1         | firstName_1 | surName_1 | email_2         | firstName_2 | surName_2 |
      | user_1@mail.com | Thomas      | Userman   | user_2@mail.com | Simone      | Schneider |

  Scenario Outline: 02 - Toggle admin role
    Given A user with email "<email_1>", password "password", first name "<firstName_1>" and surname "<surName_1>" exists
    When "/admin/overview/users" is open
    Then The admin overview has 2 users
    And A user with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed
    And I click table column 6 for user with email "<email_1>"
    And An admin with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed
    And I click table column 6 for user with email "<email_1>"
    And A user with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed

    Examples:
      | email_1         | firstName_1 | surName_1 |
      | user_1@mail.com | Thomas      | Userman   |

  Scenario Outline: 03 - Delete user
    Given A user with email "<email_1>", password "password", first name "<firstName_1>" and surname "<surName_1>" exists
    When "/admin/overview/users" is open
    Then The admin overview has 2 users
    And A user with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed
    When I click table column 8 for user with email "<email_1>"
    And I click on element with id "deleteButton"
    Then The admin overview has 1 users
    And No user with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed

    Examples:
      | email_1         | firstName_1 | surName_1 |
      | user_1@mail.com | Thomas      | Userman   |
    
  @mailtest
  Scenario Outline: 04 - Reset password
    Given A user with email "<email_1>", password "password", first name "<firstName_1>" and surname "<surName_1>" exists
    When "/admin/overview/users" is open
    Then The admin overview has 2 users
    And A user with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed
    When I click table column 7 for user with email "<email_1>"
    And Clicking in header "Logout" on Mainpage
    And Logout was successful
    And I open password recovery mail with account "<email_1>"
    Then I'm on page with url containing "callback"
    And I enter "<new_password>" into field with id "passwordInput"
    And I enter "<new_password>" into field with id "passwordInput_rep"
    And I click on element with id "resetButton"
    Then I'm on page "/"
    And I click on element with id "loginButtonHeader"
    When Enter Email "<email_1>", password "<new_password>" and login
    Then Login "succeeds"

    Examples:
      | email_1         | firstName_1 | surName_1 | new_password |
      | user_1@127.0.0.1| Thomas      | Userman   | Test123$     |
