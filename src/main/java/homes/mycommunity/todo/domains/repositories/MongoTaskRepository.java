package homes.mycommunity.todo.domains.repositories;

import homes.mycommunity.todo.domains.models.TodoTask;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoTaskRepository implements TodoTaskRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoTaskRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public TodoTask save(String id, TodoTask task) {
        this.setTodoTaskTimes(id, task);
        return (TodoTask)this.mongoTemplate.save(task);
    }

    @Override
    public Optional<TodoTask> findById(String id) {
        return Optional.ofNullable((TodoTask)this.mongoTemplate.findById(id, TodoTask.class));
    }

    @Override
    public List<TodoTask> findAll() {
        return this.mongoTemplate.findAll(TodoTask.class);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        this.mongoTemplate.remove(query, TodoTask.class);
    }
}
