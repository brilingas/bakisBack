package lt.brilingas.guestregistry.service.api;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import java.util.List;
import java.util.Map;

public interface IEventService {
    String insertEvent(EventDTO eventDTO) throws FieldNotValidException;

    void updateEventById(String eventId, EventDTO eventDTO)
            throws FieldNotValidException, ResourceNotFoundException;

    void deleteEventById(String eventId) throws ResourceNotFoundException;

    EventDTO getEventById(String eventId) throws ResourceNotFoundException;

    List<EventDTO> getAllEvents(Map<String, String> parameters) throws Exception;

    boolean existsById(String eventId);
}
