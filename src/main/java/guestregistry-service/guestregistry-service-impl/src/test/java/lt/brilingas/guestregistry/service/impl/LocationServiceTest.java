package lt.brilingas.guestregistry.service.impl;

import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.service.data.ILocationService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.impl.config.ServiceTestConfig;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.impl.validation.ILocationValidator;
import lt.brilingas.guestregistry.service.impl.validation.IQueryParametersCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;

@SpringBootTest(classes = {ServiceTestConfig.class})
public class LocationServiceTest {
    @MockBean
    private ILocationValidator locationValidator;
    @MockBean
    private IQueryParametersCreator queryParametersCreator;
    @Autowired  //MockBean
    private ILocationDAO locationDAO;
    @Autowired
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.updateLocation(ID, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.updateLocation(null, LOCATION));
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.updateLocation(null, null));

        Mockito.when(locationDAO.existsById(ID)).thenReturn(true);
        locationService.updateLocation(ID, LOCATION);
        Assertions.assertEquals(ID, LOCATION.getId());
        Mockito.verify(locationDAO, Mockito.times(1)).update(LOCATION);

        Mockito.when(locationDAO.existsById(ID)).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> locationService.updateLocation(ID, LOCATION));

        Mockito.verify(locationValidator, Mockito.times(2)).validateOnUpdate(LOCATION);
    }

    @Test
    public void deleteLocation() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.deleteLocation(null));

        locationService.deleteLocation(ID);
        Mockito.verify(locationValidator, Mockito.times(1)).validateOnDelete(Mockito.eq(ID));
        Mockito.verify(locationDAO, Mockito.times(1)).deleteById(Mockito.eq(ID));
    }

    @Test
    public void getLocationTest() throws ResourceNotFoundException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.getLocation(null));

        Mockito.when(locationDAO.findById(ID)).thenReturn(Optional.of(LOCATION));
        Assertions.assertEquals(LOCATION, locationService.getLocation(ID));

        Mockito.when(locationDAO.findById(ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> locationService.getLocation(ID));
    }

    @Test
    public void getLocationsTest() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.getLocations(null));

        List<LocationDTO> result = new ArrayList<>();
        Mockito.when(locationDAO.findAll()).thenReturn(result);
        Assertions.assertEquals(result, locationService.getLocations(new HashMap<>()));

        result = new ArrayList<>();
        Map<String, String> parameters = Map.of("fieldName.function", "value");
        Map<String, Map<QueryParameterFunction, String>> queryParameter =
                Map.of("fieldName", Map.of(QueryParameterFunction.EQUALS, "value"));
        Mockito.when(queryParametersCreator.create(parameters, LocationDTO.FIELDS_ALLOWED_IN_FILTER)).
                thenReturn(queryParameter);
        Mockito.when(locationDAO.findByFilter(queryParameter)).thenReturn(result);
        Assertions.assertEquals(result, locationService.getLocations(parameters));
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
