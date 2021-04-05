package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;

public interface IWorkerValidator {
    void validateOnCreate(WorkerDTO workerDTO) throws FieldNotValidException, ResourceNotFoundException;
    void validateOnUpdate(WorkerDTO workerDTO) throws FieldNotValidException, ResourceNotFoundException;
    void validateOnDelete(WorkerDTO workerDTO) throws FieldNotValidException, ResourceNotFoundException;
}
