package lt.brilingas.guestregistry.dao.api;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;

import java.util.List;
import java.util.Optional;

public interface IPersonDAO {
    String insert(PersonDTO personDTO);
    Optional<PersonDTO> getById(String personId);
    List<PersonDTO> getByName(String personName);
    List<PersonDTO> getAll();
    void update(PersonDTO personDTO);
    void deleteById(String personId);
}