package lt.brilingas.guestregistry.dao.api;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Component
public interface ILocationDAO {
    String insert(LocationDTO locationDTO);
    void update(LocationDTO locationDTO);
    void deleteById(String locationId);
    Optional<LocationDTO> getById(String locationId);
    List<LocationDTO> getAll();
    List<LocationDTO> getByFilter(Map<String, Map<QueryParameterFunction, String>> parameters) throws Exception;
    boolean existsById(String locationId);
}