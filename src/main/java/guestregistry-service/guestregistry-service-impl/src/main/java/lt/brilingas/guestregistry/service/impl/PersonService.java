package lt.brilingas.guestregistry.service.impl;
import lt.brilingas.guestregistry.dao.api.IPersonDAO;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.IPersonService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.impl.validation.IPersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService implements IPersonService {
    @Autowired
    private IPersonDAO personDAO;
    @Autowired
    private IPersonValidator personValidator;

    @Override
    public String insertPerson(PersonDTO personDTO) throws FieldNotValidException {
        personValidator.validateOnCreate(personDTO);
        personDAO.insert(personDTO);
        return personDTO.getId();
    }
    @Override
    public PersonDTO getPersonById(String personId) throws FieldNotValidException {
        personValidator.validateField(personId);
        return personDAO.getById(personId).get();
    }
    @Override
    public List<PersonDTO> getPersonByName(String personName) throws FieldNotValidException {
        personValidator.validateField(personName);
        return personDAO.getByName(personName);
    }
    @Override
    public List<PersonDTO> getAllPersons() {
        return personDAO.getAll();
    }
    @Override
    public void updatePersonById(String personId, PersonDTO personDTO) throws ResourceNotFoundException, FieldNotValidException {
        personValidator.validateOnUpdate(personId,personDTO);
        if (personDAO.getById(personId).isPresent()) {
            personDAO.update(personDTO);
        } else {
            throw new ResourceNotFoundException("No such person with specified ID exists");
        }
    }
    @Override
    public void deletePersonById(String personId) throws  FieldNotValidException {
        personValidator.validateOnDelete(personId);
        personDAO.deleteById(personId);
    }
}