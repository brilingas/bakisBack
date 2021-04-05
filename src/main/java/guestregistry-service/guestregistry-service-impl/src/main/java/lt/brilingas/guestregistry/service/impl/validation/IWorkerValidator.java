package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;

public interface IWorkerValidator {
    public void validateOnCreate(WorkerDTO workerDTO) throws FieldNotValidException, ResourceNotFoundException;

    public void validateOnUpdate(WorkerDTO workerDTO) throws FieldNotValidException, ResourceNotFoundException;
}
