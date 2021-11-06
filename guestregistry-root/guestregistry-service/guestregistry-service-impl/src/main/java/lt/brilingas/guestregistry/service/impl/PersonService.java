package lt.brilingas.guestregistry.service.impl;
import lt.brilingas.guestregistry.dao.api.IPersonDAO;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.IPersonService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.impl.validation.IPersonValidator;
import lt.brilingas.guestregistry.service.impl.validation.IQueryParametersCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonService implements IPersonService {
    @Autowired
    private IPersonDAO personDAO;
    @Autowired
    private IPersonValidator personValidator;
    @Autowired
    private IQueryParametersCreator queryParametersCreator;

    @Override
    public String insertPerson(PersonDTO personDTO) throws FieldNotValidException {
        if (personDTO==null){
            throw new IllegalArgumentException();
        }
        personValidator.validateOnCreate(personDTO);
        return personDAO.insert(personDTO);
    }
    @Override
    public void updatePersonById(String personId, PersonDTO personDTO) throws ResourceNotFoundException, FieldNotValidException {
        if (personDTO == null) {
            throw new IllegalArgumentException();
        }
        personDTO.setId(personId);
        personValidator.validateOnUpdate(personDTO);
        if (personDAO.existsById(personId)) {
            personDAO.update(personDTO);
        } else {
            throw new ResourceNotFoundException("Person by ID = "+personId+" not ofund");
        }
    }
    @Override
    public void deletePersonById(String personId) throws Exception {
        if (personId == null) {
            throw new IllegalArgumentException();
        }
        personValidator.validateOnDelete(personId);
        personDAO.deleteById(personId);
    }
    @Override
    public PersonDTO getPersonById(String personId) throws ResourceNotFoundException {
        if (personId == null) {
            throw new IllegalArgumentException();
        }
        Optional<PersonDTO> personDTOOptional = personDAO.getById(personId);
        if (personDTOOptional.isEmpty()) {
            throw new ResourceNotFoundException("Person by ID = " + personId + " not found");
        } else {
            return personDTOOptional.get();
        }
    }
    @Override
    public List<PersonDTO> getAllPersons(Map<String, String> parameters) throws Exception {
        if (parameters == null) {
            throw new IllegalArgumentException();
        }
        if (parameters.isEmpty()) {
            return personDAO.getAll();
        } else {
            return personDAO.getByFilter(queryParametersCreator.create(parameters, PersonDTO.FIELDS_ALLOWED_IN_FILTER));

        }
    }
    @Override
    public List<PersonDTO> getPersonByName(String personName) throws FieldNotValidException {
        return null;//TODO kam isvis reik byName?
    }
    @Override
    public boolean existsById(String personId) {
        if (personId == null) {
            throw new IllegalArgumentException();
        }
        return personDAO.existsById(personId);    }
}