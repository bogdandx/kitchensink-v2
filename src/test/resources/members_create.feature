Feature:
  Create member

  Scenario: New member
    When creating the following member
      | Name       | Phone Number | Email                     |
      | Donna Davids | 2458854894   | donna.davids@gmail.com |
    Then status code should be 200
#    And the member should be returned when retrieved by id with the following attributes
#      | Id | Name       | Phone Number | Email                     |
#      | 28  | Donna Davids | 2458854894   | donna.davids@gmail.com |