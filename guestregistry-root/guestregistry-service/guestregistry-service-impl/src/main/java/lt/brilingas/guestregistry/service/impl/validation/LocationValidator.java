package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.dao.api.IEventDAO;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.data.dto.Address;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import lt.brilingas.guestregistry.service.impl.validation.impl.FieldValidator;
import lt.brilingas.guestregistry.service.impl.validation.impl.ObjectCheck;
import lt.brilingas.guestregistry.service.impl.validation.impl.StringCheck;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class LocationValidator implements ILocationValidator {
    @Autowired
    private IAddressValidator addressValidator;
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private IEventDAO eventDAO;

    @Override
    public void validateOnCreate(LocationDTO locationDTO) throws FieldNotValidException {
        fieldValidator.validate(locationDTO.getId(), "Id of Location", ObjectCheck.isNull());
        validateOnCreateOnUpdateCommon(locationDTO);
    }

    @Override
    public void validateOnUpdate(LocationDTO locationDTO) throws FieldNotValidException {
        if (locationDTO.getId() == null) {
            throw new NullPointerException();
        } else {
            fieldValidator.validate(locationDTO.getId(), "Id of Location",
                    StringCheck.matchesPattern("^[a-f0-9]{24}$"));
        }
        validateOnCreateOnUpdateCommon(locationDTO);
    }

    private void validateOnCreateOnUpdateCommon(LocationDTO locationDTO) throws FieldNotValidException {
        fieldValidator.validate(locationDTO.getAddress(), "Address in Location", ObjectCheck.notNull());
        fieldValidator.validate(locationDTO.getStatus(), "Status in Location", ObjectCheck.notNull());
        fieldValidator.validate(locationDTO.getType(), "Type in Location", ObjectCheck.notNull());
        fieldValidator.validate(locationDTO.getName(), "Name in Location", StringCheck.maxLength(100));
        fieldValidator.validate(locationDTO.getContactPerson(), "ContactPerson in Location",
                StringCheck.maxLength(100));
        Address locationAddress = locationDTO.getAddress();
        addressValidator.validateOnCreateOnUpdate(locationAddress);
    }

    @Override
    public void validateOnDelete(String locationId) throws Exception {
        List<EventDTO> eventsWithThisLocation = eventDAO.findByFilter(
                Collections.singletonMap("locationId", Collections.singletonMap(QueryParameterFunction.EQUALS, locationId)));
        if (!eventsWithThisLocation.isEmpty()) {
            throw new FieldNotValidException("Location by ID = " + locationId +
                    " cannot be deleted (there are Events with a reference to it)");
        }
    }
}
