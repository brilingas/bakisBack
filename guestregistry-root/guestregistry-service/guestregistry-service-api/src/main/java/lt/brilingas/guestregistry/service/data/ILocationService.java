package lt.brilingas.guestregistry.service.data;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import java.util.List;
import java.util.Map;

public interface ILocationService {
    String insertLocation(LocationDTO locationDTO) throws FieldNotValidException;
    LocationDTO getLocationById(String locationId) throws ResourceNotFoundException;
    List<LocationDTO> getAllLocations(Map<String, String> parameters) throws Exception;
    void updateLocationById(String locationId, LocationDTO locationDTO)
            throws FieldNotValidException, ResourceNotFoundException;
    void deleteLocationById(String locationId) throws Exception;
    boolean existsById(String locationId);
}