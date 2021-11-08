package lt.brilingas.guestregistry.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "lt.brilingas.guestregistry")
public class GuestRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuestRegistryApplication.class, args);
    }
}
