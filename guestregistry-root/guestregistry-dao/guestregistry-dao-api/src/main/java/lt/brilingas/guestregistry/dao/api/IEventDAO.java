package lt.brilingas.guestregistry.dao.api;

import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Component
public interface IEventDAO {
    String insert(EventDTO event);
    void update(EventDTO event);
    void deleteById(String eventId);
    Optional<EventDTO> findById(String eventId);
    List<EventDTO> findAll();
    List<EventDTO> findByFilter(Map<String, Map<QueryParameterFunction, String>> parameters) throws Exception;
    boolean existsById(String eventId);
}
