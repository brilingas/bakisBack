package lt.brilingas.guestregistry.service.impl;

import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.service.api.ILocationService;
import lt.brilingas.guestregistry.service.api.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;
import lt.brilingas.guestregistry.service.impl.validation.ILocationValidator;
import lt.brilingas.guestregistry.service.impl.validation.IQueryParametersCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;

public class LocationServiceTest {
    @MockBean
    private ILocationValidator locationValidator;
    @MockBean
    private IQueryParametersCreator queryParametersCreator;
    @MockBean
    private ILocationDAO locationDAO;
    @MockBean
    private ILocationService locationService;
    private final String ID = "0123456789abcdef01234567";
    private final LocationDTO LOCATION = new LocationDTO();
    private final List<LocationDTO> LIST = new ArrayList<>();

    @Test
    public void insertLocationTest() throws FieldNotValidException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.insertLocation(null));

        Mockito.when(locationDAO.insert(LOCATION)).thenReturn(ID);
        String locationId = locationService.insertLocation(LOCATION);
        Assertions.assertEquals(ID, locationId);

        Mockito.verify(locationValidator, Mockito.times(1)).validateOnCreate(LOCATION);
    }

    @Test
    public void updateLocationTest() throws FieldNotValidException, ResourceNotFoundException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.updateLocationById(ID, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.updateLocationById(null, LOCATION));
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.updateLocationById(null, null));

        Mockito.when(locationDAO.existsById(ID)).thenReturn(true);
        locationService.updateLocationById(ID, LOCATION);
        Assertions.assertEquals(ID, LOCATION.getId());
        Mockito.verify(locationDAO, Mockito.times(1)).update(LOCATION);

        Mockito.when(locationDAO.existsById(ID)).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> locationService.updateLocationById(ID, LOCATION));

        Mockito.verify(locationValidator, Mockito.times(2)).validateOnUpdate(LOCATION);
    }

    @Test
    public void deleteLocation() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.deleteLocationById(null));

        locationService.deleteLocationById(ID);
        Mockito.verify(locationValidator, Mockito.times(1)).validateOnDelete(Mockito.eq(ID));
        Mockito.verify(locationDAO, Mockito.times(1)).deleteById(Mockito.eq(ID));
    }

    @Test
    public void getLocationTest() throws ResourceNotFoundException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.getLocationById(null));

        Mockito.when(locationDAO.getById(ID)).thenReturn(Optional.of(LOCATION));
        Assertions.assertEquals(LOCATION, locationService.getLocationById(ID));

        Mockito.when(locationDAO.getById(ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> locationService.getLocationById(ID));
    }

    @Test
    public void getLocationsTest() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.getAllLocations(null));

        List<LocationDTO> result = new ArrayList<>();
        Mockito.when(locationDAO.getAll()).thenReturn(result);
        Assertions.assertEquals(result, locationService.getAllLocations(new HashMap<>()));

        result = new ArrayList<>();
        Map<String, String> parameters = Map.of("fieldName.function", "value");
        Map<String, Map<QueryParameterFunction, String>> queryParameter =
                Map.of("fieldName", Map.of(QueryParameterFunction.EQUALS, "value"));
        Mockito.when(queryParametersCreator.create(parameters, LocationDTO.FIELDS_ALLOWED_IN_FILTER)).
                thenReturn(queryParameter);
        Mockito.when(locationDAO.getByFilter(queryParameter)).thenReturn(result);
        Assertions.assertEquals(result, locationService.getAllLocations(parameters));
    }

    @Test
    public void existsByIdTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.existsById(null));

        Mockito.when(locationDAO.existsById(ID)).thenReturn(true);
        Assertions.assertTrue(locationService.existsById(ID));

        Mockito.when(locationDAO.existsById(ID)).thenReturn(false);
        Assertions.assertFalse(locationService.existsById(ID));
    }
}
