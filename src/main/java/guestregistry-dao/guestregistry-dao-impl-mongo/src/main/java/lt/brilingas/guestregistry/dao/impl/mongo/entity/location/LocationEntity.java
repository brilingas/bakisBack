package lt.brilingas.guestregistry.dao.impl.mongo.entity.location;
import lombok.Data;
import lt.brilingas.guestregistry.data.dto.Address;
import lt.brilingas.guestregistry.data.dto.location.Status;
import lt.brilingas.guestregistry.data.dto.location.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "location")
public class LocationEntity {
    @Id
    private String id;
    private Address address;
    private String name;
    private String contactPerson;
    private Status status;
    private Type type;
}
