package lt.brilingas.guestregistry.dao.api;

import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IEventDAO {
    public String insert(EventDTO event);
    public void update(EventDTO event);
    public void deleteById(String eventId);
    public Optional<EventDTO> findById(String eventId);
    public List<EventDTO> findAll();
    public List<EventDTO> findByFilter(Map<String, Map<QueryParameterFunction, String>> parameters) throws Exception;
    public boolean existsById(String eventId);
}
