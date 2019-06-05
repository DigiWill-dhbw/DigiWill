@uitest
Feature: EditProfile
  As a mortal
  I want to edit my profile information

  Background:
    Given "/?login" is open
    Given A user with email "example@mail.com" and password "password" exists
    When Enter Email "example@mail.com", password "password" and login
    Then Login "succeeds"

  Scenario Outline: 01 - Profile edit successful
    When I click on element with id "userButtonHeader"
    And I enter "<firstName>" into field with id "firstNameInput"
    And I enter "<surName>" into field with id "surNameInput"
    And I enter "<birthday>" into field with id "birthday"
    And I enter "<streetAddress>" into field with id "streetAddressInput"
    And I enter "<zipCode>" into field with id "zipCodeInput"
    And I enter "<city>" into field with id "cityInput"
    And I enter "<country>" into field with id "countryInput"
    And I click on element with id "saveProfileButton"
    Then I'm on page "/"

    Examples:
      | firstName | surName | birthday | streetAddress  | zipCode | city      | country |
      | Tester    | McTest  | 04201990 | Test-Street 41 | 12345   | Test-City | Germany |
      | Martin    | Muller  | 01011990 | Test-Street 42 | 12345   | Test-City | Germany |


#  Scenario Outline: 02 - Profile edit unsuccessful
#    When I click on element with id "userButtonHeader"
#    And I enter "<firstName>" into field with id "firstNameInput"
#    And I enter "<surName>" into field with id "surNameInput"
#    And I enter "<birthday>" into field with id "birthday"
#    And I enter "<streetAddress>" into field with id "streetAddressInput"
#    And I enter "<zipCode>" into field with id "zipCodeInput"
#    And I enter "<city>" into field with id "cityInput"
#    And I enter "<country>" into field with id "countryInput"
#    And I click on element with id "saveProfileButton"
#    Then I'm on page "/profile"

#    Examples:
#      | firstName | surName | birthday | streetAddress  | zipCode | city      | country |
#      |           | McTest  | 04201990 | Test-Street 41 | 12345   | Test-City | Germany |
#      | Martin    |         | 01011990 | Test-Street 42 | 12345   | Test-City | Germany |
#      | Martin    | McTest  | abc      | Test-Street 42 | 12345   | Test-City | Germany |