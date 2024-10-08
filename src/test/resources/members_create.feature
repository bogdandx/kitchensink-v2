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

  Scenario: Email address already in use
    Given the following members exist
      | Name         | Phone Number | Email                  |
      | Donna Davids | 2458854894   | donna.davids@gmail.com |
    When creating the following member
      | Name               | Phone Number | Email                  |
      | Donna Davids-Myers | 3424234232   | donna.davids@gmail.com |
    Then status code should be 409
    And response message should contain the following errors
      | Field | Error       |
      | email | Email taken |