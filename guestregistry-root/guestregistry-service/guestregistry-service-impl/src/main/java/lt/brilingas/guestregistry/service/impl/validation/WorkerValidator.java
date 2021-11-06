package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.dao.api.ICardDAO;
import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.dao.api.IPersonDAO;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.impl.validation.impl.FieldValidator;
import lt.brilingas.guestregistry.service.impl.validation.impl.ObjectCheck;
import lt.brilingas.guestregistry.service.impl.validation.impl.StringCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerValidator implements IWorkerValidator {
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private ILocationDAO locationDAO;
    @Autowired
    private IPersonDAO personDAO;
    @Autowired
    private ICardDAO cardDAO;

    @Override
    public void validateOnCreate(WorkerDTO worker) throws FieldNotValidException, ResourceNotFoundException {
        fieldValidator.validate(worker.getId(), "Id of Worker", ObjectCheck.isNull());
        validateOnCreateOnUpdateCommon(worker);
    }

    @Override
    public void validateOnUpdate(WorkerDTO worker) throws FieldNotValidException, ResourceNotFoundException {
        fieldValidator.validate(worker.getId(), "Id of Worker", StringCheck.matchesPattern("^[a-f0-9]{24}$"));
        validateOnCreateOnUpdateCommon(worker);
    }

    @Override
    public void validateOnDelete(WorkerDTO workerDTO) throws FieldNotValidException, ResourceNotFoundException {
        //TODO
    }

    private void validateOnCreateOnUpdateCommon(WorkerDTO worker) throws FieldNotValidException, ResourceNotFoundException {
        fieldValidator.validate(worker.getCompany(), "Company in Worker", StringCheck.maxLength(100));
        fieldValidator.validate(worker.getDepartment(), "Department in Worker", StringCheck.maxLength(100));
        fieldValidator.validate(worker.getPosition(), "Position in Worker", StringCheck.maxLength(100));
        fieldValidator.validate(worker.getDateOfEmployment(), "DateOfEmployment in Worker", ObjectCheck.notNull());
        fieldValidator.validate(worker.getWorkerType(), "WorkerType in Worker", ObjectCheck.notNull());
        String personId = worker.getPersonId();
        fieldValidator.validate(personId, "PersonId in Worker",
                StringCheck.matchesPattern("^[a-f0-9]{24}$"));
        System.out.println("PERSON ID OF WORKER I SEND: "+personId);
        if (personDAO.getById(personId).isEmpty()) {
            throw new ResourceNotFoundException("Person by ID = " + personId + " not found");//error comes from here
        }
        String locationOfOfficeId = worker.getLocationOfOfficeId();
        fieldValidator.validate(locationOfOfficeId, "LocationOfOfficeId in Worker",
                StringCheck.matchesPattern("^[a-f0-9]{24}$"));
        if (!locationDAO.existsById(locationOfOfficeId)) {
            throw new ResourceNotFoundException("Location by ID = " + locationOfOfficeId + " not found");
        }
        String cardId = worker.getCardId();
        if (cardId != null) {
            fieldValidator.validate(cardId, "CardId in Worker",
                    StringCheck.matchesPattern("^[a-f0-9]{24}$"));
            if (cardDAO.getById(cardId).isEmpty()) {
                throw new ResourceNotFoundException("Card by ID = " + cardId + " not found");
            }
        }
    }
}