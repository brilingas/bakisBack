package lt.brilingas.guestregistry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.brilingas.guestregistry.controller.config.ControllerTestConfig;
import lt.brilingas.guestregistry.controller.controllers.TestRequestExecutor;
import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.dao.impl.mongo.config.DAOConfig;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.ILocationRepository;
import lt.brilingas.guestregistry.data.dto.Address;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.data.dto.location.Status;
import lt.brilingas.guestregistry.data.dto.location.Type;
import lt.brilingas.guestregistry.service.impl.config.ServiceConfig;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.core.userdetails.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest(classes = {ControllerTestConfig.class, ServiceConfig.class, DAOConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LocationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ILocationDAO locationDAO;
    @Autowired
    private ILocationRepository locationRepository;
    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final String TEST_USER = "testUser";
    private final String TEST_USER_PASSWORD = "testUser123";

    @BeforeAll
    public void createTestUser() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(TEST_USER)
                .password(TEST_USER_PASSWORD)
                .roles("ADMIN")
                .build();
        inMemoryUserDetailsManager.createUser(user);
    }

    @Test
    public void createNewLocationOkTest() throws Exception {
        LocationDTO location = newLocationDTO("Abc");

        MvcResult result = TestRequestExecutor.postWithBasicAuthorization(mockMvc, "/locations", HttpStatus.CREATED,
                jsonMapper.writeValueAsString(location), TEST_USER, TEST_USER_PASSWORD);
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.matches("^[a-f0-9]{24}$"));

        LocationDTO resultLocation = locationDAO.findById(resultString).get();
        location.setId(resultString);
        Assertions.assertEquals(location, resultLocation);
    }

    @Test
    public void updateLocationByIdOkTest() throws Exception {
        LocationDTO location = insertNewLocationToDB("Abc");

        String newName = "Def";
        location.setName(newName);
        MvcResult result = TestRequestExecutor.putWithBasicAuthorization(mockMvc, "/locations/" + location.getId(),
                HttpStatus.OK, jsonMapper.writeValueAsString(location), TEST_USER, TEST_USER_PASSWORD);

        LocationDTO resultLocation = locationDAO.findById(location.getId()).get();
        Assertions.assertEquals(location, resultLocation);
    }

    @Test
    public void deleteLocationByIdOkTest() throws Exception {
        LocationDTO location = insertNewLocationToDB("Abc");

        MvcResult result = TestRequestExecutor.deleteWithBasicAuthorization(mockMvc, "/locations/" + location.getId(),
                HttpStatus.OK, TEST_USER, TEST_USER_PASSWORD);

        Assertions.assertFalse(locationDAO.existsById(location.getId()));
    }

    @Test
    public void getLocationByIdOkTest() throws Exception {
        LocationDTO location = insertNewLocationToDB("Abc");

        MvcResult result = TestRequestExecutor.getWithBasicAuthorization(mockMvc, "/locations/" + location.getId(),
                HttpStatus.OK, TEST_USER, TEST_USER_PASSWORD);

        String resultString = result.getResponse().getContentAsString();
        Assertions.assertEquals(jsonMapper.writeValueAsString(location), resultString);
    }

    @Test
    public void getAllLocationsOkTest() throws Exception {
        locationRepository.deleteAll();
        List<LocationDTO> listToSearch = new ArrayList<>();
        listToSearch.add(insertNewLocationToDB("Abc"));
        listToSearch.add(insertNewLocationToDB("Def"));

        testGetLocationsOk(listToSearch, "");
    }

    @Test
    public void getLocationsByFilterOkEqualsTest() throws Exception {
        locationRepository.deleteAll();
        List<LocationDTO> listToSearch = new ArrayList<>();
        String nameToSearch = "Abc";
        listToSearch.add(insertNewLocationToDB(nameToSearch));
        insertNewLocationToDB("Def");

        testGetLocationsOk(listToSearch, "?name.equals=" + nameToSearch);
    }

    @Test
    public void getLocationsByFilterOkEqualsWithEmptyResultTest() throws Exception {
        locationRepository.deleteAll();
        List<LocationDTO> listToSearch = new ArrayList<>();
        String nameToSearch = "Abc";
        insertNewLocationToDB("Def");

        testGetLocationsOk(listToSearch, "?name.equals=" + nameToSearch);
    }

    @Test
    public void getLocationsByFilterOkNotEqualsTest() throws Exception {
        locationRepository.deleteAll();
        List<LocationDTO> listToSearch = new ArrayList<>();
        String nameToSearch = "Abc";
        insertNewLocationToDB(nameToSearch);
        listToSearch.add(insertNewLocationToDB("Def"));

        testGetLocationsOk(listToSearch, "?name.notEquals=" + nameToSearch);
    }

    @Test
    public void getLocationsByFilterOkContainsSubstringTest() throws Exception {
        locationRepository.deleteAll();
        List<LocationDTO> listToSearch = new ArrayList<>();
        listToSearch.add(insertNewLocationToDB("Abc"));
        insertNewLocationToDB("Def");

        testGetLocationsOk(listToSearch, "?name.containsSubstring=A");
    }

    @Test
    public void getLocationsByFilterOkContainsSubstringAndNotEqualsTest() throws Exception {
        locationRepository.deleteAll();
        List<LocationDTO> listToSearch = new ArrayList<>();
        listToSearch.add(insertNewLocationToDB("Abcdef"));
        insertNewLocationToDB("Abc");

        testGetLocationsOk(listToSearch, "?name.containsSubstring=Abc&name.notEquals=Abc");
    }

    @Test
    public void getLocationsByFilterBadNotValidFunctionInFilterTest() throws Exception {
        String notValidFunctionName = "notValidFunction";

        MvcResult result = TestRequestExecutor.getWithBasicAuthorization(mockMvc,
                "/locations?name." + notValidFunctionName + "=Abc",
                HttpStatus.BAD_REQUEST, TEST_USER, TEST_USER_PASSWORD);

        testRequestResultMatchesErrorPattern(result, "Bad request due to error in syntax: " + notValidFunctionName +
                " function is not allowed in filter");
    }

    @Test
    public void getLocationsByFilterBadNotValidFieldNameInFilterTest() throws Exception {
        String notValidFieldName = "notValidFieldName";

        MvcResult result = TestRequestExecutor.getWithBasicAuthorization(mockMvc,
                "/locations?" + notValidFieldName + ".equals=Abc",
                HttpStatus.BAD_REQUEST, TEST_USER, TEST_USER_PASSWORD);

        testRequestResultMatchesErrorPattern(result, "Bad request due to error in syntax: " + notValidFieldName +
                " parameter is not allowed in filter");
    }

    private void testRequestResultMatchesErrorPattern(MvcResult result, String message) throws UnsupportedEncodingException {
        String resultString = result.getResponse().getContentAsString();
        Pattern p = Pattern.compile("^\\{\"errorId\":[0-9]{1,10},\"message\":\"" + message + "\"}$");
        Matcher m = p.matcher(resultString);
        Assertions.assertTrue(m.matches());
    }

    private void testGetLocationsOk(List<LocationDTO> listToSearch, String filter) throws Exception {
        MvcResult result = TestRequestExecutor.getWithBasicAuthorization(mockMvc,
                "/locations" + filter, HttpStatus.OK, TEST_USER, TEST_USER_PASSWORD);

        String resultString = result.getResponse().getContentAsString();
        Assertions.assertEquals(jsonMapper.writeValueAsString(listToSearch), resultString);
    }

    private LocationDTO insertNewLocationToDB(String name) {
        LocationDTO location = newLocationDTO(name);
        String id = locationDAO.insert(location);
        location.setId(id);
        Assertions.assertTrue(locationDAO.existsById(id));
        return location;
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
