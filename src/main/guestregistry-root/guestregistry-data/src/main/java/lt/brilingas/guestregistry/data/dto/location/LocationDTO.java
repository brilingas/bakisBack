package lt.brilingas.guestregistry.data.dto.location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lt.brilingas.guestregistry.data.dto.Address;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@Data
public class LocationDTO {
    public static final Map<String, Class<?>> FIELDS_ALLOWED_IN_FILTER = new HashMap<>(Map.of(
            "name", String.class,
            "contactPerson", String.class,
            "status", Enum.class,
            "type", Enum.class,
            "address.country", String.class,
            "address.city", String.class,
            "address.street", String.class,
            "address.buildingNumber", String.class,
            "address.apartmentNumber", String.class));

    private String id;
    private Address address;
    private String name;
    private String contactPerson;
    private Status status;
    private Type type;
}
