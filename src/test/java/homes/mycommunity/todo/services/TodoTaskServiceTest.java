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
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TodoTaskServiceTest {

    @InjectMocks
    private TodoTaskServiceImpl todoTaskService;

    @Mock
    private TodoTaskRepository todoTaskRepository;

    @Test
    public void testGetAllTodoTasks() {
        // Datos de prueba
        TodoTask todoTask1 = TodoTaskUtil.createTodotask();
        TodoTask todoTask2 = TodoTaskUtil.createTodotask();
        List<TodoTask> todoTasks = Arrays.asList(todoTask1, todoTask2);

        // Configurar el comportamiento del repositorio mock
        Mockito.when(todoTaskRepository.findAll()).thenReturn(todoTasks);

        // Llamar al método del servicio
        List<TodoTask> result = todoTaskService.getAllTodoTasks();

        // Verificar el resultado
        Assert.assertEquals(todoTasks, result);
    }

    @Test
    public void testCreateTodoTask() {
        // Datos de prueba
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        // Configurar el comportamiento del repositorio mock
        Mockito.when(todoTaskRepository.save(null, todoTask)).thenReturn(todoTask);

        // Llamar al método del servicio
        TodoTask createdTask = todoTaskService.createTodoTask(todoTask);

        // Verificar que se haya guardado correctamente
        Mockito.verify(todoTaskRepository).save(null, todoTask);

        // Verificar el resultado
        Assert.assertEquals(todoTask, createdTask);
    }

    @Test
    public void testUpdateTodoTask() {
        // Datos de prueba
        String taskId = "1";
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        // Configurar el comportamiento del repositorio mock
        Mockito.when(todoTaskRepository.save(taskId, todoTask)).thenReturn(todoTask);

        // Llamar al método del servicio
        TodoTask updatedTask = todoTaskService.updateTodoTask(taskId, todoTask);

        // Verificar que se haya actualizado correctamente
        Mockito.verify(todoTaskRepository).save(taskId, todoTask);

        // Verificar el resultado
        Assert.assertEquals(todoTask, updatedTask);
    }

    @Test
    public void testDeleteTodoTask() {
        // Datos de prueba
        String taskId = "1";
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        // Configurar el comportamiento del repositorio mock
        Mockito.doNothing().when(todoTaskRepository).deleteById(taskId);

        // Llamar al método del servicio
        todoTaskService.deleteTodoTaskById(taskId);

        // Verificar que se haya eliminado correctamente
        Mockito.verify(todoTaskRepository).deleteById(taskId);
    }
}
