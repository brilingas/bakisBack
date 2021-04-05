package lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.worker.WorkerEntity;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
import java.util.LinkedList;
import java.util.List;

public class WorkerMapper {
    public static WorkerDTO toDTO(WorkerEntity workerEntity) {
        WorkerDTO workerDTO = new WorkerDTO();
        workerDTO.setId(workerEntity.getId());
        workerDTO.setPersonId(workerEntity.getPersonId());
        workerDTO.setCompany(workerEntity.getCompany());
        workerDTO.setDepartment(workerEntity.getDepartment());
        workerDTO.setPosition(workerEntity.getPosition());
        workerDTO.setDateOfEmployment(workerEntity.getDateOfEmployment());
        workerDTO.setLocationOfOfficeId(workerEntity.getLocationOfOfficeId());
        workerDTO.setCardId(workerEntity.getCardId());
        workerDTO.setWorkerType(workerEntity.getWorkerType());
        return workerDTO;
    }

    public static WorkerEntity toEntity(WorkerDTO workerDTO) {
        WorkerEntity workerEntity = new WorkerEntity();
        workerEntity.setId(workerDTO.getId());
        workerEntity.setPersonId(workerDTO.getPersonId());
        workerEntity.setCompany(workerDTO.getCompany());
        workerEntity.setDepartment(workerDTO.getDepartment());
        workerEntity.setPosition(workerDTO.getPosition());
        workerEntity.setDateOfEmployment(workerDTO.getDateOfEmployment());
        workerEntity.setLocationOfOfficeId(workerDTO.getLocationOfOfficeId());
        workerEntity.setCardId(workerDTO.getCardId());
        workerEntity.setWorkerType(workerDTO.getWorkerType());
        return workerEntity;
    }

    public static List<WorkerDTO> toDTOLinkedList(List<WorkerEntity> listEntity) {
        List<WorkerDTO> listDTO = new LinkedList<>();
        for(WorkerEntity workerEntity : listEntity) {
            listDTO.add(toDTO(workerEntity));
        }
        return listDTO;
    }
}
