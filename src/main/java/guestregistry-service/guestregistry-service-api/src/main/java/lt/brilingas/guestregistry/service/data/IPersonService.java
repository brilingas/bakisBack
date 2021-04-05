package lt.brilingas.guestregistry.service.data;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import java.util.List;

public interface IPersonService {
    String insertPerson(PersonDTO personDTO) throws FieldNotValidException;
    PersonDTO getPersonById(String personId) throws ResourceNotFoundException;
    List<PersonDTO> getPersonByName(String personName) throws FieldNotValidException;
    List<PersonDTO> getAllPersons() throws Exception;
    void updatePersonById(String personId, PersonDTO personDTO) throws FieldNotValidException, ResourceNotFoundException;
    void deletePersonById(String personId) throws Exception, FieldNotValidException;
    boolean existsById(String personId);
}