package lt.brilingas.guestregistry.dao.api;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.data.dto.card.CardType;

import java.util.List;
import java.util.Optional;

public interface ICardDAO {
    String insert(CardDTO cardDto);
    Optional<CardDTO> getById(String cardId);
    List<CardDTO> getByType(CardType cardType);
    List<CardDTO> getByLocationId(String locationId);
    List<CardDTO> getAll();
    void update(CardDTO cardDto);
    void deleteById(String cardId);

}