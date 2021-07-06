Feature: Login

  Scenario: User perform login and verify homepage
    When user enter username
    And user enter password
    And user click on log in button
    Then verify user is successfully logged in