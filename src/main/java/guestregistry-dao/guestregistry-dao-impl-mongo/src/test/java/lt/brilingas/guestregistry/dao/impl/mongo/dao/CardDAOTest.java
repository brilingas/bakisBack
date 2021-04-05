package lt.brilingas.guestregistry.dao.impl.mongo.dao;
import lt.brilingas.guestregistry.dao.impl.mongo.config.DAOTestConfig;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.card.CardEntity;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.ICardRepository;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

@DataMongoTest
@ContextConfiguration(classes = {DAOTestConfig.class})
class CardDAOTest {
    @Autowired
    private CardDAO cardDAO;
    @Autowired
    private ICardRepository cardRepository;
/*
    @Test
    void insert() {
        CardDTO cardDTO=createCardDTO("1");
        Assertions.assertTrue(cardRepository.existsById(cardDAO.insert(cardDTO)));
    }

    @Test
    void getById() {
        String cardId = "1";
        saveCardEntity(cardId);
        Assertions.assertEquals(cardId,cardDAO.getById(cardId).toString());
        Assertions.assertThrows(NullPointerException.class, () -> cardDAO.getById(null));
    }

    @Test
    void getByType() {
        String cardId = "1";
        CardType cardType = CardType.GUEST;
        createCardDTO(cardId).setCardType(cardType);
        Assertions.assertNotEquals(new ArrayList<>(),cardDAO.getByType(cardType));
        Assertions.assertThrows(NullPointerException.class, () -> cardDAO.getByType(null));
    }

    @Test
    void getByLocationId() {
        String cardId = "1";
        String locationId = "2";
        createCardDTO(cardId).setLocationId(locationId);
        Assertions.assertNotEquals(new ArrayList<>(), cardDAO.getByLocationId(locationId));
        Assertions.assertThrows(NullPointerException.class, () -> cardDAO.getByLocationId(null));
    }

    @Test
    void getAll() {
        String cardId="1";
        cardRepository.deleteAll();
        Assertions.assertTrue(cardDAO.getAll().isEmpty());
        saveCardEntity(cardId);
        Assertions.assertEquals(1, cardDAO.getAll().size());
    }

    @Test
    void update() {
        String cardId = saveCardEntity("1");
        String newId = "2";
        CardDTO cardDTO = createCardDTO(newId);
        cardDTO.setId(cardId);
        cardDAO.update(cardDTO);
        Assertions.assertEquals(newId,cardRepository.findById(cardId).toString());
    }

    @Test
    void deleteById() {
        String cardId = saveCardEntity("1");
        cardDAO.deleteById(cardId);
        Assertions.assertFalse(cardRepository.existsById(cardId));
        Assertions.assertThrows(NullPointerException.class,()->cardDAO.deleteById(null));
    }
*/
    private CardDTO createCardDTO(String id) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(id);
        return cardDTO;
    }

    private String saveCardEntity(String id) {
        CardEntity cardEntity = createCardEntity(id);
        return cardRepository.save(cardEntity).getId();
    }

    private CardEntity createCardEntity(String id) {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(id);
        return cardEntity;
    }
}