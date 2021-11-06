package lt.brilingas.guestregistry.data.dto.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@Getter
@Setter
public class EventDTO {
    public static final Map<String, Class<?>> FIELDS_ALLOWED_IN_FILTER = new HashMap<>(Map.of(
            "locationId", String.class,
            "eventName", String.class,
            "participantsNumber", Integer.class,
            "date", Instant.class));

    private String id;
    private String locationId;
    private String eventName;
    private int participantsNumber;
    private ZonedDateTime date;
}
