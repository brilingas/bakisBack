package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.data.dto.Address;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;

public interface IAddressValidator {
    public void validateOnCreateOnUpdate(Address address) throws FieldNotValidException;
}
