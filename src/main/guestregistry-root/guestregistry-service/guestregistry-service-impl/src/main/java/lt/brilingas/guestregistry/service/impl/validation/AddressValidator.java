package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.data.dto.Address;
import lt.brilingas.guestregistry.service.impl.validation.impl.FieldValidator;
import lt.brilingas.guestregistry.service.impl.validation.impl.StringCheck;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressValidator implements IAddressValidator {
    @Autowired
    private FieldValidator fieldValidator;

    public void validateOnCreateOnUpdate(Address address) throws FieldNotValidException {
        fieldValidator.validate(address.getCountry(), "Country in Address", StringCheck.maxLength(100));
        fieldValidator.validate(address.getCity(), "City in Address", StringCheck.maxLength(100));
        fieldValidator.validate(address.getStreet(), "Street in Address", StringCheck.maxLength(100));
        fieldValidator.validate(address.getBuildingNumber(), "BuildingNumber in Address",
                StringCheck.matchesPattern("^[a-zA-Z0-9]{1,50}$"));
        if (address.getApartmentNumber() != null) {
            fieldValidator.validate(address.getApartmentNumber(), "ApartmentNumber in Address",
                    StringCheck.matchesPattern("^[a-zA-Z0-9]{1,50}$"));
        }
    }
}
