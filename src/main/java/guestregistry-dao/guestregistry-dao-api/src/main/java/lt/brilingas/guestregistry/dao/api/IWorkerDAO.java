package lt.brilingas.guestregistry.dao.api;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IWorkerDAO {
    String insert(WorkerDTO worker);

    void update(WorkerDTO worker);

    void deleteById(String workerId);

    Optional<WorkerDTO> findById(String workerId);

    List<WorkerDTO> findAll();

    List<WorkerDTO> findByFilter(Map<String, Map<QueryParameterFunction, String>> parameters) throws Exception;

    boolean existsById(String workerId);
}
