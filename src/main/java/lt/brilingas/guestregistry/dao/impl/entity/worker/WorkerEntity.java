package lt.brilingas.guestregistry.dao.impl.entity.worker;
import lombok.Getter;
import lombok.Setter;
import lt.brilingas.guestregistry.data.dto.worker.WorkerType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Getter
@Setter
@Document(collection = "worker")
public class WorkerEntity {
    @Id
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
