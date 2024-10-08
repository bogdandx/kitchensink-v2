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

  Scenario: Phone number of an invalid size
    When creating the following member
      | Name         | Phone Number | Email                  |
      | Donna Davids | 34324343     | donna.davids@gmail.com |
    Then status code should be 400
    And response message should contain the following errors
      | Field       | Error                          |
      | phoneNumber | size must be between 10 and 12 |

  Scenario: Phone number contains non-numeric characters
    When creating the following member
      | Name         | Phone Number | Email                  |
      | Donna Davids | 3442x2324343 | donna.davids@gmail.com |
    Then status code should be 400
    And response message should contain the following errors
      | Field       | Error                                                         |
      | phoneNumber | numeric value out of bounds (<12 digits>.<0 digits> expected) |

  Scenario: Invalid email format
    When creating the following member
      | Name         | Phone Number | Email                     |
      | Donna Davids | 3424234232   | donna.davids-at-gmail.com |
    Then status code should be 400
    And response message should contain the following errors
      | Field | Error                               |
      | email | must be a well-formed email address |

  Scenario: Blank fields
    When creating the following member
      | Name | Phone Number | Email |
      |      | 12345678908  |       |
    Then status code should be 400
    And response message should contain the following errors
      | Field | Error                         |
      | email | must not be empty             |
      | name  | size must be between 1 and 25 |