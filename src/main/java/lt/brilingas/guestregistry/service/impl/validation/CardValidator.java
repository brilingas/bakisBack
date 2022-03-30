package lt.brilingas.guestregistry.service.impl.validation;

import lt.brilingas.guestregistry.dao.impl.repository.ICardRepository;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;
import lt.brilingas.guestregistry.service.api.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class CardValidator implements ICardValidator {
    @Autowired
    private ICardRepository cardRepository;

    private Validator validator;
    Set<ConstraintViolation<CardDTO>> violations;

    public CardValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void validateField(String fieldName, String fieldValue) throws FieldNotValidException {
        violations = validator.validateValue(CardDTO.class, fieldName, fieldValue);
        for (ConstraintViolation<CardDTO> violation : violations) {
            throw new FieldNotValidException(violation.getMessage());
        }
    }
    @Override
    public void validateOnCreate(CardDTO cardDTO) throws FieldNotValidException {
        violations = validator.validate(cardDTO);
        if (!violations.isEmpty()) {
            throw new FieldNotValidException();
        }
    }
    @Override
    public void validateOnUpdate(String cardId, CardDTO cardDTO) throws FieldNotValidException, ResourceNotFoundException {
        if (cardRepository.existsById(cardId)){
            violations = validator.validate(cardDTO);
            if (!violations.isEmpty()) {
                for (ConstraintViolation<CardDTO> violation : violations) {
                    throw new FieldNotValidException(violation.getMessage());
                }
            }
        } else {
            throw new ResourceNotFoundException("Card Id for update does not exist");
        }
    }
    @Override
    public void validateOnDelete(String cardId) throws FieldNotValidException {
        if (!cardRepository.existsById(cardId)) {
            throw new FieldNotValidException("Card Id for delete does not exist");
        }
    }
}