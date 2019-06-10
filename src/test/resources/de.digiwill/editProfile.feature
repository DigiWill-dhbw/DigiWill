@uitest
Feature: Edit Profile
  As a mortal
  I want to edit my profile information

  Background:
    Given "/?login" is open
    Given A user with email "example@mail.com" and password "password" exists
    When Enter Email "example@mail.com", password "password" and login
    Then Login "succeeds"

  Scenario Outline: 01 - Edit Personal Data successful
    When I click on element with id "userButtonHeader"
    And I click on element with id "editProfileButton"
    And I clear field with id "firstNameInput"
    And I enter "<firstName>" into field with id "firstNameInput"
    And I clear field with id "surNameInput"
    And I enter "<surName>" into field with id "surNameInput"
    And I clear field with id "birthday"
    And I enter "<birthday>" into field with id "birthday"
    And I enter "<streetAddress>" into field with id "streetAddressInput"
    And I enter "<zipCode>" into field with id "zipCodeInput"
    And I enter "<city>" into field with id "cityInput"
    And I enter "<country>" into field with id "countryInput"
    And I click on element with id "saveProfileButton"
    Then I'm on page with title "DigiWill - Your Profile"

    Examples:
      | firstName | surName | birthday | streetAddress  | zipCode | city      | country |
      | Tester    | McTest  | 04201990 | Test-Street 41 | 12345   | Test-City | Germany |

  Scenario Outline: 02 - Change password successful
    When I click on element with id "userButtonHeader"
    And I click on element with id "editProfileButton"
    And I enter "<oldPassword>" into field with id "oldPasswordInput"
    And I enter "<newPassword>" into field with id "newPasswordInput"
    And I enter "<repNewPassword>" into field with id "confirmNewPasswordInput"
    And I click on element with id "changePasswordButton"
    Then I'm on page with title "DigiWill - Your Profile"
    And Clicking in header "Logout" on Mainpage
    And I click on element with id "loginButtonHeader"
    And Enter Email "example@mail.com", password "<newPassword>" and login
    Then Login "succeeds"

    Examples:
      | oldPassword | newPassword    | repNewPassword |
      | password    | validPa33word! | validPa33word! |

  Scenario Outline: 03 - Profile edit unsuccessful
    When I click on element with id "userButtonHeader"
    And I click on element with id "editProfileButton"
    And I clear field with id "firstNameInput"
    And I enter "<firstName>" into field with id "firstNameInput"
    And I clear field with id "surNameInput"
    And I enter "<surName>" into field with id "surNameInput"
    And I clear field with id "birthday"
    And I enter "<birthday>" into field with id "birthday"
    And I enter "<streetAddress>" into field with id "streetAddressInput"
    And I enter "<zipCode>" into field with id "zipCodeInput"
    And I enter "<city>" into field with id "cityInput"
    And I enter "<country>" into field with id "countryInput"
    And I click on element with id "saveProfileButton"
    Then I'm on page with title "DigiWill - Edit Your Profile"

    Examples:
      | firstName | surName | birthday | streetAddress  | zipCode | city      | country |
      |           | McTest  | 04201990 | Test-Street 41 | 12345   | Test-City | Germany |
      | Martin    |         | 01011990 | Test-Street 42 | 12345   | Test-City | Germany |
      | Martin    | McTest  | abc      | Test-Street 42 | 12345   | Test-City | Germany |

  Scenario Outline: 04 - Change password unsuccessful
    When I click on element with id "userButtonHeader"
    And I click on element with id "editProfileButton"
    And I enter "<oldPassword>" into field with id "oldPasswordInput"
    And I enter "<newPassword>" into field with id "newPasswordInput"
    And I enter "<repNewPassword>" into field with id "confirmNewPasswordInput"
    And I click on element with id "changePasswordButton"
    Then I'm on page with title "DigiWill - Edit Your Profile"

    Examples:
      | oldPassword | newPassword    | repNewPassword |
      | password1   | validPa33word! | validPa33word! |
      | password    | Test1234$      | Test123$       |