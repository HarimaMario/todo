package homes.mycommunity.todo.services;
import homes.mycommunity.todo.domains.models.TodoTask;
import homes.mycommunity.todo.domains.repositories.TodoTaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoTaskServiceImpl implements TodoTaskService {
    private final TodoTaskRepository todoTaskRepository;

    @Autowired
    public TodoTaskServiceImpl(TodoTaskRepository todoTaskRepository) {
        this.todoTaskRepository = todoTaskRepository;
    }

    @Override
    public TodoTask createTodoTask(TodoTask todoTask) {
        return this.todoTaskRepository.save(null, todoTask);
    }

    @Override
    public Optional<TodoTask> getTodoTaskById(String id) {
        return this.todoTaskRepository.findById(id);
    }

    @Override
    public List<TodoTask> getAllTodoTasks() {
        return this.todoTaskRepository.findAll();
    }

    @Override
    public TodoTask updateTodoTask(String id, TodoTask todoTask) {
        return this.todoTaskRepository.save(id, todoTask);
    }

    @Override
    public void deleteTodoTaskById(String id) {
        this.todoTaskRepository.deleteById(id);
    }
}