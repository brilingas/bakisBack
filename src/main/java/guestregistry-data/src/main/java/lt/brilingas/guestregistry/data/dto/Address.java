package lt.brilingas.guestregistry.data.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class Address {
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;//?
}
