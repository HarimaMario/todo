package homes.mycommunity.todo.services;

import homes.mycommunity.todo.domains.models.TodoTask;
import java.util.List;
import java.util.Optional;

public interface TodoTaskService {
    TodoTask createTodoTask(TodoTask todoTask);

    Optional<TodoTask> getTodoTaskById(String id);

    List<TodoTask> getAllTodoTasks();

    TodoTask updateTodoTask(String id, TodoTask todoTask);

    void deleteTodoTaskById(String id);
}
