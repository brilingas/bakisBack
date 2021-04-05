package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import javax.validation.Validator;

public interface IPersonValidator {
    Validator initializeValidator();
    void validateField(String field) throws FieldNotValidException;
    void validateOnCreate(PersonDTO personDTO) throws FieldNotValidException;
    void validateOnUpdate(String personId, PersonDTO personDTO) throws FieldNotValidException;
    void validateOnDelete(String personId) throws FieldNotValidException;
}