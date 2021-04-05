package lt.brilingas.guestregistry.dao.api;

import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ILocationDAO {
    public String insert(LocationDTO location);
    public void update(LocationDTO location);
    public void deleteById(String locationId);
    public Optional<LocationDTO> findById(String locationId);
    public List<LocationDTO> findAll();
    public List<LocationDTO> findByFilter(Map<String, Map<QueryParameterFunction, String>> parameters) throws Exception;
    public boolean existsById(String locationId);
}
