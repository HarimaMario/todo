package homes.mycommunity.todo.steps;

import homes.mycommunity.todo.CucumberSpringConfiguration;
import homes.mycommunity.todo.domains.models.TodoTask;
import homes.mycommunity.todo.domains.repositories.TodoTaskRepository;
import homes.mycommunity.todo.services.TodoTaskServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@CucumberContextConfiguration
public class TodoTaskSteps extends CucumberSpringConfiguration {

    @Autowired
    private TodoTaskRepository todoTaskRepository;
    private TodoTask createdTask;
    private TodoTask updatedTask;
    private String taskId;

    private List<TodoTask> allTasks;


    @Given("I have a TodoTask service")
    public void iHaveATodoTaskService() {
        this.todoTaskService = new TodoTaskServiceImpl(todoTaskRepository);
    }

    @When("I create a new TodoTask with name {string}")
    public void iCreateANewTodoTaskWithName(String taskName) {
        TodoTask task = new TodoTask();
        task.setTaskName(taskName);

        createdTask = this.todoTaskService.createTodoTask(task);
        taskId = createdTask.getId();
    }

    @Then("the TodoTask with name {string} should be created")
    public void theTodoTaskWithNameShouldBeCreated(String taskName) {
        Assert.assertNotNull(createdTask);
        Assert.assertEquals(taskName, createdTask.getTaskName());
    }

    @When("I update the TodoTask with name {string} to name {string}")
    public void iUpdateTheTodoTaskWithNameToName(String oldTaskName, String newTaskName) {
        TodoTask task = new TodoTask();
        task.setId(taskId);
        task.setTaskName(newTaskName);

        updatedTask = this.todoTaskService.updateTodoTask(taskId, task);
    }

    @Then("the TodoTask should be updated with name {string}")
    public void theTodoTaskShouldBeUpdatedWithName(String newTaskName) {
        Assert.assertNotNull(updatedTask);
        Assert.assertEquals(newTaskName, updatedTask.getTaskName());
    }

    @When("I delete the TodoTask")
    public void iDeleteTheTodoTask() {
        this.todoTaskService.deleteTodoTaskById(taskId);
    }

    @Then("the TodoTask should be deleted")
    public void theTodoTaskShouldBeDeleted() {
        Optional<TodoTask> deletedTask = this.todoTaskService.getTodoTaskById(taskId);
        Assert.assertEquals(Optional.empty(), deletedTask);
    }

    @When("I get the TodoTask with ID")
    public void iGetTheTodoTaskWithId() {
        Optional<TodoTask> retrievedTask = this.todoTaskService.getTodoTaskById(createdTask.getId());

        Assert.assertNotNull(retrievedTask);
        Assert.assertTrue(retrievedTask.isPresent());
        Assert.assertEquals(createdTask.getId(), retrievedTask.get().getId());
    }

    @When("I get all TodoTasks")
    public void iGetAllTodoTasks() {
        allTasks = this.todoTaskService.getAllTodoTasks();

        Assert.assertNotNull(allTasks);
        Assert.assertFalse(allTasks.isEmpty());
    }

    @Then("I should receive the TodoTask with ID")
    public void iShouldReceiveTheTodoTaskWithId() {
        // Verification already done in the "When" step
    }

    @Then("I should receive a list of TodoTasks")
    public void iShouldReceiveAListOfTodoTasks() {
        Assert.assertNotNull(allTasks);
        Assert.assertFalse(allTasks.isEmpty());
    }
}

