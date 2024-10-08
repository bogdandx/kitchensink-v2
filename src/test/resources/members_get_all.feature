Feature:
  Get all members

  Scenario: Default member
    When retrieving all members
    Then status code should be 200
    And the following members should be returned:
      | Id | Name       | Phone Number | Email                     |
      | 0  | John Smith | 2125551212   | john.smith@mailinator.com |