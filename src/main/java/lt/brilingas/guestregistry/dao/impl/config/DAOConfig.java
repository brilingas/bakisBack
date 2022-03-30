package lt.brilingas.guestregistry.dao.impl.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@Configuration
@ComponentScan(basePackages = "lt.brilingas.guestregistry.dao.impl")
@PropertySource("classpath:mongo.properties")
@EnableMongoRepositories(basePackages = "lt.brilingas.guestregistry.dao.impl")
public class DAOConfig {
}
