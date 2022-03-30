package lt.brilingas.guestregistry.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import lt.brilingas.guestregistry.service.api.IEventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebMvcTest(controllers = {EventController.class})
@AutoConfigureMockMvc(addFilters = false)  //disable filters from Spring Security/ other
public class EventControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IEventService eventService;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final String ID = "0123456789abcdef01234567";
    private final EventDTO EVENT = newEventDTO("Abc");

    @Test
    public void createNewEventOkTest() throws Exception {
        Mockito.when(eventService.insertEvent(EVENT)).thenReturn(ID);
        MvcResult result = TestRequestExecutor.post(mockMvc, "/events", HttpStatus.CREATED,
                jsonMapper.writeValueAsString(EVENT));
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertEquals(ID, resultString);
    }

    @Test
    public void updateEventByIdOkTest() throws Exception {
        MvcResult result = TestRequestExecutor.put(mockMvc, "/events/" + ID, HttpStatus.OK,
                jsonMapper.writeValueAsString(EVENT));
        Mockito.verify(eventService, Mockito.times(1)).updateEventById(ID, EVENT);
    }

    @Test
    public void deleteEventByIdOkTest() throws Exception {
        MvcResult result = TestRequestExecutor.delete(mockMvc, "/events/" + ID, HttpStatus.OK);
        Mockito.verify(eventService, Mockito.times(1)).deleteEventById(ID);
    }

    @Test
    public void getEventByIdOkTest() throws Exception {
        Mockito.when(eventService.getEventById(ID)).thenReturn(EVENT);
        MvcResult result = TestRequestExecutor.get(mockMvc, "/events/" + ID, HttpStatus.OK);
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertEquals(jsonMapper.writeValueAsString(EVENT), resultString);
    }

    @Test
    public void getEventsOkTest() throws Exception {
        List<EventDTO> list = new ArrayList<>();
        list.add(newEventDTO("Abc"));
        list.add(newEventDTO("Def"));
        String fieldWithFunction = "field.function";
        String value = "value";
        Map<String, String> parameters = Map.of(fieldWithFunction, value);
        Mockito.when(eventService.getAllEvents(parameters)).thenReturn(list);
        MvcResult result = TestRequestExecutor.get(mockMvc, "/events?" + fieldWithFunction + "=" + value, HttpStatus.OK);
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertEquals(jsonMapper.writeValueAsString(list), resultString);
    }

    private EventDTO newEventDTO(String name) {
        EventDTO event = new EventDTO();
        event.setId(null);
        event.setLocationId(null);
        event.setEventName(name);
        event.setParticipantsNumber(10);
        event.setDate(null);
        return event;
    }
}
