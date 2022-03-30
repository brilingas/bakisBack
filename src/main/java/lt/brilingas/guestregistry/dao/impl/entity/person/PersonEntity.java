package lt.brilingas.guestregistry.dao.impl.entity.person;
import lombok.Data;
import lt.brilingas.guestregistry.data.dto.Address;
import lt.brilingas.guestregistry.data.dto.person.Gender;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "person")
public class PersonEntity {
    @Id
    private String id;
    private String name;
    private String surname;
    private Date birthday;
    private String phoneNumber;
    private String email;
    private Byte[] image;
    private Byte[] signature;
    private Address address;
    private Gender gender;
}