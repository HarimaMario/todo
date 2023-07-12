package homes.mycommunity.todo.domains.repositories;

import homes.mycommunity.todo.domains.models.TodoTask;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MariaDBTaskRepository implements TodoTaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TodoTask> rowMapper = (rs, rowNum) -> {
        TodoTask task = new TodoTask();
        task.setId(rs.getString("id"));
        task.setTaskName(rs.getString("task_name"));
        task.setCreatedBy(rs.getString("created_by"));
        task.setCreateAt(rs.getObject("create_at", LocalDateTime.class));
        task.setUpdateAt(rs.getObject("update_at", LocalDateTime.class));
        task.setUserAssign(rs.getString("user_assign"));
        task.setStatus(rs.getString("status"));
        task.setTaskDescription(rs.getString("task_description"));
        return task;
    };

    @Autowired
    public MariaDBTaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TodoTask save(String id, TodoTask task) {
        this.setTodoTaskTimes(id, task);
        return id == null ? this.insert(task) : this.update(task);
    }


    private TodoTask insert(TodoTask task) {
        String query = "INSERT INTO todo_task (task_name, created_by, create_at, update_at, user_assign, status, " +
                "task_description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(query, new Object[]{task.getTaskName(), task.getCreatedBy(), LocalDateTime.now(),
                LocalDateTime.now(), task.getUserAssign(), task.getStatus(), task.getTaskDescription()});
        task.setId(String.valueOf(this.getLastId()));
        return task;
    }

    public String getLastId() {
        String sql = "SELECT LAST_INSERT_ID()";
        String ultimoId = jdbcTemplate.queryForObject(sql, String.class);
        if (ultimoId == null) {
            return "";
        }
        return ultimoId;
    }

    private TodoTask update(TodoTask task) {
        String query = "UPDATE todo_task SET task_name = ?, created_by = ?, create_at = ?, update_at = ?, " +
                "user_assign = ?, status = ?, task_description = ? WHERE id = ?";
        this.jdbcTemplate.update(query, new Object[]{task.getTaskName(), task.getCreatedBy(), task.getCreateAt(),
                LocalDateTime.now(), task.getUserAssign(), task.getStatus(), task.getTaskDescription(), task.getId()});
        return task;
    }

    @Override
    public Optional<TodoTask> findById(String id) {
        String query = "SELECT * FROM todo_task WHERE id = ?";
        TodoTask task = this.jdbcTemplate.queryForObject(query, this.rowMapper, new Object[]{id});
        return Optional.ofNullable(task);
    }

    @Override
    public List<TodoTask> findAll() {
        String query = "SELECT * FROM todo_task";
        return this.jdbcTemplate.query(query, this.rowMapper);
    }

    @Override
    public void deleteById(String id) {
        String query = "DELETE FROM todo_task WHERE id = ?";
        this.jdbcTemplate.update(query, new Object[]{id});
    }
}