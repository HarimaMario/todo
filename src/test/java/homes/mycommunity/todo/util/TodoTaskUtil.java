package homes.mycommunity.todo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import homes.mycommunity.todo.domains.models.TodoTask;
import org.apache.commons.lang3.RandomStringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

public class TodoTaskUtil {
    public static TodoTask createTodotask(){
        TodoTask todoTask = new TodoTask();
        todoTask.setId(String.valueOf(new Random().nextInt(1000)));
        todoTask.setTaskName(RandomStringUtils.randomAlphanumeric(10));
        todoTask.setCreatedBy("User " + String.valueOf(new Random().nextInt(1000)));
        todoTask.setCreateAt(null);
        todoTask.setUpdateAt(null);
        todoTask.setUserAssign("User " + String.valueOf(new Random().nextInt(1000)));
        todoTask.setStatus("Pending");
        todoTask.setTaskDescription("Task "+String.valueOf(new Random().nextInt(1000)) +" Description");
        return todoTask;
    }

    public static String asJsonString(TodoTask task) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(task);
    }

}
