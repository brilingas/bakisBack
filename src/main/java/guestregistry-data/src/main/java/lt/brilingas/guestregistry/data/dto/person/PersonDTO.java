package lt.brilingas.guestregistry.data.dto.person;
import lombok.Data;
import lt.brilingas.guestregistry.data.dto.Address;

import javax.validation.constraints.*;
import java.util.Date;
@Data
public class PersonDTO {
    @NotBlank
    @Size(min = 1,max = 5)
    private String id;
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotBlank
    @Size(min = 2)
    private String surname;
    @Past
    @NotNull
    private Date birthday;
    @NotBlank
    private String phoneNumber;
    @Email
    @NotBlank
    private String email;
    @NotNull
    private Byte[] image;
    @NotNull
    private Byte[] signature;
    @NotNull
    private Address address;
    @NotNull
    private Gender gender;
}
