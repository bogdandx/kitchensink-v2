Feature:
  Create member

  Scenario: New member
    When creating the following member
      | Name         | Phone Number | Email                  |
      | Donna Davids | 2458854894   | donna.davids@gmail.com |
    Then status code should be 200
    And the list of persisted members should be equal to
      | Name         | Phone Number | Email                     |
      | John Smith   | 2125551212   | john.smith@mailinator.com |
      | Donna Davids | 2458854894   | donna.davids@gmail.com    |