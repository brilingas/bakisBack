package lt.brilingas.guestregistry.dao.api;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Component
public interface IWorkerDAO {
    String insert(WorkerDTO workerDTO);
    void update(WorkerDTO workerDTO);
    void deleteById(String workerId);
    Optional<WorkerDTO> getById(String workerId);
    List<WorkerDTO> getAll();
    List<WorkerDTO> getByFilter(Map<String, Map<QueryParameterFunction, String>> parameters) throws Exception;
    boolean existsById(String workerId);
}
