package lt.brilingas.guestregistry.dao.impl.entity.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Getter
@Setter
@Document(collection = "event")
public class EventEntity {
    @Id
    private String id;
    private String locationId;
    private String eventName;
    private int participantsNumber;
    private Instant date;
}
