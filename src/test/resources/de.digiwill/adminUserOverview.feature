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
    And I toggle admin role for user with email "<email_1>"
    And An admin with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed
    And I toggle admin role for user with email "<email_1>"
    And A user with email "<email_1>", first name "<firstName_1>" and surname "<surName_1>" is displayed

    Examples:
      | email_1         | firstName_1 | surName_1 |
      | user_1@mail.com | Thomas      | Userman   |
