package lt.brilingas.guestregistry.service.api;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.data.dto.card.CardType;

import java.util.List;

public interface ICardService {
    String insertCard(CardDTO cardDTO) throws FieldNotValidException;
    CardDTO getCardById(String cardId) throws FieldNotValidException;
    List<CardDTO> getByType(CardType cardType) throws FieldNotValidException;
    List<CardDTO> getByLocationId(String locationId) throws FieldNotValidException;
    List<CardDTO> getAllCards();
    void updateCardById(String cardId, CardDTO cardDTO) throws FieldNotValidException, ResourceNotFoundException;
    void deleteCardById(String cardId) throws ResourceNotFoundException, FieldNotValidException;
    List<CardDTO> getFilteredCards(CardType cardType, String locationId) throws FieldNotValidException;//ar pateikti cardDto vietoj cardType?
}
