package lt.brilingas.guestregistry.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.brilingas.guestregistry.data.dto.Address;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.data.dto.location.Status;
import lt.brilingas.guestregistry.data.dto.location.Type;
import lt.brilingas.guestregistry.service.data.ILocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebMvcTest(controllers = {LocationController.class})
@AutoConfigureMockMvc(addFilters = false)  //disable filters from Spring Security/ other
public class LocationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ILocationService locationService;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final String ID = "0123456789abcdef01234567";
    private final LocationDTO LOCATION = newLocationDTO("Abc");

    @Test
    public void createNewLocationOkTest() throws Exception {
        Mockito.when(locationService.insertLocation(LOCATION)).thenReturn(ID);
        MvcResult result = TestRequestExecutor.post(mockMvc, "/locations", HttpStatus.CREATED,
                jsonMapper.writeValueAsString(LOCATION));
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertEquals(ID, resultString);
    }

    @Test
    public void updateLocationByIdOkTest() throws Exception {
        MvcResult result = TestRequestExecutor.put(mockMvc, "/locations/" + ID, HttpStatus.OK,
                jsonMapper.writeValueAsString(LOCATION));
        Mockito.verify(locationService, Mockito.times(1)).updateLocation(ID, LOCATION);
    }

    @Test
    public void deleteLocationByIdOkTest() throws Exception {
        MvcResult result = TestRequestExecutor.delete(mockMvc, "/locations/" + ID, HttpStatus.OK);
        Mockito.verify(locationService, Mockito.times(1)).deleteLocation(ID);
    }

    @Test
    public void getLocationByIdOkTest() throws Exception {
        Mockito.when(locationService.getLocation(ID)).thenReturn(LOCATION);
        MvcResult result = TestRequestExecutor.get(mockMvc, "/locations/" + ID, HttpStatus.OK);
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertEquals(jsonMapper.writeValueAsString(LOCATION), resultString);
    }

    @Test
    public void getLocationsOkTest() throws Exception {
        List<LocationDTO> list = new ArrayList<>();
        list.add(newLocationDTO("Abc"));
        list.add(newLocationDTO("Def"));
        String fieldWithFunction = "field.function";
        String value = "value";
        Map<String, String> parameters = Map.of(fieldWithFunction, value);
        Mockito.when(locationService.getLocations(parameters)).thenReturn(list);
        MvcResult result = TestRequestExecutor.get(mockMvc, "/locations?" + fieldWithFunction + "=" + value, HttpStatus.OK);
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertEquals(jsonMapper.writeValueAsString(list), resultString);
    }

    private LocationDTO newLocationDTO(String name) {
        LocationDTO location = new LocationDTO();
        location.setId(null);
        location.setName(name);
        location.setContactPerson("Abc");
        location.setStatus(Status.ACTIVE);
        location.setType(Type.OFFICE);
        Address address = new Address();
        address.setCountry("Lithuania");
        address.setCity("Vilnius");
        address.setStreet("Abc");
        address.setBuildingNumber("1A");
        address.setApartmentNumber("2B");
        location.setAddress(address);
        return location;
    }
}
