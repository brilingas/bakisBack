package lt.brilingas.guestregistry.service.data;

import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import java.util.List;
import java.util.Map;

public interface IEventService {
    public String insertEvent(EventDTO event) throws FieldNotValidException;

    public void updateEvent(String eventId, EventDTO eventForUpdate)
            throws FieldNotValidException, ResourceNotFoundException;

    public void deleteEvent(String eventId) throws ResourceNotFoundException;

    public EventDTO getEvent(String eventId) throws ResourceNotFoundException;

    public List<EventDTO> getEvents(Map<String, String> parameters) throws Exception;

    public boolean existsById(String eventId);
}
