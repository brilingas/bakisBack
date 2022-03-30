package lt.brilingas.guestregistry.dao.impl;

import lt.brilingas.guestregistry.dao.api.IPersonDAO;
import lt.brilingas.guestregistry.dao.impl.config.DAOTestConfig;
import lt.brilingas.guestregistry.dao.impl.entity.person.PersonEntity;
import lt.brilingas.guestregistry.dao.impl.repository.IPersonRepository;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

@DataMongoTest
@ContextConfiguration(classes = {DAOTestConfig.class})
class PersonDAOTest {
    @Autowired
    private IPersonDAO personDAO;
    @Autowired
    private IPersonRepository personRepository;
/*
    @Test
    void insert() {
        PersonDTO personDTO = createPersonDTO("Test");
        Assertions.assertTrue(personRepository.existsById(personDAO.insert(personDTO)));
    }

    @Test
    void getById() {
        String personName = "Test";
        savePersonEntity(personName);
        Assertions.assertEquals(personName,personDAO.getByName(personName));
    }

    @Test
    void getByName() {
        String name = "Test";
        createPersonDTO(name);
        Assertions.assertNotEquals(new ArrayList<>(), personDAO.getByName(name));
        Assertions.assertThrows(NullPointerException.class, () -> personDAO.getByName(null));
    }

    @Test
    void getAll() {
        String name = "Test";
        personRepository.deleteAll();
        Assertions.assertTrue(personDAO.getAll().isEmpty());
        savePersonEntity(name);
        Assertions.assertEquals(1,personDAO.getAll().size());
    }

    @Test
    void update() {
        String name = savePersonEntity("Test");
        String newName = "Test2";
        PersonDTO personDTO = createPersonDTO(newName);
        personDTO.setName(newName);
        personDAO.update(personDTO);
        Assertions.assertEquals(newName,personRepository.getByName(name).toString());
    }


    @Test
    void deleteById() {
        String name = "Test";
        String id=savePersonEntity(name);
        personDAO.deleteById(id);
        Assertions.assertFalse(personRepository.existsById(id));
        Assertions.assertThrows(NullPointerException.class,()->personDAO.deleteById(null));
    }
*/
    private PersonDTO createPersonDTO(String name) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(name);
        return personDTO;
    }
    private String savePersonEntity(String name) {
        PersonEntity personEntity = createPersonEntity(name);
        return personRepository.save(personEntity).getId();
    }
    private PersonEntity createPersonEntity(String name) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(name);
        return personEntity;
    }


}