package lt.brilingas.guestregistry.dao.impl.mongo.dao;

import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.dao.impl.mongo.config.DAOTestConfig;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.location.LocationEntity;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.ILocationRepository;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

@DataMongoTest
@ContextConfiguration(classes = {DAOTestConfig.class})
public class LocationDAOIntegrationTest {
    @Autowired
    private ILocationDAO locationDAO;
    @Autowired
    private ILocationRepository locationRepository;

    @Test
    public void insertTest() {
        LocationDTO location = createLocationDTO("Abc");
        Assertions.assertTrue(locationRepository.existsById(locationDAO.insert(location)));
    }

    @Test
    public void updateTest() {
        String id = insertNewLocationWithName("Abc");
        String newName = "Def";
        LocationDTO locationDTO = createLocationDTO(newName);
        locationDTO.setId(id);
        locationDAO.update(locationDTO);
        Assertions.assertEquals(newName, locationRepository.findById(id).get().getName());
    }

    @Test
    public void deleteByIdTest() {
        String id = insertNewLocationWithName("Abc");
        locationDAO.deleteById(id);
        Assertions.assertFalse(locationRepository.existsById(id));
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationDAO.deleteById(null));
    }

    @Test
    public void findByIdTest() {
        String name = "Abc";
        String id = insertNewLocationWithName(name);
        Assertions.assertEquals(name, locationDAO.getById(id).get().getName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationDAO.getById(null));
    }

    @Test
    public void findAllTest() {
        locationRepository.deleteAll();
        Assertions.assertTrue(locationDAO.getAll().isEmpty());
        insertNewLocationWithName("Abc");
        Assertions.assertEquals(1, locationDAO.getAll().size());
    }

    @Test
    public void findByFilterTest() throws Exception {
        locationRepository.deleteAll();
        Assertions.assertTrue(locationDAO.getAll().isEmpty());
        String name = "Abc";
        insertNewLocationWithName(name);
        insertNewLocationWithName("Def");
        List<LocationDTO> result = locationDAO.getByFilter(
                Collections.singletonMap("name", Collections.singletonMap(QueryParameterFunction.EQUALS, name)));
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void existsByIdTest() {
        String id = insertNewLocationWithName("Abc");
        Assertions.assertTrue(locationDAO.existsById(id));
        locationRepository.deleteAll();
        Assertions.assertFalse(locationDAO.existsById(id));
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationDAO.existsById(null));
    }

    private LocationEntity createLocationEntity(String name) {
        LocationEntity location = new LocationEntity();
        location.setName(name);
        return location;
    }

    private LocationDTO createLocationDTO(String name) {
        LocationDTO location = new LocationDTO();
        location.setName(name);
        return location;
    }

    private String insertNewLocationWithName(String name) {
        LocationEntity location = createLocationEntity(name);
        return locationRepository.insert(location).getId();
    }
}
