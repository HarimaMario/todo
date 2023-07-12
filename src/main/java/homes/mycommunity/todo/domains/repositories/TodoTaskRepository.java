package homes.mycommunity.todo.domains.repositories;

import homes.mycommunity.todo.domains.models.TodoTask;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoTaskRepository {
    TodoTask save(String id, TodoTask task);

    Optional<TodoTask> findById(String id);

    List<TodoTask> findAll();

    void deleteById(String id);

    default void setTodoTaskTimes(String id, TodoTask todoTask) {
        if (id == null) {
            todoTask.setCreateAt(LocalDateTime.now());
        }
        todoTask.setUpdateAt(LocalDateTime.now());
    }
}
