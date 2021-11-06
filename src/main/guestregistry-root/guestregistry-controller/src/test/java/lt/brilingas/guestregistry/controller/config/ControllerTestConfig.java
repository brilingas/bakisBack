package lt.brilingas.guestregistry.controller.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackages = "lt.brilingas.guestregistry.controller")
@EnableAutoConfiguration
public class ControllerTestConfig {
}
