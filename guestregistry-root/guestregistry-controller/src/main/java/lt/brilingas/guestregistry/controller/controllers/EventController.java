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
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
    @Autowired
    private IEventService eventService;

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewEvent(@RequestBody EventDTO eventDTO) throws FieldNotValidException {
        return eventService.insertEvent(eventDTO);
    }

    @GetMapping(path = "/{eventId:[a-f0-9]{24}}")
    @ResponseStatus(HttpStatus.OK)
    public EventDTO getEventById(@PathVariable String eventId) throws ResourceNotFoundException {
        return eventService.getEventById(eventId);
    }

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDTO> getAllEvents(@RequestParam Map<String, String> parameters) throws Exception {
        return eventService.getAllEvents(parameters);
    }

    @PutMapping(path = "/{eventId:[a-f0-9]{24}}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEventById(@PathVariable String eventId, @RequestBody EventDTO eventDTO)
            throws FieldNotValidException, ResourceNotFoundException {
        eventService.updateEventById(eventId, eventDTO);
    }

    @DeleteMapping(path = "/{eventId:[a-f0-9]{24}}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEventById(@PathVariable String eventId) throws ResourceNotFoundException {
        eventService.deleteEventById(eventId);
    }
}