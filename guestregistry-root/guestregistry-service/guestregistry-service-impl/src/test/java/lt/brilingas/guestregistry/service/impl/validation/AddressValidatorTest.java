package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.data.dto.Address;
//import lt.brilingas.guestregistry.service.impl.config.ServiceTestConfig;
import lt.brilingas.guestregistry.service.impl.validation.impl.FieldValidator;
import lt.brilingas.guestregistry.service.impl.validation.impl.StringCheck;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AddressValidatorTest {
    @MockBean
    private FieldValidator fieldValidator;
    @Autowired
    private IAddressValidator addressValidator;

    @Test
    public void validateTest() throws FieldNotValidException {
        Address address = new Address();

        address.setApartmentNumber("123A");
        addressValidator.validateOnCreateOnUpdate(address);
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("Country in Address"), Mockito.any(StringCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("City in Address"), Mockito.any(StringCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("Street in Address"), Mockito.any(StringCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("BuildingNumber in Address"), Mockito.any(StringCheck.class));
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("ApartmentNumber in Address"), Mockito.any(StringCheck.class));

        address.setApartmentNumber(null);
        addressValidator.validateOnCreateOnUpdate(address);
        Mockito.verify(fieldValidator, Mockito.times(1)).validate(Mockito.any(),
                Mockito.eq("ApartmentNumber in Address"), Mockito.any(StringCheck.class));
    }
}
