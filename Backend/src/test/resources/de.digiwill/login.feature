Feature: Login
  As a mortal
  I want to login with my registered account

  Background:
    Given "http://localhost:8080/login" is open

  Scenario Outline: Login successful
    Given A user with email "<email>" and password "<password>" "exists"
    When Enter Email "<email>", password "<password>" and login
    Then Login for "<email>", "succeeds"
    And Close Session

  Examples:
  | email | password |
  | user  | password |

  Scenario Outline: Login failed
    Given A user with email "<email>" and password "<password>" "doesn't exist"
    When Enter Email "<email>", password "<password>" and login
    Then Login for "<email>", "fails"
    And Close Session

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
