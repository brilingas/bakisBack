package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.service.impl.config.ServiceTestConfig;
import lt.brilingas.guestregistry.service.impl.validation.impl.FieldValidator;
import lt.brilingas.guestregistry.service.impl.validation.impl.ObjectCheck;
import lt.brilingas.guestregistry.service.impl.validation.impl.StringCheck;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {ServiceTestConfig.class})
public class LocationValidatorTest {
    @MockBean
    private FieldValidator fieldValidator;
    @MockBean
    private IAddressValidator addressValidator;
    @Autowired
    private ILocationValidator locationValidator;

    @Test
    public void validateOnCreateTest() throws FieldNotValidException {
        LocationDTO location = new LocationDTO();

        locationValidator.validateOnCreate(location);
        Mockito.verify(fieldValidator, Mockito.times(1)).validate((Object) Mockito.any(),
                Mockito.eq("Id of Location"), Mockito.any(ObjectCheck.class));
        validateOnCreateOnUpdateCommonTest(location);
    }

    @Test
    public void validateOnUpdateTest() throws FieldNotValidException {
        LocationDTO location = new LocationDTO();

        location.setId("0123456789abcdef01234567");
        locationValidator.validateOnUpdate(location);
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.eq(location.getId()),
                Mockito.eq("Id of Location"), Mockito.any(StringCheck.class));
        validateOnCreateOnUpdateCommonTest(location);

        location.setId(null);
        Assertions.assertThrows(NullPointerException.class, () -> locationValidator.validateOnUpdate(location));
    }

    private void validateOnCreateOnUpdateCommonTest(LocationDTO location) throws FieldNotValidException {
        Mockito.verify(fieldValidator, Mockito.times(1)).validate((Object) Mockito.any(),
                Mockito.eq("Address in Location"), Mockito.any(ObjectCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate((Object) Mockito.any(),
                Mockito.eq("Status in Location"), Mockito.any(ObjectCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate((Object) Mockito.any(),
                Mockito.eq("Type in Location"), Mockito.any(ObjectCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("Name in Location"), Mockito.any(StringCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("ContactPerson in Location"), Mockito.any(StringCheck.class));

        Mockito.verify(addressValidator, Mockito.times(1)).
                validateOnCreateOnUpdate(location.getAddress());
    }
}
