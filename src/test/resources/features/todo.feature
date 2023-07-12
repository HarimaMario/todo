Feature: TodoTask CRUD Operations
  Scenario: Create a new TodoTask
    Given I have a TodoTask service
    When I create a new TodoTask with name "Buy groceries"
    Then the TodoTask with name "Buy groceries" should be created

  Scenario: Update an existing TodoTask
    Given I have a TodoTask service
    And I create a new TodoTask with name "Buy groceries"
    When I update the TodoTask with name "Buy groceries" to name "Buy fruits"
    Then the TodoTask should be updated with name "Buy fruits"

  Scenario: Delete an existing TodoTask
    Given I have a TodoTask service
    And I create a new TodoTask with name "Buy groceries"
    When I delete the TodoTask
    Then the TodoTask should be deleted

  Scenario: Get a TodoTask by ID
    Given I have a TodoTask service
    And I create a new TodoTask with name "Buy groceries"
    When I get the TodoTask with ID
    Then I should receive the TodoTask with ID

  Scenario: Get all TodoTasks
    Given I have a TodoTask service
    And I create a new TodoTask with name "Buy groceries"
    And I create a new TodoTask with name "Walk the dog"
    When I get all TodoTasks
    Then I should receive a list of TodoTasks

