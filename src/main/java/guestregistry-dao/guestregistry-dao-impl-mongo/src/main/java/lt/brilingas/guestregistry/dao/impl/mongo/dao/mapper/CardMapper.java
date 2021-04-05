package lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.card.CardEntity;


public class CardMapper {
    public static CardDTO toDTO(CardEntity cardEntity) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(cardEntity.getId());
        cardDTO.setValidFrom(cardEntity.getValidFrom());
        cardDTO.setValidTo(cardEntity.getValidTo());
        cardDTO.setLocationId(cardEntity.getLocationId()==null?null:cardEntity.getLocationId());
        cardDTO.setCardType(cardEntity.getCardType());
        cardDTO.setCardAvailability(cardEntity.isCardAvailability());
        return cardDTO;
    }
    public static CardEntity toEntity(CardDTO cardDTO) {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(cardDTO.getId());
        cardEntity.setValidFrom(cardDTO.getValidFrom());
        cardEntity.setValidTo(cardDTO.getValidTo());
        cardEntity.setLocationId(cardDTO.getLocationId()==null?null:cardDTO.getLocationId());
        cardEntity.setCardType(cardDTO.getCardType());
        cardEntity.setCardAvailability(cardDTO.isCardAvailability());
        return cardEntity;
    }
}
