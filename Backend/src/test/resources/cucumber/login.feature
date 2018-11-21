Feature: Login
  As a mortal
  I want to login with my registered account

  Background:
    Given The application is opened

  Scenario Outline: Login successful
    When Enter Email "<email>", password "<password>" and click "Login"
    Then Login was successful

  Examples:
  | email | password |
  | admin@de.digiwill.de | adminpassword |

  Scenario Outline: Login failed
    When Enter Email "<email>", password "<password>" and click "Login"
    Then Login has failed

  Examples:
  | email | password |
  | admin@de.digiwill.de | adminpassword1 |

  Scenario Outline: Store login session
    When Enter Email "<email>", password "<password>", check checkbox and click "Login"
    Then Login was successful
    When Close current browser tab
    And Open web application
    Then Login was successful

  Examples:
  | email | password |
  | admin@de.digiwill.de | adminpassword |
