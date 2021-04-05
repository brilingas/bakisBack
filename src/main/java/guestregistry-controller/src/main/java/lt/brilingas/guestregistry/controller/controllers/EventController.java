package lt.brilingas.guestregistry.controller.controllers;

import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import lt.brilingas.guestregistry.service.data.IEventService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private IEventService eventService;

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewEvent(@RequestBody EventDTO event) throws FieldNotValidException {
        return eventService.insertEvent(event);
    }

    @GetMapping(path = "/{eventId:[a-f0-9]{24}}")
    public EventDTO getEventById(@PathVariable String eventId) throws ResourceNotFoundException {
        return eventService.getEvent(eventId);
    }

    @GetMapping(path = "")
    public List<EventDTO> getEvents(@RequestParam Map<String, String> parameters) throws Exception {
        return eventService.getEvents(parameters);
    }

    @PutMapping(path = "/{eventId:[a-f0-9]{24}}")
    public void updateEventById(@PathVariable String eventId, @RequestBody EventDTO event)
            throws FieldNotValidException, ResourceNotFoundException {
        eventService.updateEvent(eventId, event);
    }

    @DeleteMapping(path = "/{eventId:[a-f0-9]{24}}")
    public void deleteEventById(@PathVariable String eventId) throws ResourceNotFoundException {
        eventService.deleteEvent(eventId);
    }
}
