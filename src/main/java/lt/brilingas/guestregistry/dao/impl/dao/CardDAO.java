package lt.brilingas.guestregistry.dao.impl.dao;
import lt.brilingas.guestregistry.dao.api.ICardDAO;
import lt.brilingas.guestregistry.dao.impl.mapper.CardMapper;
import lt.brilingas.guestregistry.dao.impl.repository.ICardRepository;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.data.dto.card.CardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CardDAO implements ICardDAO {
    @Autowired
    private ICardRepository cardRepository;

    @Override
    public String insert(CardDTO cardDto) {
        if (cardDto == null) {
            throw new IllegalArgumentException();
        } else {
            cardRepository.save(CardMapper.toEntity(cardDto));
            return cardDto.getId();
        }
    }

    @Override
    public Optional<CardDTO> getById(String cardId) {
        return cardRepository.findById(cardId).map(CardMapper::toDTO);
    }

    @Override
    public List<CardDTO> getByType(CardType cardType) {
        List<CardDTO>  cardDTOList= cardRepository.getByCardType(cardType).stream().map(CardMapper::toDTO).collect(Collectors.toList());
        return cardDTOList;
    }

    @Override
    public List<CardDTO> getByLocationId(String locationId) {
        List<CardDTO>  cardDTOList= cardRepository.getByLocationId(locationId).stream().map(CardMapper::toDTO).collect(Collectors.toList());
        return cardDTOList;
    }

    @Override
    public List<CardDTO> getAll() {
        List<CardDTO> listOfCardDTO = cardRepository.findAll().stream().map(CardMapper::toDTO).collect(Collectors.toList());
        return listOfCardDTO;
    }
    @Override
    public void update(CardDTO cardDto) {
        cardRepository.save(CardMapper.toEntity(cardDto));
    }
    @Override
    public void deleteById(String cardId) {
        cardRepository.deleteById(cardId);
    }
}