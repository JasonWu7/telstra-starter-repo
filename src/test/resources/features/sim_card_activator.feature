Feature: Is SIM card activation successful?

  Scenario: Successful SIM card activation
    Given a SIM card with ICCID "1255789453849037777"
    When I send an activation request
    And I query the activation record with ID 1
    Then the SIM card should be marked as active

  Scenario: Failed SIM card activation
    Given a SIM card with ICCID "8944500102198304826"
    When I send an activation request
    And I query the activation record with ID 2
    Then the SIM card should not be marked as active
