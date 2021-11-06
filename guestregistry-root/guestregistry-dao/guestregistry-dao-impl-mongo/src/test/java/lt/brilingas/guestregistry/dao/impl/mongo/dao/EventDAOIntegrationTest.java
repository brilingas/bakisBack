package lt.brilingas.guestregistry.dao.impl.mongo.dao;

import lt.brilingas.guestregistry.dao.api.IEventDAO;
import lt.brilingas.guestregistry.dao.impl.mongo.config.DAOTestConfig;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.event.EventEntity;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.IEventRepository;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

@DataMongoTest
@ContextConfiguration(classes = {DAOTestConfig.class})
public class EventDAOIntegrationTest {
    @Autowired
    private IEventDAO eventDAO;
    @Autowired
    private IEventRepository eventRepository;

    @Test
    public void insertTest() {
        EventDTO event = createEventDTO("Abc");
        Assertions.assertTrue(eventRepository.existsById(eventDAO.insert(event)));
    }

    @Test
    public void updateTest() {
        String id = insertNewEventWithName("Abc");
        String newName = "Def";
        EventDTO eventDTO = createEventDTO(newName);
        eventDTO.setId(id);
        eventDAO.update(eventDTO);
        Assertions.assertEquals(newName, eventRepository.findById(id).get().getEventName());
    }

    @Test
    public void deleteByIdTest() {
        String id = insertNewEventWithName("Abc");
        eventDAO.deleteById(id);
        Assertions.assertFalse(eventRepository.existsById(id));
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventDAO.deleteById(null));
    }

    @Test
    public void findByIdTest() {
        String name = "Abc";
        String id = insertNewEventWithName(name);
        Assertions.assertEquals(name, eventDAO.findById(id).get().getEventName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventDAO.findById(null));
    }

    @Test
    public void findAllTest() {
        eventRepository.deleteAll();
        Assertions.assertTrue(eventDAO.findAll().isEmpty());
        insertNewEventWithName("Abc");
        Assertions.assertEquals(1, eventDAO.findAll().size());
    }

/*-----------------    @Test
    public void findByFilterTest() throws Exception {
        eventRepository.deleteAll();
        Assertions.assertTrue(eventDAO.findAll().isEmpty());
        String name = "Abc";
        insertNewEventWithName(name);
        insertNewEventWithName("Def");
        List<EventDTO> result = eventDAO.findByFilter(
                Collections.singletonMap("name", Collections.singletonMap(QueryParameterFunction.EQUALS, name)));
        Assertions.assertEquals(1, result.size());
    }*/

    @Test
    public void existsByIdTest() {
        String id = insertNewEventWithName("Abc");
        Assertions.assertTrue(eventDAO.existsById(id));
        eventRepository.deleteAll();
        Assertions.assertFalse(eventDAO.existsById(id));
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventDAO.existsById(null));
    }

    private EventEntity createEventEntity(String name) {
        EventEntity event = new EventEntity();
        event.setEventName(name);
        return event;
    }

    private EventDTO createEventDTO(String name) {
        EventDTO event = new EventDTO();
        event.setEventName(name);
        return event;
    }

    private String insertNewEventWithName(String name) {
        EventEntity event = createEventEntity(name);
        return eventRepository.insert(event).getId();
    }
}
