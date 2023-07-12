package homes.mycommunity.todo;

import homes.mycommunity.todo.services.TodoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration {
    @Autowired
    protected TodoTaskService todoTaskService;

}
