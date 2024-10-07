Feature:
  Get member by id

  Scenario: Default member
    When retrieving member with id 0
    Then status code should be 200
    And the following member should be returned:
      | Id | Name       | Phone Number | Email                     |
      | 0  | John Smith | 2125551212   | john.smith@mailinator.com |

  Scenario: Non existing member
    When retrieving member with id 500
    Then status code should be 404