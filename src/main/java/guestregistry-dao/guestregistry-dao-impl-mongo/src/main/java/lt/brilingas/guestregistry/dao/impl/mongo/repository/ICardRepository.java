package lt.brilingas.guestregistry.dao.impl.mongo.repository;

import lt.brilingas.guestregistry.dao.impl.mongo.entity.card.CardEntity;
import lt.brilingas.guestregistry.data.dto.card.CardType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICardRepository extends MongoRepository<CardEntity, String> {
     List<CardEntity> getByCardType(CardType cardType);
     List<CardEntity> getByLocationId(String locationId);
}
