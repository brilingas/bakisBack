package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import lt.brilingas.guestregistry.service.impl.validation.impl.*;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventValidator implements IEventValidator {
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private ILocationDAO locationDAO;

    @Override
    public void validateOnCreate(EventDTO event) throws FieldNotValidException {
        fieldValidator.validate(event.getId(), "Id of Event", ObjectCheck.isNull());

        validateOnCreateOnUpdateCommon(event);
    }

    @Override
    public void validateOnUpdate(EventDTO event) throws FieldNotValidException {
        if (event.getId() == null) {
            throw new NullPointerException();
        } else {
            fieldValidator.validate(event.getId(), "Id of Event", StringCheck.matchesPattern("^[a-f0-9]{24}$"));
        }

        validateOnCreateOnUpdateCommon(event);
    }

    @Override
    public void validateOnDelete(EventDTO eventDTO) throws FieldNotValidException {
        //TODO
    }

    private void validateOnCreateOnUpdateCommon(EventDTO event) throws FieldNotValidException {
        if (event.getLocationId() != null) {
            fieldValidator.validate(event.getLocationId(), "LocationId in Event",
                    StringCheck.matchesPattern("^[a-f0-9]{24}$"));
            if(!locationDAO.existsById(event.getLocationId())) {
                throw new FieldNotValidException("Location by ID = " + event.getLocationId() + " not found");
            }
        }
        fieldValidator.validate(event.getEventName(), "EventName in Event", StringCheck.maxLength(100));
        fieldValidator.validate(event.getParticipantsNumber(), "ParticipantsNumber in Event", NumberCheck.min(0));
        fieldValidator.validate(event.getDate(), "Date in Event", ObjectCheck.notNull());
    }
}
