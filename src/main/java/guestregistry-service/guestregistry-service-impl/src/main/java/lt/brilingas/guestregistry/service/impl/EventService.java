package lt.brilingas.guestregistry.service.impl;

import lt.brilingas.guestregistry.dao.api.IEventDAO;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import lt.brilingas.guestregistry.service.data.IEventService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.impl.validation.IEventValidator;
import lt.brilingas.guestregistry.service.impl.validation.IQueryParametersCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventService implements IEventService {
    @Autowired
    private IEventDAO eventDAO;
    @Autowired
    private IEventValidator eventValidator;
    @Autowired
    private IQueryParametersCreator queryParametersCreator;

    @Override
    public String insertEvent(EventDTO event) throws FieldNotValidException {
        if (event == null) throw new IllegalArgumentException();
        eventValidator.validateOnCreate(event);
        return eventDAO.insert(event);
    }

    @Override
    public void updateEvent(String eventId, EventDTO eventForUpdate)
            throws FieldNotValidException, ResourceNotFoundException {
        if (eventForUpdate == null || eventId == null) throw new IllegalArgumentException();
        eventForUpdate.setId(eventId);
        eventValidator.validateOnUpdate(eventForUpdate);
        if (eventDAO.existsById(eventId)) {
            eventDAO.update(eventForUpdate);
        } else {
            throw new ResourceNotFoundException("Event by ID = " + eventId + " not found");
        }
    }

    @Override
    public void deleteEvent(String eventId) throws ResourceNotFoundException {
        if (eventId == null) throw new IllegalArgumentException();
        eventDAO.deleteById(eventId);
    }

    @Override
    public EventDTO getEvent(String eventId) throws ResourceNotFoundException {
        if (eventId == null) throw new IllegalArgumentException();
        Optional<EventDTO> eventOptional = eventDAO.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("Event by ID = " + eventId + " not found");
        } else {
            return eventOptional.get();
        }
    }

    @Override
    public List<EventDTO> getEvents(Map<String, String> parameters) throws Exception {
        if (parameters == null) throw new IllegalArgumentException();
        if (parameters.isEmpty()) {
            return eventDAO.findAll();
        } else {
            return eventDAO.findByFilter(queryParametersCreator.create(parameters, EventDTO.FIELDS_ALLOWED_IN_FILTER));
        }
    }

    @Override
    public boolean existsById(String eventId) {
        if (eventId == null) throw new IllegalArgumentException();
        return eventDAO.existsById(eventId);
    }
}
