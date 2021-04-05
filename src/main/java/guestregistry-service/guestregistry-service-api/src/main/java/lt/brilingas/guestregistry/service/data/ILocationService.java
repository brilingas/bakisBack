package lt.brilingas.guestregistry.service.data;

import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import java.util.List;
import java.util.Map;

public interface ILocationService {
    public String insertLocation(LocationDTO location) throws FieldNotValidException;

    public void updateLocation(String locationId, LocationDTO locationForUpdate)
            throws FieldNotValidException, ResourceNotFoundException;

    public void deleteLocation(String locationId) throws Exception;

    public LocationDTO getLocation(String locationId) throws ResourceNotFoundException;

    public List<LocationDTO> getLocations(Map<String, String> parameters) throws Exception;

    public boolean existsById(String locationId);
}
