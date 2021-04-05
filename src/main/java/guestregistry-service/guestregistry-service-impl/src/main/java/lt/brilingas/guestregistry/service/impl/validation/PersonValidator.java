package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class PersonValidator implements IPersonValidator {

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    @Override
    public Validator initializeValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }

    @Override
    public void validateField(String field) throws FieldNotValidException {
        Set<ConstraintViolation<PersonDTO>> violations = initializeValidator().validateProperty(new PersonDTO(),field); //right here
        if (violations.isEmpty()) {
            return;
        } else {
            throw new FieldNotValidException();
        }
    }

    @Override
    public void validateOnCreate(PersonDTO personDTO) throws FieldNotValidException {
        Set<ConstraintViolation<PersonDTO>> violations = initializeValidator().validate(personDTO);
        if (violations.isEmpty()) {
            return;
        } else {
            System.out.println(violations);
            throw new FieldNotValidException();
        }
    }

    @Override
    public void validateOnUpdate(String personId, PersonDTO personDTO) throws FieldNotValidException {
        validateField(personId);
        Set<ConstraintViolation<PersonDTO>> violations = initializeValidator().validate(personDTO);
        if (violations.isEmpty()) {
            return;
        } else {
            throw new FieldNotValidException();
        }
    }

    @Override
    public void validateOnDelete(String personId) throws FieldNotValidException {
        validateField(personId);
    }
}
