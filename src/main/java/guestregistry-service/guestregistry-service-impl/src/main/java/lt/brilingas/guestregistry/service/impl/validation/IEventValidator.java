package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;

public interface IEventValidator {
    public void validateOnCreate(EventDTO eventDTO) throws FieldNotValidException;

    public void validateOnUpdate(EventDTO eventDTO) throws FieldNotValidException;
}
