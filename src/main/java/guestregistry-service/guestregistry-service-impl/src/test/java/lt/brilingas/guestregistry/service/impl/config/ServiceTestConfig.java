package lt.brilingas.guestregistry.service.impl.config;

import lt.brilingas.guestregistry.dao.api.ICardDAO;
import lt.brilingas.guestregistry.dao.api.IEventDAO;
import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.dao.api.IPersonDAO;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.ICardRepository;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootConfiguration
@EnableAutoConfiguration
@MockBean(value = {ILocationDAO.class, IEventDAO.class, ICardDAO.class, IPersonDAO.class,
        ICardRepository.class})
public class ServiceTestConfig extends ServiceConfig {
}
