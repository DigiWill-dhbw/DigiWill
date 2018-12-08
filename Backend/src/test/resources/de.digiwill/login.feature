Feature: Login
  As a mortal
  I want to login with my registered account

  Background:
    Given "/login" is open

  Scenario Outline: 01 - Login successful
    Given A user with email "<email>" and password "<password>" "exists"
    And "1" Users are created
    When Enter Email "<email>", password "<password>" and login
    Then Login for "<email>", "succeeds"

  Examples:
  | email | password |
  | nobody0@digiwill.de  | nobody0@digiwill.de |

  Scenario Outline: 02 - Login failed
    Given A user with email "<email>" and password "<password>" "doesn't exist"
    When Enter Email "<email>", password "<password>" and login
    Then Login for "<email>", "fails"

  Examples:
  | email | password |
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
