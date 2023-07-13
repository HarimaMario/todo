package homes.mycommunity.todo.services;

import homes.mycommunity.todo.domains.models.TodoTask;
import homes.mycommunity.todo.domains.repositories.TodoTaskRepository;
import homes.mycommunity.todo.util.TodoTaskUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TodoTaskServiceTest {

    @InjectMocks
    private TodoTaskServiceImpl todoTaskService;

    @Mock
    private TodoTaskRepository todoTaskRepository;

    @Test
    public void testGetAllTodoTasks() {
        // given data
        TodoTask todoTask1 = TodoTaskUtil.createTodotask();
        TodoTask todoTask2 = TodoTaskUtil.createTodotask();
        List<TodoTask> todoTasks = Arrays.asList(todoTask1, todoTask2);

        // when logic
        Mockito.when(todoTaskRepository.findAll()).thenReturn(todoTasks);

        // Execute
        List<TodoTask> result = todoTaskService.getAllTodoTasks();

        // Then review
        Assert.assertEquals(todoTasks, result);
    }

    @Test
    public void testCreateTodoTask() {
        // given data
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        // when logic
        Mockito.when(todoTaskRepository.save(null, todoTask)).thenReturn(todoTask);

        // Execute
        TodoTask createdTask = todoTaskService.createTodoTask(todoTask);

        // Then review
        Mockito.verify(todoTaskRepository).save(null, todoTask);
        Assert.assertEquals(todoTask, createdTask);
    }

    @Test
    public void testUpdateTodoTask() {
        // given data
        String taskId = "1";
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        // when logic
        Mockito.when(todoTaskRepository.save(taskId, todoTask)).thenReturn(todoTask);

        // Execute
        TodoTask updatedTask = todoTaskService.updateTodoTask(taskId, todoTask);

        // Then review
        Mockito.verify(todoTaskRepository).save(taskId, todoTask);
        Assert.assertEquals(todoTask, updatedTask);
    }

    @Test
    public void testDeleteTodoTask() {
        // given data
        String taskId = "1";
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        // when logic
        Mockito.doNothing().when(todoTaskRepository).deleteById(taskId);

        // Execute
        todoTaskService.deleteTodoTaskById(taskId);

        // Then review
        Mockito.verify(todoTaskRepository).deleteById(taskId);
    }
}
