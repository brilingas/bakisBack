package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
//import lt.brilingas.guestregistry.service.impl.config.ServiceTestConfig;
import lt.brilingas.guestregistry.service.impl.validation.impl.*;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class EventValidatorTest {
    @MockBean
    private FieldValidator fieldValidator;
    @Autowired  //MockBean
    private ILocationDAO locationDAO;
    @Autowired
    private IEventValidator eventValidator;
    private final String ID = "0123456789abcdef01234567";

    @FunctionalInterface
    private interface ConsumerEx<T> {
        void accept(T t) throws FieldNotValidException;
    }

    @Test
    public void validateOnCreateTest() throws FieldNotValidException {
        EventDTO event = new EventDTO();
        event.setLocationId(ID);
        Mockito.when(locationDAO.existsById(ID)).thenReturn(true);
        ConsumerEx<EventDTO> validator = eventValidator::validateOnCreate;

        eventValidator.validateOnCreate(event);
        Mockito.verify(fieldValidator, Mockito.times(1)).validate((Object) Mockito.eq(event.getId()),
                Mockito.eq("Id of Event"), Mockito.any(ObjectCheck.class));
        validateOnCreateOnUpdateCommonTest(event, validator);
    }

    @Test
    public void validateOnUpdateTest() throws FieldNotValidException {
        EventDTO event = new EventDTO();
        event.setLocationId(ID);
        Mockito.when(locationDAO.existsById(ID)).thenReturn(true);
        ConsumerEx<EventDTO> validator = eventValidator::validateOnUpdate;

        event.setId(ID);
        eventValidator.validateOnUpdate(event);
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.eq(event.getId()),
                Mockito.eq("Id of Event"), Mockito.any(StringCheck.class));
        validateOnCreateOnUpdateCommonTest(event, validator);

        event.setId(null);
        Assertions.assertThrows(NullPointerException.class, () -> eventValidator.validateOnUpdate(event));
    }

    private void validateOnCreateOnUpdateCommonTest(EventDTO event, ConsumerEx<EventDTO> validator)
            throws FieldNotValidException {
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("EventName in Event"), Mockito.any(StringCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("ParticipantsNumber in Event"), Mockito.any(NumberCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate((Object) Mockito.any(),
                Mockito.eq("Date in Event"), Mockito.any(ObjectCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.eq(event.getLocationId()),
                Mockito.eq("LocationId in Event"), Mockito.any(StringCheck.class));

        Mockito.when(locationDAO.existsById(ID)).thenReturn(false);
        Throwable exception = Assertions.assertThrows(FieldNotValidException.class, () -> validator.accept(event));
        Assertions.assertEquals("Location by ID = " + ID + " not found", exception.getMessage());

        event.setLocationId(null);
        validator.accept(event);
        Mockito.verify(fieldValidator, Mockito.times(2)).validate(Mockito.any(),
                Mockito.eq("LocationId in Event"), Mockito.any(StringCheck.class));
    }
}
