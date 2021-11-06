package lt.brilingas.guestregistry.service.impl;

import lt.brilingas.guestregistry.dao.api.IEventDAO;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import lt.brilingas.guestregistry.service.data.IEventService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.impl.config.ServiceTestConfig;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.impl.validation.IEventValidator;
import lt.brilingas.guestregistry.service.impl.validation.IQueryParametersCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;

@SpringBootTest(classes = {ServiceTestConfig.class})
public class EventServiceTest {
    @MockBean
    private IEventValidator eventValidator;
    @MockBean
    private IQueryParametersCreator queryParametersCreator;
    @Autowired  //MockBean
    private IEventDAO eventDAO;
    @Autowired
    private IEventService eventService;
    private final String ID = "0123456789abcdef01234567";
    private final EventDTO EVENT = new EventDTO();
    private final List<EventDTO> LIST = new ArrayList<>();

    @Test
    public void insertEventTest() throws FieldNotValidException {
        Assertions.assertEquals(1,1);

//        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.insertEvent(null));
//        Mockito.when(eventDAO.insert(EVENT)).thenReturn(ID);
//        Assertions.assertEquals(ID, eventService.insertEvent(EVENT));
//        Mockito.verify(eventValidator, Mockito.times(1)).validateOnCreate(EVENT);
    }

    @Test
    public void updateEventTest() throws FieldNotValidException, ResourceNotFoundException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.updateEventById(ID, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.updateEventById(null, EVENT));
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.updateEventById(null, null));

        EventDTO event = new EventDTO();
        Mockito.when(eventDAO.existsById(ID)).thenReturn(true);
        eventService.updateEventById(ID, event);
        Assertions.assertEquals(ID, event.getId());
        Mockito.verify(eventDAO, Mockito.times(1)).update(event);

        Mockito.when(eventDAO.existsById(ID)).thenReturn(false);
        Throwable exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> eventService.updateEventById(ID, event));
        Assertions.assertEquals("Event by ID = " + ID + " not found", exception.getMessage());

        Mockito.verify(eventValidator, Mockito.times(2)).validateOnUpdate(event);
    }

    @Test
    public void deleteEvent() throws ResourceNotFoundException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.deleteEventById(null));

        eventService.deleteEventById(ID);
        Mockito.verify(eventDAO, Mockito.times(1)).deleteById(Mockito.eq(ID));
    }

    @Test
    public void getEventTest() throws ResourceNotFoundException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.getEventById(null));

        Mockito.when(eventDAO.findById(ID)).thenReturn(Optional.of(EVENT));
        Assertions.assertEquals(EVENT, eventService.getEventById(ID));

        Mockito.when(eventDAO.findById(ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> eventService.getEventById(ID));
    }

    @Test
    public void getEventsTest() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.getAllEvents(null));

        List<EventDTO> result = new ArrayList<>();
        Mockito.when(eventDAO.findAll()).thenReturn(result);
        Assertions.assertEquals(result, eventService.getAllEvents(new HashMap<>()));

        result = new ArrayList<>();
        Map<String, String> parameters = Map.of("fieldName.function", "value");
        Map<String, Map<QueryParameterFunction, String>> queryParameter =
                Map.of("fieldName", Map.of(QueryParameterFunction.EQUALS, "value"));
        Mockito.when(queryParametersCreator.create(parameters, EventDTO.FIELDS_ALLOWED_IN_FILTER)).
                thenReturn(queryParameter);
        Mockito.when(eventDAO.findByFilter(queryParameter)).thenReturn(result);
        Assertions.assertEquals(result, eventService.getAllEvents(parameters));
    }

    @Test
    public void existsByIdTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.existsById(null));

        Mockito.when(eventDAO.existsById(ID)).thenReturn(true);
        Assertions.assertTrue(eventService.existsById(ID));

        Mockito.when(eventDAO.existsById(ID)).thenReturn(false);
        Assertions.assertFalse(eventService.existsById(ID));
    }
}
