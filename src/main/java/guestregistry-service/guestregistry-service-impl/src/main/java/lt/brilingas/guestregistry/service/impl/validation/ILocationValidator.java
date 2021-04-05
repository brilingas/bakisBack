package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;

public interface ILocationValidator {
    public void validateOnCreate(LocationDTO locationDTO) throws FieldNotValidException;

    public void validateOnUpdate(LocationDTO locationDTO) throws FieldNotValidException;

    public void validateOnDelete(String locationId) throws Exception;
}
