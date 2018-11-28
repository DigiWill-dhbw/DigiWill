Feature: Login
  As a mortal
  I want to login with my registered account

  Background:
    Given The "main website" is open

  Scenario Outline: Login successful
    Given A user with email "<email>" and password "<password>" "exists"
    When Enter Email "<email>", password "<password>" and click "Login"
    Then Login "succeeds"

  Examples:
  | email | password |
  | admin@de.digiwill.de | adminpassword |

  Scenario Outline: Login failed
    Given A user with email "<email>" and password "<password>" "doesn't exist"
    When Enter Email "<email>", password "<password>" and click "Login"
    Then Login "fails"

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
