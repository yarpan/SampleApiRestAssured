Feature:  API Tests

  Scenario: Create a new post
    Given the API is accessible
    When a new post is created with title "New title" and content "New content"
    Then the response code should be 201
    And the response should contain the expected title and content