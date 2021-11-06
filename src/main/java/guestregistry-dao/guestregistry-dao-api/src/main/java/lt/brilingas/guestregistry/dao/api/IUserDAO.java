package lt.brilingas.guestregistry.dao.api;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public interface IUserDAO extends IPersonDAO{
    @Override
    default String insert(PersonDTO personDTO) {
        return null;
    }
    @Override
    default Optional<PersonDTO> getById(String personId) {
        return Optional.empty();
    }
    @Override
    default List<PersonDTO> getByName(String personName) {
        return null;
    }
    @Override
    default List<PersonDTO> getAll() {
        return null;
    }
    @Override
    default void update(PersonDTO personDTO) {
    }
    @Override
    default void deleteById(String personId) {
    }
}
