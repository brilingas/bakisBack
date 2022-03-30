package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;

public interface IPersonValidator {
    void validateOnCreate(PersonDTO personDTO) throws FieldNotValidException;
    void validateOnUpdate(PersonDTO personDTO) throws FieldNotValidException;
    void validateOnDelete(String personId) throws Exception;
}