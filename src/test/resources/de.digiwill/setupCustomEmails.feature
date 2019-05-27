@uitest
Feature: Custom Emails CRUD
  As a mortal
  I want certain emails to be send should I die

  Background:
    Given "/?login" is open
    Given A user with email "example@mail.com" and password "password" exists
    When Enter Email "example@mail.com", password "password" and login
    Then Login "succeeds"
    And The user is on the actions overview page
    And There are no Email Actions

  Scenario Outline: 01 - Create valid Email Action
    When Create new email action with recipient "<recipient>", subject "<subject>", content "<content>" and click "Save"
    Then The service should accept the new action
    And A new item with recipient "<recipient>", subject "<subject>", content "<content>" should exist

    Examples:
      | recipient                                  | subject                   | content                                                                      |
      | thomas.meyer@gmail.com                     | Hi Thomas, I'm dead       | As you might have heard by know I'm dead. This email was sent automatically. |
      | thomas.meyer@gmail.com                     | Hi Thomas, I'm dead       | As you might have heard by know I'm dead. This email was sent automatically. |
      | mum.test@web.de                            | Hi mum, It's been a while | As you might have heard by know I'm dead.                                    |
      | thomas.meyer@gmail.com thomas.meier@web.de | Hi Thomas                 | As you might have heard by know I'm dead.                                    |
      | thomas.meyer@gmail.com                     |                           | As you might have heard by know I'm dead.                                    |
      | thomas.meyer@gmail.com                     | Hi Thomas                 | As you might have heard by know I'm dead.                                    |

  Scenario Outline: 02 - Cancel create valid Email Action
    When Create new email action with recipient "<recipient>", subject "<subject>", content "<content>" and click "Cancel"
    Then The service should not accept the new action
    And No item with recipient "<recipient>", subject "<subject>", content "<content>" should exist

    Examples:
      | recipient              | subject                   | content                                                                      |
      | thomas.meyer@gmail.com | Hi Thomas, I'm dead       | As you might have heard by know I'm dead. This email was sent automatically. |
      | mum.test@web.de        | Hi mum, It's been a while | As you might have heard by know I'm dead.                                    |


  Scenario Outline: 03 - Create invalid Email Action
    When Create new email action with recipient "<recipient>", subject "<subject>", content "<content>" and click "Save"
    Then The service should not accept the new action

    Examples:
      | recipient               | subject               | content                                   |
      | thomas.meyer@@gmail.com | Hi, It's been a while | As you might have heard by know I'm dead. |
      | thomas.meyer@gmail      | Hi, It's been a while | As you might have heard by know I'm dead. |
      | @gmail.com              | Hi, It's been a while | As you might have heard by know I'm dead. |
      | thomas.meyergmail.com   | Hi, It's been a while | As you might have heard by know I'm dead. |
      |                         | Hi, It's been a while | As you might have heard by know I'm dead. |

  Scenario Outline: 04 - Edit Email Action
    When Create new email action with recipient "<recipient>", subject "<subject>", content "<content>" and click "Save"
    And Clicking "Edit"
    And Editing email with recipient "<recNew>", subject "<subNew>", content "<contNew>" and click "Save"
    Then The service should accept the new action
    And A new item with recipient "<recNew>", subject "<subNew>", content "<contNew>" should exist

    Examples:
      | recipient              | subject             | content                                   | recNew                                     | subNew                    | contNew                                                                      |
      | thomas.meyer@gmail.com | Hi Thomas, I'm dead | As you might have heard by know I'm dead. | thomas.meyer@gmail.com                     | Hi Thomas, I'm dead       | As you might have heard by know I'm dead. This email was sent automatically. |
      | thomas.meyer@gmail.com | Hi Thomas           | As you might have heard by know I'm dead. | thomas.meyer@gmail.com thomas.meier@web.de | Hi Thomas                 | As you might have heard by know I'm dead.                                    |
      | mum.test@web.de        | Hi mum              | As you might have heard by know I'm dead. | mum.test@web.de                            | Hi mum, It's been a while | As you might have heard by know I'm dead.                                    |

  Scenario Outline: 05 - Cancel Edit Email Action
    When Create new email action with recipient "<recipient>", subject "<subject>", content "<content>" and click "Save"
    And Clicking "Edit"
    And Editing email with recipient "<recNew>", subject "<subNew>", content "<contNew>" and click "Cancel"
    Then The service should accept the new action
    And  A new item with recipient "<recipient>", subject "<subject>", content "<content>" should exist

    Examples:
      | recipient              | subject             | content                                   | recNew                                     | subNew                    | contNew                                                                      |
      | thomas.meyer@gmail.com | Hi Thomas, I'm dead | As you might have heard by know I'm dead. | thomas.meyer@gmail.com                     | Hi Thomas, I'm dead       | As you might have heard by know I'm dead. This email was sent automatically. |
      | thomas.meyer@gmail.com | Hi Thomas           | As you might have heard by know I'm dead. | thomas.meyer@gmail.com thomas.meier@web.de | Hi Thomas                 | As you might have heard by know I'm dead.                                    |
      | mum.test@web.de        | Hi mum              | As you might have heard by know I'm dead. | mum.test@web.de                            | Hi mum, It's been a while | As you might have heard by know I'm dead.                                    |


  Scenario Outline: 06 - Delete Email Action
    When Create new email action with recipient "<recipient>", subject "<subject>", content "<content>" and click "Save"
    Then A new item with recipient "<recipient>", subject "<subject>", content "<content>" should exist
    When Clicking "Delete"
    And Click "Confirm" on the modal
    Then No item with recipient "<recipient>", subject "<subject>", content "<content>" should exist

    Examples:
      | recipient               | subject             | content                                   |
      | sabine.miller@gmail.com | Hi Thomas, I'm dead | As you might have heard by know I'm dead. |
      | alex.smith@gmail.com    | Hi Thomas           |                                           |
      | mark.mcmuffin@web.de    | Hi mum              | As you might have heard by know I'm dead. |

  Scenario Outline: 07 - Cancel Delete Email Action
    When Create new email action with recipient "<recipient>", subject "<subject>", content "<content>" and click "Save"
    Then A new item with recipient "<recipient>", subject "<subject>", content "<content>" should exist
    When Clicking "Delete"
    And Click "Cancel" on the modal
    Then The service should accept the new action
    And  A new item with recipient "<recipient>", subject "<subject>", content "<content>" should exist

    Examples:
      | recipient               | subject             | content                                   |
      | sabine.miller@gmail.com | Hi Thomas, I'm dead | As you might have heard by know I'm dead. |
      | alex.smith@gmail.com    | Hi Thomas           |                                           |
      | mark.mcmuffin@web.de    | Hi mum              | As you might have heard by know I'm dead. |
