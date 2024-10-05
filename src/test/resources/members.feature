Feature:
  Members management

  Scenario: Retrieves default member
    When retrieving member with id 0
    Then status code should be 200
    And the following member should be returned:
      | Id | Name       | Phone Number | Email                     |
      | 0  | John Smith | 2125551212   | john.smith@mailinator.com |

  Scenario: Retrieves default member from legacy
    When retrieving member with id 0 using legacy
    Then status code should be 200
    Then the following member should be returned:
      | Id | Name       | Phone Number | Email                     |
      | 0  | John Smith | 2125551212   | john.smith@mailinator.com |