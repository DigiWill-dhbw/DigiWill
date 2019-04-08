@uitest
Feature: Login
  As a mortal
  I want to login with my registered account

  Background:
    Given "/login" is open

  Scenario Outline: 01 - Login successful
    Given A user with email "<email>" and password "<password>" exists
    When Enter Email "<email>", password "<password>" and login
    Then Login "succeeds"

    Examples:
      | email            | password |
      | example@mail.com | password |

  Scenario Outline: 02 - Login failed
    Given A user with email "<email>" doesn't exist
    When Enter Email "<email>", password "<password>" and login
    Then Login "fails"

    Examples:
      | email                | password       |
      | admin@de.digiwill.de | adminpassword1 |

  #Scenario Outline: Store login session
  #  When Enter Email "<email>", password "<password>", check checkbox and click "Login"
  #  Then Login was successful
  #  When Close current browser tab
  #  And Open web application
  #  Then Login was successful

  #Examples:
  #| email | password |
  #| admin@de.digiwill.de | adminpassword |
