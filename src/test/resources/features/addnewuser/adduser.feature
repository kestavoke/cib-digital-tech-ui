@Smoke
  Feature: Adding record to the user list table
    As a user, I want to navigate and add a new user to the list table

  Scenario Outline: Add new user to list table
    Given I navigate to the user list table
    Then I see the user list table
    When I click add user option
    Then I see add user form
    And I enter the new user details <firstName>, <lastName>, <userName>, <password>, <customer>, <role>, <email>, <cell>
    When I click save option
    Then I see the added users in the user list table <userName>
    Examples:
    |firstName|lastName|userName   |password    |customer   |role      |email           |cell     |
    |Ovoke01  |Ovoke01 |OvokeTest01|Ovoke_test01|Company AAA|Customer  |ovoke01@test.com|777888999|
    |Ovoke02  |Ovoke02 |OvokeTest02|Ovoke_test02|Company BBB|Sales Team|ovoke02@test.com|456789039|