package homes.mycommunity.todo.controllers;

import homes.mycommunity.todo.domains.models.TodoTask;
import homes.mycommunity.todo.exceptions.NotFoundException;
import homes.mycommunity.todo.services.TodoTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/todolist"})
@Api(
        tags = {"TodoTask"},
        description = "Endpoints for TodoTask management"
)
public class TodoTaskController {
    private final TodoTaskService todoTaskService;

    @Autowired
    public TodoTaskController(TodoTaskService todoTaskService) {
        this.todoTaskService = todoTaskService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Create a new TodoTask")
    public TodoTask createTodoTask(@RequestBody TodoTask todoTask) {
        return this.todoTaskService.createTodoTask(todoTask);
    }

    @GetMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Get a TodoTask by ID")
    public TodoTask getTodoTaskById(@PathVariable String id) {
        return (TodoTask)this.todoTaskService.getTodoTaskById(id).orElseThrow(() -> {
            return new NotFoundException("TodoTask not found with ID: " + id);
        });
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Get all TodoTasks")
    public List<TodoTask> getAllTodoTasks() {
        return this.todoTaskService.getAllTodoTasks();
    }

    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Update a TodoTask")
    public TodoTask updateTodoTask(@PathVariable String id, @RequestBody TodoTask todoTask) {
        todoTask.setId(id.toString());
        return this.todoTaskService.updateTodoTask(id, todoTask);
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Delete a TodoTask by ID")
    public void deleteTodoTaskById(@PathVariable String id) {
        this.todoTaskService.deleteTodoTaskById(id);
    }
}
