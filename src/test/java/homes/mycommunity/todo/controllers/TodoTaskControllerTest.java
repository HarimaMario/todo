package homes.mycommunity.todo.controllers;

import homes.mycommunity.todo.domains.models.TodoTask;
import homes.mycommunity.todo.services.TodoTaskService;
import homes.mycommunity.todo.util.TodoTaskUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TodoTaskControllerTest {

    @InjectMocks
    private TodoTaskController todoTaskController;

    @Mock
    private TodoTaskService todoTaskService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoTaskController).build();
    }

    @Test
    public void testGetTodoTasks() throws Exception {
        List<TodoTask> todoTasks = Arrays.asList(TodoTaskUtil.createTodotask(), TodoTaskUtil.createTodotask());

        Mockito.when(todoTaskService.getAllTodoTasks()).thenReturn(todoTasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/todolist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskName", Matchers.is(todoTasks.get(0).getTaskName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskName", Matchers.is(todoTasks.get(1).getTaskName())));
    }

    @Test
    public void testGetTodoTaskById() throws Exception {
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        Mockito.when(todoTaskService.getTodoTaskById(Mockito.anyString())).thenReturn(java.util.Optional.of(todoTask));

        mockMvc.perform(MockMvcRequestBuilders.get("/todolist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskName", Matchers.is(todoTask.getTaskName())));
    }

    @Test
    public void testCreateTodoTask() throws Exception {
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        Mockito.when(todoTaskService.createTodoTask(Mockito.any(TodoTask.class))).thenReturn(todoTask);

        mockMvc.perform(MockMvcRequestBuilders.post("/todolist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TodoTaskUtil.asJsonString(todoTask)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskName", Matchers.is(todoTask.getTaskName())));
    }

    @Test
    public void testUpdateTodoTask() throws Exception {
        TodoTask todoTask = TodoTaskUtil.createTodotask();

        Mockito.when(todoTaskService.updateTodoTask(Mockito.anyString(), Mockito.any(TodoTask.class))).thenReturn(todoTask);

        mockMvc.perform(MockMvcRequestBuilders.put("/todolist/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TodoTaskUtil.asJsonString(todoTask)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskName", Matchers.is(todoTask.getTaskName())));
    }

    @Test
    public void testDeleteTodoTaskById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/todolist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}