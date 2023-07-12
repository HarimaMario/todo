package homes.mycommunity.todo.configs;

import homes.mycommunity.todo.domains.repositories.MariaDBTaskRepository;
import homes.mycommunity.todo.domains.repositories.MongoTaskRepository;
import homes.mycommunity.todo.domains.repositories.TodoTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableMongoAuditing
public class RepositoryConfig {

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public RepositoryConfig(MongoTemplate mongoTemplate, JdbcTemplate jdbcTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    @Profile({"non-sql-db"})
    public TodoTaskRepository mongoTaskRepository() {
        return new MongoTaskRepository(this.mongoTemplate);
    }

    @Bean
    @Profile({"sql-db"})
    public TodoTaskRepository mariaDBTaskRepository() {
        return new MariaDBTaskRepository(this.jdbcTemplate);
    }
}
