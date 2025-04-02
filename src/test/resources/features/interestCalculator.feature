Feature: Interest Calculator
  As a user,
  I want to calculate interest based on the duration, interest rate, and principal amount
  So that I can know how much the interest is and the total amount including the interest

  Scenario:Successful Interest Calculation
    Given the user is on the login page
    When the user successfully logs in with a valid username and password
    And the user selects all required fields
    Then user should see the expectedInterestAmount and totalAmount

  Scenario Outline: Missing Mandatory Fields
    Given the user is on the login page
    When the user successfully logs in with a valid username and password
    When the user leaves a "<field>" missing
    Then an alert message should be displayed with the error
    Examples:
      | field         |
      | interest rate |
#      | consent       |

# The Duration and Principal Amount and selected by default so are not being tested for.

# Bug found : The consent value is being cached and not updated. If a user initially submits the form without
# the consent it shows an error. When they go back and tick the consent, the error is still there.

