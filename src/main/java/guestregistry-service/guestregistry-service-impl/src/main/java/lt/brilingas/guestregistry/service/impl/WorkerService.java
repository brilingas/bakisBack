package lt.brilingas.guestregistry.service.impl;
import lt.brilingas.guestregistry.dao.api.IWorkerDAO;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.data.dto.worker.LoginStatus;
import lt.brilingas.guestregistry.data.dto.worker.SignupStatus;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
import lt.brilingas.guestregistry.service.data.*;
import lt.brilingas.guestregistry.service.impl.validation.IQueryParametersCreator;
import lt.brilingas.guestregistry.service.impl.validation.IWorkerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkerService implements IWorkerService {
    @Autowired
    private IWorkerDAO workerDAO;
    @Autowired
    private IWorkerValidator workerValidator;
    @Autowired
    private IQueryParametersCreator queryParametersCreator;
    @Autowired
    private ICardService cardService;

    @Override
    public SignupStatus signup(WorkerDTO workerDTO) throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException {
        return null;
    }

    @Override
    public LoginStatus login(WorkerDTO workerDTO) throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException {
        List<WorkerDTO> workers = workerDAO.findAll();
        for (WorkerDTO worker:workers){
            if(workerDTO.getId().equals(worker.getId()) && workerDTO.getCardId().equals(worker.getCardId())){
                return LoginStatus.SUCCESS;
            }
        }
        return LoginStatus.FAILURE;
    }

    @Override
    public String insertWorker(WorkerDTO workerDTO)
            throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException {
        if (workerDTO == null) throw new IllegalArgumentException();
        workerValidator.validateOnCreate(workerDTO);
        String cardId = workerDTO.getCardId();
        if (cardId != null) {
            setCardAvailabilityFalse(cardId);
        }
        return workerDAO.insert(workerDTO);
    }

    @Override
    public void updateWorkerById(String workerId, WorkerDTO workerDTO)
            throws FieldNotValidException, ResourceNotFoundException, DTOReferenceException {
        if (workerDTO == null || workerId == null) throw new IllegalArgumentException();
        workerDTO.setId(workerId);
        workerValidator.validateOnUpdate(workerDTO);
        if (workerDAO.existsById(workerId)) {
            String cardId = workerDTO.getCardId();
            if (cardId != null) {
                setCardAvailabilityFalse(cardId);
            }

            WorkerDTO worker = getWorkerById(workerId);
            String oldAssignedCardId = worker.getCardId();
            if (oldAssignedCardId != null) {
                setCardAvailabilityTrue(oldAssignedCardId);
            }

            workerDAO.update(workerDTO);
        } else {
            throw new ResourceNotFoundException("Worker by ID = " + workerId + " not found" );
        }
    }

    @Override
    public void deleteWorkerById(String workerId) throws ResourceNotFoundException, FieldNotValidException {
        if (workerId == null) throw new IllegalArgumentException();
        Optional<WorkerDTO> workerOptional = workerDAO.findById(workerId);
        if (workerOptional.isPresent()) {
            String cardId = workerOptional.get().getCardId();
            if (cardId != null) {
                setCardAvailabilityTrue(cardId);
            }
            workerDAO.deleteById(workerId);
        }
    }

    @Override
    public WorkerDTO getWorkerById(String workerId) throws ResourceNotFoundException {
        if (workerId == null) throw new IllegalArgumentException();
        Optional<WorkerDTO> workerOptional = workerDAO.findById(workerId);
        if (workerOptional.isEmpty()) {
            throw new ResourceNotFoundException("Worker by ID = " + workerId + " not found" );
        } else {
            return workerOptional.get();
        }
    }

    @Override
    public List<WorkerDTO> getAllWorkers(Map<String, String> parameters) throws Exception {
        if (parameters == null) throw new IllegalArgumentException();
        if (parameters.isEmpty()) {
            return workerDAO.findAll();
        } else {
            return workerDAO.findByFilter(queryParametersCreator.create(parameters, WorkerDTO.FIELDS_ALLOWED_IN_FILTER));
        }
    }

    @Override
    public boolean existsById(String workerId) {
        if (workerId == null) throw new IllegalArgumentException();
        return workerDAO.existsById(workerId);
    }

    private void setCardAvailabilityFalse(String cardId)
            throws FieldNotValidException, ResourceNotFoundException, DTOReferenceException {
        CardDTO card = cardService.getCardById(cardId);
        if (card.isCardAvailability()) {
            card.setCardAvailability(false);
            cardService.updateCardById(cardId, card);
        } else {
            throw new DTOReferenceException("Card by ID " + cardId + " is not free" );
        }
    }

    private void setCardAvailabilityTrue(String cardId) throws FieldNotValidException, ResourceNotFoundException {
        CardDTO card = cardService.getCardById(cardId);
        card.setCardAvailability(true);
        cardService.updateCardById(cardId, card);
    }
}
