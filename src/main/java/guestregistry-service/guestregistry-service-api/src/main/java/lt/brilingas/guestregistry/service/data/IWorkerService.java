package lt.brilingas.guestregistry.service.data;
import lt.brilingas.guestregistry.data.dto.worker.LoginStatus;
import lt.brilingas.guestregistry.data.dto.worker.SignupStatus;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;

import java.util.List;
import java.util.Map;

public interface IWorkerService {

    SignupStatus signup(WorkerDTO workerDTO)throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException;

    LoginStatus login(WorkerDTO workerDTO)throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException;

    String insertWorker(WorkerDTO workerDTO)
            throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException;

    void updateWorkerById(String workerId, WorkerDTO workerDTO)
            throws FieldNotValidException, ResourceNotFoundException, DTOReferenceException;

    void deleteWorkerById(String workerId) throws ResourceNotFoundException, FieldNotValidException;

    WorkerDTO getWorkerById(String workerId) throws ResourceNotFoundException;

    List<WorkerDTO> getAllWorkers(Map<String, String> parameters) throws Exception;

    boolean existsById(String workerId);
}
