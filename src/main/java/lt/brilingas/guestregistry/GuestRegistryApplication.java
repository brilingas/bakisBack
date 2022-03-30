package lt.brilingas.guestregistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "lt.brilingas.guestregistry", exclude = {DataSourceAutoConfiguration.class })
public class GuestRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuestRegistryApplication.class, args);
    }
}
