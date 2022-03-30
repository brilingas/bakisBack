package lt.brilingas.guestregistry.data.dto.worker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@Getter
@Setter
public class WorkerDTO {
    public static final Map<String, Class<?>> FIELDS_ALLOWED_IN_FILTER = new HashMap<>(Map.of(
            "personId", String.class,
            "company", String.class,
            "department", String.class,
            "position", String.class,
            "dateOfEmployment", Instant.class,
            "locationOfOfficeId", String.class,
            "cardId", String.class,
            "workerType", Enum.class));

    private String id;
    private String personId;
    private String company;
    private String department;
    private String position;
    private Instant dateOfEmployment;
    private String locationOfOfficeId;
    private String cardId;
    private WorkerType workerType;
}
