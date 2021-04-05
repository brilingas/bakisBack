package lt.brilingas.guestregistry.data.dto.person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lt.brilingas.guestregistry.data.dto.Address;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@Data
public class PersonDTO {
    public static final Map<String, Class<?>> FIELDS_ALLOWED_IN_FILTER = new HashMap<>(Map.of(//TODO
            "name", String.class,
            "surname", String.class,
            "birthday", Enum.class,
            "phoneNumber", Enum.class,
            "email", String.class,
            "address.country", String.class,
            "address.city", String.class,
            "address.street", String.class,
            "address.buildingNumber", String.class,
            "address.apartmentNumber", String.class));
//    @NotBlank
//    @Size(min = 1,max = 5)
    private String id;
//    @NotBlank
//    @Size(min = 3)
    private String name;
//    @NotBlank
//    @Size(min = 2)
    private String surname;
//    @Past
//    @NotNull
    private Date birthday;
//    @NotBlank
    private String phoneNumber;
//    @Email
//    @NotBlank
    private String email;
//    @NotNull
    private Byte[] photo;
//    @NotNull
    private Byte[] signature;
//    @NotNull
    private Address address;
//    @NotNull
    private Gender gender;
}
