package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;

public interface IEventValidator {
    void validateOnCreate(EventDTO eventDTO) throws FieldNotValidException;
    void validateOnUpdate(EventDTO eventDTO) throws FieldNotValidException;
    void validateOnDelete(EventDTO eventDTO) throws FieldNotValidException;
}
