@uitest
Feature: Send Life Sign
  As a mortal
  I want to send a life sign

  Background:
    Given "/login" is open
    And The user "test_user_send_life_sign@digiwill.de" with the password "Blabla42!" is logged in

  Scenario Outline: Send valid life sign
    When Clicking in header "Send life sign"
    Then Life sign was sent

    Examples: