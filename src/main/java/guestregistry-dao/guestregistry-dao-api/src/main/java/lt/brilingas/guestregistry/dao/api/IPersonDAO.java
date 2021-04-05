package lt.brilingas.guestregistry.dao.api;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPersonDAO {
    String insert(PersonDTO personDTO);
    void update(PersonDTO personDTO);
    void deleteById(String personId);
    Optional<PersonDTO> getById(String personId);
    List<PersonDTO> getByName(String personName);//?
    List<PersonDTO> getAll();
    List<PersonDTO> getByFilter(Map<String, Map<QueryParameterFunction, String>> parameters) throws Exception;
    boolean existsById(String personId);

}