package lt.brilingas.guestregistry.service.data;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import java.util.List;

public interface IPersonService {
    String insertPerson(PersonDTO personDTO) throws FieldNotValidException;
    PersonDTO getPersonById(String personId) throws FieldNotValidException;
    List<PersonDTO> getPersonByName(String personName) throws FieldNotValidException;
    List<PersonDTO> getAllPersons();
    void updatePersonById(String personId, PersonDTO personDTO) throws ResourceNotFoundException, FieldNotValidException;
    void deletePersonById(String personId) throws ResourceNotFoundException, FieldNotValidException;

}
