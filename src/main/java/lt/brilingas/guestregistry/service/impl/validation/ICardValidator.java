package lt.brilingas.guestregistry.service.impl.validation;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;
import lt.brilingas.guestregistry.service.api.ResourceNotFoundException;

public interface ICardValidator {
    void validateField(String fieldName, String fieldValue) throws FieldNotValidException;
    void validateOnCreate(CardDTO cardDTO) throws FieldNotValidException;
    void validateOnUpdate(String cardId, CardDTO cardDTO) throws FieldNotValidException, ResourceNotFoundException;
    void validateOnDelete(String cardId) throws FieldNotValidException;

}
