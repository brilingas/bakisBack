package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;

public interface ILocationValidator {
    void validateOnCreate(LocationDTO locationDTO) throws FieldNotValidException;
    void validateOnUpdate(LocationDTO locationDTO) throws FieldNotValidException;
    void validateOnDelete(String locationId) throws Exception;
}