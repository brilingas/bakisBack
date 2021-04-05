package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.dao.api.IWorkerDAO;
import lt.brilingas.guestregistry.data.dto.Address;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.impl.validation.impl.FieldValidator;
import lt.brilingas.guestregistry.service.impl.validation.impl.ObjectCheck;
import lt.brilingas.guestregistry.service.impl.validation.impl.StringCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class PersonValidator implements IPersonValidator {
    @Autowired
    private IAddressValidator addressValidator;
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private IWorkerDAO workerDAO;

    @Override
    public void validateOnCreate(PersonDTO personDTO) throws FieldNotValidException {
        fieldValidator.validate(personDTO.getId(), "Id of Person", ObjectCheck.isNull());
        validateOnCreateOnUpdateCommon(personDTO);
    }

    @Override
    public void validateOnUpdate(PersonDTO personDTO) throws FieldNotValidException {
        if (personDTO.getId()==null){
            throw new NullPointerException();
        } else{
            fieldValidator.validate(personDTO.getId(), "Id of Person", StringCheck.matchesPattern("^[a-f0-9]{24}$"));
        }
        validateOnCreateOnUpdateCommon(personDTO);
    }

    private void validateOnCreateOnUpdateCommon(PersonDTO personDTO) throws FieldNotValidException {
        fieldValidator.validate(personDTO.getName(), "Name in Person", StringCheck.maxLength(100));
        fieldValidator.validate(personDTO.getSurname(), "Surname in Person", StringCheck.maxLength(100));
        fieldValidator.validate(personDTO.getBirthday(), "Birthday in Person", ObjectCheck.notNull());
        fieldValidator.validate(personDTO.getPhoneNumber(), "PhoneNumber in Person", StringCheck.matchesPattern("^[+]"));//check pattern

        fieldValidator.validate(personDTO.getEmail(), "Email in Person", StringCheck.maxLength(100));
        fieldValidator.validate(personDTO.getPhoto(), "Photo in Person", ObjectCheck.notNull());
        fieldValidator.validate(personDTO.getSignature(), "Signature in Person", ObjectCheck.notNull());
        fieldValidator.validate(personDTO.getAddress(), "Address in Person", ObjectCheck.notNull());
        fieldValidator.validate(personDTO.getGender(), "Gender in Person", ObjectCheck.notNull());
        Address PersonAddress = personDTO.getAddress();
        addressValidator.validateOnCreateOnUpdate(PersonAddress);
    }

    @Override
    public void validateOnDelete(String personId) throws Exception {

    }
}
