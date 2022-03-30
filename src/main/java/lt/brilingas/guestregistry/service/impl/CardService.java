package lt.brilingas.guestregistry.service.impl;
import lt.brilingas.guestregistry.dao.api.ICardDAO;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.data.dto.card.CardType;
import lt.brilingas.guestregistry.service.api.FieldNotValidException;
import lt.brilingas.guestregistry.service.api.ICardService;
import lt.brilingas.guestregistry.service.api.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.impl.validation.ICardValidator;
import lt.brilingas.guestregistry.service.impl.validation.impl.FieldValidator;
import lt.brilingas.guestregistry.service.impl.validation.impl.ObjectCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardService implements ICardService {
    @Autowired
    private ICardDAO cardDAO;
    @Autowired
    private ICardValidator cardValidator;
    @Autowired
    FieldValidator fieldValidator;


    @Override
    public String insertCard(CardDTO cardDTO) throws FieldNotValidException {
        cardValidator.validateOnCreate(cardDTO);
        cardDAO.insert(cardDTO);
        return cardDTO.getId();
    }
    @Override
    public CardDTO getCardById(String cardId) throws FieldNotValidException {
        cardValidator.validateField("id",cardId);
        return cardDAO.getById(cardId).get();
    }
    @Override
    public List<CardDTO> getByType(CardType cardType) throws FieldNotValidException {
        cardValidator.validateField("cardType",cardType.toString());
        return cardDAO.getByType(cardType);
    }
    @Override
    public List<CardDTO> getByLocationId(String locationId) throws FieldNotValidException {
        fieldValidator.validate(locationId, "Id of Location", ObjectCheck.isNull());
        return cardDAO.getByLocationId(locationId);
    }
    @Override
    public List<CardDTO> getFilteredCards(CardType cardType, String locationId) throws FieldNotValidException {
        cardValidator.validateField("cardType",cardType.toString());
        fieldValidator.validate(locationId, "Id of Location", ObjectCheck.isNull());
        List<CardDTO> filteredByType=getByType(cardType);
        List<CardDTO> filteredByTypeAndLocationId = new ArrayList<>();
        for (CardDTO cardDTO : filteredByType) {
            if (cardDTO.getLocationId().equals(locationId)) {
                filteredByTypeAndLocationId.add(cardDTO);
            }
        }
        return filteredByTypeAndLocationId;
    }
    @Override
    public List<CardDTO> getAllCards() {
        return cardDAO.getAll();
    }

    @Override
    public void updateCardById(String cardId, CardDTO cardDTO) throws FieldNotValidException, ResourceNotFoundException {
        cardValidator.validateOnUpdate(cardId,cardDTO);
        cardDAO.update(cardDTO);
    }

    @Override
    public void deleteCardById(String cardId) throws FieldNotValidException {
        cardValidator.validateOnDelete(cardId);
        cardDAO.deleteById(cardId);
    }
}
